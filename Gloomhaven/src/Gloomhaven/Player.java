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
	int initiative=-1;
	PlayerAbilityCards topCard=null;
	PlayerAbilityCards bottomCard=null;
	List<PlayerAbilityCards> inPlay = new ArrayList<PlayerAbilityCards>();
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
		initiative=-1;
		topCard=null;
		bottomCard=null;
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
		initiative=-1;
		topCard=null;
		bottomCard=null;
	}
	
	//Picks the two cards needed for initiative
	public void pickCards(int key, Graphics g) {
		
		g.drawString("Picking cards", 10, 50);
		drawAbilityCards(g);
		
		if(abilityDeck.size()>1) {
			if(cardChoice) {
				g.drawString("Choose top card.", 10, 75);
				
				//[Test] Picking cards assuming there are 8
				if(key>=0 && key<=abilityDeck.size()) {
					if(abilityDeck.get(key).cardFree()) {
						topCard=abilityDeck.get(key);
						abilityDeck.get(key).setInPlay();
						initiative=abilityDeck.get(key).getInitiative();
						cardChoice=!cardChoice;
					}
				}
			}
			else {
				g.drawString("Choose bottom card.", 10, 75);
				//[Test] Picking cards assuming there are 8
				if(key>=0 && key<=abilityDeck.size()) {
					if(abilityDeck.get(key).cardFree()) {
						bottomCard=abilityDeck.get(key);
						abilityDeck.get(key).setInPlay();
						cardChoice=!cardChoice;
					}
				}
				
			}
		}
	}
	
	public void drawAbilityCards(Graphics g) {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).cardFree())
				g.drawString(i+": "+abilityDeck.get(i).getText(), 10, 90+i*15);
		}
	}
	
	public void endTurn() {
		CardDataObject card= topCard.getTop();
		int index = topCard.getIndex();
		
		if(card.continuous) {
			inPlay.add(topCard);									//Need a way to track if i am using the top or bottom as a cont
		}else if(card.lost) {
			abilityDeck.get(index).lostPile();
		}else {
			abilityDeck.get(index).discardPile();
		}
		
		card= bottomCard.getBottom();
		index = bottomCard.getIndex();
		
		if(card.continuous) {
			inPlay.add(bottomCard);									//Need a way to track if i am using the top or bottom as a cont
		}else if(card.lost) {
			abilityDeck.get(index).lostPile();
		}else {
			abilityDeck.get(index).discardPile();
		}
		
	}
	
	
	//Returns whether the two cards have been locked for initiative
	public boolean cardsLocked() {
		if(topCard!=null && bottomCard!=null)
			return true;
		else
			return false;
	}
	
	public int getInitiative() {
		return initiative;
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
	
	//[Test]
	public int testGetTopCardIndex() {return topCard.getIndex();}
	public int testGetBottomCardIndex() {return bottomCard.getIndex();}
	
	public String getID() {return id;}
	public int getAttack() {return attack;}
	public int getHealth() {return health;}
	public void decreaseHealth(int damage) {
		health=health-damage;
		
		//[Test]
		System.out.println("Player was attacked for "+damage+" making thier health "+health);
	}
}
