package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
						if(starting.getX()%2!=0)
							convertedPoint=UtilitiesBoard.cubeToCoordOdd(x, y, z);
						else
							convertedPoint=UtilitiesBoard.cubeToCoordEven(x, y, z);

						int xToPlot=(int)(convertedPoint.getX()+starting.getX());
						int yToPlot=(int) (convertedPoint.getY()+starting.getY());

						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY())
								if(board[xToPlot][yToPlot].getQuickID().equals(quickID)){
									targets.add(new Point(xToPlot,yToPlot));
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
	
	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	//[Rem] Should evaluate whether it is faster to just create the full list using one function and no quick melee check
	public static boolean checkMeleeRange(character entity, Hex board[][], String lookingForID, Point dimensions) {
		
		Point coordinates = entity.getCoordinates();
		int x=(int) coordinates.getX();
		int y=(int) coordinates.getY();
	
		if(x-1>0) {
			if(board[x-1][y].getQuickID()==lookingForID)
				return true;
			if(y-1>0) {
				if(board[x-1][y-1].getQuickID()==lookingForID) {
					return true;
				}
			}
			if(y+1<dimensions.getY()) {
				if(board[x-1][y+1].getQuickID()==lookingForID) {
					return true;
				}
			}
		}
		
		if(x+1<dimensions.getX()) {
			if(board[x+1][y].getQuickID()==lookingForID)
				return true;
			if(y-1>0) {
				if(board[x+1][y-1].getQuickID()==lookingForID) {
					return true;
				}
			}
			if(y+1<dimensions.getY()) {
				if(board[x+1][y+1].getQuickID()==lookingForID) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static List<character> createMeleeTargetList(Hex board[][], List<character> badguys, character goodguy, Point dimensions){
		List<character> targets = new ArrayList<character>();
		targets=checkRange(board, 1, badguys, goodguy, dimensions);
		
		return targets;
	}
	
	//[Rem] This has to be a way to abstract this for both player and enemies
	public static List<character> playersInRangeEstimate(Hex board[][], List<character> party, Enemy enemy, Point dimensions){
		List<character> targets = new ArrayList<character>();
		
		if(enemy.getBaseStats().getRange()<=1)
			return targets;
		
		for(int r=2; r<=enemy.getBaseStats().getRange(); r++) {
			targets=checkRange(board, r, party, enemy, dimensions);
		}
		
		return targets;
	}
	
	public static List<character> checkRange(Hex board[][], int range, List<character> badguys, character goodguy, Point dimensions) {
		List<String> idList = new ArrayList<String>();
		List<character> targets = new ArrayList<character>();
		
		String lookingForID=goodguy.getID().substring(1,2);
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(goodguy.coordinates.getX()%2!=0)
							convertedPoint=UtilitiesBoard.cubeToCoordOdd(x, y, z);
						else
							convertedPoint=UtilitiesBoard.cubeToCoordEven(x, y, z);
						
						int xToPlot=(int)(convertedPoint.getX()+goodguy.coordinates.getX());
						int yToPlot=(int) (convertedPoint.getY()+goodguy.coordinates.getY());
						
						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY())
								if(board[xToPlot][yToPlot].getQuickID()==lookingForID)
									idList.add(board[xToPlot][yToPlot].getID());
						
					}
				}
			}
		}
		
		
		for(int idIndex=0; idIndex<idList.size(); idIndex++) {
			for(int i=0; i<badguys.size(); i++) {
				if(badguys.get(i).getID()==idList.get(idIndex)) {
					targets.add(badguys.get(i));
					break;											//[Rem] Worried this will cause a bug.
				}
			}
		}
		return targets;
	}

	//[Need to Do] Remove players that can't be reached
	/*public static List<Player> playersInRange(List<Player> targets){
		
		return targets;
	}*/

	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	/*public static void checkRange(Hex board[][], String lookingForID, int range, List<Player> party, List<Player> targets, Enemy enemy, Point dimensions) {
		List<String> idList = new ArrayList<String>();
		
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(enemy.coordinates.getX()%2!=0)
							convertedPoint=UtilitiesBoard.cubeToCoordOdd(x, y, z);
						else
							convertedPoint=UtilitiesBoard.cubeToCoordEven(x, y, z);
						
						int xToPlot=(int)(convertedPoint.getX()+enemy.coordinates.getX());
						int yToPlot=(int) (convertedPoint.getY()+enemy.coordinates.getY());
						
						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY())
								if(board[xToPlot][yToPlot].getQuickID()==lookingForID)
									idList.add(board[xToPlot][yToPlot].getID());
						
					}
				}
			}
		}
		
		
		for(int idIndex=0; idIndex<idList.size(); idIndex++) {
			for(int i=0; i<party.size(); i++) {
				if(party.get(i).getID()==idList.get(idIndex)) {
					targets.add(party.get(i));
					break;											//[Rem] Worried this will cause a bug.
				}
			}
		}
	}*/
}
