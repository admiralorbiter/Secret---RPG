package Gloomhaven;

public class Goal {
	
	int id;
	
	public Goal(int id) {
		this.id=id;
	}
	
	public boolean checkGoal(int enemyCount) {
		if(id==1) {
			if(enemyCount==0)
				return true;
		}
		
		return false;
		
	}
}
