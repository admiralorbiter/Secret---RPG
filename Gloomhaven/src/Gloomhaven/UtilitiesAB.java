package Gloomhaven;

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
		
		if(card.lootRange>0)
			room.loot(player, card.lootRange);


		if(card.getAugment())
			resolveNewAugmentedCard(player, card, abilityCard);
	
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
	
}
