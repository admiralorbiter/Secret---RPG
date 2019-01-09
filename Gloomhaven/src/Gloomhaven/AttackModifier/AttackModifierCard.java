package Gloomhaven.AttackModifier;

public class AttackModifierCard {
	int multiplier=1;
	int plusAttack=0;
	
	boolean shuffle=false;
	boolean rolling=false;
	boolean free=true;
	boolean bless=false;
	boolean curse=false;
	
	AttackModifierCard(){
	}
	
	public AttackModifierCard(String condition){
		if(condition.equals("Bless"))
			bless=true;
		else if(condition.equals("Curse"))
			curse=true;
		else
			System.out.println("Error with condition attack modifier card");
	}
	
	public AttackModifierCard(int multiplier, int plusAttack){
		this.multiplier=multiplier;
		this.plusAttack=plusAttack;
		
		shuffle=false;
		rolling=false;
		free=true;
	}
	
	AttackModifierCard(int multiplier, int plusAttack, boolean shuffle, boolean rolling){
		this.multiplier=multiplier;
		this.plusAttack=plusAttack;
		
		this.shuffle=shuffle;
		this.rolling=rolling;
		free=true;
	}
	public int getPlusAttack() {return plusAttack;}
	public int getMultiplier() {return multiplier;}
	public boolean cardFree() {return free;}
	public boolean getShuffle() {return shuffle;}
	public void setFree() {free=true;}
	public void discard() {free=false;}
	public boolean hasBless() {return bless;}
	public boolean hasCurse() {return curse;}
}
