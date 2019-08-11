package Gloomhaven.Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;
import Gloomhaven.Hex.Draw;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.HexLayout;
import Gloomhaven.Hex.UtilitiesHex;
import Gloomhaven.Scenario.ScenarioData;
import Gloomhaven.Scenario.ScenarioEnemyLoader;
import Unsorted.EnemyAbilityDeck;
import Unsorted.FontSettings;
import Unsorted.GUI;
import Unsorted.GUISettings;
import Unsorted.Setting;
import Unsorted.UtilitiesAB;

public class EnemyInfo implements Serializable{
	
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private int turnNumber;
	private List<EnemyAbilityDeck> enemyDecks = new ArrayList<EnemyAbilityDeck>();
	private String[][] initList;
	private int enemyDeckIndex;
	private boolean updateEnemyFlag=false;
	private int totalEnemyCount;
	
	public EnemyInfo(ScenarioData data) {
		enemies=ScenarioEnemyLoader.getEnemies(data.getId(), 0);
		totalEnemyCount=data.getTotalEnemies();
		
		List<String> enemyClassTypes = getEnemyTypeList();
		for(int i=0; i<enemyClassTypes.size(); i++)
			enemyDecks.add(new EnemyAbilityDeck(enemyClassTypes.get(i)));
	}
	
	public void drawEnemies(Graphics2D g, boolean flatlayout) {

		for(int i=0; i<enemies.size(); i++) {
			g.setColor(Setting.enemyColor);
			Draw.drawHex(g, enemies.get(i).getCoordinates(), enemies.get(i), flatlayout, null);
			
			/*
			HexCoordinate hex;
			HexLayout hl;
			
			if(flatlayout) {
				hex = UtilitiesHex.flatOffsetToCube(1, enemies.get(i).getCoordinates());
				hl = new HexLayout(UtilitiesHex.getFlatLayoutOrientation(), new Point(Setting.size, Setting.size), Setting.center);
			}
			else {
				hex = UtilitiesHex.pointyOffsetToCube(1, enemies.get(i).getCoordinates());
				hl = new HexLayout(UtilitiesHex.getPointyLayoutOrientation(), new Point(Setting.size, Setting.size), Setting.center);
			}
			Point2D p = UtilitiesHex.hexToPixel(hl, enemies.get(i).getCubeCoordiantes(flatlayout));
			if(enemies.get(i).getCharacterData().getHealth()<=2)
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);
			
			g.drawString(""+enemies.get(i).getCharacterData().getHealth(), (int)p.getX(), (int)p.getY()-20);
		}
		*/
			
		GUI.drawCharacterInfo(g, flatlayout, enemies.get(i));
		}
		
		g.setColor(Setting.enemyColor);
		
		
	}
	
	public void initiationRound() {
		initList = new String[enemyDecks.size()][2];
		
		for(int i=0; i<enemyDecks.size(); i++) {
			enemyDecks.get(i).pickRandomAbilityCard();
			initList[i][0]=enemyDecks.get(i).getDeckID();
			initList[i][1]=Integer.toString(enemyDecks.get(i).getInitiative());
		}

		if(enemyDecks.size()>1)
			sort(initList, enemyDecks.size());
	}
	
	public void sort(String[][] initList, int size) {
		//displaySort(initList, size);
		for(int i=0; i<size-1; i++) {
			int min = i;
			for(int j=i+1; j<size; j++) {
				if(Integer.valueOf(initList[j][1])<Integer.valueOf(initList[min][1])) {
					min=j;
				}
			}
			String tempDeckID = initList[i][0];
			String tempInit=initList[i][1];
			
			initList[i][0]=initList[min][0];
			initList[i][1]=initList[min][1];
			
			initList[min][0]=tempDeckID;
			initList[min][1]=tempInit;
		}
		//displaySort(initList, size);
	}
	
	private void displaySort(String[][] initList, int size) {
		System.out.println("Sort List:");
		for(int i=0; i<size; i++) {
			System.out.print(initList[i][0]+" "+initList[i][1]+" , ");
		}
		System.out.println("");
	}
	public void test() {
		enemyDecks.sort(Comparator.comparingInt(EnemyAbilityDeck::getInitiative));
	}
	
