package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

public final class Draw {
	
	public static  void parallelogramBoard(Graphics g, int size, boolean flatlayout, Point center, int radius) {
		for(int y=-radius; y<=radius; y++) {
			for(int x=-radius; x<=radius; x++) {			
				int q=x;
				int r=y;
				int s=-x-y;				
				drawHex(g, q, r, s, size, flatlayout, new Point(500, 500));
				//drawHex(g, s, q, r, 40, true, new Point(500, 500));
				//drawHex(g, r, s, q, 40, true, new Point(500, 500));
			}
		}
	}
	
	public static void triangleBoard(Graphics g, int size, boolean flatlayout, Point center, int radius) {
		for(int x=0; x<=radius; x++) {
			for(int y=0; y<=radius-x; y++) {		
				int q=x;
				int r=y;
				int s=-x-y;	
				drawHex(g, q, r, s, size, flatlayout, center);
			}
		}
	}
	
	public static void hexagonBoard(Graphics g, int size, boolean flatlayout, Point center, int radius) {
		for(int q=-radius; q<=radius; q++) {
			int r1=Math.max(-radius, -q-radius);
			int r2 = Math.min(radius, -q+radius);
			for(int r = r1; r<=r2; r++) {
				int s=-q-r;
				drawHex(g, q, r, s, size, flatlayout, center);
			}
		}
	}
	
	public static void rectangleBoardUpDown(Graphics g, int size, boolean flatlayout, Point center,  Point dimensions) {
		int height=(int) dimensions.getY();
		int width=(int) dimensions.getX();
		for(int q=0; q<height; q++) {
			int q_offset=(int) Math.floor(q/2);
			for(int s=-q_offset; s<width-q_offset; s++) {
				int r=-s-q;
				drawHex(g, q, r, s, size, false, center);
			}
		}
	}
	
	public static void rectangleBoardSideways(Graphics g, int size, boolean flatlayout, Point center,  Point dimensions) {
		int height=(int) dimensions.getY();
		int width=(int) dimensions.getX();	
		for(int r=0; r<height; r++) {
			int r_offset=(int) Math.floor(r/2);
			for(int q=-r_offset; q<width-r_offset; q++) {
				int s=-q-r;
				drawHex(g, q, r, s, size, flatlayout, center);
			}
		}
	}
	
	public static void drawHex(Graphics g, Hex h, int size, boolean flatlayout, Point center) {
		drawHex(g, h.q, h.r, h.s, size, flatlayout, center);
	}
	
	public static void drawHex(Graphics g, Point h, int size, boolean flatlayout, Point center) {
		
		Hex hex;
		
		if(flatlayout)
			hex = UtilitiesHex.flatOffsetToCube(1, h);
		else
			hex = UtilitiesHex.pointyOffsetToCube(1, h);
		
		drawHex(g, hex, size, flatlayout, center);
	}

	public static void drawHex(Graphics g, int q, int r, int s, int size, boolean flatlayout, Point center) {

		HexLayout layout;
		
		if(flatlayout)
			layout = new HexLayout(UtilitiesHex.getFlatLayoutOrientation(), new Point(size, size), center);
		else
			layout = new HexLayout(UtilitiesHex.getPointyLayoutOrientation(), new Point(size, size), center);
		
		
		List<Point2D> corners = UtilitiesHex.polygonCorners(layout, new Hex(q, r, s));
		int tX[] = new int[8];
		int tY[] = new int[8];

		for(int i=0; i<6; i++) {
			tX[i]=(int) corners.get(i).getX();
			tY[i]=(int) corners.get(i).getY();
		}

		g.drawPolygon(tX, tY, 6);
		
		if(size>=40) {
			g.drawString(q+", "+r+","+s, tX[3]+20, tY[3]+20);
		
			if(flatlayout)
				g.drawString(UtilitiesHex.flatOffsetFromCube(1, new Hex(q, r, s)).getX()+","+UtilitiesHex.flatOffsetFromCube(1, new Hex(q, r, s)).getY(), tX[3]+20, tY[3]+40);
			else
				g.drawString((int)UtilitiesHex.pointyOffsetFromCube(1, new Hex(q, r, s)).getX()+","+(int)UtilitiesHex.pointyOffsetFromCube(1, new Hex(q, r, s)).getY(), tX[3]+20, tY[3]+40);
	
		}
	}
	/*
	private static Color getColor(Hex hex) {
		
		Setting setting = new Setting();
		
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
	*/
}
