package Gloomhaven;

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
	
	public InfusionTable() {
		inert.add(fire);
		inert.add(ice);
		inert.add(air);
		inert.add(water);
		inert.add(light);
		inert.add(dark);
	}
	
	public void infuse(String element) {
		//NEED TO DO
		inert.remove(element);
		wanning.remove(element);
		strong.add(element);
	}
}
