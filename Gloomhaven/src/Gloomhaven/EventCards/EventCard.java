package Gloomhaven.EventCards;

import java.io.Serializable;
/**
 * Event Card that holds all the text and choices for the card
 * @author admir
 *
 */
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
	
	private String resultAltA=null;
	private String resultAltB=null;
	private String thresholdType="None";
	private boolean threshold=false;
	private boolean thresholdMet=false;
	private int thresholdAmount=0;
	private Choice choice=Choice.NONE;
	
	/**
	 * Creates event card based on type of card and the id of the card
	 * @param type		City or Road Card
	 * @param id		Unique ID for the Card
	 */
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
	
	/**
	 * @return String that details the outcome of the card and choices
	 */
	public String getResults() {
		String error = "Error in eventcard.java - get results function.";

		switch(choice) {
			case TOP:
				return resultA;
			case BOTTOM:
				return resultB;
			case ALTTOP:
				if(resultAltA!=null)
					return resultAltA;
				return error;
			case ALTBOTTOM:
				if(resultAltB!=null)
					return resultAltA;
				return error;
			default:
				return error;
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
	public boolean hasThreshold() {return threshold;}
	public void setThreshold(boolean threshold) {this.threshold=threshold;}
	public void setThresholdMet(boolean threshold) {this.thresholdMet=threshold;}
	public int getThresholdAmount() {return thresholdAmount;}
	public String getResultAltB() {return resultAltB;}
	public void setResultAltB(String resultAltB) {this.resultAltB = resultAltB;}
	public String getResultAltA() {return resultAltA;}
	public void setResultAltA(String resultAltA) {this.resultAltA = resultAltA;}
}