	//public void setTurnNumber(int turnNumber) {this.turnNumber=turnNumber;}
	//public int getTurnNumber() {return turnNumber;}
	public Enemy getEnemy(int index) {
		if(enemies.size()>0)
			return enemies.get(index);
		else
			return null;
	}
	public int getCount() {
		return totalEnemyCount;
	}
	public List<EnemyAbilityDeck> getEnemyAbilityDeck(){return enemyDecks;}
	public List<String> getEnemyTypeList(){
		List<String> enemyTypeList = new ArrayList<String>();
		
		for(int i=0; i<enemies.size(); i++)
			if(!enemyTypeList.contains(enemies.get(i).getClassID()))
				enemyTypeList.add(enemies.get(i).getClassID());
		
		return enemyTypeList;
	}
	
	public void enemyMoveProcedure(Hex[][] board, int index, List<Player> party, Graphics g) {
		
		//TODO fix this hack of a system. doesn't move diag or do line of site
		//checks which player is closest and then moves towards that player
		int closestIndex=0;
		int distance=UtilitiesAB.distance(enemies.get(index).getCoordinates(), party.get(0).getCoordinates());
		for(int j=1; j<party.size(); j++) {
			if(UtilitiesAB.distance(enemies.get(index).getCoordinates(), party.get(j).getCoordinates())<distance)
				closestIndex=j;
		}
		
		//Check if they are further x or y
		//if the difference is x is greater, move x, else move y
		
		boolean moved=false;
		
		if(Math.abs(enemies.get(index).getCoordinates().x-party.get(closestIndex).getCoordinates().x)>Math.abs(enemies.get(index).getCoordinates().y-party.get(closestIndex).getCoordinates().y)) {
			//move left
			if(enemies.get(index).getCoordinates().x-party.get(closestIndex).getCoordinates().x>0) {
				if(board[enemies.get(index).getCoordinates().x-1][enemies.get(index).getCoordinates().y]!=null && board[enemies.get(index).getCoordinates().x-1][enemies.get(index).getCoordinates().y].isSpaceEmpty()) {
					enemies.get(index).setCoordinates(new Point(enemies.get(index).getCoordinates().x-1, enemies.get(index).getCoordinates().y));
					board[enemies.get(index).getCoordinates().x-1][enemies.get(index).getCoordinates().y].setSpaceEmpty();
					moved=true;
				}
			}
			//move right
			if(moved==false) {
				if(enemies.get(index).getCoordinates().x-party.get(closestIndex).getCoordinates().x<0) {
					if(board[enemies.get(index).getCoordinates().x+1][enemies.get(index).getCoordinates().y]!=null && board[enemies.get(index).getCoordinates().x+1][enemies.get(index).getCoordinates().y].isSpaceEmpty()) {
						enemies.get(index).setCoordinates(new Point(enemies.get(index).getCoordinates().x+1, enemies.get(index).getCoordinates().y));
						board[enemies.get(index).getCoordinates().x+1][enemies.get(index).getCoordinates().y].setSpaceEmpty();
						moved=true;
					}
				}
			}
		}
		//up/down
		if(moved==false) {
			if(Math.abs(enemies.get(index).getCoordinates().x-party.get(closestIndex).getCoordinates().x)<Math.abs(enemies.get(index).getCoordinates().y-party.get(closestIndex).getCoordinates().y)) {
				//move up
				if(enemies.get(index).getCoordinates().y-party.get(closestIndex).getCoordinates().y>0) {
					if(board[enemies.get(index).getCoordinates().x][enemies.get(index).getCoordinates().y-1]!=null && board[enemies.get(index).getCoordinates().x][enemies.get(index).getCoordinates().y-1].isSpaceEmpty()) {
						enemies.get(index).setCoordinates(new Point(enemies.get(index).getCoordinates().x, enemies.get(index).getCoordinates().y-1));
						board[enemies.get(index).getCoordinates().x][enemies.get(index).getCoordinates().y-1].setSpaceEmpty();
						moved=true;
					}
				}
			}
			
			//move right
			if(moved==false) {
				if(enemies.get(index).getCoordinates().y-party.get(closestIndex).getCoordinates().y<0) {
					if(board[enemies.get(index).getCoordinates().x][enemies.get(index).getCoordinates().y+1]!=null && board[enemies.get(index).getCoordinates().x][enemies.get(index).getCoordinates().y+1].isSpaceEmpty()) {
						enemies.get(index).setCoordinates(new Point(enemies.get(index).getCoordinates().x, enemies.get(index).getCoordinates().y+1));
						board[enemies.get(index).getCoordinates().x][enemies.get(index).getCoordinates().y+1].setSpaceEmpty();
						moved=true;
					}
				}
			}
		}
	}
	
