package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import Gloomhaven.Setting;

public class AbilityCard {

	private String name;
	private int initiative;
	private boolean lostFlag;
	private boolean discardFlag;
	private boolean inPlayFlag;
	private int counter;
	private int maxCounter;
	private ImageIcon image;
	private String flag;
	
	public AbilityCard() {
		name="If you are seeing this, then error";
		initiative=-99;
		resetCardFlags();
		counter=0;
		maxCounter=1;
		setImage(null);
	}
	
	public String getFlag() {return flag;}
	public void setFlag(String flag) {this.flag=flag;}
	public String getName() {return name;}
	public int getInitiative() {return initiative;}
	public boolean getLostFlag() {return lostFlag;}
	public boolean getDiscardFlag() {return discardFlag;}
	public boolean getInPlayFlag() {return inPlayFlag;}
	public int getMaxCounter() {return maxCounter;}
	public int getCounter() {return counter;}
	
	public ImageIcon getImage() {return image;}
	public void setImage(ImageIcon image) {this.image = image;}
	
	public void resetCounter() {counter=0;}
	
	public boolean increaseCounter() {
		counter++;
		if(counter>maxCounter) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean isCardFree() {
		if(lostFlag)
			return false;
		if(discardFlag)
			return false;
		if(inPlayFlag)
			return false;
		
		return true;
	}
	
	public void resetCardFlags() {
		inPlayFlag=false;
		lostFlag=false;
		discardFlag=false;
		counter=0;
	}
	
	public void setCardInPlay() {
		inPlayFlag=true;
		lostFlag=false;
		discardFlag=false;
	}
	
	public void setCardInlostPile() {
		inPlayFlag=false;
		discardFlag=false;
		lostFlag=true;
	}
	
	public void setCardIndiscardPile() {
		inPlayFlag=false;
		lostFlag=false;
		discardFlag=true;
	}
	
	public void takeCardOutOfDiscard() {
		discardFlag=false;
	}
	
	public void showCard(Graphics g) {
		Setting setting = new Setting();
		if(getImage()!=null)
			g.drawImage(getImage().getImage(), 10, setting.getGraphicsYTop()+70, 285, 425  , null);
	}
	
	public void setName(String name) {this.name=name;}
	public void setInitiative(int init) {this.initiative=init;}
}
