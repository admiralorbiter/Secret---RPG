package Gloomhaven.Characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.AttackModifierDeck;
import Gloomhaven.CharacterDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.PositiveConditions;

public class character {
	
	String id;
	String classID;
	String name;
	Point coordinates = new Point();
	AttackModifierDeck attackModifierDeck = new AttackModifierDeck("Standard");
	NegativeConditions negativeConditions = new NegativeConditions();
	PositiveConditions positiveConditions = new PositiveConditions();
	Effects effects = new Effects();
	List<Counter> triggers = new ArrayList<Counter>();
	
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
	
	
	public boolean hasRetaliate() {
		if(effects.getRetaliate()>0)
			return true;
		
		return false;
	}
	
	public void heal(int damageToHeal) {
		if(negativeConditions.isPoison()) {
			negativeConditions.setPoison(false);
		}else {
			data.setHealth(data.getHealth()+damageToHeal);
			if(data.getHealth()>data.getMaxHealth())
				data.setHealth(data.getMaxHealth());
		}
	}
	
	public boolean canAttack() {

		if(negativeConditions.isDisarm()) {
			return false;
		}
		else if(negativeConditions.isStun()) {
			return false;
		}
		else if(negativeConditions.isImmobilize()) {
			return false;
		}
		
		return true;
	}
	
	public boolean canMove() {
		if(negativeConditions.isStun())
			return false;
		if(negativeConditions.isImmobilize())
			return false;
		
		return true;
	}
	
	public void takeDamage(int damage) {

		if(triggers.size()>0) {
			for(int i=0; i<triggers.size(); i++) {
				if(triggers.get(i).isTriggerOnDamage() || triggers.get(i).isTriggerOnMeleeAttack()) {
					
					triggers.get(i).addToCounter();
				}
				
				if(triggers.get(i).isAtMaxCount())
					triggers.remove(i);
			}
		}
		
		if(negativeConditions.isPoison())
			damage=damage+1;
		
		if(damage>0)
			data.setHealth(data.getHealth()+data.getShield()-damage);
				
		//[Test]
		System.out.println(name+" was attacked for "+damage+" making thier health "+data.getHealth());
	}
	
}
