package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;

public final class UtilitiesTargeting {

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
						
						if(xToPlot>=0 && xToPlot<=dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<=dimensions.getY()) {
								//System.out.println(board[xToPlot][yToPlot].getQuickID());
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
	
	public static boolean targetAloneToAlly(Enemy enemy, Room room) {
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(room.getBoard(), 1, enemy.getCoordinates(), "E", room.getDimensions());
		
		if(targets.size()>0)
			return false;
		
		return true;
	}
	
	public static boolean targetAdjacentToAlly(Enemy enemy, List<Player> party, int playerIndex, Room room) {
		
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(room.getBoard(), 1, enemy.getCoordinates(), "P", room.getDimensions());

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
