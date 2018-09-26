package Gloomhaven;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gloomhaven.GamePanel.GameState;
import Gloomhaven.TempStorage.Enemy;
import Gloomhaven.TempStorage.EnemyAbilityCards;
import Gloomhaven.TempStorage.Goal;
import Gloomhaven.TempStorage.ScenarioRoom;

public class Scenario {

	public enum State {
	    SETUP,
	    CARD_SELECTION,
	    INITIATIVE,
	    ATTACK,
	    ENEMYATTACK,
	    PLAYERCHOICE,
	    PLAYERDEFENSE,
	    ENEMYDEFENSE,
	    PLAYERCARD,
	    END;
	}
	
	State state;													//State of the scenario
	boolean finished;												//Tells the gamepanel if the scenario is finished
	List<Player> party = new ArrayList<Player>();					//Party
	
	//Key variables
	char k;															//Character from key
	int num;														//Number from Key
	
	private int currentPlayer;
	
	public Scenario(int sceneID, List<Player> party) {
		state=State.SETUP;
		finished=false;
		this.party=party;
		
		if(sceneID==1) {

		}
		currentPlayer=0;											//sets currentplayer to 0 for the card selection around
		num=-1;
		state=State.CARD_SELECTION;
	}
	
	
	public void playRound(KeyEvent key, Graphics g) {
		parseKey(key);
		if(state==State.CARD_SELECTION) {
			party.get(currentPlayer).pickCards(num, g);
			if(party.get(currentPlayer).cardsLocked()) {
				if((currentPlayer+1)!=party.size())
					currentPlayer++;
				else {
					currentPlayer=0;
					state=State.INITIATIVE;
				}
			}
		}
		if(state==State.INITIATIVE) {
			//Pick enemy card for initiative
			//order players
			//change state to attack
		}
		
		
		//[Temp] Press t to end the scenario
		if(k=='t') {
			state=State.END;
		}
		
	}
	
	public boolean finished() {
		if(state==State.END) {
			return true;
		}
		else
			return false;
	}
	
	/*
	 * [Rem] Should be used in a class that handles functions that will be used in other places
	 * Takes in key event and turns it into a character or a number
	 * Catches exceptions so it doesn't give errors
	 */
	private void parseKey(KeyEvent key) {
		k = key.getKeyChar();
		num = -1;
		try{
			num=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ // handle your exception
			   System.out.println("");
			}
	}
	
}
