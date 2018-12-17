package Gloomhaven;

import java.awt.Point;

public final class UtilitiesBoard {
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	public static Point cubeToCoordEven(int x, int y, int z ) {
		x=x+(z-(z&1))/2;
		y=z;

		Point point = new Point(x, y);
		return point;
	}
	
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	public static Point cubeToCoordOdd(int x, int y, int z) {
		x=x+(z+(z&1))/2;
		y=z;
		
		Point point = new Point(x, y);
		return point;
	}
	
	public static int[] oddToCubeCoord(Point p) {
		int cubeCoord[] = new int[3];
		int x=0;
		int y=1;
		int z=2;
		
		cubeCoord[x]=p.x-(p.y-(p.y&1))/2;
		cubeCoord[z]=p.y;
		cubeCoord[y]=-cubeCoord[x]-cubeCoord[z];
		
		return cubeCoord;
	}
	
	public static int[] evenToCubeCoord(Point p) {
		int cubeCoord[] = new int[3];
		int x=0;
		int y=1;
		int z=2;
		
		cubeCoord[x]=p.x-(p.y+(p.y&1))/2;
		cubeCoord[z]=p.y;
		cubeCoord[y]=-cubeCoord[x]-cubeCoord[z];
		
		return cubeCoord;
	}
}
