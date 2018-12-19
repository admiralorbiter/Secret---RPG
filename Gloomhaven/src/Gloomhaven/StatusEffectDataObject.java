package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectDataObject{
	
	//conditions and effects
	
	//Positive
	private boolean bless=false;
	private boolean invisibility=false;
	private boolean strengthen=false;
	//Negative
	private boolean curse=false;
	private boolean disarm=false;
	private boolean immobilize=false;
	private boolean wound=false;
	private boolean muddle=false;
	private boolean poison=false;
	private boolean stun=false;
	//Attack Effects
	private boolean addTarget=false;								//Might need to make this an int if a person can do multiple add targets
	private int push=0;
	private int pull=0;
	private int pierce=0;
	
	public StatusEffectDataObject() {
		
	}
	
	public boolean getBless() {return bless;}
	public boolean getCurse() {return curse;}
	public boolean getDisarm() {return disarm;}
	public boolean getImmobilize() {return immobilize;}
	public boolean getWound() {return wound;}
	public boolean getMuddle() {return muddle;}
	public boolean getPoison() {return poison;}
	public boolean getInvisibility() {return invisibility;}
	public boolean getStun() {return stun;}
	public boolean getStrengthen() {return strengthen;}
	public boolean getAddTarget() {return addTarget;}
	public int getPush() {return push;}
	public int getPull() {return pull;}
	public int getPierce() {return pierce;}
	
	public void setWound(boolean wound) {this.wound=wound;}
	public void setCurse(boolean curse) {this.curse=curse;}
	public void setDisarm(boolean disarm) {this.disarm=disarm;}
	public void setImmobilize(boolean immobilize) {this.immobilize=immobilize;}
	public void setMuddle(boolean muddle) {this.muddle=muddle;}
	public void setPoison(boolean poison) {this.poison=poison;}
	public void setStun(boolean stun) {this.stun=stun;}
	
	
	
	
	public void switchBless() {bless=!bless;}
	public void switchCurse() {curse=!curse;}
	public void switchDisarm() {disarm=!disarm;}
	public void switchImmobilize() {immobilize=!immobilize;}
	public void switchWound() {wound=!wound;}
	public void switchMuddle() {muddle=!muddle;}
	public void switchPoison() {poison=!poison;}
	public void switchInvisibility() {invisibility=!invisibility;}
	public void switchStun() {stun=!stun;}
	public void switchStrengthen() {strengthen=!strengthen;}
	public void switchAddTarget() {addTarget=!addTarget;}
	
	public void resetPush() {push=0;}
	public void resetPull() {pull=0;}
	public void resetPierce() {pierce=0;}
	
	public List<String> getNegativeConditions(){
		List<String> list = new ArrayList<String>();

		if(curse)
			list.add("Curse");
		
		if(disarm)
			list.add("Disarm");
		
		if(immobilize)
			list.add("Immoiblize");
		
		if(wound)
			list.add("Wound");
		
		if(muddle)
			list.add("Muddle");
		
		if(poison)
			list.add("Poison");
		
		if(stun)
			list.add("Stun");
		
		return list;
	}
	
	public boolean causesNegativeCondition() {
		if(wound)
			return true;
		if(curse)
			return true;
		if(disarm)
			return true;
		if(immobilize)
			return true;
		if(muddle)
			return true;
		if(poison)
			return true;
		if(stun)
			return true;
		
		return false;
	}
	
	public String getNegativeCondition() {
		if(wound)
			return "Wound";
		if(curse)
			return "Curse";
		if(disarm)
			return "Disarm";
		if(immobilize)
			return "Immobilize";
		if(muddle)
			return "Muddle";
		if(poison)
			return "Poison";
		if(stun)
			return "Stun";
		
		return "";
	}
	
	//Retrieves the number of negative conditions on enemy
	public int retrieveNegativeConditionCount() {
		int count=0;
		
		if(poison)
			count++;
		
		if(wound)
			count++;
		
		if(immobilize)
			count++;
		
		if(disarm)
			count++;
		
		if(stun)
			count++;
		
		if(muddle)
			count++;
		
		if(curse)
			count++;
		
		return count;	
	}
	
	public void setNegativeCondition(StatusEffectDataObject card) {
		if(card.getWound())
			wound=true;
		if(card.getCurse())
			curse=true;
		if(card.getDisarm())
			disarm=true;
		if(card.getImmobilize())
			immobilize=true;
		if(card.getMuddle())
			muddle=true;
		if(card.getPoison())
			poison=true;
		if(card.getStun())
			stun=true;
	}
	
	public void setNegativeCondition(String effect) {
		if(effect.equals("Wound"))
			wound=true;
		if(effect.equals("Curse"))
			curse=true;
		if(effect.equals("Disarm"))
			disarm=true;
		if(effect.equals("Immobilize"))
			immobilize=true;
		if(effect.equals("Muddle"))
			muddle=true;
		if(effect.equals("Poison"))
			poison=true;
		if(effect.equals("Stun"))
			stun=true;
	}

	public void resetBonusNegativeConditions() {
		wound=false;
		curse=false;
		disarm=false;
		immobilize=false;
		muddle=false;
		poison=false;
		stun=false;
	}
	
	public void setPositiveCondition(CardDataObject card) {
		if(card.invisible)
			invisibility=true;
		if(card.bless)
			bless=true;
		if(card.strengthen)
			strengthen=true;
	}
}
