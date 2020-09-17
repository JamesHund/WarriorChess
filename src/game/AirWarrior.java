package game;
public class AirWarrior extends Warrior implements WarriorTypeInterface {

    public AirWarrior(Position position, int id, int age, double health, int offense, int defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Air", invSize, moves);
    }

    public void performSpecialAbility() {
    }

    public void specialAbilityCompleted() {
    }
}