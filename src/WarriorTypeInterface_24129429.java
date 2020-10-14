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

    boolean canPickupWeapon();

    void pickupWeapon(Weapon_24129429 weapon);

    void adjustBufferHealth(double value);

    void adjustBufferOffense(double value);

    void adjustBufferDefense(double value);

    void updateValues();

    void move();
    
    void incrementAge();
    
    void decrementSpecialAbilityCount();

    String toString();

}
