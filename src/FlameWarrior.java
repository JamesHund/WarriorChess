public class FlameWarrior extends Warrior implements WarriorTypeInterface {

    private double preSpecialDefense;
    private boolean initialSpecialRoutine = false;


    public FlameWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Flame", 3, invSize, moves);
    }

    public void performSpecialAbility() {
        System.out.println("Special ability performed by flame warrior!");
        if(!initialSpecialRoutine){
            initialSpecial();
            initialSpecialRoutine = true;
        }
        setBufferDefense(100);

    }

    public void specialAbilityCompleted() {
        setBufferDefense(preSpecialDefense);
        initialSpecialRoutine = false;
    }

    private void initialSpecial(){
        preSpecialDefense = getDefense();
    }
}