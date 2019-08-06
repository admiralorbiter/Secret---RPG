package Unsorted;

import java.awt.Point;

import Gloomhaven.Shop;
import Gloomhaven.Characters.Player;
import Gloomhaven.Hex.Hex;

public final class UtilitiesLoot {
	public static void loot(Hex[][] board, Shop shop, Player player, Point loot) {
		
		if(board[loot.x][loot.y].getLootID().equals("Gold")) {
			player.addLoot(board[(int) loot.getX()][(int) loot.getY()]);
			board[(int) loot.getX()][(int) loot.getY()].removeLoot();
		}
		else {
			player.getStats().addTreasure(board[loot.x][loot.y].getLootID());
			TreasureLoader.load(Integer.parseInt(board[loot.x][loot.y].getLootID()), shop, player);
			board[loot.x][loot.y].removeLoot();
		}
	}
}
