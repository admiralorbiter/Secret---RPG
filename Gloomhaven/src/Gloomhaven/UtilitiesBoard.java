package Gloomhaven;

import java.awt.Point;
import java.util.List;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;

public final class UtilitiesBoard {
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	public static Point cubeToCoordEven(int x, int y, int z ) {
		x=x+(z-(z&1))/2;
		y=z;

		Point point = new Point(x, y);
		return point;
	}
	
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	public static Point cubeToCoordOdd(int x, int y, int z) {
		x=x+(z+(z&1))/2;
		y=z;
		
		Point point = new Point(x, y);
		return point;
	}
	
	public static int[] oddToCubeCoord(Point p) {
		int cubeCoord[] = new int[3];
		int x=0;
		int y=1;
		int z=2;
		
		cubeCoord[x]=p.x-(p.y-(p.y&1))/2;
		cubeCoord[z]=p.y;
		cubeCoord[y]=-cubeCoord[x]-cubeCoord[z];
		
		return cubeCoord;
	}
	
	public static int[] evenToCubeCoord(Point p) {
		int cubeCoord[] = new int[3];
		int x=0;
		int y=1;
		int z=2;
		
		cubeCoord[x]=p.x-(p.y+(p.y&1))/2;
		cubeCoord[z]=p.y;
		cubeCoord[y]=-cubeCoord[x]-cubeCoord[z];
		
		return cubeCoord;
	}
	
	public static void updatePositions(Hex[][] board, List<Player> party, List<Enemy> enemies) {
		
		for(int i=0; i<party.size(); i++) {
			board[party.get(i).getCoordinates().x][party.get(i).getCoordinates().y].setQuickID("P");
			board[party.get(i).getCoordinates().x][party.get(i).getCoordinates().y].setID(party.get(i).getID());
		}
		
		for(int i=0; i<enemies.size(); i++) {
		
			board[enemies.get(i).getCoordinates().x][enemies.get(i).getCoordinates().y].setQuickID("E");
			board[enemies.get(i).getCoordinates().x][enemies.get(i).getCoordinates().y].setID(enemies.get(i).getID());
		}
		
		
		/*
		System.out.println("Position updated");
		for(int x=0; x<11; x++) {
			for(int y=0; y<13; y++) {
				if(board[x][y]!=null)
					if(board[x][y].getQuickID()!=" ")
						System.out.println(board[x][y].getQuickID()+":   "+x+","+y);
			}
		}
		System.out.println("");
		*/
	}
}
