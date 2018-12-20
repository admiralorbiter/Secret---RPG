package Gloomhaven.CardInterfaces;

import java.util.ArrayList;
import java.util.List;

import Gloomhaven.CardDataObject;
import Gloomhaven.Counter;
import Gloomhaven.Effects;
import Gloomhaven.ElementalConsumptionBonus;
import Gloomhaven.NegativeConditions;
import Gloomhaven.PositiveConditions;
import Gloomhaven.SimpleCardData;
import Gloomhaven.Target;
import Gloomhaven.Trigger;

public class Spellweaver implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id); 
		
		switch(id) {
			case 1:
				card.setCardText("Attack +3 Range 3 Targets +3 Gain XP +1 for each Target Card consumed on use.");
				card.getData().setAttack(3);
				card.getData().setRange(3);
				card.getData().setTarget(new Target(3));
				card.setTrigger(new Trigger("ForEachTargeted"));
				card.getTrigger().getBonusData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 2:
				card.setCardText("Attack +3. Range +4. Additionally, target all enemies on the path to primary target (Not implemented). Gain XP +1 for each one targeted. Card consumed on use.");
				card.getData().setAttack(3);
				card.getData().setRange(4);
				card.getData().setTarget(new Target("AllEnemiesOnPath"));
				card.setTrigger(new Trigger("ForEachTargeted"));
				card.getTrigger().getBonusData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 3:
				card.setCardText("Recover all your lost cards. Infuse dark. Consume after use.");
				card.setRecoverLostCards(99);
				card.setInfuseElemental("Dark");
				card.getData().setConsumeFlag(true);
				break;
			case 4:
				card.setCardText("Attack +2. Target all adj enemies.(Unimplemented) Immobilize. If you consume ice, +1 Attack");
				card.getData().setAttack(2);
				card.getData().setTarget(new Target("AllAdjacentEnemies"));
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				card.setConsumeElemental("Ice");
				card.getElementalBonus().getBonusData().setAttack(1);
				break;
			case 5:
				card.setCardText("Attack +2 Range +3. If you can consume any element, Attack +1 XP+1");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.setConsumeElemental("Any");
				card.getElementalBonus().getBonusData().setAttack(1);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 6:
				card.setCardText("Attack +2 Range +3. If you can consume any element, Attack +1 XP+1");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.setConsumeElemental("Any");
				card.getElementalBonus().getBonusData().setAttack(1);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 7:
				card.setCardText("Attack +3 Range 2. If you consume fire, Wound");
				card.getData().setAttack(3);
				card.getData().setRange(2);
				card.setConsumeElemental("Fire");
				card.getElementalBonus().setNegativeConditions(new NegativeConditions("Wound"));
				break;
			case 8:
				card.setCardText("Loot +1");
				card.setEffects(new Effects("Loot", 0, 1));
				card.getEffects().setRange(1);
				break;
			case 9:
				card.setCardText("On your next four attacks, add +1 attack. If you consume air, add +2 attack. Consume after use.");
				card.setCounter(new Counter(4, "OnAttack", new SimpleCardData()));
				card.getCounter().getBonusData().setAttack(1);
				card.setConsumeElemental("Air");
				card.getElementalBonus().getBonusData().setAttack(2);
				card.getData().setConsumeFlag(true);
				break;
			case 10:
				card.setCardText("Retaliate +2 Affect self and all adjacent allies. If consume earth, +1 retaliate. +2xp Round Bonus. Consume after use.");
				card.setEffects(new Effects("Retaliate", 2, 0));
				List<String> targets = new ArrayList<String>();
				targets.add("AllAdjacentAllies");
				targets.add("Self");
				card.getEffects().setTarget(new Target(targets));
				card.setConsumeElemental("Earth");
				card.getElementalBonus().setEffects(new Effects("Retaliate", 1, 0));
				card.getData().setXpOnUse(2);
				card.getData().setRoundBonusFlag(true);
				card.getData().setConsumeFlag(true);
				break;
			case 11:
				card.setCardText("Heal +3 Range +3");
				card.setEffects(new Effects("Heal", 3, 3));
				break;
			case 12:
				card.setCardText("Attack +3 Range +3 Infuse Light.");
				card.getData().setAttack(3);
				card.getData().setRange(3);
				card.setInfuseElemental("Light");
				break;
			case 13:
				card.setCardText("Attack in a circle of range 1. Attack +2 Range +3 Muddle Infuse Ice +2 XP. Consume after use.");
				card.getData().setTarget(new Target("Circle"));
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.setNegativeConditions(new NegativeConditions("Muddle"));
				card.setInfuseElemental("Ice");
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 14:
				card.setCardText("Attack in a triangle. Attack +1 Range +3. If you consume fire, +2 Attack. If you consume ice, Stun. (Can't handle multiple yet)");
				card.getData().setTarget(new Target("Triangle"));
				card.getData().setAttack(1);
				card.getData().setRange(3);
				card.setConsumeElemental("Fire");
				card.getElementalBonus().getBonusData().setAttack(2);
				break;
			case 15:
				card.setCardText("Heal +3 Range +2. If you consume earth, +2 Heal, +1 XP");
				card.setEffects(new Effects("Heal", 3, 2));
				card.setConsumeElemental("Earth");
				card.getElementalBonus().setEffects(new Effects("Heal", 2, 0));
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 16:
				card.setCardText("Curse Range 4. If you consume dark, kill a normal target of the ability instead, +1 XP. Consume after use");
				card.setNegativeConditions(new NegativeConditions("Curse"));
				card.getData().setRange(4);
				card.setConsumeElemental("Dark");
				card.getElementalBonus().setEffects(new Effects("InstaKill", 0, 4));
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 17:
				card.setCardText("Attack +2 Range +3 Target +2 XP +1.");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.getData().setTarget(new Target(2));
				card.getData().setXpOnUse(1);
				break;
			case 18:
				card.setCardText("Attack in a triangle. Attack +3 Range +3. Infuse all elements. XP +2. Consume after use.");
				card.getData().setTarget(new Target("Triangle"));
				card.getData().setAttack(3);
				card.getData().setRange(3);
				card.setInfuseElemental("All");
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 19:
				card.setCardText("Attack +4 Range +3. Infuse fire.");
				card.getData().setAttack(4);
				card.getData().setRange(3);
				card.setInfuseElemental("Fire");
				break;
			case 20:
				card.setCardText("Attack +4 Range +3 Immobilize. If you consume light, all enemies adjacent to the target suffer 2 damage, XP +1.");
				card.getData().setAttack(4);
				card.getData().setRange(3);
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				card.setConsumeElemental("Light");
				card.getElementalBonus().setTarget(new Target("AllAdjacentEnemies"));
				card.getElementalBonus().getBonusData().setAttack(2);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 21:
				card.setCardText("Attack in a pyramid. Attack +4 Gain +1 XP for each enemy targeted. Infuse ice. Consume after use.");
				card.getData().setTarget(new Target("Pyramid"));
				card.getData().setAttack(4);
				card.setTrigger(new Trigger("ForEachTargeted", new SimpleCardData()));
				card.getTrigger().getBonusData().setXpOnUse(1);
				card.setInfuseElemental("Ice");
				card.getData().setConsumeFlag(true);
				break;
			case 22:
				card.setCardText("Recover up to two of your lost cards. Consume after use.");
				card.setRecoverLostCards(2);
				card.getData().setConsumeFlag(true);
				break;
			case 23:
				card.setCardText("Attack +6 Push +2 Immobilize XP +2. If you consume earth, Target 2, XP +1. Consume after use.");
				card.getData().setAttack(6);
				card.setEffects(new Effects("Push", 2, 0));
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				card.getData().setXpOnUse(2);
				card.setConsumeElemental("Earth");
				card.getElementalBonus().setTarget(new Target(2));
				card.getElementalBonus().getBonusData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 24:
				card.setCardText("Loot +2. If you consume air, loot 3 instead, +1 xp. You may not loot more than four with this action.");
				card.setEffects(new Effects("Loot", 0, 2));
				card.setConsumeElemental("Air");
				card.getElementalBonus().setEffects(new Effects("Loot", 0, 3));
				break;
			case 25:
				card.setCardText("Attack in a line of 5. Attack +5 Range +2 Infuse Ice XP +2. Consume after use.");
				card.getData().setTarget(new Target("Line"));
				card.getData().setAttack(5);
				card.getData().setRange(2);
				card.setInfuseElemental("Ice");
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 26:
				card.setCardText("Attack in a triangle. Attack +4 Range +3 Gain +1 XP for each enemy targeted. If you consume dark, kill all normal enemies in targeted area. Consume after use.");
				card.getData().setTarget(new Target("Triangle"));
				card.getData().setAttack(4);
				card.getData().setRange(3);
				card.setTrigger(new Trigger("ForEachTargeted"));
				card.getTrigger().getBonusData().setXpOnUse(1);
				card.setConsumeElemental("Dark");
				card.getElementalBonus().setEffects(new Effects("InstaKill", 0, 3));
				card.getData().setConsumeFlag(true);
				break;
			case 27:
				card.setCardText("Attack +3. Target all enemies in the same room as you. If you consume fire, +1 attack, +1 XP. All allies in the same room as you suffer 2 damage. (Unimplemented)");
				card.getData().setAttack(3);
				card.getData().setTarget(new Target("AllEnemiesInRoom"));
				card.setConsumeElemental("Fire");
				card.getElementalBonus().getBonusData().setAttack(1);
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
				card.setCardText("Move +3");
				card.getData().setMove(3);
				break;
			case 2:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 3:
				card.setCardText("Move +4 Jump.");
				card.getData().setMove(4);
				card.getData().setJumpFlag(true);
				break;
			case 4:
				card.setCardText("Heal +4 Range +4. Infuse Light. Consume on use");
				card.setEffects(new Effects("Heal", 4, 4));
				card.setInfuseElemental("Light");
				card.getData().setConsumeFlag(true);
				break;
			case 5:
				card.setCardText("Heal +3 Range +1");
				card.setEffects(new Effects("Heal", 3, 1));
				break;
			case 6:
				card.setCardText("Infuse Ice. On the next two sources of damage on you, suffer no damage. (Persistent) Consume after use.");
				card.setInfuseElemental("Ice");
				card.setCounter(new Counter(2, "OnDamage", "NoDamage"));
				card.getData().setConsumeFlag(true);
				break;
			case 7:
				card.setCardText("Attack +2 Range +2 XP +2");
				card.getData().setAttack(2);
				card.getData().setRange(2);
				card.getData().setXpOnUse(2);
				break;
			case 8:
				card.setCardText("Move +8. Jump. Infuse air. Card consumed on use.");
				card.getData().setMove(8);
				card.getData().setJumpFlag(true);
				card.setConsumeElemental("Air");
				card.getData().setConsumeFlag(true);
				break;
			case 9:
				card.setCardText("Move +3. If you consume fire, Retaliate +2 (Self) Round Bonus");
				card.getData().setMove(3);
				card.setConsumeElemental("Fire");
				card.getElementalBonus().setEffects(new Effects("Retaliate", 2, 0));
				card.getData().setRoundBonusFlag(true);
				break;
			case 10:
				card.setCardText("Move +3 If you consume ice, shield +2 (Self) Round Bonus");
				card.getData().setMove(3);
				card.setConsumeElemental("Ice");
				card.getElementalBonus().setEffects(new Effects("Shield", 2, 0));
				card.getData().setRoundBonusFlag(true);
				break;
			case 11:
				card.setCardText("Summon Mystic Ally (H2 A3 M2 R2) +2 XP Continuous (Unimplemented). Consume after use");
				card.getData().setConsumeFlag(true);
				break;
			case 12:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 13:
				card.setCardText("Heal +6 Range +2. Consume after use.");
				card.setEffects(new Effects("Heal", 6, 2));
				card.getData().setConsumeFlag(true);
				break;
			case 14:
				card.setCardText("Loot +2. Consume after use.");
				card.setEffects(new Effects("Loot", 0, 2));
				card.getData().setConsumeFlag(true);
				break;
			case 15:
				card.setCardText("Shield +2 Affect any one ally. If you consume air, affect all allies instead. Round bonus.");
				card.setEffects(new Effects("Shield", 2, 0));
				card.getEffects().setTarget(new Target("Ally"));
				card.getEffects().getTarget().setAllyTargets(1);
				card.setConsumeElemental("Air");
				card.getElementalBonus().setTarget(new Target("AllAllies"));
				card.getData().setRoundBonusFlag(true);
				break;
			case 16:
				card.setCardText("Heal X. Affect any one ally where X is half of that ally's maximum hit point value (rounded down) +1 XP. If you consume light, affect maximum hit point instead. Consume after use. (Whole card Unimplemented)");
				card.getData().setConsumeFlag(true);
				break;
			case 17:
				card.setCardText("Move +4");
				card.getData().setMove(4);
				break;
			case 18:
				card.setCardText("Move +2 Infuse Any");
				card.getData().setMove(2);
				card.setInfuseElemental("Any");
				break;
			case 19:
				card.setCardText("On the next five melee attacks targeting you, gain retaliate +3. Consume after use.");
				card.setCounter(new Counter(5, "OnMeleeAttack", new Effects("Retaliate", 3, 0)));
				card.getData().setConsumeFlag(true);
				break;
			case 20:
				card.setCardText("Summon Burning Avatar (H2 A2 Infuse Fire M3 R3 On death: Attack 3 all adjacent enemies) (Unimplemented) +2 XP Continous. Consume after use.");
				card.getData().setXpOnUse(2);
				card.getData().setConsumeFlag(true);
				break;
			case 21:
				card.setCardText("Move +3. If you consume dark, +2 Move, Invisible, +1 XP (Self).");
				card.getData().setMove(3);
				card.setConsumeElemental("Dark");
				card.getElementalBonus().getBonusData().setMove(2);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				card.setPositiveConditions(new PositiveConditions("Invisible"));
				break;
			case 22:
				card.setCardText("Heal +3 Range +3 Targets 2");
				card.setEffects(new Effects("Heal", 3, 3));
				card.getEffects().setTarget(new Target(2));
				break;
			case 23:
				card.setCardText("Move +3 Shield +1 Round Bonus. If you consume earth, +1 Move, +1 Shield, +1 XP.");
				card.getData().setMove(3);
				card.setEffects(new Effects("Shield", 1, 0));
				card.getData().setRoundBonusFlag(true);
				card.setConsumeElemental("Earth");
				card.getElementalBonus().getBonusData().setMove(1);
				card.getElementalBonus().setEffects(new Effects("Shield", 2, 0));
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 24:
				card.setCardText("Move +8 Jump XP +1. If you consume air, +3 Move, +1 XP. Consume after use.");
				card.getData().setMove(8);
				card.getData().setJumpFlag(true);
				card.setConsumeElemental("Air");
				card.getElementalBonus().getBonusData().setMove(3);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				card.getData().setConsumeFlag(true);
				break;
			case 25:
				card.setCardText("On the next three sources of damage from attacks targeting you, suffer no damage instead and gain retaliate +3 range +3. Consume after use.");
				card.setCounter(new Counter(3, "OnDamage", new Effects("Retaliate", 3, 3)));
				card.getData().setConsumeFlag(true);
				break;
			case 26:
				card.setCardText("Choose a hex within Range 4, Pull +2 Target all enemies within range +4 of the chosen hex and pull them toward it. (Unimplemented)");
				break;
			case 27:
				card.setCardText("Retaliate +2 Range +3 Affect self and all allies. Round bonus.");
				card.setEffects(new Effects("Retaliate", 2, 3));
				List<String> targets = new ArrayList<String>();
				targets.add("AllAllies");
				targets.add("Self");
				card.getEffects().setTarget(new Target(targets));
				card.getData().setRoundBonusFlag(true);
				break;	

		}
		
		return card;
	}

	@Override
	public CardDataObject getData(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id);
		
		return card;
	}

	@Override
	public String getName(int id) {
		switch(id) {
			case 1:
				return "Name";
			case 2:
				return "Name";
			case 3:
				return "Name";
			case 4:
				return "Name";
			case 5:
				return "Name";
			case 6:
				return "Name";
			case 7:
				return "Name";
			case 8:
				return "Name";
			case 9:
				return "Name";
			case 10:
				return "Name";
			case 11:
				return "Name";
			case 12:
				return "Name";
			case 13:
				return "Name";
			case 14:
				return "Name";
			case 15:
				return "Name";
			case 16:
				return "Name";
			case 17:
				return "Name";
			case 18:
				return "Name";
			case 19:
				return "Name";
			case 20:
				return "Name";
			case 21:
				return "Name";
			case 22:
				return "Name";
			case 23:
				return "Name";
			case 24:
				return "Name";
			case 25:
				return "Name";
			case 26:
				return "Name";
			case 27:
				return "Name";
		}
		return "Error";
	}

	@Override
	public int getInitiative(int id) {
		switch(id) {
			case 1:
				return 0;
			case 2:
				return 0;
			case 3:
				return 0;
			case 4:
				return 0;
			case 5:
				return 0;
			case 6:
				return 0;
			case 7:
				return 0;
			case 8:
				return 0;
			case 9:
				return 0;
			case 10:
				return 0;
			case 11:
				return 0;
			case 12:
				return 0;
			case 13:
				return 0;
			case 14:
				return 0;
			case 15:
				return 0;
			case 16:
				return 0;
			case 17:
				return 0;
			case 18:
				return 0;
			case 19:
				return 0;
			case 20:
				return 0;
			case 21:
				return 0;
			case 22:
				return 0;
			case 23:
				return 0;
			case 24:
				return 0;
			case 25:
				return 0;
			case 26:
				return 0;
			case 27:
				return 0;
		}
		return -999;
	}

}
