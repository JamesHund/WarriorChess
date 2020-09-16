package game;
public interface WarriorTypeInterface {

    void performSpecialAbility();
    void specialAbilityCompleted();

    public void backupValues();
    
    public void decrementWarriorInvisibility();
    
    public void setWarriorInvisibility(int val);
    
    public void setInTrance();
    
    public void setCuredFromTrance();
    
    public void setSpecialAbilityPerformed();
    
    public void incrementAge();
    
    public void setSpecialAbilityCount(int val);
    
    public void decrementSpecialAbilityCount();
    
    public void adjustDefenseStrength(double val);
    
    public void adjustOffensePower(double val);
    
    public void updateDefenseStrength();
    
    public void setDefenseStrength(double val);
    
    public void setOffensePowerStrength(double val);
    
    public void adjustHealth(double val);
    
}
