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
			g.setColor(Color.black);
			g.fillRect(Setting.width/2+drawCol*x, Setting.height/6+15+drawRow*y, x-5, y-5);
			g.setColor(Color.WHITE);
			g.drawString(text.get(i).getName(), Setting.width/2+drawCol*x, Setting.height/6+25+drawRow*y);
			//g.drawString(text.get(i).getText(), setting.getWidth()/2+drawCol*x, setting.getHeight()/6+65+drawRow*y);
			
			
			int charLength=0;//x/5;
			int rowLength=1;
			int pixelsForEachChar=10;

			for(int j=0; j<text.get(i).getText().length(); j++) {
				
				if(j%(x/pixelsForEachChar)==0) {
					rowLength++;
					charLength=0;
				}
				else {
					charLength++;
				}
				char c = text.get(i).getText().charAt(j);
				g.drawString(String.valueOf(c), Setting.width/2+drawCol*x+charLength*pixelsForEachChar, Setting.height/6+20+drawRow*y+rowLength*11);
			}
			rowLength++;
			charLength=1;
			g.setColor(Color.RED);
			g.drawString("Gold: "+text.get(i).getGold(), Setting.width/2+drawCol*x+charLength*pixelsForEachChar, Setting.height/6+20+drawRow*y+rowLength*11);
			g.setColor(Color.WHITE);
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
		
		int x = xClick-Setting.width/2;
		int y=yClick-Setting.height/6+15;
		int w=width/col;
		int h=height/row;
			
		x=x/w;
		y=y/h;
		point = new Point(x, y);
		return point;
	}

}
