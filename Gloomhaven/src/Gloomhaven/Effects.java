package Gloomhaven;

import java.util.List;

public class Effects {
	Target target = new Target();
	
	String flag="None";
	
	boolean pierce=false;
	boolean push=false;
	boolean pull=false;
	boolean heal=false;
	boolean loot=false;
	
	public Effects() {
		
	}
	
	public Effects(String flag) {
		
		this.flag=flag;
		
		switch(flag) {
			case "Pierce":
				pierce=true;
				break;
			case "push":
				push=true;
				break;
			case "pull":
				pull=true;
				break;
			case "heal":
				heal=true;
				break;
			case "loot":
				loot=true;
				break;
		}
	}
	
	public Effects(List<String> flags) {
		
		this.flag="Multiple";
		
		for(int i=0; i<flags.size(); i++) {
			switch(flags.get(i)) {
				case "Pierce":
					pierce=true;
					break;
				case "push":
					push=true;
					break;
				case "pull":
					pull=true;
					break;
				case "heal":
					heal=true;
					break;
				case "loot":
					loot=true;
					break;
			}
		}
	}

}
