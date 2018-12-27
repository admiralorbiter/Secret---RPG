package Gloomhaven.CardDataObject;

public class Trigger {
	private Target target = new Target();
	private String triggerFlag="None";
	private String effectFlag="None";
	
	private boolean triggerOnAttack=false;
	
	SimpleCardData bonusData = new SimpleCardData();
	
	public Trigger(String triggerFlag) {
		setTriggerFlags(triggerFlag, "BonusData");
	}
	
	public void setTriggerFlags(String triggerFlag, String effectFlag) {
		this.triggerFlag=triggerFlag;
		this.effectFlag=effectFlag;
		
		switch(triggerFlag) {
			case "ForEachNegativeCondition":
				triggerOnAttack=true;
				break;
			case "ForEachTargeted":
				triggerOnAttack=true;
				break;
			case "AdjacentToAllies":
				triggerOnAttack=true;
				break;
		}
	}
	
	public SimpleCardData getBonusData() {return bonusData;}
}
