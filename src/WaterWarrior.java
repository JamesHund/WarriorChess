public class WaterWarrior extends Warrior implements WarriorTypeInterface{

    public WaterWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Water", invSize, moves);
    }


    public void performSpecialAbility() {
    }

    public void specialAbilityCompleted() {
    }

}