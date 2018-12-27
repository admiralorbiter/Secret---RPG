package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MatrixSelection {
	int width;
	int height;
	int itemTotal;
	int row;
	int col;
	Setting setting = new Setting();
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
		}
	}
	
	public void drawSelection(Graphics g, List<String> text , int xClick, int yClick) {
		int x=width/row;
		int y=height/col;
		int drawRow=0;
		int drawCol=0;
		for(int i=0; i<itemTotal; i++) {
			if(i%row==0) {
				if(i!=0)
					drawRow++;
				drawCol=0;
			}
			else {
				drawCol++;
			}
			g.setColor(Color.magenta);
			g.fillRect(setting.getWidth()/2+drawCol*x, setting.getHeight()/6+15+drawRow*y, x-5, y-5);
			g.setColor(Color.WHITE);
			g.drawString(text.get(i), setting.getWidth()/2+drawCol*x, setting.getHeight()/6+15+drawRow*y);
		}
		System.out.println(xClick+", "+yClick);
	}

}
