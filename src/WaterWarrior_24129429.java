public class WaterWarrior_24129429 extends Warrior_24129429 implements WarriorTypeInterface_24129429 {

    public WaterWarrior_24129429(Position_24129429 position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Water", 1, invSize, moves);
    }


    public void performSpecialAbility() {
        System.out.println("Special ability performed by water warrior!");
        adjustBufferHealth(20);
    }

}