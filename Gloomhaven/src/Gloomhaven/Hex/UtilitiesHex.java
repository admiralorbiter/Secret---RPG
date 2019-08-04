package Gloomhaven.Hex;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Setting;

public final class UtilitiesHex {

	private static List<HexCoordinate> getHexDirectionList(){
		List<HexCoordinate> directionList = new ArrayList<HexCoordinate>();
		
		directionList.add(new HexCoordinate(1, 0, -1));
		directionList.add(new HexCoordinate(1, -1, 0));
		directionList.add(new HexCoordinate(0, -1,1));
		directionList.add(new HexCoordinate(-1, 0, 1));
		directionList.add(new HexCoordinate(-1, 1, 0));
		directionList.add(new HexCoordinate(0, 1, -1));
		
		return directionList;
	}
	
	public static int getDirection(HexCoordinate a, HexCoordinate b) {
		HexCoordinate hex = subtract(a, b);
		List<HexCoordinate> hexDirectionList = getHexDirectionList();
		
		for(int i=0; i<hexDirectionList.size(); i++)
			if(equals(hexDirectionList.get(i), hex))
				return i;
		
		return -1;
	}
	
	public static boolean equals(HexCoordinate a, HexCoordinate b) {
		return a.q==b.q && a.r==b.r && a.s==b.s; 
	}
	
	public static HexCoordinate add(HexCoordinate a, HexCoordinate b) {

		return  new HexCoordinate(a.q+b.q, a.r+b.r, a.s+b.s);
	}
	
	public static HexCoordinate subtract(HexCoordinate a, HexCoordinate b) {
		return  new HexCoordinate(a.q-b.q, a.r-b.r, a.s-b.s);
	}
	
	public static HexCoordinate multiply(HexCoordinate a, int k) {
		return  new HexCoordinate(a.q*k, a.r+k, a.s+k);
	}
	
	public static int length(HexCoordinate hex) {
		return (int)((Math.abs(hex.q)+Math.abs(hex.r)+Math.sqrt(hex.s))/2);
	}
	
	public static int distance(HexCoordinate a, HexCoordinate b) {
		return length(subtract(a, b));
	}
	
	public static HexCoordinate direction(int direction /*0 to 5*/) {
		
		if(direction>5 || direction<0)
			direction=direction&6;
		
		List<HexCoordinate> directionList = getHexDirectionList();

		if(direction>=0 && direction<=5)
			return directionList.get(direction);
		
		return null;
	}
	
	public static HexCoordinate neighbor(HexCoordinate hex, int direction) {
		return add(hex, direction(direction));
	}
	
	public static HexOrientation getPointyLayoutOrientation() {return new HexOrientation(Math.sqrt(3.0), Math.sqrt(3.0)/2.0, 0.0, 3.0/2.0,
																					Math.sqrt(3.0)/3.0, -1.0/3.0, 0.0, 2.0/3.0,
																					.5);
	}
	
	public static HexOrientation getFlatLayoutOrientation() {return new HexOrientation(3.0/2.0, 0.0, Math.sqrt(3.0)/2.0, Math.sqrt(3.0),
																2.0/3.0, 0.0, -1.0/3.0, Math.sqrt(3.0)/3.0,
																0.0);
	}
	
	public static Point2D hexToPixel(HexLayout layout, HexCoordinate h) {
		HexOrientation M = layout.getOrientation();
		double x=(M.f0*h.q+M.f1*h.r)*layout.getSize().getX()+layout.getOrigin().getX();
		double y=(M.f2*h.q+M.f3*h.r)*layout.getSize().getY()+layout.getOrigin().getY();
		return new Point2D.Double(x, y);
	}
	
	public static FractionalHex pixelToHex(HexLayout layout, Point2D p) {
		HexOrientation M = layout.getOrientation();
		Point2D pt = new Point.Double((p.getX()-layout.getOrigin().getX())/layout.getSize().getX(), ((p.getY()-layout.getOrigin().getY())/layout.getSize().getY()));
		double q = M.b0*pt.getX()+M.b1*pt.getY();
		double r = M.b2*pt.getX()+M.b3*pt.getY();
		return new FractionalHex(q, r, -q-r);
	}
	
	public static Point2D hexCornerOffset(HexLayout layout, int corner) {
		Point size = layout.getSize();
		
		double angle = 2.0*Math.PI*(layout.getOrientation().startingAngle+corner)/6;
		return new Point2D.Double(size.getX()*Math.cos(angle), size.getY()*Math.sin(angle));
	}
	
