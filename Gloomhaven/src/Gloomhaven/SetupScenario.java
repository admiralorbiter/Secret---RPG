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
		int scenarioLevel;
		
		switch(scenarioID) {
			case "Test":
				scenarioLevel=1;
				enemies.add(new Enemy("Test", 0, scenarioLevel));
				enemies.add(new Enemy("TestElite", 1, scenarioLevel));
				enemies.add(new Enemy("Test", 2, scenarioLevel));
				break;
			default:
				scenarioLevel=1;
				enemies.add(new Enemy("Test", 0, scenarioLevel));
				enemies.add(new Enemy("TestElite", 1, scenarioLevel));
				enemies.add(new Enemy("Test", 2, scenarioLevel));
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
