package Gloomhaven;

public class Enemy {
	
	boolean eliteFlag;
	String classID;
	
	public Enemy(String classID) {
		this.classID=classID;
		
		switch(classID) {
			case "Test": 
				eliteFlag=false;
				break;
			case "TestElite":
				eliteFlag=true;
				break;
			default:
				eliteFlag=true;
		}
	}	
	
	public boolean isElite() {return eliteFlag;}
	public String getClassID() {return classID;}
}
