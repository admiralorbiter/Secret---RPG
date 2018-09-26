package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import Gloomhaven.TempStorage.AbilityCards;
import Gloomhaven.TempStorage.Enemy;

public class GamePanel extends JPanel implements KeyListener{

	//Testing Variables
	int numPlayers=1;
	
	public enum GameState {
	    TITLE_STATE,
	    TESTING_SETUP,
	    PARTY_SETUP,
	    TOWN,
	    ROAD_EVENT,
	    SCENARIO,
	    END;
	}
	
	GameState state=GameState.TESTING_SETUP;
	List<Player> party = new ArrayList<Player>();
	Scenario scene;
	KeyEvent key;

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
	
	void initGame() {
		//[Temp] Will have a phase for party setup
		//Adds the players to the party
		for(int i=0; i<numPlayers; i++)
			party.add(new Player());
		
		//[Temp] Need to randomly pick town event, road event
		//[Temp] Need to pick scenario during party setup
		int sceneID=1;
		scene= new Scenario(sceneID, party);
		
		state=GameState.TOWN;
	}
	
	public void gameManager(Graphics g) {
		System.out.println(state);
		
		//[Rem] Did if/else so it has to go back and paint screen every state
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
