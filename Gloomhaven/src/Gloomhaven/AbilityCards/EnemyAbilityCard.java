package Gloomhaven.AbilityCards;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardInterfaces.CardInterface;
import Gloomhaven.CardInterfaces.CardsEnemyTest;

public class EnemyAbilityCard extends AbilityCard {

	CardDataObject cardData = new CardDataObject();
	
	public EnemyAbilityCard(String enemyClass, int cardID, int enemyLevel) {
		
		CardInterface classCardData=null;
		
		if(enemyClass.equals("Test")) {
			classCardData = new CardsEnemyTest();
		}
		
		cardData=classCardData.getData(cardID);
		setInitiative(classCardData.getInitiative(cardID));
		setName(classCardData.getName(cardID));
	}
	
	public int getAttack() {
		return cardData.getData().getAttack();
	}
	
	public int getMove() {
		return cardData.getData().getMove();
	}
	
	public int getRange() {
		return cardData.getData().getRange();
	}
	
}
