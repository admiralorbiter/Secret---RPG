package Gloomhaven;

public class Trigger {
	private Target target = new Target();
	
	private String triggerFlag="None";
	private String effectFlag="None";
	
	private boolean triggerForEachNegativeConditionsOnTarget=false;
	private boolean triggerForEachTargeted=false;
	
	private SimpleCardData bonusData = new SimpleCardData();
	
	public Trigger() {
		
	}
	
	public Trigger(String triggerFlag) {
		setFlags(triggerFlag, "");
	}
	
	public Trigger(String triggerFlag, SimpleCardData bonusData) {
		this.bonusData=bonusData;
		setFlags(triggerFlag, "BonusData");
	}
	
	public void setFlags(String triggerFlag, String effectFlag) {
		
		this.triggerFlag=triggerFlag;
		this.effectFlag=effectFlag;
		
		switch(triggerFlag) {
		case "ForEachNegativeCondition":
			triggerForEachNegativeConditionsOnTarget=true;
			break;
		case "ForEachTargeted":
			triggerForEachTargeted=true;
			break;
		}
	}

	//Getters and Setters
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public String getTriggerFlag() {return triggerFlag;}
	public void setTriggerFlag(String triggerFlag) {this.triggerFlag = triggerFlag;}
	public String getEffectFlag() {return effectFlag;}
	public void setEffectFlag(String effectFlag) {this.effectFlag = effectFlag;}
	public boolean isTriggerForEachNegativeConditionsOnTarget() {return triggerForEachNegativeConditionsOnTarget;}
	public boolean isTriggerForEachTargeted() {return triggerForEachTargeted;}
	public SimpleCardData getBonusData() {return bonusData;}
	public void setBonusData(SimpleCardData bonusData) {this.bonusData = bonusData;	}
	
	
}
