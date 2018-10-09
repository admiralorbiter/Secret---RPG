package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener{
	
	public enum GameState {
	    TITLE_STATE,
	    TESTING_SETUP,
	    PARTY_SETUP,
	    TOWN,
	    ROAD_EVENT,
	    SCENARIO,
	    END;
	}
	Setting setting = new Setting();
	GameState state=GameState.TESTING_SETUP;						//State of the Game
	List<Player> party = new ArrayList<Player>();					//Party
	Scenario scene;													//Current Scenario
	KeyEvent key;													//Current Key Event

	public GamePanel() {
		addKeyListener(this);
		setBackground(new Color(64, 64, 64));
		setDoubleBuffered(true);
		setFocusable(true);
		if(state==GameState.TESTING_SETUP) {
			initGame();
		}	
		repaint();	
	}
	
	//Initials Scenario, Town Event, Road Event
	void initGame() {
		//[Temp] Need unique IDs and class 
		for(int id=0; id<setting.getNumPlayers(); id++)
			party.add(new Player(id, "Test"));						//Adds the players to the party
		scene= new Scenario(setting.getSceneID(), party);			//Creates the scenario
		state=GameState.TOWN;										//Init Phase -> Town Phase
	}
	
	public void gameManager(Graphics g) {	
		//Goes through the game loop town->roadevent->scene->town etc...
		if(state==GameState.TOWN) {
			g.drawString("Town", setting.getGraphicsX(), setting.getGraphicsYTop());
			//Insert Town State Stuff Here
			state=GameState.ROAD_EVENT;
		}else if(state==GameState.ROAD_EVENT) {
			g.drawString("Road Event", setting.getGraphicsX(),  setting.getGraphicsYTop());
			//Insert Road Event Stuff here
			state=GameState.SCENARIO;
		}else if(state==GameState.SCENARIO) {
			g.drawString("Scenario", setting.getGraphicsX(),  setting.getGraphicsYTop());
			scene.playRound(key, g);								//Play Round
			if(scene.finished())									//If scenario is off, end state of game
				state=GameState.END;
		}else if(state==GameState.END) {
			System.exit(0);											//[Temp] End of the game, just exit program
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(setting.getDefaultColor());						//Sets the paint component to the default color	
		if(!(state==GameState.TESTING_SETUP)) 						//If it isn't in the setup, then go through game manager
			gameManager(g);
	}
	
	//Repaints if key is pressed and uses that key
	@Override
	public void keyPressed(KeyEvent e) {
		key=e;
		repaint();
	}

	//repaints if key released, but sets the key to null just to repaint graphics
	@Override
	public void keyReleased(KeyEvent e) {
		key=null;
		repaint();
	}

	//Does nothing
	@Override public void keyTyped(KeyEvent e) {}
}
