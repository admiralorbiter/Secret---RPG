package Gloomhaven;

public class CardDataObject {
	String text;
	String name;
	int initiative;
	int move;
	int attack;
	
	//Effects that change the in play or out of play
	boolean lost;
	boolean continuous;
	
	public CardDataObject() {
		text="If you are seeing this, it is a mistake";
		move=0;
		attack=0;
		
		lost=false;
		continuous=false;
	}
}
