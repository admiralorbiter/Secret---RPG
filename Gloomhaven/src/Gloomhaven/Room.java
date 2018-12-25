package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;

public class Room {

	//Constants
	private Setting setting = new Setting();
	//TODO change dimensions back to a single point that covers the whole map
	private List<Point> dimensions = new ArrayList<Point>();
	//private Point dimensions;																							//Dimensions of the room
	private Hex board[][];																								//Room board made of hexs
	private Point selectionCoordinates = new Point(0, 0);																//Where the selection icon is located
	private int roomIndex;
	private int width=0;
	private int height=0;
	
	public Room(String id, List<Player> party, List<Enemy> enemies){
		Point point;																									//Temp point used to set enemy
		//String id=setup.getRoomID();
		//SetupScenario setup = new SetupScenario(sceneID);
		//enemies=setup.getEnemies();	
		switch(id) {
			case "Test":
				dimensions.add(new Point(9, 9));
				dimensions.add(new Point(3, 3));
				width=13;
				height=11;
				board=new Hex[width][height];
				resetBoard();
				showRoom(0);
				roomIndex=0;
				
				// pass the path to the file as a parameter 
			    File file = new File("src/Gloomhaven/unusedspace.txt"); 
				Scanner sc = null;
				try {
					sc = new Scanner(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			  
			    while (sc.hasNextLine()) 
			    {
			    	String line = sc.nextLine();
			    	int x=Integer.parseInt(line);
			    	line=sc.nextLine();
			    	int y=Integer.parseInt(line);
			    	line=sc.nextLine();

			    	if(line.equals("X")) {
			    		board[x][y].disableHex();
			    	}
			    }
			    
			    file = new File("src/Gloomhaven/roomlayout.txt"); 
				sc = null;
				try {
					sc = new Scanner(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				int enemyCount=0;
			    while (sc.hasNextLine()) 
			    {
			    	String line = sc.nextLine();
			    	int x=Integer.parseInt(line);
			    	line=sc.nextLine();
			    	int y=Integer.parseInt(line);
			    	String quickIDtemp = sc.nextLine();
			    	String idtemp = sc.nextLine();
			    	point = new Point(x, y);
			    	if(quickIDtemp.equals("Player")) {
			    		setTilePlayer(party.get(0), point);
			    	}else if(quickIDtemp.equals("Enemy")) {
			    		
			    		enemies.add(new Enemy(enemyCount, idtemp));
			    		setTileEnemy(enemies.get(enemyCount), point);
			    		enemyCount++;
			    	}else if(quickIDtemp.equals("Door")) {
			    		int doorID=Integer.parseInt(sc.nextLine());
			    		board[x][y].setHex(quickIDtemp, idtemp, doorID);
			    	}
			    	else {
			    		board[x][y].setHex(quickIDtemp, idtemp);
			    	}
			    		
			    }
			  
			    /*
				point = new Point(4, 7);
				board[4][7].setHex("Door", "Door", 1);
				
				point  = new Point(8, 5);
				board[8][5].setHex("Door", "Door",2);
				
				//Create enemy 1
				point=new Point(3, 5);
				setTileEnemy(enemies.get(0), point);
				
				//Create enemy 2
				point = new Point(3, 7);
				setTileEnemy(enemies.get(1), point);
				
				//Create enemy 3
				point = new Point(3, 9);
				setTileEnemy(enemies.get(2), point);
				
				//Create player
				point = new Point(0, 7);
				setTilePlayer(party.get(0), point);
				
				//Create player 2
				if(party.size()==2) {
					point = new Point(4, 2);
					setTilePlayer(party.get(1), point);
				}
				
				
				point = new Point(12, 7);
				board[12][7].setHex("Loot", "Gold", 0);
				*/
				break;
				
			default:
				dimensions.get(0).setLocation(new Point(0, 0));
				board=new Hex[0][0];
				resetBoard();
		}
	}
	
	//Getter Functions
	public String getID(Point point) {return board[(int)point.getX()][(int)point.getY()].getID();}
	public String getQuickID(Point point) {return board[(int)point.getX()][(int)point.getY()].getQuickID();}
	public String getLootID(Point point) {return board[(int)point.getX()][(int)point.getY()].getLootID();}
	public Point getDimensions(int index) {return dimensions.get(index);}
	public Point getDimensions() {return dimensions.get(roomIndex);}
	//public Point getDimensions() {return new Point(width, height);}
	public boolean isSpaceEmpty(Point space) {return board[(int) space.getX()][(int) space.getY()].getSpaceFree();}
	public Hex[][] getBoard(){return board;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	//Creates and resets the room board
	private void resetBoard(int index) {
		for(int x=0; x<dimensions.get(index).getX(); x++) {
			for(int y=0; y<dimensions.get(index).getY(); y++) {
				board[x][y]=new Hex(x, y);
			}
		}
	}
	
	//Creates and resets the room board
	private void resetBoard() {
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				board[x][y]=new Hex(x, y);
			}
		}
	}
	
	private void showRoom(int index) {
		
		File file = new File("src/Gloomhaven/test.txt"); 
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  
	    while (sc.hasNextLine()) 
	    {
	    	String line = sc.nextLine();
	    	line=sc.nextLine();
	    	
	    	if(Integer.parseInt(line)==index) {

	    		int startX=Integer.parseInt(sc.nextLine());
	    		int endX=Integer.parseInt(sc.nextLine());
	    		int startY=Integer.parseInt(sc.nextLine());
	    		int endY=Integer.parseInt(sc.nextLine());
	    		for(int x=startX; x<=endX; x++) {
	    			for(int y=startY; y<=endY; y++) {
	    				board[x][y].toggleShowHex();
	    			}
	    		}
	    		int doorX=Integer.parseInt(sc.nextLine());
	    		int doorY=Integer.parseInt(sc.nextLine());
	    		if(doorX!=-1)
	    			board[doorX][doorY].toggleShowHex();
	    		break;
	    	}else {
	    		for(int i=0; i<6; i++)
	    			sc.nextLine();
	    	}
	    }
		/*
		for(int x=0; x<dimensions.get(index).getX(); x++) {
			for(int y=0; y<dimensions.get(index).getY(); y++) {
				board[x][y].toggleShowHex();
			}
		}*/
	}

	//Sets the tile for the player
	private void setTilePlayer(Player player, Point point) {
		player.setCoordinates(point);
		board[(int) point.getX()][(int) point.getY()].setHex("P", player.getID());
	}
	
	//Sets the tile for the enemy
	private void setTileEnemy(Enemy enemy, Point point) {
		enemy.setCoordinates(point);
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
	
	public void drawSelectionHexSemiCircle(Graphics g, int pointFlag) {
		g.setColor(Color.BLUE);
	
		drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY());
		
		if(pointFlag==0)
		{
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()+1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY());
		}
		else if(pointFlag==1) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()-1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()+1);
		}
		else if(pointFlag==2) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()-1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY());
		}
		else if(pointFlag==3) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY());
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()-1);
		}
		else if(pointFlag==4) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()+1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()-1);
		}
		else if(pointFlag==5) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY());
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()+1);
		}
		

		g.setColor(Color.MAGENTA);
	}
	
	/*
	 * Note: There needs to be a way to have two options for the first hex so it can give the player all the options.
	 */
	public void drawSelectionHexAdjCircle(Graphics g, int pointFlag) {
		g.setColor(Color.RED);
	
		drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY());
		
		if(pointFlag==0)
		{
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()+1);
		}
		else if(pointFlag==1) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()+1);
		}
		else if(pointFlag==2) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY());
		}
		else if(pointFlag==3) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()-1);
		}
		else if(pointFlag==4) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()-1);
		}
		else if(pointFlag==5) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY());
		}
		

		g.setColor(Color.MAGENTA);
	}
	
	public void drawSelectionHexTriangle(Graphics g, int pointFlag) {
		g.setColor(Color.RED);
	
		drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY());
		
		if(pointFlag==0)
		{
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()+1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY());
		}
		else if(pointFlag==1) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()+1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()+1);
		}
		else if(pointFlag==2) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY());
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()+1);
		}
		else if(pointFlag==3) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()-1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY());
			
		}
		else if(pointFlag==4) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY()-1);
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()-1);
		}
		else if(pointFlag==5) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY());
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()-1);
		}
		

		g.setColor(Color.MAGENTA);
	}
	
	public void drawSelectionHexAdjOpposing(Graphics g, int pointFlag) {
		g.setColor(Color.RED);
	
		drawHex(g, (int)selectionCoordinates.getX(), (int)selectionCoordinates.getY());
		
		if(pointFlag==0)
		{
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()+2);
		}
		else if(pointFlag==1) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+2, (int)selectionCoordinates.getY());
		}
		else if(pointFlag==2) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()+1, (int)selectionCoordinates.getY()-2);
		}
		else if(pointFlag==3) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()-2);
		}
		else if(pointFlag==4) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-2, (int)selectionCoordinates.getY());
		}
		else if(pointFlag==5) {
			g.setColor(Color.BLUE);
			drawHex(g, (int)selectionCoordinates.getX()-1, (int)selectionCoordinates.getY()+2);
		}
		

		g.setColor(Color.MAGENTA);
	}
	
	//Draws the current room
	public void drawRoom(Graphics g, int roomIndex) {
		for(int y=0; y<dimensions.get(roomIndex).getY(); y++) {
			for(int x=0; x<dimensions.get(roomIndex).getX(); x++) {
				if(board[x][y].showHex())
					drawHex(g, x, y);
			}
		}
	}
	
	public void drawBoard(Graphics g) {
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				
				if(board[x][y].showHex())
					drawHex(g, x, y);
			}
		}
	}
	
	//Moves the player from one point to another
	public void movePlayer(Player player, Point ending) {	
		Point starting=player.getCoordinates();
		if(board[(int) ending.getX()][(int) ending.getY()].getQuickID().equals("Loot")) {
			player.addLoot(board[(int) ending.getY()][(int) ending.getY()]);
		}
		
		if(board[(int) ending.getX()][(int) ending.getY()].getDoor() &&(board[(int) ending.getX()][(int) ending.getY()].doorOpen()==false)) {
			showRoom(board[(int) ending.getX()][(int) ending.getY()].getRoomID());
		}
				
		String quickID=board[(int) starting.getX()][(int) starting.getY()].getQuickID();
		String id=board[(int) starting.getX()][(int) starting.getY()].getID();
		board[(int) ending.getX()][(int) ending.getY()].setHex(quickID, id);
		board[(int) starting.getX()][(int) starting.getY()].reset();
	}
	
	public void moveEnemy(Enemy enemy, Point ending) {
		Point starting=enemy.getCoordinates();
		enemy.setCoordinates(ending);

		String quickID=board[(int) starting.getX()][(int) starting.getY()].getQuickID();
		String id=board[(int) starting.getX()][(int) starting.getY()].getID();
		board[(int) ending.getX()][(int) ending.getY()].setHex(quickID, id);
		board[(int) starting.getX()][(int) starting.getY()].reset();
	}
	
	public boolean moveEnemyOneHexCloser(Enemy enemy, Point player) {
		boolean meleeRange=UtilitiesAB.checkMeleeRange(enemy, board, "P", new Point(width, height));
		if(meleeRange==false) {
			Point temp = new Point(enemy.getCoordinates());
		
			if((enemy.getCoordinates().getX()-player.getX())>0)
				temp.translate(-1, 0);
			else
				temp.translate(1, 0);

			if(board[(int)temp.getX()][(int)temp.getY()].getSpaceFree()) {
				moveEnemy(enemy, new Point(temp));
				return true;
			}

			temp = new Point(enemy.getCoordinates());
			if((enemy.getCoordinates().getY()-player.getY())>0)
				temp.translate(0, -1);
			else
				temp.translate(0, 1);

			if(board[(int)temp.getX()][(int)temp.getY()].getSpaceFree()) {
				moveEnemy(enemy, new Point(temp));
				return true;
			}
			
		}
		
		return false;
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
						if(xToPlot>=0 && xToPlot<dimensions.get(roomIndex).getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.get(roomIndex).getY()) {
								if(!board[xToPlot][yToPlot].getHidden()) {
									g.setColor(color);
									drawHex(g, xToPlot,  yToPlot);
								}
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
	
	/*
	 * dx and dy are where the x and y are drawn, while x and y are the actual coordinates for the hex in the main array
	 */
	public void drawRoom(Graphics g, Point center) {
		int view=2;
		int dy=0;
		int dx=0;
		for(int y=(int) (center.getY()-view); y<=(int) (center.getY()+view); y++) {
			for(int x=(int) (center.getX()-view); x<=(int) (center.getX()+view); x++) {
				if(x>=0 && y>=0) {
					if(x<=dimensions.get(roomIndex).getX() && y<=dimensions.get(roomIndex).getY()) {
						drawHex(g, dx, dy, x, y);
					}
				}
				dx++;
			}
			dx=0;
			dy++;
		}
	}
	
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	/*
	 * 
	 * This was going to be the view window, but it is hard to figure out how to do the offset with a range draw.
	 */
	public void drawRoom(Graphics g, Point start, int range) {
		Point drawWindow = new Point(0, 0);
		
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
						if(xToPlot>=0 && xToPlot<dimensions.get(roomIndex).getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.get(roomIndex).getY()) {
								//drawHex(g, xToPlot,  yToPlot);
								drawHex(g, (int)drawWindow.getX(), (int)drawWindow.getY(), xToPlot, yToPlot);
							}
					}
				}
			}
		}
		//[Rem] Move default color to settings
		g.setColor(setting.getDefaultColor());																	//Resets the color
	}
	
	//Draws the hex given an x, y coordinate, Draws the quick id of the hex, Draws the x, y coordinate in the hex
	public void drawHex(Graphics g, int x, int y) {
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
	
	//Draws the hex given an x, y coordinate, Draws the quick id of the hex, Draws the x, y coordinate in the hex
	public void drawHex(Graphics g, int dx, int dy, int x, int y) {
		int nPoints=7;												//Points in a hex
		int SIZE_OF_HEX=setting.getSizeOfHex();
		int offsetY=setting.getOffsetY();
		int offsetX=setting.getOffsetX();
		int bufferY=-1*setting.getSizeOfHex()/3;
		if(y%2!=0) {
			offsetX=offsetX+setting.getSizeOfHex()/2;
		}
		
		int[] tX = {0+dx*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+dx*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX+dx*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX+dx*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+dx*(SIZE_OF_HEX)+offsetX, 0+dx*(SIZE_OF_HEX)+offsetX, 0+dx*(SIZE_OF_HEX)+offsetX};
		int[] tY = {SIZE_OF_HEX/3+dy*(SIZE_OF_HEX+bufferY)+offsetY, 0+dy*(SIZE_OF_HEX+bufferY)+offsetY,  SIZE_OF_HEX/3+dy*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX*2/3+dy*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX+dy*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX*2/3+dy*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX/3+dy*(SIZE_OF_HEX+bufferY)+offsetY};
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
			g.drawString(board[x][y].getLootID(), SIZE_OF_HEX/2+dx*(SIZE_OF_HEX)+offsetX-15, SIZE_OF_HEX/2+5+dy*(SIZE_OF_HEX+bufferY)+offsetY);
		}
		else
			g.drawString(board[x][y].getQuickID(), SIZE_OF_HEX/2+dx*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+5+dy*(SIZE_OF_HEX+bufferY)+offsetY);
		g.drawString(x+", "+y, SIZE_OF_HEX/3+dx*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/3+dy*(SIZE_OF_HEX+bufferY)+offsetY); 
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
		for(int x=0; x<dimensions.get(roomIndex).getX(); x++) {
			for(int y=0; y<dimensions.get(roomIndex).getY();  y++) {
				System.out.print(board[x][y].getQuickID()+" ");
			}
			System.out.println();
		}
		System.out.println("ID Room");
		for(int x=0; x<dimensions.get(roomIndex).getX(); x++) {
			for(int y=0; y<dimensions.get(roomIndex).getY();  y++) {
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
