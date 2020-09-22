public class Warrior{
	//constants
	private static final int AGE_STAGE_ONE = 15;
	private static final int AGE_STAGE_TWO = 25;
	private static final int AGE_STAGE_THREE = 50;

	private static final double MAX_DEFENSE_ZERO = 100;
	private static final double MAX_DEFENSE_ONE = 70;
	private static final double MAX_DEFENSE_TWO = 50;
	private static final double MAX_DEFENSE_THREE = 30;

	private static final double MAX_OFFENSE = 100;
	private static final double MAX_HEALTH = 100;

	private static final double SPECIAL_ABILITY_THRESHOLD = 10;



	//instance fields
	private int id,age, invSize;
	private double health, offense, defense;
	private Position position;
	private String type, moves;
	private int numWeapons = 0;
	private int moveIndex = 0;
	private double maxDefense = MAX_DEFENSE_ZERO;
	private boolean alive = true;

	//special ability field
	private int specialAbilityTotalCount;
	private int specialAbilityCount;
	private boolean specialAbilityBeingPerformed = false;
	private boolean specialAbilityPerformed = false;

	//buffer fields
	private double bufferHealth, bufferOffense, bufferDefense;



	public Warrior(Position position, int id, int age, double health, double offense, double defense, String type, int specialAbilityTotalCount, int invSize, String moves) {
		this.position = position;
		this.id = id;
		this.age = age;
		this.health = health;
		this.offense = offense;
		this.defense = defense;
		this.type = type;
		this.specialAbilityTotalCount = specialAbilityTotalCount;
		this.invSize = invSize;
		this.moves = moves;

		bufferHealth = this.health;
		bufferDefense = this.defense;
		bufferOffense = this.offense;

		specialAbilityCount = specialAbilityTotalCount;
	}

	//----------------ACCESSORS---------------------
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

	public int getSpecialAbilityCount() { return specialAbilityCount; }

	public int getSpecialAbilityTotalCount() { return specialAbilityTotalCount; }

	public boolean isSpecialAbilityBeingPerformed(){ return specialAbilityBeingPerformed; }

	public boolean isAlive() { return alive; }

	//----------------MODIFIERS---------------------

	//returns whether they are alive or not
	public void adjustBufferHealth(double value){
		bufferHealth += value;
		if(bufferHealth <= 0){
			alive = false;
		}else if(bufferHealth <= SPECIAL_ABILITY_THRESHOLD && !specialAbilityPerformed){
			queueSpecialAbility();
		}else if(bufferHealth > MAX_HEALTH){
			bufferHealth = MAX_HEALTH;
		}
	}

	public void adjustBufferOffense(double value){
		bufferOffense += value;
		if (bufferOffense > MAX_OFFENSE){
			bufferOffense = MAX_OFFENSE;
		}
		if (bufferOffense < 0){
			bufferOffense = 0;
		}
	}
	public void adjustBufferDefense(double value){
		bufferDefense += value;
		if (bufferDefense > maxDefense){
			bufferDefense = maxDefense;
		}
		if (bufferDefense < 0){
			bufferDefense = 0;
		}
	}

	public void setBufferDefense(double value){
		bufferDefense = value;
		if (bufferDefense > maxDefense){
			bufferDefense = maxDefense;
		}
		if (bufferDefense < 0){
			bufferDefense = 0;
		}
	}

	//used for setting offense in child classes
	//not added to WarriorTypeInterface to prevent access from main class
	void setBufferOffenseSpecial(double value){
		bufferOffense = value;
		if (bufferOffense > MAX_OFFENSE){
			bufferOffense = MAX_OFFENSE;
		}
		if (bufferOffense < 0){
			bufferOffense = 0;
		}

	}

	//defence is not capped by age
	//used for setting defense in child classes
	//not added to WarriorTypeInterface to prevent access from main class
	void setBufferDefenseSpecial(double value){
		bufferDefense = value;
		if (bufferDefense > 100){
			bufferDefense = 100;
		}
		if (bufferDefense < 0){
			bufferDefense = 0;
		}
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

	private void queueSpecialAbility() {
		specialAbilityCount = specialAbilityTotalCount;
		specialAbilityBeingPerformed = true;
		specialAbilityPerformed = true;
	}

	public void decrementSpecialAbilityCount() {
		specialAbilityCount--;
		if(specialAbilityCount == -1){
			specialAbilityBeingPerformed = false;
		}
	}

	public void incrementAge() {
		age++;
		setMaxDefense();
		adjustBufferDefense(0);
	}

	private void setMaxDefense(){
		if(age >= AGE_STAGE_THREE){
			maxDefense = MAX_DEFENSE_THREE;
		}else if(age >= AGE_STAGE_TWO){
			maxDefense = MAX_DEFENSE_TWO;
		}else if(age >= AGE_STAGE_ONE){
			maxDefense = MAX_DEFENSE_ONE;
		}else{
			maxDefense = MAX_DEFENSE_ZERO;
		}
	}


	@Override
	public String toString() {
		return String.format("%s, %s, %.1f, %.1f, %.1f, %o, %s, %s, %s", id, age, health, offense, defense, numWeapons, type, position.getY(), position.getX());
	}
}