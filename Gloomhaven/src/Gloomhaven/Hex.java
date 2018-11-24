package Gloomhaven;

import java.awt.Point;

public class Hex {
	
	String quickID="-";
	String id=" ";
	String lootID=" ";
	boolean empty=true;
	boolean loot=false;
	Point coordinate;
	boolean door=false;
	boolean open=true;
	int roomID=0;
	boolean hidden=true;
	boolean neverShown=false;
	
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
		}else if(quickID.equals("Obs")) {
			this.id=id;
			empty=false;
		}
		else {
			this.id=id;
			empty=false;
		}
	}
	
	public void setHex(String quickID, String id, int room) {
		this.quickID=quickID;
		this.roomID=room;
		this.id=id;
		open=false;
		door=true;
	}
	
	
	public String getLootID() {return lootID;}
	
	public void reset() {
		quickID="-";
		id=" ";
		loot=false;
		empty=true;
	}
	public boolean doorOpen() {return open;}
	public String getQuickID() {return quickID;}
	public String getID() {return id;}
	public boolean getSpaceFree() {return empty;}
	public boolean getLoot() {return loot;}
	public boolean getDoor() {return door;}
	public boolean getHidden() {return hidden;}
	public void toggleShowHex() {hidden=false;}
	public boolean showHex() {
		if(neverShown)
			return false;
		else if(hidden)
			return false;
		
		return true;
	}
	public void disableHex() {neverShown=true;}
	public int getRoomID() {return roomID;}
}
