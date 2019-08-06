package Gloomhaven.EventCards;

import java.io.Serializable;

public class EventCard implements Serializable{
	
	public enum Choice{
		NONE,
		TOP,
		ALTTOP,
		BOTTOM,
		ALTBOTTOM
	}
	
	private String optionA;
	private String optionB;
	private String resultA;
	private String resultB;
	private int id;
	private String text;
	private String thresholdType="None";
	
	
	private boolean threshold;
	private boolean thresholdMet=false;
	private int thresholdAmount=0;
	private Choice choice=Choice.NONE;
	
	
	public EventCard(String type, int id) {
		this.id=id;
		
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
	public Choice getChoice() {return choice;}
	public void setChoice(Choice choice) {this.choice=choice;}
	public boolean wasThresholdMet() {return thresholdMet;}
	public String getThresholdType() {return thresholdType;}
	public void setThresholdType(String type) {this.thresholdType=type;}
	public void setThresholdAmount(int amount) {this.thresholdAmount=amount;}
	public String getText() {return text;}
	
	public String getResults() {
		if(choice==Choice.TOP) {
			return resultA;
		}
		else if(choice== Choice.BOTTOM) {
			return resultB;
		}
		
		return "Error in eventcard.java - get results function.";
	}

	public boolean hasThreshold() {return threshold;}
	public void setThreshold(boolean threshold) {this.threshold=threshold;}
	public void setThresholdMet(boolean threshold) {this.thresholdMet=threshold;}
	public int getThresholdAmount() {return thresholdAmount;}
}
