package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import Gloomhaven.Characters.Player;
import Unsorted.GUI;
import Unsorted.Item;
import Unsorted.ItemLoader;
import Utility.MatrixSelection;

/**
 * Provides the framework for the shop and buying items in the town
 * @author admir
 */
public class Shop implements Serializable{
	
	private ImageIcon shopImage=null;											//Shop Image
	private List<Item> supply = new ArrayList<>();								//Item Supply
	private List<Item> randomItemDesign = new ArrayList<>();					//Random Item Designs

	/**
	 * Creates the shop, loads the image and all the items based on prosperity level
	 */
	public Shop(int prosperityLevel) {
		
		shopImage = new ImageIcon("src/Gloomhaven/img/shop.png");				//Loads shop image
		randomItemDesign=ItemLoader.loadAllRandomItemDesign();					//Loads all the random item design items							
		switch(prosperityLevel) {												//Loads items based on prosperity level
			case 1:
				supply=ItemLoader.loadAllLevel1Items();
				break;
			default:															//Default should be highest prosperity level
				supply=ItemLoader.loadAllLevel1Items();							//TODO - Add in rest of prosperity level
		}
	}
	
	/**
	 * Draws the shop background, items and updates the shop with mouse click to select items
	 * @param g				Graphics object
	 * @param player		Player that is shopping
	 * @param mouseClick	Last mouse click position based on pixels
	 */
	public void drawAndUpdateShop(Graphics g, Player player, Point mouseClick) {
		GUI.drawShop(g, shopImage, player);										//Draws shop background
		
		MatrixSelection matrix = new MatrixSelection(900, 800, supply.size());	//Creates a matrix based on the number of items available
		
		List<String> itemText = new ArrayList<>();								
		for(int i=0; i<supply.size(); i++)										//Pulls item text from all the items in the store
			itemText.add(supply.get(i).getName());
		
		matrix.drawSelection(g, supply);										//Draws the items
		int selection = matrix.selectItem(mouseClick);							//Updates the matrix with mouse click and returns selection
		
		if(selection!=-99) {													//If selection is not flagged as inactive, then attempt to buy item
			buyItem(player, supply.get(selection));
		}
	}

	/** Attempts to buy item */
	private boolean buyItem(Player player, Item item) {
		
		int playerGold=player.getCharacterData().getGold();
		
		if(playerGold>=item.getGold()) {										//Checks if player has enough gold
			player.getItems().add(item);										//Adds item to player
			player.getCharacterData().setGold(playerGold-item.getGold());		//Removes gold
			supply.remove(item);												//Removes item from supply
			return true;														//Flags item has been bought
		}else {
			System.out.println("Not enough gold");
			return false;														//Flags that the item has not been bought
		}
	}
	
	/**
	 * Draws a random item from the random design item pile and adds the rest to the shop
	 * @param Player 	
	 */
	public void retrieveRandomItemDesign(Player player) {
		
		if(!randomItemDesign.isEmpty()) {										//Checks that randomItem Design isn't empty
			Random random = new Random();
			int index=random.nextInt(randomItemDesign.size()-1);				//Pulls a random item based on random number
			player.addItem(randomItemDesign.get(index));						//Player adds item to their items
			Item item = randomItemDesign.get(index);							//Makes a copy of that item in order to add them to shop
			randomItemDesign.remove(index);										//Removes the item given to the player
			
			for(int i=0; i<randomItemDesign.size(); i++) {						//Searches the rest of the items for copies
				if(randomItemDesign.get(i).getID()==item.getID()) {				//If item has correct id
					supply.add(randomItemDesign.get(i));						//Item is added to the shop supply
					randomItemDesign.remove(i);									//Item is then removed from the random item design list
				}
			}
		}
	}
	
	public void addItemToSupply(Item item) {supply.add(item);}
}
