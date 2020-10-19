import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game_24129429 {

    public static int iterations;
    public static int currentIteration = 0;
    public static int[] gridSize = new int[2]; // x - 0 (number of columns), y - 1 (number of rows)

    public static ArrayList<WarriorTypeInterface_24129429> warriors = new ArrayList<>();
    public static ArrayList<Weapon_24129429> weapons = new ArrayList<>();
    public static Water_24129429 water;
    public static Crystal_24129429 crystal;

    public static WarriorPosition_24129429[] warriorPositions;

    //constants
    public final static String[] typeIndex = new String[]{"Air", "Flame", "Stone", "Water"};
    public static final double WATER_HEALTH_BUFF = 3;
    public static final double WATER_HEALTH_DEBUFF = -0.5;
    public static final double MULTIPLE_WARRIORS_HEALTH_BUFF = 2;
    public static final double WATER_WARRIOR_OFFENSE_BUFF = 1;

    //bonus features
    public static Potion_24129429[] potions;
    public static Restorer_24129429[] restorers;
    public static Position_24129429[] peacemakers;
        /*(storing peacemakers as positions because that is the only data that pertains to them)
        all peacemaker logic is handled within the iterate function */

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
            printStatistics();
            System.out.println();

            for(int i = 0; i < iterations; i++){
                iterate();
                printStatistics();
                System.out.println();
            }

        } else if (outputVersion == 1) { // warrior statistics with visualization mode
            printVisualization();
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

        boolean crystalActivated = false;


        if(crystal != null && crystal.isAbilityActivated(warriorPositions)){
            crystalActivated = true;
            System.out.println("The Magic Crystal has been activated! Four warriors remain...");
            warriors = getWarriorsAtPositions(crystal.getCornerPositions());
            crystal = null;

        }

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
        //BONUS -- perform restorer piece buffs
        if(restorers != null) {
            for (Restorer_24129429 restorer : restorers) {
                int benefit = restorer.getBenefit();
                Position_24129429[] neighbourhood = restorer.getPosition().getNeighbors();

                for (WarriorTypeInterface_24129429 warrior : getWarriorsAtPositions(neighbourhood)) {
                    warrior.adjustBufferHealth(restorer.getBenefit());
                }
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
        for(WarriorTypeInterface_24129429 warrior : warriors){
            for (WarriorPosition_24129429 warriorPosition : warriorPositions) {
                if (warriorPosition.getPosition().equals(warrior.getPosition())) {
                    int numSameType = warriorPosition.getNumType(warrior.getType());
                    if (numSameType > 1) {
                        warrior.adjustBufferDefense(MULTIPLE_WARRIORS_HEALTH_BUFF * (numSameType - 1));
                    }
                }
            }
        }
        //---------------------BONUS-----------------------
        //apply potion effects to warriors
        if(potions != null) {
            for (WarriorTypeInterface_24129429 warrior : warriors) {
                ArrayList<Potion_24129429> potionsToConsume = new ArrayList<>();
                for (Potion_24129429 potion : potions) {
                    if (warrior.getPosition().isInNeighborhood(potion.getPosition())) {
                        potionsToConsume.add(potion);
                    }
                }
                warrior.consumePotions(potionsToConsume);

            }
        }

        //peacemaker check
        if(peacemakers != null) {
            for (Position_24129429 peacemaker : peacemakers) {
                ArrayList<WarriorTypeInterface_24129429> immuneWarriors = getWarriorsAtPositions(peacemaker.getNeighbors());
                for (WarriorTypeInterface_24129429 warrior : immuneWarriors) {
                    warrior.setImmune();
                }

            }
        }

        //------------------battle stage------------------
        if(!crystalActivated) {
            for (WarriorTypeInterface_24129429 warrior : warriors) {
                for (WarriorTypeInterface_24129429 warrior2 : warriors) {
                    if (warrior2.getPosition().isInNeighborhood(warrior.getPosition())) {
                        if(!warrior.isInvisible() && !warrior2.isInvisible()) {
                            if (warrior.getDefense() < warrior2.getDefense()) {
                                warrior.adjustBufferHealth(-1 * warrior2.getOffense());
                            }
                        }
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


        //NB - update warrior positions
        warriorPositions = WarriorPosition_24129429.getWarriorPositions(warriors);

        //-------------------weapons--------------------

        performWeaponPickup();

        //--------------------------------------------

        water.iterate();
        validateNumberOfWarriors(true);

        evaluateRemainingWarriors();
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

        //populate board with empty cells
        for(int y = 0; y < gridSize[1]; y++){
            for(int x = 0; x < gridSize[0]; x++) {
                board[x][y] = ".";
            }
        }
        //potions
        if(potions != null){
            for(Potion_24129429 potion : potions){
                Position_24129429 pos = potion.getPosition();
                board[pos.getX()][pos.getY()] = "p";
            }
        }


        //weapons
        if(weapons != null) {
            for (Weapon_24129429 weapon : weapons) {
                Position_24129429 pos = weapon.getPosition();
                board[pos.getX()][pos.getY()] = "x";
            }
        }

        //healers
        if(peacemakers != null) {
            for (Position_24129429 peacemaker : peacemakers) {
                board[peacemaker.getX()][peacemaker.getY()] = "h";
            }
        }
        if(restorers != null) {
            for (Restorer_24129429 restorer : restorers) {
                Position_24129429 pos = restorer.getPosition();
                board[pos.getX()][pos.getY()] = "h";
            }
        }

        //water
        for(int y = 0; y < gridSize[1]; y++){
            for(int x = 0; x < gridSize[0]; x++){
                if(water.isWaterAtPosition(new Position_24129429(x,y))){
                    board[x][y] = "w";
                }
            }
        }

        //magic crystal
        if(crystal != null){
            Position_24129429 pos = crystal.getPosition();
            board[pos.getX()][pos.getY()] = "c";
        }

        //populates board with warriors
        for(WarriorPosition_24129429 warriorPosition : warriorPositions){
            Position_24129429 pos = warriorPosition.getPosition();
            int numWarriors = warriorPosition.getNumWarriors();

            if(numWarriors > 1){
                board[pos.getX()][pos.getY()] = "" + numWarriors;
            }else{
                char typeChar = ' ';
                for(String type : typeIndex){
                    if(warriorPosition.getNumType(type) == 1){
                        typeChar = type.charAt(0);
                    }
                }

                board[pos.getX()][pos.getY()] = "" + typeChar;
            }
        }

        //print board
        for(int y = 0; y < gridSize[1]; y++) {
            for (int x = 0; x < gridSize[0]; x++) {
                System.out.print(board[x][y] + " ");
            }
            System.out.println();
        }
    }

    public static void performWeaponPickup(){
        ArrayList<Weapon_24129429> weaponsForRemoval = new ArrayList<>();
        ArrayList<Weapon_24129429> weaponsForAddition = new ArrayList<>();
        if(weapons != null) {
            for (Weapon_24129429 weapon : weapons) {
                ArrayList<WarriorTypeInterface_24129429> tempWarriors = getWarriorsAtPositions(new Position_24129429[]{weapon.getPosition()});

                if(tempWarriors.size() != 0) {
                    WarriorTypeInterface_24129429 warriorToPickup = tempWarriors.get(0);
                    for (WarriorTypeInterface_24129429 warrior : tempWarriors) {
                        if (warriorToPickup.getOffense() < warrior.getOffense()) {
                            warriorToPickup = warrior;
                        }else if(warriorToPickup.getOffense() == warrior.getOffense() && warrior.getId() < warriorToPickup.getId()){
                            warriorToPickup = warrior;
                        }
                    }
                    Weapon_24129429 weaponToBeDropped = warriorToPickup.pickupWeapon(weapon);
                    weaponsForRemoval.add(weapon);

                    if(weaponToBeDropped != null){
                        weaponsForAddition.add(weaponToBeDropped);
                        weaponToBeDropped.setPosition(weapon.getPosition());
                    }
                }
            }
        }

        weapons.removeAll(weaponsForRemoval);
        weapons.addAll(weaponsForAddition);
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
                //System.out.println(nextLine);
                scLine = new Scanner(nextLine).useDelimiter(": ");

                String category = scLine.next();
                int numEntries = scLine.nextInt();

                switch (category) {
                    case "Warrior":
                        for (int i = 0; i < numEntries; i++) {
                            nextLine = scFile.nextLine();
                            scLine = new Scanner(nextLine).useDelimiter(" ");

                            int row = scLine.nextInt();
                            int column = scLine.nextInt();
                            int id = scLine.nextInt();
                            String type = scLine.next();
                            int age = scLine.nextInt();

                            //cant use scLine.nextDouble() because it doesn't work
                            //with certain Locale settings unless running under Java 8
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

                        //IMPORTANT
                        warriorPositions = WarriorPosition_24129429.getWarriorPositions(warriors);
                        break;
                    case "Water":
                        Position_24129429[] positions = new Position_24129429[numEntries];
                        for (int i = 0; i < numEntries; i++) {
                            String[] posString = scFile.nextLine().split(" ");
                            int row = Integer.parseInt(posString[0]);
                            int column = Integer.parseInt(posString[1]);
                            positions[i] = new Position_24129429(column, row);
                        }
                        water.populateGrid(positions);
                        break;
                    case "Magic Crystal":
                        if (numEntries != 1) {
                            System.out.println("Error: multiple magic crystal pieces configured on the grid.");
                            System.exit(0);
                        }
                        nextLine = scFile.nextLine();
                        scLine = new Scanner(nextLine).useDelimiter(" ");

                        Position_24129429 pos;
                        {
                            int row = scLine.nextInt();
                            int column = scLine.nextInt();
                            pos = new Position_24129429(column,row);
                        }
                        crystal = new Crystal_24129429(pos);
                        break;
                    case "Weapon":
                        for(int i = 0; i < numEntries; i++){
                            scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
                            int row = scLine.nextInt();
                            int column = scLine.nextInt();
                            double offense = Double.parseDouble(scLine.next());
                            weapons.add(new Weapon_24129429(new Position_24129429(column,row),offense));
                        }
                        performWeaponPickup();
                        break;
                    case "Potion":
                        potions = new Potion_24129429[numEntries];
                        for(int i = 0; i < numEntries; i++){
                            scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
                            int row = scLine.nextInt();
                            int column = scLine.nextInt();
                            int type = scLine.nextInt();

                            if(type == Potion_24129429.potionType.INVISIBILITY.ordinal()){
                                int iterations = scLine.nextInt();
                                potions[i] = new InvisibilityPotion(new Position_24129429(column,row), type, iterations);
                            }else{
                                potions[i] = new Potion_24129429(new Position_24129429(column,row), type);
                            }

                        }
                        break;
                    case "Restorer":
                        restorers = new Restorer_24129429[numEntries];
                        for(int i = 0; i < numEntries; i++){
                            scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
                            int row = scLine.nextInt();
                            int column = scLine.nextInt();
                            int benefit = scLine.nextInt();
                            restorers[i] = new Restorer_24129429(new Position_24129429(column,row), benefit);
                        }
                        break;
                    case "Peacemaker":
                        peacemakers = new Position_24129429[numEntries];
                        for(int i = 0; i < numEntries; i++){
                            scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
                            int row = scLine.nextInt();
                            int column = scLine.nextInt();
                            peacemakers[i] = new Position_24129429(column,row);
                        }
                        break;
                }
            }

            scFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found, please input a valid filename");
        }

    }

    //checks if there is one or zero warriors remaining and terminates the game accordingly
    public static void evaluateRemainingWarriors(){
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
        String errorMessage = "Error: multiple %s pieces were configured at the same position on the game grid.\n";
        //Warriors
        validateNumberOfWarriors(false);
        //Weapon
        if(weapons != null) {
            for (int i = 0; i < weapons.size(); i++) {
                for (int j = i + 1; j < weapons.size(); j++) {
                    if (weapons.get(i).getPosition().equals(weapons.get(j).getPosition())) {
                        System.out.printf(errorMessage, "weapon");
                    }
                }
            }
        }
        //--------Bonus---------
        //Potion
        if(potions != null) {
            for (int i = 0; i < potions.length; i++) {
                for (int j = i + 1; j < potions.length; j++) {
                    if (potions[i].getPosition().equals(potions[j].getPosition())) {
                        System.out.printf(errorMessage, "potion");
                    }
                }
            }
        }
        //Healer
        ArrayList<Position_24129429> healerPositions = new ArrayList<>();
        if(restorers != null) {
            for (Restorer_24129429 restorer : restorers) {
                healerPositions.add(restorer.getPosition());
            }
        }
        if(peacemakers != null) {
            for(Position_24129429 peacemaker : peacemakers){
                healerPositions.add(peacemaker);
            }
        }
        for(int i = 0; i < healerPositions.size(); i++){
            for(int j = i + 1; j < healerPositions.size(); j++){
                if(healerPositions.get(i).equals(healerPositions.get(j))){
                    System.out.printf(errorMessage,"healer");
                }
            }
        }

    }

    //Determines whether there are more than 10 warriors at any one position.
    //errorMessage should be false if the method is called before the game starts iterating
    //as different error messages are printed depending on the game state
    public static void validateNumberOfWarriors(boolean errorMessage){

        for (WarriorPosition_24129429 pos : warriorPositions) {
            if(pos.getNumWarriors() > 10){
                if(errorMessage) {
                    System.out.printf("Error: warrior limit exceeded at cell %s %s\n", pos.getPosition().getY(), pos.getPosition().getX());
                }else{
                    System.out.println("Error: more than 10 warrior pieces were configured at the same position on the game grid.");
                }
                System.exit(0);
            }
        }
    }
    //--------------USEFUL METHODS--------------------

    //returns warriors that occupy any of the provided positions
    //if multiple warriors exist at position it returns the warrior with lowest index in the warriors array
    //intended to be used when it is known only a single warrior occupies said position
    public static ArrayList<WarriorTypeInterface_24129429> getWarriorsAtPositions(Position_24129429[] positions){
        ArrayList<WarriorTypeInterface_24129429> warriorsAtPositions = new ArrayList<>();
        for(WarriorPosition_24129429 warriorPos : warriorPositions){
            for(Position_24129429 position : positions){
                if(warriorPos.getPosition().equals(position)){
                    warriorsAtPositions.addAll(warriorPos.getWarriors());
                }
            }
        }
        return warriorsAtPositions;
    }


}