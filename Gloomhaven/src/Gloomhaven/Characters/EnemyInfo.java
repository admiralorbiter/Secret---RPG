package Gloomhaven.Characters;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.ScenarioData;
import Gloomhaven.Setting;
import Gloomhaven.AbilityCards.EnemyAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;

public class EnemyInfo {
	
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<EnemyAbilityCard> abilityDeck = new ArrayList<EnemyAbilityCard>();
	private int abilityCardIndex;
	private int startingAbilityCardCount;
	private int turnNumber;
	
	public EnemyInfo(ScenarioData data) {
		abilityCardIndex=0;
		startingAbilityCardCount=8;
		for(int i=0; i<startingAbilityCardCount; i++)
			abilityDeck.add(new EnemyAbilityCard("Test", i+1, 1));
	}
	
	public int getInitiative() {return abilityDeck.get(abilityCardIndex).getInitiative();}
	public void setTurnNumber(int turnNumber) {this.turnNumber=turnNumber;}
	public int getTurnNumber() {return turnNumber;}
	public Enemy getEnemy(int index) {return enemies.get(index);}
	public int getCount() {return enemies.size();}
	
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
	}
	
	public void drawAbilityCard(Graphics g) {
		g.drawString("Enemy Ability Card", Setting.graphicsXLeft, Setting.graphicsYMid);
		g.drawString("Attack: "+abilityDeck.get(abilityCardIndex).getAttack()+"  Move: "+abilityDeck.get(abilityCardIndex).getMove()+" Range: "+abilityDeck.get(abilityCardIndex).getRange(), Setting.graphicsXLeft, Setting.graphicsYMid+Setting.rowSpacing);
	}
	
	public int getAttack(int enemyTurnIndex) {
		AttackModifierCard card = enemies.get(enemyTurnIndex).attackModifierDeck.pickRandomModifierCard();
		return card.getMultiplier()*(enemies.get(enemyTurnIndex).getBaseStats().getAttack()+abilityDeck.get(abilityCardIndex).getAttack()+card.getPlusAttack());
	}
	
	public void enemyMoveProcedure(int index, List<Player> party, Graphics g) {
			
	}
	
	
}
