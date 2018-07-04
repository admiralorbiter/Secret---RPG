package Gloomhaven;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
	//requirements - maybe a class for what a requirement is
	Goal goal;
	List<Player> players = new ArrayList<Player>();
	//List<Enemy> enemies = new ArrayList<Enemy>();
	List<ScenarioRoom> rooms = new ArrayList<ScenarioRoom>();
	int currentRoom;
	int sceneID;
	//temp
	//Mindthief player;
	
	public Scenario(int sceneID, int playerCount) {
		this.sceneID=sceneID;
		for(int i=0; i<playerCount; i++)
			players.add(new Player());
		
		if(sceneID==1) {
			goal=new Goal(1);
			
			//setup rooms
			int roomID=1;
			rooms.add(new ScenarioRoom(roomID));
			roomID=2;
			rooms.add(new ScenarioRoom(roomID));
			roomID=3;
			rooms.add(new ScenarioRoom(roomID));
			
			rooms.get(0).addPlayers(players);
			
		}
		
		currentRoom=0;

		
		
		//player = new Mindthief();
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Enemy> getEnemies(){
		//System.out.println("Test: "+room.get(0).getEnemies);
		return rooms.get(currentRoom).getEnemies();
		//List<Enemy> enemies=rooms.get(currentRoom).getEnemies();
		//return enemies;
	}
}
