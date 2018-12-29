package Gloomhaven.Characters;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.Room;
import Gloomhaven.Setting;
import Gloomhaven.UtilitiesAB;
import Gloomhaven.UtilitiesTargeting;
import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;

public class EnemyInfo {
	
	List<EnemyAbilityCard> abilityDeck = new ArrayList<EnemyAbilityCard>();
	List<Enemy> enemies = new ArrayList<Enemy>();
	int turnNumber;
	Room room;
	int startingAbilityCardCount;
	int abilityCardIndex;
	private int enemyInit;
	
	Point dimensions;
	
	public EnemyInfo(List<Enemy> enemies, Room room) {
		this.enemies=enemies;
		this.room=room;
		dimensions=room.getDimensions();
		abilityCardIndex=0;
		startingAbilityCardCount=8;
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new EnemyAbilityCard("Test", i+1, 1));

	}
	
	public Enemy getEnemyFromID(String id) {
		for(int i=0; i<enemies.size(); i++) {
			if(enemies.get(i).getID().equals(id))
				return enemies.get(i);
		}
		
		return null;
	}
	
	public List<Enemy> getEnemies(){return enemies;}
	public int getInitiative() {return abilityDeck.get(abilityCardIndex).getInitiative();}
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}
	
	public void playerAttack(String id, int damage) {
		for(int i=0; i<enemies.size(); i++) {

			if(enemies.get(i).getID()==id) {
				enemies.get(i).takeDamage(damage);
			}
		}
		
	}
	
	public int getTurnNumber() {return turnNumber;}
	
	public void orderEnemies() {
		List<Enemy> temp = new ArrayList<Enemy>();
		for(int i=0; i<enemies.size(); i++) {
			if(enemies.get(i).isElite()) {
				temp.add(enemies.get(i));
			}
		}
		
		for(int i=0; i<enemies.size(); i++) {
			if(!(enemies.get(i).isElite())) {
				temp.add(enemies.get(i));
			}
		}
	
		enemies=temp;
	}
	
	public Enemy getEnemy(int index) {return enemies.get(index);}
	
	public int getCount() {return enemies.size();}
	
	public void enemyMoveProcedure(int index, List<Player> party, Graphics g) {
		//TODO this needs to be rewritten
		
		if(enemies.get(index).canMove()) {
			int move = enemies.get(index).getBaseStats().getMove();
			for(int i=0; i<move; i++) {
				List<Point> points = UtilitiesTargeting.createTargetList(room.getBoard(), move+3, enemies.get(index).getCoordinates(), "P", room.getDimensions());
				if(points.size()>0) {
					//Move Closer
					//picks the closest person on the createtargetlist
					if(!room.moveEnemyOneHexCloser(enemies.get(index), points.get(0)))
						System.out.println("Unable to move up/down/left/right   "+points.get(0));
				}
			}
		}
	}
		
	/*Algorithm:
	 * Can enemy Attack -> is melee range -> quick range check
	 */
	public List<Player> createTargetListForEnemy(int index, List<Player> party, Graphics g) {

		List<Player> targets = new ArrayList<Player>();
		
		//abilityDeck.get(abilityCardIndex).graphicsAbilityCard(g);
		//drawAbilityCard(g);
		
		boolean canAttack=enemies.get(index).canAttack();			//can be rolled into the if, but this might help with testing
		
		if(canAttack) {
			boolean meleeRange=UtilitiesAB.checkMeleeRange(enemies.get(index), room.getBoard(), "P", dimensions);
			
			if(meleeRange) {
				targets = UtilitiesAB.createMeleeTargetList(room.getBoard(), party, enemies.get(index), dimensions);
				return targets;
			}
			else {

				targets = UtilitiesAB.playersInRangeEstimate(room.getBoard(), party, enemies.get(index), dimensions);
				if(targets.size()>0) {
					targets=UtilitiesAB.playersInRange(targets);
					return targets;
				}
				else {
					return targets;
				}
			}
		}
		else {
			return targets;
		}
	}
	
	public void drawAbilityCard(Graphics g) {
		Setting setting = new Setting();
		g.drawString("Enemy Ability Card", setting.getGraphicsXMid(), setting.getGraphicsYBottom());
		g.drawString("Attack: "+abilityDeck.get(abilityCardIndex).getAttack()+"  Move: "+abilityDeck.get(abilityCardIndex).getMove()+" Range: "+abilityDeck.get(abilityCardIndex).getRange(), setting.getGraphicsXMid(), setting.getGraphicsYBottom()+15);
	}
	
	public void pickRandomAbilityCard() {
		Random rand = new Random();
		boolean running=true;
		do
		{
		 int pick = rand.nextInt(abilityDeck.size());
		 if(abilityDeck.get(pick).isCardFree()) {
			 abilityCardIndex=pick;
			 running=false;
		 }
		}
		while(running);
	}
	
	public int getAttack(int enemyTurnIndex) {
		AttackModifierCard card = enemies.get(enemyTurnIndex).attackModifierDeck.pickRandomModifierCard();
		return card.getMultiplier()*(enemies.get(enemyTurnIndex).getBaseStats().getAttack()+abilityDeck.get(abilityCardIndex).getAttack()+card.getPlusAttack());
	}
	
	//[Test]
	public void testPrintEnemies() {
		for(int i=0; i<enemies.size(); i++) {
			System.out.println(enemies.get(i).getClassID()+"  "+enemies.get(i).getCoordinates());
		}
	}
	
}
