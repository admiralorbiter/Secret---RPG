package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import Gloomhaven.Brute;
import Gloomhaven.CardDataObject;
import Gloomhaven.CardImages;
import Gloomhaven.CardInterface;
import Gloomhaven.Cragheart;
import Gloomhaven.MindThief;
import Gloomhaven.Scoundrel;
import Gloomhaven.Setting;
import Gloomhaven.Spellweaver;
import Gloomhaven.Tinkerer;

public class PlayerAbilityCard extends AbilityCard {
	
	CardDataObject top = new CardDataObject();
	CardDataObject bottom = new CardDataObject();
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
			top=classCardData.getTop(cardID);
			bottom=classCardData.getBottom(cardID);
		}
		
		setName(top.getName());
		setInitiative(top.getInitiative());
	}
	
	public int getAltTop() {return topAttack;}
	public int getAltBottom() {return bottomMove;}
	
	public CardDataObject getTopData() {
		return top;
	}
	
	public CardDataObject getBottomData() {
		return bottom;
	}
	
	public String[] getText() {
		String text[] = new String[3];
		
		text[0]=Integer.toString(getInitiative());
		text[1]=top.getText();
		text[2]=bottom.getText();
		
		return text;
	}
	
	public void showCard(Graphics g) {
		Setting setting = new Setting();
		g.drawImage(getImage().getImage(), 10, setting.getGraphicsYTop()+70, 285, 425  , null);
		g.drawString("Press Space to Select this card. ", 20, setting.getGraphicsYTop()+70+450);
	}
}
