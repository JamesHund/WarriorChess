package game;
public class Warrior{
	private int id,age, invSize;
	private double health, offense, defense;
	private Position position;
	private String type, moves;
	//list of movements
	private int numWeapons = 0;
	//list of weapons

	public Warrior(Position position, int id, int age, double health, double offense, double defense, String type, int invSize, String moves) {
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

	public Position getPosition(){
		return position;
	}

	public int getId() {
		return id;
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

	@Override
	public String toString() {
		return String.format("%o, %o, %.1f, %.1f, %.1f, %o, %s, %o, %o", id, age, health, offense, defense, numWeapons, type, position.getY(), position.getX());
	}
}