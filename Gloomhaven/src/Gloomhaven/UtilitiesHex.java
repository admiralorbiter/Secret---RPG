package Gloomhaven;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public final class UtilitiesHex {

	private static List<Hex> getHexDirectionList(){
		List<Hex> directionList = new ArrayList<Hex>();
		
		directionList.add(new Hex(1, 0, -1));
		directionList.add(new Hex(1, -1, 0));
		directionList.add(new Hex(0, -1,1));
		directionList.add(new Hex(-1, 0, 1));
		directionList.add(new Hex(-1, 1, 0));
		directionList.add(new Hex(0, 1, -1));
		
		return directionList;
	}
	
	public static boolean equals(Hex a, Hex b) {
		return a.q==b.q && a.r==b.r && a.s==b.s; 
	}
	
	public static Hex add(Hex a, Hex b) {

		return  new Hex(a.q+b.q, a.r+b.r, a.s+b.s);
	}
	
	public static Hex subtract(Hex a, Hex b) {
		return  new Hex(a.q-b.q, a.r-b.r, a.s-b.s);
	}
	
	public static Hex multiply(Hex a, int k) {
		return  new Hex(a.q*k, a.r+k, a.s+k);
	}
	
	public static int length(Hex hex) {
		return (int)((Math.abs(hex.q)+Math.abs(hex.r)+Math.sqrt(hex.s))/2);
	}
	
	public static int distance(Hex a, Hex b) {
		return length(subtract(a, b));
	}
	
	public static Hex direction(int direction /*0 to 5*/) {
		
		List<Hex> directionList = getHexDirectionList();

		if(direction>=0 && direction<=5)
			return directionList.get(direction);
		
		return null;
	}
	
	public static Hex neighbor(Hex hex, int direction) {
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
	
	public static Point2D hexToPixel(HexLayout layout, Hex h) {
		HexOrientation M = layout.getOrientation();
		double x=(M.f0*h.q+M.f1*h.r)*layout.getSize().getX()+layout.getOrigin().getX();
		double y=(M.f2*h.q+M.f3*h.r)*layout.getSize().getY()+layout.getOrigin().getY();
		return new Point2D.Double(x, y);
	}
	
	public static FractionalHex pixelToHex(HexLayout layout, Point2D p) {
		HexOrientation M = layout.getOrientation();
		Point2D pt = new Point.Double((p.getX()-layout.getOrigin().getX())/layout.getSize().getX(), (p.getY()-layout.getOrigin().getY()/layout.getSize().getY()));
		double q = M.b0*pt.getX()+M.b1*pt.getY();
		double r = M.b2*pt.getX()+M.b3*pt.getY();
		return new FractionalHex(q, r, -q-r);
	}
	
	public static Point2D hexCornerOffset(HexLayout layout, int corner) {
		Point size = layout.getSize();
		
		double angle = 2.0*Math.PI*(layout.getOrientation().startingAngle+corner)/6;
		return new Point2D.Double(size.getX()*Math.cos(angle), size.getY()*Math.sin(angle));
	}
	
	public static List<Point2D> polygonCorners(HexLayout layout, Hex h){
		List<Point2D> corners = new ArrayList<Point2D>();
		
		Point2D center = hexToPixel(layout, h);
		
		for(int i=0; i<6; i++) {
			Point2D offset = hexCornerOffset(layout, i);
			corners.add(new Point2D.Double(center.getX()+offset.getX(), center.getY()+offset.getY()));
		}
		return corners;
	}
	
	public static Hex hexRound(FractionalHex h) {
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
		
		return new Hex(q, r, s);	
	}
	
	public static float lerp(double a, double b, double t) {
	    return (float) (a * (1-t) + b * t);
	}
	
	public static FractionalHex hexLerp(Hex a, Hex b, double t) {
		return new FractionalHex(lerp(a.q, b.q, t), lerp(a.r, b.r, t), lerp(a.s, b.s, t));
	}
	
	public static List<Hex> hexLineDraw(Hex a, Hex b){
		int N = distance(a, b);
		List<Hex> results = new ArrayList<Hex>();
		double step = 1.0/Math.max(N, 1);
		for(int i=0; i<=N; i++) {
			results.add(hexRound(hexLerp(a, b, step*i)));
		}
		return results;
	}
	
	public static Hex hexRotateLeft(Hex a) {
		return new Hex(-a.s, -a.q, -a.r);
	}
	
	public static Hex hexRotateRight(Hex a) {
		return new Hex(-a.r, -a.s, -a.q);
	}
	
	//Offset if even offset=1 and if odd offset=-1
	public static Point flatOffsetFromCube(int offset, Hex h) {
	    int col = h.q;
	    int row = h.r + (int)((h.q + offset * (h.q & 1)) / 2);
	    return new Point(col, row);
	}

	public static Hex flatOffsetToCube(int offset, Point h) {
	    int q = h.x;
	    int r = h.y - (int)((h.x + offset * (h.x & 1)) / 2);
	    int s = -q - r;
	    return new Hex(q, r, s);
	}

	public static Point pointyOffsetFromCube(int offset, Hex h) {
	    int col = h.q + (int)((h.r + offset * (h.r & 1)) / 2);
	    int row = h.r;
	    return new Point(col, row);
	}

	public static Hex pointyOffsetToCube(int offset, Point h) {
	    int q = h.x - (int)((h.y + offset * (h.y & 1)) / 2);
	    int r = h.y;
	    int s = -q - r;
	    return new Hex(q, r, s);
	}
}

