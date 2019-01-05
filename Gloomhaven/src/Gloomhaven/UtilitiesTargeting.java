package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Characters.character;
public final class UtilitiesTargeting {
	/*
	public static Point findOppisiteHex(Point povCoordinate, Point targetCoordinate) {
	
		if(povCoordinate.getX()==targetCoordinate.getX()) {
			if(povCoordinate.getY()<targetCoordinate.getY()) {
				povCoordinate.translate(1, 2);
				return povCoordinate;
			}
			else if(povCoordinate.getY()>targetCoordinate.getY()) {
				povCoordinate.translate(1, -2);
				return povCoordinate;
			}
		}else if(povCoordinate.getX()>targetCoordinate.getX()) {
			if(povCoordinate.getY()==targetCoordinate.getY()) {
				povCoordinate.translate(-2, 0);
				return povCoordinate;
			}
			else if(povCoordinate.getY()<targetCoordinate.getY()) {
				povCoordinate.translate(-1, 2);
				return povCoordinate;
			}
			else if(povCoordinate.getY()>targetCoordinate.getY()) {
				povCoordinate.translate(-1, -2);
				return povCoordinate;
			}
		}else {
			if(povCoordinate.getY()==targetCoordinate.getY()) {
				povCoordinate.translate(2, 0);
				return povCoordinate;
			}
		}
		return new Point(targetCoordinate);
	}
	*/
	
	public static List<Player> createTargetListPlayer(Hex board[][], int range, Point starting, Point dimensions, List<Player> party){
		List<Point> targetPoints = new ArrayList<Point>();
		
		createTargetList(board, range, starting, "P", dimensions);
		
		List<Player> targets = new ArrayList<Player>();
		
		for(int i=0; i<targetPoints.size(); i++) {
			for(int j=0; j<party.size(); j++) {
				if(party.get(j).getCoordinates().x==targetPoints.get(j).x && party.get(j).getCoordinates().x==targetPoints.get(j).y) {
					targets.add(party.get(j));
				}
			}
		}
		
		return targets;
	}
	
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	public static List<Point> createTargetList(Hex board[][], int range, Point starting, String quickID, Point dimensions) {
		List<Point> targets = new ArrayList<Point>();
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(starting.getY()%2!=0)
							convertedPoint=UtilitiesBoard.cubeToCoordOdd(x, y, z);
						else
							convertedPoint=UtilitiesBoard.cubeToCoordEven(x, y, z);

						int xToPlot=(int)(convertedPoint.getX()+starting.getX());
						int yToPlot=(int) (convertedPoint.getY()+starting.getY());
						
						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY()) {
								if(board[xToPlot][yToPlot]!=null)
									if(board[xToPlot][yToPlot].getQuickID().equals(quickID)){
										targets.add(new Point(xToPlot,yToPlot));
									}
							}
						}

					}
				}
			}
		
		return targets;
	}
	
	public static void highlightTargets(List<Point> targets, Graphics g) {
		for(int i=0; i<targets.size(); i++) {
			g.setColor(Setting.highlightColor);
			Draw.drawHex(g, targets.get(i));
			g.setColor(Setting.defaultColor);
		}
	}
	
	public static void drawAttack(Graphics g, HexCoordinate center, int direction, int num) {
		g.setColor(Color.cyan);
		Draw.drawHex(g, center, Setting.size, Setting.flatlayout, Setting.center);
		g.setColor(Color.cyan);

		HexCoordinate hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction));
		Draw.drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
		
		/*
		 * Note: I no longer need to check if the direction is out of bounds since I do
		 * direciton modulo 6 in the direction function.
		 */
		
		if(num>=2) {	
			if(direction+1<=5) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction+1));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(0));
			
			Draw.drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
		}
		if(num>=3) {
			if(direction-1>=0) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction-1));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(5));
					
			Draw.drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
		}
		if(num>=4) {
			if(direction+2<=5) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction+2));
			else if(direction+2==6)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(0));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(1));
			
			Draw.drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
		}
		if(num>=5) {
			if(direction-2>=0) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction-2));
			else if(direction-2==-1)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(5));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(4));
					
			Draw.drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
		}
		if(num>=6) {
			if(direction+3<=5) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction+3));
			else if(direction+3==6)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(0));
			else if(direction+3==7)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(1));
			else if(direction+3==8)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(2));
			
			Draw.drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
		}
	}
	
	
	public static boolean targetAloneToAlly(Enemy enemy, Hex[][] board, Point dimensions) {
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(board, 1, enemy.getCoordinates(), "E", dimensions);
		
		if(targets.size()>0)
			return false;
		
		return true;
	}
	
	public static boolean targetAdjacentToAlly(Enemy enemy, List<Player> party, int playerIndex, Hex[][] board, Point dimensions) {
		
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(board, 1, enemy.getCoordinates(), "P", dimensions);

		if(targets.size()>0) {
			for(int i=0; i<targets.size(); i++) {
				if(targets.get(i).equals(party.get(playerIndex).getCoordinates())) {
					targets.remove(i);
				}
			}
		}

		if(targets.size()>0)
			return true;
		else
			return false;
	}
	
}
