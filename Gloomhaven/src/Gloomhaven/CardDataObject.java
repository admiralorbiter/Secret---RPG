package Gloomhaven;

public class CardDataObject {
	
	private SimpleCardData baseData = new SimpleCardData();
	private ElementalConsumptionBonus elementalBonus = null;
	private NegativeConditions negativeConditions = null;
	private PositiveConditions positiveConditions = null;
	private Effects effects = null;
	private Counter counter = null;
	
	private boolean infuseElementalFlag=false;
	private boolean consumeElementalFlag=false;
	
	private int recoverLostCards=0;
	
	private String cardText=baseData.getCardText();
	
	private int id;
	
	public CardDataObject() {
		
	}
	
	
	//Getters and Setters
	public SimpleCardData getData() {return baseData;}
	public void setData(SimpleCardData baseData) {this.baseData = baseData;}
	public ElementalConsumptionBonus getElementalBonus() {return elementalBonus;}
	public void setElementalBonus(ElementalConsumptionBonus elementalBonus) {this.elementalBonus = elementalBonus;}
	public NegativeConditions getNegativeConditions() {return negativeConditions;}
	public void setNegativeConditions(NegativeConditions negativeConditions) {this.negativeConditions = negativeConditions;}
	public PositiveConditions getPositiveConditions() {return positiveConditions;}
	public void setPositiveConditions(PositiveConditions positiveConditions) {this.positiveConditions = positiveConditions;}
	public Effects getEffects() {return effects;}
	public void setEffects(Effects effects) {this.effects = effects;}
	public Counter getCounter() {return counter;}
	public void setCounter(Counter counter) {this.counter = counter;}
	public boolean getInfuseElementalFlag() {return infuseElementalFlag;}
	public void setInfuseElementalFlag(boolean infuseElemental) {this.infuseElementalFlag = infuseElemental;}
	public boolean getConsumeElementalFlag() {return consumeElementalFlag;}
	public void setConsumeElementalFlag(boolean consumeElemental) {this.consumeElementalFlag = consumeElemental;}
	public int getRecoverLostCards() {return recoverLostCards;}
	public void setRecoverLostCards(int recoverLostCards) {this.recoverLostCards=recoverLostCards;}
	public String getCardText() {return cardText;}
	public void setCardText(String cardText) {this.cardText=cardText;}
	public int getID() {return id;}
	public void setID(int id) {this.id=id;}
	
}
