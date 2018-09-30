package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Player {

	//List<AbilityCards> deck = new ArrayList<AbilityCards>();
	
	//Player Variables
	//[Rem] Will need to implement a system that creates a unique id since it is possible
	//to have multiple parties.
	String id;
	String character;
	List<PlayerAbilityCards> abilityDeck = new ArrayList<PlayerAbilityCards>();
	//Card choice variable - if true pick top card, if false pick bottom card
	//[Temp] Top card and bottom card should be card objects with an init variables
	boolean cardChoice=true;
	int topCard=-1;
	int bottomCard=-1;
	int turnNumber;
	int startingAbilityCardCount;
	Point2D coordinates;
	Point2D dimensions;
	
	StatusEffectDataObject effects = new StatusEffectDataObject();
	
	int range;
	int attack;
	int health;
	
	int TEST_HEALTH=12;
	int TEST_ATTACK=3;
	int TEST_RANGE=3;
	
	public Player(int id, String character) {
		this.id="P"+id;
		topCard=-1;
		bottomCard=-1;
		this.character=character;
		startingAbilityCardCount=10;
		
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new PlayerAbilityCards(1, i+1, character));
		
		//[Test]
		range=TEST_RANGE;
		attack=TEST_ATTACK;
		health=TEST_HEALTH;
	}
	
	//[Rem] This isn't currently being used, but might be useful to have a state before round that resets cards
	public void resetCards(){
		topCard=-1;
		bottomCard=-1;
	}
	
	//Picks the two cards needed for initiative
	public void pickCards(int key, Graphics g) {
		
		g.drawString("Picking cards", 10, 50);
		drawAbilityCards(g);
		
		if(abilityDeck.size()>1) {
			if(cardChoice) {
				g.drawString("Choose top card.", 10, 75);
				
				//[Test] Picking cards assuming there are 8
				if(key>=1 && key<=abilityDeck.size()) {
					topCard=key;
					cardChoice=!cardChoice;
				}
			}
			else {
				g.drawString("Choose bottom card.", 10, 75);
				//[Test] Picking cards assuming there are 8
				if(key>=1 && key<=abilityDeck.size()) {
					bottomCard=key;
					cardChoice=!cardChoice;
				}
				
			}
		}
	}
	
	public void drawAbilityCards(Graphics g) {
		for(int i=0; i<abilityDeck.size(); i++) {
			g.drawString(abilityDeck.get(i).getText(), 10, 90+i*15);
		}
	}
	
	
	//Returns whether the two cards have been locked for initiative
	public boolean cardsLocked() {
		if(topCard!=-1 && bottomCard!=-1)
			return true;
		else
			return false;
	}
	
	public int getInitiative() {
		return abilityDeck.get(topCard).getInitiative();
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}
	
	public int getTurnNumber() {return turnNumber;}
	
	public void setPoint(Point2D point) {
		this.coordinates=point;
	}
	
	public Point2D getCoordinate() {return coordinates;}
	
	public void setDimensions(Point2D dimensions) {
		this.dimensions=dimensions;
	}
	
	public String getID() {return id;}
	public int getAttack() {return attack;}
	public int getHealth() {return health;}
	public void decreaseHealth(int damage) {
		health=health-damage;
		
		//[Test]
		System.out.println("Player was attacked for "+damage+" making thier health "+health);
	}
}
