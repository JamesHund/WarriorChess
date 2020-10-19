import java.util.ArrayList;
import java.util.Locale;

public class Warrior_24129429 {
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
	private final int id, maxInvSize;

	private int age;
	private double health, offense, defense;
	private Position_24129429 position;
	private final String type, moves;

	private final Weapon_24129429[] weapons;
	private int weaponCounter = 0;
	private int moveIndex = 0;
	private double maxDefense = MAX_DEFENSE_ZERO;
	private boolean alive = true;

	//special ability field
	private final int specialAbilityTotalCount; //total iterations for which ability is performed
	private int specialAbilityCount; //iterations of special ability to be performed
	private boolean specialAbilityBeingPerformed = false;
	private boolean specialAbilityPerformed = false;

	//potion related fields
	private boolean isInTrance = false;
	private int invisibleIterations = 0; //remaining iterations of effect

	//peacemaker fieds
	private boolean isImmune = false;

	//buffer fields
	private double bufferHealth, bufferOffense, bufferDefense;
	private int bufferWeaponCounter;


	public Warrior_24129429(Position_24129429 position, int id, int age, double health, double offense, double defense, String type, int specialAbilityTotalCount, int maxInvSize, String moves) {
		this.position = position;
		this.id = id;
		this.age = age;
		this.health = health;
		this.offense = offense;
		this.defense = defense;
		this.type = type;
		this.specialAbilityTotalCount = specialAbilityTotalCount;
		this.maxInvSize = maxInvSize;
		this.moves = moves;

		bufferHealth = this.health;
		bufferDefense = this.defense;
		bufferOffense = this.offense;
		bufferWeaponCounter = this.weaponCounter;

		weapons = new Weapon_24129429[maxInvSize];

		specialAbilityCount = specialAbilityTotalCount;

		if(bufferHealth <= SPECIAL_ABILITY_THRESHOLD && !specialAbilityPerformed) {
			queueSpecialAbility();
		}
	}

	//----------------ACCESSORS---------------------
	public Position_24129429 getPosition(){ return position; }

	public int getId() { return id; }

	public double getOffense() {
		return offense;
	}

	public double getDefense() { return defense; }

	public String getType() { return type; }

	public int getSpecialAbilityCount() { return specialAbilityCount; }

	public int getSpecialAbilityTotalCount() { return specialAbilityTotalCount; }

	public boolean isSpecialAbilityBeingPerformed(){ return specialAbilityBeingPerformed; }

	public boolean isAlive() { return alive; }

	public int getNumWeapons() { return Math.min(weaponCounter, maxInvSize);}

	public boolean isInvisible(){ return invisibleIterations > 0; }

	public boolean isImmune(){return isImmune;}

	//----------------MODIFIERS---------------------

	//add weapon to player inventory
	//if inventory is full returns the weapon to be dropped
	//if no weapon is to be dropped return null
	public Weapon_24129429 pickupWeapon(Weapon_24129429 weapon){
		Weapon_24129429 weaponToBeDropped = weapons[weaponCounter%maxInvSize];
		weapons[weaponCounter%maxInvSize] = weapon;
		bufferWeaponCounter++;
		adjustBufferOffense(weapon.getOffense());
		if(bufferWeaponCounter>maxInvSize){
			adjustBufferOffense(-weaponToBeDropped.getOffense());
			return weaponToBeDropped;
		}
		return null;
	}

