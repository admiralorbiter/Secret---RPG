package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class AbilityCards {
	
	String name;
	int initiative;
	
	CardDataObject top = new CardDataObject();
	CardDataObject bottom = new CardDataObject();
	Cards card;
	
	public AbilityCards(int level, int id, String Class) {
		if(Class=="Mindthief") {
			card = new CardsMindthief();
			if(level==1) {
				switch(id)
				{
					case 1:
						name="Submissive Afflication";
						initiative=48;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
					
					case 2:
						name="Into the Night";
						initiative=14;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 3:
						name="Fearsome Blade";
						initiative=27;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 4:
						name="Feedback Loop";
						initiative=79;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 5:
						name="Gnawing Horde";
						initiative=82;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 6:
						name="The Mind's Weakness";
						initiative=75;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 7:
						name="Parasite Influence";
						initiative=71;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 8:
						name="Scurry";
						initiative=20;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 9:
						name="Perverse Edge";
						initiative=8;
						top=card.getTop(name);
						bottom=card.getBottom(name);	
						break;
						
					case 10:
						name="Empathetic Assault";
						initiative=11;
						top=card.getTop(name);
						bottom=card.getBottom(name);	
						break;
						
				}
			}
		}
		else if(Class=="Inox Brute") {
			card = new CardsBrute();
			switch(id) {
				case 101:
					name="Temple";
					initiative=72;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 102:
					name="Eye for an Eye";
					initiative=18;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 103:
					name="Sweeping Blow";
					initiative=64;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 104:
					name="Provoking Roar";
					initiative=10;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 105:
					name="Overwhelming Assault";
					initiative=61;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 106:
					name="Grab and Go";
					initiative=87;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 107:
					name="Warding Strength";
					initiative=32;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 108:
					name="Shield Bash";
					initiative=15;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 109:
					name="Leaping Cleave";
					initiative=54;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				case 110:
					name="Spare Dagger";
					initiative=27;
					top=card.getTop(name);
					bottom=card.getBottom(name);	
					break;
				
			}
		}
		
	}
	
	CardDataObject getTop() {return top;}
	CardDataObject getBottom() {return bottom;}
	
	String getText() {
		String text=initiative+": "+top.text+" - "+bottom.text;
		return text;
	}
	
	int getInitiative() {
		return initiative;
	}
	
}
