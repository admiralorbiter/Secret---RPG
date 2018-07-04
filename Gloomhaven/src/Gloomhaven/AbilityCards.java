package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class AbilityCards {
	
	String name;
	int initiative;
	
	CardDataObject top = new CardDataObject();
	CardDataObject bottom = new CardDataObject();
	Cards card = new CardsMindthief();
	
	public AbilityCards(int level, int id, String Class) {
		if(Class=="Mindthief") {
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
	}
	
	CardDataObject getTop() {return top;}
	CardDataObject getBottom() {return bottom;}
	
}
