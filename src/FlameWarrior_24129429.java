public class FlameWarrior_24129429 extends Warrior_24129429 implements WarriorTypeInterface_24129429 {

    private double preSpecialDefense;


    public FlameWarrior_24129429(Position_24129429 position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Flame", 2, invSize, moves);
    }

    private void initialSpecial(){
        System.out.println("Special ability performed by flame warrior!");
        preSpecialDefense = getDefense();
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

    private void specialAbilityCompleted() {
        //set buffer defense to maximum of previous defence and 70
        setBufferDefense(preSpecialDefense>70?preSpecialDefense:70);
    }

}