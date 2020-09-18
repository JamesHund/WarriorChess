public class StoneWarrior extends Warrior implements WarriorTypeInterface {


    public StoneWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Stone", invSize, moves);
    }



    public void performSpecialAbility() {
        System.out.println("Special ability performed by stone warrior!");
    }

    public void specialAbilityCompleted() {
    }
}