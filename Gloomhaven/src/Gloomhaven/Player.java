package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
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
	int name;																						//Name of the character
	
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
	int firstCardChoice=0;																			//Card picked first during the turn			
	int secondCardChoice=0;																			//Card picked second during the turn
	
	int turnNumber;																					//Turn number that is set when ordering players, what order the player goes in
	private Point coordinates = new Point(0, 0);													//Coordinate point of the player
	Point2D dimensions;																				//dimension of the current room																	
	boolean longRest=false;																			//Indicates if the player is currently taking a long rest
	int health;																						//Current health of the player
	int xp;																							//Current experience of the player
	
	public Player(int id, String character) {
		//Set constant variables
		switch(character) {
			default:
				this.id="P"+id;
				this.characterClass=character;
				startingAbilityCardCount=10;
				int maxHealth=10;
		}
	
		//Create ability deck
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new PlayerAbilityCards(1, i+1, character));
		
	}
	
	//Resets card variables at beginning of the round
	public void resetCards(){
		initiative=-1;
		firstCardChoice=0;
		secondCardChoice=0;
		cardChoice=true;
		topCard=null;
		bottomCard=null;
	}
	
	//Resets card choice during the round for making more choices
	public void resetCardChoice() {
		firstCardChoice=0;
		secondCardChoice=0;
		cardChoice=true;
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
	public List<Point> createTargetList(Hex board[][], int range) {
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
								if(board[xToPlot][yToPlot].getQuickID()=="E"){
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

	public int pickPlayCard(int key, Graphics g) {
		showPickedCards(g);
		if(cardChoice) {
			if(key>=1 && key<=4) {
				cardChoice=!cardChoice;
				firstCardChoice=key;
				return key;
			}		
		}
		else {
			if(key==4 || key==3) {
				if(firstCardChoice==1 || firstCardChoice==2) {
					cardChoice=!cardChoice;
					secondCardChoice=key;
					return key;
				}
			}
			else if(key==1 || key==2) {
				if(firstCardChoice==3 || firstCardChoice==4) {
					cardChoice=!cardChoice;
					secondCardChoice=key;
					return key;
				}
			}
		}

		
		return -1;
		
	}
	
	public CardDataObject playCard() {
		if(!cardChoice) {
			if(firstCardChoice==1) {
				return getAbilityCardData("Top", 1);
			}
			else if(firstCardChoice==2) {
				return playAlternative("Top");
			}
			else if(firstCardChoice==3) {
				return getAbilityCardData("Bottom", 3);
			}
			else if(firstCardChoice==4) {
				return playAlternative("Bottom");
			}
		}else {
			if(secondCardChoice==1) {
				return getAbilityCardData("Top", 1);
			}
			else if(secondCardChoice==2) {
				return playAlternative("Top");
			}
			else if(secondCardChoice==3) {
				return getAbilityCardData("Bottom", 3);
			}
			else if(secondCardChoice==4) {
				return playAlternative("Bottom");
			}
		}
		
		return null;
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
	public int getFirstCardChoice() {return firstCardChoice;}
	public int getSecondCardChoice() {return secondCardChoice;}
	
	//Picks the two cards needed for initiative
	public void pickAbilityCards(int key, Graphics g) {
		
		abilityCardsLeft();
		
		if(abilityDeck.size()>1) {
			if(cardChoice) {
				g.drawString("Choose top card.", 10, 530);
				
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
	
	public void drawAbilityCards(Graphics g) {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).cardFree())
				g.drawString(i+": "+abilityDeck.get(i).getText(), 10, 545+i*15);
		}
	}
	
	private void showPickedCards(Graphics g) {
		int startingY=530;
		int offsetY=15;
		g.drawString("Cards", 10, startingY+offsetY*0);
		g.drawString("1: "+topCard.getText(), 10, startingY+offsetY*1);
		g.drawString("2: Attack +2", 10, startingY+offsetY*2);
		g.drawString("3: "+bottomCard.getText(), 10, startingY+offsetY*3);
		g.drawString("4: Move +2", 10, startingY+offsetY*4);
	}
	
	public void endTurn() {
		
		cardChoice=true;
		secondCardChoice=0;
		firstCardChoice=0;
		if(longRest==false) {
			CardDataObject card= topCard.getTop();
			int index = topCard.getIndex();
			
			if(card.continuous) {
				inPlay.add(topCard);									//Need a way to track if i am using the top or bottom as a cont
			}else if(card.lost) {
				abilityDeck.get(index).lostPile();
			}else {
				abilityDeck.get(index).discardPile();
			}
			
			card= bottomCard.getBottom();
			index = bottomCard.getIndex();
			
			if(card.continuous) {
				inPlay.add(bottomCard);									//Need a way to track if i am using the top or bottom as a cont
			}else if(card.lost) {
				abilityDeck.get(index).lostPile();
			}else {
				abilityDeck.get(index).discardPile();
			}
		}
		longRest=false;
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
		coordinates=new Point(space);
	}
	
	//[Test]
	public int testGetTopCardIndex() {return topCard.getIndex();}
	public int testGetBottomCardIndex() {return bottomCard.getIndex();}
	
	public String getID() {return id;}
	public int getAttack(CardDataObject attackCard) {
		AttackModifierCard card = attackModifierDeck.pickRandomModifierCard();
		int damage = (attackCard.attack+card.plusAttack)*card.multiplier;
		
		return damage;
	}
	public int getHealth() {return health;}
	public void decreaseHealth(int damage) {
		if(damage>0)
			health=health-damage;
		
		//[Test]
		System.out.println("Player was attacked for "+damage+" making thier health "+health);
	}
}
