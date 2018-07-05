package Gloomhaven;

public class CardsGuard implements Cards{

	@Override
	public CardDataObject getTop(String name) {
		return null;
	}

	@Override
	public CardDataObject getBottom(String name) {
		return null;
	}

	public CardDataObject getData(int id) {
		CardDataObject card = new CardDataObject();
		
		if(id==1)
		{
			card.text="Shield +1; Retaliate +2";
			card.shield=1;
			card.retaliate=true; //+2
			card.shuffle=true;
		}
		else if(id==2) {
			card.text="Move -1; Attack +0; Range 2";
			card.move=-1;
			card.attack=0;
			card.range=2;
		}
		else if(id==3) {
			card.text="Move +0; Attack +0";
			card.move=0;
			card.attack=0;
		}
		else if(id==4) {
			card.text="Move +1; Attack -1";
			card.attack=1;
			card.move=-1;
		}
		else if(id==5) {
			card.text="Move +0; Attack +0";
			card.move=0;
			card.attack=0;
		}
		else if(id==6) {
			card.text="Move -1; Attack +1";
			card.move=-1;
			card.attack=1;
		}
		else if(id==7) {
			card.text="Shield +1; Attack +0; Poison";
			card.shield=1;
			card.attack=0;
			card.statusEffect=true; // poison
			card.shuffle=true;
		}
		else if(id==8) {
			card.text="Move -1; Attack +0; Strengthen Self.";
			card.move=-1;
			card.attack=0;
			card.statusEffect=true;//strengthen (self)
		}
		return card;
		
	}
	
}
