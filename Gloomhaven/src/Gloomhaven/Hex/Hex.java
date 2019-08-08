package Gloomhaven.Hex;

import javax.swing.ImageIcon;

import Unsorted.Door;

public class Hex extends HexCoordinate {

	private String quickID=" ";
	private String id=" ";
	private int roomID=0;
	private Door door = null;
	
	private String lootID="";
	private boolean loot=false;
	
	private boolean doorOpen=false;
	
	boolean empty=true;
	boolean obstacle=false;
	
	boolean hidden=false;
	
	private ImageIcon image = null;//new ImageIcon("src/Gloomhaven/img/Blank.png");
	
	public Hex(int x, int y, boolean flatlayout) {
		super(x, y, flatlayout);
	}
	
	public Hex(int q, int r, int s) {
		super(q, r, s);
	}
	
	public Hex(int q, int r, int s, boolean flatlayout) {
		super(q, r, s);
	}
	
	 public String getQuickID() {return quickID;}
	 public void setQuickID(String id) {this.quickID=id;}
	 public String getID() {return id;}
	 public void setID(String id) {this.id=id;}
	 public String getLootID() {return lootID;}
	 public boolean hasLoot() {return loot;}
	 public void openDoor() {doorOpen=true;}
	 public boolean isDoorOpen() {return doorOpen;}
	 public int getRoomID() {return roomID;}
	 public boolean isSpaceEmpty() {
		 if(door!=null)
			 if(door.isDoorLocked())
				 return false;
		 
		 return empty;
	}
	 public void setRoomID(int id) {this.roomID=id;}
	 public void setSpaceEmpty(boolean empty) {this.empty=empty;}
	 public boolean isHidden() {return hidden;}
	 public void setHidden(boolean hidden) {this.hidden=hidden;}
	 
	 public boolean hasDoor() {
		 if(door!=null)
			 return true;
		 return false;
	}
	 
	 public Door getDoor() {return door;}
	 public void setDoor(Door door) {this.door=door;}
	 
	 public boolean hasObstacle() {return obstacle;}
	 
	 public ImageIcon getImage() {return image;}
	 public void setImage(ImageIcon image) {this.image=image;}
	 
	 public void setObstacle(boolean obstacle) {
		 this.obstacle=obstacle;
		 quickID="O";
	}

	 public void setLootID(String lootID) {
		 this.lootID=lootID;
		 quickID="L";
		 loot=true;
	 }
	 
	 public void removeLoot() {
		 lootID="";
		 loot=false;
	 }
	 
}
