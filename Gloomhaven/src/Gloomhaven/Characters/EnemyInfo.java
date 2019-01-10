package Gloomhaven.Characters;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.EnemyAbilityDeck;
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
	
	public EnemyInfo(ScenarioData data) {
		enemies=ScenarioEnemyLoader.getEnemies(data.getId(), 0);
		
		List<String> enemyClassTypes = getEnemyTypeList();
		for(int i=0; i<enemyClassTypes.size(); i++)
			enemyDecks.add(new EnemyAbilityDeck(enemyClassTypes.get(i)));
	}
	
	public void drawEnemies(Graphics g) {
		g.setColor(Setting.enemyColor);
		for(int i=0; i<enemies.size(); i++) {
			Draw.drawHex(g, enemies.get(i).getCoordinates());
		}
		g.setColor(Setting.enemyColor);
	}
	
	public void initiationRound() {
		for(int i=0; i<enemyDecks.size(); i++)
			enemyDecks.get(i).pickRandomAbilityCard();
	}
	
	public void setTurnNumber(int turnNumber) {this.turnNumber=turnNumber;}
	public int getTurnNumber() {return turnNumber;}
	public Enemy getEnemy(int index) {return enemies.get(index);}
	public int getCount() {return enemies.size();}
	
	public List<String> getEnemyTypeList(){
		List<String> enemyTypeList = new ArrayList<String>();
		
		for(int i=0; i<enemies.size(); i++)
			if(!enemyTypeList.contains(enemies.get(i).getClassID()))
				enemyTypeList.add(enemies.get(i).getClassID());
		
		return enemyTypeList;
	}
	
	public void enemyMoveProcedure(int index, List<Player> party, Graphics g) {
			
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
			}
		}
	}
	
	public void updateEnemyList(int id, int room) {
		List<Enemy> enemiesInRoom = ScenarioEnemyLoader.getEnemies(id, room);
		
		for(int i=0; i<enemiesInRoom.size(); i++)
			enemies.add(enemiesInRoom.get(i));
		
		List<String> enemyClassTypes = getEnemyTypeList();
		List<String> enemyClassDecks = new ArrayList<String>();
		
		for(int i=0; i<enemyDecks.size(); i++)
			enemyClassDecks.add(enemyDecks.get(i).getDeckID());
		
		if(enemyClassTypes.size()>enemyDecks.size()) {
			for(int i=0; i<enemyClassTypes.size(); i++) {
				if(!enemyClassDecks.get(i).contains(enemyClassTypes.get(i))) {
					enemyDecks.add(new EnemyAbilityDeck(enemyClassTypes.get(i)));
				}
			}
		}
	}
	
	
}
