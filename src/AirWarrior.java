public class AirWarrior extends Warrior implements WarriorTypeInterface {

    public AirWarrior(Position position, int id, int age, double health, double offense, double defense, int invSize, String moves) {
        super(position, id, age, health, offense, defense, "Air", 3, invSize, moves);
    }

    //called when special ability initially performed
    private void initialSpecial(){
        System.out.println("Special ability performed by air warrior!");
        adjustBufferOffense(30);
    }

    public void performSpecialAbility() {
        if(getSpecialAbilityCount() == getSpecialAbilityTotalCount()){
            initialSpecial();
        }
        if(getSpecialAbilityCount() == 0){
            specialAbilityCompleted();
        }

    }

    public void specialAbilityCompleted() {
        adjustBufferOffense(-30);
    }


}