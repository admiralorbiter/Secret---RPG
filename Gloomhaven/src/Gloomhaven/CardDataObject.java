package Gloomhaven;

public class CardDataObject {
	int attack;
	int move;
	int experience;
	int loot;
	int push;
	int heal;
	int range;
	boolean continuous;
	boolean discard;
	boolean lost;
	String text;
	boolean augument;
	boolean summons;
	boolean statusEffect;
	boolean dependsOnTarget;
	boolean createsElement;
	boolean mindcontrol;
	boolean invisible;
	boolean jump;
	boolean complex;
	
	public CardDataObject() {
		attack=0;
		move=0;
		experience=0;
		push=0;
		heal=0;
		range=0;
		continuous=false;
		discard=true;
		lost=false;
		text="if you are seeing this, you are error";
		augument=false;
		summons=false;
		statusEffect=false;
		dependsOnTarget=false;
		loot=0;
		createsElement=false;
		mindcontrol=false;
		invisible=false;
		jump=false;
		complex=false;
	}
}
