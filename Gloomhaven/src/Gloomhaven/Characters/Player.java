package Gloomhaven.Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import Gloomhaven.AbilityCards.AbilityCard;
import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;
import Gloomhaven.AttackModifier.AttackModifierDeck;
import Gloomhaven.BattleGoals.BattleGoalCard;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Hex.HexCoordinate;
import Unsorted.CharacterDataObject;
import Unsorted.FontSettings;
import Unsorted.GUI;
import Unsorted.GUIScenario;
import Unsorted.GUISettings;
import Unsorted.Item;
import Unsorted.ItemLoader;
import Unsorted.Setting;
import Unsorted.StatsTracker;

public class Player extends Character {

	// Player variables
	private List<PlayerAbilityCard> abilityDeck = new ArrayList<PlayerAbilityCard>(); // Class Ability Deck
	private AttackModifierDeck attackModifierDeck = new AttackModifierDeck("Standard"); // Standard Attack Modifier Deck
	private List<PlayerAbilityCard> inPlay = new ArrayList<PlayerAbilityCard>(); // List of cards in play

	private CardDataObject augment = null;

	private List<String> lootInventory = new ArrayList<String>();
	private List<Item> items = new ArrayList<Item>();
	private List<Item> consumedItems = new ArrayList<Item>();
	private int smallItemTotal;

	boolean cardChoice = true; // Cardchoice variable used when selecting a first then second card
	int initiative = -1; // Initiative value based on cards, deciedes order in the game
	PlayerAbilityCard topCard = null; // Top ability card choosen, used for the initiative score
	PlayerAbilityCard bottomCard = null; // Bottom ability card
	PlayerAbilityCard firstCardChoice = null; // Card picked first during the turn
	PlayerAbilityCard secondCardChoice = null;

	private int maxHandCount;
	private boolean longRest;
	private boolean longRestFlag;

	private int turnNumber;
	private boolean movementImmunity = false;
	private boolean createAnyElement = false;

	private StatsTracker stats = new StatsTracker();

	private BattleGoalCard battleGoal = null;
	private int currentBattleGoalCount = 0;

	private boolean exhausted=false;
	
	public int getHandAndDiscardSize() {
		int count = 0;
		for (int i = 0; i < abilityDeck.size(); i++) {
			if (!abilityDeck.get(i).isLostFlag())
				count++;
		}

		return count;
	}

	public Player(int id, String classID) {
		setRoundBonus(new CardDataObject());
		setID("P" + id);
		setClassID(classID);
		setName("Jon");
		setImage(new ImageIcon("src/Gloomhaven/img/MindthiefIcon.png"));
		data = new CharacterDataObject(classID, false);

		maxHandCount = Setting.getMaxHandCount();

		// items = ItemLoader.testLoadItems();

		for (int i = 0; i < items.size(); i++)
			ItemLoader.addAttackModifier(attackModifierDeck, items.get(i).getID());

		if (classID.equals("Spellweaver"))
			randomlySelectCards();
		else {
			for (int i = 0; i < maxHandCount; i++)
				abilityDeck.add(new PlayerAbilityCard(classID, i + 1, 1));
		}

		// Sets how many small items you can carry
		if (data.getLevel() % 2 == 0)
			smallItemTotal = data.getLevel() / 2;
		else
			smallItemTotal = (data.getLevel() + 1) / 2;

	}

	public void randomlySelectCards() {
		int cardIndex[] = new int[maxHandCount];

		for (int i = 0; i < maxHandCount; i++) {
			boolean finished = false;
			int num;
			do {
				Random r = new Random();
				num = r.nextInt(27) + 1;
				finished = true;
				for (int j = 0; j < i; j++) {
					if (cardIndex[j] == num)
						finished = false;
				}
			} while (!finished);
			cardIndex[i] = num;
			abilityDeck.add(new PlayerAbilityCard(classID, num, 1));
		}

	}

	public void setStats(StatsTracker stats) {
		this.stats = stats;
	}

	public StatsTracker getStats() {
		return stats;
	}

	public int getMaxHandCount() {
		return maxHandCount;
	}

	public void setMaxHandCount(int maxHandCount) {
		this.maxHandCount = maxHandCount;
	}

	public int getSmallItemTotal() {
		return smallItemTotal;
	}

	public void setSmallItemTotal(int smallItemTotal) {
		this.smallItemTotal = smallItemTotal;
	}

