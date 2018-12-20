package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject;
import Gloomhaven.Effects;
import Gloomhaven.ElementalConsumptionBonus;
import Gloomhaven.NegativeConditions;
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
				card.setInfuseElementalFlag(true);
				card.setInfuseElemental("Dark");
				card.getData().setConsumeFlag(true);
				break;
			case 4:
				card.setCardText("Attack +2. Target all adj enemies.(Unimplemented) Immobilize. If you consume ice, +1 Attack");
				card.getData().setAttack(2);
				card.getData().setTarget(new Target("AllAdjacentEnemies"));
				card.setNegativeConditions(new NegativeConditions("Immobilize"));
				card.setConsumeElementalFlag(true);
				card.setConsumeElemental("Ice");
				card.setElementalBonus(new ElementalConsumptionBonus());
				card.getElementalBonus().getBonusData().setAttack(1);
				break;
			case 5:
				card.setCardText("Attack +2 Range +3. If you can consume any element, Attack +1 XP+1");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.setConsumeElementalFlag(true);
				card.setConsumeElemental("Any");
				card.setElementalBonus(new ElementalConsumptionBonus());
				card.getElementalBonus().getBonusData().setAttack(1);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 6:
				card.setCardText("Attack +2 Range +3. If you can consume any element, Attack +1 XP+1");
				card.getData().setAttack(2);
				card.getData().setRange(3);
				card.setConsumeElementalFlag(true);
				card.setConsumeElemental("Any");
				card.setElementalBonus(new ElementalConsumptionBonus());
				card.getElementalBonus().getBonusData().setAttack(1);
				card.getElementalBonus().getBonusData().setXpOnUse(1);
				break;
			case 7:
				card.setCardText("Attack +3 Range 2. If you consume fire, Wound");
				card.getData().setAttack(3);
				card.getData().setRange(2);
				card.setConsumeElementalFlag(true);
				card.setConsumeElemental("Fire");
				card.setElementalBonus(new ElementalConsumptionBonus());
				card.getElementalBonus().setNegativeConditions(new NegativeConditions("Wound"));
				break;
			case 8:
				card.setCardText("Loot +1");
				card.setEffects(new Effects("Loot"));
				card.getEffects().setRange(1);
				break;
			case 9:
				card.setCardText("On your next four attacks, add +1 attack. If you consume air, add +2 attack. Consume after use.");
				break;
			case 10:
				card.setCardText("Retaliate +2 Affect self and all adjacent allies. If consume leaf, +1 retaliate. +2xp Round Bonus. Consume after use.");
				break;
			case 11:
				card.setCardText("Heal +3 Range +3");
				break;
			case 12:
				card.setCardText("Attack +3 Range +3 Infuse Light.");
				break;
			case 13:
				card.setCardText("Attack in a circle of range 1. Attack +2 Range +3 Muddle Infuse Ice +2 XP. Consume after use.");
				break;
			case 14:
				card.setCardText("Attack in a triangle. Attack +1 Range +3. If you consume fire, +2 Attack. If you consume ice, Stun.");
				break;
			case 15:
				card.setCardText("Heal +3 Range +2. If you consume leaf, +2 Heal, +1 XP");
				break;
			case 16:
				card.setCardText("Curse Range 4. If you consume dark, kill a normal target of the ability instead, +1 XP. Consume after use");
				break;
			case 17:
				card.setCardText("Attack +2 Range +3 Target +2 XP +1.");
				break;
			case 18:
				card.setCardText("Attack in a triangle. Attack +3 Range +3. Infuse all elements. XP +2. Consuem after use.");
				break;
			case 19:
				card.setCardText("Attack +4 Range +3. Infuse fire.");
				break;
			case 20:
				card.setCardText("Attack +4 Range +3 Immobilize. If you consume light, all enemies adjacent to the target suffer 2 damage, XP +1.");
				break;
			case 21:
				card.setCardText("Attack in a pyramid. Attack +4 Gain +1 XP for each enemy targeted. Infuse ice. Consume after use.");
				break;
			case 22:
				card.setCardText("Recover up to two of your lost cards. Consume after use.");
				break;
			case 23:
				card.setCardText("Attack +6 Push +2 Immobilize XP +2. If you consume leaf, Target 2, XP +1. Consume after use.");
				break;
			case 24:
				card.setCardText("Loot +2. If you consume air, loot 3 instead, +1 xp. You may not loot more than four with this action.");
				break;
			case 25:
				card.setCardText("Attack in a line of 5. Attack +5 Range +2 Infuse Ice XP +2. Consume after use.");
				break;
			case 26:
				card.setCardText("Attack in a triangle. Attack +4 Range +3 Gain +1 XP for each enemy targeted. If you consume dark, kill all normal enemies in targeted area. Consume after use.");
				break;
			case 27:
				card.setCardText("Attack +3. Target all enemies in the same room as you. If you consume fire, +1 attack, +1 XP. All allies in the same room as you suffer 2 damage.");
				break;		
		}
		
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		if(id>=1 && id<=8)
			card.level=1;
		
		switch(id) {
			case 1:
				card.name="Fire Orbs";
				card.text="Move +3";
				card.move=3;
				break;
			case 2:
				card.name="Impaling Eruption";
				card.text="Move +4";
				card.move=4;
			case 3:
				card.name="Reviving Ether";
				card.text="Move +4. Jump";
				card.move=4;
				card.jump=true;
				break;
			case 4:
				card.name="Freezing Nova";
				card.text="Heal +4 Range +4. Infuse Light. Consume on use.";
				card.heal=4;
				card.range=4;
				card.lightInfusion=true;
				card.lost=true;
				break;
			case 5:
				card.name="Mana Bolt";
				card.text="Heal +3 Range +1";
				card.heal=3;
				card.range=1;
				break;
			case 6:
				card.name="Frost Armor";
				card.text="Infuse Ice. On the next two sources of damage on you, suffer no damage. (Persistent)";
				card.iceInfusion=true;
				card.triggerFlag=true;
				card.trigger.name=card.name;
				card.trigger.triggerCount=2;
				card.trigger.triggerName="PlayerTarget";
				card.trigger.shield=100;				//A hack
				card.continuous=true;
				break;
			case 7:
				card.name="Flame Strike";
				card.text="Attack +2 Range +2 XP +2";
				card.attack=2;
				card.range=2;
				card.experience=2;
				break;
			case 8:
				card.name="Ride the Wind";
				card.text="Move +8. Jump. Infuse air. Card consumed on use.";
				card.move=8;
				card.jump=true;
				card.airInfusion=true;
				card.lost=true;
				break;
			case 9:
				card.setCardText("Move +3. If you consume fire, Retaliate +2 (Self) Round Bonus");
				break;
			case 10:
				card.setCardText("Move +3 If you consume ice, shield +2 (Self) Round Bonus");
				break;
			case 11:
				card.setCardText("Summon Mystic Ally (H2 A3 M2 R2) +2 XP Continuous. Consume after use");
				break;
			case 12:
				card.setCardText("Move +4");
				break;
			case 13:
				card.setCardText("Heal +6 Range +2. Consume after use.");
				break;
			case 14:
				card.setCardText("Loot +2. Consume after use.");
				break;
			case 15:
				card.setCardText("Shield +2 Affect any one ally. If you consume air, affect all allies instead. Round bonus.");
				break;
			case 16:
				card.setCardText("Heal X. Affect any one ally where X is half of that ally's maximum hit point value (rounded down) +1 XP. If you consume light, affect maximum hit point instead. Consume after use.");
				break;
			case 17:
				card.setCardText("Move +4");
				break;
			case 18:
				card.setCardText("Move +2 Infuse Any");
				break;
			case 19:
				card.setCardText("On the next five melee attacks targeting you, gain retaliate +3. Consume after use.");
				break;
			case 20:
				card.setCardText("Summon Burning Avatar (H2 A2 Infuse Fire M3 R3 On death: Attack 3 all adjacent enemies) +2 XP Continous. Consume after use.");
				break;
			case 21:
				card.setCardText("Move +3. If you consume dark, +2 Move, Invisible, +1 XP (Self).");
				break;
			case 22:
				card.setCardText("Heal +3 Range +3 Targets 2");
				break;
			case 23:
				card.setCardText("Move +3 Shield +1 Round Bonus. If you consume leaf, +1 Move, +1 Shield, +1 XP.");
				break;
			case 24:
				card.setCardText("Move +8 Jump XP +1. If you consume air, +3 Move, +1 XP. Consume after use.");
				break;
			case 25:
				card.setCardText("On the next three sources of damage from attacks targeting you, suffer no damage instead and gain retaliate +3 range +3. Consume after use.");
				break;
			case 26:
				card.setCardText("Choose a hex within Range 4, Pull +2 Target all enemies within range +4 of the chosen hex and pull them toward it.");
				break;
			case 27:
				card.setCardText("Retaliate +2 Range +3 Affect self and all allies. Round bonus.");
				break;	
				
		
		}
		
		return card;
	}

	@Override
	public CardDataObject getData(int id) {
CardDataObject card = new CardDataObject();
		
		card.id=id;
		if(id>=1 && id<=10)
			card.level=1;
		
		switch(id) {
		
		
		}
		
		return card;
	}

}
