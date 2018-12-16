package Gloomhaven;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Shop {
	
	ImageIcon shopImage=null;
	
	Setting setting = new Setting();
	
	public Shop() {
		shopImage = new ImageIcon("src/Gloomhaven/shop.png");
	}
	
	public void drawShop(Graphics g) {
		if(shopImage!=null)
			g.drawImage(shopImage.getImage(), 50, 50, setting.getWidth()-200, setting.getHeight()-200, null);
	}
}
