public class Warrior{
	private int id,age, invSize;
	private double health, offense, defense;
	private Position position;
	private String type, moves;
	private int numWeapons = 0;
	private int moveIndex = 0;

	//buffer fields
	private double bufferHealth, bufferOffense, bufferDefense;

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

		bufferHealth = this.health;
		bufferDefense = this.defense;
		bufferOffense = this.offense;
	}

	public Position getPosition(){
		return position;
	}

	public int getId() {
		return id;
	}

	public double getHealth() { return health; }

	public double getOffense() { return offense; }

	public double getDefense() { return defense; }

	public String getType() { return type; }

	//returns whether they are alive or not
	public boolean adjustBufferHealth(double value){
		bufferHealth += value;
		if(bufferHealth <= 0){
			return false;
		}
		return true;
	}

	public void setBufferOffense(double value){
		bufferOffense = value;
	}

	public void setBufferDefense(double value){
		bufferDefense = value;
	}

	public void updateValues() {
		offense = bufferOffense;
		defense = bufferDefense;
		health = bufferHealth;
	}

	public void move(){
		char currentMove = moves.charAt(moveIndex%moves.length());
		Position offset;
		switch(currentMove){
			case 'd':
				offset = new Position(1,0);
				break;
			case 'a':
				offset = new Position(-1,0);
				break;
			case 'w':
				offset = new Position(0,-1);
				break;
			case 'x':
				offset = new Position(0,1);
				break;
			case 'e':
				offset = new Position(1,-1);
				break;
			case 'q':
				offset = new Position(-1,-1);
				break;
			case 'c':
				offset = new Position(1,1);
				break;
			case 'z':
				offset = new Position(-1,1);
				break;
			default: //case 'n'
				offset = new Position(0,0);
				break;
		}
		//System.out.println("warrior moving" + offset.toString());
		position = position.add(offset);
		moveIndex++;
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
		age++;
		//System.out.println(type + "  age++  " + age);
	}

	public void setSpecialAbilityCount(int val) {
		// TODO Auto-generated method stub
		
	}

	public void decrementSpecialAbilityCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %.1f, %.1f, %.1f, %o, %s, %s, %s", id, age, health, offense, defense, numWeapons, type, position.getY(), position.getX());
	}
}