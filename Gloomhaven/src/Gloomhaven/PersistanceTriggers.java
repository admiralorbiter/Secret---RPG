package Gloomhaven;

public class PersistanceTriggers {
	
	int count=0;
	int id;
	String idString;
	//1 - Triggers on player as a target
	boolean finished=false;
	String cardName;
	
	public PersistanceTriggers(int flag, String name) {
		id=flag;
		cardName=name;
		 switch(flag) {
			case 1:
				 idString="PlayerTarget";
				 break;
			default:
				 idString="";
		 } 
	}
	public String getName() {return cardName;}
	public String getFlag() {return idString;}
	public int getID() {return id;}
	public void addToTrigger() {
		System.out.println("Test Trigger");
		switch(id){
			case 1:
				count++;
				break;
		}
		
		calcIfFinished();
	}
	
	public boolean getFinished() {return finished;}
	
	private void calcIfFinished() {
		switch(id) {
		case 1:
			if(count==6) {
				finished=true;
			}
			break;
		}
	}
}
