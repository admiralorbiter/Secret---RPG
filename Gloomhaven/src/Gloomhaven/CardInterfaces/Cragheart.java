package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.CardDataObject.ElementalConsumptionBonus;
import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.CardDataObject.SimpleCardData;
import Gloomhaven.CardDataObject.Target;

public class Cragheart implements CardInterface{

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		switch(id) {
		case 1:
			card.setCardText("Attack +3 XP +1. Attack front and back hex");
			card.getData().setAttack(3);
			card.getData().setXpOnUse(1);
			card.getData().setTarget(new Target("OpposingAttack"));
			break;
		case 2:
			card.setCardText("Attack +3 Immobilize. Inufse earth");
			card.getData().setAttack(3);
			card.setNegativeConditions(new NegativeConditions("Immobilize"));
			card.setInfuseElemental("Earth");
			break;
		case 3:
			card.setCardText("Attack +4 XP +1 at 2 connected tiles. If you consume earth, Attack +1 XP +1. (UNIMPLEMENTED) Card consumed on use.");
			card.getData().setAttack(4);
			card.getData().setXpOnUse(1);
			card.getData().setTarget(new Target("SortOfSemiCircle"));
			card.setConsumeElemental("Earth");
			card.setElementalBonus(new ElementalConsumptionBonus());
			card.getElementalBonus().getBonusData().setAttack(1);
			card.getElementalBonus().getBonusData().setXpOnUse(1);
			card.getData().setConsumeFlag(true);
			break;
		case 4:
			card.setCardText("Heal +4 Range +2. Infuse Earth.");
			card.setEffects(new Effects("Heal", 4, 2));
			card.setInfuseElemental("Earth");
			break;
		case 5:
			card.setCardText("Attack +3 Range +3 All adj allies and enemies suffer 1 damage (UNIMPLEMENTED). Infuse earth.");
			card.getData().setAttack(3);
			card.getData().setRange(3);
			card.setInfuseElemental("Earth");
			break;
		case 6:
			card.setCardText("On your next four ranged attacks, gain Target +1 (Unimplemented). Consumed after use.");
			card.setCounter(new Counter(4, "OnAttack", new SimpleCardData()));
			card.getCounter().getBonusData().setTarget(new Target(2));
			card.getData().setConsumeFlag(true);
			break;
		case 7:
			card.setCardText("Destroy one adj obstacle. (UNIMPLEMENTED) XP +1");
			card.getData().setXpOnUse(1);
			break;
		case 8:
			card.setCardText("Attack +3 Target all adj enemies (Unimplemented). If you consume earth, range +2, XP+1. (UNIMPlemented) All adj allies suffer +2 damage. XP +1. Card consumed on use.");
			card.getData().setAttack(3);
			card.getData().setTarget(new Target("AllAdjacentEnemies"));
			card.setConsumeElemental("Earth");
			card.getElementalBonus().getBonusData().setRange(2);
			card.getElementalBonus().getBonusData().setXpOnUse(1);
			card.getData().setXpOnUse(1);
			card.getData().setConsumeFlag(true);
			break;
		case 9:
			card.setCardText("Attack +3 Range +3. If you consume earth, push +2, XP+1. (Unimplemented).");
			card.getData().setAttack(3);
			card.getData().setRange(3);
			card.setConsumeElemental("Earth");
			card.setEffects(new Effects("Push", 2, 3));
			card.getElementalBonus().getBonusData().setXpOnUse(1);
			break;
		case 10:
			card.setCardText("Attack +1. Range +2. Attack in a circle. If consume earth, Attack +1 XP+1. (UNimplemented). Muddle all allies and enemies in target area. (Maybe implemented?)");
			card.getData().setAttack(1);
			card.getData().setRange(2);
			card.getData().setTarget(new Target("Circle"));
			card.setConsumeElemental("Earth");
			card.getElementalBonus().getBonusData().setAttack(1);
			card.getElementalBonus().getBonusData().setXpOnUse(1);
			card.setNegativeConditions(new NegativeConditions("Muddle"));
			break;
		case 11:
			card.setCardText("Attack +2. Range +5. If earth consumed, immobilize XP+1. (Unimplemented)");
			card.getData().setAttack(2);
			card.getData().setRange(5);
			card.setConsumeElemental("Earth");
			card.getElementalBonus().setNegativeConditions(new NegativeConditions("Immobilize"));
			card.getElementalBonus().getBonusData().setXpOnUse(1);
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
			card.setCardText("On the six melee attacks targeting you, gain retaliate +2. (UNIMPLEMENTED).");
			card.setCounter(new Counter(6, "OnMeleeAttack", new Effects("Retaliate", 2, 0)));
			card.getData().setConsumeFlag(true);
			break;
		case 2:
			card.setCardText("Loot +1");
			card.setEffects(new Effects("Loot", 0, 1));
			break;
		case 3:
			card.setCardText("Create one adj obstacle in an empty hex. (UNIMPLEMENTED). Infuse earth.");
			card.setInfuseElemental("Earth");
			break;
		case 4:
			card.setCardText("Move +2. All adj allies and enemies suffer 1 damage (UNIMPLEMENTED). Infuse earth.");
			card.getData().setMove(2);
			card.getData().setTarget(new Target("AllAdjacent"));
			card.setInfuseElemental("Earth");
			break;
		case 5:
			card.setCardText("Move +4");
			card.getData().setMove(4);
			break;
		case 6:
			card.setCardText("Move +3");
			card.getData().setMove(3);
			break;
		case 7:
			card.setCardText("Move +5. Jump. Immobilize all targets moved through. (Unimplemented). XP +1. Consumed on use.");
			card.getData().setMove(5);
			card.getData().setJumpFlag(true);
			card.getData().setXpOnUse(1);
			card.getData().setConsumeFlag(true);
			break;
		case 8:
			card.setCardText("All allies suffer 1 damage and gain +2 shield (UNIMPLEMENTED). Round bonus. XP +2. Card consumed on use.");
			card.getData().setTarget(new Target("AllAllies"));
			card.setEffects(new Effects("Shield", 2, 0));
			card.getData().setRoundBonusFlag(true);
			card.getData().setXpOnUse(2);
			card.getData().setConsumeFlag(true);
			break;
		case 9:
			card.setCardText("All adj allies and enemies suffer 1 damage. Move +4 Jump. XP +1. All adj allies and enemies suffer 1 damage. If you consume earth, 2 damage instead and xp +1. Card consumed on us. (NONE OF THIS IMPLEMENTED)");
			card.getData().setTarget(new Target("AllAdjacent"));
			card.getData().setMove(4);
			card.getData().setXpOnUse(1);
			card.setConsumeElemental("Earth");
			card.getElementalBonus().getBonusData().setXpOnUse(1);
			card.getData().setConsumeFlag(true);
			break;
		case 10:
			card.setCardText("Move +3");
			card.getData().setMove(3);
			break;
		case 11:
			card.setCardText("Heal +2 Range +3");
			card.setEffects(new Effects("Heal",2, 3));
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
			return "Opposing Strike";
		case 2:
			return "Crushing Grasp";
		case 3:
			return "Avalanche";
		case 4:
			return "Rumbling Advance";
		case 5:
			return "Massive Boulder";
		case 6:
			return "Backup Ammunition";
		case 7:
			return "Rock Tunnel";
		case 8:
			return "Unstable Upheaval";
		case 9:
			return "Crater";
		case 10:
			return "Dirt Tornado";
		case 11:
			return "Earthen Clod";
		}
		
		return "This is error";
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
		case 1:
			return 46;
		case 2:
			return 35;
		case 3:
			return 75;
		case 4:
			return 29;
		case 5:
			return 87;
		case 6:
			return 77;
		case 7:
			return 41;
		case 8:
			return 13;
		case 9:
			return 61;
		case 10:
			return 82;
		case 11:
			return 38;
		}
		
		return -99;
	}

}
