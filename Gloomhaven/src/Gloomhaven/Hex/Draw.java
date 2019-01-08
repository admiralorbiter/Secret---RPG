package Gloomhaven.Hex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import Gloomhaven.Setting;
import Gloomhaven.Characters.Player;

public final class Draw {
	
	@SuppressWarnings("ucd")
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
	
	@SuppressWarnings("ucd")
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
	
	public static void range(Graphics g, HexCoordinate point, int range) {
		
		g.setColor(Color.blue);
		
		for(int q=-range; q<=range; q++) {
			int r1=Math.max(-range, -q-range);
			int r2 = Math.min(range, -q+range);
			for(int r = r1; r<=r2; r++) {
				int s=-q-r;
				HexCoordinate hex = UtilitiesHex.add(new HexCoordinate(q, s, r), point);
				drawHex(g, hex, Setting.size, Setting.flatlayout, Setting.center);
			}
		}
	}
	
	@SuppressWarnings("ucd")
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

	
	@SuppressWarnings("ucd")
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
	
	@SuppressWarnings("ucd")
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
	
	public static void rectangleBoardSideways(Graphics g, Hex[][] board, Point dimensions) {
		for(int x=0; x<dimensions.x; x++) {
			for(int y=0; y<dimensions.y; y++) {
				if(board[x][y]!=null) {
					if(board[x][y].hasLoot())
						g.setColor(Setting.lootColor);
					else if(board[x][y].hasObstacle())
						g.setColor(Setting.obstacleColor);
					else
						g.setColor(Setting.defaultColor);
					drawHex(g, board[x][y]);
				}
			}	
		}
	}
	
	public static void drawHex(Graphics g, Point h) {
		drawHex(g, h, Setting.size, Setting.flatlayout, Setting.center);
	}
	
	public static void drawHex(Graphics g, HexCoordinate h) {
		drawHex(g, h, Setting.size, Setting.flatlayout, Setting.center);
	}
	
	public static void drawHex(Graphics g, HexCoordinate h, int size, boolean flatlayout, Point center) {
		drawHex(g, h.q, h.r, h.s, size, flatlayout, center);
	}
	
	public static void drawHex(Graphics g, Point h, int size, boolean flatlayout, Point center) {
		
		HexCoordinate hex;
		
		if(flatlayout)
			hex = UtilitiesHex.flatOffsetToCube(1, h);
		else
			hex = UtilitiesHex.pointyOffsetToCube(1, h);
		
		drawHex(g, hex, size, flatlayout, center);
	}
	
	public static void drawHex(Graphics g, Hex hex) {
		if(hex!=null) {
			if(!hex.isHidden())
				drawHex(g, hex.offsetCoordinate);
		}
	}

	public static void drawHex(Graphics g, int q, int r, int s, int size, boolean flatlayout, Point center) {
		HexLayout layout;

		if(flatlayout)
			layout = new HexLayout(UtilitiesHex.getFlatLayoutOrientation(), new Point(size, size), center);
		else
			layout = new HexLayout(UtilitiesHex.getPointyLayoutOrientation(), new Point(size, size), center);
		
		
		List<Point2D> corners = UtilitiesHex.polygonCorners(layout, new HexCoordinate(q, r, s));
		int tX[] = new int[8];
		int tY[] = new int[8];

		for(int i=0; i<6; i++) {
			tX[i]=(int) corners.get(i).getX();
			tY[i]=(int) corners.get(i).getY();
		}

		g.drawPolygon(tX, tY, 6);
		
		if(size>=40) {
			if(flatlayout) {
				g.drawString(q+", "+r+","+s, tX[3]+20, tY[3]);
				g.drawString(UtilitiesHex.flatOffsetFromCube(1, new HexCoordinate(q, r, s)).x+","+UtilitiesHex.flatOffsetFromCube(1, new HexCoordinate(q, r, s)).y, tX[3]+20, tY[3]+15);
			}
			else {
				g.drawString((int)UtilitiesHex.pointyOffsetFromCube(1, new HexCoordinate(q, r, s)).getX()+","+(int)UtilitiesHex.pointyOffsetFromCube(1, new HexCoordinate(q, r, s)).getY(), tX[3]+20, tY[3]+40);
				g.drawString(q+", "+r+","+s, tX[3]+20, tY[3]+20);
			}
		}
	}
	
	public static void drawParty(Graphics g, List<Player> party) {
		g.setColor(Setting.playerColor);
		for(int i=0; i<party.size(); i++) {
			drawHex(g, party.get(i).getCoordinates());
		}
		g.setColor(Setting.defaultColor);
	}
}
