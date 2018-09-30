package Gloomhaven;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class EnemyInfo {
	
	List<Enemy> enemies = new ArrayList<Enemy>();
	AttackDeck enemyDeck = new AttackDeck();						//Creates enemy attack deck - [Temp] Will need to have it flag and select the enemy one
	int turnNumber;
	Room room;
	
	public EnemyInfo(List<Enemy> enemies, Room room) {
		this.enemies=enemies;
		this.room=room;
		Point2D dimensions=room.getDimensions();
		
		for(int i=0; i<enemies.size(); i++) {
			enemies.get(i).setDimensions(dimensions);
		}
	}
	
	public int drawCard() {
		return enemyDeck.drawCard();
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
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
	public List<Player> enemyAttackProcedure(int index) {
		List<Player> targets = new ArrayList<Player>();
		
		boolean canAttack=enemies.get(index).canAttack();			//can be rolled into the if, but this might help with testing
		
		if(canAttack) {
			boolean meleeRange=enemies.get(index).checkMeleeRange(room.getqBoard(), "P");
			
			if(meleeRange) {
				targets=enemies.get(index).createMeleeTargetList();
				return targets;
			}
			else {
				targets=enemies.get(index).playersInRangeEstimate();
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
