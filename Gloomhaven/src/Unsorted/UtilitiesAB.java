package Unsorted;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Shop;
import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.AttackModifier.AttackModifierCard;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Characters.UtilitiesCharacters;
import Gloomhaven.Characters.Character;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Scenario.ScenarioData;

public final class UtilitiesAB {

	public static void resolveCard(Player player, PlayerAbilityCard abilityCard, InfusionTable elements, Hex[][] board, ScenarioData data, Shop shop) { // NO_UCD (unused code)

		
		CardDataObject card = new CardDataObject();
		card=UsePlayerAbilityCard.getCardData(abilityCard); //abilityCard.getData()
		
		if(card.getData().getRoundBonusFlag()) {
			player.setRoundBonus(card);
			player.addRoundTrigger(card.getCounter());
		}
		
		if(card.getCounter()!=null)
			player.addCounter(card.getCounter());
		
		if(card.getData().getXpOnUse()>0)
			player.increaseXP(card.getData().getXpOnUse());
		
		if(card.getInfuseElementalFlag())
			elements.infuse(card.getInfuseElemental());

		if(card.getPositiveConditions()!=null) {
			player.getPositiveConditions().setPositiveConditions(card.getPositiveConditions());
			
			if(player.getPositiveConditions().isBless())
				player.getAttackModDeck().addCard(new AttackModifierCard("Bless"));
		}
				
		if(card.getEffects()!=null) {
					
			if(card.getEffects().getHeal()>0 && card.getEffects().getTarget().isSelf())
				player.heal(card.getEffects().getHeal());
			
			if(card.getEffects().getLoot()>0) {
				List<Point> loot = new ArrayList<Point>();
				loot=UtilitiesTargeting.createTargetList(board, card.getEffects().getLoot()+1, player.getCubeCoordiantes(data.getHexLayout()), "Loot", data.getBoardSize(), data.getHexLayout());
				for(int i=0; i<loot.size(); i++)
					UtilitiesLoot.loot(board, shop, player, loot.get(i));
			}
			
			if(card.getEffects().getShield()>0) {
				if(player.getRoundBonus().getEffects()!=null)
					player.getRoundBonus().getEffects().setShield(card.getEffects().getShield());
				else {
					player.setRoundBonus(new CardDataObject());
					player.getRoundBonus().setEffects(new Effects("Shield", card.getEffects().getShield(), 0));
				}
			}
			
			if(card.getEffects().getRetaliate()>0) {
				if(player.getRoundBonus().getEffects()!=null)
					player.getRoundBonus().getEffects().setRetaliate(card.getEffects().getRetaliate());
				else {
					player.setRoundBonus(new CardDataObject());
					player.getRoundBonus().setEffects(new Effects("Retaliate", card.getEffects().getRetaliate(), card.getEffects().getRange()));
				}
			}
		}
		if(card.getRecoverLostCards()>0) {
			player.recoverLostCards(card.getRecoverLostCards());
		}
		
		if(card.hasAugment())
			resolveNewAugmentedCard(player, card, abilityCard);
	
	}
	
	public static void resolveAttackEnemyOnEnemy(Enemy enemy, Enemy attacker, int attack) {
		int enemyShield=enemy.getCharacterData().getShield();
		if(enemyShield>0){
			attack=attack-enemyShield;
			if(attack<0)
				attack=0;
		}
		enemy.takeDamage(attack);
	}
	
