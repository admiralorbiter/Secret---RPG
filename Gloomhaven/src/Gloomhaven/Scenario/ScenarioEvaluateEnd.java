package Gloomhaven.Scenario;

import java.util.List;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;

public final class ScenarioEvaluateEnd {
	public static boolean evaluateOne(List<Enemy> enemies, ScenarioData data, List<Player> party) {

		if(data.getId()==3) {
			if(data.getEnemiesKilled()>=(party.size()*5))
				return true;
				
		}
		
		if(enemies.size()==0 && data.getTotalEnemies()==data.getEnemiesKilled())
			return true;
		
		return false;
	}
}
