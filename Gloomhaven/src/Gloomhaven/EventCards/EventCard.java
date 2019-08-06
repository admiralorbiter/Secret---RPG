package Gloomhaven.EventCards;

import java.io.Serializable;

public class EventCard implements Serializable{
	String optionA;
	String optionB;
	String resultA;
	String resultB;
	int id;
	String type;
	String text;
	String thresholdType="None";
	
	
	boolean threshold;
	boolean thresholdMet=false;
	int thresholdAmount=0;
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
	public String getThresholdType() {return thresholdType;}
	public void setThresholdType(String type) {this.thresholdType=type;}
	public void setThresholdAmount(int amount) {this.thresholdAmount=amount;}
	public String getText() {return text;}
	
	public String getResults() {
		if(choice==1) {
			return resultA;
		}
		else if(choice== 2) {
			return resultB;
		}
		
		return "Error in eventcard.java - get results function.";
	}

	public boolean hasThreshold() {return threshold;}
	public void setThreshold(boolean threshold) {this.threshold=threshold;}
	public void setThresholdMet(boolean threshold) {this.thresholdMet=threshold;}
	public int getThresholdAmount() {return thresholdAmount;}
}
