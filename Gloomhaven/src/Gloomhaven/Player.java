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
	int id;
	
	//Card choice variable - if true pick top card, if false pick bottom card
	//[Temp] Top card and bottom card should be card objects with an init variables
	boolean cardChoice=true;
	int topCard=-1;
	int bottomCard=-1;
	int turnNumber;
	
	Point2D coordinates;
	Point2D dimensions;
	
	StatusEffectDataObject effects = new StatusEffectDataObject();
	
	public Player(int id) {
		this.id=id;
		topCard=-1;
		bottomCard=-1;
	}
	
	//[Rem] This isn't currently being used, but might be useful to have a state before round that resets cards
	public void resetCards(){
		topCard=-1;
		bottomCard=-1;
	}
	
	//Picks the two cards needed for initiative
	public void pickCards(int key, Graphics g) {
		
		g.drawString("Picking cards", 10, 50);
		if(cardChoice) {
			g.drawString("Choose top card.", 10, 75);
			
			//[Test] Picking cards assuming there are 8
			if(key>=1 && key<=8) {
				topCard=key;
				cardChoice=!cardChoice;
			}
		}
		else {
			g.drawString("Choose bottom card.", 10, 75);
			//[Test] Picking cards assuming there are 8
			if(key>=1 && key<=8) {
				bottomCard=key;
				cardChoice=!cardChoice;
			}
			
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
		return topCard;
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
}
