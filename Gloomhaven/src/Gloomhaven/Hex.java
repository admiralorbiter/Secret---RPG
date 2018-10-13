package Gloomhaven;

import java.awt.Point;

public class Hex {
	
	String quickID="-";
	String id=" ";
	String lootID=" ";
	boolean empty=true;
	boolean loot=false;
	Point coordinate;
	
	public Hex(int x, int y) {
		coordinate = new Point(x,y);
	}
	
	public void setHex(String quickID, String id) {
		this.quickID=quickID;
		
		if(quickID.equals("Loot")) {
			if(id.equals("Gold")) {
				this.lootID="Gold";
				this.id="lootGold";
				loot=true;
			}
		}else {
			this.id=id;
			empty=false;
		}
	}
	
	public String getLootID() {return lootID;}
	
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
