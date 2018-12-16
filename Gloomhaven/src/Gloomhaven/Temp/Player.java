package Gloomhaven.Temp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.AttackModifierCard;
import Gloomhaven.AttackModifierDeck;
import Gloomhaven.CardDataObject;
import Gloomhaven.Hex;
import Gloomhaven.Item;
import Gloomhaven.ItemLoader;
import Gloomhaven.Setting;
import Gloomhaven.SimpleCards;
import Gloomhaven.StatusEffectDataObject;
import Gloomhaven.Trigger;
import Gloomhaven.UsePlayerAbilityCard;
import Gloomhaven.AbilityCards.PlayerAbilityCard;

public class Player {

	//Player Variables - Get set and don't change
	//[Rem] Will need to implement a system that creates a unique id since it is possible parties
	String id;																						//Unique ID
	String characterClass;																			//Class
	int startingAbilityCardCount;																	//Sets how many ability cards are allowed in the deck ( probably depends on level)
	int maxHealth;																					//Sets what the max health of the player can be
	String name;																						//Name of the character
	Setting setting = new Setting();
	PlayerAbilityCard augment = null;
	int level;
	int displayCard;
	
	//Player variables
	List<PlayerAbilityCard> abilityDeck = new ArrayList<PlayerAbilityCard>();                     	//Class Ability Deck
	AttackModifierDeck attackModifierDeck= new AttackModifierDeck("Standard");                      //Standard Attack Modifier Deck
	List<PlayerAbilityCard> inPlay = new ArrayList<PlayerAbilityCard>();							//List of cards in play
	StatusEffectDataObject effects = new StatusEffectDataObject();                                  //Status Effects and Conditions of the Player
	
	//Card choice variables
	boolean cardChoice=true;																		//Cardchoice variable used when selecting a first then second card
	int initiative=-1;																				//Initiative value based on cards, deciedes order in the game
	PlayerAbilityCard topCard=null;																//Top ability card choosen, used for the initiative score
	PlayerAbilityCard bottomCard=null;																//Bottom ability card
	PlayerAbilityCard firstCardChoice=null;//new PlayerAbilityCard();																			//Card picked first during the turn			
	PlayerAbilityCard secondCardChoice=null;//new PlayerAbilityCard();																			//Card picked second during the turn
	int shield;
	int turnNumber;																					//Turn number that is set when ordering players, what order the player goes in
	private Point coordinates = new Point(0, 0);													//Coordinate point of the player
	Point2D dimensions;																				//dimension of the current room																	
	boolean longRest=false;																			//Indicates if the player is currently taking a long rest
	int health;																						//Current health of the player
	int xp;																							//Current experience of the player
	List<String> lootInventory = new ArrayList<String>();
	int gold;
	List<Item> items = new ArrayList<Item>();
	List<Item> consumedItems = new ArrayList<Item>();
	int smallItemTotal;
	int smallItemCount;
	boolean movementImmunity=false;
	int bonusMove=0;
	boolean createAnyElement=false;
	CardDataObject negativeConditions=null;
	
