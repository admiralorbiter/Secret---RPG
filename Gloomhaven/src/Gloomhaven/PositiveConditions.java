package Gloomhaven;

import java.util.List;

public class PositiveConditions {
	Target target = new Target("Self");
	
	String flag="None";
	
	boolean bless=false;
	boolean invisibility=false;
	boolean strengthen=false;
	boolean retaliate=false;
	
	public PositiveConditions() {
		
	}
	
	public PositiveConditions(String flag) {
		this.flag=flag;
		
		switch(flag) {
		case "Bless":
			bless=true;
			break;
		case "Invisibility":
			invisibility=true;
			break;
		case "Strengthen":
			strengthen=true;
			break;
		case "Retaliate":
			retaliate=true;
			break;
		}
	}
	
	public PositiveConditions(List<String> flags) {
		this.flag="Multiple";
		
		for(int i=0; i<flags.size(); i++) {
			switch(flags.get(i)) {
			case "Bless":
				bless=true;
				break;
			case "Invisibility":
				invisibility=true;
				break;
			case "Strengthen":
				strengthen=true;
				break;
			case "Retaliate":
				retaliate=true;
				break;
			}
		}
	}
}
