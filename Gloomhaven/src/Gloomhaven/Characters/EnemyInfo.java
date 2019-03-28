package Gloomhaven.Characters;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import Gloomhaven.EnemyAbilityDeck;
import Gloomhaven.FontSettings;
import Gloomhaven.GUI;
import Gloomhaven.GUISettings;
import Gloomhaven.Setting;
import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;
import Gloomhaven.Hex.Draw;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Hex.UtilitiesHex;
import Gloomhaven.Scenario.ScenarioData;
import Gloomhaven.Scenario.ScenarioEnemyLoader;

public class EnemyInfo {
	
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
	
	public void drawEnemies(Graphics2D g) {
		g.setColor(Setting.enemyColor);
		for(int i=0; i<enemies.size(); i++) {
			Draw.drawHex(g, enemies.get(i).getCoordinates(), enemies.get(i).getImage());
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
	
	public void setTurnNumber(int turnNumber) {this.turnNumber=turnNumber;}
	public int getTurnNumber() {return turnNumber;}
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
	
	public void enemyMoveProcedure(int index, List<Player> party, Graphics g) {
			
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
		
		for(int i=0; i<enemyDecks.size(); i++)
			enemyClassDecks.add(enemyDecks.get(i).getDeckID());
		
		if(enemyClassTypes.size()>enemyDecks.size()) {
			for(int i=0; i<enemyClassTypes.size(); i++) {
				for(int j=0; j<enemyClassDecks.size(); j++) {
					if(!enemyClassDecks.get(j).contains(enemyClassTypes.get(i))) {
						enemyDecks.add(new EnemyAbilityDeck(enemyClassTypes.get(i)));
					}
				}
			}
		}
	}
	
	public int getAttack(int index) {return enemyDecks.get(enemyDeckIndex).getAttack(enemies.get(index));}
	
	public void setEnemyDeckIndex(int index) {this.enemyDeckIndex=index;}
	public String getDeckClass() {return enemyDecks.get(enemyDeckIndex).getDeckID();}
	
	public void drawAbilityCard(Graphics g) {
		GUI.drawEnemyAbilityCards(g, enemyDecks, enemyDeckIndex);
	}
	
}
