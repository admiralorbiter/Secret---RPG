package Gloomhaven.Scenario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Gloomhaven.City;
import Gloomhaven.GUIScenario;
import Gloomhaven.InfusionTable;
import Gloomhaven.Item;
import Gloomhaven.ItemLoader;
import Gloomhaven.Setting;
import Gloomhaven.Shop;
import Gloomhaven.TreasureLoader;
import Gloomhaven.UtilitiesAB;
import Gloomhaven.UtilitiesBoard;
import Gloomhaven.UtilitiesGeneral;
import Gloomhaven.UtilitiesLoot;
import Gloomhaven.UtilitiesTargeting;
import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.EnemyInfo;
import Gloomhaven.Characters.Player;
import Gloomhaven.Hex.Draw;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.UtilitiesHex;

public class Scenario {
	public enum State {
	    CARD_SELECTION,
	    INITIATIVE,
	    ATTACK,
	    ENEMY_ATTACK,
	    ENEMY_MOVE,
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
	    PLAYER_PUSH_SELECTION,
	    PLAYER_PUSH,
	    PLAYER_ITEM,
	    CREATE_INFUSION,
	    USE_ANY_INFUSION,
	    MINDCONTROL,
	    END;
	}
	
	private City gloomhaven;
	private ScenarioData data;
	private List<Player> party = new  ArrayList<Player>();
	
	private State state;
	private Graphics g;
	private KeyEvent key;
	private char k;
	private int num;
	
	private InfusionTable elements = new InfusionTable();
	
	private int currentPlayer=0;
	private int turnIndex=0;
	private int enemyTurnIndex=0;
	private String targetID;
	private int itemUsed;
	
	//private List<Enemy> enemies = new ArrayList<Enemy>();
	private EnemyInfo enemyInfo;
	
	private PlayerAbilityCard card = null;
	
	private Point selectionCoordinate;
	
	private Hex[][] board;
	
	private int direction=0;
	
	private Enemy enemyControlled;
	private Enemy enemyTarget;
	private Shop shop;
	
	public Scenario(int sceneID, List<Player> party, City gloomhaven, Shop shop) {
		this.shop=shop;
		this.gloomhaven=gloomhaven;
		this.party=party;
		data = ScenarioDataLoader.loadScenarioData(sceneID);
		enemyInfo = new EnemyInfo(data);
		board = ScenarioBoardLoader.loadBoardLayout(sceneID, data);
		
		for(int i=0; i<party.size(); i++)
			party.get(i).getStats().startScenario();
		
		//enemies=data.getEnemies(0);
		//TODO: Have the party pick thier starting positions
		party.get(0).setCoordinates(data.getStartingPosition());
		UtilitiesBoard.updatePositions(board, party, enemyInfo.getEnemies());
		state=State.CARD_SELECTION;
	}
	
	private void selection() {
		
		if(key!=null)
			if(key.getKeyCode()==KeyEvent.VK_SPACE)
				direction++;
		
		if(k==Setting.up) {
			if(selectionCoordinate.y-1>=0 && board[selectionCoordinate.x][selectionCoordinate.y-1]!=null) {
				selectionCoordinate.y=selectionCoordinate.y-1;
			}
			direction++;
		}
		if(k==Setting.left) {
			if(selectionCoordinate.x-1>=0 && board[selectionCoordinate.x-1][selectionCoordinate.y]!=null) {
				selectionCoordinate.x=selectionCoordinate.x-1;
			}
			direction--;
		}
		if(k==Setting.down) {
			if(selectionCoordinate.y+1<=data.getBoardSize().getY() && board[selectionCoordinate.x][selectionCoordinate.y+1]!=null) {
				selectionCoordinate.y=selectionCoordinate.y+1;
			}
			direction--;
		}
		if(k==Setting.right) {
			if(selectionCoordinate.x+1<=data.getBoardSize().getX() && board[selectionCoordinate.x+1][selectionCoordinate.y]!=null) {
				selectionCoordinate.x=selectionCoordinate.x+1;
			}
			direction++;
		}
		
		if(direction==6)
			direction=0;
		else if(direction==-1)
			direction=5;
	}
	
