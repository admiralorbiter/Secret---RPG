package Gloomhaven.Hex;

import java.awt.Point;

public class HexLayout {
	private HexOrientation orientation;
	private Point size;
	private Point origin;
	
	public HexLayout(HexOrientation orientation, Point size, Point origin) {
		this.orientation=orientation;
		this.size=size;
		this.origin=origin;
	}
	
	public HexOrientation getOrientation() {return orientation;}
	public Point getSize() {return size;}
	public Point getOrigin() {return origin;}
}
