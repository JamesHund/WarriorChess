package game;
public class Warrior{
	private int id,age, offense, defense, invSize;
	private double health;
	private Position position;
	private String type, moves;
	//list of movements
	//list of weapons

	public Warrior(Position position, int id, int age, double health, int offense, int defense, String type, int invSize, String moves) {
		this.position = position;
		this.id = id;
		this.age = age;
		this.health = health;
		this.offense = offense;
		this.defense = defense;
		this.type = type;
		this.invSize = invSize;
		this.moves = moves;
	}

	public void backupValues() {
		// TODO Auto-generated method stub

	}

	public void decrementWarriorInvisibility() {
		// TODO Auto-generated method stub
		
	}

	public void setWarriorInvisibility(int val) {
		// TODO Auto-generated method stub
		
	}

	public void setInTrance() {
		// TODO Auto-generated method stub
		
	}

	public void setCuredFromTrance() {
		// TODO Auto-generated method stub
		
	}

	public void setSpecialAbilityPerformed() {
		// TODO Auto-generated method stub
		
	}

	public void incrementAge() {
		// TODO Auto-generated method stub
		
	}

	public void setSpecialAbilityCount(int val) {
		// TODO Auto-generated method stub
		
	}

	public void decrementSpecialAbilityCount() {
		// TODO Auto-generated method stub
		
	}

	public void adjustDefenseStrength(double val) {
		// TODO Auto-generated method stub
		
	}

	public void adjustOffensePower(double val) {
		// TODO Auto-generated method stub
		
	}

	public void updateDefenseStrength() {
		// TODO Auto-generated method stub
		
	}

	public void setDefenseStrength(double val) {
		// TODO Auto-generated method stub
		
	}

	public void setOffensePowerStrength(double val) {
		// TODO Auto-generated method stub
		
	}

	public void adjustHealth(double val) {
		// TODO Auto-generated method stub
		
	}
	
}