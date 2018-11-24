package Gloomhaven;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class InfusionTable {
	
	String earth = "Earth";
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
		inert.add(earth);
		inert.add(fire);
		inert.add(ice);
		inert.add(air);
		inert.add(water);
		inert.add(light);
		inert.add(dark);
		
		//testingStrong();
	}
	
	private void testingStrong() {
		inert = new ArrayList<String>();
		strong.add(earth);
		strong.add(fire);
		strong.add(ice);
		strong.add(air);
		strong.add(water);
		strong.add(light);
		strong.add(dark);
	}
	
	public void infuse(String element) {
		inert.remove(element);
		wanning.remove(element);
		strong.add(element);
	}
	
	public void endOfRound() {
		
		for(int i=0; i<wanning.size(); i++) {
			inert.add(wanning.get(0));
			wanning.remove(0);
		}
		
		for(int i=0; i<strong.size(); i++) {
			wanning.add(strong.get(0));
			strong.remove(0);
		}
	}
	
	public boolean consume(String element) {
		System.out.println("Trying to consume "+element);
		
		if(element.equals("Any")) {
			if(strong.size()>0) {
				inert.add(strong.get(0));
				strong.remove(0);
				return true;
			}
			
			if(wanning.size()>0) {
				inert.add(wanning.get(0));
				wanning.remove(0);
				return true;
			}
			return false;
		}
		
		if(strong.contains(element)) {
			strong.remove(element);
			inert.add(element);
			return true;
		}
		else if(wanning.contains(element)) {
			wanning.remove(element);
			inert.add(element);
			return true;
		}
		else
			return false;
		
		
	}
	
	public void graphicsDrawTable(Graphics g) {
		//g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*6);
		//Earth
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33);
		g.drawString("Earth:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33);
		if(strong.contains("Earth"))
			g.drawString("Strong", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33);
		else if(wanning.contains("Earth"))
			g.drawString("Wanning", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33);
		
		//Air
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*2);
		g.drawString("Air:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*2);
		if(strong.contains("Air"))
			g.drawString("Strong", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*2);
		else if(wanning.contains("Air"))
			g.drawString("Wanning", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*2);
		
		//Fire
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*3);
		g.drawString("Fire:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*3);
		if(strong.contains("Fire"))
			g.drawString("Strong", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*3);
		else if(wanning.contains("Fire"))
			g.drawString("Wanning", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*3);
		
		//Water
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*4);
		g.drawString("Water:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*4);
		if(strong.contains("Water"))
			g.drawString("Strong", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*4);
		else if(wanning.contains("Water"))
			g.drawString("Wanning", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*4);
		
		//Dark
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*5);
		g.drawString("Dark:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*5);
		if(strong.contains("Dark"))
			g.drawString("Strong", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*5);
		else if(wanning.contains("Dark"))
			g.drawString("Wanning", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*5);
		
		//Light
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid()+215, 200, 33*6);
		g.drawString("Light:", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+200+33*6);
		if(strong.contains("Light"))
			g.drawString("Strong", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*6);
		else if(wanning.contains("Light"))
			g.drawString("Wanning", setting.getGraphicsXRight()+50, setting.getGraphicsYMid()+200+33*6);
	}
}
