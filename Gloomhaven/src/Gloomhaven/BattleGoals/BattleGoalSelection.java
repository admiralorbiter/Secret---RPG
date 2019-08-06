package Gloomhaven.BattleGoals;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import Gloomhaven.Characters.Player;
import Unsorted.GUI;

/**
 * Framework for Selecting the Battle Goal Card
 */
public class BattleGoalSelection implements Serializable{

	private BattleGoalCard card1=null;
	private BattleGoalCard card2=null;
	
	/**
	 * Randomly draws two unique cards from the deck and
	 * temporarily holds them until player chooses 1
	 * @param deck	Battle Goal Card Deck
	 */
	public BattleGoalSelection(List<BattleGoalCard> deck) {
		Random r = new Random();
		int index1=r.nextInt(deck.size()-1);
		card1 = deck.get(index1);
		
		int index2;
		do{
			index2=r.nextInt(deck.size()-1);
		}
		while(index2==index1);
		
		card2 = deck.get(index2);
	}
	
	/**
	 * Draws the two battle goal cards, then has the player select one of them
	 * @param g			Graphics Object
	 * @param key		KeyEvent
	 * @param player 	Plyaer Object
	 * @param deck		Battle Goal Deck
	 * @return			Returns wether a card has been picked yet
	 */
	public boolean chooseCard(Graphics g, KeyEvent key, Player player, List<BattleGoalCard> deck) {
			
		GUI.drawBattleGoal(g, card1, card2);
		
		if(key!=null) {
			if(key.getKeyCode()==KeyEvent.VK_1) {
				player.setBattleGoalCard(card1);
				deck.remove(card1);
				return true;
			}
			if(key.getKeyCode()==KeyEvent.VK_2) {
				player.setBattleGoalCard(card2);
				deck.remove(card2);
				return true;
			}
		}
		return false;
	}
}
