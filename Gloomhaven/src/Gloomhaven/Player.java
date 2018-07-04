package Gloomhaven;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
	
	
	public Player() {
		xp=0;
		health=6;
		level=1;
		cardCount=10;
		Class="Mindthief";
		position = new Point(0, 0);
		pSprite=new Sprite("src/Gloomhaven/Sprites/Player.png");
		
		for(int i=0; i<cardCount; i++)
			list.add(new AbilityCards(level, i, Class));
	}
	
	private void playCard(AbilityCards card, boolean top) {
		if(top)
			cardData = card.getTop();
		else
			cardData = card.getBottom();

	}
	
	void action(KeyEvent e) {
		int key = e.getKeyCode();

	    if(key==KeyEvent.VK_SPACE) {

	    }
	}
	
	public Image getImage(){return pSprite.getImage();}
	
	public void setXY(Point position) {
		this.position=position;
	}
	
	public double getX() {return position.getX();}
	public double getY() {return position.getY();}
	
}
