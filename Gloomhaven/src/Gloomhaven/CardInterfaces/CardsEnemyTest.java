package Gloomhaven.CardInterfaces;

import Gloomhaven.CardDataObject.CardDataObject;

public class CardsEnemyTest implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardDataObject getBottom(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardDataObject getData(int id) {
		CardDataObject data = new CardDataObject();
		
		switch(id) {
		case 1:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 2:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 3:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 4:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 5:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 6:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 7:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		case 8:
			data.getData().setMove(0);
			data.getData().setAttack(2);
			break;
		}
		
		return data;
	}

	@Override
	public String getName(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInitiative(int id) {
		
		
		switch(id) {
		case 1:
			return 15;
		case 2:
			return 35;
		case 3:
			return 50;
		case 4:
			return 30;
		case 5:
			return 70;
		case 6:
			return 15;
		case 7:
			return 60;
		case 8:
			return 71;
		}
		
		return 99;
	}

}
