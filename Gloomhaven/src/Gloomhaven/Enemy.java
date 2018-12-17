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
	public boolean isElite() {return eliteFlag;}

	
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
}
