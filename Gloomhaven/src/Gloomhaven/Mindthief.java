package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class Mindthief{
	
	int level;
	int xp;
	int health;
	int cardCount;
	List<AbilityCards> list = new ArrayList<AbilityCards>();
	Augument augument;
	StatusEffects status;
	CardDataObject cardData;
	String Class;
	
	public Mindthief() {
		xp=0;
		health=6;
		level=1;
		cardCount=10;
		Class="Mindthief";
		
		for(int i=0; i<cardCount; i++)
			list.add(new AbilityCards(level, i, Class));
	}
	
	private void playCard(AbilityCards card, boolean top) {
		if(top)
			cardData = card.getTop();
		else
			cardData = card.getBottom();
		
		
		
	}
	
}
