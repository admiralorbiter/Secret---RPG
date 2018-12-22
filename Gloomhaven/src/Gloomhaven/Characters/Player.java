package Gloomhaven.Characters;

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
import Gloomhaven.CharacterDataObject;
import Gloomhaven.Hex;
import Gloomhaven.Item;
import Gloomhaven.ItemLoader;
import Gloomhaven.Setting;
import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.CardDataObject.CardDataObject;

public class Player extends character {

	//Player variables
	private List<PlayerAbilityCard> abilityDeck = new ArrayList<PlayerAbilityCard>();                     	//Class Ability Deck
	private AttackModifierDeck attackModifierDeck= new AttackModifierDeck("Standard");                      //Standard Attack Modifier Deck
	private List<PlayerAbilityCard> inPlay = new ArrayList<PlayerAbilityCard>();							//List of cards in play

	private PlayerAbilityCard augment = null;

	private List<String> lootInventory = new ArrayList<String>();
	private List<Item> items = new ArrayList<Item>();
	private List<Item> consumedItems = new ArrayList<Item>();
	private int smallItemTotal;
	
	boolean cardChoice=true;																		//Cardchoice variable used when selecting a first then second card
	int initiative=-1;																				//Initiative value based on cards, deciedes order in the game
	PlayerAbilityCard topCard=null;																//Top ability card choosen, used for the initiative score
	PlayerAbilityCard bottomCard=null;																//Bottom ability card
	PlayerAbilityCard firstCardChoice=null;																		//Card picked first during the turn			
	PlayerAbilityCard secondCardChoice=null;
	
	private int maxHandCount;
	private boolean longRest;
	
	private int displayCard=0;
	private int turnNumber;
	private boolean movementImmunity=false;
	private boolean createAnyElement=false;
	
	Setting setting = new Setting();
	
	public Player(int id, String classID) {
		setRoundBonus(new CardDataObject());
		setID("P"+id);
		setClassID(classID);
		setName("Jon");
		data = new CharacterDataObject(classID);
		
		maxHandCount=setting.getMaxHandCount();
		
		randomlySelectCards();
		/*
		for(int i=0; i<maxHandCount; i++)
			abilityDeck.add(new PlayerAbilityCard(classID, i+1, 1));
		*/
		
		//Sets how many small items you can carry
		if(data.getLevel()%2==0)
			smallItemTotal=data.getLevel()/2;
		else
			smallItemTotal=(data.getLevel()+1)/2;
		
	}
	
	public void randomlySelectCards() {
		int cardIndex[] = new int[maxHandCount];
		
		for(int i=0; i<maxHandCount; i++) {
			boolean finished=false;
			int num;
			do {
				Random r = new Random();
				num = r.nextInt(27)+1;
				finished=true;
				for(int j=0; j<i; j++) {
					if(cardIndex[j]==num)
						finished=false;
				}
			}
			while(!finished);
			cardIndex[i]=num;
			abilityDeck.add(new PlayerAbilityCard(classID, num, 1));
		}
		
		
	}
	
	public int getMaxHandCount() {return maxHandCount;}
	public void setMaxHandCount(int maxHandCount) {this.maxHandCount = maxHandCount;}
	public int getSmallItemTotal() {return smallItemTotal;}
	public void setSmallItemTotal(int smallItemTotal) {this.smallItemTotal = smallItemTotal;}

	//Player Variables
	public PlayerAbilityCard getAugmentCard() {return augment;}
	
	//Item
	public List<Item> getItems(){return items;}

	//On the cutting block
	public void setBonusMove(int bonus) {}
	public void toggleMovementImmunity() {movementImmunity=!movementImmunity;}
	public boolean getMovementImmunity() {return movementImmunity;}
	
	public void reset() {
		initiative=-1;
		firstCardChoice=null;
		secondCardChoice=null;
		cardChoice=true;
		topCard=null;
		bottomCard=null;
		createAnyElement=false;
	}
	
