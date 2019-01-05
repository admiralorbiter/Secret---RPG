package Gloomhaven;

public class Hex extends HexCoordinate {

	private String quickID=" ";
	private String id=" ";
	private int roomID=0;
	
	private String lootID="";
	private boolean loot=false;
	
	private boolean door=false;
	private boolean doorOpen=false;
	
	boolean empty=true;
	
	boolean hidden=false;
	
	public Hex(int x, int y, boolean flatlayout) {
		super(x, y, flatlayout);
	}
	
	public Hex(int q, int r, int s) {
		super(q, r, s);
	}
	
	 public String getQuickID() {return quickID;}
	 public void setQuickID(String id) {this.quickID=id;}
	 public String getID() {return id;}
	 public void setID(String id) {this.id=id;}
	 public String getLootID() {return lootID;}
	 public boolean hasLoot() {return loot;}
	 public boolean hasDoor() {return door;}
	 public boolean isDoorOpen() {return doorOpen;}
	 public int getRoomID() {return roomID;}
	 public boolean isSpaceEmpty() {return empty;}
	 public void setRoomID(int id) {this.roomID=id;}
	 public void setSpaceEmpty(boolean empty) {this.empty=empty;}
	 public boolean isHidden() {return hidden;}
	 public void setHidden(boolean hidden) {this.hidden=hidden;}
	 public void setDoor(boolean door) {this.door=door;}
	 public void setDoorOpen(boolean doorOpen) {this.doorOpen=doorOpen;}
	 
	 public void setLootID(String lootID) {
		 this.lootID=lootID;
		 loot=true;
	 }
	 
	 public void removeLoot() {
		 lootID="";
		 loot=false;
	 }
	 
}
