import java.util.ArrayList;

//utility class
//stores the number of different types of warriors at a certain position
public class WarriorPosition_24129429 {
    private Position_24129429 position;
    private int numAir, numFlame, numStone, numWater;

    public WarriorPosition_24129429(Position_24129429 position, int numAir, int numFlame, int numStone, int numWater) {
        this.position = position;
        this.numAir = numAir;
        this.numFlame = numFlame;
        this.numStone = numStone;
        this.numWater = numWater;
    }

    public Position_24129429 getPosition() {
        return position;
    }

    public int getNumAir() {
        return numAir;
    }

    public int getNumFlame() {
        return numFlame;
    }

    public int getNumStone() {
        return numStone;
    }

    public int getNumWater() {
        return numWater;
    }

    public int getNumType(String type){
        switch (type){
            case "Air":
                return getNumAir();
            case "Flame":
                return getNumFlame();
            case "Stone":
                return getNumStone();
            case "Water":
                return getNumWater();
        }
        return -1;
    }

    public int getNumWarriors(){
        return numAir + numFlame + numStone + numWater;
    }

    //returns an array of WarriorPosition objects generated from an ArrayList of type WarriorTypeInterface
    public static WarriorPosition_24129429[] getWarriorPositions(ArrayList<WarriorTypeInterface_24129429> warriors){
        //parallel arrays that keep track of warrior positions and number of warriors at that position
        Position_24129429[] positions = new Position_24129429[warriors.size()];
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
        WarriorPosition_24129429[] warriorPositions= new WarriorPosition_24129429[numPositions];
        for(int i = 0; i < numPositions; i++){
            warriorPositions[i] = new WarriorPosition_24129429(positions[i], airCount[i],flameCount[i],stoneCount[i],waterCount[i]);
        }
        return warriorPositions;
    }
}
