package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;

public final class ScenarioEnemyLoader {
	
	public static List<Enemy> getEnemies(int sceneID){
		List<Enemy> enemies = new ArrayList<Enemy>();
		
		enemies.add(new Enemy(0, "Bandit Guard", 0, false, new Point(3,6)));
		enemies.add(new Enemy(1, "Bandit Guard", 0, true, new Point(3,7)));
		enemies.add(new Enemy(2, "Bandit Guard", 0, false, new Point(3,8)));
		
		enemies.add(new Enemy(3, "Bandit Guard", 1, false, new Point(7,6)));
		enemies.add(new Enemy(4, "Bandit Guard", 1, false, new Point(7,8)));
		enemies.add(new Enemy(5, "Bandit Archer", 1, true, new Point(11, 7)));
		
		enemies.add(new Enemy(6, "Living Bones", 2, false, new Point(8,4)));
		enemies.add(new Enemy(7, "Living Bones", 2, false, new Point(9,4)));
		enemies.add(new Enemy(8, "Bandit Guard", 2, false, new Point(7,0)));
		enemies.add(new Enemy(9, "Bandit Guard", 2, false, new Point(10,0)));
		
		
		return enemies;
	}
	
}
