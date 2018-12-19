package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject;

public class Spellweaver implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		if(id>=1 && id<=8)
			card.level=1;
		
		switch(id) {
			case 1:
				card.name="Fire Orbs";
				card.initiative=69;
				card.text="Attack +3 Range 3 Targets +3 Gain XP +1 for each Target Card consumed on use.";
				card.attack=3;
				card.range=3;
				card.flag="forEachTargeted";
				card.targetNum=3;
				card.forEachTargetedData.experience=1;
				card.lost=true;
				break;
			case 2:
				card.name="Impaling Eruption";
				card.initiative=70;
				card.text="Attack +3. Range +4. Additionally, target all enemies on the path to primary target (Not implemented). Gain XP +1 for each one targeted. Card consumed on use.";
				card.attack=3;
				card.range=4;
				card.flag="forEachTargeted";
				card.forEachTargetedData.experience=1;
				card.lost=true;
				break;
			case 3:
				card.name="Reviving Ether";
				card.initiative=80;
				card.text="Recover all your lost cards. Infuse dark. Consume after use.";
				card.recoverLostCards=true;
				card.darkInfusion=true;
				card.lost=true;
				break;
			case 4:
				card.name="Freezing Nova";
				card.initiative=21;
				card.text="Attack +2. Target all adj enemies.(Unimplemented) Immobilize. If you consume ice, +1 Attack";
				card.attack=2;
				card.elementalConsumed=true;
				card.elementalConsumedData.elemental="Ice";
				card.elementalConsumedData.attack=1;
				card.getNegativeEffects().setImmobilize(true);
				break;
			case 5:
				card.name="Mana Bolt";
				card.initiative=7;
				card.text="Attack +2 Range +3. If you can consume any element, Attack +1 XP+1";
				card.attack=2;
				card.range=3;
				card.elementalConsumed=true;
				card.elementalConsumedData.elemental="Any";
				card.elementalConsumedData.attack=1;
				card.elementalConsumedData.experience=1;
				break;
			case 6:
				card.name="Frost Armor";
				card.initiative=20;
				card.text="Attack +2 Range +3. If you can consume any element, Attack +1 XP+1";
				card.attack=2;
				card.range=3;
				card.elementalConsumed=true;
				card.elementalConsumedData.elemental="Any";
				card.elementalConsumedData.attack=1;
				card.elementalConsumedData.experience=1;
				break;
			case 7:
				card.name="Flame Strike";
				card.initiative=36;
				card.text="Attack +3 Range 2. If you consume fire, Wound";
				card.attack=3;
				card.range=2;
				card.elementalConsumed=true;
				card.elementalConsumedData.elemental="Fire";
				card.elementalConsumedData.wound=true;
				break;
			case 8:
				card.name="Ride the Wind";
				card.initiative=83;
				card.text="Loot +1";
				card.lootRange=1;
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
