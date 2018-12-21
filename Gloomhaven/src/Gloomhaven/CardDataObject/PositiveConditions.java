package Gloomhaven.CardDataObject;

import java.util.List;

public class PositiveConditions {
	private Target target = new Target("Self");
	
	private String flag="None";
	
	private boolean bless=false;
	private boolean invisibility=false;
	private boolean strengthen=false;
	
	public PositiveConditions() {
		
	}
	
	public PositiveConditions(String flag) {
		this.flag=flag;
		
		setPositiveConditions(flag);
	}
	
	public PositiveConditions(List<String> flags) {
		this.flag="Multiple";
		
		for(int i=0; i<flags.size(); i++) {
			setPositiveConditions(flags.get(i));
		}
	}
	
	public void setPositiveConditions(String flag) {
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
			default:
				System.out.println("Error: PositiveConditions");
				System.exit(0);
				
		}
	}

	//Getters and Setters
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public String getFlag() {return flag;}
	public void setFlag(String flag) {this.flag = flag;}
	public boolean isBless() {return bless;}
	public void setBless(boolean bless) {this.bless = bless;}
	public boolean isInvisibility() {return invisibility;}
	public void setInvisibility(boolean invisibility) {this.invisibility = invisibility;}
	public boolean isStrengthen() {return strengthen;}
	public void setStrengthen(boolean strengthen) {this.strengthen = strengthen;}
}
