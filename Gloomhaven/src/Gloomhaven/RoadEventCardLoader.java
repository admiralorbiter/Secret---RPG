package Gloomhaven;

public final class RoadEventCardLoader {
	public static String cardText(int id) {
		String option="Need to add text for each event card";
		
		return option;
	}
	
	public static String cityA(int id) {
		String option="Error";
		
		
		switch(id) {
			case 1:
				return "Run from the howling to safety.";
			case 2:
				return "Eat the berries.";
			case 3:
				return "Eat the berries.";
			case 4:
				return "Approach the caravan and offer to travel with them until your paths diverge.";
			case 5:
				return "Help the Inox fight off the humans.";
			case 6:
				return "Take the opportunity to run from the bear before it gets any closer.";
			case 7:
				return "Head into the forest to investigate the chanting.";
			case 8:
				return "Pay the thieves.";
			case 9:
				return "Help out the guard.";
		}
		
		return option;
	}
	
	public static String cityB(int id) {
		String option="Error";
		
		switch(id) {
			case 1:
				return "Let the wolves come.";
			case 2:
				return "Pass by the berries and just eat your normal rations";
			case 3:
				return "Pass by the berries and just eat your normal rations";
			case 4:
				return "Attack the caravan.";
			case 5:
				return "Help the humans fight off the Inox.";
			case 6:
				return "Attack the bear, hopefully catching it by suprise.";
			case 7:
				return "Keep moving on down the road. No need to get mixed up in whatever is going on.";
			case 8:
				return "Resist the robbery, killing as many thieves as you can.";
			case 9:
				return "Claim you have no aid to give and move on.";
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
				return false;
			case 4:
				return true;
			case 5:
				return true;
			case 6:
				return false;
			case 7:
				return true;
			case 8:
				if(choice==1)
					return false;
				
				return true;
			case 9:
				return false;
		}
		
		return false;
	}
	
	public static String resultsA(int id) {
		String option="Error";
		
		switch(id) {
			case 1:
				return "All start scenario with Poison";
			case 2:
				return "All start scenario with Bless";
			case 3:
				return "All start scenario with Poison";
			case 4:
				return "Gain 2 gold each. Rep >9: Gain 1 additional gold each.";
			case 5:
				return "Gain 1 collective Necklace of Teeth Item:106";
			case 6:
				return "Lose 1 check mark each.";
			case 7:
				return "Gain 5 experience each. Lose 1 check mark each.";
			case 8:
				return "Lose 5 gold each";
			case 9:
				return "Consume 1 collective item.";
		}
		
		return option;
	}
	
	public static String resultsB(int id) {
		String option="Error";
		
		switch(id) {
			case 1:
				return "All start scenario with 3 damage.";
			case 2:
				return "No effect.";
			case 3:
				return "No effect.";
			case 4:
				return "Gain 10 gold each. Lose 2 repuatation";
			case 5:
				return "All start scenario with Curse. Gain 1 reputation.";
			case 6:
				return "All start scenario with Wound.";
			case 7:
				return "No effect.";
			case 8:
				return "All start scenario with 3 damage.";
			case 9:
				return "Lose 1 reputation";
		}
		
		return option;
	}
}
