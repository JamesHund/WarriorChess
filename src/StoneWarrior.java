public class StoneWarrior extends Warrior implements WarriorTypeInterface {

    private static final double STONE_WARRIOR_DEFENSE_BUFF = 95;
    private double preSpecialDefense;

    public StoneWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Stone", 4, invSize, moves);
    }



    public void performSpecialAbility() {
        if(getSpecialAbilityCount() == 0){
            specialAbilityCompleted();
            return;
        }

        if(getSpecialAbilityCount() == getSpecialAbilityTotalCount()){
            initialSpecial();
        }

        if(getDefense() < STONE_WARRIOR_DEFENSE_BUFF){
            setBufferDefenseSpecial(STONE_WARRIOR_DEFENSE_BUFF);
        }else{
            setBufferDefense(getDefense());
        }

    }

    public void specialAbilityCompleted() {
        setBufferDefense(preSpecialDefense);
    }

    private void initialSpecial(){
        preSpecialDefense = getDefense();
        System.out.println("Special ability performed by stone warrior!");
        adjustBufferHealth(-3);
    }
}