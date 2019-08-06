package Gloomhaven.EventCards;

import java.util.ArrayList;
import java.util.List;

import Unsorted.City;

public final class CityEventCardLoader {

	public static String cardText(int id) {
		String option="Need to add text for each event card";
		
		return option;
	}
	
	public static String cityA(int id) {
		String option="Error";
		
		
		switch(id) {
			case 1:
				return "Join the fray! These insults will not go unanswered.";
			case 2:
				return "Pay for the thing. You never know.";
			case 3:
				return "Pay for the thing. You never know.";
			case 4:
				return "Find a cat? You have more important things to do.";
			case 5:
				return "Go help lay the foundation.";
			case 6:
				return "Go aid in the defense of the city.";
			case 7:
				return "Help the captain load his ship.";
			case 8:
				return "The map does lookk valuable. Deciede to bargain for it.";
			case 9:
				return "Sell the goods.";
			case 10:
				return "Error: Need to get card "+id+"  uploaded.";
			case 11:
				return "Make a deal with the Harrower";
			case 12:
				return "Yield to the pie and admit defeat";
			case 13:
				return "Stop and try the food.";
			case 14:
				return "Raise arms and fight the Lurkers back into the sea.";
			case 15:
				return "Do your best to explain that the man was like this when you found him.";
			case 16:
				return "Intervene on the boy's behalf.";
			case 17:
				return "Deman a different payment. The single coin is an insult.";
			case 18:
				return "Decline to help the old woman.";
			case 19:
				return "Attend the wedding with an expensive gift.";
			case 20:
				return "Agree to guard the goods. It should be easy money.";
			case 21:
				return "Deman payment up front before agreeing to look for the man's brother.";
			case 22:
				return "Further investigate the exchange between the men.";
			case 23:
				return "Give chase. Thieves must be brought to justice.";
			case 24:
				return "Join the expedition as a guard.";
			case 25:
				return "Attempt to catch the vase.";
			case 26:
				return "Offer to fix the lamps. It shouldn't be too hard with a bit of technical know how.";
			case 27:
				return "Attempt to stop the crowd from hanging the Vermling.";
			case 28:
				return "Accept the mysterious job. You can handle anything.";
			case 29:
				return "Talk to the Savvas, appealing to their sense of duty and community.";
			case 30:
				return "Give chase! No one steals from you and gets away with it.";
		}
		
		return option;
	}
	
	public static String cityB(int id) {
		String option="Error";
		
		switch(id) {
			case 1:
				return "Do your best to stop the fighting. This is a respectable establishment.";
			case 2:
				return "Refuse to pay. Never trust a Vermling.";
			case 3:
				return "Refuse to pay. Never trust a Vermling.";
			case 4:
				return "Reassure the boys and go find the cat.";
			case 5:
				return "Take the opportunity to steal some valuables in the area while people are distracted.";
			case 6:
				return "If the guards are distracted by an attacking force, now would be the perfect time to steal some valuables.";
			case 7:
				return "Move on with your business. You don't have the time or inclination for such things.";
			case 8:
				return "Refuse to deal with the merchant.";
			case 9:
				return "Donate the goods to the city.";
			case 10:
				return "Error: Need to get card "+id+"  uploaded.";
			case 11:
				return "Shake your head and walk away. Best not to take chances in such situations.";
			case 12:
				return "Power through the pain. You will not be bested!";
			case 13:
				return "Continue on your way to a less adventurous meal.";
			case 14:
				return "Approach the Lurkers cautiously and attempt to communicate with them.";
			case 15:
				return "Panic and kill the guard, then dispose of both corpses.";
			case 16:
				return "Let the guards haul the boy away to prison.";
			case 17:
				return "Accept the strange coin and reseach it for hidden value.";
			case 18:
				return "Agree to help with the rat infestation.";
			case 19:
				return "Attend the wedding and bring a mundane gifts.";
			case 20:
				return "Politely decline. You have much better things to do tonight.";
			case 21:
				return "Agree to help the man in his search.";
			case 22:
				return "Leave the criminal element alone and continue enjoying the show.";
			case 23:
				return "The thief had the right idea. Grab some vegetables for yourself.";
			case 24:
				return "Join the expedition as logger.";
			case 25:
				return "With no time to react, watch the vase fall to the ground.";
			case 26:
				return "Take the opportunity to steal some valuables from drunk patrons in the dark.";
			case 27:
				return "Take no action and see this through to its logical conclusion.";
			case 28:
				return "Incredulously explain she must have the wrong people and you won't take the job.";
			case 29:
				return "Talk to the managers and attempt to get the Savvas better pay.";
			case 30:
				return "Take a clear shot at him with a bow before he disappears into the grating.";
		}
		
		return option;
	}
	
