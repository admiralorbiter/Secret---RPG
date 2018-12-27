package Gloomhaven;

public class EventCard {
	String optionA;
	String optionB;
	String resultA;
	String resultB;
	int id;
	String type;
	String text;
	
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
}
