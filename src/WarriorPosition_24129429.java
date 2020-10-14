import java.lang.reflect.Array;
import java.util.ArrayList;

//utility class
//stores the number of different types of warriors at a certain position
public class WarriorPosition_24129429 {
    private Position_24129429 position;
    private int numAir, numFlame, numStone, numWater;
    private ArrayList<WarriorTypeInterface_24129429> warriors = new ArrayList<>(); //array containing pointers to warriors occupying positions

    public WarriorPosition_24129429(Position_24129429 position) {
        this.position = position;
        this.numAir = 0;
        this.numFlame = 0;
        this.numStone = 0;
        this.numWater = 0;
    }

    public void addWarrior(WarriorTypeInterface_24129429 warrior){
        if(warrior.getPosition().equals(position)){
            warriors.add(warrior);
            switch (warrior.getType()){
                case "Air":
                    numAir++;
                    break;
                case "Flame":
                    numFlame++;
                    break;
                case "Stone":
                    numStone++;
                    break;
                case "Water":
                    numWater++;
                    break;
            }
        }
    }

    public ArrayList<WarriorTypeInterface_24129429> getWarriors(){
        return warriors;
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
        //2D array grouping warriors
        WarriorTypeInterface_24129429[][] positions = new WarriorTypeInterface_24129429[warriors.size()][warriors.size()];
        int[] warriorCount = new int[warriors.size()];
        int numPositions = 0; //number of unique positions

        //iterates through list of warriors and populates the above arrays
        for (WarriorTypeInterface_24129429 warrior : warriors) {
            Position_24129429 currentPosition = warrior.getPosition();
            boolean unique = true;
            int matchingIndex = -1;
            for (int p = 0; p < numPositions; p++) {
                if (currentPosition.equals(positions[p][0].getPosition())) {
                    unique = false;
                    matchingIndex = p;
                    break;
                }
            }

            if (unique) {
                positions[numPositions][0] = warrior;
                //matchingIndex = numPositions;
                warriorCount[numPositions]++;
                //System.out.printf("%s , %s\n",numPositions,warriorCount[numPositions]);
                numPositions++;
            }else{
                positions[matchingIndex][warriorCount[matchingIndex]] = warrior;
                warriorCount[matchingIndex]++;
            }
        }

        //hybrid array which combines all the parallel arrays
        WarriorPosition_24129429[] warriorPositions= new WarriorPosition_24129429[numPositions];
        for(int i = 0; i < numPositions; i++){
            warriorPositions[i] = new WarriorPosition_24129429(positions[i][0].getPosition());
            for(int j = 0; j < warriorCount[i]; j++){
                warriorPositions[i].addWarrior(positions[i][j]);
            }
        }
        return warriorPositions;
    }
}
