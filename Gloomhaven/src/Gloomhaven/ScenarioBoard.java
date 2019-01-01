package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;

public class ScenarioBoard {
	private HexCoordinate board[][];	
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Point dimensions;
	private Draw drawBoard;
	
	public ScenarioBoard(ScenarioData data) {
		dimensions = new Point(data.getBoardSize());
		board=new HexCoordinate[(int) data.getBoardSize().getX()][(int) data.getBoardSize().getY()];
		resetBoard();
		drawBoard = new Draw(board, dimensions);
		enemies=ScenarioEnemyLoader.getEnemies(data.getId(), 0);	
	}
	
	public void run(Graphics g) {
		drawBoard.drawBoard(g);
	}
	
	//Creates and resets the room board
	private void resetBoard() {
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY(); y++) {
				board[x][y]=new HexCoordinate(x, y);
			}
		}
	}
}
