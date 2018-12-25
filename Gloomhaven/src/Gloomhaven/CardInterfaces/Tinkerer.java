package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.Target;
import Gloomhaven.CardDataObject.Trigger;

public class Tinkerer implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);

		switch(id) {
			case 1:
				card.setCardText("Create one 6 damage trap in an adj empty hex (Unimplemented). Gain XP +2 when trap is sprung by enemey. (Unimplemented) Card consumed on use.");
				card.getData().setConsumeFlag(true);
				break;
			case 2:
				card.setCardText("Summon Decoy. (Unimplemented). Persistent. XP +2");
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 3:
				card.setCardText("Attack +3 for 3 hex triangle. Wound. XP +2. Infuse Fire. Card consumed on use.");
				card.getData().setAttack(3);
				card.getData().setTarget(new Target("Triangle"));
				card.setNegativeConditions(new NegativeConditions("Wound"));
				card.getData().setXpOnUse(2);
				card.setInfuseElemental("Fire");
				card.getData().setConsumeFlag(true);
				break;
			case 4:
				card.setCardText("Attack +3 Range +3 Pull +2 (Unimplemented)");
				card.getData().setAttack(3);
				card.getData().setRange(3);
				card.setEffects(new Effects("Pull", 2, 0));
				break;
			case 5:
				card.setCardText("Attack +4 Range +3 for a 3 hex triangle. Gain XP +1 for each enemy targeted (Unimplemented, Maybe). Infuse dark. Card consumed on use.");
				card.getData().setAttack(4);
				card.getData().setRange(3);
				card.getData().setTarget(new Target("Triangle"));
				card.setTrigger(new Trigger("ForEachTargeted"));
				card.getTrigger().getBonusData().setXpOnUse(1);
				card.setInfuseElemental("Dark");
				card.getData().setConsumeFlag(true);
				break;
			case 6:
				card.setCardText("Attack +3 Range +3 for a 3 hex triangle. Immobilize. Gain XP +1 for each target. (Unimplemented)");
				card.getData().setAttack(3);
				card.getData().setRange(3);
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				card.getData().setTarget(new Target("Triangle"));
				card.setTrigger(new Trigger("ForEachTargeted"));
				card.getTrigger().getBonusData().setXpOnUse(1);
				break;
			case 7:
				card.setCardText("Attack +1 Range +3 Stun");
				card.getData().setAttack(1);
				card.getData().setRange(3);
				card.setNegativeConditions(new NegativeConditions("Stun"));
				break;
			case 8:
				card.setCardText("Heal +3 Range +3");
				card.setEffects(new Effects("Heal", 3, 3));
				break;
			case 9:
				card.setCardText("Heal +3 Range +3");
				card.setEffects(new Effects("Heal", 3, 3));
				break;
			case 10:
				card.setCardText("Heal +5 Range +2 XP +2. Consumed on use.");
				card.setEffects(new Effects("Heal", 5, 2));
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 11:
				card.setCardText("Attack +3 Range +3");
				card.getData().setAttack(3);
				card.getData().setRange(3);
				break;
			case 12:
				card.setCardText("Attack +2 Range +3 Poison. Infuse earth");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.setNegativeConditions(new NegativeConditions("Poison"));
				card.setInfuseElemental("Earth");
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
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 2:
				card.setCardText("Heal +2 Range +3");
				card.setEffects(new Effects("Heal", 2, 3));
				break;
			case 3:
				card.setCardText("Shield +1. Affect self and all adj allies (Unimplemented). Round Bonus.");
				card.getData().setRoundBonusFlag(true);
				card.setCounter(new Counter(99, "OnDamage", new Effects("Shield", 1, 0)));
				card.getCounter().setTarget(new Target("AllAdjacentAllies"));
				break;
			case 4:
				card.setCardText("Loot +2 XP +1. Consumed on use.");
				card.setEffects(new Effects("Loot", 0, 1));
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 5:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 6:
				card.setCardText("Immobilize. Target one adjacent enemy (Unimplemented?). Move +2");
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				card.getData().setTarget(new Target("OneAdjacentEnemy"));
				card.getData().setMove(2);
				break;
			case 7:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 8:
				card.setCardText("One adj ally may recover all lost cards (Unimplemented) XP +2. Consumed on use.");
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 9:
				card.setCardText("Move +2 Heal +1 Affect all adj allies (Unimplemented)");
				card.getData().setMove(2);
				card.setEffects(new Effects("Heal", 1, 0));
				card.getEffects().setTarget(new Target("AllAdjacentAllies"));
				break;
			case 10:
				card.setCardText("Move +6 XP +1. Consumed on use.");
				card.getData().setMove(6);
				card.getData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 11:
				card.setCardText("You and all adj allies add +1 attack to all attacks this round. (Unimplemented) Round Bonus.");
				card.getData().setRoundBonusFlag(true);
				card.getData().setAttack(1);
				card.getData().setTarget(new Target("AllAdjacentAllies"));
				break;
			case 12:
				card.setCardText("Attack +5 Range +2 XP +2 Card consumed on use.");
				card.getData().setAttack(5);
				card.getData().setRange(2);
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
				
			
		}
		
		return card;
	}

	@Override
	public CardDataObject getData(int id) {
		CardDataObject card = new CardDataObject();
		card.setID(id);

		switch(id) {
		
		
		}
		
		return card;
	}

	@Override
	public String getName(int id) {
		switch(id) {
			case 1:
				return "Proximity Mine";
			case 2:
				return "Harmless Contraption";
			case 3:
				return "Flamethrower";
			case 4:
				return "Hook Gun";
			case 5:
				return "Ink Bomb";
			case 6:
				return "Net Shooter";
			case 7:
				return "Stun Shot";
			case 8:
				return "Reinvigorating Elixir";
			case 9:
				return "Restorative Mist";
			case 10:
				return "Energizing Tonic";
			case 11:
				return "Enhancement Field";
			case 12:
				return "Toxic Bolt";
		
		}
	
		return "Error Will Robinson. Error";
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
		case 1:
			return 62;
		case 2:
			return 74;
		case 3:
			return 47;
		case 4:
			return 72;
		case 5:
			return 74;
		case 6:
			return 19;
		case 7:
			return 20;
		case 8:
			return 37;
		case 9:
			return 17;
		case 10:
			return 16;
		case 11:
			return 61;
		case 12:
			return 18;
		
	}
	
	return -99;
	}

}
