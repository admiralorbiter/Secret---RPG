package Gloomhaven.EventCards;

import java.util.List;

import Gloomhaven.City;
import Gloomhaven.ItemLoader;
import Gloomhaven.Shop;
import Gloomhaven.Characters.Player;

public final class CityEventCardUtilities {

	
	public static void resolveCityEvent(EventCard card, City gloomhaven, List<Player> party, List<EventCard> deck, Shop shop) {
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
					gloomhaven.addCollectiveItems(ItemLoader.Load(125));
					gloomhaven.addGlobalAchievements("Ancient Technology");
					break;
				case 5:
					EventCardUtilities.changeBattleGoalTotal(-1, party);
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
						EventCardUtilities.changeGold(10, party);
					if(gloomhaven.getReputationLevel()<-9) {
						EventCardUtilities.changeGold(5, party);
					}
					break;
				case 11:
					if(card.wasThresholdMet()) {
						//TODO: Add random item design
						if(gloomhaven.getReputationLevel()>9)
							gloomhaven.changeReputation(-1);
					}else {
						gloomhaven.changeReputation(-1);
					}
					break;
				case 12:
					gloomhaven.changeReputation(-1);
					break;
				case 13:
					EventCardUtilities.addPositiveConditions("Bless", party);
					EventCardUtilities.changeGold(-3, party);
					break;
				case 14:
					EventCardUtilities.changeXP(10, party);
					break;
				case 15:
					if(gloomhaven.getReputationLevel()<6)
						gloomhaven.changeReputation(-2);
					break;
				case 16:
					if(card.wasThresholdMet())
						deck.add(new EventCard("City", 70));
					break;
				case 17:
					//TODO: Need to have it so I can collective take gold and disperse it
					EventCardUtilities.changeGold(10, party);
					break;
				case 18:
					gloomhaven.changeReputation(-1);
					break;
				case 19:
					//TODO: Need to have it so it can have 3 options, not 2
					if(card.wasThresholdMet() && gloomhaven.getReputationLevel()>9)
						gloomhaven.changeReputation(2);
					else if(card.wasThresholdMet()==false && gloomhaven.getReputationLevel()<-4) {
						EventCardUtilities.changeGold(-5, party);
						gloomhaven.changeReputation(-1);
					}	else if(card.wasThresholdMet()==false) {
						EventCardUtilities.changeGold(-5, party);
					}
					break;
				case 20:
					//TODO: Need to have it so I can collective take gold and disperse it
					if(gloomhaven.getReputationLevel()>9)
						EventCardUtilities.changeGold(10, party);
					else {
						EventCardUtilities.changeXP(5, party);
						EventCardUtilities.changeGold(10, party);
					}
					break;
				case 21:
					//TODO: Need to have it so I can collective take gold and disperse it
					if(gloomhaven.getReputationLevel()>7) {
						deck.add(new EventCard("City", 70));
						EventCardUtilities.changeGold(5, party);
					}
					break;
				case 22:
					gloomhaven.changeReputation(2);
					break;
				case 23:
					gloomhaven.changeReputation(1);
					break;
				case 24:
					EventCardUtilities.changeXP(10, party);
					break;
				case 25:
					//TODO: Need to have it so I can collective take gold and disperse it
					//TODO: Need a way to pay collective gold
					if(card.wasThresholdMet())
						EventCardUtilities.changeGold(5, party);
					else
						EventCardUtilities.changeGold(-10, party);
					break;
				case 26:
					if(card.wasThresholdMet())
						gloomhaven.changeReputation(2);
					break;
				case 27:
					if(card.wasThresholdMet())
						gloomhaven.changeProsperity(1);
					else
						gloomhaven.changeReputation(-1);
					break;
				case 28:
					if(card.wasThresholdMet())
						EventCardUtilities.changeGold(10, party);
					else
						gloomhaven.changeReputation(-3);
					break;
				case 29:
					if(card.wasThresholdMet())
						gloomhaven.changeProsperity(1);
					break;
				case 30:
					if(card.wasThresholdMet()) {
						EventCardUtilities.changeGold(5, party);
						gloomhaven.addCollectiveItems(ItemLoader.Load(105));
					}else {
						EventCardUtilities.changeGold(-5, party);
					}
					break;
					
			}
		}else if(choice==2) {
			switch(id) {
				case 1:
					gloomhaven.changeReputation(1);
					break;
				case 4:
					EventCardUtilities.changeBattleGoalTotal(-1, party);
					gloomhaven.changeReputation(1);
					
					break;
				case 5:
					EventCardUtilities.changeGold(5, party);
					if(gloomhaven.getReputationLevel()<-4)
						EventCardUtilities.changeGold(5, party);
					break;
				case 6:
					//TODO: Need to gain random item design
					shop.retrieveRandomItemDesign(party.get(0));
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
				case 11:
					if(gloomhaven.getReputationLevel()<-4)
						gloomhaven.changeReputation(1);
					break;
				case 12:
					EventCardUtilities.changeBattleGoalTotal(-1, party);
					EventCardUtilities.changeGold(10, party);
					gloomhaven.changeReputation(1);
					break;
				case 14:
					gloomhaven.changeReputation(2);
					break;
				case 15:
					if(!card.wasThresholdMet())
						EventCardUtilities.changeBattleGoalTotal(-1, party);
					break;
				case 17:
					//TODO need to unlock map spaces
					break;
				case 18:
					EventCardUtilities.changeGold(2, party);
					break;
				case 19:
					if(gloomhaven.getReputationLevel()<-4) {
						//TODO: Need to have it so I can collective take gold and disperse it
						EventCardUtilities.changeGold(-5, party);
						gloomhaven.changeReputation(-1);
					}else {
						EventCardUtilities.changeGold(-5, party);
					}
					break;
				case 20:
					gloomhaven.changeProsperity(-1);
					break;
				case 21:
					deck.add(new EventCard("City", 65));
					break;
				case 22:
					EventCardUtilities.changeBattleGoalTotal(1, party);
					break;
				case 23:
					EventCardUtilities.addPositiveConditions("Bless", party);
					break;
				case 24:
					gloomhaven.changeProsperity(1);
					break;
				case 26:
					//TODO: Need to have it so I can collective take gold and disperse it
					if(card.wasThresholdMet())
						EventCardUtilities.changeGold(10, party);
					else
						gloomhaven.changeReputation(-1);
					break;
				case 27:
					deck.add(new EventCard("City", 60));
					break;
				case 29:
					if(card.wasThresholdMet())
						gloomhaven.changeProsperity(1);
					break;
				case 30:
					gloomhaven.changeReputation(-1);
					break;
			
			}
		}
	}
	
}
