package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    public static int iterations, rows,columns;
    public static Warrior[] warriors;

    public static void main(String[] args) {
    	
        String path = args[0];
        parseInput(path);
        int outputVersion = Integer.parseInt(args[1]);
        if (outputVersion == 0) {
            // warrior statistics mode
        } else if (outputVersion == 1) {
            // warrior statistics with visualization mode
        }


//        Position.setGridSize(5,5);
//        Position p = new Position(0,0);
//        for(Position neighbour : p.getNeighbors()) {
//        	System.out.println(neighbour);
//        }
    }

    public static void parseInput(String filepath){
        try {
            Scanner scFile = new Scanner(new File(filepath));
            Scanner scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");
            iterations = scLine.nextInt();
            rows = scLine.nextInt();
            columns = scLine.nextInt();
            while(scFile.hasNextLine()){
                scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");

                if(scLine.next().contains("Warrior")){
                    int numWarriors = scLine.nextInt();
                    warriors = new Warrior[numWarriors];
                    for(int i = 0; i < numWarriors; i++){

                        scLine = new Scanner(scFile.nextLine()).useDelimiter(" ");

                        int row = scLine.nextInt();
                        int column = scLine.nextInt();
                        int id = scLine.nextInt();
                        String type = scLine.next();
                        int age = scLine.nextInt();
                        double health = scLine.nextDouble();
                        int offense = scLine.nextInt();
                        int defense = scLine.nextInt();
                        int invSize = scLine.nextInt();
                        String moves = scLine.next();

                        Warrior warrior;
                        switch (type) {
                            case "Stone" -> warrior = new StoneWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                            case "Water" -> warrior = new WaterWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                            case "Flame" -> warrior = new FlameWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                            case "Air" -> warrior = new AirWarrior(new Position(column, row), id, age, health, offense, defense, invSize, moves);
                            default -> {
                                System.err.printf("Warrior type: %s does not exist", type);
                                System.exit(0);
                                return;
                            }
                        }

                        warriors[i] = warrior;
                    }
                }else{
                    // do water actions
                }
            }
        }catch(FileNotFoundException e){
            System.err.println("File not found, please input a valid filename");
        }
    }

}