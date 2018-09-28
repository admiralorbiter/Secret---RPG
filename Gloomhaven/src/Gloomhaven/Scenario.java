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
	    ENEMY_ATTACK,
	    PLAYER_CHOICE,
	    PLAYER_DEFENSE,
	    ENEMY_DEFENSE,
	    PLAYER_CARD,
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
	private int turn;
	private int playerIndex;
	
	public Scenario(int sceneID, List<Player> party) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		state=State.SETUP;
		finished=false;
		this.party=party;

		if(sceneID==1) {
			enemies.add(new Enemy("Test"));
			enemies.add(new Enemy("TestElite"));
		}
		enemyInfo=new EnemyInfo(enemies);
		enemyInfo.orderEnemies();
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
			
			//Goes through the party and enemy and gives a turn number
			//The party is in order, so i just have to fit the enemy in
			for(int i=0; i<party.size(); i++) {
				if(enemyInit<party.get(i).getInitiative()) {		//If enemy's init is lowest, it goes first
					enemyInfo.setTurnNumber(i);
					party.get(i).setTurnNumber(i+1);
				}
				else if(party.get(i).getInitiative()<enemyInit) {	//Sorts player's with lower init before enemy
					party.get(i).setTurnNumber(i);
				}
				else if(party.get(i-1).getInitiative()<enemyInit){	//places the enemy between players if the previous one is lower, but the next is higher
					enemyInfo.setTurnNumber(i);
					party.get(i).setTurnNumber(i+1);
				}
				else {												//Everyone else is placed after the enemy
					party.get(i).setTurnNumber(i+1);
				}
			}
			playerIndex=-1;
			turn=0;
			state=State.ATTACK;										//change state to attack
		}
		//State has the logic for who should be attacking and setting the state for enemy attack, player defense, etc
		//Also should know the end state of when attacks are over and round is done
		if(state==State.ATTACK) {
	
			if(enemyInfo.getTurnNumber()==turn) {					//If enemy turns, do enemy stuff
				//do enemy stuff
				turn=0;
				state=State.ENEMY_ATTACK;
			}
			else {
				for(int i=0; i<party.size(); i++) {					//Searches for a match on the turn and the players
					if(party.get(i).getTurnNumber()==turn) {		//Once a match is found, sets the index, changes state, and breaks
						playerIndex=i;
						state=State.PLAYER_CHOICE;
						break;
					}
				}
			}
		}
		
		if(state==State.ENEMY_ATTACK) {
			//Do Enemy Attack Stuff
			//Enemies ordered at the beginning of the scenario
			//Goes through all the enemies and sets distance and range flags
			//if no one is next, move and attack
			//if none are in distance, move to the next
			//if turn is over
			
			//turn variable will keep track of whose turn it is
			//This will allow me to move them one by one so it shows up in the graphics
			int enemyCount=enemyInfo.getCount();
			boolean playerInRange;
			//Goes through the enemies and sets range / distance flags
			playerInRange=enemyInfo.enemyAttackProcedure(turn);
			
			if(playerInRange)
				state=State.PLAYER_DEFENSE;
			else {
				//If it has gone through all the enemies, go to next state
				if(turn==enemyInfo.getCount()) {
					turn=0;
					if(turn==party.size()+1)
						state=State.ROUND_FINISHED;
					else
						state=State.ATTACK;
				}
			}
		}
		
		if(state==State.PLAYER_DEFENSE) {
			
			//If it has gone through all the enemies, go to next state
			if(turn==enemyInfo.getCount()) {
				turn=0;
				if(turn==party.size()+1)
					state=State.ROUND_FINISHED;
				else
					state=State.ATTACK;
			}
		}
		
		if(state==State.PLAYER_CHOICE) {
			//Do player stuff
			
			//if turn is over
			if(turn==party.size()+1)
				state=State.ROUND_FINISHED;
			else
				state=State.ATTACK;
		}
		
		//State decides if scenario is over or another round should begin
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
	
	//[Test] Function to print order of the init
	private void testPrintTurnList() {
		//[Test]
		for(int i=0; i<party.size()+1; i++) {
			for(int j=0; j<party.size(); j++) {
				if(party.get(j).getTurnNumber()==i)
					System.out.println(i+" - "+party.get(j).getInitiative());
				else
					System.out.println(i+" - "+enemyInfo.getTurnNumber());
			}
		}
	}
	
	
}
