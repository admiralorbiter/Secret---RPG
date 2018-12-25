package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.PositiveConditions;
import Gloomhaven.CardDataObject.SimpleCardData;
import Gloomhaven.CardDataObject.Target;
import Gloomhaven.CardDataObject.Trigger;

public class Scoundrel implements CardInterface{

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		switch(id) {
			case 1:
				card.setCardText("Attack +3. Add +2 and XP +1 when target is adj to allies.");
				card.getData().setAttack(3);
				card.setTrigger(new Trigger("AdjacentToAllies", new SimpleCardData()));
				card.getTrigger().getBonusData().setAttack(2);
				card.getTrigger().getBonusData().setXpOnUse(1);
				break;
			case 2:
				card.setCardText("Attack +3. Add +2 and XP +1 when target is adj to allies.");
				card.getData().setAttack(3);
				card.setTrigger(new Trigger("AdjacentToAllies", new SimpleCardData()));
				card.getTrigger().getBonusData().setAttack(2);
				card.getTrigger().getBonusData().setXpOnUse(1);
				break;
			case 3:
				card.setCardText("Invisible on self. Infuse dark. On your next attack while you have invisbile, double the value of the attack. Persistent. (UNIMPLEMENTED). ");
				card.setPositiveConditions(new PositiveConditions("Invisibile"));
				card.setInfuseElemental("Dark");
				break;
			case 4:
				card.setCardText("Attack +3. Add +2 Attack and gain XP +1 when target is adj to your allies. Add +2 Attack and gain XP +1 when target is adj is not adj to allies. (Can't do both yet) Consumed on use.");
				card.getData().setAttack(3);
				card.setTrigger(new Trigger("AdjacentToAllies", new SimpleCardData()));
				card.getTrigger().getBonusData().setAttack(2);
				card.getTrigger().getBonusData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 5:
				card.setCardText("Disarm one adjacent trap. (Unimplemented) XP +2");
				card.getData().setXpOnUse(2);
				break;
			case 6:
				card.setCardText("Attack +3. Poison. XP +1");
				card.getData().setAttack(3);
				card.getData().setXpOnUse(1);
				card.setNegativeConditions(new NegativeConditions("Poison"));
				break;
			case 7:
				card.setCardText("Attack +2 Range +3 Target +2 (Not implemented). XP +1");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.getData().setTarget(new Target(2));
				card.getData().setXpOnUse(1);
				break;
			case 8:
				card.setCardText("Move +2. Attack +2");
				card.getData().setMove(2);
				card.getData().setAttack(2);
				break;
			case 9:
				card.setCardText("Heal +3 on Self");
				card.setEffects(new Effects("Heal", 3, 0));
				break;
		}
		
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		switch(id) {
			case 1:
				card.setCardText("On the next 4 attacks target enemies that aren't adj to allies, add +2 attack. Persistent (uniplmented need a counter)");
				break;
			case 2:
				card.setCardText("Move +5");
				card.getData().setMove(5);
				break;
			case 3:
				card.setCardText("Pull +2. (UNIMPLEMENTED) Range +3.");
				card.setEffects(new Effects("Pull", 2, 3));
				break;
			case 4:
				card.setCardText("Move +6");
				card.getData().setMove(6);
				break;
			case 5:
				card.setCardText("Attack +3. XP +1");
				card.getData().setAttack(3);
				card.getData().setXpOnUse(1);
				break;
			case 6:
				card.setCardText("Move +5");
				card.getData().setMove(5);
				break;
			case 7:
				card.setCardText("Loot +2");
				card.setEffects(new Effects("Loot", 0, 2));
				break;
			case 8:
				card.setCardText("Loot +1");
				card.setEffects(new Effects("Loot", 0, 1));
				break;
			case 9:
				card.setCardText("Move +3. Poison. Target one adjacent enemy (No Implemented)");
				card.getData().setMove(3);
				card.setNegativeConditions(new NegativeConditions("Poison"));
				break;
		}
		
		return card;
	}

	@Override
	public CardDataObject getData(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName(int id) {
		switch(id) {
			case 1:
				return "Single Out";
			case 2:
				return "Flanking Strike";
			case 3:
				return "Smoke Bomb";
			case 4:
				return "Backstab";
			case 5:
				return "Thief's Knack";
			case 6:
				return "Venom Shiv";
			case 7:
				return "Thorwing Knives";
			case 8:
				return "Quick Hands";
			case 9:
				return "Special Mixture";
		}
		
		return "Error in card name";
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
			case 1:
				return 86;
			case 2:
				return 4;
			case 3:
				return 12;
			case 4:
				return 6;
			case 5:
				return 23;
			case 6:
				return 60;
			case 7:
				return 10;
			case 8:
				return 64;
			case 9:
				return 33;
		}
		
		return -99;
	}

}
