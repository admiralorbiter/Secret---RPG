package Gloomhaven;

import java.awt.Color;
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
	    CARD_SELECTION,
	    INITIATIVE,
	    ATTACK,
	    ENEMY_ATTACK,
	    PLAYER_CHOICE,
	    PLAYER_DEFENSE,
	    PLAYER_DISCARD,
	    ENEMY_DEFENSE,
	    PLAYER_CARD,
	    ROUND_END_DISCARD,
	    ROUND_END_REST,
	    PLAYER_ATTACK_LOGIC,
	    PLAYER_MOVE,
	    PLAYER_ATTACK,
	    LONG_REST,
	    END;
	}
	
	private State state;																			//State of the scenario
	private List<Player> party = new ArrayList<Player>();											//Current party of players
	private EnemyInfo enemyInfo;																	//Holds the info about the enemies in the scenario
	private Room room;																				//Holds the room data including where enemies and players are											
	private Setting setting = new Setting();
	
	//Key variables
	private char k=' ';																				//Character from key
	private int num=-1;																				//Number from Key
	
	//Variables that are global due to having to refresh and keep the state the same
	private int currentPlayer;																		//Current Player's turn (Used before the round, during initiative and closing the round)
	private int playerIndex;																		//Player being targeted
	private int turn;																				//Turn number (Used during the battle/attack phases so enemies are involved)				
	private Point dimensions;																		//Dimensions of the room
	private List<Player> targets;																	//Target list created by the enemy
	private String targetID;																		//Player being targeted by the enemy						
	private CardDataObject card = new CardDataObject();												//Card object used by the player's attack
	
	//Need to refactor - Enemy turn index is held in enemy info, so no need to keep a variable
	private int enemyTurnIndex;		
	
	public Scenario(String sceneID, List<Player> party) {			
		
		this.party=party;																			//imports the party into the scenarios
		//Setups up the room and enemies based on the scene id
		SetupScenario setup = new SetupScenario(sceneID);			
		room = new Room(setup.getRoomID(), party, setup.getEnemies());
		enemyInfo=new EnemyInfo(setup.getEnemies(), room);
		
		
		currentPlayer=0;																			//sets current player to 0 for the card selection around
		enemyInfo.orderEnemies();																	//orders the enemies in list
		dimensions=room.getDimensions();															//Sets dimensions from room
		passDimensions();																			//passes dimension to party
		
		state=State.CARD_SELECTION;
	}
	
	//[Temp] Should handle the party the same way i do with enemies with an object that holds all the info
	private void passDimensions() {		
		for(int i=0; i<party.size(); i++) {
			party.get(i).setDimensions(dimensions);
		}
	}
	
	//Function called to play the around, technically plays part of the round so the graphics can be updated
	//[Rem] Else ifs in order to have the graphics update
	public void playRound(KeyEvent key, Graphics g) {

		//[Test]
		g.drawString("State of Scenario", setting.getGraphicsX()*5, setting.getGraphicsYBottom());
		g.drawString(state.toString(), setting.getGraphicsX()*5, setting.getGraphicsYBottom()+15);
		
		
		room.drawRoom(g);																			//Draws current room
		parseKey(key);																				//Parses the input key as either a character or number
	
		//STATE: CARD_SELECTION: Players pick their cards for initiative (or take a rest)
		if(state==State.CARD_SELECTION) {
			
			g.drawString("Picking Cards for your turn.", setting.getGraphicsX(), setting.getGraphicsYTop());
			
			//Allows the user to take a long rest if they have enough in the discard pile 
			if(party.get(currentPlayer).discardPileSize()>1)	
				g.drawString("Take a long rest with 'r'", setting.getGraphicsX(), setting.getGraphicsX()+15);
			
			//Draw's the player's available ability cards
			party.get(currentPlayer).drawAbilityCards(g);			
			
			//Player enters long rest or picks ability cards
			if((k=='r') && (party.get(currentPlayer).discardPileSize()>1))
				party.get(currentPlayer).setLongRest();
			else
				party.get(currentPlayer).pickAbilityCards(num, g);
			
			//Cycles through the players then the enemy picks an ability card
			//Next State: Initiative
			if(party.get(currentPlayer).cardsLocked()) {
				if((currentPlayer+1)!=party.size())
					currentPlayer++;
				else {
					enemyInfo.pickRandomAbilityCard();
					currentPlayer=0;
					state=State.INITIATIVE;
				}
			}
		}
		//State: INITIATIVE: Everyone is ordered based on their initiative
		else if(state==State.INITIATIVE) {

			int enemyInit=enemyInfo.getInitiative();												//Collect enemy initiative from ability card
			party.sort(Comparator.comparingInt(Player::getInitiative));								//Order just the players based on initiative
			
			//List of all the initiatives for the round
			g.drawString("Initiatives:", 50, 380);											
			g.drawString("Enemy: "+String.valueOf(enemyInit), 50, 400);								
			for(int i=0; i<party.size();  i++) {
				g.drawString("Player "+party.get(i).getID()+"      "+String.valueOf(party.get(i).getInitiative()), 5*setting.getGraphicsX(), setting.getGraphicsYBottom()+15*i);
			}
			
			
			//[Temp] Couldn't get the order right so players go first, enemies last
			for(int i=0; i<party.size(); i++)
				party.get(i).setTurnNumber(i);
			
			enemyInfo.setTurnNumber(party.size());
			
			/*
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
			}*/
			
			playerIndex=-1;
			turn=0;
			state=State.ATTACK;										//change state to attack
		}
		//State has the logic for who should be attacking and setting the state for enemy attack, player defense, etc
		//Also should know the end state of when attacks are over and round is done
		else if(state==State.ATTACK) {
		
			if(enemyInfo.getTurnNumber()==turn) {					//If enemy turns, do enemy stuff
				//do enemy stuff
				enemyTurnIndex=0;
				state=State.ENEMY_ATTACK;
			}
			else {
				
				
				for(int i=0; i<party.size(); i++) {					//Searches for a match on the turn and the players
					if(party.get(i).getTurnNumber()==turn) {		//Once a match is found, sets the index, changes state, and breaks
						if(party.get(i).onRest()) {
							playerIndex=i;
							party.get(i).resetCardChoice();
							state=State.LONG_REST;
							break;
						}
						else {
							playerIndex=i;
							party.get(i).resetCardChoice();				//Resets card choice so it can be used in player choice when picking cards
							state=State.PLAYER_CHOICE;
							break;
						}
					}
				}
				
				System.out.println("In Scenario.java [Test] If you are seeing this, it shouldn't be possible");
			}
		}
		else if(state==State.LONG_REST) {
			boolean finished=false;
			party.get(playerIndex).takeLongRest(g, num);
			if(party.get(playerIndex).onRest()==false)
				finished=true;
			
			if(finished) {
				turn++;
				state=State.ATTACK;
			}
		}
		else if(state==State.ENEMY_ATTACK) {
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
		else if(state==State.PLAYER_DEFENSE) {
			g.drawString("Press d to discard card or h to take damage.", 50, 550);
			int playerIndex = getTargetIndex();
			
			if((k=='h') || (party.get(playerIndex).abilityCardsLeft()==0)) {
				int damage=enemyInfo.getAttack(enemyTurnIndex);
				party.get(playerIndex).decreaseHealth(damage);
				if(party.get(playerIndex).getHealth()<=0) {
					//[Test]
					System.out.println("Player is dead");
					party.remove(playerIndex);						//Kill Player
				}
				if(party.size()==0)
					state=State.ROUND_END_DISCARD;
				else
					enemyControlLogic();
			}
			if(k=='d') {
				state=State.PLAYER_DISCARD;
			}
		}
		else if(state==State.PLAYER_DISCARD) {
			if(party.get(playerIndex).discardForHealth(num, g))
				enemyControlLogic();
		}
		else if(state==State.PLAYER_CHOICE) {
			//Do player stuff
			card = new CardDataObject();
			
			//[Test]
			//room.drawRange(g, party.get(currentPlayer).getCoordinate(), 1, Color.GREEN);
			//room.drawRange(g, party.get(currentPlayer).getCoordinate(), 2, Color.BLUE);
			//room.drawRange(g, party.get(currentPlayer).getCoordinate(), 3, Color.GREEN);
			
			
			int cardPick=party.get(currentPlayer).pickPlayCard(num, g);
			if(cardPick>=1 && cardPick<=4)
				state=State.PLAYER_ATTACK_LOGIC;
		
		}
		else if(state==State.PLAYER_ATTACK_LOGIC) {
			card = party.get(currentPlayer).playCard();
			//[Temp] Currently only moves then attacks
			Point playerPoint=party.get(currentPlayer).getCoordinate();
			room.setSelectionCoordinates(playerPoint);
			String textTemp = "Move "+card.move+"     Attack: "+card.attack;
			g.drawString(textTemp, 50, 400);
			
			if(card.move>0) {
				state=State.PLAYER_MOVE;
			}else if(card.attack>0) {
				state=State.PLAYER_ATTACK;
			}else {
				//if turn is over
				if(turn==party.size())
					state=State.ROUND_END_DISCARD;
				else {
					turn++;
					state=State.ATTACK;
				}
			}
		}
		else if(state==State.PLAYER_MOVE) {
			boolean finished=false;
			
			
			//Highlight tiles that players can move to
			Point playerPoint=party.get(currentPlayer).getCoordinate();
			for(int r=1; r<=card.move; r++)
				room.drawRange(g, playerPoint, r, Color.BLUE);
	
			g.drawString("Press m to move.", 10, 400);
			selection(g);
			
			if(k=='m') {
				if(room.isSpaceEmpty(room.getSelectionCoordinates())) {
					room.movePlayer(party.get(currentPlayer).getCoordinate(), room.getSelectionCoordinates());
					party.get(currentPlayer).movePlayer(new Point(room.getSelectionCoordinates()));
					finished=true;
				}
				
				if(room.isSpace(room.getSelectionCoordinates(), "P")) {
					finished=true;
				}
			}

			if(finished) {
				//[Test]
				System.out.println(card.attack);
				
				if(card.attack>0) {
					room.setSelectionCoordinates(new Point(room.getSelectionCoordinates()));
					state=State.PLAYER_ATTACK;
				}else {
					//if turn is over
					if(turn==party.size())
						state=State.ROUND_END_DISCARD;
					else {
						turn++;
						state=State.ATTACK;
					}
				}
			}
		}
		else if(state==State.PLAYER_ATTACK) {
			boolean finished=false;
			//make the player attack
			
			List<Point> targets = new ArrayList<Point>();
			if(card.range>0) {
				for(int range=1; range<card.range; range++)
					targets = party.get(currentPlayer).createTargetList(room.getBoard(), range);
			}
			
			if(targets.size()>0) {
				room.highlightTargets(targets, g);
				
				selection(g);
				
				if(k==' ') {
					if(room.isSpace(room.getSelectionCoordinates(), "E")) {
						if(targets.contains(room.getSelectionCoordinates())){
							String id = room.getID(room.getSelectionCoordinates());
							int damage=party.get(currentPlayer).getAttack(card);
							enemyInfo.playerAttack(id, damage);
							finished=true;
						}
					}
				}
			}else {
				finished=true;
			}

			
			if(finished) {
				if(party.get(currentPlayer).getCardChoice()==false) {
					state=State.PLAYER_CHOICE;
				}else {
					//if turn is over
					if(turn==party.size())
						state=State.ROUND_END_DISCARD;
					else {
						turn++;
						state=State.ATTACK;
					}
				}
			}
		}
		//State decides if scenario is over or another round should begin
		else if(state==State.ROUND_END_DISCARD) {
			for(int i=0; i<party.size(); i++)
				party.get(i).endTurn();
			
			if(party.size()==0) 
				System.exit(1);
			
			if(enemyInfo.getCount()==0)
				System.exit(1);
			
			currentPlayer=0;
			state=State.ROUND_END_REST;
		}
		else if(state==State.ROUND_END_REST) {
			//Look at win and finish conditions
			boolean finished=false;
			if(party.get(currentPlayer).discardPileSize()>1) {
			
				party.get(currentPlayer).shortRestInfo(g);
				
				if(k=='y') {
					party.get(currentPlayer).takeShortRest();
					if((currentPlayer+1)!=party.size())
						currentPlayer++;
					else
						finished=true;
				}
				
				if(k=='n') {
					if((currentPlayer+1)!=party.size())
						currentPlayer++;
					else
						finished=true;
				}
			}else {
				finished=true;
			}
			
			if(finished) {
				for(int i=0; i<party.size(); i++)
					party.get(i).resetCards();
				turn=0;
				currentPlayer=0;
				state=State.CARD_SELECTION;
			}
		}
		//[Temp] Press t to end the scenario
		else if(k=='t') {
			state=State.END;
		}
		
		//delayBySeconds(1);
		
	}
	
	//Parses key event into either a character or a number to be used
	private void parseKey(KeyEvent key) {
		k=Character.MIN_VALUE;
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ 						// handle null pointers for getKeyChar
			   System.out.println("");
			}
		
		num = -1;
		try{
			num=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ 						// handle if it isn't a number character
			   System.out.println("");
			}
	}

	private void selection(Graphics g) {
		room.drawSelectionHex(g);
		delayBy(100);											//Makes it feel more smoother
		Point selectTemp=new Point(room.getSelectionCoordinates());			//[Rem] Probably more efficient to move in the if  and change it only if it is changed	
		
		if(k=='w') {
			if(selectTemp.y-1>=0) {
				selectTemp.y=selectTemp.y-1;
			}
			
		}
		if(k=='a') {
			if(selectTemp.x-1>=0) {
				selectTemp.x=selectTemp.x-1;
			}
		}
		if(k=='s') {
			if(selectTemp.y+1<room.getDimensions().getY()) {
				selectTemp.y=selectTemp.y+1;
			}
		}
		if(k=='d') {
			if(selectTemp.x+1<room.getDimensions().getX()) {
				selectTemp.x=selectTemp.x+1;
			}
		}
		

		room.setSelectionCoordinates(selectTemp);
	}
	
	
	private void enemyControlLogic() {
		//If it has gone through all the enemies, go to next state
		if(enemyTurnIndex==(enemyInfo.getCount()-1)) {
			if(turn==party.size())
				state=State.ROUND_END_DISCARD;
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
	private void delayBy(int time) {
		try {
			TimeUnit.MILLISECONDS.sleep(time);
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
	
	private int getTargetIndex() {
		int playerIndex = -1;
		for(int i=0; i<party.size(); i++) {
			if(party.get(i).getID()==targetID) {
				playerIndex=i;
			}
		}
		return playerIndex;
	}
	
	
}
