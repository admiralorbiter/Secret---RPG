package Unsorted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.BattleGoals.BattleGoalCard;
import Gloomhaven.Characters.Character;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Hex.Draw;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.HexLayout;
import Gloomhaven.Hex.UtilitiesHex;
import Gloomhaven.Scenario.ScenarioData;
import Utility.StringUtilities;

public final class GUI {

	//For testing purposes only
	public static void drawLines(Graphics g) {
		int lineLength=GUISettings.lineLength;

		for(int x=0; x<GUISettings.width; x=x+lineLength) {
			g.setColor(Setting.lineColor);
			g.drawLine(x, 0, x, GUISettings.height);
			g.setColor(Setting.defaultColor);
			g.drawString(Integer.toString(x), x, 0);
			g.drawString(Integer.toString(x), x, GUISettings.height-25);
		}
		
		for(int y=0; y<GUISettings.height; y=y+lineLength) {
			g.setColor(Setting.lineColor);
			g.drawLine(0, y, GUISettings.width, y);
			g.setColor(Setting.defaultColor);
			g.drawString(Integer.toString(y), 0, y);
			g.drawString(Integer.toString(y), GUISettings.width-25, y);
		}

	}

	public static void chooseDiscard(Graphics g) {
		g.setFont(FontSettings.body);
		g.drawString("Pick card to discard.", GUISettings.gLeft, GUISettings.gTop);
	}
	
