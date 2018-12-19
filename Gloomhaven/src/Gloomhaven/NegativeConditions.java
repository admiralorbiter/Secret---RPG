package Gloomhaven;

import java.util.List;

public class NegativeConditions {
	Target target = new Target();
	
	String flag="None";
	
	boolean curse=false;
	boolean disarm=false;
	boolean immobilize=false;
	boolean wound=false;
	boolean muddle=false;
	boolean poison=false;
	boolean stun=false;
	
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
}
