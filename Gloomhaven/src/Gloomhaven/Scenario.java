package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
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
	Room room;
	List<Player> targets;
	String targetID;
	
	SetupScenario setup;
	
	//Key variables
	char k;															//Character from key
	int num;														//Number from Key
	
	private int currentPlayer;
	private int enemyInit;
	private int turn;
	private int playerIndex;
	private int enemyTurnIndex;
	private Point2D dimensions;
	public Scenario(String sceneID, List<Player> party) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		state=State.SETUP;											//Not sure this is necessary, flags the state for setup inside that state so...
		finished=false;												//Flag that tells whether the scenario is finished - Sets it to false
		this.party=party;											//imports the party into the scenarios

		setup = new SetupScenario(sceneID);							//creates the setup based on the scene id
		enemies = setup.getEnemies();								//gets the enemies form the setup
		
		currentPlayer=0;											//sets current player to 0 for the card selection around
		num=-1;														//sets the num variable to -1 but will be changed to what the user types in
		room = new Room(setup.getRoomID(), party, enemies);			//sets the room based on the scene id
		enemyInfo=new EnemyInfo(enemies, room);							//Creates enemy tracking object
		enemyInfo.orderEnemies();									//orders the enemies in list
		room.testDisplayRoom();
		
		//[Temp]
		dimensions=room.getDimensions();							//Sets dimensions from room
		passDimensions(enemies);									//passes dimension to enemies and party
		
		state=State.CARD_SELECTION;
	}
	
	//[Temp] Should handle the party the same way i do with enemies with an object that holds all the info
	private void passDimensions(List<Enemy> enemies) {		
		for(int i=0; i<party.size(); i++) {
			party.get(i).setDimensions(dimensions);
		}
	}
	
	//Function called to play the around, technically plays part of the round so the graphics can be updated
	public void playRound(KeyEvent key, Graphics g) {

		//[Rem] Might need else ifs in order to have the graphics update
		
		g.drawString(state.toString(), 50, 500);
		room.drawRoom(g);
		
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

				if(enemyInit==party.get(i).getInitiative()) {
					//Do nothing at the moment
					//[Temp]
				}
				else if(enemyInit<party.get(i).getInitiative()) {		//If enemy's init is lowest, it goes first
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
		
			//[Test]
			System.out.println("Turn: "+turn);
			
			if(enemyInfo.getTurnNumber()==turn) {					//If enemy turns, do enemy stuff
				//do enemy stuff
				enemyTurnIndex=0;
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
			targets = new ArrayList<Player>();
			
			
			//Goes through the enemies and sets range / distance flags
			targets=enemyInfo.enemyAttackProcedure(enemyTurnIndex, party);
			
			if(targets.size()>0) {
				//Picks first one on the list
				//[Temp]
				targetID=targets.get(0).getID();
				
				state=State.PLAYER_DEFENSE;
			}else {
				enemyControlLogic();
			}
		
		}
		
		if(state==State.PLAYER_DEFENSE) {
			for(int i=0; i<party.size(); i++) {
				if(party.get(i).getID()==targetID) {
					party.get(i).decreaseHealth(enemyInfo.getEnemy(enemyTurnIndex).getAttack());
					if(party.get(i).getHealth()<=0) {
						party.remove(i);							//Kill Player
					}
				}
			}
			enemyControlLogic();
		}
		
		if(state==State.PLAYER_CHOICE) {
			//Do player stuff

			//if turn is over
			if(turn==party.size())
				state=State.ROUND_FINISHED;
			else {
				turn++;
				state=State.ATTACK;
			}
		}
		
		//State decides if scenario is over or another round should begin
		if(state==State.ROUND_FINISHED) {
			//Look at win and finish conditions
			if(party.size()==0) 
				System.exit(1);
			
			if(enemyInfo.getCount()==0)
				System.exit(1);
			
			for(int i=0; i<party.size(); i++)
				party.get(i).resetCards();
			turn=0;
			currentPlayer=0;
			state=State.CARD_SELECTION;
		}
		
		//[Temp] Press t to end the scenario
		if(k=='t') {
			state=State.END;
		}
		
		//delayBySeconds(1);
		
	}
	
	private void enemyControlLogic() {
		//If it has gone through all the enemies, go to next state
		if(enemyTurnIndex==(enemyInfo.getCount()-1)) {
			if(turn==party.size())
				state=State.ROUND_FINISHED;
			else {
				turn++;
				state=State.ATTACK;
			}	
		}
		else {
			enemyTurnIndex++;
			state=State.ENEMY_ATTACK;
		}
	}
	
	//[Test] Function that delays for a certain amount of seconds
	private void delayBySeconds(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		//[Rem] This feels like a super bad workaround
		//Was getting null pointer if i tried to move the screen before typing in
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ // handle your exception
			   System.out.println("");
			}
		
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
	
	private void testPrintEnemyTargets() {
		//[Test]
		System.out.println("Targets for Enemy "+enemyTurnIndex+":");
		for(int i=0; i<targets.size(); i++) {
			System.out.println(i+": "+targets);
		}
		System.out.println("");
	}
	
	
}
