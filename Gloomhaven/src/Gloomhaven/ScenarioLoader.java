package Gloomhaven;

public final class ScenarioLoader {
	public static ScenarioData loadScenarioData(int id) {
		ScenarioData data = new ScenarioData(id);
		
		switch(id) {
			case 1:
				data.setName("Black Barrow");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('G', 10));
				return data;
			case 2:
				data.setName("Barrow Lair");
				data.setGoal("Kill the Bandit Commander and all revealed enemies");
				data.setRequirements("Party Achievement: First Steps");
				data.setLocation(new MapLocation('G', 11));
				return data;
			case 3:
				data.setName("Inox Encampement");
				data.setRequirements("The Merchant Flees (Global) Incomplete");
				data.setGoal("Kill a number of enemies equal to five times the number of characters");
				data.setLocation(new MapLocation('G', 3));
				return data;
			case 4:
				data.setName("Crypt of the Damned");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('E', 11));
				return data;
			case 5:
				data.setName("Ruinous Crypt");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('D', 6));
				return data;
			case 6:
				data.setName("Decaying Crypt");
				data.setGoal("Reveal the M tile and kill all revealed enemies.");
				data.setLocation(new MapLocation('F', 10));
				return data;
			case 7:
				data.setName("Vibrant Grotto");
				data.setRequirements("The Power of Enhancement (Global) and The Merchant Flees (Global) Complete");
				data.setGoal("Loot all treasure tiles");
				data.setLocation(new MapLocation('C', 12));
				return data;
			case 8:
				data.setName("Gloomhaven Warehouse");
				data.setRequirements("Jekserah's Plans (Party) Complete and The Dead Invade (Global) Incomplete");
				data.setGoal("Kill both Inox Bodyguards");
				data.setLocation(new MapLocation('C', 18));
				return data;
			case 9:
				data.setName("Diamond Mine");
				data.setRequirements("The Merchant Flees (Global) Incomplete");
				data.setGoal("Kill the Merciless Overseer and loot the treasure tile.");
				data.setLocation(new MapLocation('L', 2));
				return data;
			case 10:
				data.setName("Plane of Elemental Power");
				data.setRequirements("The Rift Neutralized (Global) Incomplete");
				data.setGoal("Kill all enemies");
				data.setLocation(new MapLocation('C', 7));
				return data;
		}
		
		System.out.println("Error with scenario loader with scenario with ID: "+id);
		return data;
	}
}