	// Player Variables
	public CardDataObject getAugmentCard() {
		return augment;
	}

	// Item
	public List<Item> getItems() {
		return items;
	}

	// On the cutting block
	public void setBonusMove(int bonus) {
	}

	public void toggleMovementImmunity() {
		movementImmunity = !movementImmunity;
	}

	public boolean getMovementImmunity() {
		return movementImmunity;
	}

	public void reset() {
		initiative = -1;
		firstCardChoice = null;
		secondCardChoice = null;
		cardChoice = true;
		topCard = null;
		bottomCard = null;
		createAnyElement = false;
	}

	public void resetCardChoice() {
		firstCardChoice = null;
		secondCardChoice = null;
		cardChoice = true;
	}

	// Sets the player on a long rest and creates an initiative of 99
	// Used during the beginning of the round, then during their turn they take the
	// long rest
	public void setLongRest() {
		longRest = true;
		longRestFlag = true;
		initiative = 99;
		stats.addLongRest();
	}

	// Choose to discard a card instead of taking damage
	public boolean discardForHealth(int key, Graphics g) {
		GUI.chooseDiscard(g);
		drawAbilityCards(g);
		int currentAbilityDeckSize = abilityCardsLeft();

		if (currentAbilityDeckSize == 1) {
			for (int i = 0; i < abilityDeck.size(); i++) {
				if (abilityDeck.get(i).isCardFree()) {
					abilityDeck.get(i).setCardIndiscardPile();
					return true;
				}
			}
		} else {
			if (key >= 0 && key < abilityDeck.size()) {
				if (abilityDeck.get(key).isCardFree()) {
					abilityDeck.get(key).setCardIndiscardPile();
					return true;
				}
				return false;
			}
		}

		return false;
	}

	public void addLoot(Hex hex) {

		System.out.println("Player.java Loc 142: Player successfully looted " + hex.getLootID().equals("Gold"));

		if (hex.getLootID().equals("Gold")) {
			data.setGold(data.getGold() + 1);
			stats.addGold(1);
		} else {

			// TreasureLoader(String.valueOf(hex.getLootID()), shop);
			// lootInventory.add(hex.getLootID());
			// stats.addTreasure(hex.getLootID());
		}

	}