	private boolean movePlayer(Player player, Point ending) {	
	
		if(board[ending.x][ending.y].hasObstacle() || board[ending.x][ending.y].isHidden() || board[ending.x][ending.y].getQuickID().equals("E"))
			return false;
		
		if(board[(int) ending.getX()][(int) ending.getY()].hasLoot()) {
			UtilitiesLoot.loot(board, shop, player, ending);
		}
		
		if(board[(int) ending.getX()][(int) ending.getY()].hasDoor() &&(board[(int) ending.getX()][(int) ending.getY()].isDoorOpen()==false)) {
			//showRoom(board[(int) ending.getX()][(int) ending.getY()].getRoomID());
			ScenarioBoardLoader.showRoom(board, data.getId(), board[ending.x][ending.y].getRoomID());
			enemyInfo.updateEnemyList(data.getId(), board[ending.x][ending.y].getRoomID());
			System.out.println("Opening Door");
		}
		
		/*
		String quickID=board[(int) starting.getX()][(int) starting.getY()].getQuickID();
		String id=board[(int) starting.getX()][(int) starting.getY()].getID();
		
		board[(int) ending.getX()][(int) ending.getY()].setHex(quickID, id);
		board[(int) starting.getX()][(int) starting.getY()].reset();
		*/

		board[player.getCoordinates().x][player.getCoordinates().y].setQuickID(" ");
		board[player.getCoordinates().x][player.getCoordinates().y].setID(" ");
		player.setCoordinates(ending);
		
		return true;

	}
	
	public boolean playRound(KeyEvent key, Graphics g) {
		
		this.g=g;
		this.key=key;
		
		setupBeginningOfRound();
		graphics();
		
		switch(state) {
			case CARD_SELECTION:
				cardSelection();
				break;
			case INITIATIVE:
				initiative();
				break;
			case ATTACK:
				matchTurnWithEnemyOrPlayer();
				break;
			case LONG_REST:
				longRest();
				break;
			case ENEMY_ATTACK:
				enemyAttack();
				break;
			case PLAYER_DEFENSE:
				playerDefense();
				break;
			case PLAYER_DISCARD:
				if(party.get(currentPlayer).discardForHealth(num, g))										//Prints ability cards then waits until one is picked. 
					enemyControlLogic();
				break;
			case PLAYER_CHOICE:
				playerCardChoice();
				break;
			case PLAYER_ATTACK_LOGIC:
				playerAttackLogic();
				break;
			case PLAYER_ITEM:
				usePlayerItem();
				break;
			case PLAYER_MOVE:
				playerMove();
				break;
			case PLAYER_ATTACK:
				playerAttack();
				break;
			case PLAYER_PUSH_SELECTION:
				playerPushSelection();
				break;
			case PLAYER_PUSH:
				playerPush();
				break;
			case MINDCONTROL:
				mindcontrol();
				break;
			case CREATE_INFUSION:
				createInfusion();
				break;
			case USE_ANY_INFUSION:
				useAnyInfusion();
				break;
			case ROUND_END_DISCARD:
				roundEndDiscard();
				break;
			case ROUND_END_REST:
				roundEndRest();
				break;
		}
		
		return ScenarioEvaluateEnd.evaluateOne(enemyInfo.getEnemies(), data);
	}
	
	private void graphics() {
		switch(state) {
			case CARD_SELECTION:
				g.drawString("Picking Cards for your turn.", Setting.graphicsXLeft, Setting.graphicsYTop+Setting.rowSpacing*2);
				//Allows the user to take a long rest if they have enough in the discard pile 
				if(party.get(currentPlayer).discardPileSize()>1)	
					g.drawString("Take a long rest with "+Setting.restKey, Setting.graphicsXLeft, Setting.graphicsYTop+Setting.rowSpacing*3);
				break;
			case INITIATIVE:
				//List of all the initiatives for the round
				g.drawString("Initiatives:", Setting.graphicsXLeft, Setting.graphicsYBottom);											
				g.drawString("Enemy: Note - Need to fix enemyInfo", Setting.graphicsXLeft, Setting.graphicsYBottom+Setting.rowSpacing);								
				for(int i=0; i<party.size();  i++) {
					g.drawString("Player "+party.get(i).getID()+"      "+String.valueOf(party.get(i).getInitiative()), Setting.graphicsXLeft, Setting.graphicsYBottom+Setting.rowSpacing*(i+2));
				}
				break;
			case PLAYER_DEFENSE:
				g.drawString("Press "+Setting.discardKey+" to discard card or "+Setting.healKey+" to take damage.", Setting.graphicsXLeft, Setting.graphicsYBottom);
				break;
			case PLAYER_ATTACK_LOGIC:
				g.drawString("Move "+UsePlayerAbilityCard.getMove(card)+"     Attack: "+UsePlayerAbilityCard.getAttack(card)+"  (Loc: Scenario -Player Attack Logic)", Setting.graphicsXLeft, Setting.graphicsYBottom);
				break;
			case PLAYER_MOVE:
				g.drawString("Press "+Setting.moveKey+" to move.", Setting.graphicsXLeft, Setting.graphicsYBottom);
				break;
			case PLAYER_ATTACK:
				g.drawString("Press "+Setting.targetKey+" to target.", Setting.graphicsXLeft, Setting.graphicsYBottom);
				break;
			case PLAYER_PUSH:
				g.drawString("Press 1, 2, 3.", Setting.graphicsXLeft, Setting.graphicsYBottom);
				break;
			case MINDCONTROL:
				g.drawString("Press "+Setting.moveKey+"to move.", Setting.graphicsXLeft, Setting.graphicsYBottom);
				g.drawString("Press "+Setting.targetKey+" to target.", Setting.graphicsXLeft, Setting.graphicsYBottom+Setting.rowSpacing);
				break;
			case CREATE_INFUSION:
				g.drawString("Pick an infusion", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*0);
				g.drawString("1 Fire", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*1);
				g.drawString("2 Ice", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*2);
				g.drawString("3 Air", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*3);
				g.drawString("4 Earth", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*4);
				g.drawString("5 Light", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*5);
				g.drawString("6 Dark", Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing*6);
				break;

		}
	}
	