	public static List<Point2D> polygonCorners(HexLayout layout, HexCoordinate h){
		List<Point2D> corners = new ArrayList<Point2D>();
		
		Point2D center = hexToPixel(layout, h);
		
		for(int i=0; i<6; i++) {
			Point2D offset = hexCornerOffset(layout, i);
			corners.add(new Point2D.Double(center.getX()+offset.getX(), center.getY()+offset.getY()));
		}
		return corners;
	}
	
	public static HexCoordinate hexRound(FractionalHex h) {
		int q = (int) (Math.round(h.q));
		int r = (int) (Math.round(h.r));
		int s = (int) (Math.round(h.s));
		
		double  q_diff = Math.abs(q-h.q);
		double r_diff = Math.abs(r-h.r);
		double s_diff = Math.abs(s-h.s);
		
		if(q_diff>r_diff && q_diff>s_diff) {
			q=-r-s;
		}else if(r_diff>s_diff) {
			r=-q-s;
		}else {
			s=-q-r;
		}
		
		return new HexCoordinate(q, r, s);	
	}
	
	public static float lerp(double a, double b, double t) {
	    return (float) (a * (1-t) + b * t);
	}
	
	public static FractionalHex hexLerp(HexCoordinate a, HexCoordinate b, double t) {
		return new FractionalHex(lerp(a.q, b.q, t), lerp(a.r, b.r, t), lerp(a.s, b.s, t));
	}
	
	public static List<HexCoordinate> hexLineDraw(HexCoordinate a, HexCoordinate b){
		int N = distance(a, b);
		List<HexCoordinate> results = new ArrayList<HexCoordinate>();
		double step = 1.0/Math.max(N, 1);
		for(int i=0; i<=N; i++) {
			results.add(hexRound(hexLerp(a, b, step*i)));
		}
		return results;
	}
	
	public static HexCoordinate hexRotateLeft(HexCoordinate a) {
		return new HexCoordinate(-a.s, -a.q, -a.r);
	}
	
	public static HexCoordinate hexRotateRight(HexCoordinate a) {
		return new HexCoordinate(-a.r, -a.s, -a.q);
	}
	
	//Offset if even offset=1 and if odd offset=-1
	public static Point flatOffsetFromCube(int offset, HexCoordinate h) {
	    int col = h.q;
	    int row = h.r + (int)((h.q + offset * (h.q & 1)) / 2);
	    return new Point(col, row);
	}

	public static HexCoordinate flatOffsetToCube(int offset, Point h) {
	    int q = h.x;
	    int r = h.y - (int)((h.x + offset * (h.x & 1)) / 2);
	    int s = -q - r;
	    return new HexCoordinate(q, r, s);
	}

	public static Point pointyOffsetFromCube(int offset, HexCoordinate h) {
	    int col = h.q + (int)((h.r + offset * (h.r & 1)) / 2);
	    int row = h.r;
	    return new Point(col, row);
	}

	public static HexCoordinate pointyOffsetToCube(int offset, Point h) {
	    int q = h.x - (int)((h.y + offset * (h.y & 1)) / 2);
	    int r = h.y;
	    int s = -q - r;
	    return new HexCoordinate(q, r, s);
	}
	
	public static HexCoordinate getCubeCoordinates(boolean flatlayout, Point coordinates) {
		if(flatlayout)
			return flatOffsetToCube(1, coordinates);
		else
			return pointyOffsetToCube(1, coordinates);
	}
	
	public static Point getOffset(boolean flatlayout, HexCoordinate hex) {
		if(flatlayout)
			return flatOffsetFromCube(1, hex);
		else
			return pointyOffsetFromCube(1, hex);
	}
	
	public static Point getOffsetHexFromPixels(Point pixelPoint, boolean flatlayout) {
		FractionalHex fh;
		HexLayout hl;
		
		if(flatlayout)
			hl = new HexLayout(UtilitiesHex.getFlatLayoutOrientation(), new Point(Setting.size, Setting.size), Setting.center);
		else
			hl = new HexLayout(UtilitiesHex.getPointyLayoutOrientation(), new Point(Setting.size, Setting.size), Setting.center);
			
		
		fh=UtilitiesHex.pixelToHex(hl, pixelPoint);
		
		HexCoordinate hc = UtilitiesHex.hexRound(fh);
		System.out.println(UtilitiesHex.getOffset(flatlayout, hc));
		System.out.println(pixelPoint+" - "+UtilitiesHex.hexToPixel(hl, hc));
		return UtilitiesHex.getOffset(flatlayout, hc);
	}
}

