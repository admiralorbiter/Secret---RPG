package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.AbilityCards.UsePlayerAbilityCard;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardDataObject.Effects;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Characters.character;

public final class UtilitiesAB {

	public static void resolveCard(Player player, PlayerAbilityCard abilityCard, InfusionTable elements, Hex[][] board, ScenarioData data) {

		
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

		if(card.getPositiveConditions()!=null)
			player.getPositiveConditions().setPositiveConditions(card.getPositiveConditions());
				
		if(card.getEffects()!=null) {
					
			if(card.getEffects().getHeal()>0 && card.getEffects().getTarget().isSelf())
				player.heal(card.getEffects().getHeal());
			
			if(card.getEffects().getLoot()>0) {
				List<Point> loot = new ArrayList<Point>();
				loot=UtilitiesTargeting.createTargetList(board, card.getEffects().getLoot()+1, player.getCubeCoordiantes(Setting.flatlayout), "Loot", data.getBoardSize());
				
				//room.loot(player, loot);
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
	
	//Note need to have the player choose which direction out of the 3 possible ones to push.
	public static void drawPush(Point oppPoint, Room room, Graphics g) {
		
		room.drawHex(g, (int)oppPoint.getX(), (int)oppPoint.getY());
	}
	
	/*
	//Note need to have the player choose which direction out of the 3 possible ones to push.
	public static void push(Player player, Enemy enemy, int pushRange, Room room) {
		Point playerCoordinate = player.getCoordinate();
		Point coordinates = new Point(enemy.getCoordinate());
		//If player is above it on the x axis, push down
		//if player same level, push right or left
		//if plyaer is below it on the x axis, push up
		if(playerCoordinate.getY()<coordinates.getY()) {
			//Push Down
			for(int i=0; i<pushRange; i++) {
				coordinates.translate(0, 1);
				if(room.isSpaceEmpty(coordinates))
					room.moveEnemy(enemy, coordinates);
				System.out.println(coordinates+","+enemy.getCoordinate());
			}
				
			
		}else if(playerCoordinate.getY()>coordinates.getY()) {
			//Push Up
			for(int i=0; i<pushRange; i++) {
				coordinates.translate(0, -1);
				if(room.isSpaceEmpty(coordinates))
					room.moveEnemy(enemy, coordinates);
				System.out.println(coordinates+","+enemy.getCoordinate());
			}
			
		}else {
			if(playerCoordinate.getX()<coordinates.getX()) {
				//Push Right
				for(int i=0; i<pushRange; i++) {
					coordinates.translate(1, 0);
					if(room.isSpaceEmpty(coordinates))
						room.moveEnemy(enemy, coordinates);
					System.out.println(coordinates+","+enemy.getCoordinate());
				}
			}else {
				//Push Left
				for(int i=0; i<pushRange; i++) {
					coordinates.translate(-1, 0);
					if(room.isSpaceEmpty(coordinates))
						room.moveEnemy(enemy, coordinates);
					System.out.println(coordinates+","+enemy.getCoordinate());
				}
				
			}
		}
	}*/
	
	public static void resolveAttackEnemyOnEnemy(Enemy enemy, Enemy attacker, int attack) {
		int enemyShield=enemy.getCharacterData().getShield();
		if(enemyShield>0){
			attack=attack-enemyShield;
			if(attack<0)
				attack=0;
		}
		enemy.takeDamage(attack);
	}
	
	public static void resolveAttack(Enemy enemy, Player player, PlayerAbilityCard abilityCard, Hex[][] board, boolean adjacentBonus, InfusionTable elements, ScenarioData data) {
		
		//int attack=card.getAttack();
		CardDataObject card =UsePlayerAbilityCard.getCardData(abilityCard);
		
		int attack=attack = 0;

		if(abilityCard.getFlag()=="AltTop")
			attack=6;
		else
			card.getData().getAttack();
		
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
					if(UtilitiesTargeting.targetAloneToAlly(enemy, board, data.getBoardSize())) {
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
		
	//Uses cube coordinates to figure out the distance is correct, then converts it to my coordinate system then displays the hex
	//https://www.redblobgames.com/grids/hexagons/
	/*
	public List<Point> createTargetList(Hex board[][], int range, String quickID, Point dimensions) {
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
	}*/
		
	
	/*
	public static int distance(Point p1, Point p2) {
		int distance=0;
		int cubeCoord1[] = new int[3];
		int cubeCoord2[] = new int[3];
		int x=0, y=0, z=0;
		System.out.println("Finding distance (Util -distance 447) "+p1+"   "+p2);
		if(p1.x%2!=0)
			cubeCoord1=oddToCubeCoord(p1);
		else
			cubeCoord1=evenToCubeCoord(p1);

		if(p2.x%2!=0)
			cubeCoord2=oddToCubeCoord(p2);
		else
			cubeCoord2=evenToCubeCoord(p2);
		
		//System.out.println(cubeCoord1+"   "+cubeCoord2);
		distance=(Math.abs(cubeCoord1[x]-cubeCoord2[x])+Math.abs(cubeCoord1[y]-cubeCoord2[y])+Math.abs(cubeCoord1[z]-cubeCoord2[z]))/2;
		return distance;
	}*/
	
	public static int distance(Point p1, Point p2) {

		int penalty=((p1.y%2==0)&&(p2.y%2!=0)&&(p1.x<p2.x))||((p2.y%2==0)&&(p2.y!=0)&&(p2.x<p1.x))?1:0;
		
		return (int) Math.max(Math.abs(p1.y-p2.y), Math.abs(p1.x-p2.x)+Math.floor(Math.abs(p1.y-p2.y)/2)+penalty)-1;
		
	}
	/*
	public static void drawArrows(Graphics g, Point player, Point opposite) {
		
		Setting setting = new Setting();
		int SIZE_OF_HEX=setting.getSizeOfHex();
		int offsetY=setting.getOffsetY()-SIZE_OF_HEX+SIZE_OF_HEX/3;
		int offsetXP=setting.getOffsetX()+SIZE_OF_HEX+SIZE_OF_HEX/3;
		int offsetXO=setting.getOffsetX()+SIZE_OF_HEX+SIZE_OF_HEX/3;
		int bufferY=-1*setting.getSizeOfHex()/3;
	
		
		if(player.getX()==opposite.getX()) {
			if(player.getY()<opposite.getY()) {
				player.translate(0, 1);
			}
			else if(player.getY()>opposite.getY()) {
				player.translate(0, -1);
			}
		}else if(player.getX()>opposite.getX()){
			if(player.getY()==opposite.getY()) {
				player.translate(-1, 0);
			}
			else if(player.getY()<opposite.getY()) {
				player.translate(-1, 1);
			}
			else if(player.getY()>opposite.getY()) {
				player.translate(0, -1);
			}
		}else {
			if(player.getY()==opposite.getY()) {
				player.translate(1,0);
			}
		}
		
		if(player.getY()%2!=0) {
			offsetXP=offsetXP+setting.getSizeOfHex()/2;
		}
		
		if(opposite.getY()%2!=0) {
			offsetXO=offsetXO+setting.getSizeOfHex()/2;
		}
	
		g.setColor(Color.MAGENTA);
		g.drawLine((int)player.getX()*(SIZE_OF_HEX)+offsetXP,(int) player.getY()*(SIZE_OF_HEX+bufferY)+offsetY, (int)opposite.getX()*(SIZE_OF_HEX)+offsetXO-15,(int) opposite.getY()*(SIZE_OF_HEX+bufferY)+offsetY+15);
		//System.out.println(player+","+SIZE_OF_HEX+","+offsetX+","+offsetY+","+bufferY+","+opposite);
		//System.out.println((int)player.getX()*(SIZE_OF_HEX)+offsetX+","+(int) player.getY()*(SIZE_OF_HEX+bufferY)+offsetY+"     "+ (int)opposite.getX()*(SIZE_OF_HEX)+offsetX+","+(int) opposite.getY()*(SIZE_OF_HEX+bufferY)+offsetY);
	}
	
	public static int getPointFlag(Point player, Point opponent){
		if(player.getX()==opponent.getX()) {
			if(player.getY()<opponent.getY()) {
				return 0;
			}
			else if(player.getY()>opponent.getY()) {
				return 2;
			}
		}else if(player.getX()>opponent.getX()){
			if(player.getY()==opponent.getY()) {
				return 4;
			}
			else if(player.getY()<opponent.getY()) {
				return 5;
			}
			else if(player.getY()>opponent.getY()) {
				return 3;
			}
		}else {
			if(player.getY()==opponent.getY()) {
				return 1;
			}
		}
		return -1;
	}
	*/
	//Quickly checks if anything is in melee range, if it finds something, goes back and does a more thorough target list
	//[Rem] Should evaluate whether it is faster to just create the full list using one function and no quick melee check
	public static boolean checkMeleeRange(character entity, Hex board[][], String lookingForID, Point dimensions) {
		
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
	
	//[Rem] This has to be a way to abstract this for both player and enemies
	public static List<Player> createMeleeTargetList(Hex board[][],List<Player> party, Enemy enemy, Point dimensions){
		List<Player> targets = new ArrayList<Player>();
		checkRange(board, "P", 1, party, targets, enemy, dimensions);
		
		return targets;
	}
	
	public static List<Player> playersInRangeEstimate(Hex board[][], List<Player> party, Enemy enemy, Point dimensions){
		List<Player> targets = new ArrayList<Player>();
		
		if(enemy.getBaseStats().getRange()<=1)
			return targets;
		
		for(int r=2; r<=enemy.getBaseStats().getRange(); r++) {
			checkRange(board, "P", r, party, targets, enemy, dimensions);
		}
		
		return targets;
	}

	//[Need to Do] Remove players that can't be reached
	public static List<Player> playersInRange(List<Player> targets){
		
		return targets;
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

	
	/*
	public static Point function() {
		
		
		if(pointFlag==0) {
			if(num==1) {
				pointToMove=new Point((int)oppPoint.getX()+1, (int)oppPoint.getY());
				//oppPoint=new Point((int)pointToMove.getX()+1, (int)pointToMove.getY());
				finished=true;
			}
			else if(num==2) {
				pointToMove = new Point(oppPoint);
				finished=true;
			}
			else if(num==3) {
				pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()+1);
				finished=true;
			}
		}
		if(pointFlag==1) {
			if(num==1) {
				pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY());
				finished=true;
			}
			else if(num==2) {
				pointToMove=new Point(oppPoint);
				finished=true;
			}
			else if(num==3) {
				pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()+1);
				finished=true;
			}
		}
		else if(pointFlag==2) {
			if(num==1) {
				pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()-1);
				finished=true;
			}
			else if(num==2) {
				pointToMove=new Point(oppPoint);
				finished=true;
			}
			else if(num==3) {
				pointToMove=new Point((int)oppPoint.getX()+1, (int)oppPoint.getY());
				finished=true;
			}
		}
		else if(pointFlag==4) {
			if(num==1) {
				pointToMove=new Point((int)oppPoint.getX()-1, (int)oppPoint.getY()-1);
				finished=true;
			}
			else if(num==2) {
				pointToMove=new Point(oppPoint);
				finished=true;
			}
			else if(num==3) {
				pointToMove=new Point((int)oppPoint.getX()+1, (int)oppPoint.getY()+1);
				finished=true;
			}
		}
		else if(pointFlag==3 || pointFlag==5) {
			if(num==1) {
				pointToMove=new Point((int)oppPoint.getX()-1, (int)oppPoint.getY());
				finished=true;
			}
			else if(num==2) {
				pointToMove=new Point(oppPoint);
				finished=true;
			}
			else if(num==3) {
				pointToMove=new Point((int)oppPoint.getX(), (int)oppPoint.getY()+1);
				finished=true;
			}
		}
	}
	*/
}
