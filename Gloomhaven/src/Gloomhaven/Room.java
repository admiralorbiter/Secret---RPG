package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;


public class Room {

	//Constants
	private Setting setting = new Setting();
	private Point dimensions;																							//Dimensions of the room
	private Hex board[][];																								//Room board made of hexs
	private Point selectionCoordinates = new Point(0, 0);																//Where the selection icon is located
	
	public Room(String id, List<Player> party, List<Enemy> enemies) {
		Point point;																									//Temp point used to set enemy
		
		switch(id) {
			case "Test":
				dimensions= new Point(9, 9);
				board=new Hex[9][9];
				resetBoard();
				
				//Create enemy 1
				point=new Point(1, 1);
				setTileEnemy(enemies.get(0), point);
				
				//Create enemy 2
				point = new Point(3, 1);
				setTileEnemy(enemies.get(1), point);
				
				//Create enemy 3
				point = new Point(5, 1);
				setTileEnemy(enemies.get(2), point);
				
				//Create player
				point = new Point(3, 3);
				setTilePlayer(party.get(0), point);
				
				point = new Point(5, 5);
				board[5][5].setHex("Loot", "Gold");
				break;
				
			default:
				dimensions= new Point(0, 0);
				board=new Hex[0][0];
				resetBoard();
		}
	}
	
	//Getter Functions
	public String getID(Point point) {return board[(int)point.getX()][(int)point.getY()].getID();}
	public String getQuickID(Point point) {return board[(int)point.getX()][(int)point.getY()].getQuickID();}
	public String getLootID(Point point) {return board[(int)point.getX()][(int)point.getY()].getLootID();}
	public Point getDimensions() {return dimensions;}
	public boolean isSpaceEmpty(Point space) {return board[(int) space.getX()][(int) space.getY()].getSpaceFree();}
	public Hex[][] getBoard(){return board;}
	
