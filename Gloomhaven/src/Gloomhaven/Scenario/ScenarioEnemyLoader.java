package Gloomhaven.Scenario;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;

public final class ScenarioEnemyLoader {
	
	public static List<Enemy> getEnemies(int sceneID, int room){
		List<Enemy> enemies = new ArrayList<Enemy>();
		switch(sceneID) {
			case 1:
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
					enemies.add(new Enemy(9, "Bandit Guard", 2, false, new Point(2,4)));
				}
				break;
				
			case 2:
				if(room==0) {
					enemies.add(new Enemy(0, "Bandit Archer", 0, false, new Point(6, 3)));
					enemies.add(new Enemy(1, "Bandit Archer", 0, false, new Point(9, 3)));
				}else if(room==1) {
					enemies.add(new Enemy(2, "Bandit Commander", 1, false, new Point(7, 10)));
					enemies.add(new Enemy(3, "Bandit Archer", 1, false, new Point(6, 10)));
					enemies.add(new Enemy(4, "Bandit Archer", 1, true, new Point(8, 10)));
				}else if(room==2) {
					enemies.add(new Enemy(5, "Living Corpse", 2, false, new Point(13, 7)));
					enemies.add(new Enemy(6, "Living Corpse", 2, false, new Point(14, 7)));
					enemies.add(new Enemy(7, "Living Corpse", 2, false, new Point(13, 8)));
				}else if(room==3) {
					enemies.add(new Enemy(8, "Living Corpse", 3, false, new Point(8, 14)));
					enemies.add(new Enemy(9, "Living Corpse", 3, true, new Point(9, 14)));
				}else if(room==4) {
					enemies.add(new Enemy(10, "Living Corpse", 4, false, new Point(5, 14)));
					enemies.add(new Enemy(11, "Living Corpse", 4, true, new Point(4, 14)));
				}else if(room==5) {
					enemies.add(new Enemy(12, "Living Corpse", 5, false, new Point(1, 7)));
					enemies.add(new Enemy(13, "Living Corpse", 5, false, new Point(2, 7)));
					enemies.add(new Enemy(14, "Living Corpse", 5, false, new Point(1, 8)));
				}
				break;
			case 3:
				if(room==0) {
					enemies.add(new Enemy(0, "Inox Guard", 0, false, new Point(6, 10)));
					enemies.add(new Enemy(1, "Inox Guard", 0, false, new Point(6, 11)));
					enemies.add(new Enemy(2, "Inox Guard", 0, false, new Point(10, 10)));
					enemies.add(new Enemy(3, "Inox Guard", 0, false, new Point(10, 11)));
					enemies.add(new Enemy(4, "Inox Archer", 0, false, new Point(7, 6)));
					enemies.add(new Enemy(5, "Inox Shaman", 0, true, new Point(8, 6)));
					enemies.add(new Enemy(6, "Inox Archer", 0, false, new Point(9, 6)));
				}else if(room==1) {
					enemies.add(new Enemy(7, "Inox Shaman", 1, true, new Point(7, 2)));
					enemies.add(new Enemy(8, "Inox Archer", 1, false, new Point(8, 0)));
				}else if(room==2) {
					enemies.add(new Enemy(9, "Inox Guard", 2, false, new Point(2, 5)));
				}else if(room==3) {
					enemies.add(new Enemy(10, "Inox Shaman", 3, true, new Point(15, 6)));
				}else if(room==4) {
					enemies.add(new Enemy(11, "Inox Guard", 4, true, new Point(2, 11)));
				}else if(room==5) {
					enemies.add(new Enemy(12, "Inox Guard", 5, true, new Point(14, 11)));
				}
				break;
		
		}
		return enemies;
	}
	
}
