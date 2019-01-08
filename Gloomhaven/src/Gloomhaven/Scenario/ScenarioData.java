package Gloomhaven.Scenario;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.MapLocation;
import Gloomhaven.Characters.Enemy;

public class ScenarioData {
	private String name;
	private int id;
	private String requirements="None";
	private String goal;
	private MapLocation location;
	private boolean specialRules=false;
	private List<MapLocation> newLocations = new ArrayList<MapLocation>();
	private List<String> partyAchievements = new ArrayList<String>();
	private List<String> globalAchievements = new ArrayList<String>();
	private int roomTotal=0;
	private Point boardSize;
	private Point startingPosition;
	private int totalEnemies;
	private int enemiesKilled=0;
	
	public ScenarioData(int id) {
		this.id=id;
	}

	//Getters and Setters
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getId() {return id;}
	public String getRequirements() {return requirements;}
	public void setRequirements(String requirements) {this.requirements = requirements;}
	public String getGoal() {return goal;}
	public void setGoal(String goal) {this.goal = goal;}
	public MapLocation getLocation() {return location;}
	public void setLocation(MapLocation location) {this.location=location;}
	public List<MapLocation> getNewLocations(){return newLocations;}
	public List<String> getPartyAchievements(){return partyAchievements;}
	public void setSpecialRules(boolean flag) {this.specialRules=flag;}
	public boolean getSpecialRulesFlag() {return specialRules;}
	public List<String> getGlobalAchievements(){return globalAchievements;}
	public void setRoomTotal(int roomTotal) {this.roomTotal=roomTotal;}
	public int getRoomTotal() {return roomTotal;}
	public void setBoardSize(Point size) {boardSize=size;}
	public Point getBoardSize() {return boardSize;}
	public Point getStartingPosition() {return startingPosition;}
	public void setStartingposition(Point p) {startingPosition=new Point(p);}
	public int getTotalEnemies() {return totalEnemies;}
	public void setTotalEnemies(int totalEnemies) {this.totalEnemies=totalEnemies;}
	public void increaseEnemiesKilled() {enemiesKilled++;}
	public int getEnemiesKilled() {return enemiesKilled;}
	public List<Enemy> getEnemies(int room) {
		return ScenarioEnemyLoader.getEnemies(id, room);
	}
	
}
