package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

public class Room {

	//Constants
	int SIZE_OF_HEX=60;
	
	String qBoard[][];
	String idBoard[][]; 											//Eventually want this to hold objects, not just lookup ids
	
	//[Rem] Need to make sure there are enough enemies created in setup scenario.
	//Might want to combine the two classes
	Point2D dimensions;
	private Point selectionCoordinates;
	
	public void setSelectionCoordinates(Point newCoordinates) {selectionCoordinates=newCoordinates;}
	public Point getSelectionCoordinates() {return selectionCoordinates;}
	
	
	public Room(String id, List<Player> party, List<Enemy> enemies) {
		
		selectionCoordinates = new Point(0, 0);
		
		Point point;
		
		
		switch(id) {
			case "Test":
				dimensions= new Point2D.Double(10, 10);
				qBoard = new String[(int) dimensions.getX()][(int) dimensions.getY()];
				idBoard = new String[(int) dimensions.getX()][(int) dimensions.getY()];
				resetBoard();
				
				point=new Point(1, 1);
				setTileEnemy(enemies.get(0), point);
				
				point = new Point(3, 1);
				setTileEnemy(enemies.get(1), point);
				
				point = new Point(5, 1);
				setTileEnemy(enemies.get(2), point);
				
				point = new Point(3, 3);
				setTilePlayer(party.get(0), point);
				
				break;
			default:
				qBoard = new String[8][8];
				idBoard = new String[8][8];
				dimensions= new Point2D.Double(8, 8);
		}
	}
	
	//[Temp] Might want to do more than just P, put an object or the full name or identifer.
	private void setTilePlayer(Player player, Point point) {
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
	
	public void movePlayer(Point starting, Point ending) {
		qBoard[(int) ending.getX()][(int) ending.getY()]=qBoard[(int) starting.getX()][(int) starting.getY()];
		idBoard[(int) ending.getX()][(int) ending.getY()]=idBoard[(int) starting.getX()][(int) starting.getY()];
	
		qBoard[(int) starting.getX()][(int) starting.getY()]="-";
		idBoard[(int) starting.getX()][(int) starting.getY()]=" ";
	}
	
	public void drawSelectionHex(Graphics g) {
		g.setColor(Color.RED);
		drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY());
		g.setColor(Color.MAGENTA);
	}
	
	/*
	 * All I need to do is find the distance between the points. If it is <= range, then draw it.
	 * 
	 * 
	 */
	
	/*
	public void drawRange(Graphics g, Point2D start, int range, Color color) {
		
		g.setColor(color);
		int x=(int)start.getX();
		int y=(int)start.getY();
		double rangeTemp=range+1;
		for(int rangeX=0; rangeX<=range; rangeX++) {
			
			for(int rangeY=0; rangeY<=range; rangeY++) {

				if(!(rangeY==range && rangeX==range)) {
					if(y-rangeY>=0) {
						
						if(start.distance(x, y-rangeY)<=rangeTemp)
							drawHex(g, x, y-rangeY);
					}
					
					if(y+rangeY<dimensions.getY()) {
						if(start.distance(x, y+rangeY)<=rangeTemp)
							drawHex(g, x, y+rangeY);
					}
		
					if(x-rangeX>=0) {
						if(start.distance(x-rangeX, y)<=rangeTemp)
							drawHex(g, x-rangeX, y);
						if(!(rangeX==range)) {
							if(y-rangeY>0) {
								//System.out.println(rangeX+","+rangeY+"   "+start.distance(x-rangeX, y-rangeY));
								if(start.distance(x-rangeX, y-rangeY)<=rangeTemp)
									drawHex(g, x-rangeX, y-rangeY);
							}
							if(y+rangeY<dimensions.getY()) {
								if(start.distance(x-rangeX, y-rangeY)<=rangeTemp)
									drawHex(g, x-rangeX, y+rangeY);
							}
						}	
						
					}	
					
					if(x+rangeX<dimensions.getX()) {
						if(start.distance(x+rangeX, y)<=rangeTemp)
							drawHex(g, x+rangeX, y);
						
						if(y-rangeY>0) {
							if(start.distance(x+rangeX, y-rangeY)<=rangeTemp)
								drawHex(g, x+rangeX, y-rangeY);
						}
						if(y+rangeY<dimensions.getY()) {
							if(start.distance(x+rangeX, y+rangeY)<=rangeTemp)
								drawHex(g, x+rangeX, y+rangeY);
						}
					}
				}
			}
		}
		start.setLocation(x, y);
		g.setColor(Color.MAGENTA);
	}
	*/
	
