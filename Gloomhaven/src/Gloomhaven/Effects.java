package Gloomhaven;

import java.util.List;

public class Effects {
	private Target target = new Target();
	
	private String flag="None";
	
	private int pierce=0;
	private int push=0;
	private int pull=0;
	private int heal=0;
	private int loot=0;
	private int retaliate=0;
	private int shield=0;
	
	private int range=0;
	
	boolean instaKill=false;
	
	public Effects() {
		
	}
	
	public Effects(String flag, int amount, int range) {
		
		this.flag=flag;
		this.range=range;
		
		switch(flag) {
			case "Pierce":
				pierce=amount;
				break;
			case "Push":
				push=amount;
				break;
			case "Pull":
				pull=amount;
				break;
			case "Heal":
				heal=amount;
				break;
			case "Loot":
				loot=amount;
				break;
			case "Retaliate":
				retaliate=amount;
				break;
			case "InstaKill":
				instaKill=true;
				break;
			case "Shield":
				shield=amount;
				break;
		}
	}
	
	public Effects(List<String> flags, int[] amounts, int range) {
		
		this.flag="Multiple";
		this.range=range;
		
		for(int i=0; i<flags.size(); i++) {
			switch(flags.get(i)) {
				case "Pierce":
					pierce=amounts[i];
					break;
				case "Push":
					push=amounts[i];
					break;
				case "Pull":
					pull=amounts[i];
					break;
				case "Heal":
					heal=amounts[i];
					break;
				case "Loot":
					loot=amounts[i];
					break;
				case "Retaliate":
					retaliate=amounts[i];
					break;
				case "InstaKill":
					instaKill=true;
					break;
				case "Shield":
					shield=amounts[i];
					break;
			}
		}
	}

	//Getters and Setters
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public String getFlag() {return flag;}
	public void setFlag(String flag) {this.flag = flag;}
	public int getPierce() {return pierce;}
	public void setPierce(int pierce) {this.pierce = pierce;}
	public int getPush() {return push;}
	public void setPush(int push) {this.push = push;}
	public int getPull() {return pull;}
	public void setPull(int pull) {this.pull = pull;}
	public int getHeal() {return heal;}
	public void setHeal(int heal) {this.heal = heal;}
	public int getLoot() {return loot;}
	public void setLoot(int loot) {this.loot = loot;}
	public int getRange() {return range;}
	public void setRange(int range) {this.range=range;}
	public int getRetaliate() {return retaliate;}
	public void setRetaliate(int retaliate) {this.retaliate=retaliate;}
	
}
