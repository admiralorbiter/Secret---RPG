package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.SimpleCardData;
import Gloomhaven.CardDataObject.Target;

public class Brute implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		switch(id) {
			case 1:
				card.setCardText("Attack +3. Pierce +2.");
				card.getData().setAttack(3);
				card.setEffects(new Effects("Pierce", 2, 0));
				break;
			case 2:
				card.setCardText("Retaliate +2. XP +1 for each Retaliate (Maybe implemented). Round Bonus");
				card.setEffects(new Effects("Retaliate", 2, 0));
				card.setCounter(new Counter(99, "OnRetaliate", new SimpleCardData()));
				card.getCounter().getBonusData().setXpOnUse(1);
				card.getData().setRoundBonusFlag(true);
				break;
			case 3:
				card.setCardText("Attack +2. Semi Circle of 3 tiles.");
				card.getData().setAttack(2);
				card.getData().setTarget(new Target("SemiCircle"));
				break;
			case 4:
				card.setCardText("Attack +2. Disarm");
				card.getData().setAttack(2);
				card.setNegativeConditions(new NegativeConditions("Disarm"));
				break;
			case 5:
				card.setCardText("Attack +6 XP +2. Card consumed on use.");
				card.getData().setAttack(6);
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 6:
				card.setCardText("Loot +1");
				card.setEffects(new Effects("Loot" , 0, 1));
				break;
			case 7:
				card.setCardText("Attack +3. Push +2");
				card.getData().setAttack(3);
				card.setEffects(new Effects("Push",2, 0));
				break;
			case 8:
				card.setCardText("Attack +4 Stun XP +2. Consumed on Use.");
				card.getData().setAttack(4);
				card.getData().setXpOnUse(2);
				card.setNegativeConditions(new NegativeConditions("Stun"));
				card.getData().setConsumeFlag(true);
				break;
			case 9:
				card.setCardText("Attack +3 at 2 connected tiles. XP +1");
				card.getData().setAttack(3);
				card.getData().setXpOnUse(1);
				card.getData().setTarget(new Target("SortOfSemiCircle"));
				break;
			case 10:
				card.setCardText("Attack +3 Range +3 XP +1");
				card.getData().setAttack(3);
				card.getData().setRange(3);
				card.getData().setXpOnUse(1);
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
				card.setCardText("Move +4. Jump. Attack +2 each enemy passed through. (UNIMPLEMENTED) XP +2. Card Consumed on discard.");
				card.getData().setMove(4);
				card.getData().setJumpFlag(true);
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 2:
				card.setCardText("Heal +2 Range +1. Infuse Earth.");
				card.setEffects(new Effects("Heal", 2, 1 ));
				card.setInfuseElemental("Earth");
				break;
			case 3:
				card.setCardText("Move +3. Push +1 All Adjacent Tiles (UNIMPLEMENTED)");
				card.getData().setMove(3);
				card.setEffects(new Effects("Push", 1, 0));
				card.getEffects().setTarget(new Target("AllAdjacent"));
				break;
			case 4:
				card.setCardText("Any enemy who targets one of your adj allies with an attack targets you instead regardless of range. (UNIMPLEMENTED) Continous (Not really implemented)");
				break;
			case 5:
				card.setCardText("Move +3. Push +2. Target one adjacent enemy (UNIMPLEMENTED)");
				card.getData().setMove(3);
				card.setEffects(new Effects("Push", 2, 0));
				card.getEffects().setTarget(new Target("OneAdjacentEnemy"));
				break;
			case 6:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 7:
				card.setCardText("On the next 6 sources of damage from attacks targeting you (Maybe implemented)., gain shield +1. Continous. Conusmed on use.");
				card.setCounter(new Counter(6, "OnDamage", new Effects("Shield", 1, 0)));
				card.getData().setConsumeFlag(false);
				break;
			case 8:
				card.setCardText("Shield +1 (Self). Round Bonus.");
				card.setEffects(new Effects("Shield", 1, 0));
				card.getData().setRoundBonusFlag(true);
				break;
			case 9:
				card.setCardText("Move +3. Jump. Infuse air.");
				card.getData().setMove(3);
				card.getData().setJumpFlag(true);
				card.setInfuseElemental("Air");
				break;
			case 10:
				card.setCardText("Attack +2");
				card.getData().setAttack(2);
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
				return "Trample";
			case 2:
				return "Eye for an Eye";
			case 3:
				return "Sweeping Blow";
			case 4:
				return "Provoking Roar";
			case 5:
				return "Overwhelming Assault";
			case 6:
				return "Grab and Go";
			case 7:
				return "Warding Stregth";
			case 8:
				return "Shield Bash";
			case 9:
				return "Leaping Cleave";
			case 10:
				return "Spare Dagger";
				
		}
		return "I am error";
			
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
			case 1:
				return 72;
			case 2:
				return 18;
			case 3:
				return 64;
			case 4:
				return 10;
			case 5:
				return 61;
			case 6:
				return 87;
			case 7:
				return 32;
			case 8:
				return 15;
			case 9:
				return 54;
			case 10:
				return 27;
				
		}
		return -99;
	}

}
