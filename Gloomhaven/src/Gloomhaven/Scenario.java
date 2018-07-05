package Gloomhaven;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scenario {
	//requirements - maybe a class for what a requirement is
	Goal goal;
	List<Player> players = new ArrayList<Player>();
	//List<Enemy> enemies = new ArrayList<Enemy>();
	List<ScenarioRoom> rooms = new ArrayList<ScenarioRoom>();
	int currentRoom;
	int sceneID;
	List<EnemyAbilityCards> list = new ArrayList<EnemyAbilityCards>();
	int cardCount;
	int card;
	
	//temp
	//Mindthief player;
	
	public Scenario(int sceneID, List<Player> players) {
		this.sceneID=sceneID;
		this.players=players;		
		
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

		//depends on class/name
		cardCount=8;
		for(int i=0; i<cardCount; i++) {
			//the game generalizes enemies
			list.add(new EnemyAbilityCards(1, i+1, "Guard"));
		}
		
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
	
	public void pickCard() {
		Random rand = new Random();
		boolean running=true;
		do
		{
		 int pick = rand.nextInt(list.size());
		 if(list.get(pick).cardFree()) {
			 card=pick;
			 running=false;
		 }
		}
		while(running);
	}
	
	public int getEnemyInitiative() {
		return list.get(card).getInitiative();
	}
}
