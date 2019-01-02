package Gloomhaven;

public class Hex extends HexCoordinate {

	private String quickID=" ";
	
	private int roomID=-1;
	
	private String lootID="";
	private boolean loot=false;
	
	private boolean door=false;
	private boolean doorOpen=false;
	
	boolean empty=true;
	
	public Hex(int x, int y, boolean flatlayout) {
		super(x, y, flatlayout);
	}
	
	public Hex(int q, int r, int s) {
		super(q, r, s);
	}
	
	 public String getQuickID() {return quickID;}
	 public String getLootID() {return lootID;}
	 public boolean hasLoot() {return loot;}
	 public boolean hasDoor() {return door;}
	 public boolean isDoorOpen() {return doorOpen;}
	 public int getRoomID() {return roomID;}
	 public boolean isSpaceEmpty() {return empty;}
	 public void setRoomID(int id) {this.roomID=id;}
	 public void setSpaceEmpty(boolean empty) {this.empty=empty;}
	 
	 public void setLootID(String lootID) {
		 this.lootID=lootID;
		 loot=true;
	 }
	 
	 public void removeLoot() {
		 lootID="";
		 loot=false;
	 }
	 
}
