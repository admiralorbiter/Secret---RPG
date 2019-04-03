package Gloomhaven;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;
import Gloomhaven.AttackModifier.AttackModifierDeck;
import Gloomhaven.Characters.Enemy;

public class EnemyAbilityDeck {
	
	private List<EnemyAbilityCard> abilityDeck = new ArrayList<EnemyAbilityCard>();
	private int abilityCardIndex=0;
	private AttackModifierDeck attackModifierDeck= new AttackModifierDeck("Standard");
	private String deckID;
	private int turnNumber;
	
	AttackModifierCard card= attackModifierDeck.pickRandomModifierCard();
	
	public EnemyAbilityDeck(String classID) {
		int abilityCardCount=8;
		this.deckID=classID;
		turnNumber=0;
		for(int i=0; i<abilityCardCount; i++)
			abilityDeck.add(new EnemyAbilityCard(classID, i+1, 1));
	}
	
	public int getAbilityCardIndex() {return abilityCardIndex;}
	public int getInitiative() {return abilityDeck.get(abilityCardIndex).getInitiative();}
	public void setTurnNumber(int turnNumber) {this.turnNumber=turnNumber;}
	public int getTurnNumber() {return turnNumber;}
	
	
	public void pickRandomAbilityCard() {
		Random rand = new Random();
		boolean running=true;
		do
		{
		 int pick = rand.nextInt(abilityDeck.size());
		 if(abilityDeck.get(pick).isCardFree()) {
			 abilityCardIndex=pick;
			 running=false;
		 }
		}
		while(running);
		
		card= attackModifierDeck.pickRandomModifierCard();
	}
	
	public void drawAbilityCard(Graphics g) {
		GUI.drawEnemyAbilityCard(g, abilityDeck, abilityCardIndex);
	}
	public String getDeckID() {return deckID;}
	
	public int getAttack(Enemy enemy) {
		return card.getMultiplier()*(enemy.getBaseStats().getAttack()+abilityDeck.get(abilityCardIndex).getAttack()+card.getPlusAttack());
	}
	
	public EnemyAbilityCard getEnemyAbilityCard() {return abilityDeck.get(abilityCardIndex);}
}
