package Gloomhaven;

import java.awt.Point;

public class HexCoordinate {
	
	Point offsetCoordinate;
	int q,r,s;
	
	public HexCoordinate(int x, int y, boolean flatlayout) {
		
		this.offsetCoordinate=new Point(x, y);
		
		HexCoordinate hex;
		if(flatlayout)
			hex=UtilitiesHex.flatOffsetToCube(1, new Point(x, y));
		else
			hex=UtilitiesHex.pointyOffsetToCube(1, new Point(x, y));
		
		this.q=hex.q;
		this.r=hex.r;
		this.s=hex.s;
			
	}
	
	public HexCoordinate(int q, int r, int s) {
		this.q=q;
		this.r=r;
		this.s=s;
	}
	
	public void setOffsetCoordinate(Point coordinate) {this.offsetCoordinate=coordinate;}
	
}
