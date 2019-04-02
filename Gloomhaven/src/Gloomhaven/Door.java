package Gloomhaven;

public class Door {
	
	boolean open=false;
	boolean locked=false;
	boolean trigger=false;
	
	public Door() {

	}
	
	public Door(boolean locked) {
		this.locked=locked;
	}
	
	//Getters and Setters
	public boolean doorOpen() {return open;}
	public void setDoorOpen(boolean open) {this.open=open;}
	public boolean isDoorLocked() {return locked;}
	public void setDoorLocked(boolean locked) {this.locked=locked;}
}
