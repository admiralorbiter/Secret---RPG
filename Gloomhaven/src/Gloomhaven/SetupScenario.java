package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;

public class SetupScenario {
	String scenarioID;
	
	public SetupScenario(String sceneID) {
		scenarioID=sceneID;
	}
	
	public List<Enemy> getEnemies(){
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		switch(scenarioID) {
			case "Test":
				enemies.add(new Enemy(0, "Test"));
				enemies.add(new Enemy(1, "TestElite"));
				enemies.add(new Enemy(2, "Test"));
				break;
			default:
				enemies.add(new Enemy(0, "Test"));
				enemies.add(new Enemy(1, "TestElite"));
				enemies.add(new Enemy(2, "Test"));
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
