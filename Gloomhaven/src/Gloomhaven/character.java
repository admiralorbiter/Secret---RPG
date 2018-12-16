package Gloomhaven;

import java.awt.Point;

public class character {
	
	String id;
	String classID;
	String name;
	Point coordinates = new Point();
	AttackModifierDeck attackModifierDeck = new AttackModifierDeck("Standard");
	StatusEffectDataObject effects = new StatusEffectDataObject();                                  //Status Effects and Conditions of the Player
	CharacterDataObject data;

	public character() {
		
	}
	
	//GETTERS and SETTERS
	public String getID() {return id;}
	public void setID(String id) {this.id=id;}
	public String getClassID() {return classID;}
	public void setClassID(String classID) {this.classID=classID;}
	public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	public Point getCoordinates() {return coordinates;}
	public void setCoordinates(Point point) {this.coordinates=new Point(point);}
	public AttackModifierDeck getAttackModDeck() {return attackModifierDeck;}
	public void setAttackModDeck(AttackModifierDeck deck) {this.attackModifierDeck=deck;}
	public CharacterDataObject getCharacterData() {return data;}
	public StatusEffectDataObject getStatusEffect() {return effects;}
	
	
	public void heal(int damageToHeal) {
		if(effects.getPoison()) {
			effects.switchPoison();
		}else {
			data.setHealth(data.getHealth()+damageToHeal);
			if(data.getHealth()>data.getMaxHealth())
				data.setHealth(data.getMaxHealth());
		}
	}
}
