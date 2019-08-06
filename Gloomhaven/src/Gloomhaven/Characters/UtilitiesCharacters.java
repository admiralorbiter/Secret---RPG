package Gloomhaven.Characters;

import Gloomhaven.AttackModifier.AttackModifierCard;
import Gloomhaven.AttackModifier.AttackModifierDeck;
import Gloomhaven.CardDataObject.CardDataObject;

public final class UtilitiesCharacters {
	public static  int getAttack(Character entity, CardDataObject attackCard, AttackModifierDeck attackModifierDeck, boolean advantage, boolean disadvantage) {
		
		
		
		System.out.println("Attack Break Down: (Loc: Player.java -getAttack Line843");
		AttackModifierCard card = attackModifierDeck.pickRandomModifierCard();
		
		if(card.hasBless() || advantage==true || entity.getPositiveConditions().isBless()) {
			if(card.hasBless()) {
				attackModifierDeck.remove(card);
				card = attackModifierDeck.pickRandomModifierCard();
			}
			
			AttackModifierCard secondCard = attackModifierDeck.pickRandomModifierCard();
			
			System.out.println("Card 1 - A:"+attackCard.getData().getAttack()+"  +AM:"+card.getPlusAttack()+"  XM:"+card.getMultiplier());
			System.out.println("Card 2 - A:"+attackCard.getData().getAttack()+"  +AM:"+secondCard.getPlusAttack()+"  XM:"+secondCard.getMultiplier());
			
			if(attackModifierDeck.firstIsBest(card, secondCard)==false)
				card=secondCard;
		}
		
		if(card.hasCurse() || disadvantage==true || entity.getNegativeConditions().isCurse()) {
			if(card.hasCurse()) {
				attackModifierDeck.remove(card);
				card = attackModifierDeck.pickRandomModifierCard();
			}
			
			AttackModifierCard secondCard = attackModifierDeck.pickRandomModifierCard();
			
			System.out.println("Card 1 - A:"+attackCard.getData().getAttack()+"  +AM:"+card.getPlusAttack()+"  XM:"+card.getMultiplier());
			System.out.println("Card 2 - A:"+attackCard.getData().getAttack()+"  +AM:"+secondCard.getPlusAttack()+"  XM:"+secondCard.getMultiplier());
			
			if(attackModifierDeck.firstIsBest(card, secondCard))
				card=secondCard;
		}
		
		System.out.println("Final Attack Mod - A:"+attackCard.getData().getAttack()+"  +AM:"+card.getPlusAttack()+"  XM:"+card.getMultiplier());
		
		int damage = (attackCard.getData().getAttack()+card.getPlusAttack())*card.getMultiplier();
		
		if(entity.getRoundTriggers().size()>0) {
			for(int i=0; i<entity.getRoundTriggers().size(); i++) {
				if(entity.getRoundTriggers().get(i).getTriggerFlag().equals("EnemyAlone")){
					damage=damage+entity.getRoundTriggers().get(i).getBonusData().getAttack();
					entity.getCharacterData().setXp(entity.getCharacterData().getXp()+entity.getRoundTriggers().get(i).getBonusData().getXpOnUse());
				}
			}
		}
		
		if(card.getShuffle())
			attackModifierDeck.shuffleDeck();
		
		return damage;
	}
}