	private void setupBeginningOfRound() {
		//Title
		g.drawString("State of Scenario      Prosperity: "+gloomhaven.getProspLevel()+"Rep: "+gloomhaven.getReputationLevel(), Setting.graphicsXLeft, Setting.graphicsYTop);
		g.drawString(state.toString(), Setting.graphicsXLeft, Setting.graphicsYTop+Setting.rowSpacing);
		
		
		elements.graphicsDrawTable(g);
		Draw.rectangleBoardSideways(g, board, data.getBoardSize());
		Draw.drawParty(g, party);
		enemyInfo.drawEnemies(g);
		enemyInfo.update(board, data);
		GUIScenario.EntityTable(g, party, enemyInfo.getEnemies());
		
		party.get(0).graphicsPlayerInfo(g);
		party.get(0).graphicsDrawCardsInPlay(g);
		
		//Parse the keyboard event into character and number (sometimes both)
		k=UtilitiesGeneral.parseKeyCharacter(key);
		num=UtilitiesGeneral.parseKeyNum(key);
	}
	
	private void cardSelection() {


		//Draw's the player's available ability cards
		party.get(currentPlayer).drawAbilityCards(g);			
		
		//Player enters long rest or picks ability cards
		if((k==Setting.restKey) && (party.get(currentPlayer).discardPileSize()>1))
			party.get(currentPlayer).setLongRest();
		else
			party.get(currentPlayer).pickAbilityCards(key, num, g);
		
		if(party.get(currentPlayer).cardsLocked()) {
			if((currentPlayer+1)!=party.size())
				currentPlayer++;
			else {
				enemyInfo.initiationRound();
				currentPlayer=0;
				state=State.INITIATIVE;
			}
		}
	}
	
	private void initiative() {
		enemyInfo.initiationRound();
		//int enemyInit=enemyInfo.getInitiative();
		party.sort(Comparator.comparingInt(Player::getInitiative));								//Order just the players based on initiative
		
		//Goes through the party and enemy and gives a turn number
		//The party is in order, so i just have to fit the enemy in
		//[Rem] Doesn't work if the players have the same init	
		/*
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
		*/
		
		UtilitiesGeneral.setTurnNumbers(party, enemyInfo);
		
		currentPlayer=0;
		turnIndex=0;
		state=State.ATTACK;
	}
	
	private void matchTurnWithEnemyOrPlayer() {
		if(enemyInfo.getTurnNumber()==turnIndex) {													//If enemy turns, do enemy attack
			enemyTurnIndex=0;																	//Resets enemy turn index
			state=State.ENEMY_ATTACK;															//Goes to STATE:ENEMY_ATTACK
		}
		else {

			//Next State: Long Rest or Player Choice
			for(int i=0; i<party.size(); i++) {													//Searches for a match on the turn and the players
				if(party.get(i).getTurnNumber()==turnIndex) {										//Once a match is found, sets the index, changes state, and breaks
					if(party.get(i).onRest()) {					
						currentPlayer=i;
						party.get(i).resetCardChoice();
						state=State.LONG_REST;
						break;
					}
					else {
						currentPlayer=i;
						party.get(i).resetCardChoice();											//Resets card choice so it can be used in player choice when picking cards
						state=State.PLAYER_CHOICE;
						break;
					}
				}
			}
		}
	}
	
	private void longRest() {
		boolean finished=false;																	//Indicates if the round is over
		party.get(currentPlayer).takeLongRest(g, num);											//Draws discard pile and has player pick a card and sets long rest to false
		if(party.get(currentPlayer).onRest()==false)												//If long rest is over, then the turn is over
			finished=true;
		
		if(finished) {
			turnIndex++;																				//Moves to the next player
			state=State.ATTACK;																	//Next State: Attack
		}
	}
	
