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

    boolean isSpecialAbilityBeingPerformed();

    boolean isAlive();

    boolean isVisible();

    Weapon_24129429 pickupWeapon(Weapon_24129429 weapon);

    void adjustBufferHealth(double value);

    void adjustBufferOffense(double value);

    void adjustBufferDefense(double value);

    void setImmune();

    void updateValues();

    void move();
    
    void incrementAge();
    
    void decrementSpecialAbilityCount();

    void consumePotions(ArrayList<Potion_24129429> potions);

    String toString();

}
