package Gloomhaven;

public class Brute implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		switch(id) {
			case 1:
				card.name="Trample";
				card.level=1;
				card.initiative=72;
				card.text="Attack +3. Pierce +2.";
				card.pierce=2;
				break;
			case 2:
				card.name="Eye for an Eye";
				card.level=1;
				card.initiative=18;
				card.text="Retaliate +2. XP +1 for each Retaliate. Round Bonus";
				card.retaliateFlag=true;
				card.retaliate.attack=2;
				card.retaliate.experience=1;
				card.roundBonus=true;
				break;
			case 3:
				card.name="Sweeping Blow";
				card.level=1;
				card.initiative=64;
				card.text="Attack +2. Semi Circle of 3 tiles.";
				card.attack=2;
				card.semicircle=true;
				break;
			case 4:
				card.name="Provoking Roar";
				card.level=1;
				card.initiative=10;
				card.text="Attack +2. Disarm.";
				card.attack=2;
				card.disarm=true;
				break;
			case 5:
				card.name="Overwhelming Assault";
				card.level=1;
				card.initiative=61;
				card.text="Attack +6. XP +2. Card consumed on use.";
				card.attack=6;
				card.experience=2;
				break;
			case 6:
				card.name="Grab and Go";
				card.level=1;
				card.initiative=87;
				card.text="Loot +1";
				card.lootRange=1;
				break;
			case 7:
				card.name="Warding Strength";
				card.level=1;
				card.initiative=32;
				card.text="Attack +3. Push +2.";
				card.attack=3;
				card.push=2;
				break;
			case 8:
				card.name="Shield Bash";
				card.level=1;
				card.initiative=15;
				card.text="Attack +4. Stun. XP +2. Consumed on use.";
				card.attack=4;
				card.stun=true;
				card.experience=2;
				card.lost=true;
				break;
			case 9:
				card.name="Leaping Cleave";
				card.level=1;
				card.initiative=54;
				card.text="Attack +3 at 2 connected tiles. XP +1";
				card.attack=3;
				card.sortOfSemiCircle=true;
				card.experience=1;
				break;
			case 10:
				card.name="Spare Dagger";
				card.level=1;
				card.initiative=27;
				card.text="Attack +3. Range +3. XP +1.";
				card.attack=3;
				card.range=3;
				card.experience=1;
				break;
				
				
		}
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		switch(id) {
			case 1:
				card.name="Trample";
				card.level=1;
				card.text="Move +4. Jump. Attack +2 each enemy passed through. (UNIMPLEMENTED) XP +2. Card Consumed on discard.";
				card.move=4;
				card.jump=true;
				card.experience=2;
				break;
			case 2:
				card.name="Eye for an Eye";
				card.level=1;
				card.text="Heal +2 Range +1. Infuse earth.";
				card.heal=2;
				card.range=1;
				card.earthInfusion=true;
				break;
			case 3:
				card.name="Sweeping Blow";
				card.level=1;
				card.text="Move +3. Push +1 All Adjacent Tiles (UNIMPLEMENTED)";
				card.move=3;
				card.push=1;
				break;
			case 4:
				card.name="Provoking Roar";
				card.level=1;
				card.text="Any enemy who targets one of your adj allies with an attack targets you instead regardless of range. (UNIMPLEMENTED) Continous (Not really implemented)";
				card.taunt=true;
				break;
			case 5:
				card.name="Overwhelming Assault";
				card.level=1;
				card.text="Move +3. Push +2. Target one adjacent enemy (UNIMPLEMENTED)";
				card.move=3;
				card.push=2;
				break;
			case 6:
				card.name="Grab and Go";
				card.level=1;
				card.text="Move +4";
				card.move=4;
				break;
			case 7:
				card.name="Warding Strength";
				card.level=1;
				card.text="On the next 6 sources of damage from attacks targeting you (Maybe implemented)., gain shield +1. Continous. Conusmed on use.";
				card.triggerFlag=true;
				card.trigger.name=card.name;
				card.trigger.triggerName="PlayerTarget";
				card.trigger.triggerCount=6;
				card.trigger.shield=1;
				card.continuous=true;
				//card.lost=true;
				break;
			case 8:
				card.name="Shield Bash";
				card.level=1;
				card.text="Shield +1 (Self). Round Bonus.";
				card.shield=1;
				card.roundBonus=true;
				break;
			case 9:
				card.name="Leaping Cleave";
				card.level=1;
				card.text="Move +3. Jump. Infuse air.";
				card.move=3;
				card.jump=true;
				card.airInfusion=true;
				break;
			case 10:
				card.name="Spare Dagger";
				card.level=1;
				card.text="Attack +2";
				card.attack=2;
				break;
				
		}
		return card;
	}

	@Override
	public CardDataObject getData(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
