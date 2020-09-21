public class WaterWarrior extends Warrior implements WarriorTypeInterface{

    public WaterWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Water", 1, invSize, moves);
    }


    public void performSpecialAbility() {
        System.out.println("Special ability performed by water warrior!");
        adjustBufferHealth(20);
    }

    public void specialAbilityCompleted() {
    }

}