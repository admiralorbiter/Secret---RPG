package Gloomhaven;

public class CardDataObject {
	String text;
	String name;
	int initiative;
	int move;
	int attack;
	int range;
	int experience;
	
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
	
	public CardDataObject() {
		text="If you are seeing this, it is a mistake";
		move=0;
		attack=0;
		range=0;
		experience=0;
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
	}
	
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
}
