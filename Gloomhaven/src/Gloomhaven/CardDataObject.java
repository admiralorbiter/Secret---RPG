package Gloomhaven;

public class CardDataObject {
	String text;
	String name;
	int initiative;
	int move;
	int attack;
	int range;
	int experience;
	int heal;
	
	int lootRange;
	
	//Effects that change the in play or out of play
	boolean lost;
	boolean continuous;
	boolean addNegativesToAttack;
	
	//Element Infusions
	boolean darkInfusion;
	boolean lightInfusion;
	boolean fireInfusion;
	boolean iceInfusion;
	boolean airInfusion;
	boolean earthInfusion;
	
	//Conditions it causes
	boolean invisible;
	boolean bless;
	boolean strengthen;
	boolean jump;
	
	//Negative conditions it causes
	boolean wound;
	boolean curse;
	boolean disarm;
	boolean immoblize;
	boolean muddle;
	boolean poison;
	boolean stun;
	
	//Card effects
	int push;
	int pull;
	
	boolean augment;
	
	public CardDataObject() {
		text="If you are seeing this, it is a mistake";
		move=0;
		attack=0;
		range=0;
		experience=0;
		heal=0;
		lost=false;
		continuous=false;
		addNegativesToAttack=false;
		lootRange=0;
		
		darkInfusion=false;
		lightInfusion=false;
		fireInfusion=false;
		iceInfusion=false;
		earthInfusion=false;
		airInfusion=false;
		
		invisible=false;
		bless=false;
		strengthen=false;
		jump=false;
		
		wound=false;
		curse=false;
		disarm=false;
		immoblize=false;
		muddle=false;
		poison=false;
		stun=false;
		
		push=0;
		pull=0;
		
		augment=false;
	}
	
	public int getHeal() {return heal;}
	public String getText() {return text;}
	public String getName() {return name;}
	public int getInitiative(){return initiative;}
	public int getMove() {return move;}
	public int getAttack() {return attack;}
	public int getRange() {return range;}
	public boolean getLost() {return lost;}
	public boolean getContinuous() {return continuous;}
	public boolean getAddNegativeConditionsToAttack() {return addNegativesToAttack;}
	public int getExperience() {return experience;}
	public int getLootRange() {return lootRange;}
	public boolean getInvisible() {return invisible;}
	public boolean getBless() {return bless;}
	public boolean getStrengthen() {return strengthen;}
	public int getPush() {return push;}
	public int getPull() {return pull;}
	public boolean getAugment() {return augment;}
	public boolean getJump() {return jump;}
	public boolean getWound() {return wound;}
	public boolean getCurse() {return curse;}
	public boolean getDisarm() {return disarm;}
	public boolean getImmobilize() {return immoblize;}
	public boolean getMuddle() {return muddle;}
	public boolean getPoison() {return poison;}
	public boolean getStun() {return stun;}
	
	public boolean causesNegativeCondition() {
		if(wound)
			return true;
		if(curse)
			return true;
		if(disarm)
			return true;
		if(immoblize)
			return true;
		if(muddle)
			return true;
		if(poison)
			return true;
		if(stun)
			return true;
		
		return false;
	}
	
	public boolean infusion() {
		if(darkInfusion)
			return true;
		
		if(lightInfusion)
			return true;
		
		if(fireInfusion)
			return true;
		
		if(iceInfusion)
			return true;
		
		if(airInfusion)
			return true;
		
		if(earthInfusion)
			return true;

		return false;
	}
	
	public boolean positiveConditions() {
		if(invisible)
			return true;
		if(bless)
			return true;
		if(strengthen)
			return true;
		
		return false;
	}
}