	public List<Enemy> getTurnEnemies(){
		List<Enemy> enemiesForTurn = new ArrayList<Enemy>();
		
		for(int i=0; i<enemies.size(); i++) {
			if(enemyDecks.get(enemyDeckIndex).getDeckID()==enemies.get(i).getClassID())
				enemiesForTurn.add(enemies.get(i));
		}
		
		return enemiesForTurn;
	}
	
	public Enemy getEnemy(Point p) {
		for(int i=0; i<enemies.size(); i++)
		{
			
			if(enemies.get(i).getCoordinates().equals(p))
				return enemies.get(i);
		}
		return null;
	}
	
	public List<Enemy> getEnemies(){return enemies;}
	
	public void update(Hex[][] board, ScenarioData data) {
		
		for(int i=0; i<enemies.size(); i++) {
			if(enemies.get(i).getCharacterData().getHealth()<=0) {
				data.increaseEnemiesKilled();
				board[enemies.get(i).getCoordinates().x][enemies.get(i).getCoordinates().y].setQuickID(" ");
				board[enemies.get(i).getCoordinates().x][enemies.get(i).getCoordinates().y].setID(" ");
				enemies.remove(i);
				totalEnemyCount=totalEnemyCount-1;
			}
		}
	}
	
	public void setUpdateEnemyFlag(boolean flag) {this.updateEnemyFlag=flag;}
	public boolean getUpdateEnemyFlag() {return updateEnemyFlag;}
	public void updateEnemyList(int id, int room) {
		setUpdateEnemyFlag(false);
		List<Enemy> enemiesInRoom = ScenarioEnemyLoader.getEnemies(id, room);
		
		for(int i=0; i<enemiesInRoom.size(); i++)
			enemies.add(enemiesInRoom.get(i));
		
		List<String> enemyClassTypes = getEnemyTypeList();
		List<String> enemyClassDecks = new ArrayList<String>();
		
		
		for(int i=0; i<enemyDecks.size(); i++) {
			enemyClassDecks.add(enemyDecks.get(i).getDeckID());
		}
		
		
		if(enemyClassTypes.size()>enemyDecks.size()) {
			for(int i=0; i<enemyClassTypes.size(); i++) {
				for(int j=0; j<enemyClassDecks.size(); j++) {
					if(!enemyClassDecks.contains(enemyClassTypes.get(i))) {
						System.out.println("Added Enemy Ability Deck: "+enemyClassTypes.get(i));
						enemyDecks.add(new EnemyAbilityDeck(enemyClassTypes.get(i)));
						enemyClassDecks.add(enemyClassTypes.get(i));
					}
				}
			}
		}
	}
	
	public int getAttack(int index) {return enemyDecks.get(enemyDeckIndex).getAttack(enemies.get(index));}
	
	public void setEnemyDeckIndex(int index) {this.enemyDeckIndex=index;}
	public String getDeckClass() {return enemyDecks.get(enemyDeckIndex).getDeckID();}
	
	public void drawAbilityCard(Graphics g, Enemy enemy) {
		GUI.drawEnemyAbilityCards(g, enemyDecks, enemyDeckIndex, enemy);
	}
	
}
