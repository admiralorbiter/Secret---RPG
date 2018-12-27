package Gloomhaven.EventCards;

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
				return "Reassure the b oys and go find the cat.";
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
		}
		
		return option;
	}
	
}
