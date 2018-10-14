package Gloomhaven;

public class Cragheart implements CardInterface{

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		card.id=id;
		if(id>=1 && id<=11)
			card.level=1;
		
		switch(id) {
		case 1:
			card.name="Opposing Strike";
			card.initiative=46;
			card.text="Attack +3 XP +1. Attack front and back hex (UNIMPLEMENTED)";
			card.attack=3;
			card.experience=1;
			break;
		case 2:
			card.name="Crushing Grasp";
			card.initiative=35;
			card.text="Attack +3 Immobilize. Inufse earth";
			card.attack=3;
			card.immoblize=true;
			card.earthInfusion=true;
			break;
		case 3:
			card.name="Avalanche";
			card.initiative=75;
			card.text="Attack +4 XP +1 at 2 connected tiles (Unimplemented). If you consume earth, Attack +1 XP +1. (UNIMPLEMENTED) Card consumed on use.";
			card.attack=4;
			card.experience=1;
			card.lost=true;
			break;
		case 4:
			card.name="Rumbling Advance";
			card.initiative=29;
			card.text="Heal +4. Range 2. Infuse earth";
			card.heal=4;
			card.range=2;
			card.earthInfusion=true;
			break;
		case 5:
			card.name="Massive Boulder";
			card.initiative=87;
			card.text="Attack +3 Range +3 All adj allies and enemies suffer 1 damage (UNIMPLEMENTED). Infuse earth.";
			card.attack=3;
			card.range=3;
			break;
		case 6:
			card.name="Backup Ammunition";
			card.initiative=77;
			card.text="On your next four ranged attacks, gain Target +1 (Unimplemented). Consumed after use.";
			card.targetNum=1;
			card.continuous=true;
			break;
		case 7:
			card.name="Rock Tunnel";
			card.initiative=41;
			card.text="Destroy one adj obstacle. (UNIMPLEMENTED) XP +1";
			card.experience=1;
			break;
		case 8:
			card.name="Unstable Upheaval";
			card.initiative=13;
			card.text="Attack +3 Target all adj enemies (Unimplemented). If you consume earth, range +2, XP+1. (UNIMPlemented) All adj allies suffer +2 damage. XP +1. Card consumed on use.";
			card.attack=3;
			card.experience=1;
			card.lost=true;
			break;
		case 9:
			card.name="Crater";
			card.initiative=61;
			card.text="Attack +3 Range +3. If you consume earth, push +2, XP+1. (Unimplemented).";
			card.attack=3;
			card.range=3;
			break;
		case 10:
			card.name="Dirt Tornado";
			card.initiative=82;
			card.text="Attack +1. Range +2. Attack in a circle. (Unimplemented). If consume earth, Attack +1 XP+1. (UNimplemented). Muddle all allies and enemies in target area. (Unimplemented)";
			card.attack=1;
			card.range=2;
			card.muddle=true;
			break;
		case 11:
			card.name="Earthen Clod";
			card.initiative=38;
			card.text="Attack +2. Range +5. If earth consumed, immobilize XP+1. (Unimplemented)";
			card.attack=2;
			card.range=5;
			break;
		}
		
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		card.id=id;
		if(id>=1 && id<=11)
			card.level=1;
		
		switch(id) {
		case 1:
			card.name="Opposing Strike";
			card.text="On the six melee attacks targeting you, gain retaliate +2. (UNIMPLEMENTED).";
			card.continuous=true;
			break;
		case 2:
			card.name="Crushing Grasp";
			card.text="Loot +1";
			card.lootRange=1;
			break;
		case 3:
			card.name="Avalanche";
			card.text="Create one adj obstacle in an empty hex. (UNIMPLEMENTED). Infuse earth.";
			card.earthInfusion=true;
			break;
		case 4:
			card.name="Rumbling Advance";
			card.text="Move +2. All adj allies and enemies suffer 1 damage (UNIMPLEMENTED). Infuse earth.";
			card.move=2;
			card.earthInfusion=true;
			break;
		case 5:
			card.name="Massive Boulder";
			card.text="Move +4";
			card.move=4;
			break;
		case 6:
			card.name="Backup Ammunition";
			card.text="Move +3";
			card.move=3;
			break;
		case 7:
			card.name="Rock Tunnel";
			card.text="Move +5. Jump. Immobilize all targets moved through. (Unimplemented). XP +1. Consumed on use.";
			card.move=5;
			card.jump=true;
			card.experience=1;
			card.lost=true;
			break;
		case 8:
			card.name="Unstable Upheaval";
			card.text="All allies suffer 1 damage and gain +2 shield (UNIMPLEMENTED). Round bonus. XP +2. Card consumed on use.";
			card.roundBonus=true;
			card.experience=2;
			card.lost=true;
			break;
		case 9:
			card.name="Crater";
			card.text="All adj allies and enemies suffer 1 damage. Move +4 Jump. XP +1. All adj allies and enemies suffer 1 damage. If you consume earth, 2 damage instead and xp +1. Card consumed on us. (NONE OF THIS IMPLEMENTED)";
			card.complex=true;
			card.move=4;
			card.jump=true;
			card.experience=1;
			break;
		case 10:
			card.name="Dirt Tornado";
			card.text="Move +3";
			card.move=3;
			break;
		case 11:
			card.name="Earthen Clod";
			card.text="Heal +2. Range +3.";
			card.heal=2;
			card.range=3;
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
