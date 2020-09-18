public class AirWarrior extends Warrior implements WarriorTypeInterface {

    public AirWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Air", invSize, moves);
    }

    public void performSpecialAbility() {
    }

    public void specialAbilityCompleted() {
    }
}