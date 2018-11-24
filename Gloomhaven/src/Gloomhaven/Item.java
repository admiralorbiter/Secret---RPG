package Gloomhaven;

import java.awt.Point;

public class Item {
	String quickID="Item";
	int id;
	int gold;
	String name;
	
	boolean consumed=false;
	boolean spent=false;
	boolean continuous=false;
	int useCount=0;
	int maxUses=0;
	Point tracking;				//y is the total amount of items, x is where this card lies in that count
	String equipSlot;
	
	public Item(int id, int gold, String name, String equipSlot, String useFlag) {
		this.id=id;
		this.gold=gold;
		this.name=name;
		this.equipSlot=equipSlot;
		
		if(useFlag.equals("spent")) {
			spent=true;
			maxUses=ItemLoader.maxUses(id);
		}else if(useFlag.equals("continuous")) {
			continuous=true;
		}else if(useFlag.equals("consumed")) {
			consumed=true;
		}
	}
	
	public String getQuickID() {return quickID;}
	public int getID() {return id;}
	public int getGold() {return gold;}
	public String getName() {return name;}
	
	public void refresh() {
		spent=false;
		useCount=0;
	}
	
	public void inventoryTracker(int maxItems) {
		tracking= new Point(0, maxItems);
	}
	
	public void setIndexNum(int i) {
		tracking = new Point(i, (int) tracking.getY());
	}
	
	public void testDisplay() {
		System.out.println(name+"  Gold: "+gold+"     "+tracking.getX()+"/"+tracking.getY());
	}
}
