package Gloomhaven;

import java.awt.Point;

public class Hex {
	
	String quickID="-";
	String id=" ";
	
	boolean empty=true;
	boolean loot=false;
	Point coordinate;
	
	public Hex(int x, int y) {
		coordinate = new Point(x,y);
	}
	
	public void setHex(String quickID, String id) {
		this.quickID=quickID;
		this.id=id;
		if(quickID=="Loot")
			loot=true;
		empty=false;
	}
	
	public void reset() {
		quickID="-";
		id=" ";
		loot=false;
		empty=true;
	}
	
	public String getQuickID() {return quickID;}
	public String getID() {return id;}
	public boolean getSpaceFree() {return empty;}
	public boolean getLoot() {return loot;}
}
