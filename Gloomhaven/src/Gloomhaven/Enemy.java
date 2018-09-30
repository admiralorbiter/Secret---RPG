package Gloomhaven;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Enemy {
	
	boolean eliteFlag;
	String classID;
	String id;
	Point2D coordinates;
	StatusEffectDataObject effects = new StatusEffectDataObject();
	Point2D dimensions;
	
	public Enemy(String classID, int id) {
		this.classID=classID;
		this.id=id+classID;
		
		switch(classID) {
			case "Test": 
				eliteFlag=false;
				break;
			case "TestElite":
				eliteFlag=true;
				break;
			default:
				eliteFlag=true;
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
	public boolean checkMeleeRange(String board[][], String lookingForID) {
		
		int x=(int) coordinates.getX();
		int y=(int) coordinates.getY();
	
		if(x>0) {
			if(board[x-1][y]==lookingForID)
				return true;
			if(y>0) {
				if(board[x-1][y-1]==lookingForID) {
					return true;
				}
			}
			if(y<dimensions.getY()) {
				if(board[x-1][y+1]==lookingForID) {
					return true;
				}
			}
		}
		
		if(x<dimensions.getX()) {
			if(board[x+1][y]==lookingForID)
				return true;
			if(y>0) {
				if(board[x+1][y-1]==lookingForID) {
					return true;
				}
			}
			if(y<dimensions.getY()) {
				if(board[x+1][y+1]==lookingForID) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public List<Player> createMeleeTargetList(){
		List<Player> targets = new ArrayList<Player>();
		
		return targets;
	}
	
	public List<Player> playersInRangeEstimate(){
		List<Player> targets = new ArrayList<Player>();
		
		return targets;
	}
	
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
}