	//Ability Card Selection
	public static void abilityCardTitle(Graphics g, int discardPileSize) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GUISettings.width, GUISettings.height);
		g.setColor(new Color(242, 224, 199));
		g.fillRect(GUISettings.gLeft+25, GUISettings.gYQ1-GUISettings.leadingBigText-50, GUISettings.width, GUISettings.leadingBigBody*(Setting.getMaxHandCount()+3)*2);
		g.setColor(new Color(245, 239, 230));
		g.fillRect(GUISettings.gLeft-5, GUISettings.gYQ1-GUISettings.leadingBigText, GUISettings.width-50, GUISettings.leadingBigBody*(Setting.getMaxHandCount()+3)*2);
		g.setColor(Color.black);
		g.setFont(FontSettings.hugeText);
		g.drawString("Ability Cards", GUISettings.gLeft, GUISettings.gYQ1);
		g.setFont(FontSettings.abilityCardList);
		if(discardPileSize>1)	
			g.drawString("Take a long rest with "+Setting.restKey, GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody);
	}
	//Ability Card Selection
	public static void chooseTopCard(Graphics g) {
		g.setFont(FontSettings.abilityCardList);
		g.drawString("Choose top card.", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody*2);
	}
	//Ability Card Selection
	public static void chooseBottomCard(Graphics g) {
		g.setFont(FontSettings.abilityCardList);
		g.drawString("Choose bottom card.", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody*2);
	}
	//Ability Card Selection
	public static void drawAbilityCardText(Graphics g, List<PlayerAbilityCard> abilityDeck, int i) {
		g.setColor(Color.black);
		g.setFont(FontSettings.abilityCardList);
		Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		if(checkMousePositionX(mousePosition) && checkMousePositionY(mousePosition, i)) {
				g.setColor(Color.white);
		}
		g.drawString(i+": "+abilityDeck.get(i).getText()[0]+"   "+abilityDeck.get(i).getText()[1], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody*3+i*70);
		g.drawString("   			"+abilityDeck.get(i).getText()[2], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody*4+i*70);
	}
	
	//TODO need to move these into a utility card
	private static boolean checkMousePositionX(Point mousePosition) {return mousePosition.x>= GUISettings.gLeft && mousePosition.x<=GUISettings.width;}
	private static boolean checkMousePositionY(Point mousePosition, int i) {return mousePosition.y>GUISettings.gYQ1+GUISettings.leadingBigBody*3+i*70 && mousePosition.y<=GUISettings.gYQ1+GUISettings.leadingBigBody*5+i*70;}
	public static boolean checkMouseIsOnAbilityCard(Point mousePosition, int i) { return checkMousePositionX(mousePosition) && checkMousePositionY(mousePosition, i);}
	
	
	public static int drawAbilityCardTextTop(Graphics g, PlayerAbilityCard topCard) {
		g.setFont(FontSettings.body);
		int width=1050;
		int row = drawString(g, "Cards", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody, width, GUISettings.leadingBody);
		row+=drawString(g, "Init: "+topCard.getText()[0], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		row+=drawString(g, "1: "+topCard.getText()[1], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		row+=drawString(g, "2: "+topCard.getText()[2], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		row+=drawString(g, "3: Top Alt - Attack +2                4: Bottom Alt - Move +2", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		g.drawLine(GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width/2, GUISettings.gYQ1+GUISettings.leadingBody*(row+1));
		return row;
	}
	
	public static void drawAbilityCardTextBottom(Graphics g, PlayerAbilityCard bottomCard, int row) {
		g.setFont(FontSettings.body);
		int width=1050;
		row=row+3;
		row+=drawString(g, "Bottom Card", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		row+=drawString(g, "5: "+bottomCard.getText()[1], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		row+=drawString(g, "6: "+bottomCard.getText()[2], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
		row+=drawString(g, "7: Top Alt - Attack +2                8: Bottom Alt - Move +2", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBody*(row+1), width, GUISettings.leadingBody);
		row++;
	}
	
	public static int drawString(Graphics g, String string, int x, int y, int width, int leading) {
		List<String> list = StringUtilities.lineBreakBasedOnCharacter(g, width, string);
		
		for(int i=0; i<list.size(); i++)
			g.drawString(list.get(i), x, y+leading*i);
		
		return list.size();
	}
	
	public static void drawItemCardDuringTurn(Graphics g, char[] buttons, List<Item> usableItems, int i) {
		g.setFont(FontSettings.body);
		g.drawString(buttons[i]+": "+usableItems.get(i).getName(), GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*i);
	}
	
	public static void drawCard(Graphics g, ImageIcon image) {
		if(image!=null)
			g.drawImage(image.getImage(), GUISettings.gAbilityCardX, GUISettings.gAbilityCardY, GUISettings.gAbilityCardw, GUISettings.gAbilityCardh, null);
		
		g.setFont(FontSettings.body);
		g.drawString("Press Space to Select this card. ", GUISettings.gAbilityCardX, GUISettings.gAbilityCardY+GUISettings.gAbilityCardh+GUISettings.leadingBody);
	}
	
	/**
	 * Draws the Two Battle Goals
	 * @param g			Graphics Object
	 * @param card1		Battle goal Card1
	 * @param card2		Battle goal Card2
	 */
	public static void drawBattleGoal(Graphics g, BattleGoalCard card1, BattleGoalCard card2) {
		g.setFont(FontSettings.bigText);
		g.drawString("Pick Battle Goal Card", GUISettings.gLeft, GUISettings.gTop);
		g.drawString("1: "+card1.getName()+": "+card1.getText()+"    "+card1.getReward(), GUISettings.gGoalX, GUISettings.gGoalY);
		g.drawString("2: "+card2.getName()+": "+card2.getText()+"    "+card2.getReward(), GUISettings.gGoalX, GUISettings.gGoalY+GUISettings.leadingBigText);
		g.drawImage(new ImageIcon("src/Gloomhaven/img/BattleGoal.png").getImage(), GUISettings.gMidX, GUISettings.gYQ1, GUISettings.eventImageW, GUISettings.eventImageH, null);
	}
	
	public static void drawEnemyAbilityCards(Graphics g, List<EnemyAbilityDeck> enemyDecks, int enemyDeckIndex) {
		g.setFont(FontSettings.body);
		g.drawString("Enemy Ability Card "+enemyDecks.get(enemyDeckIndex).getDeckID(), GUISettings.gEnemyAbilityCardX, GUISettings.gEnemyAbilityCardY);
		g.drawString("Attack: "+enemyDecks.get(enemyDeckIndex).getEnemyAbilityCard().getAttack()+"  Move: "+enemyDecks.get(enemyDeckIndex).getEnemyAbilityCard().getMove()+" Range: "+enemyDecks.get(enemyDeckIndex).getEnemyAbilityCard().getRange(), GUISettings.gEnemyAbilityCardX, GUISettings.gEnemyAbilityCardY+GUISettings.leadingBody);
	}
	
	public static void drawEnemyAttack(Graphics g, Enemy enemy, int damage) {
		g.drawImage(enemy.getImageIcon().getImage(), GUISettings.gLeft, GUISettings.gTop+100, 100, 100, null);
		g.setFont(FontSettings.body);
		g.drawString(enemy.getClassID(), GUISettings.gLeft, GUISettings.gTop+225);
		g.setFont(FontSettings.hugeText);
		g.drawString("Damage: "+damage, GUISettings.gLeft, GUISettings.gTop+300);
	}
	
	public static void drawShortRestInfo(Graphics g, List<PlayerAbilityCard> abilityDeck) {
		g.setFont(FontSettings.body);
		g.drawString("Take a short rest. Shuffle in discard pile and randomly discard? y/n", GUISettings.restX, GUISettings.restY+GUISettings.leadingBigText);
		showDiscardPile(g, abilityDeck);
	}
	
	public static void showDiscardPile(Graphics g, List<PlayerAbilityCard> abilityDeck) {
		g.setFont(FontSettings.body);
		g.drawString("Discard Pile:", GUISettings.gLeft, GUISettings.restY+GUISettings.leadingBigText+GUISettings.leadingBody);
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isDiscardFlag()) {
				g.setFont(FontSettings.bodySmall);
				//g.drawString(i+": "+abilityDeck.get(i).getText()[0]+" "+abilityDeck.get(i).getText()[1]+" "+abilityDeck.get(i).getText()[2], GUISettings.restX, GUISettings.restY+GUISettings.leadingBigText+GUISettings.leadingBody+(i+1)*GUISettings.leadingBody);
				g.drawString(i+": "+abilityDeck.get(i).getText()[0]+"   "+abilityDeck.get(i).getText()[1], GUISettings.restX, GUISettings.restY+GUISettings.leadingBigText+GUISettings.leadingBody+(i+1)*GUISettings.leadingBody*2);
				g.drawString("   			"+abilityDeck.get(i).getText()[2], GUISettings.gLeft, GUISettings.restX+GUISettings.restY+GUISettings.leadingBigText+GUISettings.leadingBody+(i+1)*GUISettings.leadingBody*2+3);
			}
		}
	}
	
	public static void drawEnemyAbilityCard(Graphics g, List<EnemyAbilityCard> abilityDeck, int abilityCardIndex) {
		g.setFont(FontSettings.body);
		g.drawString("Enemy Ability Card", GUISettings.enemyAbilityCardX, GUISettings.enemyAbilityCardY);
		g.drawString("Attack: "+abilityDeck.get(abilityCardIndex).getAttack()+"  Move: "+abilityDeck.get(abilityCardIndex).getMove()+" Range: "+abilityDeck.get(abilityCardIndex).getRange(), GUISettings.enemyAbilityCardX, GUISettings.enemyAbilityCardY+GUISettings.leadingBody);
	}
	
	/**
	 * TODO - Change this function
	 * @param g				Graphics Object
	 * @param drawRow		Row that is being drawn
	 * @param drawCol		Col that is being drawn
	 * @param width			Width of the item box	
	 * @param height		Height of the item Box
	 * @param item			Item on display
	 */
	public static void drawMatrixSelection(Graphics g, int drawRow, int drawCol, int width, int height, Item item) {
		g.setFont(FontSettings.bodySmall);

		//Draws Background for the item
		g.setColor(Color.black);
		g.fillRect(GUISettings.width/2+drawCol*width-5, GUISettings.height/6+15+drawRow*height-5, width+10, height+10);
		
		//Draws Item Name
		g.setColor(Color.WHITE);
		//g.drawString(item.getName(), GUISettings.width/2+drawCol*width, GUISettings.height/6+25+drawRow*height);
		int row=0;
		row+=drawString(g, item.getName(), GUISettings.width/2+drawCol*width, GUISettings.height/6+25+drawRow*height+row*15, width , 15);

		//Goes through and draws each character and splits it that way
		/*
		int charLength=0;
		int rowLength=1;
		int pixelsForEachChar=10;
		for(int j=0; j<item.getText().length(); j++) {
			
			if(j%(width/pixelsForEachChar)==0) {
				rowLength++;
				charLength=0;
			}
			else {
				charLength++;
			}
			char c = item.getText().charAt(j);
			g.drawString(String.valueOf(c), GUISettings.width/2+drawCol*width+charLength*pixelsForEachChar, GUISettings.height/6+20+drawRow*height+rowLength*11);
		}
		rowLength++;
		charLength=1;
		*/
		row+=drawString(g, item.getText(), GUISettings.width/2+drawCol*width, GUISettings.height/6+40+drawRow*height+row*15, width, 15);
		
		//Draws how much it costs
		g.setColor(Color.RED);
		row+=drawString(g, "Gold: "+item.getGold(), GUISettings.width/2+drawCol*width, GUISettings.height/6+40+drawRow*height+row*15, width, 15);
		//g.drawString("Gold: "+item.getGold(), GUISettings.width/2+drawCol*width+charLength*pixelsForEachChar, GUISettings.height/6+20+drawRow*height+rowLength*11);
		g.setColor(Color.WHITE);
	}
	
	/**
	 * Draws the shop image and the items
	 * @param g  			Graphics Object
	 * @param shopImage		Shop Image
	 * @param party			List of players
	 * @param supply		Current Items in Shop
	 * @param mouseClick	Last mouse click
	 */
	public static void drawShop(Graphics g, ImageIcon shopImage, Player player) {
		if(shopImage!=null)
			g.drawImage(shopImage.getImage(), 50, 50, GUISettings.width-200, GUISettings.height-200, null);	
		
		g.setColor(new Color(227, 224, 188));													
		g.fillRect(GUISettings.width/2-10, GUISettings.height/6, 900+20, 800);		//Draws black filled in rect that is the background for the items
		g.setColor(Color.white);
		
		g.setColor(Color.BLACK);
		g.fillRect(75, GUISettings.height-350, 300, 150);						//Draws black filled in rect that is the background for the gold
		g.setColor(Color.WHITE);
		
		g.setFont(FontSettings.bigText);
		g.drawRect(90, GUISettings.height-345, 250, 100);						//Draws current player's name and gold
		g.setColor(Color.WHITE);
		g.drawString(player.getName(),  100, GUISettings.height-325);
		g.drawString("Available Gold: "+player.getCharacterData().getGold(), 100, GUISettings.height-300);
		
	}
	
	public static void drawCharacterInfo(Graphics g, boolean flatlayout, Character character) {
		HexCoordinate hex;
		HexLayout hl;
		
		if(flatlayout) {
			hex = UtilitiesHex.flatOffsetToCube(1, character.getCoordinates());
			hl = new HexLayout(UtilitiesHex.getFlatLayoutOrientation(), new Point(Setting.size, Setting.size), Setting.center);
		}
		else {
			hex = UtilitiesHex.pointyOffsetToCube(1, character.getCoordinates());
			hl = new HexLayout(UtilitiesHex.getPointyLayoutOrientation(), new Point(Setting.size, Setting.size), Setting.center);
		}
		Point2D p = UtilitiesHex.hexToPixel(hl, character.getCubeCoordiantes(flatlayout));
		if(character.getCharacterData().getHealth()<=2)
			g.setColor(Color.RED);
		else
			g.setColor(Color.GREEN);
		
		g.drawString(""+character.getCharacterData().getHealth(), (int)p.getX()-10, (int)p.getY()-20);
	}

}
