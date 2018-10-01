package Gloomhaven;

public class CardsTest implements CardInterface {

	@Override
	public CardDataObject getTop(int id) {
		CardDataObject top = new CardDataObject();	
		
		switch(id)
		{
			case 1:
				top.move=0;
				top.attack=0;
				top.initiative=48;
				top.name="Feel The Love";
				break;
			
			case 2:
				top.move=1;
				top.attack=2;
				top.name="Fire";
				top.initiative=14;
				break;
				
			case 3:
				top.move=2;
				top.attack=1;
				top.name="4th Dimension";
				top.initiative=27;
				break;
				
			case 4:
				top.move=3;
				top.attack=2;
				top.name="Freee (Ghost Town Pt.2)";
				top.initiative=79;
				break;
				
			case 5:
				top.move=0;
				top.attack=3;
				top.name="Reborn";
				top.initiative=82;
				break;
				
			case 6:
				top.move=1;
				top.attack=1;
				top.name="Kids See Ghost";
				top.initiative=75;
				break;
				
			case 7:
				top.move=2;
				top.attack=2;
				top.name="Cudi Montage";
				top.initiative=71;
				break;
				
			case 8:
				top.move=3;
				top.attack=3;
				top.name="I Thought About Killing You";
				top.initiative=20;
				break;
				
			case 9:
				top.move=1;
				top.attack=4;	
				top.name="Yikes";
				top.initiative=8;
				break;
				
			case 10:
				top.move=0;
				top.attack=0;
				top.name="All Mine";
				top.initiative=11;
				break;
				
		}
		//[Test]
		top.text=top.name;
		return top;
	}

	@Override
	public CardDataObject getBottom(int id) {
		CardDataObject bottom = new CardDataObject();	
	
		switch(id)
		{
			case 1:
				bottom.move=3;
				bottom.attack=3;
				bottom.name="Feel The Love";
				break;
			
			case 2:
				bottom.move=4;
				bottom.attack=0;
				bottom.name="Fire";
				break;
				
			case 3:
				bottom.move=2;
				bottom.attack=0;
				bottom.name="4th Dimension";
				break;
				
			case 4:
				bottom.name="Freee (Ghost Town Pt.2)";
				bottom.move=1;
				bottom.attack=0;
				break;
				
			case 5:
				bottom.name="Reborn";
				bottom.move=0;
				bottom.attack=0;
				break;
				
			case 6:
				bottom.name="Kids See Ghost";
				bottom.move=1;
				bottom.attack=2;
				break;
				
			case 7:
				bottom.name="Cudi Montage";
				bottom.move=2;
				bottom.attack=0;
				break;
				
			case 8:
				bottom.name="I Thought About Killing You";
				bottom.move=3;
				bottom.attack=1;
				break;
				
			case 9:
				bottom.name="Yikes";
				bottom.move=3;
				bottom.attack=0;	
				break;
				
			case 10:
				bottom.name="All Mine";
				bottom.move=2;
				bottom.attack=1;
				break;
				
		}
		
		//[Test]
		bottom.text="Bottom Text";
		return bottom;
	}

	@Override
	public CardDataObject getData(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
