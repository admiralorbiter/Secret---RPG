package Gloomhaven;

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
	
	public NegativeConditions() {
		
	}
	
	public NegativeConditions(String flag) {
		
		this.flag=flag;
		
		switch(flag) {
			case "Curse":
				curse=true;
				break;
			case "Disarm":
				disarm=true;
				break;
			case "Immobilize":
				immobilize=true;
				break;
			case "Wound":
				wound=true;
				break;
			case "Muddle":
				muddle=true;
				break;
			case "Poison":
				poison=true;
				break;
			case "Stun":
				stun=true;
				break;
		}
	}
	
	public NegativeConditions(List<String> flags) {
		
		this.flag="Multiple";
		
		for(int i=0; i<flags.size(); i++) {
			switch(flags.get(i)) {
				case "Curse":
					curse=true;
					break;
				case "Disarm":
					disarm=true;
					break;
				case "Immobilize":
					immobilize=true;
					break;
				case "Wound":
					wound=true;
					break;
				case "Muddle":
					muddle=true;
					break;
				case "Poison":
					poison=true;
					break;
				case "Stun":
					stun=true;
					break;
			}
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
}
