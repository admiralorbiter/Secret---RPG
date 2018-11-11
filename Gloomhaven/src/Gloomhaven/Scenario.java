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
	    PLAYER_LOOT,
	    PLAYER_HEAL,
	    LONG_REST,
	    MINDCONTROL,
	    PLAYER_PUSH_SELECTION,
	    PLAYER_PUSH,
	    END;
	}
	
	private State state;																			//State of the scenario
	private List<Player> party = new ArrayList<Player>();											//Current party of players
	private EnemyInfo enemyInfo;																	//Holds the info about the enemies in the scenario
	private Room room;																				//Holds the room data including where enemies and players are											
	private Setting setting = new Setting();
	private InfusionTable elements = new InfusionTable();
	
	private Point oppPoint;
	
	//Key variables
	private char k=' ';																				//Character from key
	private int num=-1;																				//Number from Key
	
	//Variables that are global due to having to refresh and keep the state the same
	private int currentPlayer;																		//Current Player's turn (Used before the round, during initiative and closing the round)
	private int playerIndex;																		//Player being targeted
	private int turn;																				//Turn number (Used during the battle/attack phases so enemies are involved)				
	private Point dimensions;																		//Dimensions of the room
	//private List<Player> targets;																	//Target list created by the enemy
	private String targetID;																		//Player being targeted by the enemy						
	private PlayerAbilityCards card = null;											//Card object used by the player's attack
	private Enemy enemyControlled = null;
	private Enemy enemyTarget = null;
	//Need to refactor - Enemy turn index is held in enemy info, so no need to keep a variable
	private int enemyTurnIndex;		
	
	public Scenario(String sceneID, List<Player> party) {			
		
		this.party=party;		//imports the party into the scenarios
		SetupScenario setup = new SetupScenario(sceneID);	
		//Setups up the room and enemies based on the scene id
		List<Enemy> enemies = new ArrayList<Enemy>();
		enemies=setup.getEnemies();		
		room = new Room(setup.getRoomID(), party, enemies);
		enemyInfo=new EnemyInfo(enemies, room);
	
		currentPlayer=0;																			//sets current player to 0 for the card selection around
		enemyInfo.orderEnemies();																	//orders the enemies in list
		dimensions=room.getDimensions();															//Sets dimensions from room
		passDimensions();																			//passes dimension to party
		
		state=State.CARD_SELECTION;
	}
	
	//Returns if the round is finished or still being played
	//[Rem] Needs to be fleshed out
	public boolean finished() {
		return false;
	}
		
	//Function called to play the around, technically plays part of the round so the graphics can be updated
	//[Rem] Else ifs in order to have the graphics update
	public void playRound(KeyEvent key, Graphics g) {
	
		//System.out.println(state);
		
		
		//[Test]
		g.drawString("State of Scenario", setting.getGraphicsX()*5, setting.getGraphicsYTop());
		g.drawString(state.toString(), setting.getGraphicsX()*5, setting.getGraphicsYTop()+15);
		
		
		party.get(0).graphicsPlayerInfo(g);
		elements.graphicsDrawTable(g);
		party.get(0).graphicsDrawCardsInPlay(g);
		
		room.drawRoom(g);																			//Draws current room
		parseKey(key);																				//Parses the input key as either a character or number
	
		//STATE: CARD_SELECTION: Players pick their cards for initiative (or take a rest)--------------------------------------------------------------------------------
		if(state==State.CARD_SELECTION) {
			g.drawString("Picking Cards for your turn.", setting.getGraphicsX(), setting.getGraphicsYTop()+30);
			
			//Allows the user to take a long rest if they have enough in the discard pile 
			if(party.get(currentPlayer).discardPileSize()>1)	
				g.drawString("Take a long rest with "+setting.getRestKey(), setting.getGraphicsX(), setting.getGraphicsYTop()+45);
			
			//Draw's the player's available ability cards
			party.get(currentPlayer).drawAbilityCards(g);			
			
			//Player enters long rest or picks ability cards
			if((k==setting.getRestKey()) && (party.get(currentPlayer).discardPileSize()>1))
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
		//State: INITIATIVE: Everyone is ordered based on their initiative-----------------------------------------------------------------------------------------------
		else if(state==State.INITIATIVE) {
	
			int enemyInit=enemyInfo.getInitiative();												//Collect enemy initiative from ability card
			party.sort(Comparator.comparingInt(Player::getInitiative));								//Order just the players based on initiative
			
			//List of all the initiatives for the round
			g.drawString("Initiatives:", 5*setting.getGraphicsX(), setting.getGraphicsYBottom()-30);											
			g.drawString("Enemy: "+String.valueOf(enemyInit), 5*setting.getGraphicsX(), setting.getGraphicsYBottom()-15);								
			for(int i=0; i<party.size();  i++) {
				g.drawString("Player "+party.get(i).getID()+"      "+String.valueOf(party.get(i).getInitiative()), 5*setting.getGraphicsX(), setting.getGraphicsYBottom()+15*i);
			}
			
			/*enemyInfo.setTurnNumber(0);
			//[Temp] Couldn't get the order right so players go first, enemies last
			for(int i=0; i<party.size(); i++)
				party.get(i).setTurnNumber(i+1);
			
			enemyInfo.setTurnNumber(party.size());
			*/
			
			//Goes through the party and enemy and gives a turn number
			//The party is in order, so i just have to fit the enemy in
			//[Rem] Doesn't work if the players have the same init
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
					enemyInfo.setTurnNumber(i+1);
				}
				else if(party.get(i-1).getInitiative()<enemyInit){	//places the enemy between players if the previous one is lower, but the next is higher
					enemyInfo.setTurnNumber(i);
					party.get(i).setTurnNumber(i+1);
				}
				else {												//Everyone else is placed after the enemy
					party.get(i).setTurnNumber(i+1);
				}

			}
			
			playerIndex=0;																			//playerIndex is reset so it can be used instead of current player
			turn=0;																					//Sets the turn number to 0 and will cyle through all players/enemies
			state=State.ATTACK;																		//Next State: ATTACK
		}
		//State: ATTACK: has the logic for who should be attacking and setting the state for enemy attack, player defense, etc-------------------------------------------
		//Also should know the end state of when attacks are over and round is done
		else if(state==State.ATTACK) {
			//Next State: Enemy Attack
			if(enemyInfo.getTurnNumber()==turn) {													//If enemy turns, do enemy attack
				enemyTurnIndex=0;																	//Resets enemy turn index
				state=State.ENEMY_ATTACK;															//Goes to STATE:ENEMY_ATTACK
			}
			else {
				
				//Next State: Long Rest or Player Choice
				for(int i=0; i<party.size(); i++) {													//Searches for a match on the turn and the players
					if(party.get(i).getTurnNumber()==turn) {										//Once a match is found, sets the index, changes state, and breaks
						if(party.get(i).onRest()) {					
							playerIndex=i;
							party.get(i).resetCardChoice();
							state=State.LONG_REST;
							break;
						}
						else {
							playerIndex=i;
							party.get(i).resetCardChoice();											//Resets card choice so it can be used in player choice when picking cards
							state=State.PLAYER_CHOICE;
							break;
						}
					}
				}
			}
		}
		//State: LONG_REST: Player takes long rest and picks a card to discard-------------------------------------------------------------------------------------------
		else if(state==State.LONG_REST) {
			
			boolean finished=false;																	//Indicates if the round is over
			party.get(playerIndex).takeLongRest(g, num);											//Draws discard pile and has player pick a card and sets long rest to false
			if(party.get(playerIndex).onRest()==false)												//If long rest is over, then the turn is over
				finished=true;
			
			if(finished) {
				turn++;																				//Moves to the next player
				state=State.ATTACK;																	//Next State: Attack
			}
		}
		//State: ENEMY_ATTACK: Goes through all the enemy procedure for attack for all enemies---------------------------------------------------------------------------
		else if(state==State.ENEMY_ATTACK) {

			enemyInfo.enemyMoveProcedure(enemyTurnIndex, party, g);
			
			List<Player> targets = new ArrayList<Player>();														//Resets the target list
			targets=enemyInfo.enemyAttackProcedure(enemyTurnIndex, party, g);							//Goes through the enemies and sets range / distance flags
			//once i have enemy pick target, need to change this code slightly.
			if(targets.size()>0) {
				targetID=targets.get(0).getID();													//[Temp] Picks first one on the list
				if(targets.get(0).hasRetaliate())
					UtilitiesAB.resolveRetalaite(enemyInfo.getEnemy(enemyTurnIndex), party.get(0));
				state=State.PLAYER_DEFENSE;															//Next State: Player Defense
			}else {
				enemyControlLogic();																//Next State: Attack, Enemy Attack, Round End
			}
		}
		//State: PLAYER_DEFENSE: Player chooses to discard or take the damage--------------------------------------------------------------------------------------------
		else if(state==State.PLAYER_DEFENSE) {
			
			g.drawString("Press "+setting.getDiscardKey()+" to discard card or "+setting.getHealKey()+" to take damage.", 5*setting.getGraphicsX(), setting.getGraphicsYBottom());
			int playerIndex = getTargetIndex();														//Sets target
			
			if((k==setting.getHealKey()) || (party.get(playerIndex).abilityCardsLeft()==0)) {						//If player doesn't discard (or can't), take damage
				int damage=enemyInfo.getAttack(enemyTurnIndex);										//Retrieves enemy's attack
				party.get(playerIndex).decreaseHealth(damage);										//Decreases player health
				if(party.get(playerIndex).getHealth()<=0) {											//If player's health is below 0, kill player
					party.remove(playerIndex);
				}
				if(party.size()==0)
					state=State.ROUND_END_DISCARD;													//If dead, Next State: Round End
				else
					enemyControlLogic();															//Next State: Attack, Enemy Attack, Round End
			}
			
			if(k==setting.getDiscardKey()) {
				state=State.PLAYER_DISCARD;															//Next State: Player Discard
			}
		}
		//State: PLAYER_DISCARD: Player discards a card instead of taking damage
		else if(state==State.PLAYER_DISCARD) {
			
			if(party.get(playerIndex).discardForHealth(num, g))										//Prints ability cards then waits until one is picked. 
				enemyControlLogic();																//Next State: Attack, Enemy Attack, Round End
		}
		//State: PLAYER_CHOICE: Player chooses their card
		else if(state==State.PLAYER_CHOICE) {

			int cardPick=party.get(playerIndex).pickPlayCard(num, g);								//Prints ability cards then waits for one to pick
			if(cardPick>=1 && cardPick<=8)
				state=State.PLAYER_ATTACK_LOGIC;													//Next State: Player Attack Logic
		}
		//State: PLAYER_ATTACK_LOGIC: Player picks card, then uses data for next state-----------------------------------------------------------------------------------
		else if(state==State.PLAYER_ATTACK_LOGIC) {
			card = party.get(playerIndex).playCard();												//Card Picked by the player
			room.setSelectionCoordinates(party.get(playerIndex).getCoordinate());					//Sets selection coordinates based on player
			g.drawString("Move "+card.getMove()+"     Attack: "+card.getAttack(), 5*setting.getGraphicsX(), setting.getGraphicsYBottom());
			
			UtilitiesAB.resolveCard(party.get(playerIndex), card, elements, room);
			//Next State: Player Move, Player attack, Back to Attack, or End Turn
			if(card.getMove()>0) {
				state=State.PLAYER_MOVE;
			//if there is range? that way it hits all targed attacks
			//}else if(card.getAttack()>0 || card.getTargetHeal()==true) {
			}else if(card.getRange()>0 || card.getAttack()>0) {
				state=State.PLAYER_ATTACK;
			}else {
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
		//State: PLAYER_MOVE: Player moves to a new hex or stays there---------------------------------------------------------------------------------------------------
		else if(state==State.PLAYER_MOVE) {
			boolean finished=false;
			if(party.get(playerIndex).canMove()){
				//Highlight tiles that players can move to
				Point playerPoint=party.get(playerIndex).getCoordinate();
				for(int r=1; r<=card.getMove(); r++) {
					room.drawRange(g, playerPoint, r, Color.BLUE);
				}
		
				//Moves selection highlight
				g.drawString("Press "+setting.getMoveKey()+" to move.", setting.getGraphicsX(), setting.getGraphicsYBottom());
				selection(g);
				
				//Player moves if the space is empty or the space hasn't changed
				if(k==setting.getMoveKey()) {
					if(room.isSpace(room.getSelectionCoordinates(), "P")) {
						finished=true;
					}
					else if(card.getFlying()) {
						//NEED TO HANDLE MULTIPLE PEOPLE OR THINGS ON A HEX
						room.movePlayer(party.get(playerIndex), room.getSelectionCoordinates());
						party.get(playerIndex).movePlayer(new Point(room.getSelectionCoordinates()));
						finished=true;
					}
					else if(card.getJump()) {
						if(room.isSpaceEmpty(room.getSelectionCoordinates())) {
							if(room.getQuickID(room.getSelectionCoordinates())=="Loot")
								room.loot(party.get(playerIndex), room.getSelectionCoordinates());
							room.movePlayer(party.get(playerIndex), room.getSelectionCoordinates());
							party.get(playerIndex).movePlayer(new Point(room.getSelectionCoordinates()));
							finished=true;
						}
	
					}else {
						//NEED TO ADD IN A CHECK FOR PATH IF JUMP IS NOT TRUE
						if(room.isSpaceEmpty(room.getSelectionCoordinates())) {
							room.movePlayer(party.get(playerIndex), room.getSelectionCoordinates());
							party.get(playerIndex).movePlayer(new Point(room.getSelectionCoordinates()));
							finished=true;
						}
					}
				}
			}
			else {
				finished=true;
			}
			//Next State: Player Attack, Attack Logic, Round End
			if(finished) {
			
				if(card.getRange()>0 || card.getAttack()>0) {
					//room.setSelectionCoordinates(new Point(room.getSelectionCoordinates()));		//Resets selection coordinates
					state=State.PLAYER_ATTACK;
				}else if(card.getData().getPush()>0) {
					//room.setSelectionCoordinates(new Point(room.getSelectionCoordinates()));		//Resets selection coordinates
					state=State.PLAYER_PUSH_SELECTION;
				}else {
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
		}
		//State: PLAYER_ATTACK: Creates target list and has player select a target to attack-----------------------------------------------------------------------------
		else if(state==State.PLAYER_ATTACK) {
			boolean finished=false;
			if(party.get(playerIndex).canAttack()) {
				g.drawString("Press "+setting.getTargetKey()+" to target.", setting.getGraphicsX(), setting.getGraphicsYBottom());
				
				//Creates target list of enemy coordinates
				List<Point> targets = new ArrayList<Point>();
				int cardRange=card.getRange();
				if(card.getRange()>=0) {
					if(card.getRange()==0)
						cardRange=1;
		
					if(card.getTargetHeal()) {
						for(int range=1; range<=cardRange; range++)
							targets = party.get(playerIndex).createTargetList(room.getBoard(), range, "P");
						targets.add(party.get(playerIndex).getCoordinate());
					}
					else {
						for(int range=1; range<=cardRange; range++)
							targets = party.get(playerIndex).createTargetList(room.getBoard(), range, "E");
					}
				}
				
				//If there are targets, highlight the targets and wait for selection
				if(targets.size()>0) {
	
					room.highlightTargets(targets, g);
					selection(g);
					
					//Space is used for selection of target
					if(k==setting.getTargetKey()) {
						
	
						if(card.getTargetHeal()) {
							if(room.isSpace(room.getSelectionCoordinates(), "P")) {							//If the space selected has an enemy
								if(targets.contains(room.getSelectionCoordinates())){						//If the target is in range
									String id = room.getID(room.getSelectionCoordinates());
									for(int i=0; i<party.size(); i++)
									{
										if(party.get(i).getID()==id) {
											party.get(i).heal(card.getHeal());
											finished=true;
										}
									}
								}
							}
						}
						else {
							if(room.isSpace(room.getSelectionCoordinates(), "E")) {							//If the space selected has an enemy
								if(targets.contains(room.getSelectionCoordinates())){						//If the target is in range
									//String id = room.getID(room.getSelectionCoordinates());					//Get id of the enemy
									//int damage=party.get(currentPlayer).getAttack(card);					//Get attack of the player
									//enemyInfo.playerAttack(id, damage);
									if(card.getMindControl()) {
										enemyControlled=enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates()));
										state=State.MINDCONTROL;
									}
									else {
										boolean adjacentBonus=card.getData().getAdjacentBonus();
							
										if(adjacentBonus)
											adjacentBonus=UtilitiesAB.targetAdjacentToAlly(enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates())), party, playerIndex, room);
									
										UtilitiesAB.resolveAttack(enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates())), party.get(playerIndex), card.getData(), room, adjacentBonus, elements);
										finished=true;	
									}
								}
							}
						}
					}
				}else {																					//If there are no enemies in target range
					finished=true;
				}
			}
			else {
				finished=true;
			}
			//Next State: Next card, Attack Logic, End Round
			if(finished) {
				if(card.getData().getPush()>0) {
					//room.setSelectionCoordinates(new Point(room.getSelectionCoordinates()));		//Resets selection coordinates
					state=State.PLAYER_PUSH_SELECTION;
				}else {
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
		}
		else if(state==State.PLAYER_PUSH_SELECTION) {
			boolean finished=false;
			//Creates target list of enemy coordinates
			List<Point> targets = new ArrayList<Point>();
			int cardRange=card.getRange();
			if(card.getRange()>=0) {
				if(card.getRange()==0)
					cardRange=1;
	
					for(int range=1; range<=cardRange; range++)
						targets = party.get(playerIndex).createTargetList(room.getBoard(), range, "E");
			}
			
			//If there are targets, highlight the targets and wait for selection
			if(targets.size()>0) {
				
				room.highlightTargets(targets, g);
				selection(g);
				//Space is used for selection of target
				if(k==setting.getTargetKey()) {
					if(room.isSpace(room.getSelectionCoordinates(), "E")) {							//If the space selected has an enemy
						if(targets.contains(room.getSelectionCoordinates())){						//If the target is in range
							oppPoint = new Point(UtilitiesAB.findOppHex(party.get(currentPlayer), enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates()))));
							enemyTarget=enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates()));
							state=State.PLAYER_PUSH;
						}
					}
				}
			}
			else {
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
		else if(state==State.PLAYER_PUSH) {
			boolean finished=false;
			Point pointToMove = null;
			int pointFlag = -1;
			g.setColor(Color.MAGENTA);
			room.drawHex(g, (int)oppPoint.getX(), (int)oppPoint.getY());

			//UtilitiesAB.drawArrows(g, new Point(party.get(currentPlayer).getCoordinate()), oppPoint);
			g.drawString("Press 1, 2, 3.", 5*setting.getGraphicsX(), setting.getGraphicsYBottom());
			pointFlag=UtilitiesAB.getPointFlag(party.get(currentPlayer).getCoordinate(), oppPoint);
			System.out.println(pointFlag+","+k);
			if(num>=1 && num<=3) {
				
				if(pointFlag==0) {
					if(num==1) {
						pointToMove=new Point((int)oppPoint.getX()+1, (int)oppPoint.getY());
						//oppPoint=new Point((int)pointToMove.getX()+1, (int)pointToMove.getY());
						finished=true;
					}
					else if(num==2) {
						pointToMove = new Point(oppPoint);
						finished=true;
					}
					else if(num==3) {
						pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()+1);
						finished=true;
					}
				}
				if(pointFlag==1) {
					if(num==1) {
						pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY());
						finished=true;
					}
					else if(num==2) {
						pointToMove=new Point(oppPoint);
						finished=true;
					}
					else if(num==3) {
						pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()+1);
						finished=true;
					}
				}
				else if(pointFlag==2) {
					if(num==1) {
						pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()-1);
						finished=true;
					}
					else if(num==2) {
						pointToMove=new Point(oppPoint);
						finished=true;
					}
					else if(num==3) {
						pointToMove=new Point((int)oppPoint.getX()+1, (int)oppPoint.getY());
						finished=true;
					}
				}
				else if(pointFlag==4) {
					if(num==1) {
						pointToMove=new Point((int)oppPoint.getX()-1, (int)oppPoint.getY()-1);
						finished=true;
					}
					else if(num==2) {
						pointToMove=new Point(oppPoint);
						finished=true;
					}
					else if(num==3) {
						pointToMove=new Point((int)oppPoint.getX()+1, (int)oppPoint.getY()+1);
						finished=true;
					}
				}
				else if(pointFlag==3 || pointFlag==5) {
					if(num==1) {
						pointToMove=new Point((int)oppPoint.getX()-1, (int)oppPoint.getY());
						finished=true;
					}
					else if(num==2) {
						pointToMove=new Point(oppPoint);
						finished=true;
					}
					else if(num==3) {
						pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()+1);
						finished=true;
					}
				}
			}
			
			if(finished) {
				card.getData().increasePush();
				room.moveEnemy(enemyTarget, pointToMove);
				System.out.println(oppPoint+" "+pointToMove);
				oppPoint = new Point(UtilitiesAB.findOppHex(oppPoint, pointToMove));
				System.out.println("oppPoint "+oppPoint);
				if(card.getData().getPush()>card.getData().getPushCount())
				{
					oppPoint=new Point(pointToMove);
					state=State.PLAYER_PUSH;
				}else {
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
		}
		else if(state==State.MINDCONTROL) {
			boolean finished=false;
			if(card.getData().getMindControData().getMove()>0)
				finished=mindControlMove(party.get(playerIndex), enemyControlled, g);
			
			if(card.getData().getMindControData().getAttack()>0)
				finished=mindControlAttack(party.get(playerIndex), enemyControlled, g);
			
			//Next State: Next card, Attack Logic, End Round
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
		//State: ROUND_END_DISCARD: Decides if scenario is over or another round should begin----------------------------------------------------------------------------
		else if(state==State.ROUND_END_DISCARD) {
			
			for(int i=0; i<party.size(); i++)
				party.get(i).endTurn();																//End of turn clean up for each player
			
			if(party.size()==0) 
				System.exit(1);																		//If party is dead, end program
			
			if(enemyInfo.getCount()==0)
				System.exit(1);																		//If all enemies are dead, end program
			
			currentPlayer=0;																		//Resets current player to use in round end rest state
			state=State.ROUND_END_REST;																//If game isn't immediately over, next state: round end rest
		}
		//State: ROUND_END_REST:Gives the player an option of a short rest-----------------------------------------------------------------------------------------------
		else if(state==State.ROUND_END_REST) { 	
			
			boolean finished=false;
			
			//If player has enough in the discard pile give option of short rest
			if(party.get(currentPlayer).discardPileSize()>1) {
				party.get(currentPlayer).shortRestInfo(g);											//Short rest shuffles back discard pile and randomly discards a card
				
				if(k=='y') {																		//Takes rest, moves on to next player or finishes round
					party.get(currentPlayer).takeShortRest();
					if((currentPlayer+1)!=party.size())	
						currentPlayer++;
					else
						finished=true;
				}
				
				if(k=='n') {																		//Doesn't take rest, moves on to next player or finishes round
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
					party.get(i).resetCards();														//Resets card variables for party
				turn=0;																				//Resets turn
				currentPlayer=0;																	//Resets currentPlayer
				state=State.CARD_SELECTION;															//Next State: Card Selection (Back to beginning)
			}
		}	
	}
	
	//[Temp] Should handle the party the same way i do with enemies with an object that holds all the info
	private void passDimensions() {		
		for(int i=0; i<party.size(); i++) {
			party.get(i).setDimensions(dimensions);
		}
	}
	
	//Parses key event into either a character or a number to be used
	private void parseKey(KeyEvent key) {
		k=Character.MIN_VALUE;
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ 														// handle null pointers for getKeyChar
			   
			}
		
		num = -1;
		try{
			num=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ 														// handle if it isn't a number character
			   
			}
	}

	//Selection function: draws current hex selected and moves selection
	private void selection(Graphics g) {
		room.drawSelectionHex(g);																	//Draws selection hex
		delayBy(100);																				//Makes it feel more smoother
		Point selectTemp=new Point(room.getSelectionCoordinates());									//[Rem] Probably more efficient to move iff it is changed	
		
		//Checks that new coordinate is inside the current room
		if(k==setting.up()) {
			if(selectTemp.y-1>=0) {
				selectTemp.y=selectTemp.y-1;
			}
			
		}
		if(k==setting.left()) {
			if(selectTemp.x-1>=0) {
				selectTemp.x=selectTemp.x-1;
			}
		}
		if(k==setting.down()) {
			if(selectTemp.y+1<room.getDimensions().getY()) {
				selectTemp.y=selectTemp.y+1;
			}
		}
		if(k==setting.right()) {
			if(selectTemp.x+1<room.getDimensions().getX()) {
				selectTemp.x=selectTemp.x+1;
			}
		}
		
		room.setSelectionCoordinates(selectTemp);													//Changes selection coordinate to new coordinate
	}
	
	//Controls the enemy logic at the end of the enemy round
	private void enemyControlLogic() {
		
		if(enemyTurnIndex==(enemyInfo.getCount()-1)) {												//If it has gone through all the enemies, go to next state
			if(turn==party.size())																	//If if it is on the last turn, End Round
				state=State.ROUND_END_DISCARD;														
			else {
				turn++;																				//End turn go back to attack logic state
				state=State.ATTACK;
			}	
		}
		else {
			enemyTurnIndex++;																		//Cycle through enemies and go to enemy attack state
			state=State.ENEMY_ATTACK;
		}
	}
	
	//Function that delays for a certain amount of seconds
	//Used to slow down the selection function
	private void delayBy(int time) {
		try {
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//Returns the playerIndex based on the targetID
	private int getTargetIndex() {
		for(int index=0; index<party.size(); index++) {												//Cycles through the party
			if(party.get(index).getID()==targetID) {												//When it matches ID, sets the player index and returns
				return index;
			}
		}
		return -1;																					//Only returns a -1 if there is an error
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
	
	//[Test] Function to print the enemy targets
	private void testPrintEnemyTargets(List<Player> targets) {
		//[Test]
		System.out.println("Targets for Enemy "+enemyTurnIndex+":");
		for(int i=0; i<targets.size(); i++) {
			System.out.println(i+": "+targets);
		}
		System.out.println("");
	}
	
	private boolean mindControlMove(Player player, Enemy enemy, Graphics g) {
		boolean finished=false;

		
		//Highlight tiles that players can move to
		Point enemyPoint=enemy.getCoordinate();
		SimpleCards cardData = card.getData().getMindControData();
		
		for(int r=1; r<=cardData.range; r++) {
			room.drawRange(g, enemyPoint, r, Color.BLUE);
		}

		//Moves selection highlight
		g.drawString("Press "+setting.getMoveKey()+"to move.", setting.getGraphicsX(), setting.getGraphicsYBottom());
		selection(g);
		
		//Player moves if the space is empty or the space hasn't changed
		if(k==setting.getMoveKey()) {
			if(room.isSpace(room.getSelectionCoordinates(), "E")) {
				return true;
			}
			else if(card.getFlying()) {
				//NEED TO HANDLE MULTIPLE PEOPLE OR THINGS ON A HEX
				room.moveEnemy(enemy, room.getSelectionCoordinates());
				enemy.moveEnemy(new Point(room.getSelectionCoordinates()));
				return true;
			}
			else if(card.getJump()) {
				if(room.isSpaceEmpty(room.getSelectionCoordinates())) {
					room.moveEnemy(enemy, room.getSelectionCoordinates());
					enemy.moveEnemy(new Point(room.getSelectionCoordinates()));
					return true;
				}

			}else {
				//NEED TO ADD IN A CHECK FOR PATH IF JUMP IS NOT TRUE
				if(room.isSpaceEmpty(room.getSelectionCoordinates())) {
					room.moveEnemy(enemy, room.getSelectionCoordinates());
					enemy.moveEnemy(new Point(room.getSelectionCoordinates()));
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	private boolean mindControlAttack(Player player, Enemy enemy, Graphics g) {
		boolean finished=false;
		
		g.drawString("Press "+setting.getTargetKey()+" to target.", setting.getGraphicsX(), setting.getGraphicsYBottom());
		
		//Creates target list of enemy coordinates
		List<Point> targets = new ArrayList<Point>();
		int cardRange=card.getData().getMindControData().getRange();
		if(cardRange>=0) {
			if(cardRange==0)
				cardRange=1;
	
				for(int range=1; range<=cardRange; range++)
					targets = enemy.createTargetList(room.getBoard(), range, "E");	
		}
		
		//If there are targets, highlight the targets and wait for selection
		if(targets.size()>0) {

			room.highlightTargets(targets, g);
			selection(g);
			
			//Space is used for selection of target
			if(k==setting.getTargetKey()) {
				if(room.isSpace(room.getSelectionCoordinates(), "E")) {							//If the space selected has an enemy
					if(targets.contains(room.getSelectionCoordinates())){						//If the target is in range
							UtilitiesAB.resolveAttackEnemyOnEnemy(enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates())), enemy, card.getData().getMindControData().getAttack());
							return true;
					}
				}
				return false;
			}
			else {
				return false;
			}
		}else {																					//If there are no enemies in target range
			return true;
		}
	}
	
}
