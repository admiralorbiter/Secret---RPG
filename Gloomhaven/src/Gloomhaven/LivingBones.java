package Gloomhaven;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardInterfaces.CardInterface;

public class LivingBones implements CardInterface {

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
				card.setCardText("Move -1. Attack +1.");
				card.getData().setMove(-1);
				card.getData().setAttack(1);
				break;
			case 2:
				card.setCardText("Move -2. Heal +2 (Self)");
				card.getData().setMove(-2);
				card.setEffects(new Effects("Heal", 2, 0 ));
				break;
			case 3:
				card.setCardText("Move +1. Attack -1.");
				card.getData().setMove(1);
				card.getData().setAttack(-1);
				break;
			case 4:
				card.setCardText("Move/Attack/Range 0");
				break;
			case 5:
				card.setCardText("Move/Attack/Range 0");
				break;
			case 6:
				card.setCardText("Attack +2");
				card.getData().setAttack(2);
				break;
			case 7:
				card.setCardText("Target one enemy with all attacks.");
				//TODO
				break;
			case 8:
				card.setCardText("Shield +1. Heal +2 (Self)");
				card.setEffects(new Effects("Heal", 2, 0 ));
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
				return 64;
			case 2:
				return 20;
			case 3:
				return 25;
			case 4:
				return 45;
			case 5:
				return 45;
			case 6:
				return 81;
			case 7:
				return 74;
			case 8:
				return 12;
		}
		return 99;
	}

}
