package Gloomhaven.Characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Gloomhaven.CardDataObject.SimpleCardData;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.UtilitiesHex;
import Unsorted.CharacterDataObject;
import Unsorted.Setting;

public class Enemy extends Character{
	
	private boolean eliteFlag;
	private SimpleCardData baseStats;
	private Point startingPosition;
	private int attack=0;
	
	public Enemy(int id, String classID, int room, boolean elite, Point startingPosition) {
		
		setID("E"+id+classID);
		setClassID(classID);
		setName("Enemy");

		data = new CharacterDataObject(classID, elite);
		
		eliteFlag=elite;
		
		//Testing Need to actually udate stats.
		if(elite) {
			setImage(new ImageIcon("src/Gloomhaven/img/EnemyIconElite.png"));
			baseStats=new SimpleCardData(4, 3, 6);
		}else {
			setImage(new ImageIcon("src/Gloomhaven/img/EnemyIcon.png"));
			baseStats=new SimpleCardData(3, 2, 5);
		}
		
		this.startingPosition=startingPosition;
		setCoordinates(startingPosition);
	}
	
	public SimpleCardData getBaseStats() {return baseStats;}
	public boolean isElite() {return eliteFlag;}
	public ImageIcon getImageIcon() {return image;}
	
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
	public int getAttack() {return attack;}
	public void setAttack(int attack) {this.attack=attack;}
	
	public void move(HexCoordinate hex, Point dimensions) {
		Point coordinate = UtilitiesHex.getOffset(hex.getLayout(), hex);
		
		if(coordinate.x>=0 && coordinate.x<dimensions.x) {
			if(coordinate.y>=0 && coordinate.y<dimensions.y) {
				setCoordinates(coordinate);
			}
		}
	}
	
	public void move(Point coordinate, Point dimensions) {
		if(coordinate.x>=0 && coordinate.x<dimensions.x) {
			if(coordinate.y>=0 && coordinate.y<dimensions.y) {
				setCoordinates(coordinate);
			}
		}
	}
}
