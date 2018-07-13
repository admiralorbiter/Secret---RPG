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
	List<Enemy> enemies = new ArrayList<Enemy>();
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
			rooms.add(new ScenarioRoom(roomID, enemies));
			roomID=2;
			rooms.add(new ScenarioRoom(roomID, enemies));
			roomID=3;
			rooms.add(new ScenarioRoom(roomID, enemies));
			
			rooms.get(0).addPlayers(players);
			
		}
		
		currentRoom=0;

		//depends on class/name
		cardCount=8;
		for(int i=0; i<cardCount; i++) {
			//the game generalizes enemies
			list.add(new EnemyAbilityCards(1, i+1, "Guard"));
		}
		
		orderEnemies();
		//player = new Mindthief();
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Enemy> getEnemies(){
		//System.out.println("Test: "+room.get(0).getEnemies);
		//return rooms.get(currentRoom).getEnemies();
		return enemies;
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
	
	public void enemyAttack() {
		//need to order the list of enemies - order list done at start of scenario
		//need to for each check what players are in distance
		//if no one is next, move and attack
		//if none are in distance, move to the next
		
		for(int i=0; i<enemies.size(); i++) {
			if(inMelee())
		}
		
		for(int i=0; i<enemies.size(); i++)
			System.out.println(enemies.get(i).getName()+": "+enemies.get(i).getX()+","+enemies.get(i).getY());
	}
	
	private void inMelee(Enemy enemy) {
		for(int i=0; i<players.size(); i++) {
			
		}
	}
	
	private void orderEnemies() {
		
		List<Enemy> temp = new ArrayList<Enemy>();
		for(int i=0; i<enemies.size(); i++) {
			if(enemies.get(i).isElite()) {
				temp.add(enemies.get(i));
			}
		}
		
		for(int i=0; i<enemies.size(); i++) {
			if(!(enemies.get(i).isElite())) {
				temp.add(enemies.get(i));
			}
		}
	
		enemies=temp;		
	}
}
