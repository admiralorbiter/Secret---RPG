package Gloomhaven;

import java.util.List;

import Gloomhaven.Characters.Player;

public final class RoadEventCardUtilities {
	
	public static void resolveCityEvent(EventCard card, City gloomhaven, List<Player> party) {
		int id=card.getID();
		int choice=card.getChoice();
		if(choice==1) {
			switch(id) {
				case 1:
					addNegativeConditions("Poison", party);
					break;
				case 2:
					addPositiveConditions("Bless", party);
					break;
				case 3:
					addNegativeConditions("Poison", party);
					break;
				case 4:
					changePartyGold(2, party);
					if(gloomhaven.getReputationLevel()>9)
						changePartyGold(1, party);
					break;
				case 5:
					//TODO: Need to add collective item
					break;
				case 6:
					//TODO: Lose 1 mark each
					break;
				case 7:
					changeXP(5, party);
					break;
				case 8:
					changePartyGold(-5, party);
					break;
				case 9:
					//TODO: Need consume 1 collective item
					break;
					
			}
		}else if(choice==2) {
			switch(id) {
				case 1:
					for(int i=0; i<party.size(); i++)
						party.get(i).takeDamage(3);
					break;
				case 4:
					changePartyGold(10, party);
					gloomhaven.changeReputation(2);
					break;
				case 5:
					addNegativeConditions("Curse", party);
					gloomhaven.changeReputation(1);
					break;
				case 6:
					addNegativeConditions("Wound", party);
					break;
				case 8:
					for(int i=0; i<party.size(); i++)
						party.get(i).takeDamage(3);
					break;
				case 9:
					gloomhaven.changeReputation(-1);
					break;
			}
		}
	}
	
	public static void changeXP(int xp, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getCharacterData().setXp(party.get(i).getCharacterData().getXp()+xp);
	}
	
	public static void changePartyGold(int gold, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getCharacterData().changeGold(gold);
	}
	
	public static void addNegativeConditions(String negativeConditions, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getNegativeConditions().setNegativeConditions(negativeConditions);
	}
	
	public static void addPositiveConditions(String positiveConditions, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getPositiveConditions().setPositiveConditions(positiveConditions);
		
	}
}
