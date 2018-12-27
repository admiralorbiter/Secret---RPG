package Gloomhaven.EventCards;

import java.util.List;

import Gloomhaven.City;
import Gloomhaven.Characters.Player;

public final class CityEventCardUtilities {

	
	public static void resolveCityEvent(EventCard card, City gloomhaven, List<Player> party) {
		int id=card.getID();
		int choice=card.getChoice();
		if(choice==1) {
			switch(id) {
				case 1:
					//TODO: If everyone can't lose 5 gold, lose 1 rep.
					for(int i=0; i<party.size(); i++) {
						party.get(i).increaseXP(10);
						if(party.get(i).getCharacterData().getGold()>=5)
							party.get(i).getCharacterData().changeGold(-5);
						else
							gloomhaven.changeReputation(-1);
					}
					break;
				case 2:
					//TODO: Need a way for party to give collective gold
					break;
				case 3:
					//TODO: Need to add collective gear
					gloomhaven.addGlobalAchievements("Ancient Technology");
					break;
				case 5:
					//TOD0: Need to add and destroy battle goal check marks
					gloomhaven.changeReputation(1);
					gloomhaven.changeProsperity(1);
					break;
				case 6:
					for(int i=0; i<party.size(); i++) {
						party.get(i).increaseXP(5);
					}
					gloomhaven.changeProsperity(1);
					break;
				case 7:
					for(int i=0; i<party.size(); i++)
						party.get(i).getCharacterData().changeGold(5);
					if(gloomhaven.getReputationLevel()>9)
						gloomhaven.changeReputation(-1);
					else
						gloomhaven.changeReputation(1);
					break;
				case 8:
					//TODO: Need a way to pay collective gold
					//TODO: Unlock map places
					gloomhaven.addPartyAchievement("A Map to Treasure");
					break;
				case 9:
					for(int i=0; i<party.size(); i++)
						changePartyGold(party, 10);
					if(gloomhaven.getReputationLevel()<-9) {
						changePartyGold(party, 5);
					}
					break;
			}
		}else if(choice==2) {
			switch(id) {
				case 1:
					gloomhaven.changeReputation(1);
					break;
				case 4:
					//TODO: Need to add and destroy battle goal check marks
					gloomhaven.changeReputation(1);
					break;
				case 5:
					changePartyGold(party, 5);
					if(gloomhaven.getReputationLevel()<-4)
						changePartyGold(party, 5);
					break;
				case 6:
					//TODO: Need to gain random item design
					if(gloomhaven.getReputationLevel()>4)
						gloomhaven.changeReputation(-1);
					break;
				case 7:
					if(gloomhaven.getReputationLevel()>4)
						gloomhaven.changeReputation(1);
					else if(gloomhaven.getReputationLevel()<-9)
						gloomhaven.changeReputation(-1);
					break;
				case 9:
					gloomhaven.changeProsperity(1);
					break;
			}
		}
	}
	
	public static void changePartyGold(List<Player> party, int gold) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getCharacterData().changeGold(gold);
	}
}