	public void drawRange(Graphics g, Point2D start, int range, Color color) {
		
		g.setColor(color);

		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						System.out.println(x+","+y+","+z+"           "+(int)(x+start.getX())+","+(int) (y+start.getY()));
						drawHex(g, (int)(x+start.getX()),(int) (y+start.getY()));
						/*
						if(x<0)
							drawHex(g, (int)(x+start.getX()+1),(int) (z+start.getY()));
						else if(x>0)
							drawHex(g, (int)(x+start.getX()-1),(int) (z+start.getY()));
						else
							drawHex(g, (int)(x+start.getX()),(int) (z+start.getY()));
						*/
					}
				}
			}
		}
		
		g.setColor(Color.MAGENTA);
	}
	
	private void drawHex(Graphics g, int x, int y) {
		int nPoints=7;
		int offsetY=0;
		int offsetX=400;
		int bufferY=-20;
		int bufferX=0;
		if(y%2!=0) {
			offsetX=430;
			//offsetY=-40;
		}
		else {
			bufferY=-20;
		}
		/*
		if(x%2!=0) {
			offsetY=40;
			bufferX=0;
			bufferY=20;
		}*/

		//int[] tX = {0+x*SIZE_OF_HEX+offsetX, 50+x*SIZE_OF_HEX+offsetX, 100+x*SIZE_OF_HEX+offsetX, 150+x*SIZE_OF_HEX+offsetX, 100+x*SIZE_OF_HEX+offsetX, 50+x*SIZE_OF_HEX+offsetX, 0+x*SIZE_OF_HEX+offsetX};
		//int[] tY = {50+y*SIZE_OF_HEX+offsetY, 0+y*SIZE_OF_HEX+offsetY,  0+y*SIZE_OF_HEX+offsetY, 50+y*SIZE_OF_HEX+offsetY, 100+y*SIZE_OF_HEX+offsetY, 100+y*SIZE_OF_HEX+offsetY, 50+y*SIZE_OF_HEX+offsetY};
		
		int[] tX = {0+x*(SIZE_OF_HEX+bufferX)+offsetX, 30+x*(SIZE_OF_HEX+bufferX)+offsetX, 60+x*(SIZE_OF_HEX+bufferX)+offsetX, 60+x*(SIZE_OF_HEX+bufferX)+offsetX, 30+x*(SIZE_OF_HEX+bufferX)+offsetX, 0+x*(SIZE_OF_HEX+bufferX)+offsetX, 0+x*(SIZE_OF_HEX+bufferX)+offsetX};
		int[] tY = {20+y*(SIZE_OF_HEX+bufferY)+offsetY, 0+y*(SIZE_OF_HEX+bufferY)+offsetY,  20+y*(SIZE_OF_HEX+bufferY)+offsetY, 40+y*(SIZE_OF_HEX+bufferY)+offsetY, 60+y*(SIZE_OF_HEX+bufferY)+offsetY, 40+y*(SIZE_OF_HEX+bufferY)+offsetY, 20+y*(SIZE_OF_HEX+bufferY)+offsetY};
		g.drawPolygon(tX, tY, nPoints);
		g.drawString(qBoard[x][y], 30+x*(SIZE_OF_HEX+bufferX)+offsetX, 35+y*(SIZE_OF_HEX+bufferY)+offsetY);
		g.drawString(x+", "+y, 20+x*(SIZE_OF_HEX+bufferX)+offsetX, 20+y*(SIZE_OF_HEX+bufferY)+offsetY);
	}
	
	public void highlightTargets(List<Point> targets, Graphics g) {
		for(int i=0; i<targets.size(); i++) {
			g.setColor(Color.GREEN);
			drawHex(g, (int)targets.get(i).getX(), (int)targets.get(i).getY());
			g.setColor(Color.MAGENTA);
		}
	}
	
	public String getID(Point point) {
		return idBoard[(int)point.getX()][(int)point.getY()];
	}
	
	public boolean isSpace(Point point, String check) {
		if(qBoard[(int)point.getX()][(int)point.getY()]==check)
			return true;
		
		return false;
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
	
	public boolean isSpaceEmpty(Point space) {
		if(qBoard[(int) space.getX()][(int) space.getY()]=="-")
			return true;
		
		return false;
	}
	
	public String[][] getqBoard(){return qBoard;}
	public String[][] getIDBoard(){return idBoard;}
	
}