	public int pickPlayCard(KeyEvent e, int key, char k, Graphics g) {
		List<Item> usableItems = ItemLoader.onTurn(getItems());
		char buttons[] = buttons();

		showPickedCards(e, g);
		showDuringTurnItemCards(usableItems, g);

		// returns 100+item index, then will subtract 100 to get the index
		for (int i = 0; i < usableItems.size(); i++) {
			if (i < 9) {
				if (buttons[i] == k) {
					return 100 + i;
				}
			}
		}

		if (cardChoice) {

			if (key >= 1 && key <= 8) {
				cardChoice = !cardChoice;
				if (key == 1) {
					firstCardChoice = topCard;
					topCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Top");
				} else if (key == 2) {
					firstCardChoice = topCard;
					topCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Bottom");
				} else if (key == 3) {
					firstCardChoice = topCard;
					topCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltTop");
				} else if (key == 4) {
					firstCardChoice = topCard;
					topCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltBottom");
				} else if (key == 5) {
					firstCardChoice = bottomCard;
					bottomCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Top");
				} else if (key == 6) {
					firstCardChoice = bottomCard;
					bottomCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("Bottom");
				} else if (key == 7) {
					firstCardChoice = bottomCard;
					bottomCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltTop");
				} else if (key == 8) {
					firstCardChoice = bottomCard;
					bottomCard = null;
					abilityDeck.get(abilityDeck.indexOf(firstCardChoice)).setFlag("AltBottom");
				}

				return key;
			}
		} else {
			if (topCard == null && (key >= 5 && key <= 8)) {
				cardChoice = !cardChoice;
				secondCardChoice = bottomCard;
				bottomCard = null;
				if (key == 5) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Top");
				} else if (key == 6) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Bottom");
				} else if (key == 7) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltTop");
				} else if (key == 8) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltBottom");
				}
				return key;
			}

			if (bottomCard == null && (key >= 1 && key <= 4)) {
				cardChoice = !cardChoice;
				secondCardChoice = topCard;
				topCard = null;
				if (key == 1) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Top");
				} else if (key == 2) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("Bottom");
				} else if (key == 3) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltTop");
				} else if (key == 4) {
					abilityDeck.get(abilityDeck.indexOf(secondCardChoice)).setFlag("AltBottom");
				}
				return key;
			}
		}
		return -1;
	}

	public PlayerAbilityCard playCard() {
		if (!cardChoice) {
			return firstCardChoice;
		} else {
			return secondCardChoice;
		}
	}

	// [Rem] I had to write this way because I was dumb and forgot that either card
	// could be played as the top or bottom after init round
	public CardDataObject getAbilityCardData(String flag, int cardFlag) {
		CardDataObject card = new CardDataObject();

		if (flag == "Top") {
			// Get the data from the correct card
			if (cardFlag == 1)
				card = topCard.getTopData();
			if (cardFlag == 3)
				card = bottomCard.getTopData();

		} else {

			// get the data from the correct card
			if (cardFlag == 1)
				card = topCard.getBottomData();
			if (cardFlag == 3)
				card = bottomCard.getBottomData();

			return card;
		}

		return card;
	}

	public boolean getCardChoice() {
		return cardChoice;
	}

	public PlayerAbilityCard getFirstCardChoice() {
		return firstCardChoice;
	}

	public PlayerAbilityCard getSecondCardChoice() {
		return secondCardChoice;
	}

	/**
	 * Player chooses the the ability cards
	 */
	public void pickAbilityCards(KeyEvent e, int key, Graphics g, Point mouseClick) {

		abilityCardsLeft();
		GUI.abilityCardTitle(g, discardPileSize());
		drawAbilityCards(g);

		if (abilityDeck.size() > 1) {
			if (cardChoice) {
				GUI.chooseTopCard(g);

				if (checkIfAbilityCardSelected(mouseClick) != -1) {
					int index = checkIfAbilityCardSelected(mouseClick);
					if (abilityDeck.get(index).isCardFree()) {
						topCard = abilityDeck.get(index);
						abilityDeck.get(index).setCardInPlay();
						initiative = abilityDeck.get(index).getInitiative();
						cardChoice = !cardChoice;
					}
				}else if (key >= 0 && key < abilityDeck.size() && abilityDeck.get(key).isCardFree()) {
					topCard = abilityDeck.get(key);
					abilityDeck.get(key).setCardInPlay();
					initiative = abilityDeck.get(key).getInitiative();
					cardChoice = !cardChoice;
				}
			}
			else {
				GUI.chooseBottomCard(g);
	
				if (checkIfAbilityCardSelected(mouseClick) != -1) {
					int index = checkIfAbilityCardSelected(mouseClick);
					if (abilityDeck.get(index).isCardFree()) {
						bottomCard = abilityDeck.get(index);
						abilityDeck.get(index).setCardInPlay();
						cardChoice = !cardChoice;
					}
				}else if (key >= 0 && key < abilityDeck.size() && abilityDeck.get(key).isCardFree()) {
					bottomCard = abilityDeck.get(key);
					abilityDeck.get(key).setCardInPlay();
					cardChoice = !cardChoice;
				}
			}
		}	
	}

	/**
	 * @return Number of ability cards free
	 */
	public int abilityCardsLeft() {
		int currentAbilityDeckSize = 0;
		for (int i = 0; i < abilityDeck.size(); i++) {
			if (abilityDeck.get(i).isCardFree())
				currentAbilityDeckSize++;
		}
		return currentAbilityDeckSize;
	}

	/**
	 * Draws all the available ability cards
	 */
	private void drawAbilityCards(Graphics g) {
		for (int i = 0; i < abilityDeck.size(); i++) {
			if (abilityDeck.get(i).isCardFree())
				GUI.drawAbilityCardText(g, abilityDeck, i);
		}
	}

	/**
	 * @return If mouse is clicked on the ability card, returns ability card inex
	 */
	private int checkIfAbilityCardSelected(Point mouseClick) {
		if (mouseClick != null) {
			for (int i = 0; i < abilityDeck.size(); i++)
				if (abilityDeck.get(i).isCardFree())
					if (GUI.checkMouseIsOnAbilityCard(mouseClick, i))
						return i;
		}
		return -1;
	}

	private void showPickedCards(KeyEvent e, Graphics g) {

		int rows=0;
		if (topCard != null) {
			rows=GUI.drawAbilityCardTextTop(g, topCard);
		}
		if (bottomCard != null) {
			GUI.drawAbilityCardTextBottom(g, bottomCard, rows);
		}
	}

	private void showDuringTurnItemCards(List<Item> usableItems, Graphics g) {

		char buttons[] = buttons();

		for (int i = 0; i < usableItems.size(); i++) {
			if (i == 9)
				break;
			GUI.drawItemCardDuringTurn(g, buttons, usableItems, i);
		}
	}

	private char[] buttons() {
		char buttons[] = new char[10];
		buttons[0] = 'q';
		buttons[1] = 'w';
		buttons[2] = 'e';
		buttons[3] = 'r';
		buttons[4] = 't';
		buttons[5] = 'y';
		buttons[6] = 'u';
		buttons[7] = 'i';
		buttons[8] = 'o';
		buttons[9] = 'p';

		return buttons;
	}

	public void endTurn() {

		endOfRound();

		cardChoice = true;

		if (longRest == false) {
			CardDataObject card = new CardDataObject();
			int index = -1;
			if (firstCardChoice.getFlag().equals("Top") || firstCardChoice.getFlag().equals("AltTop")) {
				card = firstCardChoice.getTopData();
				index = abilityDeck.indexOf(firstCardChoice); // firstCardChoice.getIndex();
			} else if (firstCardChoice.getFlag().equals("Bottom") || firstCardChoice.getFlag().equals("AltBottom")) {
				card = firstCardChoice.getBottomData();
				index = abilityDeck.indexOf(firstCardChoice);
			}

			// This is for brute: shield bash - Not sure it will always hold true
			/*
			 * if(card.getData().getRoundBonusFlag() && card.getEffects().getShield()>0) {
			 * data.setShield(data.getShield()-1); }
			 * 
			 * if(card.getData().getRoundBonusFlag() && card.getEffects().getRetaliate()>0)
			 * { retaliate= new SimpleCards(); }
			 */

			if (card.getData().getConsumeFlag())
				abilityDeck.get(index).setCardInlostPile();
			else
				abilityDeck.get(index).setCardIndiscardPile();

			if (secondCardChoice.getFlag().equals("Top") || secondCardChoice.getFlag().equals("AltTop")) {
				card = secondCardChoice.getTopData();
				index = abilityDeck.indexOf(secondCardChoice); // secondCardChoice.getIndex();
			} else if (secondCardChoice.getFlag().equals("Bottom") || secondCardChoice.getFlag().equals("AltBottom")) {
				card = secondCardChoice.getBottomData();
				index = abilityDeck.indexOf(secondCardChoice);
			}

			/*
			 * //This is for brute: shield bash - Not sure it will always hold true
			 * if(card.getRoundBonusFlag() && card.getShield()>0) {
			 * data.setShield(data.getShield()-1); }
			 * 
			 * if(card.getRoundBonusFlag() && card.getRetaliate()==true) { retaliate= new
			 * SimpleCards(); }
			 */

			if (card.getData().getConsumeFlag())
				abilityDeck.get(index).setCardInlostPile();
			else
				abilityDeck.get(index).setCardIndiscardPile();
		}
		
		int lostPile=0;
		
		for(AbilityCard card : abilityDeck) {
			if(card.isLostFlag())
				lostPile++;
		}
		
		if(abilityDeck.size()-lostPile<3)
			exhausted=true;
		
		secondCardChoice = null;
		firstCardChoice = null;
		longRest = false;
	}

	public void setAugment(PlayerAbilityCard abilityCard) {
		if (abilityCard.getTopData().hasAugment()) {
			augment = abilityCard.getTopData();
		} else {
			augment = abilityCard.getBottomData();
		}
	}

	public void replaceAugmentCard(PlayerAbilityCard abilityCard) {
		// Set previous augment in the lost pile
		for (int i = 0; i < abilityDeck.size(); i++) {
			if ((abilityDeck.get(i).getTopData() == augment) || (abilityDeck.get(i).getBottomData() == augment)) {
				abilityDeck.get(i).setCardInlostPile();
			}
		}
		abilityDeck.get(abilityDeck.indexOf(abilityCard)).setCardInPlay();

		if (abilityCard.getTopData().hasAugment()) {
			augment = abilityCard.getTopData();
		} else {
			augment = abilityCard.getBottomData();
		}
	}

	public void discardAugmentCard() {
		inPlay.remove(abilityDeck.get(abilityDeck.indexOf(augment)));
		abilityDeck.get(abilityDeck.indexOf(augment)).setCardInlostPile();
		augment = null;
	}

	public void graphicsDrawCardsInPlay(Graphics g) {

		GUIScenario.graphiascsDrawCardsInPlay(g, augment, inPlay, counterTriggers);
	}

	public void graphicsPlayerInfo(Graphics g) {
		GUIScenario.graphicsPlayerInfo(g, name, getClassID(), positiveConditions, data, isAugmented(), augment,
				counterTriggers, roundTriggers, roundBonus);
	}

	public boolean isAugmented() {
		return UsePlayerAbilityCard.hasAugment(augment);

	}

	public void shortRestInfo(Graphics g) {
		GUI.drawShortRestInfo(g, abilityDeck);
	}

	public int discardPileSize() {
		int count = 0;
		for (int i = 0; i < abilityDeck.size(); i++) {
			if (abilityDeck.get(i).isDiscardFlag())
				count++;
		}
		return count;
	}

	public void takeShortRest() {

		stats.addShortRest();

		collectDiscardPile();
		Random rand = new Random();
		boolean running = true;
		do {
			int pick = rand.nextInt(abilityDeck.size());
			if (abilityDeck.get(pick).isCardFree()) {
				abilityDeck.get(pick).setCardInlostPile();
				running = false;
			}
		} while (running);
	}

	public void takeLongRest(Graphics g, int key) {
		GUI.showDiscardPile(g, abilityDeck);

		if (cardChoice) {
			if (key >= 0 && key <= abilityDeck.size()) {
				if (abilityDeck.get(key).isDiscardFlag()) {
					abilityDeck.get(key).setCardInlostPile();
					cardChoice = !cardChoice;
				}
			}
		} else {
			if (key >= 0 && key <= abilityDeck.size()) {
				if (abilityDeck.get(key).isDiscardFlag()) {
					abilityDeck.get(key).setCardInlostPile();
					cardChoice = !cardChoice;
					longRestFlag = false;
					collectDiscardPile();
				}
			}
		}
	}

	private void collectDiscardPile() {
		for (int i = 0; i < abilityDeck.size(); i++) {
			if (abilityDeck.get(i).isDiscardFlag())
				abilityDeck.get(i).takeCardOutOfDiscard();
		}
	}

	// Returns whether the two cards have been locked for initiative
	public boolean cardsLocked() {
		if (longRest)
			return true;

		if (topCard != null && bottomCard != null)
			return true;
		else
			return false;
	}

	public boolean onRest() {
		return longRestFlag;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	/*
	 * public int getAttack(CardDataObject attackCard, boolean advantage, boolean
	 * disadvantage) {
	 * System.out.println("Attack Break Down: (Loc: Player.java -getAttack Line843"
	 * ); AttackModifierCard card = attackModifierDeck.pickRandomModifierCard();
	 * 
	 * if(card.hasBless() || advantage==true || positiveConditions.isBless()) {
	 * if(card.hasBless()) { attackModifierDeck.remove(card); card =
	 * attackModifierDeck.pickRandomModifierCard(); }
	 * 
	 * AttackModifierCard secondCard = attackModifierDeck.pickRandomModifierCard();
	 * 
	 * System.out.println("Card 1 - A:"+attackCard.getData().getAttack()+"  +AM:"
	 * +card.getPlusAttack()+"  XM:"+card.getMultiplier());
	 * System.out.println("Card 2 - A:"+attackCard.getData().getAttack()+"  +AM:"
	 * +secondCard.getPlusAttack()+"  XM:"+secondCard.getMultiplier());
	 * 
	 * if(attackModifierDeck.firstIsBest(card, secondCard)==false) card=secondCard;
	 * }
	 * 
	 * if(card.hasCurse() || disadvantage==true || negativeConditions.isCurse()) {
	 * if(card.hasCurse()) { attackModifierDeck.remove(card); card =
	 * attackModifierDeck.pickRandomModifierCard(); }
	 * 
	 * AttackModifierCard secondCard = attackModifierDeck.pickRandomModifierCard();
	 * 
	 * System.out.println("Card 1 - A:"+attackCard.getData().getAttack()+"  +AM:"
	 * +card.getPlusAttack()+"  XM:"+card.getMultiplier());
	 * System.out.println("Card 2 - A:"+attackCard.getData().getAttack()+"  +AM:"
	 * +secondCard.getPlusAttack()+"  XM:"+secondCard.getMultiplier());
	 * 
	 * if(attackModifierDeck.firstIsBest(card, secondCard)) card=secondCard; }
	 * 
	 * System.out.println("Final Attack Mod - A:"+attackCard.getData().getAttack()
	 * +"  +AM:"+card.getPlusAttack()+"  XM:"+card.getMultiplier());
	 * 
	 * int damage =
	 * (attackCard.getData().getAttack()+card.getPlusAttack())*card.getMultiplier();
	 * 
	 * if(roundTriggers.size()>0) { for(int i=0; i<roundTriggers.size(); i++) {
	 * if(roundTriggers.get(i).getTriggerFlag().equals("EnemyAlone")){
	 * damage=damage+roundTriggers.get(i).getBonusData().getAttack();
	 * data.setXp(data.getXp()+roundTriggers.get(i).getBonusData().getXpOnUse()); }
	 * } }
	 * 
	 * if(card.getShuffle()) attackModifierDeck.shuffleDeck();
	 * 
	 * return damage; }
	 */
	@Override
	public void takeDamage(int damage) {

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getPlayFlag().equals("when_attacked")) {
				// TODO: Need to go through items that could be triggered
				System.out.println("Player.java -takeDamage Loc 764: item may have been triggered due to an attack");
			}
		}

		if (counterTriggers.size() > 0) {
			for (int i = 0; i < counterTriggers.size(); i++) {
				if (counterTriggers.get(i).isTriggerOnDamage()) {
					if (counterTriggers.get(i).getEffectFlag().equals("NoDamage"))
						damage = 0;

					counterTriggers.get(i).addToCounter();
				}

				if (counterTriggers.get(i).isAtMaxCount())
					counterTriggers.remove(i);
			}
		}

		if (negativeConditions.isPoison())
			damage = damage + 1;

		if (augment != null)
			if (augment.getEffects() != null)
				if (augment.getEffects().getShield() > 0)
					if (damage - augment.getEffects().getShield() >= 0)
						damage = damage - augment.getEffects().getShield();

		if (data.getShield() > 0)
			if (damage - data.getShield() >= 0)
				damage = damage - data.getShield();

		if (damage > 0)
			data.setHealth(data.getHealth() - damage);
		
		if(data.getHealth()<data.getMaxHealth()/2)
			stats.setLessHalfHPFlag(true);

		// [Test]
		System.out.println(name + " was attacked for " + damage + " making thier health " + data.getHealth());
	}

	public void increaseXP(int xpGained) {
		data.setXp(data.getXp() + xpGained);
		stats.xpGained(xpGained);
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public void consumeItem(Item item) {
		consumedItems.add(item);
		items.remove(item);
		stats.increaseItemUse();
	}

	public void spendItem(Item item) {
		int index = items.indexOf(item);
		items.get(index).use();
		stats.increaseItemUse();

	}

	public List<Item> getConsumedItems() {
		return consumedItems;
	}

	public void randomlyRestoreConsumedItem() {
		Random r = new Random();
		if (consumedItems.size() > 0) {
			int n = r.nextInt(consumedItems.size());
			items.add(consumedItems.get(n));
			consumedItems.remove(n);
		}
	}

	public void setCreateAnyElement(boolean flag) {
		createAnyElement = true;
	}

	public boolean getCreateAnyElement() {
		return createAnyElement;
	}

	public void recoverLostCards(int number) {

		if (number == 99) {
			for (int i = 0; i < abilityDeck.size(); i++) {
				if (abilityDeck.get(i).isLostFlag())
					abilityDeck.get(i).resetCardFlags();
			}
		}
	}

	public void addItem(List<Item> collectiveItems) {
		for (int i = 0; i < collectiveItems.size(); i++)
			items.add(collectiveItems.get(i));
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public void changeBattleGoalTotal(int change) {
		currentBattleGoalCount = currentBattleGoalCount + change;

		if (this.currentBattleGoalCount < 0)
			currentBattleGoalCount = 0;
	}

	public void setBattleGoalCard(BattleGoalCard card) {
		this.battleGoal = card;
	}

	public BattleGoalCard getBattleGoalCard() {
		return battleGoal;
	}

	public int getBattleGoalTotal() {
		return currentBattleGoalCount;
	}
	
	public boolean isExhausted() {return exhausted;}
	
	//Testing
	public void setBattleGoalTotal(int battleGoalCount) {this.currentBattleGoalCount=battleGoalCount;}
	public void setData(CharacterDataObject data) {this.data=data;}
}
