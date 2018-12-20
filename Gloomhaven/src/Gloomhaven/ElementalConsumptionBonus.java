package Gloomhaven;

public class ElementalConsumptionBonus {
	private Target target = new Target();
	
	private SimpleCardData bonusData = new SimpleCardData();
	private PositiveConditions positiveConditions=null;
	private NegativeConditions negativeConditions=null;
	private Effects effects=null;
	
	public ElementalConsumptionBonus() {
		
	}
	
	//Getters and Setters
	public ElementalConsumptionBonus(SimpleCardData bonusData) {this.bonusData=bonusData;}
	public ElementalConsumptionBonus(PositiveConditions positiveConditions) {this.positiveConditions=positiveConditions;}
	public ElementalConsumptionBonus(NegativeConditions negativeConditions) {this.negativeConditions=negativeConditions;}
	public ElementalConsumptionBonus(Effects effects) {this.effects=effects;}

	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}
	public SimpleCardData getBonusData() {return bonusData;}
	public void setBonusData(SimpleCardData bonusData) {this.bonusData = bonusData;}
	public PositiveConditions getPositiveConditions() {return positiveConditions;}
	public void setPositiveConditions(PositiveConditions positiveConditions) {this.positiveConditions = positiveConditions;}
	public NegativeConditions getNegativeConditions() {return negativeConditions;}
	public void setNegativeConditions(NegativeConditions negativeConditions) {this.negativeConditions = negativeConditions;}
	public Effects getEffects() {return effects;}
	public void setEffects(Effects effects) {this.effects = effects;}
	
	
}
