package Gloomhaven.EventCards;

import java.util.ArrayList;
import java.util.List;

import Unsorted.City;

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
			case 10:
				return "Use a weights and rpoes to climb out of the hole as quickly as possible.";
			case 11:
				return "Try to find a clearing where you can avoid the falling rocks.";
			case 12:
				return "Leave the puppy to fend for itself.";
			case 13:
				return "Defend yourself with lethal force.";
			case 14:
				return "Shoot at the birds.";
			case 15:
				return "Shoot at the birds.";
			case 16:
				return "Shoot at one of the deer.";
			case 17:
				return "Do whatever the Quatryl says.";
			case 18:
				return "Attempt to come to a peaful resolution.";
			case 19:
				return "Help the guards catch the escaping man.";
			case 20:
				return "The song must serve some nefarious purpose. Attack the Vermlings.";
			case 21:
				return "Keep moving forward.";
			case 22:
				return "Help push out Quartyl ringmaster's wagon.";
			case 23:
				return "Bring the man some leaves.";
			case 24:
				return "Aid the Inox with what they are carrying.";
			case 25:
				return "Try to reason with the Inox.";
			case 26:
				return "Attempt to help the man with his axle problem.";
			case 27:
				return "Clear the trees from the oard. It is the best way through and will help other travelers.";
			case 28:
				return "Sell the man a stamina potion.";
			case 29:
				return "Attempt to clear the stones from the path.";
			case 30:
				return "Help the survivors deal with the ccarnage.";
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
			case 10:
				return "Explore the area.";
			case 11:
				return "Take cover under a nearby outcropping and wait out the earthquake.";
			case 12:
				return "Take the puppy and bring it back to Gloomhaven.";
			case 13:
				return "Attempt to calm down the hermit and resolve the situation peacefully.";
			case 14:
				return "Let the birds pass undisturbed.";
			case 15:
				return "Let the birds pass undisturbed.";
			case 16:
				return "Just watch the deer for a little bit.";
			case 17:
				return "Demand an explanation before  you help it any way.";
			case 18:
				return "Attack the insulting, contemptuous Inox.";
			case 19:
				return "Interfere with the guards to help the man escape.";
			case 20:
				return "Move closer and enjoy the music.";
			case 21:
				return "Follow the animals' cue and run in the opposite direction.";
			case 22:
				return "Help push out the fortune teller's wagon.";
			case 23:
				return "Grab his stuff and run off while he's indisposed.";
			case 24:
				return "Ignore the Shaman's ramblings.";
			case 25:
				return "Fight off the Inox. They'll get no payments from you.";
			case 26:
				return "Tie up the man and take all of the goods he is so concerned about.";
			case 27:
				return "Take the time to find a way around the trees.";
			case 28:
				return "Politely decline and move quickly on the way.";
			case 29:
				return "Backtrack and find a way around the stone-filled valley.";
			case 30:
				return "Finish the job the Vermlings started and loot whatever is left.";
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
			case 10:
				return true;
			case 11:
				if(choice==2)
					return true;
				return false;
			case 12:
				return true;
			case 13:
				return true;
			case 14:
				return false;
			case 15: return false;
			case 16: return true;
			case 17: return true;
			case 18: return true;
			case 19: return true;
			case 20: return true;
			case 21: return true;
			case 22: return true;
			case 23: return true;
			case 24: return true;
			case 25: return false;
			case 26: return true;
			case 27:
				if(choice==2)
					return false;
				return true;
			case 28: return true;
			case 29: 
				if(choice==2)
					return false;
				return true;
			case 30: return true;
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
			case 10:
				return "All start scenario with 1 damage.";
			case 11:
				return "All start scenario with 2 damage.";
			case 12:
				return "No effect.";
			case 13:
				return "Gain 2 gold each.";
			case 14:
				return "All star scenario with Bless.";
			case 15:
				return "All start scneario with Muddle. All start scneario with 2 damage.";
			case 16:
				return "All start scneario with Curse.";
			case 17:
				return "Gain 5 collective gold.";
			case 18:
				return "Rep<-4: No effect. Otherwise, All start scneario with curse.";
			case 19:
				return "Rep<-4: No effect. Otherwise, gain 1 rep.";
			case 20:
				return "All start scenario with Poison. All start scneario with 2 damage.";
			case 21:
				return "All start scenario with wound. All start scenario with 3 damage. Lose 1 battle goal each.";
			case 22:
				return "Add City Event 74 to deck.";
			case 23:
				return "No effect.";
			case 24:
				return "Gain 10 collective gold. Discard 2 cards each. Unlock Burning Mountain 82 (m-6).";
			case 25:
				return "If Brute/Berseker No effect. Otherwise, lose 5 gold each.";
			case 26:
				return "If tinkerer/quartermaster All start with Bless. Gain 1 rep. Otherwise, no effect.";
			case 27:
				return "If spellweaver/elementalist/summoner No effect. Otherwise, Discard 3 cards each.";
			case 28:
				return "Pay 1 collective Minor Stamina Potion (Item 13) Gain 10 collective gold. If scoundrel/soothsinger/sawbones gain 10 more collective gold. Otherwise, no effect.";
			case 29:
				return "If cragheart, no effect. Otherwise, Discard 2 cards each.";
			case 30:
				return "If Mind Thief/Beast Tyrant No effect. Otherwise, consume 1 item each. Gain 1 rep.";
					
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
			case 10:
				return "Add City Event 71 to the deck. All start scenario with 1 damage.";
			case 11:
				return "If cragheart/spellweaver/elementalist/summoner - Add city event 73 to deck. Otherwise start scenario with 4 damage.";
			case 12:
				return "One starts scenario with -1 Attack mod cards x3. Gain 1 rep.";
			case 13:
				return "All start scneario with curse.";
			case 14:
				return "No effect.";
			case 15:
				return "No effect.";
			case 16:
				return "All start scneario with 2 damage.";
			case 17:
				return "Gain 1 prosperity.";
			case 18:
				return "All start scneario with 3 damage.";
			case 19:
				return "Lose 2 rep. Rep <-9: Gain 1 collective Major Power Potion. Item 41";
			case 20:
				return "Gain 3 experience each.";
			case 21:
				return "Discard 2 cards each.";
			case 22:
				return "Gain 2 experience each. All start scenario with Curse.";
			case 23:
				return "Gain 2 gold each. Lose 1 rep.";
			case 24:
				return "Unlock burning mountain 82 (m-6)";
			case 25:
				return "All start scenario with 3 damage.";
			case 26:
				return "Gain 10 collective gold. Lose 1 rep.";
			case 27:
				return "All start scenario with 2 damage.";
			case 28:
				return "No effect.";
			case 29:
				return "All start scneario with Wound.";
			case 30:
				return "Gain 2 gold each.";
		}
		
		return option;
	}
	
	public static void determineThresholdForResults(EventCard card, City gloomhaven) {
		int id=card.getID();
		int choice = card.getChoice();
		
		card.setThreshold(false);
		
		if(choice==1) {
			switch(id) {
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
				case 11:
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
					classes.add("Berseker");
					return classes;
				case 26:
					classes.add("Tinkerer");
					classes.add("Quartermaster");
					return classes;
				case 27:
					classes.add("Spellweaver");
					classes.add("Elementalist");
					classes.add("Summoner");
					return classes;
				case 28:
					classes.add("Scoundrel");
					classes.add("Soothsinger");
					classes.add("Sawbones");
					return classes;
				case 29:
					classes.add("Cragheart");
					return classes;
				case 30:
					classes.add("Mind Thief");
					classes.add("Beast Tyrant");
					return classes;
			}
		}else if(choice==2) {
			switch(id) {
				case 11:
					classes.add("Cragheart");
					classes.add("Spellweaver");
					classes.add("Elementalist");
					classes.add("Summoner");
					return classes;
			}
		}
		
		return classes;
	}
}
