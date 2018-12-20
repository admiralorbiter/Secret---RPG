package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import Gloomhaven.CardDataObject;
import Gloomhaven.Setting;
import Gloomhaven.CardInterfaces.Brute;
import Gloomhaven.CardInterfaces.CardImages;
import Gloomhaven.CardInterfaces.CardInterface;
import Gloomhaven.CardInterfaces.Cragheart;
import Gloomhaven.CardInterfaces.MindThief;
import Gloomhaven.CardInterfaces.Scoundrel;
import Gloomhaven.CardInterfaces.Spellweaver;
import Gloomhaven.CardInterfaces.Tinkerer;

public class PlayerAbilityCard extends AbilityCard {
	
	CardDataObject topData = new CardDataObject();
	CardDataObject bottomData = new CardDataObject();
	int topAttack=2;
	int bottomMove=2;
	
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
	
	public int getAltTop() {return topAttack;}
	public int getAltBottom() {return bottomMove;}
	
	public CardDataObject getTopData() {
		return topData;
	}
	
	public CardDataObject getBottomData() {
		return bottomData;
	}
	
	public String[] getText() {
		String text[] = new String[3];
		
		text[0]=Integer.toString(getInitiative());
		text[1]=topData.getCardText();
		text[2]=bottomData.getCardText();
		
		return text;
	}
	
	public void showCard(Graphics g) {
		Setting setting = new Setting();
		g.drawImage(getImage().getImage(), 10, setting.getGraphicsYTop()+70, 285, 425  , null);
		g.drawString("Press Space to Select this card. ", 20, setting.getGraphicsYTop()+70+450);
	}
}
