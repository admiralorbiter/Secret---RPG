package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject;

public class Tinkerer implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		card.id=id;
		if(id>=1 && id<=12)
			card.level=1;
		
		switch(id) {
		case 1:
			card.name="Proximity Mine";
			card.initiative=62;
			card.text="Create one 6 damage trap in an adj empty hex (Unimplemented). Gain XP +2 when trap is sprung by enemey. (Unimplemented) Card consumed on use.";
			card.lost=true;
			break;
		case 2:
			card.name="Harmless Contraption";
			card.initiative=74;
			card.text="Summon Decoy. (Unimplemented). Persistent. XP +2";
			card.continuous=true;
			card.experience=2;
			break;
		case 3:
			card.name="Flamethrower";
			card.initiative=47;
			card.text="Attack +3 for 3 hex triangle. Wound. XP +2. Infuse Fire. Card consumed on use. ";
			card.triangle=true;
			card.attack=3;
			card.getNegativeEffects().setWound(true);
			card.experience=2;
			card.fireInfusion=true;
			card.lost=true;
			break;
		case 4:
			card.name="Hook Gun";
			card.initiative=72;
			card.text="Attack +3 Range +3 Pull +2 (Unimplemented)";
			card.attack=3;
			card.range=3;
			break;
		case 5:
			card.name="Ink Bomb";
			card.initiative=74;
			card.text="Attack +4 Range +3 for a 3 hex triangle. Gain XP +1 for each enemy targeted (Unimplemented). Infuse dark. Card consumed on use. ";
			card.triangle=true;
			card.attack=4;
			card.range=3;
			card.darkInfusion=true;
			card.lost=true;
			break;
		case 6:
			card.name="Net Shooter";
			card.initiative=19;
			card.text="Attack +3 Range +3 for a 3 hex triangle. Immobilize. Gain XP +1 for each target. (Unimplemented)";
			card.triangle=true;
			card.attack=3;
			card.range=3;
			card.getNegativeEffects().setImmobilize(true);
			break;
		case 7:
			card.name="Stun Shot";
			card.initiative=20;
			card.text="Attack +1. Range +3. Stun.";
			card.attack=1;
			card.range=3;
			card.getNegativeEffects().setStun(true);
			break;
		case 8:
			card.name="Reinvigorating Elixir";
			card.initiative=37;
			card.text="Heal +3 Range +3";
			card.heal=3;
			card.range=3;
			break;
		case 9:
			card.name="Restorative Mist";
			card.initiative=17;
			card.text="Heal +3 Range +3";
			card.heal=3;
			card.range=3;
			break;
		case 10:
			card.name="Energizing Tonic";
			card.initiative=16;
			card.text="Heal +5 Range +2 XP +2. Card consumed on use.";
			card.heal=5;
			card.range=2;
			card.experience=2;
			card.lost=true;
			break;
		case 11:
			card.name="Enhancement Field";
			card.initiative=61;
			card.text="Attack +3 Range +3";
			card.attack=3;
			card.range=3;
			break;
		case 12:
			card.name="Toxic Bolt";
			card.initiative=18;
			card.text="Attack +2 Range +3 Poison. Infuse earth";
			card.attack=2;
			card.range=3;
			card.getNegativeEffects().setPoison(true);
			card.earthInfusion=true;
			break;
		
		}
		
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		card.id=id;
		if(id>=1 && id<=12)
			card.level=1;
		
		switch(id) {
		case 1:
			card.name="Proximity Mine";
			card.text="Move +4";
			card.move=4;
			break;
		case 2:
			card.name="Harmless Contraption";
			card.text="Heal +2 Range +3";
			card.heal=2;
			card.range=3;
			break;
		case 3:
			card.name="Flamethrower";
			card.text="Shield +1. Affect self and all adj allies (Unimplemented). Round Bonus.";
			card.shield=1;
			card.roundBonus=true;
			break;
		case 4:
			card.name="Hook Gun";
			card.text="Loot +2. XP +1 Card consumed on use.";
			card.lootRange=2;
			card.experience=1;
			card.lost=true;
			break;
		case 5:
			card.name="Ink Bomb";
			card.text="Move +4";
			card.move=4;
			break;
		case 6:
			card.name="Net Shooter";
			card.text="Immobilize. Target one adjacent enemy (Unimplemented?). Move +2";
			card.range=1;
			card.getNegativeEffects().setImmobilize(true);
			card.move=2;
			break;
		case 7:
			card.name="Stun Shot";
			card.text="Move +4";
			card.move=4;
			break;
		case 8:
			card.name="Reinvigorating Elixir";
			card.text="One adj ally may recover all lost cards (Unimplemented) XP +2. Consumed on use.";
			card.experience=2;
			card.lost=true;
			break;
		case 9:
			card.name="Restorative Mist";
			card.text="Move +2 Heal +1 Affect all adj allies (Unimplemented)";
			card.move=2;
			card.heal=1;
			break;
		case 10:
			card.name="Energizing Tonic";
			card.text="Move +6. XP +1. Card consumed on use.";
			card.move=6;
			card.experience=1;
			card.lost=true;
			break;
		case 11:
			card.name="Enhancement Field";
			card.text="You and all adj allies add +1 attack to all attacks this round. (Unimplemented) Round Bonus.";
			card.roundBonus=true;
			break;
		case 12:
			card.name="Toxic Bolt";
			card.text="Attack +5 Range +2 XP +2 Card consumed on use.";
			card.attack=5;
			card.range=2;
			card.experience=2;
			card.lost=true;
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
