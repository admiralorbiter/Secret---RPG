package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.PositiveConditions;
import Gloomhaven.CardDataObject.SimpleCardData;
import Gloomhaven.CardDataObject.Trigger;

public class MindThief implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id); 
	
		switch(id) {
			case 1:
				card.setCardText("Attack +2 and (add +1 Attack for each negative condition on target.) XP +1");
				card.getData().setAttack(2);
				card.setTrigger(new Trigger("ForEachNegativeCondition"));
				card.getTrigger().getBonusData().setAttack(1);
				card.getData().setXpOnUse(1);
				break;
			case 2:
				card.setCardText("Loot +1. Infuse dark");
				card.setInfuseElemental("Dark");
				card.setEffects(new Effects("Loot", 0, 1));
				break;
			case 3:
				card.setCardText("Attack +2 Push +3 XP +1");
				card.getData().setAttack(2);
				card.getData().setXpOnUse(1);
				card.setEffects(new Effects("Push", 3, 0));
				break;
			case 4:
				card.setCardText("Augument Shield +1 on Self. Attack +1 XP +1");
				card.setAugment(true);
				card.setEffects(new Effects("Shield", 1, 0));
				card.getData().setAttack(1);
				card.getData().setXpOnUse(1);
				break;
			case 5:
				card.setCardText("Summon Rat Swarm. Xp +2. Cont. Then consumed on discard. (UNIMPLEMENTED)");
				card.getData().setXpOnUse(2);
				break;
			case 6:
				card.setCardText("Augument On Melee Attack +2. Attack +1 XP +1");
				card.setAugment(true);
				card.setCounter(new Counter(1000, "OnMeleeAttack", new SimpleCardData()));
				card.getCounter().getBonusData().setAttack(2);
				card.getData().setAttack(1);
				card.getData().setXpOnUse(1);
				break;
			case 7:
				card.setCardText("Augument On Melee Attack Heal +2. Attack +1. XP +1.");
				card.setAugment(true);
				card.setEffects(new Effects("Heal", 2, 0));
				card.getData().setAttack(1);
				card.getData().setXpOnUse(1);
				break;
			case 8:
				card.setCardText("Move +3. Attack +1.");
				card.getData().setMove(3);
				card.getData().setAttack(1);
				break;
			case 9:
				card.setCardText("Attack +3. Add +2 Attack and gain XP +1 for each negative condition on target.");
				card.getData().setAttack(3);
				card.setTrigger(new Trigger("ForEachNegativeConditions"));
				card.getTrigger().getBonusData().setAttack(2);
				card.getTrigger().getBonusData().setXpOnUse(1);
				break;
			case 10:
				card.setCardText("Attack +4. Range +5. Disarm. Infuse Ice. XP +2. Card consumed on discard.");
				card.getData().setAttack(4);
				card.getData().setRange(5);
				card.getData().setXpOnUse(2);
				card.setInfuseElemental("Ice");
				card.setNegativeConditions(new NegativeConditions("Disarm"));
				card.getData().setConsumeFlag(true);
				break;
			default:
				card.setCardText("If you see this, it is a mistake.");
		}
	
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		switch(id) {
			case 1:
				card.setCardText("Mindcontrol: Force enemy to target another enemy at Range 5. Attack +2 Range +0 (Unimplemented)");
				//Force one enemy within 5 range to do 2 attack to melee
				card.setMindControl(true);
				//card.mindControl=true;
				card.getData().setRange(5);
				card.getData().setAttack(2);
				break;
			case 2:
				card.setCardText("Invisibility on Self");
				card.setPositiveConditions(new PositiveConditions("Invisibility"));
				break;
			case 3:
				card.setCardText("Move +4. Attack +2. XP +2. Consumed after use.");
				card.getData().setMove(4);
				card.getData().setAttack(2);
				card.getData().setXpOnUse(2);
				card.getData().getConsumeFlag();
				break;
			case 4:
				card.setCardText("Move +3. Jump. If you end in the same hex. Perform muddle on all target enemies moved through. (UNIMPLEMENTED");
				card.getData().setMove(3);
				card.getData().setJumpFlag(true);
				break;
			case 5:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 6:
				card.setCardText("Attack +1 Wound target");
				card.getData().setAttack(1);
				card.setNegativeConditions(new NegativeConditions("Wound"));
				break;
			case 7:
				card.setCardText("MindControl: Force enemy to move with player control of range 4. Move +1. (Unimplemented)");
				card.setMindControl(true);
				card.getData().setRange(4);
				card.getData().setMove(1);
				break;
			case 8:
				card.setCardText("Loot +2. XP +1. Card Consumed on discard.");
				card.setEffects(new Effects("Loot", 0, 2));
				card.getData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 9:
				card.setCardText("Attack +1 Range +2. Stun. Infuse Ice. XP +1");
				card.getData().setAttack(1);
				card.getData().setRange(2);
				card.setNegativeConditions(new NegativeConditions("Stun"));
				card.setInfuseElemental("Ice");
				card.getData().setXpOnUse(1);
				break;
			case 10:
				card.setCardText("Move +2. Heal +2 (Self)");
				card.getData().setMove(2);
				card.setEffects(new Effects("Heal", 2, 0));
				break;
			default:
				card.setCardText("If you see this, it is a mistake.");
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
				return "Submissive Affliction";
			case 2:
				return "Into the Night";
			case 3:
				return "Fearsome Blade";
			case 4:
				return "Feedback Loop";
			case 5:
				return "Gnawing Horde";
			case 6:
				return "The Mind's Weakness";
			case 7:
				return "Parasitic Influence";
			case 8:
				return "Scurry";
			case 9:
				return "Perverse Edge";
			case 10:
				return "Empathetic Assault";
		}
		
		return "Error";
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
		case 1:
			return 48;
		case 2:
			return 14;
		case 3:
			return 27;
		case 4:
			return 79;
		case 5:
			return 82;
		case 6:
			return 75;
		case 7:
			return 71;
		case 8:
			return 20;
		case 9:
			return 8;
		case 10:
			return 11;
		}
		
		return 99;
	}

}
