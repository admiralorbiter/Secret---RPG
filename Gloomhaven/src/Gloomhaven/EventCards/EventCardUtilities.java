package Gloomhaven.EventCards;

import java.util.List;

import Gloomhaven.Characters.Player;

public final class EventCardUtilities {
	
	public static void changeXP(int xp, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getCharacterData().setXp(party.get(i).getCharacterData().getXp()+xp);
	}
	
	public static void changeGold(int gold, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getCharacterData().changeGold(gold);
	}
	
	public static void addNegativeConditions(String negativeConditions, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getNegativeConditions().setNegativeConditions(negativeConditions);
	}
	
	public static void addPositiveConditions(String positiveConditions, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).getPositiveConditions().setPositiveConditions(positiveConditions);
		
	}
	
	public static void changeBattleGoalTotal(int change, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).changeBattleGoalTotal(-1);
	}
	
	public static void takeDamage(int damage, List<Player> party) {
		for(int i=0; i<party.size(); i++)
			party.get(i).takeDamage(damage);
	}
}