	//List<PersistanceTriggers> triggers = new ArrayList<PersistanceTriggers>();
	List<Trigger> triggers = new ArrayList<Trigger>();
	SimpleCards retaliate = new SimpleCards();
	public Player(int id, String character) {
		//Set constant variables
		switch(character) {
			default:
				this.id="P"+id;
				this.characterClass=character;
				startingAbilityCardCount=setting.getMaxHandCount();
				maxHealth=50;
				health=500;
				xp=0;
				shield=0;
				level=1;
				name="Jon";
				gold=0;
				displayCard=0;
				
				if(level%2==0)
					smallItemTotal=level/2;
				else
					smallItemTotal=(level+1)/2;
				
				
				//items = ItemLoader.testLoadAllItems();
				items=ItemLoader.testLoadItems();
				
				/*
				for(int i=0; i<items.size(); i++)
				{
					if(items.get(i).getID()==16)
						smallItemTotal=smallItemTotal+2;
				}*/

		}
	
		//Create ability deck
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new PlayerAbilityCard(character, i+1, 1));
		
	}
	
	public void setBonusMove(int bonus) {bonusMove=bonus;}
	public void toggleMovementImmunity() {movementImmunity=!movementImmunity;}
	public boolean getMovementImmunity() {return movementImmunity;}
	public List<Item> getItems(){return items;}
	public int getSmallItemTotal() {return smallItemTotal;}
	public void setSmallItemTotal(int total) {smallItemTotal=total;}
	//Resets card variables at beginning of the round
	
	
	public void resetCards(){
		initiative=-1;
		firstCardChoice=null;
		secondCardChoice=null;
		cardChoice=true;
		topCard=null;
		bottomCard=null;
		negativeConditions=null;
		createAnyElement=false;
	}
	
	
	
	//Resets card choice during the round for making more choices
	public void resetCardChoice() {
		firstCardChoice=null;
		secondCardChoice=null;
		cardChoice=true;
	}
	
	public PlayerAbilityCard getAugmentCard() {
		return augment;
	}
	
	//Sets the player on a long rest and creates an initiative of 99
	//Used during the beginning of the round, then during their turn they take the long rest
	public void setLongRest() {
		longRest=true;
		initiative=99;
	}
	
	//Choose to discard a card instead of taking damage
	public boolean discardForHealth(int key, Graphics g) {
		g.drawString("Pick card to discard.", 10, 50);
		drawAbilityCards(g);
		int currentAbilityDeckSize=abilityCardsLeft();
		
		if(currentAbilityDeckSize==1) {
			for(int i=0; i<abilityDeck.size(); i++) {
				if(abilityDeck.get(i).isCardFree()) {
					abilityDeck.get(i).setCardIndiscardPile();
					return true;
				}
			}
		}else {
			if(key>=0 && key<abilityDeck.size()) {
				if(abilityDeck.get(key).isCardFree()) {
					abilityDeck.get(key).setCardIndiscardPile();
					return true;
				}
				return false;
			}
		}
		
		return false;
	}
	
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	public List<Point> createTargetList(Hex board[][], int range, String quickID) {
		List<Point> targets = new ArrayList<Point>();
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(coordinates.getX()%2!=0)
							convertedPoint=cubeToCoordOdd(x, y, z);
						else
							convertedPoint=cubeToCoordEven(x, y, z);

						int xToPlot=(int)(convertedPoint.getX()+coordinates.getX());
						int yToPlot=(int) (convertedPoint.getY()+coordinates.getY());

						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY())
								if(board[xToPlot][yToPlot].getQuickID().equals(quickID)){
									targets.add(new Point(xToPlot,yToPlot));
						}

					}
				}
			}
		}
		
		return targets;
	}
		
		
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	private Point cubeToCoordEven(int x, int y, int z ) {
		x=x+(z-(z&1))/2;
		y=z;

		Point point = new Point(x, y);
		return point;
	}
	
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	private Point cubeToCoordOdd(int x, int y, int z) {
		x=x+(z+(z&1))/2;
		y=z;
		
		Point point = new Point(x, y);
		return point;
	}
	
	public void addLoot(Hex hex) {
		if(hex.getLootID().equals("Gold"))
			gold++;
		else
			lootInventory.add(hex.getLootID());
	}

	public int pickPlayCard(KeyEvent e, int key, char k, Graphics g) {
		List<Item> usableItems = ItemLoader.onTurn(getItems());
		char buttons[] = buttons();
		
		showPickedCards(e, g);
		showDuringTurnItemCards(usableItems, g);
		
		//returns 100+item index, then will subtract 100 to get the index
		for(int i=0; i<usableItems.size(); i++) {
			if(i<9) {
				if(buttons[i]==k) {
					return 100+i;
				}
			}
		}
		
		if(cardChoice) {
			
			if(key>=1 && key<=8) {
				cardChoice=!cardChoice;
				if(key==1) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Top");
				}
				else if(key==2) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Bottom");
				}
				else if(key==3) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltTop");
				}
				else if(key==4) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltBottom");
				}
				else if(key==5) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Top");
				}
				else if(key==6) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Bottom");
				}
				else if(key==7) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltTop");
				}
				else if(key==8) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltBottom");
				}
				
				
				return key;
			}		
		}
		else {
			if(topCard==null && (key>=5 && key<=8)) {
				cardChoice=!cardChoice;
				secondCardChoice=bottomCard;
				bottomCard=null;
				if(key==5) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Top");
				}
				else if(key==6) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Bottom");
				}
				else if(key==7) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltTop");
				}
				else if(key==8) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltBottom");
				}
				return key;
			}
			
			if(bottomCard==null && (key>=1 && key<=4)) {
				cardChoice=!cardChoice;
				secondCardChoice=topCard;
				topCard=null;
				if(key==1) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Top");
				}
				else if(key==2) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Bottom");
				}
				else if(key==3) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltTop");
				}
				else if(key==4) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltBottom");
				}
				return key;
			}
		}	
		return -1;	
	}
	
	public PlayerAbilityCard playCard() {
		if(!cardChoice) {
			return firstCardChoice;
		}else {
			return secondCardChoice;
		}
	}
	
	//[Rem] I had to write this way because I was dumb and forgot that either card could be played as the top or bottom after init round
	public CardDataObject getAbilityCardData(String flag, int cardFlag) {
		CardDataObject card = new CardDataObject();
		
		//if(flag.compareTo("Top")==0)
		if(flag=="Top")
		{
			
			//Get the data from the correct card
			if(cardFlag==1)
				card=topCard.getTopData();
			if(cardFlag==3)
				card=bottomCard.getTopData();
				
		}else {
		
		//get the data from the correct card
		if(cardFlag==1)
			card=topCard.getBottomData();
		if(cardFlag==3)
			card=bottomCard.getBottomData();
		
		return card;
		}
		
		return card;
	}
	
	public CardDataObject playAlternative(String flag) {
	
		CardDataObject card = new CardDataObject();
		if(flag=="Top")
		{
			//do the top alt of the ability card
			card.attack=2;
			card.move=0;
			return card;
		}
		
		card.attack=0;
		card.move=2;
		return card;
		
		//Play bottom alt of the ability card
	}
	
	public boolean getCardChoice() {return cardChoice;}
	public PlayerAbilityCard getFirstCardChoice() {return firstCardChoice;}
	public PlayerAbilityCard getSecondCardChoice() {return secondCardChoice;}
	
	//Picks the two cards needed for initiative
	public void pickAbilityCards(KeyEvent e, int key, Graphics g) {
		
		try {
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				displayCard=displayCard-1;
				if(displayCard<0)
					displayCard=abilityDeck.size()-1;
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				displayCard=displayCard+1;
				if(displayCard>=abilityDeck.size())
					displayCard=0;
			}
		}catch(NullPointerException ex){ }
		
		abilityDeck.get(displayCard).showCard(g);
		
		abilityCardsLeft();

		if(abilityDeck.size()>1) {
			if(cardChoice) {
				g.drawString("Choose top card.", 10, setting.getGraphicsYBottom());
				
				try {
					if(e.getKeyCode()==KeyEvent.VK_SPACE) {
						if(abilityDeck.get(displayCard).isCardFree()) {
							topCard=abilityDeck.get(displayCard);
							abilityDeck.get(displayCard).setCardInPlay();
							initiative=abilityDeck.get(displayCard).getInitiative();
							cardChoice=!cardChoice;
							setDisplayCard();
						}
					}
				}catch(NullPointerException ex){ }
				
				//[Test] Picking cards assuming there are 8
				if(key>=0 && key<abilityDeck.size()) {
					if(abilityDeck.get(key).isCardFree()) {
						topCard=abilityDeck.get(key);
						abilityDeck.get(key).setCardInPlay();
						initiative=abilityDeck.get(key).getInitiative();
						cardChoice=!cardChoice;
						setDisplayCard();
					}
				}
				
				
			}
			else {
				g.drawString("Choose bottom card.", 10, setting.getGraphicsYBottom());
				
				try {
					if(e.getKeyCode()==KeyEvent.VK_SPACE) {
						if(abilityDeck.get(displayCard).isCardFree()) {
							bottomCard=abilityDeck.get(displayCard);
							abilityDeck.get(displayCard).setCardInPlay();
							cardChoice=!cardChoice;
						}
					}
				}catch(NullPointerException ex){ }
				
				//[Test] Picking cards assuming there are 8
				if(key>=0 && key<abilityDeck.size()) {
					if(abilityDeck.get(key).isCardFree()) {
						bottomCard=abilityDeck.get(key);
						abilityDeck.get(key).setCardInPlay();
						cardChoice=!cardChoice;
					}
				}
				
			}
		}
	}
	
	public void setDisplayCard() {
		displayCard=abilityDeck.indexOf(topCard); //topCard.getIndex();
	}
	
	public void drawAbilityCards(Graphics g) {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isCardFree()) {
				g.drawString(i+": "+abilityDeck.get(i).getText()[0]+"   "+abilityDeck.get(i).getText()[1], 10, setting.getGraphicsYBottom()+15+i*30);
				g.drawString("   			"+abilityDeck.get(i).getText()[2], 10, setting.getGraphicsYBottom()+30+i*30);
			}
		}
	}
	
	private void showPickedCards(KeyEvent e, Graphics g) {
		int startingY=setting.getGraphicsYBottom();
		int offsetY=15;
		
		try {
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				if(displayCard==abilityDeck.indexOf(topCard))
					displayCard=abilityDeck.indexOf(bottomCard);
				else
					displayCard=abilityDeck.indexOf(topCard);
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				if(displayCard==abilityDeck.indexOf(topCard))
					displayCard=abilityDeck.indexOf(bottomCard);
				else
					displayCard=abilityDeck.indexOf(topCard);
			}
		}catch(NullPointerException ex){ }
		abilityDeck.get(displayCard).showCard(g);
		
		if(topCard!=null) {
			g.drawString("Cards", 10, startingY+offsetY*0);
			g.drawString("Init: "+topCard.getText()[0], 10, startingY+offsetY*1);
			g.drawString("1: Top of Card: "+topCard.getText()[1], 10, startingY+offsetY*2);
			g.drawString("2: Bottom of Card "+topCard.getText()[2], 10, startingY+offsetY*3);
			g.drawString("3: Top Alt - Attack +2", 10, startingY+offsetY*4);
			g.drawString("4: Bottom Alt - Move +2", 10, startingY+offsetY*5);
		}
		if(bottomCard!=null) {
			//g.drawString(bottomCard.getText()[0], 10, startingY+offsetY*6);
			g.drawString("5: Top of Card "+bottomCard.getText()[1], 10, startingY+offsetY*7);
			g.drawString("6: Bottom of Card "+bottomCard.getText()[2], 10, startingY+offsetY*8);
			g.drawString("7: Top Alt - Attack +2", 10, startingY+offsetY*9);
			g.drawString("8: Bottom Alt - Move +2", 10, startingY+offsetY*10);
		}
	}
	
	private void showDuringTurnItemCards(List<Item> usableItems, Graphics g) {
		int startingY=setting.getGraphicsYBottom()+15*13;
		int offsetY=15;
		char buttons[] = buttons();
		
		for(int i=0; i<usableItems.size(); i++) {
			if(i==9)
				break;
			g.drawString(buttons[i]+": "+usableItems.get(i).getName(), 10, startingY+i*offsetY);
		}
	}
	
	private char[] buttons() {
		char buttons[] = new char[10];
		buttons[0]='q';
		buttons[1]='w';
		buttons[2]='e';
		buttons[3]='r';
		buttons[4]='t';
		buttons[5]='y';
		buttons[6]='u';
		buttons[7]='i';
		buttons[8]='o';
		buttons[9]='p';
		
		return buttons;
	}
	
	public void endTurn() {
		
		if(effects.getDisarm()) {
			effects.switchDisarm();
		}
		
		if(effects.getStun()) {
			effects.switchStun();
		}
		
		if(effects.getMuddle()) {
			effects.switchMuddle();
		}
		
		if(effects.getInvisibility()) {
			effects.switchInvisibility();
		}
		
		if(effects.getStrengthen()) {
			effects.switchStrengthen();
		}
		
		if(effects.getImmobilize()) {
			effects.switchImmobilize();
		}
		
		cardChoice=true;
		
		if(longRest==false) {
			CardDataObject card = new CardDataObject();
			int index=-1;
			if(firstCardChoice.getFlag().equals("Top")|| firstCardChoice.getFlag().equals("AltTop")) {
				card= firstCardChoice.getTopData();
				index = abilityDeck.indexOf(firstCardChoice); //firstCardChoice.getIndex();
			} else if(firstCardChoice.getFlag().equals("Bottom") || firstCardChoice.getFlag().equals("AltBottom")) {
				card= firstCardChoice.getBottomData();
				index = abilityDeck.indexOf(firstCardChoice);
			}
			
			//This is for brute: shield bash - Not sure it will always hold true
			if(card.roundBonus && card.shield>0) {
				shield=shield-1;
			}
			
			if(card.roundBonus && card.retaliateFlag==true) {
				retaliate= new SimpleCards();
			}

			//If persistant card trigger is finished, removes from play and removes from trigger deck
			if(triggers.size()>0) {
				for(int i=0; i<triggers.size(); i++) {
					if(triggers.get(i).isFinished()) {
						
						for(int j=0; j<inPlay.size(); j++)
							if(triggers.get(i).getName().equals(inPlay.get(j).getName()))
								inPlay.remove(j);
						triggers.remove(i);
					}
				}
			}
			 
			if(card.continuous) {
				inPlay.add(firstCardChoice);						//Need a way to track if i am using the top or bottom as a cont
			}else if(card.lost){
			}else {
				abilityDeck.get(index).setCardIndiscardPile();
			}
			
			if(secondCardChoice.getFlag().equals("Top") || secondCardChoice.getFlag().equals("AltTop")) {
				card= secondCardChoice.getTopData();
				index =abilityDeck.indexOf(secondCardChoice); //secondCardChoice.getIndex();
			}else if(secondCardChoice.getFlag().equals("Bottom") || secondCardChoice.getFlag().equals("AltBottom")) {
				card= secondCardChoice.getBottomData();
				index = abilityDeck.indexOf(secondCardChoice);
			}
			
			
			//This is for brute: shield bash - Not sure it will always hold true
			if(card.roundBonus && card.shield>0) {
				shield=shield-1;
			}
			
			if(card.roundBonus && card.retaliateFlag==true) {
				retaliate= new SimpleCards();
			}

			if(card.continuous) {
				inPlay.add(secondCardChoice);									//Need a way to track if i am using the top or bottom as a cont
			}else if(card.lost){
			}else {
				abilityDeck.get(index).setCardIndiscardPile();
			}
		}
		secondCardChoice=null;
		firstCardChoice=null;
		longRest=false;
	}
	
	public void transferToLostPile(PlayerAbilityCard card) {
		int index=abilityDeck.indexOf(card);
		abilityDeck.get(index).setCardInlostPile();
	}
	
	public void setAugment(PlayerAbilityCard card) {
		augment=card;
		inPlay.add(augment);
	}
	
	public void replaceAugmentCard(PlayerAbilityCard abilityCard) {
		abilityDeck.get(abilityDeck.indexOf(augment)).setCardInlostPile();
		inPlay.remove(augment);
		augment=abilityCard;
		inPlay.add(augment);
	}
	
	public void discardAugmentCard() {
		abilityDeck.get(abilityDeck.indexOf(augment)).setCardInlostPile();
		augment=null;
	}
	/*
	public void graphicsAugmentCard(Graphics g) {
		g.drawString("Augment Active:", setting.getGraphicsXMid(), setting.getGraphicsYBottom());
		g.drawString(augment.getTop().getAugmentText(), setting.getGraphicsXMid(), setting.getGraphicsYBottom()+15);
	}*/
	
	public void graphicsDrawCardsInPlay(Graphics g) {
		g.drawString("Cards in play.", setting.getGraphicsXRight(), setting.getGraphicsYTop());
		for(int i=0; i<inPlay.size(); i++) {
			g.drawString(inPlay.get(i).getName(), setting.getGraphicsXRight(), setting.getGraphicsYTop()+15+15*i);
		}
	}
	
	public void graphicsPlayerInfo(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid(), 200, 200);
		g.drawString(name+"  "+characterClass, setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+15);
		g.drawString("Level "+level, setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+30);
		g.drawString("Health "+health+"  XP"+xp+"  Shield "+shield+" Ret: "+retaliate.getAttack(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+45);
		if(isAugmented()) {
			g.drawString("Augment Active: ", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+60);
			g.drawString(augment.getTopData().getAugmentText(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+75);
		}
		List<String> negativeConditions = effects.getNegativeConditions();
		if(negativeConditions.size()>0) {
			g.drawString("Negative Conditions", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+90);
			for(int i=0; i<negativeConditions.size(); i++)
				g.drawString(negativeConditions.get(i), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+105+15*i);
		}
		g.drawString("Gold: "+gold, setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+150);
		if(getBonusNegativeConditions()!=null)
			g.drawString("Bonus Condition on Attack: "+getBonusNegativeConditions().getNegativeCondition(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+165);
		
		
		
		/*
		g.drawString("Discard Deck Size and Lost Deck Size (Need to do)", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+60);
		g.drawString("Hand Size", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+75);
		g.drawString("Goal", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+90);
		g.drawString("Scenario Goal", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+105);
		g.drawString("Gold", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+120);
		g.drawString("Items", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+135);*/
		g.setColor(setting.getDefaultColor());
	}
	
	public void setRetaliate(SimpleCards retCard) {
		this.retaliate=retCard;
	}
	
	public boolean isAugmented() {
		return UsePlayerAbilityCard.hasAugment(augment);

	}
	
	public void setShield(int shield) {
		this.shield=shield;
	}
	
	public void heal(int damageToHeal) {
		if(effects.getPoison()) {
			effects.switchPoison();
		}else {
			health=health+damageToHeal;
			if(health>maxHealth) {
				health=maxHealth;
			}
		}
	}
	
	public void shortRestInfo(Graphics g) {
		g.drawString("Take a short rest. Shuffle in discard pile and randomly discard? y/n", 10, 100);
		showDiscardPile(g);
	}
	
	public void showDiscardPile(Graphics g) {
		g.drawString("Discard Pile:", 10, 115);
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).getDiscardFlag())
				g.drawString(i+": "+abilityDeck.get(i).getText(), 10, 130+i*15);
		}
	}
	
	public int discardPileSize() {
		int count=0;
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).getDiscardFlag())
				count++;
		}
		return count;
	}
	
	public void takeShortRest() {
		collectDiscardPile();
		Random rand = new Random();
		boolean running=true;
		do
		{
		 int pick = rand.nextInt(abilityDeck.size());
		 if(abilityDeck.get(pick).isCardFree()) {
			 abilityDeck.get(pick).setCardInlostPile();
			 running=false;
		 }
		}
		while(running);
	}
	
	public void  takeLongRest(Graphics g, int key) {
		showDiscardPile(g);
		if(cardChoice) {
			if(key>=0 && key<=abilityDeck.size()) {
				if(abilityDeck.get(key).getDiscardFlag()) {
					abilityDeck.get(key).setCardInlostPile();
					cardChoice=!cardChoice;
				}
			}
		}else {
			if(key>=0 && key<=abilityDeck.size()) {
				if(abilityDeck.get(key).getDiscardFlag()) {
					abilityDeck.get(key).setCardInlostPile();
					cardChoice=!cardChoice;
					longRest=false;
				}
			}
		}
	}
	
	private void collectDiscardPile() {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).getDiscardFlag())
				abilityDeck.get(i).takeCardOutOfDiscard();
		}
	}
	
	//Returns whether the two cards have been locked for initiative
	public boolean cardsLocked() {
		if(longRest)
			return true;
		
		if(topCard!=null && bottomCard!=null)
			return true;
		else
			return false;
	}
	
	public boolean onRest() {return longRest;}
	
	public int getInitiative() {
		return initiative;
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}
	
	public int getTurnNumber() {return turnNumber;}
	
	public void setPoint(Point point) {
		this.coordinates=new Point(point);
	}
	
	public Point getCoordinate() {return coordinates;}
	
	public void setDimensions(Point2D dimensions) {
		this.dimensions=dimensions;
	}
	public int abilityCardsLeft() {
		int currentAbilityDeckSize=0;
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isCardFree())
				currentAbilityDeckSize++;
		}
		return currentAbilityDeckSize;
	}
	
	public void movePlayer(Point space) {
		coordinates=space;
	}
	
	//[Test]
	public int testGetTopCardIndex() {return abilityDeck.indexOf(topCard);}
	public int testGetBottomCardIndex() {return abilityDeck.indexOf(bottomCard);}
	
	public String getID() {return id;}
	public int getAttack(CardDataObject attackCard) {
		System.out.println("Attack Break Down: (Loc: Player.java -getAttack Line843");
		AttackModifierCard card = attackModifierDeck.pickRandomModifierCard();
		System.out.println(attackCard.attack+"  "+card.plusAttack+"  "+card.multiplier);
		int damage = (attackCard.attack+card.plusAttack)*card.multiplier;
		
		if(triggers.size()>0) {
			for(int i=0; i<triggers.size(); i++) {
				if(triggers.get(i).getTriggerName().equals("OnTargetEnemyAlone")) {
					damage=damage+triggers.get(i).getAloneBonusData().getAttack();
					xp=xp+triggers.get(i).getAloneBonusData().getExperience();
					triggers.get(i).addToTrigger();
				}
			}
		}
		
		return damage;
	}
	public int getHealth() {return health;}
	public void decreaseHealth(int damage) {
		boolean needToReset=false;
		if(triggers.size()>0) {
			for(int i=0; i<triggers.size(); i++) {
				if(triggers.get(i).getTriggerName()=="PlayerTarget") {
					shield=shield+triggers.get(i).getShield();
					needToReset=true;
					triggers.get(i).addToTrigger();
				}
			}
		}
		if(effects.getPoison())
			damage=damage+1;
		
		if(damage>0)
			health=health+shield-damage;
		
		if(needToReset) {
			for(int i=0; i<triggers.size(); i++) {
				if(triggers.get(i).getTriggerName()=="PlayerTarget") {
					shield=shield-triggers.get(i).getShield();
				}
			}
		}
		
		//[Test]
		System.out.println("Player was attacked for "+damage+" making thier health "+health);
	}
	
	public void removePersistanceBonus(int index) {
		//if(triggers.get(index).getName()=="Warding Strength")
			//shield=shield-1;
	}
	
	public void increaseXP(int xpGained) {xp=xp+xpGained;}
	
	public void setCondition(String condition) {
		if(condition=="Invisible" && effects.getInvisibility()!=true)
			effects.switchInvisibility();
		
		if(condition=="Bless" && effects.getBless()!=true)
			effects.switchBless();
		
		if(condition=="Strengthen" && effects.getStrengthen()!=true)
			effects.switchStrengthen();
	}
	
	public void addPersistanceBonus(CardDataObject card) {
		triggers.add(card.getTriggerData());
	}
	
	public void setNegativeCondition(String condition) {
		if(condition=="Wound" && effects.getWound()==false)
			effects.switchWound();
		
		if(condition=="Curse" && effects.getCurse()==false)
			effects.switchCurse();
		
		if(condition=="Disarm" && effects.getDisarm()==false)
			effects.switchDisarm();
		
		if(condition=="Immobilize" && effects.getImmobilize()==false)
			effects.switchImmobilize();
		
		if(condition=="Muddle" && effects.getMuddle()==false)
			effects.switchMuddle();
		
		if(condition=="Poison" && effects.getPoison()==false)
			effects.switchPoison();
		
		if(condition=="Stun" && effects.getStun()==false)
			effects.switchStun();
			
	}
	
	public boolean canAttack() {

		if(effects.getDisarm()) {
			return false;
		}
		else if(effects.getStun()) {
			return false;
		}
		else if(effects.getImmobilize()) {
			return false;
		}
		
		return true;
	}
	
	public boolean canMove() {
		if(effects.getStun())
			return false;
		if(effects.getImmobilize())
			return false;
		
		return true;
	}
	
	public boolean hasRetaliate() {
		if(retaliate.attack>0)
			return true;
		else
			return false;
	}
	
	public SimpleCards getRetaliate() {
		return retaliate;
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public void consumeItem(Item item) {
		consumedItems.add(item);
		items.remove(item);
	}
	
	public void spendItem(Item item) {
		int index=items.indexOf(item);
		items.get(index).use();
		
	}
	
	public List<Item> getConsumedItems(){
		return consumedItems;
	}
	
	public void randomlyRestoreConsumedItem() {
		Random r = new Random();
		if(consumedItems.size()>0) {
			int n = r.nextInt(consumedItems.size());
			items.add(consumedItems.get(n));
			consumedItems.remove(n);
		}
	}
	
	public void setBonusNegativeConditions(String name) {
		negativeConditions = new CardDataObject();
		if(name=="Wound")
			negativeConditions.wound=true;
		else if(name=="Curse")
			negativeConditions.curse=true;
		else if(name=="Disarm")
			negativeConditions.disarm=true;
		else if(name=="Immobilize")
			negativeConditions.immoblize=true;
		else if(name=="Muddle")
			negativeConditions.muddle=true;
		else if(name=="Poison")
			negativeConditions.poison=true;
		else if(name=="Stun")
			negativeConditions.stun=true;
	}
	
	public void resetBonusNegativeConditions() {
		negativeConditions=null;
	}
	
	public CardDataObject getBonusNegativeConditions() {return negativeConditions;}
	
	//[Test] Print Loot
	public void testPrintLoot() {
		System.out.println("Loost List:");
		for(int i=0; i<lootInventory.size(); i++)
			System.out.println(lootInventory.get(i));
	}
	
	public void setCreateAnyElement(boolean flag) {createAnyElement=true;}
	public boolean getCreateAnyElement() {return createAnyElement;}
	
	public void recoverLostCards() {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).getLostFlag())
				abilityDeck.get(i).resetCardFlags();
		}
	}
	
	public void addAttackModifierCard(AttackModifierCard card) {
		attackModifierDeck.addCard(card);
	}
}
