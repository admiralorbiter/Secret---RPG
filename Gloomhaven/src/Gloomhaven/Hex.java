package Gloomhaven;

public class Hex extends HexCoordinate {

	private String quickID="";
	
	private String lootID="";
	private boolean loot=false;
	
	private boolean door=false;
	
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
	 public void setLootID(String lootID) {
		 this.lootID=lootID;
		 loot=true;
	 }
	 
	 public void removeLoot() {
		 lootID="";
		 loot=false;
	 }
}