	private void enemyAttack() {
		enemyInfo.drawAbilityCard(g);
		enemyInfo.enemyMoveProcedure(enemyTurnIndex, party, g);
		
		UtilitiesBoard.updatePositions(board, party, enemyInfo.getEnemies());
		
		List<Player> targets = new ArrayList<Player>();
		if(enemyInfo.getEnemies().size()!=0)
			targets = UtilitiesTargeting.createTargetListPlayer(board, enemyInfo.getEnemy(enemyTurnIndex).getBaseStats().getRange(), enemyInfo.getEnemy(enemyTurnIndex).getCubeCoordiantes(Setting.flatlayout), data.getBoardSize(), party);
		//targets = enemyInfo.createTargetListForEnemy(enemyTurnIndex, party, g);
		
		if(targets.size()>0) {
			
			int min=100;
			int targetIndex=-1;
			
			for(int i=0; i<targets.size(); i++) {
				if(UtilitiesAB.distance(enemyInfo.getEnemy(enemyTurnIndex).getCoordinates(), party.get(currentPlayer).getCoordinates())<min) {
					targetIndex=i;
					min=UtilitiesAB.distance(enemyInfo.getEnemy(enemyTurnIndex).getCoordinates(), party.get(currentPlayer).getCoordinates());
				}else if(UtilitiesAB.distance(enemyInfo.getEnemy(enemyTurnIndex).getCoordinates(), party.get(currentPlayer).getCoordinates())==min) {
					if(targets.get(targetIndex).getInitiative()>targets.get(i).getInitiative()) {
						targetIndex=i;
					}
				}
			}
			
			targetID=targets.get(targetIndex).getID();													//[Temp] Picks first one on the list
		
			if(targets.get(targetIndex).hasRetaliate())
				System.out.println("Scenario.java Loc 276: Reminder that if the player attacks a target with retalite it doesn't resolve anymore");
			state=State.PLAYER_DEFENSE;															//Next State: Player Defense
		}else {
			enemyControlLogic();																//Next State: Attack, Enemy Attack, Round End
		}
	}
	
	private void enemyControlLogic() {
		
		if(enemyTurnIndex==(enemyInfo.getCount()-1)) {												//If it has gone through all the enemies, go to next state
			if(turnIndex==party.size())																	//If if it is on the last turn, End Round
				state=State.ROUND_END_DISCARD;														
			else {
				turnIndex++;																				//End turn go back to attack logic state
				state=State.ATTACK;
			}	
		}
		else {
			enemyTurnIndex++;																		//Cycle through enemies and go to enemy attack state
			state=State.ENEMY_ATTACK;
		}
	}
	
