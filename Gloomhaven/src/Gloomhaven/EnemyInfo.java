package Gloomhaven;

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
	
	public EnemyInfo(List<Enemy> enemies, Room room) {
		this.enemies=enemies;
		this.room=room;
		Point2D dimensions=room.getDimensions();
		
		abilityCardIndex=0;
		startingAbilityCardCount=8;
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new EnemyAbilityCards(1, i+1, "Test"));
		
		for(int i=0; i<enemies.size(); i++) {
			enemies.get(i).setDimensions(dimensions);
		}
	}
	public int getInitiative() {return abilityDeck.get(abilityCardIndex).getInitiatve();}
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}
	
	public void playerAttack(String id, CardDataObject attackData) {
		for(int i=0; i<enemies.size(); i++) {

			if(enemies.get(i).getID()==id) {
				enemies.get(i).takeDamage(attackData.attack);
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
	
	/*Algorithm:
	 * Can enemy Attack -> is melee range -> quick range check
	 */
	public List<Player> enemyAttackProcedure(int index, List<Player> party) {

		List<Player> targets = new ArrayList<Player>();
		
		boolean canAttack=enemies.get(index).canAttack();			//can be rolled into the if, but this might help with testing
		
		if(canAttack) {
			
			boolean meleeRange=enemies.get(index).checkMeleeRange(room.getqBoard(), "P");
			
			if(meleeRange) {
				targets=enemies.get(index).createMeleeTargetList(room.getqBoard(), room.getIDBoard(), party);
				return targets;
			}
			else {

				targets=enemies.get(index).playersInRangeEstimate(room.getqBoard(), room.getIDBoard(), party);
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
		return enemies.get(enemyTurnIndex).getAttack()+abilityDeck.get(abilityCardIndex).getAttack();
	}
	
	//[Test]
	public void testPrintEnemies() {
		for(int i=0; i<enemies.size(); i++) {
			System.out.println(enemies.get(i).getClassID());
		}
	}
	
	//[Test]
	public void testPrintDimensions() {
		for(int i=0; i<enemies.size(); i++) {
			System.out.println(enemies.get(i).getDimensions());
		}
	}
	
	
}
