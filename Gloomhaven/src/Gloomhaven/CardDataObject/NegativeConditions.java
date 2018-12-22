package Gloomhaven.CardDataObject;

import java.util.ArrayList;
import java.util.List;

public class NegativeConditions {
	private Target target = new Target();
	
	private String flag="None";
	
	private boolean curse=false;
	private boolean disarm=false;
	private boolean immobilize=false;
	private boolean wound=false;
	private boolean muddle=false;
	private boolean poison=false;
	private boolean stun=false;
	
	private int disarmCount=0;
	private int immobilizeCount=0;
	private int muddleCount=0;
	private int stunCount=0;
	
	public NegativeConditions() {
		
	}
	
	public NegativeConditions(String flag) {
		
		this.flag=flag;
		
		setNegativeConditions(flag);
	}
	
	public NegativeConditions(List<String> flags) {
		
		this.flag="Multiple";
		
		for(int i=0; i<flags.size(); i++) {
			setNegativeConditions(flags.get(i));
		}
	}

	public void setNegativeConditions(String flag) {
		switch(flag) {
			case "Curse":
				curse=true;
				break;
			case "Disarm":
				disarm=true;
				disarmCount=1;
				break;
			case "Immobilize":
				immobilize=true;
				immobilizeCount=1;
				break;
			case "Wound":
				wound=true;
				break;
			case "Muddle":
				muddle=true;
				muddleCount=1;
				break;
			case "Poison":
				poison=true;
				break;
			case "Stun":
				stun=true;
				stunCount=1;
				break;
			default:
				System.out.println("Error: NegativeConditions");
				System.exit(0);
		}
	}
	
	public void setNegativeConditions(NegativeConditions card) {
		if(card.isCurse())
			curse=true;
		
		if(card.isPoison())
			poison=true;
		
		if(card.isWound())
			wound=true;
		
		if(card.isDisarm()) {
			disarm=true;
			disarmCount=1;
		}
		
		if(card.isImmobilize()) {
			immobilize=true;
			immobilizeCount=1;
		}
		
		if(card.isMuddle()) {
			muddle=true;
			muddleCount=1;
		}
		
		if(card.isStun()) {
			stun=true;
			stunCount=1;
		}
	}
	
	//Getters and Setters
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public String getFlag() {return flag;}
	public void setFlag(String flag) {this.flag = flag;}
	public boolean isCurse() {return curse;}
	public void setCurse(boolean curse) {this.curse = curse;}
	public boolean isDisarm() {return disarm;}
	public void setDisarm(boolean disarm) {this.disarm = disarm;}
	public boolean isImmobilize() {return immobilize;}
	public void setImmobilize(boolean immobilize) {this.immobilize = immobilize;}
	public boolean isWound() {return wound;}
	public void setWound(boolean wound) {this.wound = wound;}
	public boolean isMuddle() {return muddle;}
	public void setMuddle(boolean muddle) {this.muddle = muddle;}
	public boolean isPoison() {return poison;}
	public void setPoison(boolean poison) {this.poison = poison;}
	public boolean isStun() {return stun;}
	public void setStun(boolean stun) {this.stun = stun;}
	public int getDisarmCount() {return disarmCount;}
	public int getImmobilizeCount() {return immobilizeCount;}
	public int getMuddleCount() {return muddleCount;}
	public int getStunCount() {return stunCount;}
	
	
	public List<String> getList(){
		List<String> negativeConditionList = new ArrayList<String>();
		
		if(curse)
			negativeConditionList.add("Curse");
		
		if(disarm)
			negativeConditionList.add("Disarm");
		
		if(immobilize)
			negativeConditionList.add("Immobilize");
		
		if(wound)
			negativeConditionList.add("Wound");
		
		if(muddle)
			negativeConditionList.add("Muddle");
		
		if(poison)
			negativeConditionList.add("Poison");
		
		if(stun)
			negativeConditionList.add("Stun");
		
		return negativeConditionList;
	}
	
	public void increaseCount(String flag) {
		
		if(flag.equals("Disarm"))
			disarmCount++;
		
		if(flag.equals("Immobilize"))
			immobilizeCount++;
		
		if(flag.equals("Muddle"))
			muddleCount++;
		
		if(flag.equals("Stun"))
			stunCount++;
	}
}
