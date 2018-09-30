package Gloomhaven;

public class CardsTest implements CardInterface {

	@Override
	public CardDataObject getTop(String name) {
		CardDataObject top = new CardDataObject();	
		top.text = "test";
		top.move=3;
		top.attack=0;
			
		return top;
	}

	@Override
	public CardDataObject getBottom(String name) {
		CardDataObject bottom = new CardDataObject();	
		bottom.text = "test";
		bottom.move=3;
		bottom.attack=3;
			
		return bottom;
	}

	@Override
	public CardDataObject getData(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
