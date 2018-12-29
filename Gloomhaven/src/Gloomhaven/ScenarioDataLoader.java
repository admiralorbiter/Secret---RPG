package Gloomhaven;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;

public final class ScenarioDataLoader {
	
	public static String getRoomID() {
		return "Test";
	}
	
	public static String getArea() {
		return "Test";
	}
	
	
	public static ScenarioData loadScenarioData(int id) {
		ScenarioData data = new ScenarioData(id);
		
		switch(id) {
			case 1:
				data.setName("Black Barrow");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('G', 10));
				data.getPartyAchievements().add("First Steps");
				data.getNewLocations().add(new MapLocation('G', 11));
				data.setRoomTotal(3);
				data.setBoardSize(new Point(13, 11));
				return data;
			case 2:
				data.setName("Barrow Lair");
				data.setGoal("Kill the Bandit Commander and all revealed enemies");
				data.setRequirements("Party Achievement: First Steps");
				data.setLocation(new MapLocation('G', 11));
				data.setSpecialRules(true);
				data.getNewLocations().add(new MapLocation('G', 3));
				data.getNewLocations().add(new MapLocation('E', 11));
				data.setRoomTotal(6);
				data.setBoardSize(new Point(16, 16));
				return data;
			case 3:
				data.setName("Inox Encampement");
				data.setRequirements("The Merchant Flees (Global) Incomplete");
				data.setGoal("Kill a number of enemies equal to five times the number of characters");
				data.setLocation(new MapLocation('G', 3));
				data.setSpecialRules(true);
				data.getNewLocations().add(new MapLocation('C', 18));
				data.getNewLocations().add(new MapLocation('L', 2));
				data.getPartyAchievements().add("Jekserah's Plans");
				data.setRoomTotal(6);
				data.setBoardSize(new Point(15, 17));
				return data;
			case 4:
				data.setName("Crypt of the Damned");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('E', 11));
				data.getNewLocations().add(new MapLocation('D', 6));
				data.getNewLocations().add(new MapLocation('F', 10));
				data.setRoomTotal(4);
				data.setBoardSize(new Point(11, 17));
				return data;
			case 5:
				data.setName("Ruinous Crypt");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('D', 6));
				data.setSpecialRules(true);
				data.getNewLocations().add(new MapLocation('C', 7));
				data.getNewLocations().add(new MapLocation('C', 10));
				data.getNewLocations().add(new MapLocation('M', 7));
				data.setRoomTotal(3);
				data.setBoardSize(new Point(8, 21));
				return data;
			case 6:
				data.setName("Decaying Crypt");
				data.setGoal("Reveal the M tile and kill all revealed enemies.");
				data.setLocation(new MapLocation('F', 10));
				data.setSpecialRules(true);
				data.getNewLocations().add(new MapLocation('C', 18));
				data.getPartyAchievements().add("Jekserah's Plans");
				data.getPartyAchievements().add("Dark Bounty");
				data.setRoomTotal(4);
				data.setBoardSize(new Point(10, 21));
				return data;
			case 7:
				data.setName("Vibrant Grotto");
				data.setRequirements("The Power of Enhancement (Global) and The Merchant Flees (Global) Complete");
				data.setGoal("Loot all treasure tiles");
				data.setLocation(new MapLocation('C', 12));
				data.setSpecialRules(true);
				data.getNewLocations().add(new MapLocation('H', 13));
				data.setRoomTotal(6);
				data.setBoardSize(new Point(15, 19));
				return data;
			case 8:
				data.setName("Gloomhaven Warehouse");
				data.setRequirements("Jekserah's Plans (Party) Complete and The Dead Invade (Global) Incomplete");
				data.setGoal("Kill both Inox Bodyguards");
				data.setLocation(new MapLocation('C', 18));
				data.getNewLocations().add(new MapLocation('C', 12));
				data.getNewLocations().add(new MapLocation('N', 3));
				data.getNewLocations().add(new MapLocation('C', 10));
				data.getGlobalAchievements().add("The Merchant Flees");
				data.setRoomTotal(3);
				data.setBoardSize(new Point(8, 15));
				return data;
			case 9:
				data.setName("Diamond Mine");
				data.setRequirements("The Merchant Flees (Global) Incomplete");
				data.setGoal("Kill the Merciless Overseer and loot the treasure tile.");
				data.setLocation(new MapLocation('L', 2));
				data.getNewLocations().add(new MapLocation('B', 16));
				data.getNewLocations().add(new MapLocation('B', 16));
				data.getGlobalAchievements().add("The Dead Invade");
				data.setRoomTotal(2);
				data.setBoardSize(new Point(8, 13));
				return data;
			case 10:
				data.setName("Plane of Elemental Power");
				data.setRequirements("The Rift Neutralized (Global) Incomplete");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('C', 7));
				data.getNewLocations().add(new MapLocation('C', 7));
				data.getNewLocations().add(new MapLocation('K', 8));
				data.getGlobalAchievements().add("A Demon's Errand");
				data.setRoomTotal(3);
				data.setBoardSize(new Point(16, 11));
				return data;
		}
		
		System.out.println("Error with scenario loader with scenario with ID: "+id);
		return data;
	}
}
