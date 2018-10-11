package Gloomhaven;

public class MindThief implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject top = new CardDataObject();
		
		top.id=id; 
		switch(id) {
			case 1:
				top.name="Submissive Affliction";
				top.level=1;
				top.initiative=48;
				top.text="Attack +2 and add +1 Attack for each negative condition on target. XP +1";
				top.attack=2;
				top.addNegativesToAttack=true;
				top.experience=1;
				break;
			case 2:
				top.name="Into the Night";
				top.level=1;
				top.initiative=14;
				top.text="Loot +1.(UNIMPLEMENTED) Infuse Dark";
				top.lootRange=1;
				top.darkInfusion=true;
				break;
			case 3:
				top.name="Fearsome Blade";
				top.level=1;
				top.initiative=27;
				top.text="Attack +2. Push +3 (UNIMPLEMENTED) and XP +1";
				top.attack=2;
				top.push=3;
				top.experience=1;
				break;
			case 4:
				top.name="Feedback Loop";
				top.level=1;
				top.initiative=79;
				top.text="Augument Shield +1 on Self. Attack +1. XP +1";
				top.augment=true;
				top.continuous=true;
				top.attack=1;
				top.experience=1;
				break;
			case 5:
				top.name="Gnawing Horde";
				top.level=1;
				top.initiative=82;
				top.text="Summon Rat Swarm. Xp +2. Cont. Then consumed on discard. (UNIMPLEMENTED)";
				top.experience=2;
				top.continuous=true;
				top.lost=true;
				break;
			case 6:
				top.name="The Mind's Weakness";
				top.level=1;
				top.initiative=75;
				top.text="Augument On Melee Attack +2. Attack +1. XP +1";
				top.augment=true;
				top.continuous=true;
				top.attack=1;
				top.experience=1;
				break;
			case 7:
				top.name="Parasitic Influence";
				top.level=1;
				top.initiative=71;
				top.text="Augument On Melee Attack Heal +2. Attack +1. XP +1";
				top.augment=true;
				top.continuous=true;
				top.attack=1;
				top.experience=1;
				break;
			case 8:
				top.name="Scurry";
				top.level=1;
				top.initiative=20;
				top.text="Move +3. Attack +1.";
				top.move=3;
				top.attack=1;
				break;
			case 9:
				top.name="Perverse Edge";
				top.level=1;
				top.initiative=8;
				top.text="Attack +3. Add +2 Attack and gain XP +1 for each negative condition on target.";
				top.attack=3;
				top.addNegativesToAttack=true;
				break;
			case 10:
				top.name="Empathetic Assault";
				top.level=1;
				top.initiative=11;
				top.text="Attack +4. Range +5. Disarm. Infuse Ice. XP +2. Card consumed on discard.";
				top.attack=4;
				top.range=5;
				top.disarm=true;
				top.iceInfusion=true;
				top.experience=2;
				top.lost=true;
				break;
			default:
				top.name="If you see this, it is a mistake.";
		}
	
		return top;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject bottom = new CardDataObject();
		
		bottom.id=id;
		
		switch(id) {
			case 1:
				bottom.name="Submissive Affliction";
				bottom.level=1;
				//Force one enemy within 5 range to do 2 attack to melee
				bottom.text="Force enemy to target another enemy (UNIMPLEMENTED)";
				break;
			case 2:
				bottom.name="Into the Night";
				bottom.level=1;
				bottom.text="Invisibility on Self";
				bottom.invisible=true;
				break;
			case 3:
				bottom.name="Fearsome Blade";
				bottom.level=1;
				bottom.text="Move +4. Attack +2. XP +2. Consumed after use.";
				bottom.move=4;
				bottom.attack=2;
				bottom.experience=2;
				bottom.lost=true;
				break;
			case 4:
				bottom.name="Feedback Loop";
				bottom.level=1;
				bottom.text="Move +3. Jump. (UNIMPLEMENTED) If you end in the same hex. Perform muddle on all target enemies moved through. (UNIMPLEMENTED";
				bottom.move=3;
				bottom.jump=true;
				break;
			case 5:
				bottom.name="Gnawing Horde";
				bottom.level=1;
				bottom.text="Move +4";
				bottom.move=4;
				break;
			case 6:
				bottom.name="The Mind's Weakness";
				bottom.level=1;
				bottom.text="Attack +1. Wound target.";
				bottom.attack=1;
				bottom.wound=true;
				break;
			case 7:
				bottom.name="Parasitic Influence";
				bottom.level=1;
				bottom.text="Force enemy to move with player control (UNIMPLEMENTED).";
				break;
			case 8:
				bottom.name="Scurry";
				bottom.level=1;
				bottom.text="Loot +2. XP +1. Card Consumed on discard.";
				bottom.lootRange=2;
				bottom.experience=1;
				bottom.lost=true;
				break;
			case 9:
				bottom.name="Perverse Edge";
				bottom.level=1;
				bottom.text="Attack +1. Range +2. Stun. Infuse Ice. XP +1.";
				bottom.attack=1;
				bottom.range=2;
				bottom.stun=true;
				bottom.iceInfusion=true;
				bottom.experience=1;
				break;
			case 10:
				bottom.name="Empathetic Assault";
				bottom.level=1;
				bottom.text="Move +2. Heal +2 (Self)";
				bottom.move=2;
				bottom.heal=2;
				break;
			default:
				bottom.name="If you see this, it is a mistake.";
		}
		
		
		return bottom;
	}

	@Override
	public CardDataObject getData(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
