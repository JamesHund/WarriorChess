import java.util.ArrayList;

public interface WarriorTypeInterface_24129429 {

    //Subtype specific methods

    void performSpecialAbility();

    //Warrior instance methods

    Position_24129429 getPosition();

    int getId();

    double getOffense();

    double getDefense();

    String getType();

    int getSpecialAbilityCount();

    int getSpecialAbilityTotalCount();

    boolean isSpecialAbilityBeingPerformed();

    boolean isAlive();

    boolean isInvisible();

    Weapon_24129429 pickupWeapon(Weapon_24129429 weapon);

    void adjustBufferHealth(double value);

    void adjustBufferOffense(double value);

    void adjustBufferDefense(double value);

    void updateValues();

    void move();
    
    void incrementAge();
    
    void decrementSpecialAbilityCount();

    void consumePotions(ArrayList<Potion> potions);

    String toString();

}
