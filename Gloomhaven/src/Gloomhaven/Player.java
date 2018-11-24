package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

	//Player Variables - Get set and don't change
	//[Rem] Will need to implement a system that creates a unique id since it is possible parties
	String id;																						//Unique ID
	String characterClass;																			//Class
	int startingAbilityCardCount;																	//Sets how many ability cards are allowed in the deck ( probably depends on level)
	int maxHealth;																					//Sets what the max health of the player can be
	String name;																						//Name of the character
	Setting setting = new Setting();
	PlayerAbilityCards augment = new PlayerAbilityCards();
	int level;
	int displayCard;
	
	//Player variables
	List<PlayerAbilityCards> abilityDeck = new ArrayList<PlayerAbilityCards>();                     //Class Ability Deck
	AttackModifierDeck attackModifierDeck= new AttackModifierDeck("Standard");                      //Standard Attack Modifier Deck
	List<PlayerAbilityCards> inPlay = new ArrayList<PlayerAbilityCards>();							//List of cards in play
	StatusEffectDataObject effects = new StatusEffectDataObject();                                  //Status Effects and Conditions of the Player
	
	//Card choice variables
	boolean cardChoice=true;																		//Cardchoice variable used when selecting a first then second card
	int initiative=-1;																				//Initiative value based on cards, deciedes order in the game
	PlayerAbilityCards topCard=null;																//Top ability card choosen, used for the initiative score
	PlayerAbilityCards bottomCard=null;																//Bottom ability card
	PlayerAbilityCards firstCardChoice=new PlayerAbilityCards();																			//Card picked first during the turn			
	PlayerAbilityCards secondCardChoice=new PlayerAbilityCards();																			//Card picked second during the turn
	int shield;
	int turnNumber;																					//Turn number that is set when ordering players, what order the player goes in
	private Point coordinates = new Point(0, 0);													//Coordinate point of the player
	Point2D dimensions;																				//dimension of the current room																	
	boolean longRest=false;																			//Indicates if the player is currently taking a long rest
	int health;																						//Current health of the player
	int xp;																							//Current experience of the player
	List<String> lootInventory = new ArrayList<String>();
	int gold;
	//List<PersistanceTriggers> triggers = new ArrayList<PersistanceTriggers>();
	List<Trigger> triggers = new ArrayList<Trigger>();
	SimpleCards retaliate = new SimpleCards();
	public Player(int id, String character) {
		//Set constant variables
		switch(character) {
			default:
				this.id="P"+id;
				this.characterClass=character;
				startingAbilityCardCount=setting.getStartingAbilityCardCount();
				maxHealth=50;
				health=500;
				xp=0;
				shield=0;
				level=1;
				name="Jon";
				gold=0;
				displayCard=0;
		}
	
		//Create ability deck
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new PlayerAbilityCards(1, i+1, character));
		
	}
	
	//Resets card variables at beginning of the round
	public void resetCards(){
		initiative=-1;
		firstCardChoice=null;
		secondCardChoice=null;
		cardChoice=true;
		topCard=null;
		bottomCard=null;
	}
	
	
	
	//Resets card choice during the round for making more choices
	public void resetCardChoice() {
		firstCardChoice=null;
		secondCardChoice=null;
		cardChoice=true;
	}
	
	public PlayerAbilityCards getAugmentCard() {
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
				if(abilityDeck.get(i).cardFree()) {
					abilityDeck.get(i).discardPile();
					return true;
				}
			}
		}else {
			if(key>=0 && key<abilityDeck.size()) {
				if(abilityDeck.get(key).cardFree()) {
					abilityDeck.get(key).discardPile();
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

	public int pickPlayCard(KeyEvent e, int key, Graphics g) {
		showPickedCards(e, g);
		
		if(cardChoice) {
			
			if(key>=1 && key<=8) {
				cardChoice=!cardChoice;
				if(key==1) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useTop();
				}
				else if(key==2) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useBottom();
				}
				else if(key==3) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useTopAlt();
				}
				else if(key==4) {
					firstCardChoice=topCard;
					topCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useBottomAlt();
				}
				else if(key==5) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useTop();
				}
				else if(key==6) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useBottom();
				}
				else if(key==7) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useTopAlt();
				}
				else if(key==8) {
					firstCardChoice=bottomCard;
					bottomCard=null;
					abilityDeck.get(firstCardChoice.getIndex()).useBottomAlt();
				}
				
				setDisplayCard();
				
				return key;
			}		
		}
		else {
			if(topCard==null && (key>=5 && key<=8)) {
				cardChoice=!cardChoice;
				secondCardChoice=bottomCard;
				bottomCard=null;
				if(key==5) {
					abilityDeck.get(secondCardChoice.getIndex()).useTop();
				}
				else if(key==6) {
					abilityDeck.get(secondCardChoice.getIndex()).useBottom();
				}
				else if(key==7) {
					abilityDeck.get(secondCardChoice.getIndex()).useTopAlt();
				}
				else if(key==8) {
					abilityDeck.get(secondCardChoice.getIndex()).useBottomAlt();
				}
				return key;
			}
			
			if(bottomCard==null && (key>=1 && key<=4)) {
				cardChoice=!cardChoice;
				secondCardChoice=topCard;
				topCard=null;
				if(key==1) {
					abilityDeck.get(secondCardChoice.getIndex()).useTop();
				}
				else if(key==2) {
					abilityDeck.get(secondCardChoice.getIndex()).useBottom();
				}
				else if(key==3) {
					abilityDeck.get(secondCardChoice.getIndex()).useTopAlt();
				}
				else if(key==4) {
					abilityDeck.get(secondCardChoice.getIndex()).useBottomAlt();
				}
				return key;
			}
		}	
		return -1;	
	}
	
	public PlayerAbilityCards playCard() {
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
				card=topCard.getTop();
			if(cardFlag==3)
				card=bottomCard.getTop();
				
		}else {
		
		//get the data from the correct card
		if(cardFlag==1)
			card=topCard.getBottom();
		if(cardFlag==3)
			card=bottomCard.getBottom();
		
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
	public PlayerAbilityCards getFirstCardChoice() {return firstCardChoice;}
	public PlayerAbilityCards getSecondCardChoice() {return secondCardChoice;}
	
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
				g.drawString("Choose top card.", 10, 530);
				
				try {
					if(e.getKeyCode()==KeyEvent.VK_SPACE) {
						if(abilityDeck.get(displayCard).cardFree()) {
							topCard=abilityDeck.get(displayCard);
							abilityDeck.get(displayCard).setInPlay();
							initiative=abilityDeck.get(displayCard).getInitiative();
							cardChoice=!cardChoice;
						}
					}
				}catch(NullPointerException ex){ }
				
				//[Test] Picking cards assuming there are 8
				if(key>=0 && key<abilityDeck.size()) {
					if(abilityDeck.get(key).cardFree()) {
						topCard=abilityDeck.get(key);
						abilityDeck.get(key).setInPlay();
						initiative=abilityDeck.get(key).getInitiative();
						cardChoice=!cardChoice;
					}
				}
			}
			else {
				g.drawString("Choose bottom card.", 10, 530);
				
				try {
					if(e.getKeyCode()==KeyEvent.VK_SPACE) {
						if(abilityDeck.get(displayCard).cardFree()) {
							bottomCard=abilityDeck.get(displayCard);
							abilityDeck.get(displayCard).setInPlay();
							cardChoice=!cardChoice;
						}
					}
				}catch(NullPointerException ex){ }
				
				//[Test] Picking cards assuming there are 8
				if(key>=0 && key<abilityDeck.size()) {
					if(abilityDeck.get(key).cardFree()) {
						bottomCard=abilityDeck.get(key);
						abilityDeck.get(key).setInPlay();
						cardChoice=!cardChoice;
					}
				}
				
			}
		}
	}
	
	public void setDisplayCard() {
		displayCard=topCard.getIndex();
	}
	
	public void drawAbilityCards(Graphics g) {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).cardFree())
				g.drawString(i+": "+abilityDeck.get(i).getText(), 10, 545+i*15);
		}
	}
	
	private void showPickedCards(KeyEvent e, Graphics g) {
		int startingY=530;
		int offsetY=15;
		
		try {
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				if(displayCard==topCard.getIndex())
					displayCard=bottomCard.getIndex();
				else
					displayCard=topCard.getIndex();
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				if(displayCard==topCard.getIndex())
					displayCard=bottomCard.getIndex();
				else
					displayCard=topCard.getIndex();
			}
		}catch(NullPointerException ex){ }
		abilityDeck.get(displayCard).showCard(g);
		
		if(topCard!=null) {
			g.drawString("Cards", 10, startingY+offsetY*0);
			g.drawString(topCard.getText(), 10, startingY+offsetY*1);
			g.drawString("1: Top of Card", 10, startingY+offsetY*2);
			g.drawString("2: Bottom of Card", 10, startingY+offsetY*3);
			g.drawString("3: Top Alt - Attack +2", 10, startingY+offsetY*4);
			g.drawString("4: Bottom Alt - Move +2", 10, startingY+offsetY*5);
		}
		if(bottomCard!=null) {
			g.drawString(bottomCard.getText(), 10, startingY+offsetY*6);
			g.drawString("5: Top of Card", 10, startingY+offsetY*7);
			g.drawString("6: Bottom of Card", 10, startingY+offsetY*8);
			g.drawString("7: Top Alt - Attack +2", 10, startingY+offsetY*9);
			g.drawString("8: Bottom Alt - Move +2", 10, startingY+offsetY*10);
		}
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
			if(firstCardChoice.getFlag()==0 || firstCardChoice.getFlag()==2) {
				card= firstCardChoice.getTop();
				index = firstCardChoice.getIndex();
			} else if(firstCardChoice.getFlag()==1 || firstCardChoice.getFlag()==3) {
				card= firstCardChoice.getBottom();
				index = firstCardChoice.getIndex();
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
			}else if(card.lost) {
				abilityDeck.get(index).lostPile();
			}else {
				abilityDeck.get(index).discardPile();
			}
			
			if(secondCardChoice.getFlag()==0 || secondCardChoice.getFlag()==2) {
				card= secondCardChoice.getTop();
				index = secondCardChoice.getIndex();
			}else if(secondCardChoice.getFlag()==1 || secondCardChoice.getFlag()==3) {
				card= secondCardChoice.getBottom();
				index = secondCardChoice.getIndex();
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
			}else if(card.lost) {
				abilityDeck.get(index).lostPile();
			}else {
				abilityDeck.get(index).discardPile();
			}
		}
		secondCardChoice=null;
		firstCardChoice=null;
		longRest=false;
	}
	
	public void setAugment(PlayerAbilityCards card) {
		augment=card;
		inPlay.add(augment);
	}
	
	public void replaceAugmentCard(PlayerAbilityCards abilityCard) {
		abilityDeck.get(augment.getIndex()).lostPile();
		inPlay.remove(augment);
		augment=abilityCard;
		inPlay.add(augment);
	}
	
	public void discardAugmentCard() {
		abilityDeck.get(augment.getIndex()).lostPile();
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
			g.drawString(augment.getTop().getAugmentText(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+75);
		}
		List<String> negativeConditions = effects.getNegativeConditions();
		if(negativeConditions.size()>0) {
			g.drawString("Negative Conditions", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+90);
			for(int i=0; i<negativeConditions.size(); i++)
				g.drawString(negativeConditions.get(i), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+105+15*i);
		}
		g.drawString("Gold: "+gold, setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+150);
		
		
		
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
		return augment.getAugment();

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
			if(abilityDeck.get(i).cardInDiscardPile())
				g.drawString(i+": "+abilityDeck.get(i).getText(), 10, 130+i*15);
		}
	}
	
	public int discardPileSize() {
		int count=0;
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).cardInDiscardPile())
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
		 if(abilityDeck.get(pick).cardFree()) {
			 abilityDeck.get(pick).lostPile();
			 running=false;
		 }
		}
		while(running);
	}
	
	public void  takeLongRest(Graphics g, int key) {
		showDiscardPile(g);
		if(cardChoice) {
			if(key>=0 && key<=abilityDeck.size()) {
				if(abilityDeck.get(key).cardInDiscardPile()) {
					abilityDeck.get(key).lostPile();
					cardChoice=!cardChoice;
				}
			}
		}else {
			if(key>=0 && key<=abilityDeck.size()) {
				if(abilityDeck.get(key).cardInDiscardPile()) {
					abilityDeck.get(key).lostPile();
					cardChoice=!cardChoice;
					longRest=false;
				}
			}
		}
	}
	
	private void collectDiscardPile() {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).cardInDiscardPile())
				abilityDeck.get(i).takeOutOfDiscard();
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
			if(abilityDeck.get(i).cardFree())
				currentAbilityDeckSize++;
		}
		return currentAbilityDeckSize;
	}
	
	public void movePlayer(Point space) {
		coordinates=space;
	}
	
	//[Test]
	public int testGetTopCardIndex() {return topCard.getIndex();}
	public int testGetBottomCardIndex() {return bottomCard.getIndex();}
	
	public String getID() {return id;}
	public int getAttack(CardDataObject attackCard) {
		AttackModifierCard card = attackModifierDeck.pickRandomModifierCard();
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
	
	//[Test] Print Loot
	public void testPrintLoot() {
		System.out.println("Loost List:");
		for(int i=0; i<lootInventory.size(); i++)
			System.out.println(lootInventory.get(i));
	}
}
