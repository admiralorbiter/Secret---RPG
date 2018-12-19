package Gloomhaven;

public class Target {
	String flag ="Enemy";
	
	boolean self=false;
	
	boolean allAdjacentEnemies=false;
	boolean allAdjacentAllies=false;
	boolean allEnemiesInRoom=false;
	boolean allAlliesInRoom=false;
	boolean allEnemiesOnPath=false;
	
	int allyTargets=0;
	int targets=0;
	
	public Target() {
		targets=1;
	}
	
	public Target(String flag) {
		
		this.flag=flag;
		
		switch(flag) {
			case "Self":
				self=true;
				break;
			case "allAdjacentEnemies":
				allAdjacentEnemies=true;
				break;
			case "allAdjacentAllies":
				allAdjacentAllies=true;
				break;
			case "allEnemiesInRoom":
				allEnemiesInRoom=true;
				break;
			case "allAlliesInRoom":
				allAlliesInRoom=true;
				break;
			case "allEnemiesOnPath":
				allEnemiesOnPath=true;
				break;
		}

	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isSelf() {
		return self;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public boolean isAllAdjacentEnemies() {
		return allAdjacentEnemies;
	}

	public void setAllAdjacentEnemies(boolean allAdjacentEnemies) {
		this.allAdjacentEnemies = allAdjacentEnemies;
	}

	public boolean isAllAdjacentAllies() {
		return allAdjacentAllies;
	}

	public void setAllAdjacentAllies(boolean allAdjacentAllies) {
		this.allAdjacentAllies = allAdjacentAllies;
	}

	public boolean isAllEnemiesInRoom() {
		return allEnemiesInRoom;
	}

	public void setAllEnemiesInRoom(boolean allEnemiesInRoom) {
		this.allEnemiesInRoom = allEnemiesInRoom;
	}

	public boolean isAllAlliesInRoom() {
		return allAlliesInRoom;
	}

	public void setAllAlliesInRoom(boolean allAlliesInRoom) {
		this.allAlliesInRoom = allAlliesInRoom;
	}

	public boolean isAllEnemiesOnPath() {
		return allEnemiesOnPath;
	}

	public void setAllEnemiesOnPath(boolean allEnemiesOnPath) {
		this.allEnemiesOnPath = allEnemiesOnPath;
	}

	public int getAllyTargets() {
		return allyTargets;
	}

	public void setAllyTargets(int allyTargets) {
		this.allyTargets = allyTargets;
	}

	public int getTargets() {
		return targets;
	}

	public void setTargets(int targets) {
		this.targets = targets;
	}
}
