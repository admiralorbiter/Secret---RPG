package Unsorted;

import Gloomhaven.Shop;
import Gloomhaven.Characters.Player;

public final class TreasureLoader {
	
	public static void load(int id, Shop shop, Player player) {
		System.out.println("Treasure Loader: Loaded "+id);
		switch(id) {
			case 1:
				shop.retrieveRandomItemDesign(player);
				break;
			case 2:
				player.addItem(ItemLoader.Load(32));
				break;
			case 3:
				player.addItem(ItemLoader.Load(44));
				break;
			case 4:
				player.getCharacterData().changeGold(15);
				player.getStats().addGold(15);
				break;
			case 5:
				player.addItem(ItemLoader.Load(23));
				break;
			case 6:
				player.addItem(ItemLoader.Load(39));
				break;
			case 7:
				//need to add
				break;
			case 8:
				player.changeBattleGoalTotal(1);
				break;
			case 9:
				player.addItem(ItemLoader.Load(111));
				break;
			case 10:
				player.increaseXP(10);
				break;
			case 11:
				shop.retrieveRandomItemDesign(player);
				break;
			case 12:
				player.addItem(ItemLoader.Load(99));
				break;
			case 13:
				player.takeDamage(5);
				player.getNegativeConditions().setPoison(true);
				player.getNegativeConditions().setWound(true);
				break;
			case 14:
				player.increaseXP(10);
				break;
			case 15:
				player.addItem(ItemLoader.Load(45));
				break;
			case 16:
				player.getCharacterData().changeGold(10);
				player.getStats().addGold(10);
				break;
			case 17:
				player.getCharacterData().changeGold(20);
				player.getStats().addGold(20);
				break;
			case 18:
				player.getCharacterData().changeGold(15);
				player.getStats().addGold(15);
				break;
			case 19:
				shop.retrieveRandomItemDesign(player);
				break;
			case 20:
				shop.retrieveRandomItemDesign(player);
				break;
			case 21:
				player.takeDamage(5);
				break;
			case 22:
				shop.retrieveRandomItemDesign(player);
				break;
			case 23:
				player.addItem(ItemLoader.Load(103));
				break;
			case 24:
				player.takeDamage(5);
				break;
			case 25:
				player.getCharacterData().changeGold(25);
				player.getStats().addGold(25);
				break;
			case 26:
				player.getCharacterData().changeGold(20);
				player.getStats().addGold(20);
				break;
			case 27:
				player.addItem(ItemLoader.Load(122));
				break;
			case 28:
				player.getCharacterData().changeGold(15);
				player.getStats().addGold(15);
				break;
			case 29:
				player.addItem(ItemLoader.Load(97));
				break;
			case 30:
				player.changeBattleGoalTotal(1);
				break;
			case 31:
				shop.retrieveRandomItemDesign(player);
				break;
			case 32:
				shop.retrieveRandomItemDesign(player);
				break;
			case 33:
				player.addItem(ItemLoader.Load(19));
				break;
			case 34:
				player.addItem(ItemLoader.Load(24));
				break;
			case 35:
				player.addItem(ItemLoader.Load(98));
				break;
			case 36:
				player.addItem(ItemLoader.Load(96));
				shop.addItemToSupply(ItemLoader.Load(96));
				break;
			case 37:
				player.changeBattleGoalTotal(1);
				break;
			case 38:
				player.addItem(ItemLoader.Load(123));
				shop.addItemToSupply(ItemLoader.Load(123));
				break;
			case 39:
				player.takeDamage(5);
				break;
			case 40:
				shop.retrieveRandomItemDesign(player);
				break;
			case 41:
				player.addItem(ItemLoader.Load(53));
				break;
			case 42:
				shop.retrieveRandomItemDesign(player);
				break;
			case 43:
				player.takeDamage(5);
				player.getNegativeConditions().setPoison(true);
				player.getNegativeConditions().setWound(true);
				break;
			case 44:
				//need to do
				break;
			case 45:
				shop.retrieveRandomItemDesign(player);
				break;
			case 46:
				player.takeDamage(3);
				player.getNegativeConditions().setPoison(true);
				break;
			case 47:
				player.addItem(ItemLoader.Load(104));
				break;
			case 48:
				player.getCharacterData().changeGold(30);
				player.getStats().addGold(30);
				break;
			case 49:
				//need to do
				break;
			case 50:
				player.addItem(ItemLoader.Load(101));
				break;
			case 51:
				//need to do
				break;
			case 52:
				player.addItem(ItemLoader.Load(131));
				break;
			case 53:
				shop.retrieveRandomItemDesign(player);
				break;
			case 54:
				player.addItem(ItemLoader.Load(124));
				break;
			case 55:
				player.changeBattleGoalTotal(1);
				break;
			case 56:
				player.addItem(ItemLoader.Load(69));
				break;
			case 57:
				player.getCharacterData().changeGold(15);
				player.getStats().addGold(15);
				break;
			case 58:
				player.addItem(ItemLoader.Load(108));
				break;
			case 59:
				shop.retrieveRandomItemDesign(player);
				break;
			case 60:
				player.addItem(ItemLoader.Load(113));
				break;
			case 61:
				player.addItem(ItemLoader.Load(40));
				break;
			case 62:
				player.addItem(ItemLoader.Load(110));
				player.addItem(ItemLoader.Load(115));
				break;
			case 63:
				shop.retrieveRandomItemDesign(player);
				break;
			case 64:
				player.getCharacterData().changeGold(30);
				player.getStats().addGold(30);
				break;
			case 65:
				player.addItem(ItemLoader.Load(107));
				shop.addItemToSupply(ItemLoader.Load(107));
				break;
			case 66:
				player.addItem(ItemLoader.Load(33));
				break;
			case 67:
				player.getCharacterData().changeGold(10);
				player.getStats().addGold(10);
				break;
			case 68:
				player.addItem(ItemLoader.Load(130));
				break;
			case 69:
				player.addItem(ItemLoader.Load(100));
				break;
			case 70:
				//TODO:
				break;
			case 71:
				//TODO:
				break;
			case 72:
				player.addItem(ItemLoader.Load(116));
				shop.addItemToSupply(ItemLoader.Load(116));
				break;
			case 73:
				shop.retrieveRandomItemDesign(player);
				break;
			case 74:
				shop.retrieveRandomItemDesign(player);
				break;
			case 75:
				//TODO:
				break;
		}
	}
}
