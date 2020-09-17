package game;
public class StoneWarrior extends Warrior implements WarriorTypeInterface {


    public StoneWarrior(Position position, int id, int age, double health, int offense, int defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Stone", invSize, moves);
    }

    public void performSpecialAbility() {
    }

    public void specialAbilityCompleted() {
    }
}