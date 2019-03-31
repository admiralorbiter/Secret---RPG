package Gloomhaven.Characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Gloomhaven.CharacterDataObject;
import Gloomhaven.AttackModifier.AttackModifierDeck;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.PositiveConditions;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.UtilitiesHex;

public class character {
	
	ImageIcon image=null;
	String id;
	String classID;
	String name;
	Point coordinates = new Point();
	AttackModifierDeck attackModifierDeck = new AttackModifierDeck("Standard");
	NegativeConditions negativeConditions = new NegativeConditions();
	PositiveConditions positiveConditions = new PositiveConditions();
	Effects effects = new Effects();
	List<Counter> counterTriggers = new ArrayList<Counter>();
	List<Counter> roundTriggers = new ArrayList<Counter>();
	
	CardDataObject roundBonus = new CardDataObject();
	
	protected CharacterDataObject data;

	public character() {
		
	}
	
	//GETTERS and SETTERS
	public ImageIcon getImageIcon() {return image;}
	public void setImage(ImageIcon image) {this.image=image;}
	public String getID() {return id;}
	public void setID(String id) {this.id=id;}
	public String getClassID() {return classID;}
	public void setClassID(String classID) {this.classID=classID;}
	public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	public Point getCoordinates() {return coordinates;}
	public HexCoordinate getCubeCoordiantes(boolean flatlayout) {
		if(flatlayout)
			return UtilitiesHex.flatOffsetToCube(1, coordinates);
		else
			return UtilitiesHex.pointyOffsetToCube(1, coordinates);
	}
	public void setCoordinates(Point point) {this.coordinates=new Point(point);}
	public AttackModifierDeck getAttackModDeck() {return attackModifierDeck;}
	public void setAttackModDeck(AttackModifierDeck deck) {this.attackModifierDeck=deck;}
	public CharacterDataObject getCharacterData() {return data;}
	public CardDataObject getRoundBonus() {return roundBonus;}
	public void setRoundBonus(CardDataObject card) {this.roundBonus=card;}
	public List<Counter> getCounterTriggers(){return counterTriggers;}
	public List<Counter> getRoundTriggers(){return roundTriggers;}
	public PositiveConditions getPositiveConditions() {return positiveConditions;}
	public NegativeConditions getNegativeConditions() {return negativeConditions;}
	
	public void addCounter(Counter trigger) {
		counterTriggers.add(trigger);
	}
	
	public void addRoundTrigger(Counter trigger) {
		roundTriggers.add(trigger);
	}
	
	public boolean hasRetaliate() {
		if(effects.getRetaliate()>0)
			return true;
		
		return false;
	}
	
	public void heal(int damageToHeal) {
		System.out.println("character.java Loc 72: Health before heal    "+damageToHeal+": "+data.getHealth());
		if(negativeConditions.isPoison()) {
			negativeConditions.setPoison(false);
		}else {
			data.setHealth(data.getHealth()+damageToHeal);
			if(data.getHealth()>data.getMaxHealth())
				data.setHealth(data.getMaxHealth());
		}
		System.out.println("character.java Loc 80: Health after heal: "+data.getHealth());
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

		if(counterTriggers.size()>0) {
			for(int i=0; i<counterTriggers.size(); i++) {
				if(counterTriggers.get(i).isTriggerOnDamage()) {
					if(counterTriggers.get(i).getEffectFlag().equals("NoDamage"))
						damage=0;
					
					counterTriggers.get(i).addToCounter();
				}
				
				if(counterTriggers.get(i).isAtMaxCount())
					counterTriggers.remove(i);
			}
		}
		
		if(negativeConditions.isPoison())
			damage=damage+1;
		
		
		if(damage>0)
			data.setHealth(data.getHealth()+data.getShield()-damage);
				
		//[Test]
		System.out.println(name+" was attacked for "+damage+" making thier health "+data.getHealth());
	}
	
	public void endOfRound() {
		if(negativeConditions.isDisarm()) {
			if(negativeConditions.getDisarmCount()==2)
				negativeConditions.setDisarm(false);
			else
				negativeConditions.increaseCount("Disarm");
		}
		
		if(negativeConditions.isStun()) {
			if(negativeConditions.getStunCount()==2)
				negativeConditions.setStun(false);
			else
				negativeConditions.increaseCount("Stun");
		}
		
		if(negativeConditions.isMuddle()) {
			if(negativeConditions.getMuddleCount()==2)
				negativeConditions.setMuddle(false);
			else
				negativeConditions.increaseCount("Muddle");
		}
		
		if(positiveConditions.isInvisibility()) {
			if(positiveConditions.getInvisibilityCount()==2)
				positiveConditions.setInvisibility(false);
			else
				positiveConditions.increaseCount("Invisibility");
		}
		
		if(positiveConditions.isStrengthen()) {
			if(positiveConditions.getStrengthenCount()==2)
				positiveConditions.setStrengthen(false);
			else
				positiveConditions.increaseCount("Strengthen");
		}
		
		if(negativeConditions.isImmobilize()) {
			if(negativeConditions.getImmobilizeCount()==2)
				negativeConditions.setImmobilize(false);
			else
				negativeConditions.increaseCount("Immobilize");
		}
		
		if(getRoundBonus()!=null)
			setRoundBonus(null);
		
		//If persistant card trigger is finished, removes from play and removes from trigger deck
		if(counterTriggers.size()>0) {
			for(int i=0; i<counterTriggers.size(); i++) {
				if(counterTriggers.get(i).isAtMaxCount()) {
					
					counterTriggers.remove(i);
				}
			}
		}
		
		roundTriggers = new ArrayList<Counter>();

	}
	
}
