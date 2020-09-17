package game;
public class FlameWarrior extends Warrior implements WarriorTypeInterface {


    public FlameWarrior(Position position, int id, int age, double health, int offense, int defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Flame", invSize, moves);
    }

    public void performSpecialAbility() {
    }

    public void specialAbilityCompleted() {
    }
}