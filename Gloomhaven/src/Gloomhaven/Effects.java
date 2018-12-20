package Gloomhaven;

import java.util.List;

public class Effects {
	private Target target = new Target();
	
	private String flag="None";
	
	private boolean pierce=false;
	private boolean push=false;
	private boolean pull=false;
	private boolean heal=false;
	private boolean loot=false;
	
	int range=0;
	
	public Effects() {
		
	}
	
	public Effects(String flag) {
		
		this.flag=flag;
		
		switch(flag) {
			case "Pierce":
				pierce=true;
				break;
			case "Push":
				push=true;
				break;
			case "Pull":
				pull=true;
				break;
			case "Heal":
				heal=true;
				break;
			case "Loot":
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

	//Getters and Setters
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public String getFlag() {return flag;}
	public void setFlag(String flag) {this.flag = flag;}
	public boolean isPierce() {return pierce;}
	public void setPierce(boolean pierce) {this.pierce = pierce;}
	public boolean isPush() {return push;}
	public void setPush(boolean push) {this.push = push;}
	public boolean isPull() {return pull;}
	public void setPull(boolean pull) {this.pull = pull;}
	public boolean isHeal() {return heal;}
	public void setHeal(boolean heal) {this.heal = heal;}
	public boolean isLoot() {return loot;}
	public void setLoot(boolean loot) {this.loot = loot;}
	public int getRange() {return range;}
	public void setRange(int range) {this.range=range;}
	
}
