package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import Gloomhaven.Characters.Player;

public class Shop {
	
	ImageIcon shopImage=null;
	List<Item> supply = new ArrayList<Item>();
	List<Item> randomItemDesign = new ArrayList<Item>();
	
	int currentParty=0;
	int maxPlayers=0;
	public Shop(int prosperityLevel) {
		
		shopImage = new ImageIcon("src/Gloomhaven/img/shop.png");
		if(prosperityLevel==1)
			supply=ItemLoader.loadAllLevel1Items();
		
		randomItemDesign=ItemLoader.loadAllRandomItemDesign();
		
		currentParty=0;
	}
	
	public void addItemToSupply(Item item) {
		supply.add(item);
	}
	
	public void drawShop(Graphics g, List<Player> party, Point mouseClick) {
		GUI.drawShop(g, shopImage, party, supply, mouseClick);

	}
	
	public int getCurrentParty() {return currentParty;}
	public boolean atLastPartyMember() {
		currentParty++;
		if(currentParty==maxPlayers) {
			resetCurrentPartyCount();
			return true;
		}
		
		return false;
	}
	
	public void setMaxPlayers(int maxPlayers) {this.maxPlayers=maxPlayers;}
	
	public void resetCurrentPartyCount() {currentParty=0;}
	
	public boolean buyItem(List<Player> party, int selectionIndex) {
		if(party.get(currentParty).getCharacterData().getGold()>=supply.get(selectionIndex).getGold()) {
			party.get(currentParty).getItems().add(supply.get(selectionIndex));
			party.get(currentParty).getCharacterData().setGold(party.get(currentParty).getCharacterData().getGold()-supply.get(selectionIndex).getGold());
			supply.remove(selectionIndex);
			return true;
		}else {
			System.out.println("Not enough gold");
			return false;
		}
	}
	
	public void retrieveRandomItemDesign(Player player) {
		if(randomItemDesign.size()>0) {
			Random random = new Random();
			int index=random.nextInt(randomItemDesign.size()-1);
			
			player.addItem(randomItemDesign.get(index));
			Item item = randomItemDesign.get(index);
			randomItemDesign.remove(index);
			
			for(int i=0; i<randomItemDesign.size(); i++) {
				if(randomItemDesign.get(i).getID()==item.getID()) {
					supply.add(randomItemDesign.get(i));
					randomItemDesign.remove(i);
				}
			}
		}
	}
}
