package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyInfo {
	
	List<EnemyAbilityCards> abilityDeck = new ArrayList<EnemyAbilityCards>();
	List<Enemy> enemies = new ArrayList<Enemy>();
	AttackDeck enemyDeck = new AttackDeck();						//Creates enemy attack deck - [Temp] Will need to have it flag and select the enemy one
	int turnNumber;
	Room room;
	int startingAbilityCardCount;
	int abilityCardIndex;
	private int enemyInit;
	
	public EnemyInfo(List<Enemy> enemies, Room room) {
		this.enemies=enemies;
		this.room=room;
		Point dimensions=room.getDimensions();
		
		abilityCardIndex=0;
		startingAbilityCardCount=8;
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new EnemyAbilityCards(1, i+1, "Test"));
		
		for(int i=0; i<enemies.size(); i++) {
			enemies.get(i).setDimensions(dimensions);
		}
	}
	
	public Enemy getEnemyFromID(String id) {
		for(int i=0; i<enemies.size(); i++) {
			if(enemies.get(i).getID().equals(id))
				return enemies.get(i);
		}
		
		return null;
	}
	
	public int getInitiative() {return abilityDeck.get(abilityCardIndex).getInitiatve();}
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
		
		if(enemies.get(index).canMove()) {
			int move = enemies.get(index).getMove();
			List<Point> points=enemies.get(index).createTargetList(room.getBoard(), move, "P");
			if(points.size()>0) {
				//Move Closer
				
			}
		}
	}
		
	/*Algorithm:
	 * Can enemy Attack -> is melee range -> quick range check
	 */
	public List<Player> enemyAttackProcedure(int index, List<Player> party, Graphics g) {

		List<Player> targets = new ArrayList<Player>();
		
		abilityDeck.get(abilityCardIndex).graphicsAbilityCard(g);
		
		boolean canAttack=enemies.get(index).canAttack();			//can be rolled into the if, but this might help with testing
		
		if(canAttack) {
			
			boolean meleeRange=enemies.get(index).checkMeleeRange(room.getBoard(), "P");
			
			if(meleeRange) {
				targets=enemies.get(index).createMeleeTargetList(room.getBoard(), party);
				return targets;
			}
			else {

				targets=enemies.get(index).playersInRangeEstimate(room.getBoard(), party);
				if(targets.size()>0) {
					
					targets=enemies.get(index).playersInRange(targets);
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
	
	public void pickRandomAbilityCard() {
		Random rand = new Random();
		boolean running=true;
		do
		{
		 int pick = rand.nextInt(abilityDeck.size());
		 if(abilityDeck.get(pick).cardFree()) {
			 abilityCardIndex=pick;
			 running=false;
		 }
		}
		while(running);
	}
	
	public int getAttack(int enemyTurnIndex) {
		AttackModifierCard card = enemies.get(enemyTurnIndex).attackModifierDeck.pickRandomModifierCard();
		return card.multiplier*(enemies.get(enemyTurnIndex).getAttack()+abilityDeck.get(abilityCardIndex).getAttack()+card.plusAttack);
	}
	
	//[Test]
	public void testPrintEnemies() {
		for(int i=0; i<enemies.size(); i++) {
			System.out.println(enemies.get(i).getClassID()+"  "+enemies.get(i).getCoordinate());
		}
	}
	
	//[Test]
	public void testPrintDimensions() {
		for(int i=0; i<enemies.size(); i++) {
			System.out.println(enemies.get(i).getDimensions());
		}
	}
	
	
}
