package Gloomhaven;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Setting {
	private int SIZE_OF_HEX=60;
	private Color DEFAULT_COLOR = Color.WHITE;
	private int offsetY=0;
	private int offsetX=350;
	private Color HIGHLIGHT_COLOR = Color.GREEN;
	private Color GUI_COLOR = Color.WHITE;
	public Color getGUIColor() {return GUI_COLOR;}
	private int WIDTH=1400;
	private int HEIGHT=800;
	private String TITLE="Gloomhaven";
	
	public String getTitle() {return TITLE;}
	public int getWidth() {return WIDTH;}
	public int getHeight() {return HEIGHT;}
	public Color getHighlightColor() {return HIGHLIGHT_COLOR;}
	public int getOffsetY() {return offsetY;}
	public int getOffsetX() {return offsetX;}
	public int getSizeOfHex(){return SIZE_OF_HEX;}
	public Color getDefaultColor() {return DEFAULT_COLOR;}
	char targetKey = 't';
	public char getTargetKey() {return targetKey;}
	char moveKey = 'm';
	public char getMoveKey() {return moveKey;}
	char restKey='r';
	public char getRestKey() {return restKey;}
	char healKey='h';
	public char getHealKey() {return healKey;}
	char discardKey='d';
	public char getDiscardKey() {return discardKey;}
	char up = 'w';
	char left = 'a';
	char right = 'd';
	char down = 's';
	public char up() {return up;}
	public char left() {return left;}
	public char right() {return right;}
	public char down() {return down;}
	private int offsetXGraphics=10;
	private int offsetYGraphicsTop=25;
	private int offsetXGraphicsMid=WIDTH/3;
	private int offsetXGraphicsRight=WIDTH*5/6;
	public int getGraphicsXRight() {return offsetXGraphicsRight;}
	public int getGraphicsXMid() {return offsetXGraphicsMid;}
	public int getGraphicsX() {return offsetXGraphics;}
	public int getGraphicsYTop() {return offsetYGraphicsTop;}
	private int offsetYGraphicsBottom=500;
	public int getGraphicsYBottom() {return offsetYGraphicsBottom;}
	private int offsetYGraphicsMid=300;
	public int getGraphicsYMid() {return offsetYGraphicsMid;}
	
	
	private String playerClass="Mind Thief";
	
	
	public int getStartingAbilityCardCount() {
		if(playerClass=="Brute" || playerClass=="Mind Thief")
			return 10;
		if(playerClass=="Scoundrel")
			return 9;
		if(playerClass=="Spellweaver")
			return 8;
		if(playerClass=="Cragheart")
			return 11;
		if(playerClass=="Tinkerer")
			return 12;
		
		return 0;
	}
	
	
	public String getPlayerClass() {return playerClass;}
	private Color playerColor = Color.GREEN;
	public Color getPlayerColor() {return playerColor;}
	private Color enemyEliteColor = Color.yellow;
	public Color getEliteEnemyColor() {return enemyEliteColor;}
	private Color enemyColor = Color.CYAN;
	public Color getEnemyColor() {return enemyColor;}
	//[Testing]
	private int NUM_PLAYERS=1;
	private String SCENE_ID="Test";
	public String getSceneID() {return SCENE_ID;}
	public int getNumPlayers() {return NUM_PLAYERS;}
	
	//Functions
	//[Test] Function that delays for a certain amount of seconds
	private void delayBySeconds(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
