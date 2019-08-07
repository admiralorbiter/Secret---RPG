package Gloomhaven.Hex;

import java.awt.Point;
import java.io.Serializable;

public class HexCoordinate implements Serializable{
	
	Point offsetCoordinate;
	int q,r,s;
	boolean flatlayout;
	
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
		
		this.flatlayout=flatlayout;
	}
	
	public HexCoordinate(int q, int r, int s) {
		this.q=q;
		this.r=r;
		this.s=s;
	}
	
	public HexCoordinate(int q, int r, int s, boolean flatlayout) {
		this.q=q;
		this.r=r;
		this.s=s;
		this.flatlayout=flatlayout;
	}
	
	public void setOffsetCoordinate(Point coordinate) {this.offsetCoordinate=coordinate;}
	public boolean getLayout() {return flatlayout;}
	public void setLayout(boolean flatlayout) {this.flatlayout=flatlayout;}
	
}
