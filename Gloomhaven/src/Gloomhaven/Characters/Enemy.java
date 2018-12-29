package Gloomhaven.Characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.CharacterDataObject;
import Gloomhaven.CardDataObject.SimpleCardData;

public class Enemy extends character{
	
	private boolean eliteFlag;
	private SimpleCardData baseStats;
	private Point startingPosition;
	
	public Enemy(int id, String classID, int room, boolean elite, Point startingPosition) {
		
		setID("E"+id+classID);
		setClassID(classID);
		setName("Enemy");
		
		data = new CharacterDataObject(classID);
		
		eliteFlag=elite;
		
		//Testing Need to actually udate stats.
		if(elite) {
			baseStats=new SimpleCardData(4, 3, 6);
		}else {
			baseStats=new SimpleCardData(3, 2, 5);
		}
		
		this.startingPosition=startingPosition;
	}
	
	public SimpleCardData getBaseStats() {return baseStats;}
	public boolean isElite() {return eliteFlag;}
	
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
	
	public Point getStartingPosition() {return startingPosition;}
}
