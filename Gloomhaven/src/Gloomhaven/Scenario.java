package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.EnemyInfo;
import Gloomhaven.Characters.Player;

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
	
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private EnemyInfo enemyInfo;
	
	private PlayerAbilityCard card = null;
	
	private Point selectionCoordinate;
	
	private Hex[][] board;
	
	public Scenario(int sceneID, List<Player> party, City gloomhaven) {
		this.gloomhaven=gloomhaven;
		this.party=party;
		data = ScenarioDataLoader.loadScenarioData(sceneID);
		enemyInfo = new EnemyInfo(data);
		board = new Hex[data.getBoardSize().x][data.getBoardSize().y];
		
		for(int x=0; x<data.getBoardSize().x; x++) {
			for(int y=0; y<data.getBoardSize().y; y++) {
				board[x][y] = new Hex(x, y, Setting.flatlayout);
			}
		}
		
		state=State.CARD_SELECTION;
	}
	
	private void selection() {
		
		if(k==Setting.up) {
			if(selectionCoordinate.y-1>=0) {
				selectionCoordinate.y=selectionCoordinate.y-1;
			}
			
		}
		if(k==Setting.left) {
			if(selectionCoordinate.x-1>=0) {
				selectionCoordinate.x=selectionCoordinate.x-1;
			}
		}
		if(k==Setting.down) {
			if(selectionCoordinate.y+1<=data.getBoardSize().getY()) {
				selectionCoordinate.y=selectionCoordinate.y+1;
			}
		}
		if(k==Setting.right) {
			if(selectionCoordinate.x+1<=data.getBoardSize().getX()) {
				selectionCoordinate.x=selectionCoordinate.x+1;
			}
		}
	}
	
	private void movePlayer(Player player, Point ending) {	
	
		if(board[(int) ending.getX()][(int) ending.getY()].hasLoot()) {
			loot(player, ending);
		}
		
		if(board[(int) ending.getX()][(int) ending.getY()].hasDoor() &&(board[(int) ending.getX()][(int) ending.getY()].isDoorOpen()==false)) {
			//showRoom(board[(int) ending.getX()][(int) ending.getY()].getRoomID());
		}
		
		/*
		String quickID=board[(int) starting.getX()][(int) starting.getY()].getQuickID();
		String id=board[(int) starting.getX()][(int) starting.getY()].getID();
		
		board[(int) ending.getX()][(int) ending.getY()].setHex(quickID, id);
		board[(int) starting.getX()][(int) starting.getY()].reset();
		*/
		
		player.setCoordinates(ending);
		
		board[(int) ending.getX()][(int) ending.getY()].setSpaceEmpty(true);
	}
	
	private void loot(Player player, Point loot) {
		
		player.addLoot(board[(int) loot.getX()][(int) loot.getY()]);
		board[(int) loot.getX()][(int) loot.getY()].removeLoot();
	}
	
	public void playRound(KeyEvent key, Graphics g) {
		
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
		}
		
		/*
		else if(state==State.PLAYER_ITEM) {
			usePlayerItem();
		}
		else if(state==State.PLAYER_MOVE) {
			playerMove();
		}
		else if(state==State.PLAYER_ATTACK) {
			playerAttack();
		}
		else if(state==State.PLAYER_PUSH_SELECTION) {
			playerPushSelection();
		}
		else if(state==State.PLAYER_PUSH) {
			playerPush();
		}
		else if(state==State.MINDCONTROL) {
			mindcontrol();
		}
		else if(state==State.CREATE_INFUSION) {
			createInfusion();
		}
		else if(state==State.USE_ANY_INFUSION) {
			useAnyInfusion();
		}
		else if(state==State.ROUND_END_DISCARD) {
			roundEndDiscard();
		}
		else if(state==State.ROUND_END_REST) {
			roundEndRest();
		}
		*/
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
		}
	}
	
	private void setupBeginningOfRound() {
		//Title
		g.drawString("State of Scenario      Prosperity: "+gloomhaven.getProspLevel()+"Rep: "+gloomhaven.getReputationLevel(), Setting.graphicsXLeft, Setting.graphicsYTop);
		g.drawString(state.toString(), Setting.graphicsXLeft, Setting.graphicsYTop+Setting.rowSpacing);
		
		
		elements.graphicsDrawTable(g);
		Draw.rectangleBoardSideways(g, Setting.size, Setting.flatlayout, Setting.center, data.getBoardSize());
		
		GUIScenario.EntityTable(g, party);
		
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
				enemyInfo.pickRandomAbilityCard();
				currentPlayer=0;
				state=State.INITIATIVE;
			}
		}
	}
	
	private void initiative() {
		int enemyInit=enemyInfo.getInitiative();
		party.sort(Comparator.comparingInt(Player::getInitiative));								//Order just the players based on initiative
		
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
		
		List<Player> targets = new ArrayList<Player>();
		targets = UtilitiesTargeting.createTargetListPlayer(board, enemyInfo.getEnemy(enemyTurnIndex).getBaseStats().getRange(), enemyInfo.getEnemy(enemyTurnIndex).getCoordinates(), data.getBoardSize(), party);
		//targets = enemyInfo.createTargetListForEnemy(enemyTurnIndex, party, g);

		if(targets.size()>0) {
			
			int min=100;
			int targetIndex=-1;
			
			for(int i=0; i<targets.size(); i++) {
				if(UtilitiesAB.distance(enemyInfo.getEnemy(enemyTurnIndex).getCoordinates(), party.get(currentPlayer).getCoordinates())<min) {
					targetIndex=i;
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
			int damage = enemyInfo.getAttack(enemyTurnIndex);
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
		
		UtilitiesAB.resolveCard(party.get(currentPlayer), card, elements, null);
		
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
			g.setColor(Color.BLUE);
			Draw.drawHex(g, UtilitiesHex.getCubeCoordinates(Setting.flatlayout, selectionCoordinate));
			
			if(k==Setting.moveKey) {
				if(board[selectionCoordinate.x][selectionCoordinate.y].getQuickID().equals("P"))
					finished=true;
				else if(UsePlayerAbilityCard.hasFlying(card)) {
					movePlayer(party.get(currentPlayer), selectionCoordinate);
					finished=true;
				}
				else if(UsePlayerAbilityCard.hasJump(card)) {
					movePlayer(party.get(currentPlayer), selectionCoordinate);
					finished=true;
				}
				else {
					if(board[selectionCoordinate.x][selectionCoordinate.y].isSpaceEmpty()) {
						movePlayer(party.get(currentPlayer), selectionCoordinate);
						finished=true;
					}
				}

			}
		}else {
			finished=true;
		}
		
		//Next State: Player Attack, Attack Logic, Round End
		if(finished) {
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
		
		if(party.get(currentPlayer).canAttack()) {
			
			//Creates target list of enemy coordinates
			List<Point> targets = new ArrayList<Point>();
			
			int cardRange=UsePlayerAbilityCard.getRange(card);
			
			if(UsePlayerAbilityCard.getRange(card)>=0) {
				if(UsePlayerAbilityCard.getRange(card)==0)
					cardRange=1;
	
				if(UsePlayerAbilityCard.hasTargetHeal(card)) {
					for(int range=1; range<=cardRange; range++)
						targets=UtilitiesTargeting.createTargetList(board, range, party.get(currentPlayer).getCoordinates(), "P", data.getBoardSize());
					targets.add(party.get(currentPlayer).getCoordinates());
				}
				else {
					for(int range=1; range<=cardRange; range++)
						targets=UtilitiesTargeting.createTargetList(board, range, party.get(currentPlayer).getCoordinates(), "E", data.getBoardSize());
					
				}
			}
			
			
			
		}
	}
	
}