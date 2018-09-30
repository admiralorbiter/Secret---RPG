package Gloomhaven;

public class EnemyAbilityCards {
	private String name;
	private int initiative;
	private boolean lost;
	private boolean discard;
	private boolean inPlay;
	
	CardDataObject data = new CardDataObject();
	CardInterface card;
	
	public EnemyAbilityCards(int level, int id, String Class) {
		if(Class=="Test") {
			card = new CardsEnemyTest();
			if(level==1) {
				switch(id) {
					case 1:
						name=Class;
						initiative=15;
						data=card.getData(id);
						break;
					case 2:
						name=Class;
						initiative=35;
						data=card.getData(id);
						break;
					case 3:
						name=Class;
						initiative=50;
						data=card.getData(id);
						break;
					case 4:
						name=Class;
						initiative=30;
						data=card.getData(id);
						break;
					case 5:
						name=Class;
						initiative=50;
						data=card.getData(id);
						break;
					case 6:
						name=Class;
						initiative=70;
						data=card.getData(id);
						break;
					case 7:
						name=Class;
						initiative=15;
						data=card.getData(id);
						break;
					case 8:
						name=Class;
						initiative=55;
						data=card.getData(id);
						break;
				}
			}
		}
	}
	
	public int getAttack() {return data.attack;}
	public int getInitiatve() {return initiative;}
	
	public boolean cardFree() {
		if(lost)
			return false;
		if(discard)
			return false;
		if(inPlay)
			return false;
		
		return true;
	}
}
