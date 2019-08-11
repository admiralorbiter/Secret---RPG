package Gloomhaven.AbilityCards;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardInterfaces.CardInterface;
import Gloomhaven.CardInterfaces.CardsEnemyTest;
import Unsorted.Archer;
import Unsorted.Guard;
import Unsorted.LivingBones;

public class EnemyAbilityCard extends AbilityCard {

	CardDataObject cardData = new CardDataObject();
	
	public EnemyAbilityCard(String enemyClass, int cardID, int enemyLevel) {
		
		CardInterface classCardData=null;
	
		if(enemyClass.equals("Bandit Archer")) {
			classCardData = new Archer();
		}
		else if(enemyClass.equals("Living Bones")) {
			classCardData = new LivingBones();
		}
		else if(enemyClass.equals("Bandit Guard")) {
			classCardData = new Guard();
		}
		else {
			classCardData = new CardsEnemyTest();
		}
		
		cardData=classCardData.getData(cardID);
		setInitiative(classCardData.getInitiative(cardID));
		setName(enemyClass);
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
	
	public String getText() {
		return cardData.getCardText();
	}
	
	public CardDataObject getData() {
		return cardData;
	}
	
}
