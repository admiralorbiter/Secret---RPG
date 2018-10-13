package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class UtilitiesAB {

	public static void resolveCard(Player player, PlayerAbilityCards abilityCard, InfusionTable elements, Room room) {

		CardDataObject card = new CardDataObject();
		card=abilityCard.getData();
		
		if(card.getExperience()>0)
			player.increaseXP(card.getExperience());
		
		if(card.infusion())
			infuseElement(card, elements);
		
		if(card.positiveConditions())
			positiveConditionOnPlayer(card, player);
				
		if(card.getHeal()>0)
			player.heal(card.getHeal());
		
		if(card.getLootRange()>0) {
			List<Point> loot = new ArrayList<Point>();
			loot=createTargetList(room.getBoard(), card.getLootRange(), player.getCoordinate(), "Loot", room.getDimensions());
			room.loot(player, loot);
		}
		
		if(card.getContinuous()) {
			if(card.name.equals("Warding Strength"))
			{
				player.persistanceBonus(1, "Warding Strength");
			}
		}


		if(card.getAugment())
			resolveNewAugmentedCard(player, card, abilityCard);
		
		if(card.getSheild()>0)
			player.setShield(card.getSheild());
	
	}
	
	public static void resolveAttack(Enemy enemy, Player player, CardDataObject card) {
		
		int attack=card.getAttack();
		System.out.println("Utility Class Damage: "+attack);
		
		if(card.causesNegativeCondition())
			negativeConditionOnEnemy(card, enemy);
		
		if(card.getAddNegativeConditionsToAttack()) {
			if(card.getName().equals("Submissive Affliction"))
				attack=attack+retrieveNegativeConditions(enemy);
			else if(card.getName().equals("Perverse Edge")) {
				attack=attack+2*retrieveNegativeConditions(enemy);
				player.increaseXP(retrieveNegativeConditions(enemy));
				
			}
		}
		
		if(player.isAugmented()) {
			if(player.getAugmentCard().getName().equals("Feedback Loop"))
				player.setShield(1);
			if(player.getAugmentCard().getName().equals("The Mind's Weakness"))
				//If target is melee,
				attack=attack+2;
			if(player.getAugmentCard().getName().equals("Parasitic influence"))
				//If target is melle
				player.heal(2);
		}
		
		int enemyShield=enemy.getShield();
		if(enemyShield>0){
			int playerPierce=card.getPierce();
			if(playerPierce>0) {
				if(enemyShield>playerPierce)
					attack=attack+playerPierce;
				else
					attack=attack+enemyShield;
			}
		}
		System.out.println("Utility Class Damage 2: "+attack);
		enemy.takeDamage(attack);
		
		if(card.getPush()>0) {
			enemy.push(player.getCoordinate(), card.getPush());
		}
		
	}
	
	private static void resolveNewAugmentedCard(Player player, CardDataObject card, PlayerAbilityCards abilityCard) {
		if(player.isAugmented()==false) {
			player.setAugment(abilityCard);
		}
		else {
			player.replaceAugmentCard(abilityCard);
		}
	}
	
	private static void negativeConditionOnEnemy(CardDataObject card, Enemy enemy) {
		if(card.wound)
			enemy.setNegativeCondition("Wound");
		if(card.curse)
			enemy.setNegativeCondition("Curse");
		if(card.disarm)
			enemy.setNegativeCondition("Disarm");
		if(card.immoblize)
			enemy.setNegativeCondition("Immobilize");
		if(card.muddle)
			enemy.setNegativeCondition("Muddle");
		if(card.poison)
			enemy.setNegativeCondition("Poison");
		if(card.stun)
			enemy.setNegativeCondition("Stun");
	}
	
	private static void positiveConditionOnPlayer(CardDataObject card, Player player) {
		if(card.invisible)
			player.setCondition("Invisible");
		if(card.bless)
			player.setCondition("Bless");
		if(card.strengthen)
			player.setCondition("Strengthen");
	}
	
	private static void infuseElement(CardDataObject card, InfusionTable elements) {
	
		if(card.darkInfusion)
			elements.infuse("Dark");
		
		if(card.lightInfusion)
			elements.infuse("Light");
		
		if(card.fireInfusion)
			elements.infuse("Fire");
		
		if(card.iceInfusion)
			elements.infuse("Ice");
		
		if(card.airInfusion)
			elements.infuse("Air");
		
		if(card.earthInfusion)
			elements.infuse("Earth");

	}
	
	//Retrieves the number of negative conditions on enemy
	private static int retrieveNegativeConditions(Enemy enemy) {
		int count=0;
		StatusEffectDataObject effect = enemy.getStatusEffects();
		
		if(effect.getPoison())
			count++;
		
		if(effect.getWound())
			count++;
		
		if(effect.getImmobilize())
			count++;
		
		if(effect.getDisarm())
			count++;
		
		if(effect.getStun())
			count++;
		
		if(effect.getMuddle())
			count++;
		
		if(effect.getCurse())
			count++;
		
		return count;	
	}
	
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	public static List<Point> createTargetList(Hex board[][], int range, Point starting, String quickID, Point dimensions) {
		List<Point> targets = new ArrayList<Point>();
		
		for(int x=-range; x<=range; x++) {
			for(int y=-range; y<=range; y++) {
				for(int z=-range; z<=range; z++) {
					if(x+y+z==0) {
						Point convertedPoint = new Point();
			
						//Converts cube coord to a coord to plot
						//https://www.redblobgames.com/grids/hexagons/#conversions
						if(starting.getX()%2!=0)
							convertedPoint=cubeToCoordOdd(x, y, z);
						else
							convertedPoint=cubeToCoordEven(x, y, z);

						int xToPlot=(int)(convertedPoint.getX()+starting.getX());
						int yToPlot=(int) (convertedPoint.getY()+starting.getY());

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
	private static Point cubeToCoordEven(int x, int y, int z ) {
		x=x+(z-(z&1))/2;
		y=z;

		Point point = new Point(x, y);
		return point;
	}
	
	//Converts cube coord to a coord to plot
	//https://www.redblobgames.com/grids/hexagons/#conversions
	private static Point cubeToCoordOdd(int x, int y, int z) {
		x=x+(z+(z&1))/2;
		y=z;
		
		Point point = new Point(x, y);
		return point;
	}
	
}
