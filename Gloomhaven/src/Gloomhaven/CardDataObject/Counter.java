package Gloomhaven.CardDataObject;

import java.util.ArrayList;
import java.util.List;

public class Counter {
	Target target = new Target("Self");
	
	String triggerFlag="None";
	String effectFlag="None";
	
	
	int maxCounters=0;
	int currentCount=0;
	
	boolean triggerOnDamage=false;
	boolean triggerOnAttack=false;
	boolean triggerOnMeleeAttack=false;
	
	boolean noDamage=false;
	
	SimpleCardData bonusData = null;
	PositiveConditions positiveConditions=null;
	NegativeConditions negativeConditions=null;
	Effects effects =null;
	
	public Counter() {
		
	}
	
	public Counter(int max, String triggerFlag, String effectFlag) {
		this.triggerFlag=triggerFlag;
		this.effectFlag=effectFlag;
		this.maxCounters=max;
		
		setCounterFlags(triggerFlag, effectFlag, null, null, null, null);
	}
	
	public Counter(int max, String triggerFlag, SimpleCardData bonusData) {
		this.triggerFlag=triggerFlag;
		this.maxCounters=max;
		
		setCounterFlags(triggerFlag, "BonusData", bonusData, null, null, null);
	}
	
	public Counter(int max, String triggerFlag, PositiveConditions positiveConditions) {
		this.triggerFlag=triggerFlag;
		this.maxCounters=max;
		
		setCounterFlags(triggerFlag, "PositiveConditions", null, positiveConditions, null, null);
	}

	public Counter(int max, String triggerFlag, NegativeConditions negativeConditions) {
		this.triggerFlag=triggerFlag;
		this.maxCounters=max;
		
		setCounterFlags(triggerFlag, "NegativeConditions", null, null, negativeConditions, null);
	}
	
	public Counter(int max, String triggerFlag, Effects effects) {
		this.triggerFlag=triggerFlag;
		this.maxCounters=max;
		
		setCounterFlags(triggerFlag, "Effects", null, null, null, effects);
	}
	
	public List<String> getTriggerFlagList(){
		List<String> triggerFlagList = new ArrayList<String>();
		
		triggerFlagList.add("OnDamage");
		triggerFlagList.add("OnAttack");
		triggerFlagList.add("OnMeleeAttack");
		
		return triggerFlagList;
	}
	public void setCounterFlags(String triggerFlag, String effectFlag, SimpleCardData bonusData, PositiveConditions positiveConditions, NegativeConditions negativeConditions, Effects effects) {
		switch(triggerFlag) {
			case "OnDamage":
				triggerOnDamage=true;
				break;
			case "OnAttack":
				triggerOnAttack=true;
				break;
			case "OnMeleeAttack":
				triggerOnAttack=true;
			default:
				triggerOnAttack=true;
				System.out.println("Error: Counter "+triggerFlag);
				//System.exit(0);
		}
		
		switch(effectFlag) {
			case "NoDamage":
				noDamage=true;
				break;
		}
		
		if(bonusData!=null) {
			effectFlag="BonusData";
			this.bonusData=bonusData;
		}
		
		if(positiveConditions!=null) {
			effectFlag="PositiveConditions";
			this.positiveConditions=positiveConditions;
		}
		
		if(negativeConditions!=null) {
			effectFlag="NegativeConditions";
			this.negativeConditions=negativeConditions;
		}
		
		if(effects!=null) {
			effectFlag="Effects";
			this.effects=effects;
		}
	}

	//Getters and Setters
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public String getTriggerFlag() {return triggerFlag;}
	public void setTriggerFlag(String triggerFlag) {this.triggerFlag = triggerFlag;}
	public String getEffectFlag() {return effectFlag;}
	public void setEffectFlag(String effectFlag) {this.effectFlag = effectFlag;}
	public int getMaxCounters() {return maxCounters;}
	public void setMaxCounters(int maxCounters) {this.maxCounters = maxCounters;}
	public int getCurrentCount() {return currentCount;}
	public void setCurrentCount(int currentCount) {this.currentCount = currentCount;}
	public boolean isTriggerOnDamage() {return triggerOnDamage;}
	public void setTriggerOnDamage(boolean triggerOnDamage) {this.triggerOnDamage = triggerOnDamage;}
	public boolean isTriggerOnAttack() {return triggerOnAttack;}
	public void setTriggerOnAttack(boolean triggerOnAttack) {this.triggerOnAttack = triggerOnAttack;}
	public boolean isTriggerOnMeleeAttack() {return triggerOnMeleeAttack;}
	public void setTriggerOnMeleeAttack(boolean triggerOnMeleeAttack) {this.triggerOnMeleeAttack = triggerOnMeleeAttack;}
	public boolean isNoDamage() {return noDamage;}
	public void setNoDamage(boolean noDamage) {this.noDamage = noDamage;}
	public SimpleCardData getBonusData() {return bonusData;}
	public void setBonusData(SimpleCardData bonusData) {this.bonusData = bonusData;}
	public PositiveConditions getPositiveConditions() {return positiveConditions;}
	public void setPositiveConditions(PositiveConditions positiveConditions) {this.positiveConditions = positiveConditions;}
	public NegativeConditions getNegativeConditions() {return negativeConditions;}
	public void setNegativeConditions(NegativeConditions negativeConditions) {this.negativeConditions = negativeConditions;}
	public Effects getEffects() {return effects;}
	public void setEffects(Effects effects) {this.effects=effects;}
	
	public void addToCounter() {
		currentCount++;
	}
	
	public boolean isAtMaxCount() {
		if(maxCounters==currentCount)
			return true;
		
		return false;
	}
}
