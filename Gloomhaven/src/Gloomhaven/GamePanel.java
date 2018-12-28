package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import Gloomhaven.CardDataObject.NegativeConditions;
import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.CityEventCardLoader;
import Gloomhaven.EventCards.CityEventCardUtilities;
import Gloomhaven.EventCards.EventCard;
import Gloomhaven.EventCards.RoadEventCardUtilities;


public class GamePanel extends JPanel implements KeyListener, MouseListener{
	
	public enum GameState {
	    TITLE_STATE,
	    TESTING_SETUP,
	    PARTY_SETUP,
	    TOWN,
	    CITY_EVENT,
	    ROAD_EVENT,
	    SCENARIO,
	    END;
	}
	Setting setting = new Setting();
	GameState state=GameState.TESTING_SETUP;						//State of the Game
	List<Player> party = new ArrayList<Player>();					//Party
	Scenario scene;													//Current Scenario
	KeyEvent key;													//Current Key Event
	City gloomhaven = new City();
	List<EventCard> cityDeck = new ArrayList<EventCard>();
	List<EventCard> roadDeck = new ArrayList<EventCard>();
	Shop shop = new Shop(gloomhaven.getProspLevel());
	Event event;
	int xClick=-99;
	int yClick=-99;

	public GamePanel() {
		
		for(int i=1; i<=30; i++) {
			//cityDeck.add(new EventCard("City", i));
			if(i<10)
				roadDeck.add(new EventCard("Road", i));
		}

		cityDeck.add(new EventCard("City", 6));
		cityDeck.add(new EventCard("City", 6));
		cityDeck.add(new EventCard("City", 6));
		cityDeck.add(new EventCard("City", 6));
		cityDeck.add(new EventCard("City", 6));
		
		addKeyListener(this);
		addMouseListener(this);
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
			party.add(new Player(id, setting.getPlayerClass()));				//Adds the players to the party
		scene= new Scenario(setting.getSceneID(), party, gloomhaven);			//Creates the scenario
		shop.setMaxPlayers(party.size());
		state=GameState.TOWN;										//Init Phase -> Town Phase
	}
	
	public void gameManager(Graphics g) {	
		//Goes through the game loop town->roadevent->scene->town etc...
		if(state==GameState.TOWN) {
			g.drawString("Town", setting.getGraphicsX(), setting.getGraphicsYTop());
			shop.drawShop(g, party, xClick, yClick);
			g.drawString("Press space to continue", 10, setting.getHeight()-100);
			//Insert Town State Stuff Here
			if(key!=null)
				if(key.getKeyCode()==KeyEvent.VK_SPACE)
					if(shop.atLastPartyMember()) {
						event = new Event("City", cityDeck);
						state=GameState.CITY_EVENT;
					}
		}else if(state==GameState.CITY_EVENT) {
			event.playRound(key, g, party, gloomhaven, shop);
			
			if(key!=null) {
				if(event.isFinished()) {
					event = new Event("Road", roadDeck);
					state=GameState.ROAD_EVENT;
				}
			}
		}else if(state==GameState.ROAD_EVENT) {
			event.playRound(key, g, party, gloomhaven, shop);
			
			if(key!=null) {
				if(event.isFinished()) {
					//event = null;
					state=GameState.SCENARIO;
				}
			}
		}else if(state==GameState.SCENARIO) {
			g.drawString("Scenario", 0,  setting.getHeight()-30);
			scene.playRound(key, g);								//Play Round
			//if(scene.finished())									//If scenario is off, end state of game
				//state=GameState.END;
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		xClick=arg0.getX();
		yClick=arg0.getY();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
