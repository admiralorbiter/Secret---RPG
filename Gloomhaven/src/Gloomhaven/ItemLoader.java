package Gloomhaven;

import java.util.ArrayList;
import java.util.List;


public final class ItemLoader {
	public static Item Load(int id) {
		
		Item item = null;
		
		switch(id) {
			case 1:
				item = new Item(id, 20, "Boots of Striding", "Boots", "spent", "during_turn", 1);
				item.inventoryTracker(2);
				break;
			case 2:
				item = new Item(id, 20, "Winged Shoes", "Boots", "spent", "during_turn", 1);
				item.inventoryTracker(2);
				break;
			case 3:
				item = new Item(id, 10, "Hide Armor", "Body", "continuous", "when_attacked", 1);
				item.inventoryTracker(2);
				break;
			case 4:
				item = new Item(id, 20, "Leather Armor", "Body", "spent", "when_attacked", 1);
				item.inventoryTracker(2);
				break;
			case 5:
				item = new Item(id, 20, "Cloak of Invisibility", "Body", "consumed", "during_turn", 1);
				item.inventoryTracker(2);
			case 6:
				item = new Item(id, 30, "Eagle-Eye Goggles", "Head", "spent", "during_turn", 1);
				item.inventoryTracker(2);
				break;
			case 7:
				item=new Item(id, 10, "Iron Helmet", "Head", "continuous", "when_attacked", 1);
				item.inventoryTracker(2);
				break;
			case 8:
				item = new Item(id, 20, "Heater Shield", "One-Hand", "spent", "when_attacked", 1);
				item.inventoryTracker(2);
				break;
			case 9:
				item = new Item(id, 30, "Piercing Bow", "Two-Hands", "consumed", "during_turn", 1);
				item.inventoryTracker(2);
				break;
			case 10:
				item = new Item(id, 30, "War Hammer", "Two-Hands", "consumed", "during_turn", 1);
				item.inventoryTracker(2);
				break;
			case 11:
				item = new Item(id, 20, "Poison Dagger", "One-Hand", "spent", "during_turn", 1);
				item.inventoryTracker(2);
				break;
			case 12:
				item = new Item(id, 10, "Minor Healing Potion", "Small Item", "consumed", "during_turn", 1);
				item.inventoryTracker(4);
				break;
			case 13:
				item = new Item(id, 10, "Minor Stamina Potion", "Small_Item", "consumed", "during_turn", 1);
				item.inventoryTracker(4);
				break;
			case 14:
				item = new Item(id, 10, "Minor Power Potion", "Small_Item", "consumed", "during_turn", 1);
				item.inventoryTracker(4);
				break;
			case 15:
				item = new Item(id, 30, "Boots of Speed", "Boots", "spent", "after_initiative", 2);
				item.inventoryTracker(4);
				break;
			case 16:
				item = new Item(id, 20, "Cloak of Pockets", "Body", "continuous", "continuous", 2);
				item.inventoryTracker(2);
				break;
			case 17:
				item = new Item(id, 45, "Empowering Talisman", "Head", "consumed", "during_turn", 2);
				item.inventoryTracker(2);
				break;
			case 18:
				item = new Item(id, 45, "Battle-Axe", "One-Hand", "consumed", "during_turn", 2);
				item.inventoryTracker(2);
				break;
			case 19:
				item = new Item(id, 20, "Weighted Net", "Two-Hand", "spent", "during_turn", 2);
				item.inventoryTracker(2);
				break;
			case 20:
				item = new Item(id, 10, "Minor Mana Potion", "Small Item", "consumed", "during_turn", 2);
				item.inventoryTracker(4);
				break;
			case 21:
				item = new Item(id, 20, "Stun Powder", "Small Item", "consumed", "during_turn", 2);
				item.inventoryTracker(2);
				break;
			case 22:
				item = new Item(id, 20, "Heavy Greaves", "Boots", "continuous", "continuous", 3);
				item.inventoryTracker(2);
				break;
			case 23:
				item = new Item(id, 20, "Chainmail", "Body", "spent", "when_attacked", 3);
				item.inventoryTracker(2);
				break;
			case 24:
				item = new Item(id, 20, "Amulet of Life", "Head", "spent", "during_turn", 3);
				item.inventoryTracker(2);
				break;
			case 25:
				item = new Item(id, 30, "Jagged Sword", "One-Hand", "spent", "during_turn", 3);
				item.inventoryTracker(2);
				break;
			case 26:
				item = new Item(id, 30, "Long Spear", "Two-Hand", "spent", "during_turn", 3);
				item.inventoryTracker(2);
				break;
			case 27:
				item = new Item(id, 30, "Major Healing Potion", "Small Item", "consumed", "during_turn", 3);
				item.inventoryTracker(4);
				break;
			case 28:
				item = new Item(id, 20, "Moon Earring", "Small Item", "consumed", "during_turn", 3);
				item.inventoryTracker(2);
				break;
			case 29:
				item = new Item(id, 30, "Comfortable Shoes", "Boots", "continuous", "continuous", 4);
				item.inventoryTracker(2);
				break;
			case 30:
				item = new Item(id, 30, "Studded Leather", "Body", "spent", "when_attacked", 4);
				item.inventoryTracker(2);
				break;
		}
		
		return item;
	}
	
