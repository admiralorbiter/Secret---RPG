package Gloomhaven;

import java.awt.Point;

public class Hex {
	
	Point offsetCoordinate;
	int q,r,s;
	
	public Hex(int x, int y, boolean flatlayout) {
		
		this.offsetCoordinate=new Point(x, y);
		
		Hex hex;
		if(flatlayout)
			hex=UtilitiesHex.flatOffsetToCube(1, new Point(x, y));
		else
			hex=UtilitiesHex.pointyOffsetToCube(1, new Point(x, y));
		
		this.q=hex.q;
		this.r=hex.r;
		this.s=hex.s;
			
	}
	
	public Hex(int q, int r, int s) {
		this.q=q;
		this.r=r;
		this.s=s;
	}
	
	public void setOffsetCoordinate(Point coordinate) {this.offsetCoordinate=coordinate;}
	
}
