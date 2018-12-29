package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class ScenarioDrawBoard {
	private Hex[][] board;
	private Point dimensions;
	private Setting setting = new Setting();
	
	public ScenarioDrawBoard(Hex[][] board, Point dimensions) {
		this.board=board;
		this.dimensions=dimensions;
	}
	
	public void drawBoard(Graphics g) {
		for(int y=0; y<dimensions.getY(); y++) {
			for(int x=0; x<dimensions.getX(); x++) {
					drawHex(g, x, y);
			}
		}
	}
	
	//Draws the hex given an x, y coordinate, Draws the quick id of the hex, Draws the x, y coordinate in the hex
	private void drawHex(Graphics g, int x, int y) {
		int nPoints=7;												//Points in a hex
		int SIZE_OF_HEX=setting.getSizeOfHex();
		int offsetY=setting.getOffsetY();
		int offsetX=setting.getOffsetX();
		int bufferY=-1*setting.getSizeOfHex()/3;
		
		if(y%2!=0) {
			offsetX=offsetX+setting.getSizeOfHex()/2;
		}
		
		int[] tX = {0+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX, 0+x*(SIZE_OF_HEX)+offsetX, 0+x*(SIZE_OF_HEX)+offsetX};
		int[] tY = {SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY, 0+y*(SIZE_OF_HEX+bufferY)+offsetY,  SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX*2/3+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX*2/3+y*(SIZE_OF_HEX+bufferY)+offsetY, SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY};
		g.drawPolygon(tX, tY, nPoints);
		
		Color c =getColor(board[x][y]);
		
		g.drawString(board[x][y].getQuickID(), SIZE_OF_HEX/2+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/2+5+y*(SIZE_OF_HEX+bufferY)+offsetY);
		g.drawString(x+", "+y, SIZE_OF_HEX/3+x*(SIZE_OF_HEX)+offsetX, SIZE_OF_HEX/3+y*(SIZE_OF_HEX+bufferY)+offsetY);
		
		g.setColor(setting.getDefaultColor());
	}
	
	private Color getColor(Hex hex) {
		
		if(hex.getQuickID()=="P")
			return setting.getPlayerColor();
		else if(hex.getQuickID()=="E")
		{
			if(hex.getID().contains("Elite"))
				return setting.getEliteEnemyColor();
			else
				return setting.getEnemyColor();
			
		}else if(hex.getQuickID()=="Loot") {
			return Color.ORANGE;
		}
		
		return Color.WHITE;
	}
}
