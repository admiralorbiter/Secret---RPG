package Gloomhaven;

public class MindThief implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject top = new CardDataObject();
		
		switch(id) {
			case 1:
				top.text="Attack +2 and add +1 Attack for each negative condition on target. +1 XP";
				top.attack=3;
				top.addNegativesToAttack=true;
				top.initiative=48;
				top.experience=1;
			case 2:
				top.text="Loot +1.(UNIMPLEMENTED) Infuse Dark";
				top.lootRange=1;
				top.darkInfusion=true;
		}
		
		
		return null;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject bottom = new CardDataObject();
		
		switch(id) {
			case 1:
				//Force one enemy within 5 range to do 2 attack to melee
				bottom.text="Force enemy to target another enemy (UNIMPLEMENTED)";
		}
		
		
		return null;
	}

	@Override
	public CardDataObject getData(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
