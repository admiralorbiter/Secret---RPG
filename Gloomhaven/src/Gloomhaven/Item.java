package Gloomhaven;

public class Item {
	String quickID="Item";
	int id;
	int gold;
	String name;
	
	boolean consumed=false;
	boolean spent=false;
	int useCount=0;
	int maxUses=0;
	
	public Item(int id, int gold, String name) {
		this.id=id;
		this.gold=gold;
		this.name=name;
	}
	
	public String getQuickID() {return quickID;}
	public int getID() {return id;}
	public int getGold() {return gold;}
	public String getName() {return name;}
	
	public void refresh() {
		spent=false;
		useCount=0;
	}
}