	//returns whether they are alive or not
	public void adjustBufferHealth(double value){
		if(!isImmune()) {
			bufferHealth += value;
			if (bufferHealth <= 0) {
				alive = false;
			} else if (bufferHealth <= SPECIAL_ABILITY_THRESHOLD && !specialAbilityPerformed) {
				queueSpecialAbility();
			} else if (bufferHealth > MAX_HEALTH) {
				bufferHealth = MAX_HEALTH;
			}
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

	//used for setting defense in child classes performing special abilities
	//defence is not limited by age only by base maximum
	//not added to WarriorTypeInterface to prevent access from main class
	//protected so that warrior subtypes can access
	protected void setBufferDefenseSpecial(double value){
		bufferDefense = value;
		if (bufferDefense > 100){
			bufferDefense = 100;
		}
		if (bufferDefense < 0){
			bufferDefense = 0;
		}
	}

	//makes the warrior invincible for the current iteration
	//in the context of the whole application, this is used with peacemakers
	public void setImmune(){
		isImmune = true;
	}

	//updates values - to be called at the end of an iteration
	public void updateValues() {
		offense = bufferOffense;
		defense = bufferDefense;
		health = bufferHealth;
		weaponCounter = bufferWeaponCounter;
		invisibleIterations--;
		isImmune = false;
	}

	public void move(){
		char currentMove = moves.charAt(moveIndex%moves.length());
		Position_24129429 offset;
		switch(currentMove){
			case 'd':
				offset = new Position_24129429(1,0);
				break;
			case 'a':
				offset = new Position_24129429(-1,0);
				break;
			case 'w':
				offset = new Position_24129429(0,-1);
				break;
			case 'x':
				offset = new Position_24129429(0,1);
				break;
			case 'e':
				offset = new Position_24129429(1,-1);
				break;
			case 'q':
				offset = new Position_24129429(-1,-1);
				break;
			case 'c':
				offset = new Position_24129429(1,1);
				break;
			case 'z':
				offset = new Position_24129429(-1,1);
				break;
			default: //case 'n'
				offset = new Position_24129429(0,0);
				break;
		}
		if(isInTrance){
			int x = offset.getX()*-1;
			int y = offset.getY()*-1;
			offset = new Position_24129429(x,y);
		}
		//System.out.println("warrior moving" + offset.toString());
		position = position.add(offset);
		moveIndex++;
	}

	public void incrementAge() {
		age++;
		if(!((type.equals("Flame")||(type.equals("Stone"))) && specialAbilityBeingPerformed)) {
			setMaxDefense();
			adjustBufferDefense(0);
		}
	}

	public void decrementSpecialAbilityCount() {
		specialAbilityCount--;
		if(specialAbilityCount == -1){
			specialAbilityBeingPerformed = false;
		}
	}

	//all potions in neighbourhood should be input
	public void consumePotions(ArrayList<Potion_24129429> potions){

		boolean tranceCausingPresent = false;
		boolean tranceHealingPresent = false;
		int largestInvisibilityDuration = 0;


		for(Potion_24129429 potion : potions){
			switch(potion.getType()){
				case TRANCE_CAUSING:
					tranceCausingPresent = true;
					break;
				case TRANCE_HEALING:
					tranceHealingPresent = true;
					break;
				case INVISIBILITY:
					InvisibilityPotion temp = (InvisibilityPotion)potion;
					largestInvisibilityDuration = Math.max(largestInvisibilityDuration, temp.getIterations());
					//System.out.printf("%s Warrior %s is now invisble for %s iterations (temp = %s)\n", type, id, largestInvisibilityDuration, temp.getIterations());
					break;
			}
		}


		//possibly use buffer variables and update in updateValues()

		if(largestInvisibilityDuration > 0){
			invisibleIterations = largestInvisibilityDuration;
		}

		if(tranceHealingPresent){
			isInTrance = false;
		}else{
			if(tranceCausingPresent){
				isInTrance = true;
			}
		}




	}

	private void queueSpecialAbility() {
		specialAbilityCount = specialAbilityTotalCount;
		specialAbilityBeingPerformed = true;
		specialAbilityPerformed = true;
	}

	//sets maximum defense based on the warrior's age
	private void setMaxDefense(){
		if(age > AGE_STAGE_THREE){
			maxDefense = MAX_DEFENSE_THREE;
		}else if(age > AGE_STAGE_TWO){
			maxDefense = MAX_DEFENSE_TWO;
		}else if(age > AGE_STAGE_ONE){
			maxDefense = MAX_DEFENSE_ONE;
		}else{
			maxDefense = MAX_DEFENSE_ZERO;
		}
	}

	@Override
	public String toString() {
		//Locale set to english so that user locales dont affect output eg. (80.0 as opposed to 80,0)
		return String.format(Locale.ENGLISH, "%s, %s, %s, %s, %s, %s, %s, %s, %s", id, age, health, offense, defense, getNumWeapons(), type, position.getY(), position.getX());
	}
}