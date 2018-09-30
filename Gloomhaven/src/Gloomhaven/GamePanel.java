package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener{

	//Testing Variables
	int NUMPLAYERS=1;
	
	public enum GameState {
	    TITLE_STATE,
	    TESTING_SETUP,
	    PARTY_SETUP,
	    TOWN,
	    ROAD_EVENT,
	    SCENARIO,
	    END;
	}
	
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
		//[Temp] Will have a phase for party setup
		//Adds the players to the party
		for(int id=0; id<NUMPLAYERS; id++)
			party.add(new Player(id, "Test"));
		
		//[Temp] Need to randomly pick town event, road event
		//[Temp] Need to pick scenario during party setup
		String sceneID="Test";
		scene= new Scenario(sceneID, party);
		
		state=GameState.TOWN;
	}
	
	public void gameManager(Graphics g) {
		
		//[Temp] Prints the Current State of the Game
		System.out.println(state);															
		
		//[Rem] Did if/else so it has to go back and paint screen every state
		//Goes through the game loop town->roadevent->scene->town etc...
		if(state==GameState.TOWN) {
			g.drawString("Town", 10, 25);
			state=GameState.ROAD_EVENT;
		}else if(state==GameState.ROAD_EVENT) {
			g.drawString("Road Event", 10, 25);
			state=GameState.SCENARIO;
		}else if(state==GameState.SCENARIO) {
			g.drawString("Scenario", 10, 25);
			
			//Play a round
			scene.playRound(key, g);
			
			//If the scenario is over, end
			if(scene.finished())
				state=GameState.END;
		}else if(state==GameState.END) {
			
			System.exit(0);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.MAGENTA);
		
		if(!(state==GameState.TESTING_SETUP)) 
			gameManager(g);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		key=e;
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
