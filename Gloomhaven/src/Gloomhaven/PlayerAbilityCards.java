package Gloomhaven;

public class PlayerAbilityCards {
	private String name;
	private int initiative;
	
	CardDataObject top = new CardDataObject();
	CardDataObject bottom = new CardDataObject();
	CardInterface card;
	
	public PlayerAbilityCards(int level, int id, String Class) {
		if(Class=="Test") {
			card = new CardsTest();
			if(level==1) {
				switch(id)
				{
					case 1:
						name="Feel The Love";
						initiative=48;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
					
					case 2:
						name="Fire";
						initiative=14;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 3:
						name="4th Dimension";
						initiative=27;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 4:
						name="Freee (Ghost Town Pt.2)";
						initiative=79;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 5:
						name="Reborn";
						initiative=82;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 6:
						name="Kids See Ghost";
						initiative=75;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 7:
						name="Cudi Montage";
						initiative=71;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 8:
						name="I Thought About Killing You";
						initiative=20;
						top=card.getTop(name);
						bottom=card.getBottom(name);
						break;
						
					case 9:
						name="Yikes";
						initiative=8;
						top=card.getTop(name);
						bottom=card.getBottom(name);	
						break;
						
					case 10:
						name="All Mine";
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
	
	String getText() {
		String text=initiative+": "+top.text+" - "+bottom.text;
		return text;
	}
	
	int getInitiative() {
		return initiative;
	}
}