	public void resetCardChoice() {
		firstCardChoice=null;
		secondCardChoice=null;
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
	
	public void addLoot(Hex hex) {
		System.out.println("Player.java Loc 142: Player successfully looted "+hex.getLootID().equals("Gold"));
		
		if(hex.getLootID().equals("Gold"))
			data.setGold(data.getGold()+1);
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
		displayCard=abilityDeck.indexOf(topCard);
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

		endOfRound();
		
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
			/*
			if(card.getData().getRoundBonusFlag() && card.getEffects().getShield()>0) {
				data.setShield(data.getShield()-1);
			}
			
			if(card.getData().getRoundBonusFlag() && card.getEffects().getRetaliate()>0) {
				retaliate= new SimpleCards();
			}*/

			if(card.getData().getConsumeFlag())
				abilityDeck.get(index).setCardInlostPile();
			else
				abilityDeck.get(index).setCardIndiscardPile();
			
			if(secondCardChoice.getFlag().equals("Top") || secondCardChoice.getFlag().equals("AltTop")) {
				card= secondCardChoice.getTopData();
				index =abilityDeck.indexOf(secondCardChoice); //secondCardChoice.getIndex();
			}else if(secondCardChoice.getFlag().equals("Bottom") || secondCardChoice.getFlag().equals("AltBottom")) {
				card= secondCardChoice.getBottomData();
				index = abilityDeck.indexOf(secondCardChoice);
			}
			
			/*
			//This is for brute: shield bash - Not sure it will always hold true
			if(card.getRoundBonusFlag() && card.getShield()>0) {
				data.setShield(data.getShield()-1);
			}
			
			if(card.getRoundBonusFlag() && card.getRetaliate()==true) {
				retaliate= new SimpleCards();
			}
			*/
			
			if(card.getData().getConsumeFlag())
				abilityDeck.get(index).setCardInlostPile();
			else
				abilityDeck.get(index).setCardIndiscardPile();
		}
		secondCardChoice=null;
		firstCardChoice=null;
		longRest=false;
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

	public void graphicsDrawCardsInPlay(Graphics g) {
		g.drawString("Cards in play.", setting.getGraphicsXRight(), setting.getGraphicsYTop());
		for(int i=0; i<inPlay.size(); i++) {
			g.drawString(inPlay.get(i).getName(), setting.getGraphicsXRight(), setting.getGraphicsYTop()+15+15*i);
		}
		
		for(int j=0; j<counterTriggers.size(); j++) {
			g.drawString(counterTriggers.get(j).getEffectFlag()+"  "+counterTriggers.get(j).getTriggerFlag(), setting.getGraphicsXRight(), setting.getGraphicsYTop()+60+15*j);
		}
	}
	
	public void graphicsPlayerInfo(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(setting.getGraphicsXRight(), setting.getGraphicsYMid(), 200, 200);
		g.drawString(name+"  "+getClassID(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+15);
		g.drawString("Level "+data.getLevel(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+30);
		g.drawString("Health "+data.getHealth()+"  XP"+data.getXp()+"  Shield "+data.getShield()+" Ret: Need to fix retatliate showing", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+45);
		if(isAugmented()) {
			g.drawString("Augment Active: ", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+60);
			g.drawString("Need to fix augment", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+75);
		}
		List<String> negativeConditionList = negativeConditions.getList();
		if(negativeConditionList.size()>0) {
			g.drawString("Negative Conditions", setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+90);
			for(int i=0; i<negativeConditionList.size(); i++)
				g.drawString(negativeConditionList.get(i), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+105+15*i);
		}
		g.drawString("Gold: "+data.getGold(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+150);
		
		if(roundBonus!=null)
			if(roundBonus.getNegativeConditions()!=null)
				g.drawString("Bonus Condition on Attack: "+roundBonus.getNegativeConditions().getFlag(), setting.getGraphicsXRight()+10, setting.getGraphicsYMid()+165);
		
		g.setColor(setting.getDefaultColor());
	}
	
	public boolean isAugmented() {
		return UsePlayerAbilityCard.hasAugment(augment);

	}
	
	public void shortRestInfo(Graphics g) {
		g.drawString("Take a short rest. Shuffle in discard pile and randomly discard? y/n", 10, 100);
		showDiscardPile(g);
	}
	
	public void showDiscardPile(Graphics g) {
		g.drawString("Discard Pile:", 10, 115);
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isDiscardFlag())
				g.drawString(i+": "+abilityDeck.get(i).getText(), 10, 130+i*15);
		}
	}
	
	public int discardPileSize() {
		int count=0;
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isDiscardFlag())
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
				if(abilityDeck.get(key).isDiscardFlag()) {
					abilityDeck.get(key).setCardInlostPile();
					cardChoice=!cardChoice;
				}
			}
		}else {
			if(key>=0 && key<=abilityDeck.size()) {
				if(abilityDeck.get(key).isDiscardFlag()) {
					abilityDeck.get(key).setCardInlostPile();
					cardChoice=!cardChoice;
					longRest=false;
				}
			}
		}
	}
	
	private void collectDiscardPile() {
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isDiscardFlag())
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

	public int abilityCardsLeft() {
		int currentAbilityDeckSize=0;
		for(int i=0; i<abilityDeck.size(); i++) {
			if(abilityDeck.get(i).isCardFree())
				currentAbilityDeckSize++;
		}
		return currentAbilityDeckSize;
	}

	public int getAttack(CardDataObject attackCard) {
		System.out.println("Attack Break Down: (Loc: Player.java -getAttack Line843");
		AttackModifierCard card = attackModifierDeck.pickRandomModifierCard();
		System.out.println(attackCard.getData().getAttack()+"  "+card.getPlusAttack()+"  "+card.getMultiplier());
		int damage = (attackCard.getData().getAttack()+card.getPlusAttack())*card.getMultiplier();
		
		if(roundTriggers.size()>0) {
			for(int i=0; i<roundTriggers.size(); i++) {
				if(roundTriggers.get(i).getTriggerFlag().equals("EnemyAlone")){
					damage=damage+roundTriggers.get(i).getBonusData().getAttack();
					data.setXp(data.getXp()+roundTriggers.get(i).getBonusData().getXpOnUse());
				}
			}
		}
				
		return damage;
	}
	
	public void increaseXP(int xpGained) {data.setXp(data.getXp()+xpGained);}
	
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
	
	public void setCreateAnyElement(boolean flag) {createAnyElement=true;}
	public boolean getCreateAnyElement() {return createAnyElement;}
	
	public void recoverLostCards(int number) {
		
		if(number==99) {
			for(int i=0; i<abilityDeck.size(); i++) {
				if(abilityDeck.get(i).isLostFlag())
					abilityDeck.get(i).resetCardFlags();
			}
		}
	}
	
	public void addAttackModifierCard(AttackModifierCard card) {
		attackModifierDeck.addCard(card);
	}
	
}
