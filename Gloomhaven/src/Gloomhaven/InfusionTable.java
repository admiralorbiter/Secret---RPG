package Gloomhaven;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class InfusionTable {
	
	String fire="Fire";
	String ice = "Ice";
	String air = "Air";
	String water = "Water";
	String light = "Light";
	String dark = "Dark";
	
	List<String> strong = new ArrayList<String>();
	List<String> wanning = new ArrayList<String>();
	List<String> inert = new ArrayList<String>();
	
	Setting setting = new Setting();
	
	public InfusionTable() {
		inert.add(fire);
		inert.add(ice);
		inert.add(air);
		inert.add(water);
		inert.add(light);
		inert.add(dark);
	}
	
	public void infuse(String element) {
		inert.remove(element);
		wanning.remove(element);
		strong.add(element);
	}
	
	public void graphicsDrawTable(Graphics g) {
		//g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*6);
		//Earth
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33);
		g.drawString("Earth:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33);
		//Air
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*2);
		g.drawString("Air:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*2);
		//Fire
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*3);
		g.drawString("Fire:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*3);
		//Water
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*4);
		g.drawString("Water:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*4);
		//Dark
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*5);
		g.drawString("Dark:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*5);
		//Light
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*6);
		g.drawString("Light:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*6);
	}
}
