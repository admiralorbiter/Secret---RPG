package Gloomhaven;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardInterfaces.CardInterface;

public class Archer implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {return null;}

	@Override
	public CardDataObject getBottom(int id) {return null;}

	@Override
	public CardDataObject getData(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		switch(id) {
			case 1:
				card.setCardText("Move 1. Attack 1");
				card.getData().setAttack(1);
				card.getData().setMove(1);
				break;
			case 2:
				card.setCardText("Move/Attack/Range 0");
				break;
			case 3:
				card.setCardText("Attack 1. Range -1.");
				card.getData().setAttack(1);
				card.getData().setRange(-1);
				break;
			case 4:
				card.setCardText("Move -1. Attack 1.");
				card.getData().setMove(-1);
				card.getData().setAttack(1);
				break;
			case 5:
				card.setCardText("Attack -1. Target 2.");
				card.getData().setAttack(-1);
				card.getData().getTarget().setTargets(2);
				break;
			case 6:
				card.setCardText("Attack 1. Range 1.");
				card.getData().setAttack(1);
				card.getData().setRange(1);
				break;
			case 7:
				card.setCardText("Move -1. Attack -1. Create 3 damage trap in an adjacent hex closest to the enemy.");
				card.getData().setMove(-1);
				card.getData().setAttack(-1);
				break;
			case 8:
				card.setCardText("Attack -1. Range 1. Immobilize.");
				card.getData().setAttack(-1);
				card.getData().setRange(1);
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				break;	
		}
		
		return card;
	}

	@Override
	public String getName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
			case 1:
				return 16;
			case 2:
				return 31;
			case 3:
				return 32;
			case 4:
				return 44;
			case 5:
				return 56;
			case 6:
				return 68;
			case 7:
				return 14;
			case 8:
				return 29;
		}
		return 99;
	}
}
