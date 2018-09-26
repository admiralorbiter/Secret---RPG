package Gloomhaven.TempStorage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.Player;

public class ScenarioRoom {
	List<Player> players;
	List<Enemy> enemies;
	int enemyCount;
	int height;
	int width;
	Point starting;//starting place for players this should be temp
	Point dimensions;
	
	String board[][];
	
	public ScenarioRoom(int id, List<Enemy> enemies) {
		this.enemies=enemies;
		if(id==1) {
			dimensions= new Point(7, 4);
			board = new String[7][4];
			createEmptyBoard();
			enemyCount=7;
			for(int x=0; x<enemyCount; x++) {
				enemies.add(new Enemy("Bandit Guard", x));
				board[x][0]="Bandit Guard";
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
	
	public void createEmptyBoard() {
		for(int x=0; x<dimensions.x; x++) {
			for(int y=0; y<dimensions.y; y++) {
				board[x][y]="Empty";
			}
		}
	}
	
	public Point getDim() {
		return dimensions;
	}
	
	public void addPlayers(List<Player> players) {
		this.players=players;
		for(int i=0; i<players.size(); i++) {
			board[starting.x][starting.y]=players.get(i).getName();
			players.get(i).setXY(starting);
		}
	}
	
	public String[][] getBoard() { return board;}
	
	/*
	public List<Enemy> getEnemies() {
		return enemies;
	}*/
}
