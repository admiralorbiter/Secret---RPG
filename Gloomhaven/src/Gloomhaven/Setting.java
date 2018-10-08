package Gloomhaven;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Setting {
	private int SIZE_OF_HEX=60;
	private Color DEFAULT_COLOR = Color.magenta;
	private int offsetY=0;
	private int offsetX=400;
	private Color HIGHLIGHT_COLOR = Color.GREEN;
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
	
	private int offsetXGraphics=10;
	private int offsetYGraphics=25;
	public int getGraphicsX() {return offsetXGraphics;}
	public int getGraphicsY() {return offsetYGraphics;}
	
	
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