	//Creates and resets the room board
	private void resetBoard() {
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY(); y++) {
				board[x][y]=new Hex(x, y);
			}
		}
	}

	//Sets the tile for the player
	private void setTilePlayer(Player player, Point point) {
		player.setPoint(point);
		board[(int) point.getX()][(int) point.getY()].setHex("P", player.getID());
	}
	
	//Sets the tile for the enemy
	private void setTileEnemy(Enemy enemy, Point point) {
		enemy.setPoint(point);
		board[(int) point.getX()][(int) point.getY()].setHex("E", enemy.getID());
	}
	
	//Hex Selection Functions
	public void setSelectionCoordinates(Point newCoordinates) {selectionCoordinates=newCoordinates;}
	public Point getSelectionCoordinates() {return selectionCoordinates;}
	public void drawSelectionHex(Graphics g) {
		g.setColor(Color.RED);
		drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY());
		g.setColor(Color.MAGENTA);
	}
	
	//Draws the current room
	public void drawRoom(Graphics g) {
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY(); y++) {
				drawHex(g, x, y);
			}
		}
	}
	
	//Moves the player from one point to another
	public void movePlayer(Player player, Point ending) {	
		Point starting=player.getCoordinate();
		if(board[(int) ending.getX()][(int) ending.getY()].getQuickID().equals("Loot")) {
			player.addLoot(board[(int) ending.getY()][(int) ending.getY()]);
		}
				
		String quickID=board[(int) starting.getX()][(int) starting.getY()].getQuickID();
		String id=board[(int) starting.getX()][(int) starting.getY()].getID();
		board[(int) ending.getX()][(int) ending.getY()].setHex(quickID, id);
		board[(int) starting.getX()][(int) starting.getY()].reset();
	}
	
	public void moveEnemy(Enemy enemy, Point ending) {
		Point starting=enemy.getCoordinate();
		System.out.println(starting+","+ending);
		String quickID=board[(int) starting.getX()][(int) starting.getY()].getQuickID();
		String id=board[(int) starting.getX()][(int) starting.getY()].getID();
		board[(int) ending.getX()][(int) ending.getY()].setHex(quickID, id);
		board[(int) starting.getX()][(int) starting.getY()].reset();
	}
	
	
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	public void drawRange(Graphics g, Point start, int range, Color color) {
				
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {																	//If the x,y,z axial coordinate's are equal to zero
						Point convertedPoint = new Point();
						
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(start.getY()%2!=0)														//If the column is odd use odd conversion
							convertedPoint=cubeToCoordOdd(x, y, z);
						else
							convertedPoint=cubeToCoordEven(x, y, z);								//If the column is even use even conversion
						
						//Plotted point is equal to the converted point + player point
						int xToPlot=(int)(convertedPoint.getX()+start.getX());					
						int yToPlot=(int) (convertedPoint.getY()+start.getY());
						
						//Checks that the plotted x and y are inside the dimensions
						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY()) {
								g.setColor(color);
								drawHex(g, xToPlot,  yToPlot);
							}
					}
				}
			}
		}
		//[Rem] Move default color to settings
		g.setColor(setting.getDefaultColor());																	//Resets the color
	}
	
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	private Point cubeToCoordEven(int x, int y, int z ) {
		x=x+(z-(z&1))/2;
		y=z;

		Point point = new Point(x, y);
		return point;
	}
	
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	private Point cubeToCoordOdd(int x, int y, int z) {
		x=x+(z+(z&1))/2;
		y=z;
		
		Point point = new Point(x, y);
		return point;
	}
	
	//Draws the hex given an x, y coordinate, Draws the quick id of the hex, Draws the x, y coordinate in the hex
	private void drawHex(Graphics g, int x, int y) {
		int nPoints=7;												//Points in a hex
		int SIZE_OF_HEX=setting.getSizeOfHex();
		int offsetY=setting.getOffsetY();
		int offsetX=setting.getOffsetX();
		int bufferY=-1*setting.getSizeOfHex()/3;
		if(y%2!=0) {
			offsetX=offsetX+setting.getSizeOfHex()/2;
		}
		
		int[] tX = {0+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX, 0+x*(SIZE_OF_HEX)+offsetX, 0+x*(SIZE_OF_HEX)+offsetX};
		int[] tY = {SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY, 0+y*(SIZE_OF_HEX+bufferY)+offsetY,  SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX*2/3+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX*2/3+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY};
		g.drawPolygon(tX, tY, nPoints);
		if(board[x][y].getQuickID()=="P")
			g.setColor(setting.getPlayerColor());
		else if(board[x][y].getQuickID()=="E")
		{
			if(board[x][y].getID().contains("Elite"))
				g.setColor(setting.getEliteEnemyColor());
			else
				g.setColor(setting.getEnemyColor());
			
		}
		if(board[x][y].getQuickID()=="Loot") {
			g.setColor(Color.ORANGE);
			g.drawString(board[x][y].getLootID(), SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX-15, SIZE_OF_HEX/2+5+y*(SIZE_OF_HEX+bufferY)+offsetY);
		}
		else
			g.drawString(board[x][y].getQuickID(), SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+5+y*(SIZE_OF_HEX+bufferY)+offsetY);
		g.drawString(x+", "+y, SIZE_OF_HEX/3+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY);
		g.setColor(setting.getDefaultColor());
	}
	
	//Highlights are the target points in a list
	public void highlightTargets(List<Point> targets, Graphics g) {
		for(int i=0; i<targets.size(); i++) {
			g.setColor(setting.getHighlightColor());
			drawHex(g, (int)targets.get(i).getX(), (int)targets.get(i).getY());
			g.setColor(setting.getDefaultColor());
		}
	}
	
	//Checks if a space is occupied by the string
	public boolean isSpace(Point point, String check) {
		if(board[(int)point.getX()][(int)point.getY()].getQuickID()==check)
			return true;
		
		return false;
	}
	
	//[Test] Displays room in console
	public void testDisplayRoom() {
		System.out.println("Quick Room");
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY();  y++) {
				System.out.print(board[x][y].getQuickID()+" ");
			}
			System.out.println();
		}
		System.out.println("ID Room");
		for(int x=0; x<dimensions.getX(); x++) {
			for(int y=0; y<dimensions.getY();  y++) {
				System.out.print(board[x][y].getID()+" ");
			}
			System.out.println();
		}
	}
	
	public void loot(Player player, List<Point> loot) {
		for(int i=0; i<loot.size(); i++) {
			player.addLoot(board[(int) loot.get(i).getX()][(int) loot.get(i).getY()]);
			board[(int) loot.get(i).getX()][(int) loot.get(i).getY()].reset();
		}
	}
	
	public void loot(Player player, Point loot) {
		player.addLoot(board[(int) loot.getX()][(int) loot.getY()]);
		board[(int) loot.getX()][(int) loot.getY()].reset();
	}
}
