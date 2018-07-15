package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
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
	
	int attackedPlayer;
	
	Boolean wait;
	
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
	
	public void enemyAttackProcedure() {
		//need to order the list of enemies -ene order list done at start of scenario
		//need to for each check what players are in distance
		//if no one is next, move and attack
		//if none are in distance, move to the next
		
		/*this shit is in here because i literally can't figure out how
		 * to have it wait for a command from the user to decide then move on
		 * so i have this janky ass system. if you are reading this, it is probably
		 * to late to help. If wait is commented out with no comments, god help us all.
		 */
		//wait=false;
		
		//first do melee check because then move/range doesn't matter
		for(int i=0; i<enemies.size(); i++) {
			int meleeCode=inMelee(enemies.get(i));

			//do stuff if in melee with meleeCode
			if(list.get(card).getRange()==0)
				attackPlayer(meleeCode);
			else {
				/*
				 * Need to check if there is a direct line, not just if something is range. 
				 * First check if something is in range. Then check if there is los
				 */
				int rangeCode=inRange(enemies.get(i));
				if(rangeCode!=-1) {
					attackPlayer(rangeCode);
				}
			}

		}
		
		for(int i=0; i<enemies.size(); i++)
			System.out.println(enemies.get(i).getName()+": "+enemies.get(i).getX()+","+enemies.get(i).getY());
	}
	
	public void playerDefendProcedure(Graphics g) {
		int attack=list.get(card).getAttack();
		
		g.drawString("Defend against "+card+" with "+attack+" of damage by discarding?", 50, 500);
	}
	
	private void attackPlayer(int playerIndex) {
		attackedPlayer=playerIndex;
		
	}
	
	public int getTargetedPlayer() {return attackedPlayer;}
	
	public void decreaseTargetPlayerHealth() {
		int attack=list.get(card).getAttack();
		players.get(attackedPlayer).takeDamage(attack);
	}
	
	public void discardTargetPlayer() {
		players.get(attackedPlayer).randomDiscard();
	}
	
	private	int inRange(Enemy enemy) {
		Point enemyPos = enemy.getPosition();
		String board[][] = rooms.get(currentRoom).getBoard();
		Point dim = rooms.get(currentRoom).getDim();
		int range = list.get(card).getRange();
		for(int i=0; i<players.size(); i++) {
			Point playerPos = players.get(i).getPosition();
			int x;
			int y;
			for(int r=0; r<range; r++) {
				x=enemyPos.x;
				y=enemyPos.y;
				
				if((enemyPos.y-r)>=0) {
					y=y-r;
					if(board[x][y]=="Player") {
						return i;
					}
				}
				if((enemyPos.y+r)<=dim.getY()) {
					y=y+r;
					if(board[x][y]=="Player") {
						return i;
					}
				}
				
				if((enemyPos.x-r)>=0) {
					x=x-r;
					if(board[x][y]=="Player") {
						return i;
					}
					if((enemyPos.y-r)>=0) {
						y=y-r;
						if(board[x][y]=="Player") {
							return i;
						}
					}
					if((enemyPos.y+r)<=dim.getY()) {
						y=y+r;
						if(board[x][y]=="Player") {
							return i;
						}
					}
				}
				
				if((enemyPos.x+r)<=dim.getY()) {
					x=x+r;
					if(board[x][y]=="Player") {
						return i;
					}
					if((enemyPos.y-r)>=0) {
						y=y-r;
						if(board[x][y]=="Player") {
							return i;
						}
					}
					if((enemyPos.y+r)<=dim.getY()) {
						y=y+r;
						if(board[x][y]=="Player") {
							return i;
						}
					}
				}
			}
			
		}
		
		
		return -1;
	}
	
	private int inMelee(Enemy enemy) {
		Point enemyPos = enemy.getPosition();
		for(int i=0; i<players.size(); i++) {
			Point playerPos = players.get(i).getPosition();
			if((enemyPos.x!=0)&&(playerPos.x==(enemyPos.x-1))) {
				if(playerPos.y==(enemyPos.y-1)) {
					return i;
				}
				else if(playerPos.y==(enemyPos.y)) {
					return i;
				}
				else if(playerPos.y==(enemyPos.y+1)) {
					return i;
				}
			}
			else if((enemyPos.x!=0)&&(playerPos.x==(enemyPos.x+1))) {
				if(playerPos.y==(enemyPos.y-1)) {
					return i;
				}
				else if(playerPos.y==(enemyPos.y)) {
					return i;
				}
				else if(playerPos.y==(enemyPos.y+1)) {
					return i;
				}
			}
			else if((enemyPos.x!=0)&&(playerPos.x==enemyPos.x)) {
				if(playerPos.y==(enemyPos.y-1)) {
					return i;
				}
				else if(playerPos.y==(enemyPos.y+1)) {
					return i;
				}
			}
		}
		
		return -1;
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