	public static void resolveAttack(Enemy enemy, Player player, PlayerAbilityCard abilityCard, Hex[][] board, boolean adjacentBonus, InfusionTable elements, ScenarioData data, boolean anyMonstersKilled) {

		CardDataObject card =UsePlayerAbilityCard.getCardData(abilityCard);
		
		int attack= 0;

		if(abilityCard.getFlag()=="AltTop")
			attack=Setting.ALTATTACK;
		else
			attack=UtilitiesCharacters.getAttack(player, card, player.getAttackModDeck(), false, false);
			//attack=player.getAttack(card, false, false);
		
		System.out.println("Base Damage: "+attack);
		
		if(card.getNegativeConditions()!=null) {
			System.out.println("UtilitiesAB.java resolveAttack: Setting ngeative condition "+card.getNegativeConditions().getFlag());
			enemy.getNegativeConditions().setNegativeConditions(card.getNegativeConditions());
		}
		
		if(player.getRoundBonus()!=null) {
			if(player.getRoundBonus().getNegativeConditions()!=null) {
				enemy.getNegativeConditions().setNegativeConditions(player.getRoundBonus().getNegativeConditions());
			}
		}
		
		//Add trigger effects to attacks
		for(int i=0; i<player.getRoundTriggers().size(); i++) {
			if(player.getRoundTriggers().get(i).isTriggerOnAttack()) {
				if(player.getRoundTriggers().get(i).getTriggerFlag().equals("EnemyAlone")) {
					if(UtilitiesTargeting.targetAloneToAlly(enemy, board, data.getBoardSize(), data.getHexLayout())) {
						if(player.getRoundTriggers().get(i).getBonusData().getAttack()>0)
							attack=attack+player.getRoundTriggers().get(i).getBonusData().getAttack();
						
						if(player.getRoundTriggers().get(i).getBonusData().getXpOnUse()>0)
							player.increaseXP(player.getRoundTriggers().get(i).getBonusData().getXpOnUse());
					}
				}
				
				if(player.getRoundTriggers().get(i).getTriggerFlag().equals("ForEachTargeted")) {
					player.increaseXP(player.getRoundTriggers().get(i).getBonusData().getXpOnUse());
				}
				
				if(player.getRoundTriggers().get(i).equals("ForEachNegativeCondition")) {
					for(int j=0; j<enemy.getNegativeConditions().countNegativeConditions(); j++) {
						if(player.getRoundTriggers().get(i).getBonusData().getAttack()>0)
							attack=attack+player.getRoundTriggers().get(i).getBonusData().getAttack();
						
						if(player.getRoundTriggers().get(i).getBonusData().getXpOnUse()>0)
							player.increaseXP(player.getRoundTriggers().get(i).getBonusData().getXpOnUse());
					}
				}
			}
		}
		
		//Add counter to attacks
		for(int i=0; i<player.getCounterTriggers().size(); i++) {
			if(player.getCounterTriggers().get(i).isTriggerOnAttack()) {
				if(player.getCounterTriggers().get(i).getBonusData().getAttack()>0)
					attack=attack+player.getCounterTriggers().get(i).getBonusData().getAttack();
				
				if(player.getCounterTriggers().get(i).getBonusData().getXpOnUse()>0)
					player.increaseXP(player.getCounterTriggers().get(i).getBonusData().getXpOnUse());
			}
			
		}
		
		if(card.getConsumeElementalFlag()) {
			String element = card.getConsumeElemental();
			
			if(elements.consume(element)) {
				if(card.getElementalBonus().getBonusData().getAttack()>0)
					attack=attack+card.getElementalBonus().getBonusData().getAttack();
				
				if(card.getElementalBonus().getBonusData().getXpOnUse()>0)
					player.increaseXP(card.getElementalBonus().getBonusData().getXpOnUse());
			}
		}
		
		if(player.isAugmented() && checkMeleeRange(player,board,"E" , data.getBoardSize())) {
			if(player.getAugmentCard().getEffects()!=null)
				if(player.getAugmentCard().getEffects().getShield()>0)
					player.getCharacterData().setShield(1);
				
				if(player.getAugmentCard().getCounter()!=null)
					if(distance(player.getCoordinates(), enemy.getCoordinates())<=player.getAugmentCard().getCounter().getBonusData().getRange()) {
						attack=attack+player.getAugmentCard().getCounter().getBonusData().getAttack();
					
					
					if(player.getAugmentCard().getEffects()!=null) {
						if(player.getAugmentCard().getEffects().getHeal()>0)
							player.heal(player.getAugmentCard().getEffects().getHeal());
					}
				}
		}

		int enemyShield=enemy.getCharacterData().getShield();
		if(enemyShield>0){
			int playerPierce=card.getEffects().getPierce();
			if(playerPierce>0) {
				if(enemyShield>playerPierce)
					attack=attack+playerPierce;
				else
					attack=attack+enemyShield;
			}
		}
		
		System.out.println("Final Attack: "+attack);
		enemy.takeDamage(attack);
		
		if(enemy.getCharacterData().getHealth()<=0) {
			if(player.getBattleGoalCard()!=null) {
				if(player.getBattleGoalCard().getThresholdKeyword().equals("overkill"))
						player.getStats().setOverkill(true);
				
				if(player.getBattleGoalCard().getThresholdKeyword().equals("first_blood") && anyMonstersKilled==false) {
					anyMonstersKilled=true;
					player.getStats().setFirstBlood(true);
				}
				
				if(player.getBattleGoalCard().getThresholdKeyword().equals("single_blow") && enemy.getCharacterData().getMaxHealth()<=attack)
					player.getStats().setSingleBlow(true);
			}
			System.out.println(player.getName()+"   killed enemy "+enemy.getClassID());
			player.getStats().addKilledEnemy(enemy);
			if(enemy.isElite())
				player.getStats().increaseEliteKillCount();
		}
		System.out.println("");
	}
	
