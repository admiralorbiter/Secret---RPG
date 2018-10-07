package Gloomhaven;

import java.awt.Point;

public class Hex {
	
	String quickID="-";
	String id=" ";
	
	boolean empty=true;

	Point coordinate;
	
	public Hex(int x, int y) {
		coordinate = new Point(x,y);
	}
	
	public void setHex(String quickID, String id) {
		this.quickID=quickID;
		this.id=id;
		empty=false;
	}
	
	public void reset() {
		quickID="-";
		id=" ";
		empty=true;
	}
	
	public String getQuickID() {return quickID;}
	public String getID() {return id;}
	public boolean getSpaceFree() {return empty;}
}
