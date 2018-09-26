package Gloomhaven.TempStorage;

import java.util.ArrayList;
import java.util.List;

public class EnemyAbilityCards {
	String name;
	int initiative;
	boolean lost;
	boolean discard;
	boolean inPlay;
	
	
	CardDataObject cardDO = new CardDataObject();
	Cards card;
	
	public EnemyAbilityCards(int level, int id, String Class) {
		lost=false;
		discard=false;
		inPlay=false;
		
		if(Class=="Guard") {
			card = new CardsGuard();
			if(level==1) {
				switch(id)
				{
					case 1:
						name=Class;
						initiative=15;
						cardDO=card.getData(id);
						break;
					case 2:
						name=Class;
						initiative=35;
						cardDO=card.getData(id);
						break;
					case 3:
						name=Class;
						initiative=50;
						cardDO=card.getData(id);
						break;
					case 4:
						name=Class;
						initiative=30;
						cardDO=card.getData(id);
						break;
					case 5:
						name=Class;
						initiative=50;
						cardDO=card.getData(id);
						break;
					case 6:
						name=Class;
						initiative=70;
						cardDO=card.getData(id);
						break;
					case 7:
						name=Class;
						initiative=15;
						cardDO=card.getData(id);
						break;
					case 8:
						name=Class;
						initiative=55;
						cardDO=card.getData(id);
						break;
	
				}
			}
		}
		
		
	}
	
	int getAttack() {
		return cardDO.attack;
	}
	
	int getRange() {return cardDO.range;}
	
	
	Boolean cardFree() {
		if(lost)
			return false;
		if(discard)
			return false;
		if(inPlay)
			return false;
		
		return true;
	}
	
	String getText() {
		String text=initiative+": "+cardDO.text;
		return text;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
}