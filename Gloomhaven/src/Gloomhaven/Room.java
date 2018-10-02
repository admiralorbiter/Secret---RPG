package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.List;

public class Room {

	//Constants
	int SIZE_OF_HEX=100;
	
	String qBoard[][];
	String idBoard[][]; 											//Eventually want this to hold objects, not just lookup ids
	
	//[Rem] Need to make sure there are enough enemies created in setup scenario.
	//Might want to combine the two classes
	Point2D dimensions;
	
	public Room(String id, List<Player> party, List<Enemy> enemies) {
		Point2D point;
		
		
		switch(id) {
			case "Test":
				dimensions= new Point2D.Double(7, 5);
				qBoard = new String[(int) dimensions.getX()][(int) dimensions.getY()];
				idBoard = new String[(int) dimensions.getX()][(int) dimensions.getY()];
				resetBoard();
				
				point=new Point2D.Double(1, 1);
				setTileEnemy(enemies.get(0), point);
				
				point = new Point2D.Double(3, 1);
				setTileEnemy(enemies.get(1), point);
				
				point = new Point2D.Double(5, 1);
				setTileEnemy(enemies.get(2), point);
				
				point = new Point2D.Double(3, 3);
				setTilePlayer(party.get(0), point);
				
				break;
			default:
				qBoard = new String[8][8];
				idBoard = new String[8][8];
				dimensions= new Point2D.Double(8, 8);
		}
	}
	
	//[Temp] Might want to do more than just P, put an object or the full name or identifer.
	private void setTilePlayer(Player player, Point2D point) {
		player.setPoint(point);
		qBoard[(int) point.getX()][(int) point.getY()]="P";
		idBoard[(int) point.getX()][(int) point.getY()]=player.getID();
	}
	
	private void setTileEnemy(Enemy enemy, Point2D point) {
		enemy.setPoint(point);
		qBoard[(int) point.getX()][(int) point.getY()]="E";
		idBoard[(int) point.getX()][(int) point.getY()]=enemy.getID();
	}
	
	private void resetBoard() {
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY(); y++) {
				qBoard[x][y]="-";
				idBoard[x][y]=" ";
			}
		}
	}
	
	public void drawRoom(Graphics g) {
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY(); y++) {
				drawHex(g, x, y);
			}
		}
	}
	
	//[Rem] Needs to be fixed
	public void drawRange(Graphics g, Point2D start, int range, Color color) {
		int x=(int)start.getX();
		int y=(int)start.getY();
		
		g.setColor(color);
		
		if(y-range>0) {
			drawHex(g, x, y-range);
		}
		
		if(y+range<dimensions.getY()) {
			drawHex(g, x, y+range);
		}
		
		if(x-range>0) {
			drawHex(g, x-range, y);
			if(y-range>0) {
				drawHex(g, x-range, y-range);
			}
			if(y+range<dimensions.getY()) {
				drawHex(g, x-range, y+range);
			}
		}
		
		if(x+range<dimensions.getX()) {
			drawHex(g, x+range, y);
			if(y-range>0) {
				drawHex(g, x+range, y-range);
			}
			if(y+range<dimensions.getY()) {
				drawHex(g, x+range, y+range);
			}
		}
		g.setColor(Color.MAGENTA);
	}
	
	private void drawHex(Graphics g, int x, int y) {
		int nPoints=7;
		int offsetY=0;
		int offsetX=400;
		if(x%2!=0) {
			offsetY=50;
		}
		
		int[] tX = {0+x*SIZE_OF_HEX+offsetX, 50+x*SIZE_OF_HEX+offsetX, 100+x*SIZE_OF_HEX+offsetX, 150+x*SIZE_OF_HEX+offsetX, 100+x*SIZE_OF_HEX+offsetX, 50+x*SIZE_OF_HEX+offsetX, 0+x*SIZE_OF_HEX+offsetX};
		int[] tY = {50+y*SIZE_OF_HEX+offsetY, 0+y*SIZE_OF_HEX+offsetY,  0+y*SIZE_OF_HEX+offsetY, 50+y*SIZE_OF_HEX+offsetY, 100+y*SIZE_OF_HEX+offsetY, 100+y*SIZE_OF_HEX+offsetY, 50+y*SIZE_OF_HEX+offsetY};
		g.drawPolygon(tX, tY, nPoints);
		g.drawString(qBoard[x][y], 75+x*SIZE_OF_HEX+offsetX, 50+y*SIZE_OF_HEX+offsetY);
	}
	
	public void testDisplayRoom() {
		System.out.println("Quick Room");
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY();  y++) {
				System.out.print(qBoard[x][y]+" ");
			}
			System.out.println();
		}
		
		System.out.println("ID Room");
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY();  y++) {
				System.out.print(idBoard[x][y]+" ");
			}
			System.out.println();
		}
	}
	
	public Point2D getDimensions() {
		return dimensions;
	}
	
	public String[][] getqBoard(){return qBoard;}
	public String[][] getIDBoard(){return idBoard;}
	
}
