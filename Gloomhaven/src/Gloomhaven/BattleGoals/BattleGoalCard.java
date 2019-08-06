package Gloomhaven.BattleGoals;

import java.io.Serializable;

/**
 * Battle Goal Card Data Object
 */
public class BattleGoalCard implements Serializable{
	private int id;
	private String name="";
	private String text="";
	private int reward=0;
	private String thresholdKeyword="";
	private int thresholdAmount=-1;
	private String overUnderThresholdKeyword="";
	
	public BattleGoalCard(int id) {
		this.id=id;
	}
	
	//Getters and Setters
	public void setName(String name) {this.name=name;}
	public void setText(String text) {this.text=text;}
	public void setReward(int reward) {this.reward=reward;}
	public void setThresholdKeyword(String keyword) {this.thresholdKeyword=keyword;}
	public void setThresholdAmount(int amount) {this.thresholdAmount=amount;}
	public void setOverUnderThresholdKeyword(String keyword) {this.overUnderThresholdKeyword=keyword;}
	public int getID() {return id;}
	public String getName() {return name;}
	public String getText() {return text;}
	public int getReward() {return reward;}
	public String getThresholdKeyword() {return thresholdKeyword;}
	public int getThresholdAmount() {return thresholdAmount;}
	public String getOverUnderThresholdKeyword() {return overUnderThresholdKeyword;}
}
