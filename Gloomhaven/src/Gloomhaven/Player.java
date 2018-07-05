package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Player {
	Sprite pSprite;
	String Class;
	int level;
	int xp;
	int health;
	int cardCount;
	List<AbilityCards> list = new ArrayList<AbilityCards>();
	Augument augument;
	StatusEffects status;
	CardDataObject cardData;
	Point position;
	
	int lockedCard1;
	int lockedCard2;
	
	
	public Player(String Class) {
		
		//depends on class
		xp=0;
		health=6;
		level=1;
		cardCount=10;
		
		this.Class=Class;
		position = new Point(0, 0);
		pSprite=new Sprite("src/Gloomhaven/Sprites/Player.png");
		
		for(int i=0; i<cardCount; i++)
			list.add(new AbilityCards(level, i+1, Class));
		
		lockedCard1=-1;
		lockedCard2=-1;
	}
	
	private void playCard(AbilityCards card, boolean top) {
		if(top)
			cardData = card.getTop();
		else
			cardData = card.getBottom();

	}

	public void pickCards(Graphics g) {
		for(int i=0; i<list.size(); i++)	
			g.drawString(i+": "+list.get(i).getText(), 50, i*20+500);
		g.drawString("Pick your top card", 50, list.size()*20+500);
	}
	
	public Image getImage(){return pSprite.getImage();}
	
	public void setXY(Point position) {
		this.position=position;
	}
	
	public double getX() {return position.getX();}
	public double getY() {return position.getY();}
	public int getCardCount() {return list.size();}
	
	public int getInitiative() {
		return list.get(lockedCard1).getInitiative();
	}
	
}
