package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject;
import Gloomhaven.Trigger;

public class MindThief implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.setID(id); 
	
		switch(id) {
			case 1:
				card.setCardText("Attack +2 and (add +1 Attack for each negative condition on target.) Unimplemented. XP +1");
				card.getData().setAttack(2);
				card.setTrigger(new Trigger("ForEachNegativeCondition"));
				card.getTrigger().getBonusData().setAttack(1);
				card.getData().setXpOnUse(1);
				break;
			case 2:
				card.name="Into the Night";
				card.level=1;
				card.initiative=14;
				card.text="Loot +1. Infuse Dark";
				card.lootRange=1;
				card.darkInfusion=true;
				break;
			case 3:
				card.name="Fearsome Blade";
				card.level=1;
				card.initiative=27;
				card.text="Attack +2. Push +3 and XP +1";
				card.attack=2;
				card.push=3;
				card.experience=1;
				break;
			case 4:
				card.name="Feedback Loop";
				card.level=1;
				card.initiative=79;
				card.text="Augument Shield +1 on Self. Attack +1. XP +1";
				card.augmentText="Shield +1 on Self";
				card.augment=true;
				card.continuous=true;
				card.attack=1;
				card.experience=1;
				break;
			case 5:
				card.name="Gnawing Horde";
				card.level=1;
				card.initiative=82;
				card.text="Summon Rat Swarm. Xp +2. Cont. Then consumed on discard. (UNIMPLEMENTED)";
				card.experience=2;
				card.continuous=true;
				card.complex=true;
				break;
			case 6:
				card.name="The Mind's Weakness";
				card.level=1;
				card.initiative=75;
				card.text="Augument On Melee Attack +2. Attack +1. XP +1";
				card.augmentText="On Melee Attack +2";
				card.augment=true;
				card.continuous=true;
				card.attack=1;
				card.experience=1;
				break;
			case 7:
				card.name="Parasitic Influence";
				card.level=1;
				card.initiative=71;
				card.text="Augument On Melee Attack Heal +2. Attack +1. XP +1";
				card.augmentText="On Melee Attack Heal +2";
				card.augment=true;
				card.continuous=true;
				card.attack=1;
				card.experience=1;
				break;
			case 8:
				card.name="Scurry";
				card.level=1;
				card.initiative=20;
				card.text="Move +3. Attack +1.";
				card.move=3;
				card.attack=1;
				break;
			case 9:
				card.name="Perverse Edge";
				card.level=1;
				card.initiative=8;
				card.text="Attack +3. Add +2 Attack and gain XP +1 for each negative condition on target.";
				card.attack=3;
				card.addNegativesToAttack=true;
				break;
			case 10:
				card.name="Empathetic Assault";
				card.level=1;
				card.initiative=11;
				card.text="Attack +4. Range +5. Disarm. Infuse Ice. XP +2. Card consumed on discard.";
				card.attack=4;
				card.range=5;
				card.getNegativeEffects().setDisarm(true);
				card.iceInfusion=true;
				card.experience=2;
				card.lost=true;
				break;
			default:
				card.name="If you see this, it is a mistake.";
		}
	
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		
		switch(id) {
			case 1:
				card.name="Submissive Affliction";
				card.level=1;
				//Force one enemy within 5 range to do 2 attack to melee
				card.text="Force enemy to target another enemy at Range 5. Attack +2 Range +0";
				card.mindControl=true;
				card.range=5;
				card.mindControlData.attack=2;
				card.mindControlData.range=0;
				break;
			case 2:
				card.name="Into the Night";
				card.level=1;
				card.text="Invisibility on Self";
				card.invisible=true;
				break;
			case 3:
				card.name="Fearsome Blade";
				card.level=1;
				card.text="Move +4. Attack +2. XP +2. Consumed after use.";
				card.move=4;
				card.attack=2;
				card.experience=2;
				card.lost=true;
				break;
			case 4:
				card.name="Feedback Loop";
				card.level=1;
				card.text="Move +3. Jump. If you end in the same hex. Perform muddle on all target enemies moved through. (UNIMPLEMENTED";
				card.move=3;
				card.jump=true;
				break;
			case 5:
				card.name="Gnawing Horde";
				card.level=1;
				card.text="Move +4";
				card.move=4;
				break;
			case 6:
				card.name="The Mind's Weakness";
				card.level=1;
				card.text="Attack +1. Wound target.";
				card.attack=1;
				card.getNegativeEffects().setWound(true);
				break;
			case 7:
				card.name="Parasitic Influence";
				card.level=1;
				card.text="Force enemy to move with player control of range 4. Move +1.";
				card.mindControl=true;
				card.range=4;
				card.mindControlData.move=1;
				card.mindControlData.attack=0;
				card.mindControlData.range=1;
				break;
			case 8:
				card.name="Scurry";
				card.level=1;
				card.text="Loot +2. XP +1. Card Consumed on discard.";
				card.lootRange=2;
				card.experience=1;
				card.lost=true;
				break;
			case 9:
				card.name="Perverse Edge";
				card.level=1;
				card.text="Attack +1. Range +2. Stun. Infuse Ice. XP +1.";
				card.attack=1;
				card.range=2;
				card.getNegativeEffects().setStun(true);
				card.iceInfusion=true;
				card.experience=1;
				break;
			case 10:
				card.name="Empathetic Assault";
				card.level=1;
				card.text="Move +2. Heal +2 (Self)";
				card.move=2;
				card.heal=2;
				break;
			default:
				card.name="If you see this, it is a mistake.";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInitiative(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
