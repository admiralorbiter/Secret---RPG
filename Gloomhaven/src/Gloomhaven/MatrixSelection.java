package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MatrixSelection {
	int width;
	int height;
	int itemTotal;
	int row;
	int col;

	List<Point> itemIndexes = new ArrayList<Point>();
	
	public MatrixSelection(int width, int height, int itemTotal) {
		this.width=width;
		this.height=height;
		this.itemTotal=itemTotal;
		
		if(itemTotal<9) {
			row=3;
			col=3;
		}else if(itemTotal<16) {
			row=4;
			col=4;
		}
		else if(itemTotal<25) {
			row=5;
			col=5;
		}else {
			row=6;
			col=6;
		}
	}
	
	public int drawSelection(Graphics g, List<Item> text , int xClick, int yClick) {
		int x=width/row;
		int y=height/col;
		int drawRow=0;
		int drawCol=0;
		for(int i=0; i<itemTotal; i++) {
			itemIndexes.add(new Point(drawRow, drawCol));

			if(i%row==0) {
				if(i!=0)
					drawRow++;
				drawCol=0;
			}
			else {
				drawCol++;
			}
			
			GUI.drawMatrixSelection(g, drawRow, drawCol, x, y, text, i);
		}
		Point selectionPoint = new Point(findSelection(xClick, yClick));
		if(selectionPoint.getX()>=0 && selectionPoint.getX()<=col) {
			if(selectionPoint.getY()>=0 && selectionPoint.getY()<=row)
				return itemIndexes.indexOf(new Point(selectionPoint));
		}

		return -99;
	}
	
	public Point findSelection(int xClick, int yClick) {
		Point point = new Point(-99, -99);
		
		int x = xClick-GUISettings.width/2;
		int y=yClick-GUISettings.height/6+15;
		int w=width/col;
		int h=height/row;
			
		x=x/w;
		y=y/h;
		point = new Point(x, y);
		return point;
	}

}
