package Gloomhaven.EventCards;

import java.util.List;

import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.EventCard.Choice;
import Unsorted.City;
import Unsorted.ItemLoader;

public final class RoadEventCardUtilities {
	
	private RoadEventCardUtilities() {}
	
	public static void resolveRoadEvent(EventCard card, City gloomhaven, List<Player> party, List<EventCard> cityDeck) {
		int id=card.getID();
		Choice choice=card.getChoice();
		
		//System.out.println("Resolve Road Event    "+id+","+choice);
		
		if(choice==Choice.TOP || choice==Choice.ALTTOP) {
			switch(id) {
				case 1:
					EventCardUtilities.addNegativeConditions("Poison", party);
					break;
				case 2:
					EventCardUtilities.addPositiveConditions("Bless", party);
					break;
				case 3:
					EventCardUtilities.addNegativeConditions("Poison", party);
					break;
				case 4:
					EventCardUtilities.changeGold(2, party);
					if(gloomhaven.getReputationLevel()>9)
						EventCardUtilities.changeGold(1, party);
					break;
				case 5:
					gloomhaven.addCollectiveItems(ItemLoader.Load(106));
					break;
				case 6:
					EventCardUtilities.changeBattleGoalTotal(-1, party);
					break;
				case 7:
					EventCardUtilities.changeXP(5, party);
					EventCardUtilities.changeBattleGoalTotal(-1, party);
					break;
				case 8:
					EventCardUtilities.changeGold(-5, party);
					break;
				case 9:
					//TODO: Need consume 1 collective item
					//Not implemented
					break;
				case 10:
					EventCardUtilities.takeDamage(1, party);
					break;
				case 11:
					EventCardUtilities.takeDamage(2, party);
					break;
				case 13:
					EventCardUtilities.changeGold(2, party);
					break;
				case 14:
					EventCardUtilities.addPositiveConditions("Bless", party);
					break;
				case 15:
					EventCardUtilities.addNegativeConditions("Muddle", party);
					EventCardUtilities.takeDamage(2, party);
					break;
				case 16:
					EventCardUtilities.addNegativeConditions("Curse", party);
					EventCardUtilities.takeDamage(2, party);
					break;
				case 17:
					EventCardUtilities.changeGold(5, party);
					break;
				case 18:
					if(gloomhaven.getReputationLevel()>-5)
						EventCardUtilities.addNegativeConditions("Curse", party);
					break;
				case 19:
					if(gloomhaven.getReputationLevel()>-5)
						gloomhaven.changeReputation(1);
					break;
				case 20:
					EventCardUtilities.addNegativeConditions("Poison", party);
					EventCardUtilities.takeDamage(2, party);
					break;
				case 21:
					EventCardUtilities.addNegativeConditions("Wound", party);
					EventCardUtilities.takeDamage(3, party);
					EventCardUtilities.changeBattleGoalTotal(-1, party);
					break;
				case 22:
					cityDeck.add(new EventCard("City", 74));
					break;
				case 24:
					EventCardUtilities.changeGold(10, party);
					//Need to be able to unlock map
					//Not implemented.
					break;
				case 25:
					if(!card.wasThresholdMet())
						EventCardUtilities.changeGold(-5, party);
					break;
				case 26:
					if(card.wasThresholdMet()) {
						EventCardUtilities.addPositiveConditions("Bless", party);
						gloomhaven.changeReputation(2);
					}
					break;
				case 27:
					//Need discard mechanic
					//Not implemented
					break;
				case 28:
					//Need to pay using a collective item
					//Not implemented
					if(card.wasThresholdMet())
						EventCardUtilities.changeGold(20, party);
					else
						EventCardUtilities.changeGold(10, party);
					break;
				case 29:
					//Need discard mechanic
					//Not implemented
					break;
				case 30:
					//Need consume 1 item
					//Not implemented
					if(!card.wasThresholdMet())
						gloomhaven.changeReputation(1);
					break;
				default:
					System.out.println("Card had no effect.");
			}
		}else if(choice==Choice.BOTTOM || choice==Choice.ALTBOTTOM) {
			switch(id) {
				case 1:
					EventCardUtilities.takeDamage(3, party);
					break;
				case 4:
					EventCardUtilities.changeGold(10, party);
					gloomhaven.changeReputation(-2);
					break;
				case 5:
					EventCardUtilities.addNegativeConditions("Curse", party);
					gloomhaven.changeReputation(1);
					break;
				case 6:
					EventCardUtilities.addNegativeConditions("Wound", party);
					break;
				case 8:
					EventCardUtilities.takeDamage(3, party);
					break;
				case 9:
					gloomhaven.changeReputation(-1);
					break;
				case 10:
					cityDeck.add(new EventCard("City", 71));
					EventCardUtilities.takeDamage(1, party);
					break;
				case 11:
					if(card.wasThresholdMet())
						cityDeck.add(new EventCard("City", 73));
					else
						EventCardUtilities.takeDamage(4, party);
					break;
				case 12:
					//TODO need to add -1 attack modifiers to collective
					//Not implemented
					gloomhaven.changeReputation(1);
					break;
				case 13:
					EventCardUtilities.addNegativeConditions("Curse", party);
					break;
				case 16:
					EventCardUtilities.addPositiveConditions("Bless", party);
					break;
				case 17:
					gloomhaven.changeProsperity(1);
					break;
				case 18:
					EventCardUtilities.takeDamage(3, party);
					break;
				case 19:
					gloomhaven.changeReputation(-2);
					if(gloomhaven.getReputationLevel()<-9)
						gloomhaven.addCollectiveItems(ItemLoader.Load(41));
					break;
				case 20:
					EventCardUtilities.changeXP(3, party);
					break;
				case 21:
					//TODO: Need to add discard mechanics
					//Not Implemented.
					break;
				case 22:
					EventCardUtilities.changeXP(2, party);
					EventCardUtilities.addNegativeConditions("Curse", party);
					break;
				case 23:
					EventCardUtilities.changeGold(2, party);
					gloomhaven.changeReputation(-1);
					break;
				case 24:
					break;
				case 25:
					EventCardUtilities.takeDamage(3, party);
					break;
				case 26:
					EventCardUtilities.changeGold(10, party);
					gloomhaven.changeReputation(-1);
					break;
				case 27:
					EventCardUtilities.takeDamage(2, party);
					break;
				case 29:
					EventCardUtilities.addNegativeConditions("Wound", party);
					break;
				case 30:
					EventCardUtilities.changeGold(2, party);
					break;
				default:
					System.out.println("Card had no effect.");
					
			}
		}else {
			System.out.println("Error in roadeventcardutiltiies with the choice: "+choice);
		}
	}

}
