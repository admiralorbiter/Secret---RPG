package Gloomhaven;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Counter;
import Gloomhaven.CardDataObject.PositiveConditions;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Scenario.Scenario.State;

public final class GUIScenario {
	
	public static void EntityTable(Graphics g, List<Player> party , List<Enemy> enemies) {
		g.setColor(Setting.defaultColor);
		g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gMid, GUISettings.entityTableW, GUISettings.entityTableH);
		
		g.setFont(FontSettings.heading);
		g.drawString("Entity Table", GUISettings.gRight, GUISettings.gMid+GUISettings.leadingBody);
		
		g.setFont(FontSettings.body);
		
		int entityCount=2;
		
		for(int i=0; i<party.size(); i++) {
			g.setColor(Setting.defaultColor);
			g.drawString(party.get(i).getName(), GUISettings.gRight, GUISettings.gMid+GUISettings.leadingBody*entityCount);
			
			if(party.get(i).getPositiveConditions()!=null) {
				g.setColor(Color.YELLOW);
				if(party.get(i).getPositiveConditions().isBless())
					g.drawString("B", GUISettings.gRight+70,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.gray);
				if(party.get(i).getPositiveConditions().isInvisibility())
					g.drawString("I",GUISettings.gRight+80,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.red);
				if(party.get(i).getPositiveConditions().isStrengthen())
					g.drawString("S", GUISettings.gRight+90,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
			}
			
			if(party.get(i).getNegativeConditions()!=null) {
				g.setColor(new Color(238,130,238));
				if(party.get(i).getNegativeConditions().isCurse())
					g.drawString("C", GUISettings.gRight+100,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.ORANGE);
				if(party.get(i).getNegativeConditions().isDisarm())
					g.drawString("D", GUISettings.gRight+110,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.cyan);
				if(party.get(i).getNegativeConditions().isImmobilize())
					g.drawString("I", GUISettings.gRight+120,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.red);
				if(party.get(i).getNegativeConditions().isWound())
					g.drawString("W", GUISettings.gRight+125,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.LIGHT_GRAY);
				if(party.get(i).getNegativeConditions().isMuddle())
					g.drawString("M", GUISettings.gRight+140,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.GREEN);
				if(party.get(i).getNegativeConditions().isPoison())
					g.drawString("P", GUISettings.gRight+150,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.yellow);
				if(party.get(i).getNegativeConditions().isStun())
					g.drawString("S", GUISettings.gRight+160,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
			}
			
			entityCount++;
		}
		
		for(int i=0; i<enemies.size(); i++) {
			g.setColor(Setting.defaultColor);
			g.drawString(enemies.get(i).getClassID(), GUISettings.gRight+10,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
			
			if(enemies.get(i).getPositiveConditions()!=null) {
				g.setColor(Color.YELLOW);
				if(enemies.get(i).getPositiveConditions().isBless())
					g.drawString("B", GUISettings.gRight+70,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.gray);
				if(enemies.get(i).getPositiveConditions().isInvisibility())
					g.drawString("I", GUISettings.gRight+80,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.red);
				if(enemies.get(i).getPositiveConditions().isStrengthen())
					g.drawString("S", GUISettings.gRight+90,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
			}
			
			if(enemies.get(i).getNegativeConditions()!=null) {
				g.setColor(new Color(238,130,238));
				if(enemies.get(i).getNegativeConditions().isCurse())
					g.drawString("C", GUISettings.gRight+100,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.ORANGE);
				if(enemies.get(i).getNegativeConditions().isDisarm())
					g.drawString("D", GUISettings.gRight+110,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.cyan);
				if(enemies.get(i).getNegativeConditions().isImmobilize())
					g.drawString("I", GUISettings.gRight+120,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.red);
				if(enemies.get(i).getNegativeConditions().isWound())
					g.drawString("W", GUISettings.gRight+125,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.LIGHT_GRAY);
				if(enemies.get(i).getNegativeConditions().isMuddle())
					g.drawString("M", GUISettings.gRight+140,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.GREEN);
				if(enemies.get(i).getNegativeConditions().isPoison())
					g.drawString("P", GUISettings.gRight+150,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
				g.setColor(Color.yellow);
				if(enemies.get(i).getNegativeConditions().isStun())
					g.drawString("S", GUISettings.gRight+160,  GUISettings.gMid+GUISettings.leadingBody*entityCount);
			}
			
			entityCount++;
		}
		
		g.setColor(Setting.defaultColor);
	}
	
	public static void graphicsDrawCardsInPlay(Graphics g, CardDataObject augment, List<PlayerAbilityCard> inPlay, List<Counter> counterTriggers) {

		g.setFont(FontSettings.heading);
		g.drawString("Cards in play.", GUISettings.gRight, GUISettings.gTop+GUISettings.leadingBody);
		
		g.setFont(FontSettings.body);
		int rows=1;
		
		if(augment!=null) {
			g.drawString(augment.getCardText(), GUISettings.gRight, GUISettings.gTop+GUISettings.leadingBody*rows);
			rows++;
		}
		
		for(int i=0; i<inPlay.size(); i++) {
			g.drawString(inPlay.get(i).getName(), GUISettings.gRight, GUISettings.gTop+GUISettings.leadingBody*rows);
			rows++;
		}
		
		for(int j=0; j<counterTriggers.size(); j++) {
			g.drawString(counterTriggers.get(j).getEffectFlag()+"  "+counterTriggers.get(j).getTriggerFlag(), GUISettings.gRight, GUISettings.gTop+GUISettings.leadingBody*rows);
		}

		rows++;
		g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gTop, GUISettings.cardsInPlayTableW, GUISettings.leadingBody*rows);
	}
	
	public static void graphicsPlayerInfo(Graphics g, String name, String classID, PositiveConditions positiveConditions, CharacterDataObject data, boolean isAugmented, CardDataObject augment, List<Counter> counterTriggers, List<Counter> roundTriggers, CardDataObject roundBonus) {
		g.setColor(Setting.defaultColor);
		g.setFont(FontSettings.heading);
		g.drawString(name+"  "+classID, GUISettings.gRight, GUISettings.gYQ1+GUISettings.leadingBody);
		
		g.setFont(FontSettings.body);
		
		if(positiveConditions.isBless()) {
			g.setColor(Color.YELLOW);
			g.drawString("B", GUISettings.gRight+100, GUISettings.gYQ1+GUISettings.leadingBody);
			g.setColor(Setting.defaultColor);
		}
		
		if(positiveConditions.isInvisibility()) {
			g.setColor(Color.gray);
			g.drawString("I", GUISettings.gRight+120, GUISettings.gYQ1+GUISettings.leadingBody);
			g.setColor(Setting.defaultColor);
		}
		
		if(positiveConditions.isStrengthen()) {
			g.setColor(Color.red);
			g.drawString("S", GUISettings.gRight+140, GUISettings.gYQ1+GUISettings.leadingBody);
			g.setColor(Setting.defaultColor);
		}
		
		g.drawString("Level "+data.getLevel(), GUISettings.gRight, GUISettings.gYQ1+GUISettings.leadingBody*2);
		g.drawString("Health "+data.getHealth()+"  XP"+data.getXp(), GUISettings.gRight, GUISettings.gYQ1+GUISettings.leadingBody*3);
	
		int rows=0;
		
		if(isAugmented) {
			g.drawString("Augment Active: ", GUISettings.gRight, GUISettings.gYQ1+GUISettings.leadingBody*(4));
			g.drawString(augment.getCardText(), GUISettings.gRight, GUISettings.gYQ1+GUISettings.leadingBody*5);
			rows=2;
		}
		
		for(int i=0; i<counterTriggers.size();i++) {
			rows++;
			g.drawString(counterTriggers.get(i).getTriggerFlag(), GUISettings.gRight+10, GUISettings.gYQ1+GUISettings.leadingBody*4);
		}
		
		for(int i=0; i<roundTriggers.size();i++) {
			rows++;
			g.drawString(roundTriggers.get(i).getTriggerFlag(), GUISettings.gRight+10, GUISettings.gYQ1+GUISettings.leadingBody*(4+rows));
		}
		
		g.drawString("Gold: "+data.getGold(), GUISettings.gRight+10, GUISettings.gYQ1+GUISettings.leadingBody*(4+rows));
		
		if(roundBonus!=null)
			if(roundBonus.getNegativeConditions()!=null)
				g.drawString("Bonus Condition on Attack: "+roundBonus.getNegativeConditions().getFlag(), GUISettings.gRight+10, GUISettings.gYQ1+GUISettings.leadingBody*(5+rows));
		
		
		g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ1, 200, GUISettings.leadingBody*(6+rows));
		
		g.setColor(Setting.defaultColor);
	}
	
	public static void drawControlsAndHelp(Graphics g, State state, List<Player> party, int currentPlayer, PlayerAbilityCard card) {
		
		g.setFont(FontSettings.body);
		
		switch(state) {
			case CARD_SELECTION:
				g.drawString("Picking Cards for your turn.", GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBody*2);
				//Allows the user to take a long rest if they have enough in the discard pile 
				if(party.get(currentPlayer).discardPileSize()>1)	
					g.drawString("Take a long rest with "+Setting.restKey, GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBody*3);
				break;
			case INITIATIVE:
				//List of all the initiatives for the round
				g.drawString("Initiatives:", GUISettings.gLeft, GUISettings.gBottom);											
				g.drawString("Enemy: Note - Need to fix enemyInfo", GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody);								
				for(int i=0; i<party.size();  i++) {
					g.drawString("Player "+party.get(i).getID()+"      "+String.valueOf(party.get(i).getInitiative()), GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*(i+2));
				}
				break;
			case PLAYER_DEFENSE:
				g.drawString("Press "+Setting.discardKey+" to discard card or "+Setting.healKey+" to take damage.", GUISettings.gLeft, GUISettings.gBottom);
				break;
			case PLAYER_ATTACK_LOGIC:
				g.drawString("Move "+UsePlayerAbilityCard.getMove(card)+"     Attack: "+UsePlayerAbilityCard.getAttack(card)+"  (Loc: Scenario -Player Attack Logic)", GUISettings.gLeft, GUISettings.gBottom);
				break;
			case PLAYER_MOVE:
				g.drawString("Press "+Setting.moveKey+" to move.", GUISettings.gLeft, GUISettings.gBottom);
				break;
			case PLAYER_ATTACK:
				g.drawString("Press "+Setting.targetKey+" to target.", GUISettings.gLeft, GUISettings.gBottom);
				break;
			case PLAYER_PUSH:
				g.drawString("Press 1, 2, 3.", GUISettings.gLeft, GUISettings.gBottom);
				break;
			case MINDCONTROL:
				g.drawString("Press "+Setting.moveKey+"to move.", GUISettings.gLeft, GUISettings.gBottom);
				g.drawString("Press "+Setting.targetKey+" to target.", GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody);
				break;
			case CREATE_INFUSION:
				g.drawString("Pick an infusion", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*0);
				g.drawString("1 Fire", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*1);
				g.drawString("2 Ice", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*2);
				g.drawString("3 Air", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*3);
				g.drawString("4 Earth", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*4);
				g.drawString("5 Light", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*5);
				g.drawString("6 Dark", GUISettings.gLeft, GUISettings.gMid+GUISettings.leadingBody*6);
				break;
		}
	}
	
	public static void drawStateOfScenario(Graphics g, City gloomhaven, State state) {
		g.drawString("State of Scenario      Prosperity: "+gloomhaven.getProspLevel()+"Rep: "+gloomhaven.getReputationLevel(), GUISettings.gLeft, GUISettings.gTop);
		g.drawString(state.toString(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBody);
		
	}
}
