package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.*;

import Gloomhaven.GamePanel.GameState;

public class Scenario {

	public enum State {
	    SETUP,
	    CARD_SELECTION,
	    INITIATIVE,
	    ATTACK,
	    ENEMYATTACK,
	    PLAYERCHOICE,
	    PLAYERDEFENSE,
	    ENEMYDEFENSE,
	    PLAYERCARD,
	    ROUND_FINISHED,
	    END;
	}
	
	State state;													//State of the scenario
	boolean finished;												//Tells the gamepanel if the scenario is finished
	List<Player> party = new ArrayList<Player>();					//Party
	//List<Enemy> enemies = new ArrayList<Enemy>();
	//AttackDeck enemyDeck = new AttackDeck();						//Creates enemy attack deck - [Temp] Will need to have it flag and select the enemy one
	EnemyInfo enemyInfo;
	
	//Key variables
	char k;															//Character from key
	int num;														//Number from Key
	
	private int currentPlayer;
	private int enemyInit;
	
	public Scenario(int sceneID, List<Player> party) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		state=State.SETUP;
		finished=false;
		this.party=party;

		if(sceneID==1) {
			enemies.add(new Enemy());
		}
		enemyInfo=new EnemyInfo(enemies);
		
		currentPlayer=0;											//sets currentplayer to 0 for the card selection around
		num=-1;
		state=State.CARD_SELECTION;
	}
	
	//Function called to play the around, technically plays part of the round so the graphics can be updated
	public void playRound(KeyEvent key, Graphics g) {

		//[Rem] Might need else ifs in order to have the graphics update
		
		parseKey(key);
		if(state==State.CARD_SELECTION) {
			party.get(currentPlayer).pickCards(num, g);
			if(party.get(currentPlayer).cardsLocked()) {
				if((currentPlayer+1)!=party.size())
					currentPlayer++;
				else {
					currentPlayer=0;
					state=State.INITIATIVE;
				}
			}
		}
		//State sets initiative and orders players based on it
		if(state==State.INITIATIVE) {

			enemyInit=enemyInfo.drawCard();							//Pick enemy card for initiative
			orderPlayers();											//order players
			
			for(int i=0; i<party.size(); i++) {
				if(enemyInit<party.get(i).getInitiative()) {
					enemyInfo.setTurnNumber(i);
					party.get(i).setTurnNumber(i+1);
				}
				else if(party.get(i).getInitiative()<enemyInit) {
					party.get(i).setTurnNumber(i);
				}
				else if(party.get(i-1).getInitiative()<enemyInit){
					enemyInfo.setTurnNumber(i);
					party.get(i).setTurnNumber(i+1);
				}
				else {
					party.get(i).setTurnNumber(i+1);
				}
			}
			
			state=State.ATTACK;										//change state to attack
		}
		//State has the logic for who should be attacking and setting the state for enemy attack, player defense, etc
		//Also should know the end state of when attacks are over and round is done
		if(state==State.ATTACK) {
			//[Test]
			for(int i=0; i<party.size()+1; i++) {
				for(int j=0; j<party.size(); j++) {
					if(party.get(j).getTurnNumber()==i)
						System.out.println(party.get(j).getInitiative());
					else
						System.out.println(enemyInfo.getTurnNumber());
				}
			}
		}
		
		//State deciedes if scenario is over or another round should begin
		if(state==State.ROUND_FINISHED) {
			
		}
		
		//[Temp] Press t to end the scenario
		if(k=='t') {
			state=State.END;
		}
		
	}
	
	//Returns if the round is finished or still being played
	public boolean finished() {
		if(state==State.END) {
			return true;
		}
		else
			return false;
	}
	
	/*
	 * [Rem] Should be used in a class that handles functions that will be used in other places
	 * Takes in key event and turns it into a character or a number
	 * Catches exceptions so it doesn't give errors
	 */
	private void parseKey(KeyEvent key) {
		k = key.getKeyChar();
		num = -1;
		try{
			num=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ // handle your exception
			   System.out.println("");
			}
	}
	
	//[Rem] This will only sort the players based on initiative. The enemy init will need to be taken into account during the battle phase
	private List<Player> orderPlayers() {
		List<Player> order = new ArrayList<Player>();

		order=party;
		
		//Sorts the party comparing the initiative which is based on the top card
		//[Rem] That at the moment initiative is based on top card number, not actual init
		order.sort(Comparator.comparingInt(Player::getInitiative));

		return order;
	}
	
}
