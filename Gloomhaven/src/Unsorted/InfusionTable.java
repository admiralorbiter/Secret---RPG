package Unsorted;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class InfusionTable implements Serializable{
	
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
	
	boolean anyflag=false;
	
	public InfusionTable() {
		inert.add(earth);
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

		if(anyflag) {
			anyflag=false;
			return true;
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
	
	public boolean consumeAny(Graphics g, int num) {
		//if(strong.size()==0 && wanning.size()==0)
			//return true;

		GUIInfusion.drawConsumeAny(g, strong, wanning);
		
		if(strong.size()==0 && wanning.size()==0)
			return true;
		
		if(num>=1 && num<=6) {
			String element="";
			switch(num) {
				case 1: if(strong.contains("Fire") || wanning.contains("Fire")) {
					element="Fire";
					consume(element);
					anyflag=true;
					return true;
				}
				break;
				case 2: if(strong.contains("Ice") || wanning.contains("Ice")) {
					element="Ice";
					consume(element);
					anyflag=true;
					return true;
				}
				break;
				case 3: if(strong.contains("Air") || wanning.contains("Air")) {
					element="Air";
					consume(element);
					anyflag=true;
					return true;
				}
				break;
				case 4: if(strong.contains("Earth") || wanning.contains("Earth")) {
					element="Earth";
					consume(element);
					anyflag=true;
					return true;
				}
				break;
				case 5: if(strong.contains("Light") || wanning.contains("Light")) {
					element="Light";
					consume(element);
					anyflag=true;
					return true;
				}
				break;
				case 6: if(strong.contains("Dark") || wanning.contains("Dark")) {
					element="Dark";
					consume(element);
					anyflag=true;
					return true;
				}
				break;
			}
		}

		return false;
	}
	
	public void graphicsDrawTable(Graphics g) {
		GUIInfusion.drawInfusionTable(g, strong, wanning);
	}
}
