package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static int iterations;
    public static int currentIteration = 0;
    public static int[] gridSize = new int[2]; // x - 0 (number of columns), y - 1 (number of rows)

    public static ArrayList<Warrior> warriors = new ArrayList<Warrior>();
    public static Water water = new Water(null);

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

        for (Warrior w : warriors) {
            System.out.println(w);
        }

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
            for(int i = 0; i < iterations; i++){
                iterate();
                printStatistics();
            }

        } else if (outputVersion == 1) {
            // warrior statistics with visualization mode
            printVisualization();
            for(int i = 0; i < iterations; i++){
                iterate();
                printVisualization();
            }
        } else {
            System.out.println("Usage: < program name > < name of the game setup file > .txt");
            return;
        }


//        Position.setGridSize(5,5);
//        Position p = new Position(0,0);
//        for(Position neighbour : p.getNeighbors()) {
//        	System.out.println(neighbour);
//        }
    }


    //steps the game forward in time a single iteration
    public static void iterate(){
        water.iterate();
        currentIteration++;
    }

    private static void printStatistics() {
        System.out.println();
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
    }

    public static void parseSetupFile(String filepath) {
        try {
            Scanner scFile = new Scanner(new File(filepath));
            Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
            iterations = scLine.nextInt();
            int rows = scLine.nextInt();
            int columns = scLine.nextInt();
            gridSize[0] = columns;
            gridSize[1] = rows;
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
                        warriors.add(warrior);
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
        //-----------Warriors------------
        {
            //parallel arrays that keep track of warrior positions and number of warriors at that position
            Position[] positions = new Position[warriors.size()];
            int[] positionCount = new int[warriors.size()];
            int numPositions = 0; //number of unique positions

            for (Warrior warrior : warriors) {
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
                    positionCount[numPositions] = 1;
                    numPositions++;
                } else {
                    positionCount[matchingIndex]++;
                }
            }

//            for(int i = 0; i < numPositions; i++){
//                System.out.println(positions[i] + "    " + positionCount[i]);
//            }

            for (int count : positionCount) {
                if(count >= 10){
                    System.out.println("Error: more than 10 warrior pieces were configured at the same position on the game grid.");
                    System.exit(0);
                }
            }
        }

        //-----------------------
    }

}