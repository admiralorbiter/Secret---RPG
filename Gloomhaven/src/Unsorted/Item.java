package Unsorted;

import java.awt.Point;
import java.io.Serializable;

public class Item implements Serializable{
	String quickID="Item";
	int id;
	int gold;
	String name;
	String text="Needs to be created in the uploader";
	
	boolean consumed=false;
	boolean spent=false;
	boolean continuous=false;
	int useCount=0;
	int maxUses=0;
	boolean available=true;
	Point tracking;				//y is the total amount of items, x is where this card lies in that count
	String equipSlot;
	String playFlag;
	
	public Item(int id, int gold, String name, String equipSlot, String useFlag, String playFlag, int level) {
		this.id=id;
		this.gold=gold;
		this.name=name;
		this.equipSlot=equipSlot;
		this.playFlag=playFlag;
		
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
	public String getText() {return text;}
	public void setText(String text) {this.text=text;}
	public void refresh() {
		useCount=0;
	}
	
	public void use() {
		useCount++;
		if(maxUses==useCount) {
			available=false;
		}
	}
	
	public boolean getAvailable() {return available;}
	
	public void inventoryTracker(int maxItems) {
		tracking= new Point(0, maxItems);
	}
	
	public void setIndexNum(int i) {
		tracking = new Point(i, (int) tracking.getY());
	}
	
	public void testDisplay() {
		System.out.println(name+"  Gold: "+gold+"     "+tracking.getX()+"/"+tracking.getY());
	}
	
	public boolean getConsumed() {return consumed;}
	public boolean getSpent() {return spent;}
	public String getPlayFlag() {return playFlag;}
}
