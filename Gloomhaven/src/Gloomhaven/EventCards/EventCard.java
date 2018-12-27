package Gloomhaven.EventCards;

public class EventCard {
	String optionA;
	String optionB;
	String resultA;
	String resultB;
	int id;
	String type;
	String text;
	
	
	boolean threshold;
	boolean thresholdMet=false;
	int choice=0;//1 is top 2 is bottom 3 is alttop 4 is altbottom
	
	public EventCard(String type, int id) {
		this.id=id;
		this.type=type;
		
		if(type.equals("City")) {
			text=CityEventCardLoader.cardText(id);
			optionA=CityEventCardLoader.cityA(id);
			optionB=CityEventCardLoader.cityB(id);
			resultA=CityEventCardLoader.resultsA(id);
			resultB=CityEventCardLoader.resultsB(id);
		}else if(type.equals("Road")){
			text=RoadEventCardLoader.cardText(id);
			optionA=RoadEventCardLoader.cityA(id);
			optionB=RoadEventCardLoader.cityB(id);
			resultA=RoadEventCardLoader.resultsA(id);
			resultB=RoadEventCardLoader.resultsB(id);
		}
	}
	
	//Getters and Setters
	public int getID() {return id;}
	public String getOptionA() {return optionA;}
	public String getOptionB() {return optionB;}
	public int getChoice() {return choice;}
	public void setChoice(int choice) {this.choice=choice;}
	public boolean wasThresholdMet() {return thresholdMet;}
	public String getResults() {
		if(choice==1) {
			return resultA;
		}
		else if(choice== 2) {
			return resultB;
		}
		
		return "Error in eventcard.java - get results function.";
	}

}
