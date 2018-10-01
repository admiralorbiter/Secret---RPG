package Gloomhaven;

public class CardsEnemyTest implements CardInterface {

	@Override
	public CardDataObject getTop(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardDataObject getBottom(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardDataObject getData(int id) {
		CardDataObject data = new CardDataObject();
			
		switch(id) {
		case 1:
			data.move=0;
			data.attack=2;
			data.initiative=15;
			break;
		case 2:
			data.move=0;
			data.initiative=35;
			data.attack=2;
			break;
		case 3:
			data.move=0;
			data.attack=2;
			data.initiative=50;
			break;
		case 4:
			data.move=0;
			data.attack=2;
			data.initiative=30;
			break;
		case 5:
			data.move=0;
			data.attack=2;
			data.initiative=70;
			break;
		case 6:
			data.move=0;
			data.attack=2;
			data.initiative=15;
			break;
		case 7:
			data.move=0;
			data.attack=2;
			data.initiative=55;
			break;
		case 8:
			data.move=0;
			data.attack=2;
			data.initiative=50;
			break;
		}
		
		return data;
	}

}
