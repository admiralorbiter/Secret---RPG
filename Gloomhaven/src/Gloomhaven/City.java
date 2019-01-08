package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class City {
	private int prospLevel=1;
	private int reputationLevel=0;
	private List<String> globalAchievements = new ArrayList<String>();
	private List<String> partyAchievements = new ArrayList<String>();
	private List<Item> collectiveItems = new ArrayList<Item>();
	
	public City() {
		
	}
	
	// TODO from UCDetector: Constructor "City.City(int,int)" has 0 references
	public City(int prospLevel, int reputationLevel) { // NO_UCD (unused code)
		this.prospLevel=prospLevel;
		this.reputationLevel=reputationLevel;
	}
	
	//Setters and Getters
	public int getProspLevel() {return prospLevel;}
	public void setProspLevel(int level) {this.prospLevel=level;}
	public int getReputationLevel() {return reputationLevel;}
	public void setReputationLevel(int repLevel) {this.reputationLevel=repLevel;}
	public List<String> getGlobalAchievements() {return globalAchievements;}
	public void addGlobalAchievements(String achievement) {globalAchievements.add(achievement);}
	public void changeReputation(int change) {this.reputationLevel=this.reputationLevel+change;}
	public void changeProsperity(int change) {
		prospLevel=prospLevel+change;
		if(prospLevel<0)
			prospLevel=0;
	}
	public List<String> getPartyAchievements(){return partyAchievements;}
	public void addPartyAchievement(String achievement) {partyAchievements.add(achievement);}
	public List<Item> getCollectiveItems(){return collectiveItems;}
	public void addCollectiveItems(Item item) {collectiveItems.add(item);}
}
