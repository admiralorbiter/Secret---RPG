package Unsorted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.BattleGoals.BattleGoalCard;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Hex.Draw;
import Gloomhaven.Scenario.ScenarioData;

public final class GUI {

	
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
	
	public static void drawBoardRectangle(Graphics g, ScenarioData data) {
		//g.drawRect(Setting.center.x-data.getBoardSize().x*Setting.size/2, Setting.center.y-data.getBoardSize().y*Setting.size/2, data.getBoardSize().x*Setting.size, data.getBoardSize().y*Setting.size);
		//if(Setting.test)
			g.drawRect(Setting.center.x-Setting.size, Setting.center.y-Setting.size, (data.getBoardSize().x-2)*Setting.size*2, (data.getBoardSize().y-2)*Setting.size*2+Setting.size);
	}
	
	public static void chooseDiscard(Graphics g) {
		g.setFont(FontSettings.body);
		g.drawString("Pick card to discard.", GUISettings.gLeft, GUISettings.gTop);
	}
	
	//Ability Card Selection
	public static void abilityCardTitle(Graphics g) {
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
		g.drawString("Cards Left", GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody);
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
		g.drawString(i+": "+abilityDeck.get(i).getText()[0]+"   "+abilityDeck.get(i).getText()[1], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody*3+i*70);
		g.drawString("   			"+abilityDeck.get(i).getText()[2], GUISettings.gLeft, GUISettings.gYQ1+GUISettings.leadingBigBody*4+i*70);
	}
	
	public static void drawAbilityCardTextTop(Graphics g, PlayerAbilityCard topCard) {
		g.setFont(FontSettings.body);
		g.drawString("Cards", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*0);
		g.drawString("Init: "+topCard.getText()[0], GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*1);
		g.drawString("1: Top of Card: "+topCard.getText()[1], GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*2);
		g.drawString("2: Bottom of Card "+topCard.getText()[2], GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*3);
		g.drawString("3: Top Alt - Attack +2", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*4);
		g.drawString("4: Bottom Alt - Move +2", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*5);
	}
	
	public static void drawAbilityCardTextBottom(Graphics g, PlayerAbilityCard bottomCard) {
		g.setFont(FontSettings.body);
		//g.drawString(bottomCard.getText()[0], 10, startingY+offsetY*6);
		g.drawString("5: Top of Card "+bottomCard.getText()[1], GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*7);
		g.drawString("6: Bottom of Card "+bottomCard.getText()[2], GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*8);
		g.drawString("7: Top Alt - Attack +2", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*9);
		g.drawString("8: Bottom Alt - Move +2", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*10);
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
	
	public static void drawBattleGoal(Graphics g, List<BattleGoalCard> deck, int index1, int index2) {
		g.setFont(FontSettings.bigText);
		g.drawString("Pick Battle Goal Card", GUISettings.gLeft, GUISettings.gTop);
		g.drawString("1: "+deck.get(index1).getName()+": "+deck.get(index1).getText()+"    "+deck.get(index1).getReward(), GUISettings.gGoalX, GUISettings.gGoalY);
		g.drawString("2: "+deck.get(index2).getName()+": "+deck.get(index2).getText()+"    "+deck.get(index2).getReward(), GUISettings.gGoalX, GUISettings.gGoalY+GUISettings.leadingBigText);
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
		g.fillRect(GUISettings.width/2+drawCol*width, GUISettings.height/6+15+drawRow*height, width-5, height-5);
		
		//Draws Item Name
		g.setColor(Color.WHITE);
		g.drawString(item.getName(), GUISettings.width/2+drawCol*width, GUISettings.height/6+25+drawRow*height);

		//Goes through and draws each character and splits it that way
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
		
		//Draws how much it costs
		g.setColor(Color.RED);
		g.drawString("Gold: "+item.getGold(), GUISettings.width/2+drawCol*width+charLength*pixelsForEachChar, GUISettings.height/6+20+drawRow*height+rowLength*11);
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
		
		g.setColor(Color.black);													
		g.fillRect(GUISettings.width/2, GUISettings.height/6, 650, 650);		//Draws black filled in rect that is the background for the items
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

}