	public static boolean destroyCard(int id, int choice) {

		switch(id) {
			case 1:
				return false;
			case 2:
				return false;
			case 3:
				if(choice==1 || choice==2)
					return true;
			case 4:
				return true;
			case 5:
				return true;
			case 6:
				return true;
			case 7:
				return true;
			case 8:
				if(choice==1 || choice==2)
					return true;
			case 9:
				return true;
			case 10:
				return false;
			case 11:
				return true;
			case 12:
				return true;
			case 13:
				return false;
			case 14:
				return true;
			case 15:
				return true;
			case 16:
				return true;
			case 17:
				return true;
			case 18:
				return false;
			case 19:
				return true;
			case 20:
				if(choice==1)
					return false;
				return true;
			case 21:
				return true;
			case 22:
				if(choice==1)
					return true;
				return false;
			case 23:
				return true;
			case 24:
				return false;
			case 25:
				return true;
			case 26:
				return true;
			case 27:
				return true;
			case 28:
				return true;
			case 29:
				if(choice==1 || choice==2)
					return true;
				return false;
			case 30:
				if(choice==1 || choice==2)
					return true;
				return false;
		}
		
		return false;
	}
	
	public static String resultsA(int id) {
		String option="Error";
		
		switch(id) {
			case 1:
				return "Gain 10 experience each. Either lose 5 gold each or lose 1 repuatation.";
			case 2:
				return "No effect";
			case 3:
				return "Two choices: Gain 1 collective Curious Gear (Item 125) Global Achievement: Ancient Technology or don't have enough gold.";
			case 4:
				return "No effect";
			case 5:
				return "Lose 1 check mark. Gain 1 reputation. Gain 1 prosperity";
			case 6:
				return "Gain 5 experience each. Gain 1 prosperity.";
			case 7:
				return "Gain 5 gold each. Rep >9: Lose 1 Rep. Rep <-4: Gain 1 Rep.";
			case 8:
				return "Unlock Sunken Vessel (n-17) Party Achievement: A map to treasure. Otherwise not enough gold. No effect";
			case 9:
				return "Gain 10 gold each. Rep <-9: Gain 5 additional gold each.";
			case 10:
				return "Error: Need to get card "+id+"  uploaded.";
			case 11:
				return "Pay 5 collective gold. Gain Random Item Design Rep>9: Lose 1 rep otherwise, Lose 1 rep.";
			case 12:
				return "Lose 1 Rep.";
			case 13:
				return "All start scenario with Bless. Lose 3 gold each.";
			case 14:
				return "Gain 10 experience each.";
			case 15:
				return "Rep >5: No effect. Otherwise, lose 2 rep.";
			case 16:
				return "Pay 10 collective gold. Add city event 70 to deck. Otherise, no effect.";
			case 17:
				return "Gain 10 collective gold.";
			case 18:
				return "Lose 1 rep.";
			case 19:
				return "Rep>9. Pay 20 collective gold. Gain 2 rep. Rep<10, pay 20 gold, no effect. Otherwise. option b.";
			case 20:
				return "Rep>9: Gain 10 collective gold. Otherwise, gain 5 experience each. Gain 10 collective gold.";
			case 21:
				return "Rep>7: Gain 5 collective gold. Add road event 65 to the deck. Otherwise, no effect.";
			case 22:
				return "Gain 2 rep.";
			case 23:
				return "Gain 1 rep.";
			case 24:
				return "Gain 10 experience each.";
			case 25:
				return "Brute/Craghart/Berseker - Gain collective gold. Otherwise, lose 10 collective gold.";
			case 26:
				return "Tinkerer - Gain 2 rep. Otherwise, no effect.";
			case 27:
				return "Spellweaver/Doomstalker/Soothsinger - Gain 1 prosperity. Otherwise, lose 1 rep.";
			case 28:
				return "Scoundrel/Mindthief/Nightshroud - Gain 10 gold each. Othewrise, lose 3 rep.";
			case 29:
				return "Cragheart/Elementalist - Gain 1 prosperity. Othewrise, no effect.";
			case 30:
				return "Mindthief - Gain 5 gold each. Gain 1 collective Flea Bitten Shawl (Item 105). Otherwise, lose 5 gold each.";
		}
		
		return option;
	}
	
