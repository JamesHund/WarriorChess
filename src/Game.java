import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static int iterations;
    public static int currentIteration = 0;
    public static int[] gridSize = new int[2]; // x - 0 (number of columns), y - 1 (number of rows)

    public static ArrayList<WarriorTypeInterface> warriors = new ArrayList<>();
    public static Water water;

    public static enum typeCode{
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
        try {
            Scanner test = new Scanner(new File(path));
            test.close();
        } catch (FileNotFoundException e) {
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
            return;
        }

        parseSetupFile(path);
        validateCells();
        //System.out.println(gridSize[0] + " " + gridSize[1]);

        int outputVersion;
        try {
            outputVersion = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
            return;
        }
        if (outputVersion == 0) {
            // warrior statistics mode
            printStatistics();
            System.out.println();
            for(int i = 0; i < iterations; i++){
                iterate();
                printStatistics();
                System.out.println();
            }

        } else if (outputVersion == 1) {
            // warrior statistics with visualization mode
            printVisualization();
            System.out.println();
            for(int i = 0; i < iterations; i++){
                //System.out.printf("\nCurrent iteration: %o\n", currentIteration);
                iterate();
                printVisualization();
                System.out.println();
            }
        } else {
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
        }


//        Position.setGridSize(5,5);
//        Position p = new Position(0,0);
//        for(Position neighbour : p.getNeighbors()) {
//        	System.out.println(neighbour);
//        }
    }


    //steps the game forward in time a single iteration
    public static void iterate(){
        currentIteration++;

        //water
        for(WarriorTypeInterface warrior: warriors){

            int waterInNeighbourhood = 0;
            for(Position p : warrior.getPosition().getNeighbors()){
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
        for(WarriorTypeInterface warrior : warriors){
            if(warrior.isSpecialAbilityPerformed()){
                warrior.decrementSpecialAbilityCount();
                warrior.performSpecialAbility();
            }
        }



        //adjust defence based on warriors of same type in same cell
        int[][] warriorPositions = getWarriorPositions();
        for(WarriorTypeInterface warrior : warriors){
            for(int i = 0; i < warriorPositions.length; i++){
                int xPos = warriorPositions[i][0];
                int yPos = warriorPositions[i][1];
                if(new Position(xPos,yPos).equals(warrior.getPosition())) {
                    int warriorTypeCode = typeCode.valueOf("" + warrior.getType().charAt(0)).ordinal();
                    int numSameType = warriorPositions[i][3 + warriorTypeCode];
                    if(numSameType > 1){
                        warrior.adjustBufferDefense(MULTIPLE_WARRIORS_HEALTH_BUFF*numSameType);
                    }
                }
            }
        }


        //battle stage
        ArrayList<WarriorTypeInterface> deadWarriors = new ArrayList<>();

        for(WarriorTypeInterface warrior : warriors){
            for(WarriorTypeInterface warrior2 : warriors){
                if(warrior2.getPosition().isInNeighborhood(warrior.getPosition())){
                    if(warrior.getDefense() < warrior2.getDefense()){
                        boolean alive = warrior.adjustBufferHealth(-1*warrior2.getOffense());
                        if(!alive) deadWarriors.add(warrior);
                    }
                }
            }
        }
        for(WarriorTypeInterface warrior : deadWarriors){
            warriors.remove(warrior);
            System.out.println("A warrior has left the game!");
        }

        for(WarriorTypeInterface warrior : warriors){
            warrior.updateValues();
            warrior.move();
            warrior.incrementAge();
        }
        water.iterate();
        validateNumberOfWarriors();

        if(warriors.size() == 1){
            System.out.println("A warrior has been proven victor!");
            System.exit(0);
        }

    }

    private static void printStatistics() {
        Warrior[] sortedWarriors = warriors.toArray(new Warrior[warriors.size()]);

        for(int i = 0; i < sortedWarriors.length; i++){
            for(int j = i + 1; j < sortedWarriors.length; j++){
                Position posI = sortedWarriors[i].getPosition();
                Position posJ = sortedWarriors[j].getPosition();
                int relativePosI = posI.getY()*gridSize[1] + posI.getX();
                int relativePosJ = posJ.getY()*gridSize[1] + posJ.getX();
                if(relativePosJ < relativePosI){
                    Warrior temp = sortedWarriors[i];
                    sortedWarriors[i] = sortedWarriors[j];
                    sortedWarriors[j] = temp;
                }else if(relativePosI == relativePosJ){
                    if(sortedWarriors[j].getId() < sortedWarriors[i].getId()){
                        Warrior temp = sortedWarriors[i];
                        sortedWarriors[i] = sortedWarriors[j];
                        sortedWarriors[j] = temp;
                    }
                }
            }
        }
        for(Warrior warrior : sortedWarriors){
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
                if(water.isWaterAtPosition(new Position(x,y))){
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
        printStatistics();
    }

    public static void parseSetupFile(String filepath) {
        try {
            Scanner scFile = new Scanner(new File(filepath));
            Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
            int rows = scLine.nextInt();
            int columns = scLine.nextInt();
            gridSize[0] = columns;
            gridSize[1] = rows;
            iterations = scLine.nextInt();

            water = new Water(null);

            while (scFile.hasNextLine()) {
                scLine = new Scanner(scFile.nextLine()).useDelimiter(": ");
                String category = scLine.next();
                int numEntries = scLine.nextInt();
                if (category.equals("Warrior")) {
                    //warriors = new Warrior[numEntries];
                    for (int i = 0; i < numEntries; i++) {

                        scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");

                        int row = scLine.nextInt();
                        int column = scLine.nextInt();
                        int id = scLine.nextInt();
                        String type = scLine.next();
                        int age = scLine.nextInt();
                        double health = scLine.nextDouble();
                        double offense = scLine.nextDouble();
                        double defense = scLine.nextDouble();
                        int invSize = scLine.nextInt();
                        String moves = scLine.next();

                        Warrior warrior;

                        switch (type) {
                            case "Stone":
                                warrior = new StoneWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            case "Water":
                                warrior = new WaterWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            case "Flame":
                                warrior = new FlameWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            case "Air":
                                warrior = new AirWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                                break;
                            default:
                                //this code should never run and is for debugging purposes
                                System.err.printf("Warrior type: %s does not exist", type);
                                System.exit(0);
                                return;
                        }

                        //warriors[i] = warrior;
                        warriors.add((WarriorTypeInterface) warrior);
                    }

                } else if (category.equals("Water")) {
                    Position[] positions = new Position[numEntries];
                    for(int i = 0; i < numEntries; i++){
                        String[] posString = scFile.nextLine().split(" ");
                        int row = Integer.parseInt(posString[0]);
                        int column = Integer.parseInt(posString[1]);
                        positions[i] = new Position(column, row);
                    }
                    water = new Water(positions);
                }
            }

            scFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found, please input a valid filename");
        }

    }

    //determines whether game setup file does not violate any game rules
    //terminates program if a rule is broken
    public static void validateCells(){
        //Warriors
        int[][] warriorPositions = getWarriorPositions();

        for (int[] n : warriorPositions) {
            if(n[2] > 10){
                System.out.println("Error: more than 10 warrior pieces were configured at the same position on the game grid.");
                System.exit(0);
            }
        }
    }

    public static void validateNumberOfWarriors(){
        int[][] warriorPositions = getWarriorPositions();

        for (int[] n : warriorPositions) {
            if(n[2] > 10){
                System.out.printf("Error: warrior limit exceeded at cell %s %s\n",n[1],n[0]);
                System.exit(0);
            }
        }
    }
    //--------------USEFUL METHODS--------------------


    //returns a 2d array in the form
    //{{x-coordinate,y-coordinate, number of warriors, number of air, number of flame, number of stone, number of water},...}
    public static int[][] getWarriorPositions(){
        //parallel arrays that keep track of warrior positions and number of warriors at that position
        Position[] positions = new Position[warriors.size()];
        int[] positionCount = new int[warriors.size()];
        int[] airCount = new int[warriors.size()];
        int[] flameCount = new int[warriors.size()];
        int[] stoneCount = new int[warriors.size()];
        int[] waterCount = new int[warriors.size()];
        int numPositions = 0; //number of unique positions

        for (WarriorTypeInterface warrior : warriors) {
            Position currentPosition = warrior.getPosition();
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
        int[][] hybridArr = new int[numPositions][];
        for(int i = 0; i < numPositions; i++){
            hybridArr[i] = new int[]{positions[i].getX(),positions[i].getY(),positionCount[i], airCount[i],flameCount[i],stoneCount[i],waterCount[i]};
        }
        return hybridArr;

    }

    //loops through warriors and returns the first warrior it finds at Position p
    //if no such warrior exists the method will return null
    //OBSOLETE
//    public static WarriorTypeInterface getFirstWarriorAtPosition(Position p){
//        for(WarriorTypeInterface warrior : warriors){
//            if(p.equals(warrior.getPosition())){
//                return warrior;
//            }
//        }
//        return null;
//    }

}