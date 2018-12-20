package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import Gloomhaven.Setting;

public class AbilityCard {

	private String name="If you are seeing this, then error";
	private int initiative=-99;
	private ImageIcon image=null;

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
	
	
	public void showCard(Graphics g) {
		Setting setting = new Setting();
		if(getImage()!=null)
			g.drawImage(getImage().getImage(), 10, setting.getGraphicsYTop()+70, 285, 425  , null);
	}

}
