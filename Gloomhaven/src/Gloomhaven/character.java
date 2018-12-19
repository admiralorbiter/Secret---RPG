package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class character {
	
	String id;
	String classID;
	String name;
	Point coordinates = new Point();
	AttackModifierDeck attackModifierDeck = new AttackModifierDeck("Standard");
	StatusEffectDataObject effects = new StatusEffectDataObject();                                  //Status Effects and Conditions of the Player
	CharacterDataObject data;
	SimpleCards retaliate = new SimpleCards();
	List<Trigger> triggers = new ArrayList<Trigger>();
	StatusEffectDataObject negativeBonusEffects = new StatusEffectDataObject();
	
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
	public void setRetaliate(SimpleCards retaliate) {this.retaliate=retaliate;}
	public SimpleCards getRetaliate() {return retaliate;}
	
	public boolean hasRetaliate() {
		if(retaliate.attack>0)
			return true;
		else
			return false;
	}
	
	public void heal(int damageToHeal) {
		if(effects.getPoison()) {
			effects.switchPoison();
		}else {
			data.setHealth(data.getHealth()+damageToHeal);
			if(data.getHealth()>data.getMaxHealth())
				data.setHealth(data.getMaxHealth());
		}
	}
	
	public boolean canAttack() {

		if(effects.getDisarm()) {
			return false;
		}
		else if(effects.getStun()) {
			return false;
		}
		else if(effects.getImmobilize()) {
			return false;
		}
		
		return true;
	}
	
	public boolean canMove() {
		if(effects.getStun())
			return false;
		if(effects.getImmobilize())
			return false;
		
		return true;
	}
	
	public void takeDamage(int damage) {
		boolean needToReset=false;
		if(triggers.size()>0) {
			for(int i=0; i<triggers.size(); i++) {
				if(triggers.get(i).getTriggerName()=="PlayerTarget") {
					data.setShield(data.getShield()+triggers.get(i).getShield());
					needToReset=true;
					triggers.get(i).addToTrigger();
				}
			}
		}
		if(effects.getPoison())
			damage=damage+1;
		
		if(damage>0)
			data.setHealth(data.getHealth()+data.getShield()-damage);
		
		if(needToReset) {
			for(int i=0; i<triggers.size(); i++) {
				if(triggers.get(i).getTriggerName()=="PlayerTarget") {
					data.setShield(data.getShield()+triggers.get(i).getShield());
				}
			}
		}
		
		//[Test]
		System.out.println(name+" was attacked for "+damage+" making thier health "+data.getHealth());
	}
	
	public void setNegativeCondition(String condition) {
		if(condition=="Wound" && effects.getWound()==false)
			effects.switchWound();
		
		if(condition=="Curse" && effects.getCurse()==false)
			effects.switchCurse();
		
		if(condition=="Disarm" && effects.getDisarm()==false)
			effects.switchDisarm();
		
		if(condition=="Immobilize" && effects.getImmobilize()==false)
			effects.switchImmobilize();
		
		if(condition=="Muddle" && effects.getMuddle()==false)
			effects.switchMuddle();
		
		if(condition=="Poison" && effects.getPoison()==false)
			effects.switchPoison();
		
		if(condition=="Stun" && effects.getStun()==false)
			effects.switchStun();
	}
	
	public void setCondition(String condition) {
		if(condition=="Invisible" && effects.getInvisibility()!=true)
			effects.switchInvisibility();
		
		if(condition=="Bless" && effects.getBless()!=true)
			effects.switchBless();
		
		if(condition=="Strengthen" && effects.getStrengthen()!=true)
			effects.switchStrengthen();
	}
	
	public void resolveRetaliate(character attacker) {
		attacker.takeDamage(getRetaliate().getAttack());
		getCharacterData().setXp(getCharacterData().getXp()+getRetaliate().getExperience());
	}
	
	public void setBonusNegativeConditions(String name) {
		negativeBonusEffects = new StatusEffectDataObject();
		if(name=="Wound")
			negativeBonusEffects.setWound(true);
		else if(name=="Curse")
			negativeBonusEffects.setCurse(true);
		else if(name=="Disarm")
			negativeBonusEffects.setDisarm(true);
		else if(name=="Immobilize")
			negativeBonusEffects.setImmobilize(true);
		else if(name=="Muddle")
			negativeBonusEffects.setMuddle(true);
		else if(name=="Poison")
			negativeBonusEffects.setPoison(true);
		else if(name=="Stun")
			negativeBonusEffects.setStun(true);
	}
	
	public void resetBonusNegativeConditions() {
		negativeBonusEffects=null;
	}
	
	public StatusEffectDataObject getBonusNegativeConditions() {return negativeBonusEffects;}
}
