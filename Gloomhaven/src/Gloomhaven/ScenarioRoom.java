package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScenarioRoom {
	List<Player> players;
	List<Enemy> enemies;
	int enemyCount;
	int height;
	int width;
	Point starting;//starting place for players this should be temp
	
	public ScenarioRoom(int id, List<Enemy> enemies) {
		this.enemies=enemies;
		if(id==1) {
			enemyCount=7;
			for(int i=0; i<enemyCount; i++) {
				enemies.add(new Enemy("Bandit Guard", i));
			}
			
				enemies.get(1).makeElite();
				enemies.get(3).makeElite();
				enemies.get(5).makeElite();
			
				height=5;
				width=7;
				
				enemies.get(0).setXY(new Point(0, 0));
				enemies.get(1).setXY(new Point(1, 0));
				enemies.get(2).setXY(new Point(2, 0));
				enemies.get(3).setXY(new Point(3, 0));
				enemies.get(4).setXY(new Point(4, 0));
				enemies.get(5).setXY(new Point(5, 0));
				enemies.get(6).setXY(new Point(6, 0));
				starting = new Point(3, 3);
				
		}
		
	}
	
	public void addPlayers(List<Player> players) {
		this.players=players;
		for(int i=0; i<players.size(); i++) {
			players.get(i).setXY(starting);
		}
	}
	
	/*
	public List<Enemy> getEnemies() {
		return enemies;
	}*/
}
