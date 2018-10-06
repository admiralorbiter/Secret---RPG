package Gloomhaven;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {
	
	boolean eliteFlag;
	String classID;
	String id;
	Point2D coordinates;
	StatusEffectDataObject effects = new StatusEffectDataObject();
	Point2D dimensions;

	int move;
	int shield;
	int range;
	int attack;
	int health;
	
	int TEST_HEALTH=6;
	int TEST_ATTACK=3;
	int TEST_RANGE=3;
	int TEST_MOVE=2;
	int TEST_SHIELD=0;
	
	public Enemy(String classID, int id, int scenarioLevel) {
		this.classID=classID;
		this.id=id+classID;
	
		switch(classID) {
			case "Test": 
				eliteFlag=false;
				range=TEST_RANGE;
				attack=TEST_ATTACK;
				health=TEST_HEALTH;
				move=TEST_MOVE;
				shield=TEST_SHIELD;
				break;
			case "TestElite":
				eliteFlag=true;
				range=TEST_RANGE+scenarioLevel;
				attack=TEST_ATTACK+scenarioLevel;
				health=TEST_HEALTH+scenarioLevel;
				move=TEST_MOVE+scenarioLevel;
				shield=TEST_SHIELD+scenarioLevel;
				break;
			default:
				eliteFlag=true;
				range=TEST_RANGE;
				attack=TEST_ATTACK;
				health=TEST_HEALTH;
				move=TEST_MOVE;
				shield=TEST_SHIELD;
		}
	}	
	
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
	
	public boolean isElite() {return eliteFlag;}
	public String getClassID() {return classID;}
	
	public void setPoint(Point2D point) {
		this.coordinates=point;
	}
	
	public Point2D getCoordinate() {return coordinates;}
	
	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	//[Rem] Should evaluate whether it is faster to just create the full list using one function and no quick melee check
	public boolean checkMeleeRange(String board[][], String lookingForID) {
		
		
		int x=(int) coordinates.getX();
		int y=(int) coordinates.getY();
	
		if(x-1>0) {
			if(board[x-1][y]==lookingForID)
				return true;
			if(y-1>0) {
				if(board[x-1][y-1]==lookingForID) {
					return true;
				}
			}
			if(y+1<dimensions.getY()) {
				if(board[x-1][y+1]==lookingForID) {
					return true;
				}
			}
		}
		
		if(x+1<dimensions.getX()) {
			if(board[x+1][y]==lookingForID)
				return true;
			if(y-1>0) {
				if(board[x+1][y-1]==lookingForID) {
					return true;
				}
			}
			if(y+1<dimensions.getY()) {
				if(board[x+1][y+1]==lookingForID) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void takeDamage(int damage) {
		damage=damage-shield;
		if(damage>0)
			health=health-damage;
		
		//[Test]
		System.out.println("Enemy "+id+" took "+damage+" and is now at "+health);
	}
	
	//[Rem] This has to be a way to abstract this for both player and enemies
	public List<Player> createMeleeTargetList(String qBoard[][], String idBoard[][], List<Player> party){
		List<Player> targets = new ArrayList<Player>();
		checkRange(qBoard, idBoard, "P", 1, party, targets);
		
		return targets;
	}
	
	public List<Player> playersInRangeEstimate(String qBoard[][], String idBoard[][], List<Player> party){
		List<Player> targets = new ArrayList<Player>();
		
		if(range<=1)
			return targets;
		
		for(int r=2; r<=range; r++) {
			checkRange(qBoard, idBoard, "P", r, party, targets);
		}
		
		return targets;
	}

	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	public void checkRange(String qBoard[][], String idBoard[][], String lookingForID, int range, List<Player> party, List<Player> targets) {
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
								if(qBoard[xToPlot][yToPlot]==lookingForID)
									idList.add(idBoard[xToPlot][yToPlot]);
						
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
	
	public void setDimensions(Point2D dimensions) {
		this.dimensions=dimensions;
	}
	
	public Point2D getDimensions() {
		return dimensions;
	}
	

	public int getAttack() {return attack;}
	public String getID() {return id;}
	public void decreaseHealth(int damage) {
		health=health-damage;
	}
}
