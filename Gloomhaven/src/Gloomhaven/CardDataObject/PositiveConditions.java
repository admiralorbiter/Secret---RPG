package Gloomhaven.CardDataObject;

import java.util.List;

public class PositiveConditions {
	private Target target = new Target("Self");
	
	private String flag="None";
	
	private boolean bless=false;
	private boolean invisibility=false;
	private boolean strengthen=false;

	private int invisibilityCount=0;
	private int strengthenCount=0;
	
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
	
	public void setPositiveConditions(PositiveConditions card) {
		
		if(card.isInvisibility()) {
			invisibility=true;
			invisibilityCount=1;
		}
		
		if(card.isStrengthen()) {
			strengthen=true;
			strengthenCount=1;
		}
	}
	
	public void setPositiveConditions(String flag) {
		switch(flag) {
			case "Bless":
				bless=true;
				break;
			case "Invisibility":
				invisibility=true;
				invisibilityCount=1;
				break;
			case "Strengthen":
				strengthen=true;
				strengthenCount=1;
				break;
			default:
				System.out.println("Error: PositiveConditions");
				System.exit(0);
				
		}
	}
	
	public void increaseCount(String flag) {
		
		if(flag.equals("Invisibility"))
			invisibilityCount++;
		
		if(flag.equals("Strengthen"))
			strengthenCount++;
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
	public int getInvisibilityCount() {return invisibilityCount;}
	public int getStrengthenCount() {return strengthenCount;}
}
