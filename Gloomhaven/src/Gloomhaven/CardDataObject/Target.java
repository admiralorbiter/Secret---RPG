package Gloomhaven.CardDataObject;

import java.util.ArrayList;
import java.util.List;

public class Target {
	private String flag ="Enemy";
	
	private boolean self=false;
	
	private boolean allAdjacentEnemies=false;
	private boolean allAdjacentAllies=false;
	private boolean allEnemiesInRoom=false;
	private boolean allAlliesInRoom=false;
	private boolean allEnemiesOnPath=false;
	private boolean opposingAttack=false;
	private boolean circle=false;
	private boolean semiCircle=false;
	private boolean sortOfSemiCircle=false;
	private boolean triangle=false;
	private boolean line=false;
	private boolean ally=false;
	private boolean allAllies=false;
	private boolean allAdjacent=false;
	private boolean oneAdjacent=false;
	
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
	
	public List<String> getTargetFlagList(){
		List<String> targetFlagList = new ArrayList<String>();
		
		targetFlagList.add("Self");
		targetFlagList.add("OpposingAttack");
		targetFlagList.add("AllAdjacent");
		targetFlagList.add("OneAdjacentEnemy");
		targetFlagList.add("AllAdjacentEnemies");
		targetFlagList.add("AllAdjacentAllies");
		targetFlagList.add("AllEnemiesInRoom");
		targetFlagList.add("AllAlliesInRoom");
		targetFlagList.add("AllEnemiesOnPath");
		targetFlagList.add("Circle");
		targetFlagList.add("SemiCircle");
		targetFlagList.add("SortOfSemiCircle");
		targetFlagList.add("Triangle");
		targetFlagList.add("Ally");
		targetFlagList.add("AllAllies");
		
		return targetFlagList;
	}
	
	public void setTarget(String flag) {
		
		switch(flag) {
			case "Self":
				self=true;
				break;
			case "OpposingAttack":
				opposingAttack=true;
				break;
			case "OneAdjacentEnemy":
				oneAdjacent=true;
				break;
			case "AllAdjacent":
				allAdjacent=true;
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
			case "SemiCircle":
				semiCircle=true;
				break;
			case "SortOfSemiCircle":
				sortOfSemiCircle=true;
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
	public boolean getOpposingAttack() {return opposingAttack;}
	public void setOpposingAttack(boolean opposingAttack) {this.opposingAttack=opposingAttack;}
	public boolean getCircle() {return circle;}
	public void setCircle(boolean circle) {this.circle = circle;}
	public boolean getSemiCircle() {return semiCircle;}
	public void setSemiCircle(boolean semiCircle) {this.semiCircle=semiCircle;}
	public boolean getSortOfSemiCircle() {return sortOfSemiCircle;}
	public void setSortaOfSemiCircle(boolean sortaSemi) {this.sortOfSemiCircle=sortaSemi;}
	public boolean getTriangle() {return triangle;}
	public void setTriangle(boolean triangle) {this.triangle = triangle;}
	public boolean isLine() {return line;}
	public void setLine(boolean line) {this.line = line;}
	public int getLineLength() {return lineLength;}
	public void setLineLength(int lineLength) {this.lineLength = lineLength;}	
	
}
