package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends character{
	
	private boolean eliteFlag;
	private SimpleCards baseStats;
	
	public Enemy(int id, String classID) {
		
		setID("E"+id+classID);
		setClassID(classID);
		setName("Enemy");
		data = new CharacterDataObject(classID);
		
		if(classID.equals("TestElite")) {
			eliteFlag=true;
			baseStats=new SimpleCards(4, 3, 6);
		}
		else {
			eliteFlag=true;
			baseStats=new SimpleCards(3, 2, 5);
		}
	}
	
	public SimpleCards getBaseStats() {return baseStats;}

	public boolean canAttack() {

		if(effects.getDisarm()) {
			return false;
		}
		else if(effects.getStun()) {
			return false;
		}
		else if(effects.getImmobilize()) {
			return false;
		}
		
		return true;
	}
	public boolean canMove() {
		if(effects.getStun())
			return false;
		if(effects.getImmobilize())
			return false;
		
		return true;
	}

	public boolean isElite() {return eliteFlag;}

	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	//[Rem] Should evaluate whether it is faster to just create the full list using one function and no quick melee check
	public boolean checkMeleeRange(Hex board[][], String lookingForID, Point dimensions) {
		
		
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
	
	public void takeDamage(int damage) {
		if(effects.getPoison())
			damage=damage+1;
		
		damage=damage-data.getShield();
		if(damage>0)
			data.setHealth(data.getHealth()-damage);
		
		//[Test]
		System.out.println("Enemy "+id+" took "+damage+" and is now at "+data.getHealth());
	}
	
	//[Rem] This has to be a way to abstract this for both player and enemies
	public List<Player> createMeleeTargetList(Hex board[][],List<Player> party, Point dimensions){
		List<Player> targets = new ArrayList<Player>();
		checkRange(board, "P", 1, party, targets, dimensions);
		
		return targets;
	}
	
	public List<Player> playersInRangeEstimate(Hex board[][], List<Player> party, Point dimensions){
		List<Player> targets = new ArrayList<Player>();
		
		if(baseStats.range<=1)
			return targets;
		
		for(int r=2; r<=baseStats.range; r++) {
			checkRange(board, "P", r, party, targets, dimensions);
		}
		
		return targets;
	}

	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	public void checkRange(Hex board[][], String lookingForID, int range, List<Player> party, List<Player> targets, Point dimensions) {
		List<String> idList = new ArrayList<String>();
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(coordinates.getX()%2!=0)
							convertedPoint=cubeToCoordOdd(x, y, z);
						else
							convertedPoint=cubeToCoordEven(x, y, z);
						
						int xToPlot=(int)(convertedPoint.getX()+coordinates.getX());
						int yToPlot=(int) (convertedPoint.getY()+coordinates.getY());
						
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
	
	//[Need to Do] Remove players that can't be reached
	public List<Player> playersInRange(List<Player> targets){
		
		return targets;
	}
	
	//Should do ALL end of the round stuff
	public void endOfRound() {
		if(effects.getDisarm()) {
			effects.switchDisarm();
		}
		
		if(effects.getStun()) {
			effects.switchStun();
		}
		
		if(effects.getMuddle()) {
			effects.switchMuddle();
		}
		
		if(effects.getInvisibility()) {
			effects.switchInvisibility();
		}
		
		if(effects.getStrengthen()) {
			effects.switchStrengthen();
		}
		
		if(effects.getImmobilize()) {
			effects.switchImmobilize();
		}
	}
	
	public void setNegativeCondition(String condition) {
		if(condition=="Wound" && effects.getWound()==false)
			effects.switchWound();
		
		if(condition=="Curse" && effects.getCurse()==false)
			effects.switchCurse();
		
		if(condition=="Disarm" && effects.getDisarm()==false)
			effects.switchDisarm();
		
		if(condition=="Immobilize" && effects.getImmobilize()==false)
			effects.switchImmobilize();
		
		if(condition=="Muddle" && effects.getMuddle()==false)
			effects.switchMuddle();
		
		if(condition=="Poison" && effects.getPoison()==false)
			effects.switchPoison();
		
		if(condition=="Stun" && effects.getStun()==false)
			effects.switchStun();
			
	}
	
	public void push(Point playerCoordinate, int pushRange) {
		//If player is above it on the x axis, push down
		//if player same level, push right or left
		//if plyaer is below it on the x axis, push up
		if(playerCoordinate.getY()<coordinates.getY()) {
			//Push Down
			for(int i=0; i<pushRange; i++) {
				//Note need to know if the hex is free
			}
				
			
		}else if(playerCoordinate.getY()>coordinates.getY()) {
			//Push Up
			
		}else {
			if(playerCoordinate.getX()<coordinates.getX()) {
				//Push Right
			}else {
				//Push Left
				
			}
		}
	}
	
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	public List<Point> createTargetList(Hex board[][], int range, String quickID, Point dimensions) {
		List<Point> targets = new ArrayList<Point>();
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(coordinates.getX()%2!=0)
							convertedPoint=cubeToCoordOdd(x, y, z);
						else
							convertedPoint=cubeToCoordEven(x, y, z);

						int xToPlot=(int)(convertedPoint.getX()+coordinates.getX());
						int yToPlot=(int) (convertedPoint.getY()+coordinates.getY());

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
}
