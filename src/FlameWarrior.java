public class FlameWarrior extends Warrior implements WarriorTypeInterface {

    private double preSpecialDefense;


    public FlameWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Flame", 2, invSize, moves);
    }

    public void performSpecialAbility() {

        if(getSpecialAbilityCount() == getSpecialAbilityTotalCount()){
            initialSpecial();
            setBufferDefenseSpecial(100);
        }else if(getSpecialAbilityCount() == 0){
            specialAbilityCompleted();
        }else{
            setBufferDefenseSpecial(100);
        }

    }

    public void specialAbilityCompleted() {
        setBufferDefense(preSpecialDefense);
    }

    private void initialSpecial(){
        System.out.println("Special ability performed by flame warrior!");
        preSpecialDefense = getDefense();
    }
}