	public static String resultsB(int id) {
		String option="Error";
		
		switch(id) {
			case 1:
				return "Gain 1 reputation.";
			case 2:
				return "No effect.";
			case 3:
				return "No effect.";
			case 4:
				return "Lost 1 check mark each. Gain 1 reputation.";
			case 5:
				return "Gain 5 gold each. Repuatation <-4: Gain 5 additional gold each.";
			case 6:
				return "Gain random item deseign. Reputation >4: Lose 1 repuation.";
			case 7:
				return "Rep >4: Gain 1 rep. Rep <-9: Lose 1 rep.";
			case 8:
				return "No effect.";
			case 9:
				return "Gain 1 prosperity";
			case 10:
				return "Error: Need to get card "+id+"  uploaded.";
			case 11:
				return "Rep <-4: Gain 1 rep.";
			case 12:
				return "Lose 1 battle goal. Gain 10 gold each. Gain 1 rep.";
			case 13:
				return "No Effect.";
			case 14:
				return "Gain 2 rep.";
			case 15:
				return "Pay 15 collective gold. No effect. Otherwise, lose 1 battle goal.";
			case 16:
				return "No Effect.";
			case 17:
				return "Unlock Temple of the Eclipse.";
			case 18:
				return "Gain 2 gold each.";
			case 19:
				return "Rep<-4: Lose 5 collective gold. Lose 1 rep. Otherise, lose 5 collective gold.";
			case 20:
				return "Lose 1 prosperity.";
			case 21:
				return "Add road event 655 to deck.";
			case 22:
				return "Gain 1 battle goal mark.";
			case 23:
				return "All start scenario with Bless.";
			case 24:
				return "Gain 1 prosperity.";
			case 25:
				return "No effect.";
			case 26:
				return "Scoundrel/Mindthief/Nightshroud - Gain 10 collective gold. Otherwise, lose 1 rep.";
			case 27:
				return "Add city event 60 to the deck.";
			case 28:
				return "No effect.";
			case 29:
				return "Scoundrel/Sawbones/Soothsinger - Gain 1 prosperity. Otherwise, no effect.";
			case 30:
				return "Lose 1 rep.";
		}
		
		return option;
	}
	
	public static void determineThresholdForResults(EventCard card, City gloomhaven) {
		int id=card.getID();
		int choice = card.getChoice();
		
		card.setThreshold(false);
		
		if(choice==1) {
			switch(id) {
				case 2:
					card.setThresholdType("PayCollectiveGold");
					card.setThresholdAmount(10);
					card.setThreshold(true);
					break;
				case 3:
					card.setThresholdType("PayCollectiveGold");
					card.setThresholdAmount(10);
					card.setThreshold(true);
					break;
				case 8:
					card.setThresholdType("PayCollectiveGold");
					if(gloomhaven.getReputationLevel()>9)
						card.setThresholdAmount(15);
					else
						card.setThresholdAmount(20);
					card.setThreshold(true);
					break;
				case 11:
					card.setThresholdType("PayCollectiveGold");
					card.setThresholdAmount(3);
					card.setThreshold(true);
					break;
				case 16:
					card.setThresholdType("PayCollectiveGold");
					card.setThresholdAmount(10);
					card.setThreshold(true);
					break;
				case 19:
					card.setThresholdType("PayCollectiveGold");
					card.setThresholdAmount(20);
					card.setThreshold(true);
					break;
				case 25:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
				case 26:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
				case 27:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
				case 28:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
				case 29:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
				case 30:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
			}
		}else if(choice==2) {
			switch(id) {
				case 15:
					card.setThresholdType("PayCollectiveGold");
					card.setThresholdAmount(15);
					card.setThreshold(true);
					break;
				case 26:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
				case 29:
					card.setThresholdType("Class");
					card.setThreshold(true);
					break;
			}
		}
	}
	
	public static List<String> thresholdClassList(EventCard card){
		int id=card.getID();
		int choice = card.getChoice();
		List<String> classes = new ArrayList<String>();
		if(choice==1) {
			switch(id) {
				case 25:
					classes.add("Brute");
					classes.add("Cragheart");
					classes.add("Berseker");
					return classes;
				case 26:
					classes.add("Tinkerer");
					return classes;
				case 27:
					classes.add("Spellweaver");
					classes.add("Doomstalker");
					classes.add("Soothsinger");
					return classes;
				case 28:
					classes.add("Scoundrel");
					classes.add("Mind Thief");
					classes.add("Nighshroud");
					return classes;
				case 29:
					classes.add("Cragheart");
					classes.add("Elementalist");
					return classes;
				case 30:
					classes.add("Mind Thief");
					return classes;
			}
		}else if(choice==2) {
			switch(id) {
				case 26:
					classes.add("Scoundrel");
					classes.add("Mind Thief");
					classes.add("Nightshroud");
					return classes;
				case 29:
					classes.add("Scoundrel");
					classes.add("Sawbones");
					classes.add("Soothsinger");
					return classes;
			}
		}
		
		return classes;
	}
	
}
