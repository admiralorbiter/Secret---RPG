package Gloomhaven.CardDataObject;

import java.util.List;

public class Target {
	private String flag ="Enemy";
	
	private boolean self=false;
	
	private boolean allAdjacentEnemies=false;
	private boolean allAdjacentAllies=false;
	private boolean allEnemiesInRoom=false;
	private boolean allAlliesInRoom=false;
	private boolean allEnemiesOnPath=false;
	private boolean circle=false;
	private boolean triangle=false;
	private boolean line=false;
	private boolean ally=false;
	private boolean allAllies=false;
	
	private int lineLength=0;
	
	private int allyTargets=0;
	private int targets=0;
	
	public Target() {
		targets=1;
	}
	
	public Target(int targetNum) {
		targets=targetNum;
	}
	
	public Target(String flag) {
		
		this.flag=flag;
		
		setTarget(flag);
	}
	
	public Target(List<String> flags) {
		this.flag="Multiple";
		
		for(int i=0; i<flags.size(); i++)
			setTarget(flags.get(i));
	}
	
	public void setTarget(String flag) {
		
		switch(flag) {
			case "Self":
				self=true;
				break;
			case "AllAdjacentEnemies":
				allAdjacentEnemies=true;
				break;
			case "AllAdjacentAllies":
				allAdjacentAllies=true;
				break;
			case "AllEnemiesInRoom":
				allEnemiesInRoom=true;
				break;
			case "AllAlliesInRoom":
				allAlliesInRoom=true;
				break;
			case "AllEnemiesOnPath":
				allEnemiesOnPath=true;
				break;
			case "Circle":
				circle=true;
				break;
			case "Line":
				line=true;
				break;
			case "Triangle":
				triangle=true;
				break;
			case "Ally":
				ally=true;
				break;
			case "AllAllies":
				allAllies=true;
				break;
		}
	}
	
	//Getters and Setters
	public String getFlag() {return flag;}
	public void setFlag(String flag) {this.flag = flag;}
	public boolean isSelf() {return self;}
	public void setSelf(boolean self) {this.self = self;}
	public boolean isAllAdjacentEnemies() {return allAdjacentEnemies;}
	public void setAllAdjacentEnemies(boolean allAdjacentEnemies) {this.allAdjacentEnemies = allAdjacentEnemies;}
	public boolean isAllAdjacentAllies() {return allAdjacentAllies;}
	public void setAllAdjacentAllies(boolean allAdjacentAllies) {this.allAdjacentAllies = allAdjacentAllies;}
	public boolean isAllEnemiesInRoom() {return allEnemiesInRoom;}
	public void setAllEnemiesInRoom(boolean allEnemiesInRoom) {this.allEnemiesInRoom = allEnemiesInRoom;}
	public boolean isAllAlliesInRoom() {return allAlliesInRoom;}
	public void setAllAlliesInRoom(boolean allAlliesInRoom) {this.allAlliesInRoom = allAlliesInRoom;}
	public boolean isAllEnemiesOnPath() {return allEnemiesOnPath;}
	public void setAllEnemiesOnPath(boolean allEnemiesOnPath) {this.allEnemiesOnPath = allEnemiesOnPath;}
	public int getAllyTargets() {return allyTargets;}
	public void setAllyTargets(int allyTargets) {this.allyTargets = allyTargets;}
	public int getTargets() {return targets;}
	public void setTargets(int targets) {this.targets = targets;}
	public boolean isCircle() {return circle;}
	public void setCircle(boolean circle) {this.circle = circle;}
	public boolean isTriangle() {return triangle;}
	public void setTriangle(boolean triangle) {this.triangle = triangle;}
	public boolean isLine() {return line;}
	public void setLine(boolean line) {this.line = line;}
	public int getLineLength() {return lineLength;}
	public void setLineLength(int lineLength) {this.lineLength = lineLength;}	
	
}
