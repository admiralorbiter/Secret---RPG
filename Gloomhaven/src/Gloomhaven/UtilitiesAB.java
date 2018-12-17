package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.AbilityCards.PlayerAbilityCard;

public final class UtilitiesAB {

	public static void resolveCard(Player player, PlayerAbilityCard abilityCard, InfusionTable elements, Room room) {

		CardDataObject card = new CardDataObject();
		card=UsePlayerAbilityCard.getCardData(abilityCard); //abilityCard.getData()
		
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
			loot=createTargetList(room.getBoard(), card.getLootRange(), player.getCoordinates(), "Loot", room.getDimensions());
			room.loot(player, loot);
		}
		
		if(card.getContinuous()) {
			player.addPersistanceBonus(card);
			/*
			if(card.name.equals("Warding Strength"))
			{
				player.persistanceBonus(1, "Warding Strength");
			}*/
		}
		
		if(card.hasAugment())
			resolveNewAugmentedCard(player, card, abilityCard);
		
		if(card.getSheild()>0)
			player.getCharacterData().setShield(card.getSheild());
		
		if(card.getRetaliateFlag()) {
			player.setRetaliate(card.getRetaliateData());
		}
		
		if(card.getRecoverLostCards()) {
			player.recoverLostCards();
		}
		
		if(card.getLost())
			player.transferToLostPile(abilityCard);
		
		
	
	}
	
	public static void findClosestPlayer(Enemy enemy, List<Player> targets) {
		
	}
	
	//Note need to have the player choose which direction out of the 3 possible ones to push.
	public static void drawPush(Point oppPoint, Room room, Graphics g) {
		
		room.drawHex(g, (int)oppPoint.getX(), (int)oppPoint.getY());
	}
	
	public static Point findOppHex(Player player, Enemy enemy) {
		Point playerCoordinate = new Point(player.getCoordinates());
		Point coordinates = new Point(enemy.getCoordinates());
		
		if(playerCoordinate.getX()==coordinates.getX()) {
			if(playerCoordinate.getY()<coordinates.getY()) {
				playerCoordinate.translate(1, 2);
				return playerCoordinate;
			}
			else if(playerCoordinate.getY()>coordinates.getY()) {
				playerCoordinate.translate(1, -2);
				return playerCoordinate;
			}
		}else if(playerCoordinate.getX()>coordinates.getX()) {
			if(playerCoordinate.getY()==coordinates.getY()) {
				playerCoordinate.translate(-2, 0);
				return playerCoordinate;
			}
			else if(playerCoordinate.getY()<coordinates.getY()) {
				playerCoordinate.translate(-1, 2);
				return playerCoordinate;
			}
			else if(playerCoordinate.getY()>coordinates.getY()) {
				playerCoordinate.translate(-1, -2);
				return playerCoordinate;
			}
		}else {
			if(playerCoordinate.getY()==coordinates.getY()) {
				playerCoordinate.translate(2, 0);
				return playerCoordinate;
			}
		}
		return coordinates;
	}
	
	public static Point findOppHex(Point playerCoordinate, Point coordinates) {
		
		if(playerCoordinate.getX()==coordinates.getX()) {
			if(playerCoordinate.getY()<coordinates.getY()) {
				playerCoordinate.translate(1, 2);
				return playerCoordinate;
			}
			else if(playerCoordinate.getY()>coordinates.getY()) {
				playerCoordinate.translate(1, -2);
				return playerCoordinate;
			}
		}else if(playerCoordinate.getX()>coordinates.getX()) {
			if(playerCoordinate.getY()==coordinates.getY()) {
				playerCoordinate.translate(-2, 0);
				return playerCoordinate;
			}
			else if(playerCoordinate.getY()<coordinates.getY()) {
				playerCoordinate.translate(-1, 2);
				return playerCoordinate;
			}
			else if(playerCoordinate.getY()>coordinates.getY()) {
				playerCoordinate.translate(-1, -2);
				return playerCoordinate;
			}
		}else {
			if(playerCoordinate.getY()==coordinates.getY()) {
				playerCoordinate.translate(2, 0);
				return playerCoordinate;
			}
		}
		return coordinates;
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
	
	public static boolean targetAdjacentToAlly(Enemy enemy, List<Player> party, int playerIndex, Room room) {
		
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(room.getBoard(), 1, enemy.getCoordinates(), "P", room.getDimensions());

		if(targets.size()>0) {
			for(int i=0; i<targets.size(); i++) {
				if(targets.get(i).equals(party.get(playerIndex).getCoordinates())) {
					targets.remove(i);
				}
			}
		}

		if(targets.size()>0)
			return true;
		else
			return false;
	}
	
	private static boolean targetAloneToAlly(Enemy enemy, Room room) {
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(room.getBoard(), 1, enemy.getCoordinates(), "E", room.getDimensions());
		
		if(targets.size()>0)
			return false;
		
		return true;
	}
	
	public static void resolveRetalaite(Enemy enemy, Player player) {
		enemy.takeDamage(player.getRetaliate().getAttack());
		player.increaseXP(player.getRetaliate().getExperience());
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
	
	public static void resolveAttack(Enemy enemy, Player player, CardDataObject card, Room room, boolean adjacentBonus, InfusionTable elements) {
		
		//int attack=card.getAttack();
		int attack = player.getAttack(card);
		System.out.println("Utility Class Damage: "+attack);
		if(player.getBonusNegativeConditions()!=null) {
			if(player.getBonusNegativeConditions().causesNegativeCondition()) {
				negativeConditionOnEnemy(player.getBonusNegativeConditions(), enemy);
				player.resetBonusNegativeConditions();
			}
		}
		
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
		
		if(card.getAloneBonus()) {
			if(targetAloneToAlly(enemy, room)) {
				if(card.getAloneBonusData().getAttack()>0)
					attack=attack+card.getAdjacentBonusData().getAttack();
				
				if(card.getAloneBonusData().getExperience()>0)
					player.increaseXP(card.getAdjacentBonusData().getExperience());
			}
		}
		
		if(adjacentBonus) {
			if(card.getAdjacentBonusData().getAttack()>0)
				attack=attack+card.getAdjacentBonusData().getAttack();
			
			if(card.getAdjacentBonusData().getExperience()>0)
				player.increaseXP(card.getAdjacentBonusData().getExperience());
		}
		
		if(card.getElementalConsumed()) {
			String element=card.getElementalConsumedData().getElementalConsumed();

			if(elements.consume(element)) {
				if(card.getElementalConsumedData().getAttack()>0)
					attack=attack+card.getElementalConsumedData().getAttack();
				
				if(card.getElementalConsumedData().getExperience()>0)
					player.increaseXP(card.getElementalConsumedData().getExperience());
			}
		}
		
		if(player.isAugmented()) {
			if(player.getAugmentCard().getName().equals("Feedback Loop"))
				player.getCharacterData().setShield(1);
			if(player.getAugmentCard().getName().equals("The Mind's Weakness"))
				//If target is melee,
				attack=attack+2;
			if(player.getAugmentCard().getName().equals("Parasitic influence"))
				//If target is melle
				player.heal(2);
		}
		
		int enemyShield=enemy.getCharacterData().getShield();
		if(enemyShield>0){
			int playerPierce=card.getPierce();
			if(playerPierce>0) {
				if(enemyShield>playerPierce)
					attack=attack+playerPierce;
				else
					attack=attack+enemyShield;
			}
		}
		
		if(card.getFlag().equals("forEachTargeted")) {
			System.out.println("(Loc: Utilities.java -resolveAttack L315)  Gettings data for targeting" );
			player.increaseXP(card.getforEachTargetedData().getExperience());
		}
		
		System.out.println("Utility Class Damage 2: "+attack);
		enemy.takeDamage(attack);
	}
	
	private static void resolveNewAugmentedCard(Player player, CardDataObject card, PlayerAbilityCard abilityCard) {
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
		StatusEffectDataObject effect = enemy.getStatusEffect();
		
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
	
	private static int[] oddToCubeCoord(Point p) {
		int cubeCoord[] = new int[3];
		int x=0;
		int y=1;
		int z=2;
		
		cubeCoord[x]=p.x-(p.y-(p.y&1))/2;
		cubeCoord[z]=p.y;
		cubeCoord[y]=-cubeCoord[x]-cubeCoord[z];
		
		return cubeCoord;
	}
	
	private static int[] evenToCubeCoord(Point p) {
		int cubeCoord[] = new int[3];
		int x=0;
		int y=1;
		int z=2;
		
		cubeCoord[x]=p.x-(p.y+(p.y&1))/2;
		cubeCoord[z]=p.y;
		cubeCoord[y]=-cubeCoord[x]-cubeCoord[z];
		
		return cubeCoord;
	}
	
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
						if(enemy.coordinates.getX()%2!=0)
							convertedPoint=cubeToCoordOdd(x, y, z);
						else
							convertedPoint=cubeToCoordEven(x, y, z);
						
						int xToPlot=(int)(convertedPoint.getX()+enemy.coordinates.getX());
						int yToPlot=(int) (convertedPoint.getY()+enemy.coordinates.getY());
						
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
