package Gloomhaven;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.Characters.Player;

public final class BattleGoalCardUtilities {

	public static List<BattleGoalCard> loadFullDeck(){
		List<BattleGoalCard> battleGoalDeck = new ArrayList<BattleGoalCard>();
		
		for(int i=458; i<481; i++)
			battleGoalDeck.add(load(i));
		
		return battleGoalDeck;
	}
	
	public static BattleGoalCard load(int id) {
		BattleGoalCard card = new BattleGoalCard(id);
		
		
		switch(id) {
			case 458:
				card.setName("Streamliner");
				card.setText("Have five or more total cards in your hand and discard at the end of the scneario.");
				card.setReward(1);
				return card;
			case 459:
				card.setName("Layabout");
				card.setText("Gain 7 or fewer experience points during the scenario.");
				card.setReward(2);
				return card;
			case 460:
				card.setName("Workhorse");
				card.setText("Gain 13 or more experience points during the scenario.");
				card.setReward(1);
				return card;
			case 461:
				card.setName("Zealot");
				card.setText("Have three or fewer total cards in your hand and discard at the end of the scenario.");
				card.setReward(1);
				return card;
			case 462:
				card.setName("Masochist");
				card.setText("Your current hit point value must be equal to or less than 2 at the end of the scenario.");
				card.setReward(1);
				return card;
			case 463:
				card.setName("Fast Healer");
				card.setText("Your current hit point value must be equal to your maximum hit point value at the end of the scenario.");
				card.setReward(1);
				return card;
			case 464:
				card.setName("Neutralizer");
				card.setText("Cause a trap to be sprung or disarmed on your turn or on the turn of one of your summons during the scneario.");
				card.setReward(1);
				return card;
			case 465:
				card.setName("Plunderer");
				card.setText("Loot a treasure overlay tile during the scenario.");
				card.setReward(1);
				return card;
			case 466:
				card.setName("Protector");
				card.setText("Allow none of your character allies to become exhausted during the scenario.");
				card.setReward(1);
				return card;
			case 467:
				card.setName("Explorer");
				card.setText("Reveal a room tile by opening a door on your turn during the scneario.");
				card.setReward(1);
				return card;
			case 468:
				card.setName("Hoarder");
				card.setText("Loot five or more money tokens during the scenario.");
				card.setReward(1);
				return card;
			case 469:
				card.setName("Indigent");
				card.setText("Loot no money tokens or treasure overlay tiles during the scneario.");
				card.setReward(2);
				return card;
			case 470:
				card.setName("Pacifist");
				card.setText("Kill three or fewer monsters during the scenario.");
				card.setReward(1);
				return card;
			case 471:
				card.setName("Sadist");
				card.setText("Kill five or more monsters during the scenario.");
				card.setReward(1);
				return card;
			case 472:
				card.setName("Hunter");
				card.setText("Kill one or more elite monsters during the scneario.");
				card.setReward(1);
				return card;
			case 473:
				card.setName("Professional");
				card.setText("Use your equipped items a number of times equal to or greater than your level plus 2 during the scenario.");
				card.setReward(1);
				return card;
			case 474:
				card.setName("Aggressor");
				card.setText("Have one or more monsters present on the map at the beginning of every round during the scenario.");
				card.setReward(2);
				return card;
			case 475:
				card.setName("Dynamo");
				card.setText("Kill a monster during the scenario by causing at least 4 more points of damage to it than is necessary.");
				card.setReward(1);
				return card;
			case 476:
				card.setName("Purist");
				card.setText("Use no items during the scenario.");
				card.setReward(2);
				return card;
			case 477:
				card.setName("Opener");
				card.setText("Be the first to kill a monster during the scenario.");
				card.setReward(1);
				return card;
			case 478:
				card.setName("Diehard");
				card.setText("Never allow your current hit point value to drop below half your maxium hi point value (rounded up) during the scenario.");
				card.setReward(1);
				return card;
			case 479:
				card.setName("Executioner");
				card.setText("Kill an undamaged monster with a single attack during the scenario.");
				card.setReward(1);
				return card;
			case 480:
				card.setName("Straggler");
				card.setText("Take only long rests during the scenario.");
				card.setReward(1);
				return card;
			case 481:
				card.setName("Scambler");
				card.setText("Take only short rests during the scenario.");
				card.setReward(1);
				return card;
		}
		
		System.out.println("There has been an error loading battle goal card with id: "+id);
		return card;
	}
}
