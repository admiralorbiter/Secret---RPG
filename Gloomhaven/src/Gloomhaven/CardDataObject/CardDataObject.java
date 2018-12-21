package Gloomhaven.CardDataObject;

public class CardDataObject {
	
	private SimpleCardData baseData = new SimpleCardData();
	private ElementalConsumptionBonus elementalBonus = null;
	private NegativeConditions negativeConditions = null;
	private PositiveConditions positiveConditions = null;
	private Effects effects = null;
	private Counter counter = null;
	private Trigger trigger = null;
	
	private boolean infuseElementalFlag=false;
	private boolean consumeElementalFlag=false;
	private String infuseElemental="None";
	private String consumeElemental="None";
	
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
	public Trigger getTrigger() {return trigger;}
	public void setTrigger(Trigger trigger) {this.trigger=trigger;}
	public boolean getInfuseElementalFlag() {return infuseElementalFlag;}
	public boolean getConsumeElementalFlag() {return consumeElementalFlag;}
	public String getInfuseElemental() {return infuseElemental;}
	public String getConsumeElemental() {return consumeElemental;}
	public int getRecoverLostCards() {return recoverLostCards;}
	public void setRecoverLostCards(int recoverLostCards) {this.recoverLostCards=recoverLostCards;}
	public String getCardText() {return cardText;}
	public void setCardText(String cardText) {this.cardText=cardText;}
	public int getID() {return id;}
	public void setID(int id) {this.id=id;}
	public void setInfuseElemental(String infuseElemental) {
		this.infuseElemental=infuseElemental;
		this.infuseElementalFlag=true;
	}
	public void setConsumeElemental(String consumeElemental) {
		this.consumeElemental=consumeElemental;
		this.consumeElementalFlag=true;
		elementalBonus = new ElementalConsumptionBonus();
	}
	
}
