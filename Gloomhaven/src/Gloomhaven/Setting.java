package Gloomhaven;

import java.awt.Color;

public class Setting {
	private int SIZE_OF_HEX=60;
	private Color DEFAULT_COLOR = Color.magenta;
	private int offsetY=0;
	private int offsetX=400;
	private Color HIGHLIGHT_COLOR = Color.GREEN;
	public Color getHighlightColor() {return HIGHLIGHT_COLOR;}
	public int getOffsetY() {return offsetY;}
	public int getOffsetX() {return offsetX;}
	public int getSizeOfHex(){return SIZE_OF_HEX;}
	public Color getDefaultColor() {return DEFAULT_COLOR;}
}