	private static void resolveNewAugmentedCard(Player player, CardDataObject card, PlayerAbilityCard abilityCard) {
		if(player.isAugmented()==false) {
			player.setAugment(abilityCard);
		}
		else {
			player.replaceAugmentCard(abilityCard);
		}
	}
	
	public static int distance(Point p1, Point p2) {

		int penalty=((p1.y%2==0)&&(p2.y%2!=0)&&(p1.x<p2.x))||((p2.y%2==0)&&(p2.y!=0)&&(p2.x<p1.x))?1:0;
		
		return (int) Math.max(Math.abs(p1.y-p2.y), Math.abs(p1.x-p2.x)+Math.floor(Math.abs(p1.y-p2.y)/2)+penalty)-1;
		
	}
	
	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	//[Rem] Should evaluate whether it is faster to just create the full list using one function and no quick melee check
	public static boolean checkMeleeRange(Character entity, Hex board[][], String lookingForID, Point dimensions) {
		
		Point coordinates = entity.getCoordinates();
		int x=(int) coordinates.getX();
		int y=(int) coordinates.getY();
	
		if(x-1>0) {
			if(board[x-1][y].getQuickID()==lookingForID)
				return true;
			if(y-1>0) {
				if(board[x-1][y-1].getQuickID()==lookingForID) {
					return true;
				}
			}
			if(y+1<dimensions.getY()) {
				if(board[x-1][y+1].getQuickID()==lookingForID) {
					return true;
				}
			}
		}
		
		if(x+1<dimensions.getX()) {
			if(board[x+1][y].getQuickID()==lookingForID)
				return true;
			if(y-1>0) {
				if(board[x+1][y-1].getQuickID()==lookingForID) {
					return true;
				}
			}
			if(y+1<dimensions.getY()) {
				if(board[x+1][y+1].getQuickID()==lookingForID) {
					return true;
				}
			}
		}
		
		return false;
	}

	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	public static void checkRange(Hex board[][], String lookingForID, int range, List<Player> party, List<Player> targets, Enemy enemy, Point dimensions) {
		List<String> idList = new ArrayList<String>();
		
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(enemy.getCoordinates().getX()%2!=0)
							convertedPoint=UtilitiesBoard.cubeToCoordOdd(x, y, z);
						else
							convertedPoint=UtilitiesBoard.cubeToCoordEven(x, y, z);
						
						int xToPlot=(int)(convertedPoint.getX()+enemy.getCoordinates().getX());
						int yToPlot=(int) (convertedPoint.getY()+enemy.getCoordinates().getY());
						
						if(xToPlot>=0 && xToPlot<dimensions.getX()) 
							if(yToPlot>=0 && yToPlot<dimensions.getY())
								if(board[xToPlot][yToPlot].getQuickID()==lookingForID)
									idList.add(board[xToPlot][yToPlot].getID());
						
					}
				}
			}
		}
		
		
		for(int idIndex=0; idIndex<idList.size(); idIndex++) {
			for(int i=0; i<party.size(); i++) {
				if(party.get(i).getID()==idList.get(idIndex)) {
					targets.add(party.get(i));
					break;											//[Rem] Worried this will cause a bug.
				}
			}
		}
	}
}
