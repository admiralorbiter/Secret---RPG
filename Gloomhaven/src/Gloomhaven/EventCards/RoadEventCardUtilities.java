package Gloomhaven.EventCards;

import java.util.List;

import Gloomhaven.City;
import Gloomhaven.ItemLoader;
import Gloomhaven.Characters.Player;

public final class RoadEventCardUtilities {
	
	public static void resolveRoadEvent(EventCard card, City gloomhaven, List<Player> party) {
		int id=card.getID();
		int choice=card.getChoice();
		
		//System.out.println("Resolve Road Event    "+id+","+choice);
		
		if(choice==1) {
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
					break;
				case 8:
					EventCardUtilities.changeGold(-5, party);
					break;
				case 9:
					//TODO: Need consume 1 collective item
					//Not implemented
					break;
					
			}
		}else if(choice==2) {
			switch(id) {
				case 1:
					for(int i=0; i<party.size(); i++)
						party.get(i).takeDamage(3);
					break;
				case 4:
					EventCardUtilities.changeGold(10, party);
					gloomhaven.changeReputation(2);
					break;
				case 5:
					EventCardUtilities.addNegativeConditions("Curse", party);
					gloomhaven.changeReputation(1);
					break;
				case 6:
					EventCardUtilities.addNegativeConditions("Wound", party);
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

}
