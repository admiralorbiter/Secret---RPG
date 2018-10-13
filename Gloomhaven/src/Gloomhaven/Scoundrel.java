package Gloomhaven;

public class Scoundrel implements CardInterface{

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		if(id>=1 && id<=10)
			card.level=1;
		
		switch(id) {
			case 1:
				card.name="Single Out";
				card.initiative=86;
				card.text="Attack +3. Add +2 and XP +1 when target is adj to allies. Persistent. (UNIMPLEMENTED)";
				card.attack=3;
				card.continuous=true;
				break;
			case 2:
				card.name="Flanking Strike";
				card.initiative=4;
				card.text="Attack +3. Add +2 and XP +1 when target is adj to allies. Persistent. (UNIMPLEMENTED)";
				card.attack=3;
				card.continuous=true;
				break;
			case 3:
				card.name="Smoke Bomb";
				card.initiative=12;
				card.text="Invisible on self. Infuse dark. On your next attack while you have invisbile, double the value of the attack. Persistent. (UNIMPLEMENTED). ";
				card.invisible=true;
				card.darkInfusion=true;
				card.continuous=true;
				break;
			case 4:
				card.name="Backstab";
				card.initiative=6;
				card.text="Attack +3. Add +2 Attack and gain XP +1 when target is adj to your allies. Add +2 Attack and gain XP +1 when target is adj is no adj to allies.(UNimplemented) Consumed on use.";
				card.attack=3;
				card.lost=true;
				break;
			case 5:
				card.name="Thief's Knack";
				card.initiative=23;
				card.text="Disarm one adjacent trap. (Unimplemented) XP +2";
				card.experience=2;
				break;
			case 6:
				card.name="Venom Shiv";
				card.initiative=60;
				card.text="Attack +3. Poison (Not implemented). XP +1";
				card.attack=3;
				card.poison=true;
				card.experience=1;
				break;
			case 7:
				card.name="Throwing Knives";
				card.initiative=10;
				card.text="Attack +2 Range +3 Target +2 (Not implemented). XP +1";
				card.attack=2;
				card.range=3;
				card.targetNum=2;
				card.experience=1;
				break;
			case 8:
				card.name="Quick Hands";
				card.initiative=64;
				card.text="Move +2. Attack +2";
				card.move=2;
				card.attack=2;
				break;
			case 9:
				card.name="Special Mixture";
				card.initiative=33;
				card.text="Heal +3 on Self.";
				card.heal=3;
				break;
		}
		
		return card;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject card = new CardDataObject();
		
		card.id=id;
		if(id>=1 && id<=10)
			card.level=1;
		
		switch(id) {
			case 1:
				card.name="Single Out";
				card.text="On the next 4 attacks target enemies that aren't adj to allies, add +2 attack. (UNIMPLEMENTED)";
				card.continuous=true;
				break;
			case 2:
				card.name="Flanking Strike";
				card.text="Move +5";
				card.move=5;
				break;
			case 3:
				card.name="Smoke Bomb";
				card.text="Pull +2. (UNIMPLEMENTED) Range +3.";
				card.pull=2;
				card.range=3;
				break;
			case 4:
				card.name="Backstab";
				card.text="Move +6";
				card.move=6;
				break;
			case 5:
				card.name="Thief's Knack";
				card.text="Attack +3. XP +1";
				card.attack=3;
				card.experience=1;
				break;
			case 6:
				card.name="Venom Shiv";
				card.text="Move +5";
				card.move=5;
				break;
			case 7:
				card.name="Throwing Knives";
				card.text="Loot +2";
				card.lootRange=2;
				break;
			case 8:
				card.name="Quick Hands";
				card.text="Loot +1";
				card.lootRange=1;
				break;
			case 9:
				card.name="Special Mixture";
				card.text="Move +3. Poison (Not implemented). Target one adjacent enemy (No Implemented)";
				card.move=3;
				card.poison=true;
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
