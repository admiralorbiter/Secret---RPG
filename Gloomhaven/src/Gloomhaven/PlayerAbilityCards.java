package Gloomhaven;

public class PlayerAbilityCards {
	private String name;
	private int initiative;
	private boolean lost;
	private boolean discard;
	private boolean inPlay;
	private int index;												//Index in the full ability deck - pretty close to the id but I want to change the id
	CardDataObject top = new CardDataObject();
	CardDataObject bottom = new CardDataObject();
	CardInterface card;
	
	public PlayerAbilityCards(int level, int id, String Class) {
		index=id-1;
		if(Class=="Test") {
			card = new CardsTest();
			if(level==1) {
				top=card.getTop(id);
				bottom=card.getBottom(id);
				name=top.name;
				initiative=top.initiative;
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
	
	public boolean cardFree() {
		if(lost)
			return false;
		if(discard)
			return false;
		if(inPlay)
			return false;
		
		return true;
	}
	
	public void setInPlay() {
		inPlay=true;
		lost=false;
		discard=false;
	}
	
	public void lostPile() {
		inPlay=false;
		discard=false;
		lost=true;
	}
	
	public void discardPile() {
		inPlay=false;
		lost=false;
		discard=true;
	}
	
	public int getIndex() {return index;}
}
