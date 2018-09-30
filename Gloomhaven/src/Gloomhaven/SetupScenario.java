package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class SetupScenario {
	String scenarioID;
	
	public SetupScenario(String sceneID) {
		scenarioID=sceneID;
	}
	
	public List<Enemy> getEnemies(){
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		switch(scenarioID) {
			case "Test":
				enemies.add(new Enemy("Test"));
				enemies.add(new Enemy("TestElite"));
				enemies.add(new Enemy("Test"));
				break;
			default:
				enemies.add(new Enemy("Test"));
				enemies.add(new Enemy("TestElite"));
				enemies.add(new Enemy("Test"));
		}
		
		return enemies;
	}
	
	public String getRoomID() {
		String id;
		
		switch(scenarioID) {
		case "Test":
			id="Test";
			break;
		default:
			id="Test";
		}
		
		return id;
	}
}
