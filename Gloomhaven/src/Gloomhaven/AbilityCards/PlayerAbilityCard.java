package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardInterfaces.Brute;
import Gloomhaven.CardInterfaces.CardImages;
import Gloomhaven.CardInterfaces.CardInterface;
import Gloomhaven.CardInterfaces.Cragheart;
import Gloomhaven.CardInterfaces.MindThief;
import Gloomhaven.CardInterfaces.Scoundrel;
import Gloomhaven.CardInterfaces.Spellweaver;
import Gloomhaven.CardInterfaces.Tinkerer;
import Unsorted.Setting;

public class PlayerAbilityCard extends AbilityCard {
	
	private CardDataObject topData = new CardDataObject();
	private CardDataObject bottomData = new CardDataObject();
	private int topAttack=Setting.ALTATTACK;
	private int bottomMove=Setting.ALTMOVE;
	private String useFlag="Schrodinger's Card";
	private int abilityCardCounter=0;//used to count stuff like how many targets hit
	public PlayerAbilityCard(String playerClass, int cardID, int cardLevel) {
		CardInterface classCardData=null;
		
		if(playerClass=="Mind Thief") {
			classCardData=new MindThief();
			setImage(CardImages.getMindThiefCard(cardID));
		}else if(playerClass=="Brute") {
			classCardData=new Brute();
			setImage(CardImages.getBruteCard(cardID));
		}else if(playerClass=="Scoundrel") {
			classCardData=new Scoundrel();
			setImage(CardImages.getScoundrelCard(cardID));
		}else if(playerClass=="Spellweaver") {
			classCardData=new Spellweaver();
			setImage(CardImages.getSpellweaverCard(cardID));
		}else if(playerClass=="Cragheart") {
			classCardData=new Cragheart();
			setImage(CardImages.getCragheartCard(cardID));
		}else if(playerClass=="Tinkerer") {
			classCardData=new Tinkerer();
			setImage(CardImages.getTinkererCard(cardID));
		}
		
		if(cardLevel==1) {
			setName(classCardData.getName(cardID));
			setInitiative(classCardData.getInitiative(cardID));
			topData=classCardData.getTop(cardID);
			bottomData=classCardData.getBottom(cardID);
		}

	}
	
	//Getters and Setters
	public int getAltTop() {return topAttack;}
	public int getAltBottom() {return bottomMove;}
	public CardDataObject getTopData() {return topData;}	
	public CardDataObject getBottomData() {return bottomData;}
	public String getFlag() {return useFlag;}
	public void setFlag(String flag) {this.useFlag=flag;}
	public int getAbilityCardCount() {return abilityCardCounter;}
	public void increaseAbilityCardCounter() {abilityCardCounter++;}
	public void setAbilityCardCounter(int count) {this.abilityCardCounter=count;}
	
	public String[] getText() {
		String text[] = new String[3];
		
		text[0]=Integer.toString(getInitiative());
		text[1]=topData.getCardText();
		text[2]=bottomData.getCardText();
		
		return text;
	}
}
