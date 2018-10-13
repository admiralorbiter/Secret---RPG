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
}
