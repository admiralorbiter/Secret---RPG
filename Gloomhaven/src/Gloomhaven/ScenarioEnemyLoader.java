package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;

public final class ScenarioEnemyLoader {
	
	public static List<Enemy> getEnemies(int sceneID, int room){
		List<Enemy> enemies = new ArrayList<Enemy>();
		if(sceneID==1) {
			if(room==0) {
				enemies.add(new Enemy(0, "Bandit Guard", 0, false, new Point(6,8)));
				enemies.add(new Enemy(1, "Bandit Guard", 0, true, new Point(7,9)));
				enemies.add(new Enemy(2, "Bandit Guard", 0, false, new Point(8,8)));
			}else if(room==1) {
				enemies.add(new Enemy(3, "Bandit Guard", 1, false, new Point(8,5)));
				enemies.add(new Enemy(4, "Bandit Guard", 1, false, new Point(6,5)));
				enemies.add(new Enemy(5, "Bandit Archer", 1, true, new Point(7, 1)));
			}else if(room==2) {
				enemies.add(new Enemy(6, "Living Bones", 2, false, new Point(0,3)));
				enemies.add(new Enemy(7, "Living Bones", 2, false, new Point(0,4)));
				enemies.add(new Enemy(8, "Bandit Guard", 2, false, new Point(2,3)));
				enemies.add(new Enemy(9, "Bandit Guard", 2, false, new Point(2,0)));
			}
		}
		
		return enemies;
	}
	
}
