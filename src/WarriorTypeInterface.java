public interface WarriorTypeInterface {

    void performSpecialAbility();
    void specialAbilityCompleted();

    public Position getPosition();

    public int getId();

    public double getHealth();

    public double getOffense();

    public double getDefense();

    public String getType();

    public boolean isSpecialAbilityPerformed();

    public boolean adjustBufferHealth(double value);

    public void adjustBufferOffense(double value);

    public void adjustBufferDefense(double value);

    public void setBufferDefense(double value);

    public void updateValues();

    public void move();
    
    public void decrementWarriorInvisibility();
    
    public void setWarriorInvisibility(int val);
    
    public void setInTrance();
    
    public void setCuredFromTrance();
    
    public void queueSpecialAbility();
    
    public void incrementAge();
    
    public void decrementSpecialAbilityCount();

    public String toString();


}
