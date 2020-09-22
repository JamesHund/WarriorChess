public interface WarriorTypeInterface {

    void performSpecialAbility();
    void specialAbilityCompleted();

    Position getPosition();

    int getId();

    double getHealth();

    double getOffense();

    double getDefense();

    String getType();

    int getSpecialAbilityCount();

    int getSpecialAbilityTotalCount();

    boolean isSpecialAbilityBeingPerformed();

    boolean isAlive();

    void adjustBufferHealth(double value);

    void adjustBufferOffense(double value);

    void adjustBufferDefense(double value);

    void updateValues();

    void move();
    
    void incrementAge();
    
    void decrementSpecialAbilityCount();

    public String toString();



    //    void decrementWarriorInvisibility();
    //
    //    void setWarriorInvisibility(int val);
    //
    //    void setInTrance();
    //
    //    void setCuredFromTrance();


}
