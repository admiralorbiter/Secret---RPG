package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import Unsorted.FontSettings;
import Unsorted.GUI;
import Unsorted.GUISettings;
import Unsorted.Setting;

public class AbilityCard {

	private String name="If you are seeing this, then error";
	private int initiative=-99;
	private ImageIcon image=null;
	private boolean lostFlag;
	private boolean discardFlag;
	private boolean inPlayFlag;
	
	public AbilityCard() {

	}
	
	public AbilityCard(ImageIcon image) {
		this.image=image;
	}
	
	public String getName() {return name;}
	public void setName(String name) {this.name=name;}
	public ImageIcon getImage() {return image;}
	public void setImage(ImageIcon image) {this.image = image;}
	public void setInitiative(int init) {this.initiative=init;}
	public int getInitiative() {return initiative;}
	public boolean isLostFlag() {return lostFlag;}
	public void setLostFlag(boolean lostFlag) {this.lostFlag = lostFlag;}
	public boolean isDiscardFlag() {return discardFlag;}
	public void setDiscardFlag(boolean discardFlag) {this.discardFlag = discardFlag;}
	public boolean isInPlayFlag() {return inPlayFlag;}
	public void setInPlayFlag(boolean inPlayFlag) {this.inPlayFlag = inPlayFlag;}

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
}
