package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class EnemyInfo {
	
	List<Enemy> enemies = new ArrayList<Enemy>();
	AttackDeck enemyDeck = new AttackDeck();						//Creates enemy attack deck - [Temp] Will need to have it flag and select the enemy one
	int turnNumber;
	
	public EnemyInfo(List<Enemy> enemies) {
		this.enemies=enemies;			
	}
	
	public int drawCard() {
		return enemyDeck.drawCard();
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}
	
	public int getTurnNumber() {return turnNumber;}
}
