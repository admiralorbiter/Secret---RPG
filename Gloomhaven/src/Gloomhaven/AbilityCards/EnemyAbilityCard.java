package Gloomhaven.AbilityCards;

import java.awt.Graphics;

import Gloomhaven.CardDataObject;
import Gloomhaven.CardInterfaces.CardInterface;
import Gloomhaven.CardInterfaces.CardsEnemyTest;

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
