package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Gloomhaven.TempStorage.AbilityCards;
import Gloomhaven.TempStorage.Augument;
import Gloomhaven.TempStorage.CardDataObject;
import Gloomhaven.TempStorage.Enemy;
import Gloomhaven.TempStorage.Sprite;
import Gloomhaven.TempStorage.StatusEffects;

public class Player {

	List<AbilityCards> deck = new ArrayList<AbilityCards>();
	List<Enemy> enemies = new ArrayList<Enemy>();
	
	//Card choice variable - if true pick top card, if false pick bottom card
	boolean cardChoice=true;
	int topCard=-1;
	int bottomCard=-1;
	
	public Player() {
		
	}
	
	//[Rem] This isn't currently being used, but might be useful to have a state before round that resets cards
	public void resetCards(){
		topCard=-1;
		bottomCard=-1;
	}
	
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
	
	public boolean cardsLocked() {
		if(topCard!=-1 && bottomCard!=-1)
			return true;
		else
			return false;
	}
}
