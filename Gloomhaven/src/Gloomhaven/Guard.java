package Gloomhaven;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.PositiveConditions;
import Gloomhaven.CardInterfaces.CardInterface;

public class Guard implements CardInterface{

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
				card.setCardText("Shield +1. Retaliate +1.");
				card.setEffects(new Effects("Shield", 1, 0));
				card.setEffects(new Effects("Retaliate", 1, 0));
				break;
			case 2:
				card.setCardText("Move 1. Attack -1.");
				card.getData().setMove(1);
				card.getData().setAttack(-1);
				break;
			case 3:
				card.setCardText("Move -1. Range 2.");
				card.getData().setMove(-1);
				card.getData().setRange(2);
				break;
			case 4:
				card.setCardText("Move/Attack/Range 0");
				break;
			case 5:
				card.setCardText("Move/Attack/Range 0");
				break;
			case 6:
				card.setCardText("Move -1. Attack 1.");
				card.getData().setMove(-1);
				card.getData().setAttack(1);
				break;
			case 7:
				card.setCardText("Move -1. Strength. Self.");
				card.getData().setMove(-1);
				card.setPositiveConditions(new PositiveConditions("Strengthen"));
				break;
			case 8:
				card.setCardText("Poison. Shield +1.");
				card.setNegativeConditions(new NegativeConditions("Poison"));
				card.setEffects(new Effects("Shield", 1, 0));
				break;
		}
		return card;
	}

	@Override
	public String getName(int id) {return null;}

	@Override
	public int getInitiative(int id) {
		switch(id) {
			case 1:
				return 15;
			case 2:
				return 30;
			case 3:
				return 35;
			case 4:
				return 50;
			case 5:
				return 50;
			case 6:
				return 70;
			case 7:
				return 55;
			case 8:
				return 15;
		}
		return 99;
	}

}
