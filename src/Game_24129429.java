import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game_24129429 {

    public static int iterations;
    public static int currentIteration = 0;
    public static int[] gridSize = new int[2]; // x - 0 (number of columns), y - 1 (number of rows)

    public static ArrayList<WarriorTypeInterface_24129429> warriors = new ArrayList<>();
    public static Water_24129429 water;

    public enum typeCode{ //used as a way to index different warrior types
        A, F, S, W
    }

    //constants
    public static final double WATER_HEALTH_BUFF = 3;
    public static final double WATER_HEALTH_DEBUFF = -0.5;
    public static final double MULTIPLE_WARRIORS_HEALTH_BUFF = 2;
    private static final double WATER_WARRIOR_OFFENSE_BUFF = 1;



    public static void main(String[] args) {

        //-----validate arguments-----
        if(args.length != 2){
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
            return;
        }

        String path = args[0];
        try { //checks if first argument points to an actual file
            Scanner test = new Scanner(new File(path));
            test.close();
        } catch (FileNotFoundException e) {
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
            return;
        }

        int outputVersion;
        try { //checking if second argument is an integer
            outputVersion = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
            return;
        }

        //------------------------------

        parseSetupFile(path);
        validateCells();

        if (outputVersion == 0) { // warrior statistics mode
            evauluateRemainingWarriors();
            printStatistics();
            System.out.println();

            for(int i = 0; i < iterations; i++){
                iterate();
                printStatistics();
                System.out.println();
            }

        } else if (outputVersion == 1) { // warrior statistics with visualization mode
            printVisualization();
            evauluateRemainingWarriors();
            printStatistics();
            System.out.println();

            for(int i = 0; i < iterations; i++){
                iterate();
                printVisualization();
                printStatistics();
                System.out.println();
            }

        } else {
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
        }
    }


    //steps the game forward in time a single iteration
    public static void iterate(){
        currentIteration++;

        //perform water related health buffs and health debuffs
        for(WarriorTypeInterface_24129429 warrior: warriors){

            int waterInNeighbourhood = 0;
            for(Position_24129429 p : warrior.getPosition().getNeighbors()){
                if(water.isWaterAtPosition(p)){
                    waterInNeighbourhood++;
                }
            }
            if(waterInNeighbourhood > 0){
                warrior.adjustBufferHealth(WATER_HEALTH_BUFF);
                if(warrior.getType().equals("Water")){
                    warrior.adjustBufferOffense(WATER_WARRIOR_OFFENSE_BUFF*waterInNeighbourhood);
                }

            }else{
                warrior.adjustBufferHealth(WATER_HEALTH_DEBUFF);
            }
        }

        //perform special abilities
        for(WarriorTypeInterface_24129429 warrior : warriors){
            if(warrior.isSpecialAbilityBeingPerformed()){
                warrior.performSpecialAbility();
                warrior.decrementSpecialAbilityCount();
            }
        }

        //adjust defense based on warriors of same type in same cell
        int[][] warriorPositions = getWarriorPositions();
        for(WarriorTypeInterface_24129429 warrior : warriors){
            for (int[] warriorPosition : warriorPositions) {
                int xPos = warriorPosition[0];
                int yPos = warriorPosition[1];
                if (new Position_24129429(xPos, yPos).equals(warrior.getPosition())) {
                    int warriorIndex = typeCode.valueOf("" + warrior.getType().charAt(0)).ordinal(); //uses typeCode enum to get an index from 0 to 3
                    int numSameType = warriorPosition[3 + warriorIndex];
                    if (numSameType > 1) {
                        warrior.adjustBufferDefense(MULTIPLE_WARRIORS_HEALTH_BUFF * (numSameType - 1));
                    }
                }
            }
        }


        //------------------battle stage------------------
        for(WarriorTypeInterface_24129429 warrior : warriors){
            for(WarriorTypeInterface_24129429 warrior2 : warriors){
                if(warrior2.getPosition().isInNeighborhood(warrior.getPosition())){
                    if(warrior.getDefense() < warrior2.getDefense()){
                        warrior.adjustBufferHealth(-1*warrior2.getOffense());
                    }
                }
            }
        }

        //-------------remove dead warriors--------------
        //this is done in two parts since you cannot remove an element from an arraylist while iterating through it
        ArrayList<WarriorTypeInterface_24129429> deadWarriors = new ArrayList<>();

        for(WarriorTypeInterface_24129429 warrior : warriors){
            if(!warrior.isAlive()) deadWarriors.add(warrior);
        }

        for(WarriorTypeInterface_24129429 warrior: deadWarriors){
            warriors.remove(warrior);
            System.out.println("A warrior has left the game!");
        }

        //----------------------------------------------

        for(WarriorTypeInterface_24129429 warrior : warriors){
            warrior.incrementAge();
            warrior.updateValues();
            warrior.move();
        }
        water.iterate();
        validateNumberOfWarriors(true);

        evauluateRemainingWarriors();
    }

    private static void printStatistics() {
        WarriorTypeInterface_24129429[] sortedWarriors = new WarriorTypeInterface_24129429[warriors.size()];
        warriors.toArray(sortedWarriors);

        for(int i = 0; i < sortedWarriors.length; i++){
            for(int j = i + 1; j < sortedWarriors.length; j++){
                Position_24129429 posI = sortedWarriors[i].getPosition();
                Position_24129429 posJ = sortedWarriors[j].getPosition();
                int relativePosI = posI.getY()*gridSize[1] + posI.getX();
                int relativePosJ = posJ.getY()*gridSize[1] + posJ.getX();
                if(relativePosJ < relativePosI){
                    WarriorTypeInterface_24129429 temp = sortedWarriors[i];
                    sortedWarriors[i] = sortedWarriors[j];
                    sortedWarriors[j] = temp;
                }else if(relativePosI == relativePosJ){
                    if(sortedWarriors[j].getId() < sortedWarriors[i].getId()){
                        WarriorTypeInterface_24129429 temp = sortedWarriors[i];
                        sortedWarriors[i] = sortedWarriors[j];
                        sortedWarriors[j] = temp;
                    }
                }
            }
        }
        for(WarriorTypeInterface_24129429 warrior : sortedWarriors){
            System.out.println(warrior);
        }
    }


    private static void printVisualization() {
        String[][] board = new String[gridSize[0]][gridSize[1]];

        //populates board with warriors
        for(int[] warriorPosition : getWarriorPositions()){
            int xPos = warriorPosition[0];
            int yPos = warriorPosition[1];
            int numWarriors = warriorPosition[2];

            if(numWarriors > 1){
                board[xPos][yPos] = "" + numWarriors;
            }else{
                String type = "";
                for(int i = 3; i < 7; i++){
                    if(warriorPosition[i] > 0){
                        type = "" + typeCode.values()[i-3];
                    }
                }

                board[xPos][yPos] = "" + type.charAt(0);
            }
        }
        //everything else
        for(int y = 0; y < gridSize[1]; y++){
            for(int x = 0; x < gridSize[0]; x++){
                if(board[x][y] != null) continue; //checks whether board position has been populated
                if(water.isWaterAtPosition(new Position_24129429(x,y))){
                    board[x][y] = "w";
                }else{
                    board[x][y] = ".";
                }

            }
        }

        for(int y = 0; y < gridSize[1]; y++) {
            for (int x = 0; x < gridSize[0]; x++) {
                System.out.print(board[x][y] + " ");
            }
            System.out.println();
        }
    }

    public static void parseSetupFile(String filepath) {
        try {
            Scanner scFile = new Scanner(new File(filepath));
            Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");

            gridSize[1] = scLine.nextInt(); //rows
            gridSize[0] = scLine.nextInt(); //columns
            iterations = scLine.nextInt();

            Position_24129429.setGridSize(gridSize);
            water = new Water_24129429(gridSize);

            //iterates through each line of the game setup file
            while (scFile.hasNextLine()) {
                String nextLine = scFile.nextLine();
                scLine = new Scanner(nextLine).useDelimiter(": ");
                String category = scLine.next();
                int numEntries = scLine.nextInt();

                if (category.equals("Warrior")) {
                    for (int i = 0; i < numEntries; i++) {
                        nextLine = scFile.nextLine();
                        scLine = new Scanner(nextLine).useDelimiter(" ");

                        int row = scLine.nextInt();
                        int column = scLine.nextInt();
                        int id = scLine.nextInt();
                        String type = scLine.next();
                        int age = scLine.nextInt();
                        double health = Double.parseDouble(scLine.next());
                        double offense = Double.parseDouble(scLine.next());
                        double defense = Double.parseDouble(scLine.next());
                        int invSize = scLine.nextInt();
                        String moves = scLine.next();

                        Warrior_24129429 warrior;

                        switch (type) {
                            case "Stone":
                                warrior = new StoneWarrior_24129429(new Position_24129429(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            case "Water":
                                warrior = new WaterWarrior_24129429(new Position_24129429(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            case "Flame":
                                warrior = new FlameWarrior_24129429(new Position_24129429(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            case "Air":
                                warrior = new AirWarrior_24129429(new Position_24129429(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            default:
                                //this code should never run and is for debugging purposes
                                System.err.printf("Warrior type: %s does not exist", type);
                                System.exit(0);
                                return;
                        }

                        //warriors[i] = warrior;
                        warriors.add((WarriorTypeInterface_24129429) warrior);
                    }

                } else if (category.equals("Water")) {
                    Position_24129429[] positions = new Position_24129429[numEntries];
                    for(int i = 0; i < numEntries; i++){
                        String[] posString = scFile.nextLine().split(" ");
                        int row = Integer.parseInt(posString[0]);
                        int column = Integer.parseInt(posString[1]);
                        positions[i] = new Position_24129429(column, row);
                    }
                    water.populateGrid(positions);
                }
            }

            scFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found, please input a valid filename");
        }

    }

    //checks if there is one or zero warriors remaining and terminates the game accordingly
    public static void evauluateRemainingWarriors(){
        if(warriors.size() == 1){
            System.out.println("A warrior has been proven victor!");
            System.exit(0);
        }else if(warriors.size() == 0){
            System.out.println("No warriors are left!");
            System.exit(0);
        }
    }

    //determines whether game setup file does not violate any game rules
    //terminates program if a rule is broken
    public static void validateCells(){
        //Warriors
        validateNumberOfWarriors(false);
        //Other pieces
    }

    //Determines whether there are more than 10 warriors at any one position.
    //errorMessage should be false if the method is called before the game starts iterating
    //as different error messages are printed depending on the game state
    public static void validateNumberOfWarriors(boolean errorMessage){
        int[][] warriorPositions = getWarriorPositions();

        for (int[] n : warriorPositions) {
            if(n[2] > 10){
                if(errorMessage) {
                    System.out.printf("Error: warrior limit exceeded at cell %s %s\n", n[1], n[0]);
                }else{
                    System.out.println("Error: more than 10 warrior pieces were configured at the same position on the game grid.");
                }
                System.exit(0);
            }
        }
    }
    //--------------USEFUL METHODS--------------------


    //returns a 2d array in the form
    //{{x-coordinate,y-coordinate, number of warriors, number of air, number of flame, number of stone, number of water},...}
    //used to show how many warriors and what type of warriors are at each occupied position
    public static int[][] getWarriorPositions(){
        //parallel arrays that keep track of warrior positions and number of warriors at that position
        Position_24129429[] positions = new Position_24129429[warriors.size()];
        int[] positionCount = new int[warriors.size()];
        //arrays that keep track of number of specific types of warriors at each position
        int[] airCount = new int[warriors.size()];
        int[] flameCount = new int[warriors.size()];
        int[] stoneCount = new int[warriors.size()];
        int[] waterCount = new int[warriors.size()];
        int numPositions = 0; //number of unique positions

        //iterates through list of warriors and populates the above arrays
        for (WarriorTypeInterface_24129429 warrior : warriors) {
            Position_24129429 currentPosition = warrior.getPosition();
            boolean unique = true;
            int matchingIndex = -1;
            for (int p = 0; p < numPositions; p++) {
                if (currentPosition.equals(positions[p])) {
                    unique = false;
                    matchingIndex = p;
                    break;
                }
            }

            if (unique) {
                positions[numPositions] = currentPosition;
                matchingIndex = numPositions;
                numPositions++;
            }
            positionCount[matchingIndex]++;
            switch(warrior.getType()){
                case "Air":
                    airCount[matchingIndex]++;
                    break;
                case "Flame":
                    flameCount[matchingIndex]++;
                    break;
                case "Stone":
                    stoneCount[matchingIndex]++;
                    break;
                case "Water":
                    waterCount[matchingIndex]++;
            }
        }

        //hybrid array which combines all the parallel arrays
        int[][] hybridArr = new int[numPositions][];
        for(int i = 0; i < numPositions; i++){
            hybridArr[i] = new int[]{positions[i].getX(),positions[i].getY(),positionCount[i], airCount[i],flameCount[i],stoneCount[i],waterCount[i]};
        }
        return hybridArr;

    }

}