package Gloomhaven;

public class AttackModifierCard {
	int multiplier;
	int plusAttack;
	
	boolean shuffle;
	boolean rolling;
	boolean free;
	
	AttackModifierCard(){
		multiplier=1;
		plusAttack=0;
		
		shuffle=false;
		rolling=false;
		free=true;
	}
	
	AttackModifierCard(int multiplier, int plusAttack){
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
}
