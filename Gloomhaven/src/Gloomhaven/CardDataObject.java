package Gloomhaven;

public class CardDataObject {
	SimpleCardData baseData = new SimpleCardData();
	ElementalConsumptionBonus elementalBonus = null;
	NegativeConditions negativeConditions = null;
	PositiveConditions positiveConditions = null;
	Effects effects = null;
	
	boolean infuseElemental=false;
	boolean consumeElemental=false;
	
	public CardDataObject() {
		
	}
}