	public static void addAttackModifier(Player player, int id) {
		switch(id) {
		case 22:
			player.addAttackModifierCard(new AttackModifierCard(1, -1));
			break;
		case 23:
			player.addAttackModifierCard(new AttackModifierCard(1, -1));
			player.addAttackModifierCard(new AttackModifierCard(1, -1));
			player.addAttackModifierCard(new AttackModifierCard(1, -1));
			break;
			
			
		}
	}
	
	public static int maxUses(int id) {
		
		if(id==23)
			return 3;
		
		return 1;
	}
	
	public static List<Item> testLoadAllItems(){
		
		List<Item> items = new ArrayList<Item>();
		
		for(int i=20; i<=30; i++) {
			if(i==20 || i==27) {
				items.add(Load(i));
				items.get(items.size()-1).setIndexNum(1);
				items.add(Load(i));
				items.get(items.size()-1).setIndexNum(2);
				items.add(Load(i));
				items.get(items.size()-1).setIndexNum(3);
				items.add(Load(i));
				items.get(items.size()-1).setIndexNum(4);
			}else {
				items.add(Load(i));
				items.get(items.size()-1).setIndexNum(1);
				items.add(Load(i));
				items.get(items.size()-1).setIndexNum(2);
			}
		}
		
		return items;
	}
	
	public static List<Item> testLoadItems(){
		List<Item> items = new ArrayList<Item>();
		
		items.add(Load(24));
		items.get(items.size()-1).setIndexNum(1);
		items.add(Load(25));
		items.get(items.size()-1).setIndexNum(1);
		items.add(Load(20));
		items.get(items.size()-1).setIndexNum(2);
		
		return items;
	}
	
	public static void continuousEffects(Player player) {
		List<Item> items = player.getItems();
		for(int i=0; i<items.size(); i++) {
			if(items.get(i).getID()==16) {
				player.setSmallItemTotal(player.getSmallItemTotal()+2);
			}else if(items.get(i).getID()==22) {
				if(!player.getMovementImmunity())
					player.toggleMovementImmunity();
			}else if(items.get(i).getID()==29) {
				player.setBonusMove(1);
			}
		}
	}
	
	public static List<Item> consumedOnTurn(List<Item> items) {
		List<Item> consumedItems = new ArrayList<Item>();
		for(int i=0; i<items.size(); i++) {
			if(items.get(i).getConsumed() && items.get(i).getPlayFlag().equals("during_turn")) {
				consumedItems.add(items.get(i));
			}
		}
		
		return consumedItems;
	}
	
	public static List<Item> onTurn(List<Item> items){
		List<Item> onTurn = new ArrayList<Item>();
		for(int i=0; i<items.size(); i++) {
			if(items.get(i).getPlayFlag().equals("during_turn") && items.get(i).getAvailable()) {
				
				onTurn.add(items.get(i));
			}
		}
		
		return onTurn;
	}
	
	public static void consumeItem(Player player, Item item) {
		

		switch(item.getID()) {
			//Refresh one of your consumed items
			case 17: player.randomlyRestoreConsumedItem();//randomly restore item, though eventually player will need to pick
			break;
			//during single-target melee, turn it into neighbor attack
			case 18: System.out.println(item.getName());
			break;
			//creates element
			case 20: player.setCreateAnyElement(true);;
			break;
			//during attack add stun to a single attack
			//what if the attack has several targets?
			case 21: player.setBonusNegativeConditions("Stun");
			break;
			//heal +5 Self
			case 27: player.heal(5);
			break;
			//Refresh all your spent items
			case 28: System.out.println(item.getName());
			break;
			
			
		}
		player.consumeItem(item);
	}
	
	public static void spendItem(Player player, Item item) {
		
		switch(item.getID()) {
			//range attack add immobbilize to a single attack
			case 19: System.out.println(item.getName());
			break;
			//heal +1 Self
			case 24: player.heal(1);
			break;
			//add wound to a single attack
			case 25: player.setBonusNegativeConditions("Wound");
			break;
			//during a melee attack, turn it into two spaces in a line
			case 26: System.out.println(item.getName());
			break;
		}
		
		player.spendItem(item);
	}
	
}
