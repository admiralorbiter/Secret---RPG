package Gloomhaven;

public class SimpleCards {
	int attack=0;
	int move=0;
	int range=0;
	int experience=0;
	int shield=0;
	
	String elemental="";
	boolean wound=false;
	public SimpleCards() {
		
	}
	
	public int getAttack() {return attack;}
	public int getMove() {return move;}
	public int getRange() {return range;}
	public int getExperience() {return experience;}
	public int getShield() {return shield;}
	public String getElementalConsumed() {return elemental;}
	public boolean getWound() {return wound;}
}
