package game;
public class WaterWarrior extends Warrior implements WarriorTypeInterface{

    public WaterWarrior(Position position, int id, int age, double health, int offense, int defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Water", invSize, moves);
    }


    public void performSpecialAbility() {
    }

    public void specialAbilityCompleted() {
    }

}