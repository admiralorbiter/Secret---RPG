package Gloomhaven.Scenario;

import java.util.List;

import Gloomhaven.Characters.Enemy;

public final class ScenarioEvaluateEnd {
	public static boolean evaluateOne(List<Enemy> enemies, ScenarioData data) {

		if(enemies.size()==0 && data.getTotalEnemies()==data.getEnemiesKilled())
			return true;
		
		return false;
	}
}
