package Gloomhaven;

public class CardsTest implements CardInterface {

	@Override
	public CardDataObject getTop(String name) {
		CardDataObject top = new CardDataObject();	
		top.text = name;

		switch(name)
		{
			case "Feel The Love":
				top.move=0;
				top.attack=0;
				break;
			
			case "Fire":
				top.move=1;
				top.attack=2;
				break;
				
			case "4th Dimension":
				top.move=2;
				top.attack=1;
				break;
				
			case "Freee (Ghost Town Pt.2)":
				top.move=3;
				top.attack=2;
				break;
				
			case "Reborn":
				top.move=0;
				top.attack=3;
				break;
				
			case "Kids See Ghost":
				top.move=1;
				top.attack=1;
				break;
				
			case "Cudi Montage":
				top.move=2;
				top.attack=2;
				break;
				
			case "I Thought About Killing You":
				top.move=3;
				top.attack=3;
				break;
				
			case "Yikes":
				top.move=1;
				top.attack=4;	
				break;
				
			case "All Mine":
				top.move=0;
				top.attack=0;
				break;
				
		}

		return top;
	}

	@Override
	public CardDataObject getBottom(String name) {
		CardDataObject bottom = new CardDataObject();	
		bottom.text = name;
		
		switch(name)
		{
			case "Feel The Love":
				bottom.move=3;
				bottom.attack=3;
				break;
			
			case "Fire":
				bottom.move=4;
				bottom.attack=0;
				break;
				
			case "4th Dimension":
				bottom.move=2;
				bottom.attack=0;
				break;
				
			case "Freee (Ghost Town Pt.2)":
				bottom.move=1;
				bottom.attack=0;
				break;
				
			case "Reborn":
				bottom.move=0;
				bottom.attack=0;
				break;
				
			case "Kids See Ghost":
				bottom.move=1;
				bottom.attack=2;
				break;
				
			case "Cudi Montage":
				bottom.move=2;
				bottom.attack=0;
				break;
				
			case "I Thought About Killing You":
				bottom.move=3;
				bottom.attack=1;
				break;
				
			case "Yikes":
				bottom.move=3;
				bottom.attack=0;	
				break;
				
			case "All Mine":
				bottom.move=2;
				bottom.attack=1;
				break;
				
		}
			
		return bottom;
	}

	@Override
	public CardDataObject getData(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
