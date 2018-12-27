package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Shop {
	
	ImageIcon shopImage=null;
	
	Setting setting = new Setting();
	
	List<Item> supply = new ArrayList<Item>();
	
	public Shop(int prosperityLevel) {
		
		shopImage = new ImageIcon("src/Gloomhaven/shop.png");
		if(prosperityLevel==1)
			supply=ItemLoader.loadAllLevel1Items();
	}
	
	public void drawShope(Graphics g) {
		if(shopImage!=null)
			g.drawImage(shopImage.getImage(), 50, 50, setting.getWidth()-200, setting.getHeight()-200, null);
		g.setColor(Color.black);
		g.fillRect(setting.getWidth()/2, setting.getHeight()/6, 650, 650);
		g.setColor(Color.white);
		
		
		int row=0;
		int col=0;
		for(int i=0; i<supply.size(); i++) {
			
			if(i%3==0) {
				row++;
				col=0;
			}
			else {
				col++;
			}
			
			g.drawString(supply.get(i).getName(), setting.getWidth()/2+col*150, setting.getHeight()/6+15+50*row);
		}
	}
	
	public void drawShop(Graphics g,int  xClick,int  yClick) {
		if(shopImage!=null)
			g.drawImage(shopImage.getImage(), 50, 50, setting.getWidth()-200, setting.getHeight()-200, null);
		g.setColor(Color.black);
		g.fillRect(setting.getWidth()/2, setting.getHeight()/6, 650, 650);
		g.setColor(Color.white);
		MatrixSelection matrix = new MatrixSelection(650, 650, supply.size());
		List<String> itemText = new ArrayList<String>();
		for(int i=0; i<supply.size(); i++)
			itemText.add(supply.get(i).getName());
		int selectionFlag=matrix.drawSelection(g, itemText, xClick, yClick);
		if(selectionFlag>=0 && selectionFlag<supply.size())
			supply.remove(selectionFlag);

		
	}
}
