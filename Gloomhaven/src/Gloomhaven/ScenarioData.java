package Gloomhaven;

public class ScenarioData {
	private String name;
	private int id;
	private String requirements="None";
	private String goal;
	private MapLocation location;
	
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
	
}
