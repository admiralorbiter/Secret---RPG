package Gloomhaven;

import java.awt.Graphics;

public class EnemyAbilityCard extends AbilityCard {

	CardDataObject card = new CardDataObject();
	
	public EnemyAbilityCard(String enemyClass, int cardID, int enemyLevel) {
		
		CardInterface classCardData=null;
		
		if(enemyClass.equals("Test")) {
			classCardData = new CardsEnemyTest();
		}
		
		card = classCardData.getData(cardID);
		setName(enemyClass);
		setInitiative(card.getInitiative());
	}
	
	public int getAttack() {
		return card.getAttack();
	}
	
	public int getMove() {
		return card.getMove();
	}
	
	public int getRange() {
		return card.getRange();
	}
	
}