	private void playerDefense() {
		enemyInfo.drawAbilityCard(g);
		int playerIndex = getTargetIndex();
		
		if((k==Setting.healKey)||(party.get(playerIndex).abilityCardsLeft()==0)) {
			int damage = 2;//enemyInfo.getAttack(enemyTurnIndex);
			party.get(playerIndex).takeDamage(damage);
			if(party.get(playerIndex).getCharacterData().getHealth()<=0)
				party.remove(playerIndex);
			
			if(party.size()==0)
				state=State.ROUND_END_DISCARD;
			else
				enemyControlLogic();
		}
		
		if(k==Setting.discardKey)
			state=State.PLAYER_DISCARD;
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
	
	private void playerCardChoice() {
		int cardPick=party.get(currentPlayer).pickPlayCard(key, num, k, g);								//Prints ability cards then waits for one to pick
		if(cardPick>=1 && cardPick<=8) {
			card = party.get(currentPlayer).playCard();
			state=State.PLAYER_ATTACK_LOGIC;													//Next State: Player Attack Logic
		}
		if(cardPick>=100) {
			itemUsed=cardPick-100;
			state=State.PLAYER_ITEM;
		}
	}
	
	private void playerAttackLogic() {
		selectionCoordinate=new Point(party.get(currentPlayer).getCoordinates());
		
		UtilitiesAB.resolveCard(party.get(currentPlayer), card, elements, board, data, shop);
		
		if(UsePlayerAbilityCard.getMove(card)>0)
			state=State.PLAYER_MOVE;
		else if(UsePlayerAbilityCard.getCardData(card).getConsumeElementalFlag())
			state=State.USE_ANY_INFUSION;
		else if(UsePlayerAbilityCard.getRange(card)>0 || UsePlayerAbilityCard.getAttack(card)>0)
			state=State.PLAYER_ATTACK;
		else {
			if(party.get(currentPlayer).getCardChoice()==false) {
				state=State.PLAYER_CHOICE;
			}else {
				//if turn is over
				if(turnIndex==party.size())
					state=State.ROUND_END_DISCARD;
				else {
					turnIndex++;
					state=State.ATTACK;
				}
			}
		}
	}
	
	private void usePlayerItem() {
		List<Item> usableItems = ItemLoader.onTurn(party.get(currentPlayer).getItems());
		
		if(usableItems.get(itemUsed).getConsumed())
			ItemLoader.consumeItem(party.get(currentPlayer), usableItems.get(itemUsed));
		else if(usableItems.get(itemUsed).getSpent())
			ItemLoader.spendItem(party.get(currentPlayer), usableItems.get(itemUsed));
		
		if(party.get(currentPlayer).getCreateAnyElement())
			state=State.CREATE_INFUSION;
		else
			state=State.PLAYER_CHOICE;
	}
	
	private void playerMove() {
		boolean finished=false;
		
		if(party.get(currentPlayer).canMove()) {
			g.setColor(Color.red);
			selection();
			Draw.range(g, party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), UsePlayerAbilityCard.getMove(card));
			
			g.setColor(Color.cyan);
			Draw.drawHex(g, UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate));
			
			if(k==Setting.moveKey) {
				if(UtilitiesHex.distance(UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate), party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout))<UsePlayerAbilityCard.getMove(card)) {
				
					if(board[selectionCoordinate.x][selectionCoordinate.y].getQuickID().equals("P"))
						finished=true;
					else if(UsePlayerAbilityCard.hasFlying(card)) {
						finished=movePlayer(party.get(currentPlayer), selectionCoordinate);
					}
					else if(UsePlayerAbilityCard.hasJump(card)) {
						if(board[selectionCoordinate.x][selectionCoordinate.y].isSpaceEmpty()) {
							finished=movePlayer(party.get(currentPlayer), selectionCoordinate);
						}
					}
					else {
						if(board[selectionCoordinate.x][selectionCoordinate.y].isSpaceEmpty()) {
							finished=movePlayer(party.get(currentPlayer), selectionCoordinate);
						}
					}
				}

			}
		}else {
			finished=true;
		}
		
		//Next State: Player Attack, Attack Logic, Round End
		if(finished) {
			UtilitiesBoard.updatePositions(board, party, enemyInfo.getEnemies());
			
			if(UsePlayerAbilityCard.getCardData(card).getConsumeElementalFlag()) {
				state=State.USE_ANY_INFUSION;
			}
			else if(UsePlayerAbilityCard.getRange(card)>0 || UsePlayerAbilityCard.getAttack(card)>0) {
				state=State.PLAYER_ATTACK;
			}else if(UsePlayerAbilityCard.getCardData(card).getEffects().getPush()>0) {
				state=State.PLAYER_PUSH_SELECTION;
			}else {
				if(party.get(currentPlayer).getCardChoice()==false) {
					state=State.PLAYER_CHOICE;
				}else {
					//if turn is over
					if(turnIndex==party.size())
						state=State.ROUND_END_DISCARD;
					else {
						turnIndex++;
						state=State.ATTACK;
					}
				}
			}
		}
	}
	
	private void playerAttack() {
		boolean finished=false;
		UtilitiesBoard.updatePositions(board, party, enemyInfo.getEnemies());
		if(party.get(currentPlayer).canAttack()) {
			
			//Creates target list of enemy coordinates
			List<Point> targets = new ArrayList<Point>();
			
			int cardRange=UsePlayerAbilityCard.getRange(card);
			
			if(UsePlayerAbilityCard.getRange(card)>=0) {
				if(UsePlayerAbilityCard.getRange(card)==0)
					cardRange=1;
	
				if(UsePlayerAbilityCard.hasTargetHeal(card)) {
					for(int range=1; range<=cardRange; range++)
						targets=UtilitiesTargeting.createTargetList(board, range, party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), "P", data.getBoardSize());
					targets.add(party.get(currentPlayer).getCoordinates());
				}
				else {
					for(int range=1; range<=cardRange; range++) {
						targets=UtilitiesTargeting.createTargetList(board, range, party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), "E", data.getBoardSize());
					}
				}
			}
			
			if(targets.size()>0) {
				UtilitiesTargeting.highlightTargets(targets, g);
				
				selection();
				g.setColor(Color.cyan);
						
				if(UsePlayerAbilityCard.getRange(card)==0) {
					UtilitiesTargeting.drawAttack(g, party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction, UsePlayerAbilityCard.getCardData(card).getData().getTarget().getTargets());
				}
				else {
					Draw.drawHex(g, selectionCoordinate);
				}
				
				if(k==Setting.targetKey) {
				
					if(UsePlayerAbilityCard.hasTargetHeal(card)) {
						if(board[selectionCoordinate.x][selectionCoordinate.y].getQuickID().equals("P")) {
							if(targets.contains(selectionCoordinate)) {
								for(int i=0; i<party.size(); i++) {
									if(party.get(i).getCoordinates()==selectionCoordinate) {
										party.get(i).heal(UsePlayerAbilityCard.getHeal(card));
										finished=true;
									}
								}
							}
						}
					}
					else if(UsePlayerAbilityCard.getRange(card)==0) {
						int num = UsePlayerAbilityCard.getCardData(card).getData().getTarget().getTargets();
						HexCoordinate hex=UtilitiesHex.neighbor(party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction);
						selectionCoordinate=new Point(UtilitiesHex.getOffset(Setting.flatlayout, hex));
						
						finished=attackProcedure(new Point(selectionCoordinate), targets);
						
						if(num>=2) {
							hex=UtilitiesHex.neighbor(party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction+1);
							selectionCoordinate=new Point(UtilitiesHex.getOffset(Setting.flatlayout, hex));
							boolean temp=attackProcedure(new Point(selectionCoordinate), targets);
							if(temp)
								finished=true;
						}
						if(num>=3) {
							hex=UtilitiesHex.neighbor(party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction-1);
							selectionCoordinate=new Point(UtilitiesHex.getOffset(Setting.flatlayout, hex));
							boolean temp=attackProcedure(new Point(selectionCoordinate), targets);
							if(temp)
								finished=true;
						}
						
						if(num>=4) {
							hex=UtilitiesHex.neighbor(party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction+2);
							selectionCoordinate=new Point(UtilitiesHex.getOffset(Setting.flatlayout, hex));
							attackProcedure(new Point(selectionCoordinate), targets);
						}
						
						if(num>=5) {
							hex=UtilitiesHex.neighbor(party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction-2);
							selectionCoordinate=new Point(UtilitiesHex.getOffset(Setting.flatlayout, hex));
							boolean temp=attackProcedure(new Point(selectionCoordinate), targets);
							if(temp)
								finished=true;
						}
						
						if(num>=6) {
							hex=UtilitiesHex.neighbor(party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), direction+3);
							selectionCoordinate=new Point(UtilitiesHex.getOffset(Setting.flatlayout, hex));
							boolean temp=attackProcedure(new Point(selectionCoordinate), targets);
							if(temp)
								finished=true;
						}
					}
					else {
					
						if(UsePlayerAbilityCard.hasMindControl(card)) {
							enemyControlled=enemyInfo.getEnemy(selectionCoordinate);
							state=state.MINDCONTROL;
						}else {
							finished=attackProcedure(new Point(selectionCoordinate), targets);
							
							if(UsePlayerAbilityCard.getCardData(card).getData().getTarget().getTargets()>1 && card.getAbilityCardCount()!=UsePlayerAbilityCard.getCardData(card).getData().getTarget().getTargets()){
								System.out.println("1:  "+targets);
								targets.remove(targets.indexOf(selectionCoordinate));
								System.out.println("2:  "+targets);
								finished=false;
							}else {
								for(int i=0; i<party.get(currentPlayer).getCounterTriggers().size(); i++) {
									if(party.get(currentPlayer).getCounterTriggers().get(i).getTriggerFlag().equals("forEachTargeted"))
										System.out.println("Scenario.java Loc 648: Reminder that there needs to be a resolution for forEachTargeted flag");
								}
								finished=true;	
							}
						}
					}
				}
			}else {
				finished=true;
			}
		}else {
			finished=true;
		}
		
		if(finished) {
			selectionCoordinate=new Point(party.get(currentPlayer).getCoordinates());
			
			if(UsePlayerAbilityCard.getCardData(card).getEffects().getPush()>0) {
				state=State.PLAYER_PUSH_SELECTION;
			}else {
				if(party.get(currentPlayer).getCardChoice()==false) {
					state=State.PLAYER_CHOICE;
				}else {
					//if turn is over
					if(turnIndex==party.size())
						state=State.ROUND_END_DISCARD;
					else {
						turnIndex++;
						state=State.ATTACK;
					}
				}
			}
		}
	}
	
	private boolean attackProcedure(Point selection, List<Point> targets) {
		if(board[selection.x][selection.y].getQuickID()=="E") {
			if(targets.contains(selection)) {
				card.increaseAbilityCardCounter();
				boolean adjacentBonus=false;
				UtilitiesAB.resolveAttack(enemyInfo.getEnemy(selection), party.get(currentPlayer), card, board, adjacentBonus, elements, data);
				return true;
			}
		}
		return false;
	}
	
	private void playerPushSelection() {
		boolean finished=false;
		
		//Creates target list of enemy coordinates
		List<Point> targets = new ArrayList<Point>();
		int cardRange=UsePlayerAbilityCard.getRange(card);
		
		if(UsePlayerAbilityCard.getRange(card)>=0) {
			if(UsePlayerAbilityCard.getRange(card)==0)
				cardRange=1;

				for(int range=1; range<=cardRange; range++)
					targets=UtilitiesTargeting.createTargetList(board, range, party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout), "E", data.getBoardSize());
		}
		
		//If there are targets, highlight the targets and wait for selection
		if(targets.size()>0) {
			
			UtilitiesTargeting.highlightTargets(targets, g);
			
			selection();
			g.setColor(Color.cyan);
			//if(UsePlayerAbilityCard.getCardData(card).getEffects().getRange()!=0)
			Draw.drawHex(g, UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate));

			//Space is used for selection of target
			if(k==Setting.targetKey) {
				if(board[selectionCoordinate.x][selectionCoordinate.y].getQuickID().equals("E")) {
					if(targets.contains(selectionCoordinate)) {

						//oppPoint = new Point(UtilitiesTargeting.findOppisiteHex(party.get(currentPlayer).getCoordinates(), enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates())).getCoordinates()));
						enemyTarget=enemyInfo.getEnemy(selectionCoordinate);
						direction=UtilitiesHex.getDirection(enemyTarget.getCubeCoordiantes(Setting.flatlayout), party.get(currentPlayer).getCubeCoordiantes(Setting.flatlayout));
						//tempHoldVar=new Point(enemyInfo.getEnemyFromID(room.getID(room.getSelectionCoordinates())).getCoordinates());
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
				if(turnIndex==party.size())
					state=State.ROUND_END_DISCARD;
				else {
					turnIndex++;
					state=State.ATTACK;
				}
			}
		}
	}
	
	private void playerPush() {
		boolean finished=false;
		
		HexCoordinate pushPoint = UtilitiesHex.neighbor(enemyTarget.getCubeCoordiantes(Setting.flatlayout), direction);
		g.setColor(Color.cyan);
		Draw.drawHex(g, pushPoint);
		
		if(num>=1 && num<=3) {
			if(num==1) {
				pushPoint = UtilitiesHex.neighbor(enemyTarget.getCubeCoordiantes(Setting.flatlayout), direction+1);
				enemyTarget.move(pushPoint, data.getBoardSize());
			}else if(num==2) {
				enemyTarget.move(pushPoint, data.getBoardSize());
			}
			else {
				pushPoint = UtilitiesHex.neighbor(enemyTarget.getCubeCoordiantes(Setting.flatlayout), direction-1);
				enemyTarget.move(pushPoint, data.getBoardSize());
			}
			finished=true;
		}
		
		if(finished) {
			UtilitiesBoard.updatePositions(board, party, enemyInfo.getEnemies());
			card.increaseAbilityCardCounter();
			/*
			 * I know the +1 below doesn't make sense but because most of the cards attack before pushing, it starts with 1. I think* they all
			 * attack first. If they don't, I will need to just clear the abilitycounter when I pick a push target
			 */
			if((UsePlayerAbilityCard.getCardData(card).getEffects().getPush()+1)>card.getAbilityCardCount())
			{
				//oppPoint=new Point(pointToMove);
				state=State.PLAYER_PUSH;
			}else {
				if(party.get(currentPlayer).getCardChoice()==false) {
					state=State.PLAYER_CHOICE;
				}else {
					//if turn is over
					if(turnIndex==party.size())
						state=State.ROUND_END_DISCARD;
					else {
						turnIndex++;
						state=State.ATTACK;
					}
				}
			}
		}
	}
	
	private void mindcontrol() {
		boolean finished=false;
		
		if(UsePlayerAbilityCard.getCardData(card).getData().getMove()>0)
			finished=mindControlMove(party.get(currentPlayer), enemyControlled);
		
		if(UsePlayerAbilityCard.getCardData(card).getData().getAttack()>0)
			finished=mindControlAttack(party.get(currentPlayer), enemyControlled);
		
		//Next State: Next card, Attack Logic, End Round
		if(finished) {
			UtilitiesBoard.updatePositions(board, party, enemyInfo.getEnemies());
			
			if(party.get(currentPlayer).getCardChoice()==false) {
				state=State.PLAYER_CHOICE;
			}else {
				//if turn is over
				if(turnIndex==party.size())
					state=State.ROUND_END_DISCARD;
				else {
					turnIndex++;
					state=State.ATTACK;
				}
			}
		}
	}
	
	private boolean mindControlMove(Player player, Enemy enemy) {
		boolean finished=false;
		
		//Highlight tiles that players can move to
		HexCoordinate enemyPoint=enemy.getCubeCoordiantes(Setting.flatlayout);
		CardDataObject cardData = UsePlayerAbilityCard.getCardData(card);
		
		for(int r=1; r<=cardData.getData().getRange(); r++) {
			Draw.range(g, enemyPoint, r);
		}
		
		selection();
		g.setColor(Color.cyan);
		Draw.drawHex(g, UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate));
				
		if(k==Setting.moveKey) {
			if(UtilitiesHex.distance(enemyPoint, UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate))<=cardData.getData().getRange()) {
				if(board[selectionCoordinate.x][selectionCoordinate.y].getQuickID().equals("E")) {
					return true;
				}
				else if(UsePlayerAbilityCard.hasFlying(card)) {
					//NEED TO HANDLE MULTIPLE PEOPLE OR THINGS ON A HEX
					enemy.move(selectionCoordinate, data.getBoardSize());
					return true;
				}
				else if(UsePlayerAbilityCard.hasJump(card)) {
					if(board[selectionCoordinate.x][selectionCoordinate.y].isSpaceEmpty()) {
						enemy.move(selectionCoordinate, data.getBoardSize());
						return true;
					}
				}
				else {
					//NEED TO ADD IN A CHECK FOR PATH IF JUMP IS NOT TRUE
					if(board[selectionCoordinate.x][selectionCoordinate.y].isSpaceEmpty()) {
						enemy.move(selectionCoordinate, data.getBoardSize());
						return true;
					}
				}
				
			}
		}
		return false;		
	}
	
	private boolean mindControlAttack(Player player, Enemy enemy) {
		boolean finished=false;
		
		//Creates target list of enemy coordinates
		List<Point> targets = new ArrayList<Point>();
		int cardRange=UsePlayerAbilityCard.getCardData(card).getData().getRange();
		if(cardRange>=0) {
			if(cardRange==0)
				cardRange=1;
	
				for(int range=1; range<=cardRange; range++)
					targets=UtilitiesTargeting.createTargetList(board, range, enemy.getCubeCoordiantes(Setting.flatlayout), "E", data.getBoardSize());
		}
		
		if(targets.size()>0) {
			UtilitiesTargeting.highlightTargets(targets, g);
			selection();
			g.setColor(Color.cyan);
			Draw.drawHex(g, UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate));
			
			if(k==Setting.targetKey) {
				if(board[selectionCoordinate.x][selectionCoordinate.y].getQuickID().equals("E")) {
					if(targets.contains(selectionCoordinate)) {
						UtilitiesAB.resolveAttackEnemyOnEnemy(enemyInfo.getEnemy(selectionCoordinate), enemy, UsePlayerAbilityCard.getCardData(card).getData().getAttack());
						return true;
					}
				}
			}
			return false;
		}else {
			return true;
		}
	}
	
	private void createInfusion() {
		if(num>=1 && num<=6) {
			String element="";
			switch(num) {
				case 1: element="Fire";
				break;
				case 2: element="Ice";
				break;
				case 3: element="Air";
				break;
				case 4: element="Earth";
				break;
				case 5: element="Light";
				break;
				case 6: element="Dark";
				break;
			}
			elements.infuse(element);
			
			party.get(currentPlayer).setCreateAnyElement(false);
			
			state=State.PLAYER_CHOICE;
		}
	}
	
	private void useAnyInfusion() {
		if(elements.consumeAny(g, num)) {
			selectionCoordinate=new Point(party.get(currentPlayer).getCoordinates());
			if(UsePlayerAbilityCard.getRange(card)>0 || UsePlayerAbilityCard.getAttack(card)>0) {
				state=State.PLAYER_ATTACK;
			}else if(UsePlayerAbilityCard.getCardData(card).getEffects().getPush()>0) {
				
				state=State.PLAYER_PUSH_SELECTION;
			}else {
				if(party.get(currentPlayer).getCardChoice()==false) {
					state=State.PLAYER_CHOICE;
				}else {
					//if turn is over
					if(turnIndex==party.size())
						state=State.ROUND_END_DISCARD;
					else {
						turnIndex++;
						state=State.ATTACK;
					}
				}
			}
		}
	}
	
	private void roundEndDiscard() {
		elements.endOfRound();
		
		for(int i=0; i<party.size(); i++)
			party.get(i).endTurn();																//End of turn clean up for each player
		
		if(party.size()==0) 
			System.exit(1);																		//If party is dead, end program
		
		if(enemyInfo.getCount()==0)
			System.exit(1);																		//If all enemies are dead, end program
		
		currentPlayer=0;																		//Resets current player to use in round end rest state
		state=State.ROUND_END_REST;	
	}
	
	private void roundEndRest() {
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
				party.get(i).reset();														//Resets card variables for party
			turnIndex=0;																				//Resets turn
			currentPlayer=0;																	//Resets currentPlayer
			state=State.CARD_SELECTION;															//Next State: Card Selection (Back to beginning)
		}
	}
}