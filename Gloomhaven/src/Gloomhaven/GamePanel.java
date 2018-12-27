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
	Random r = new Random();
	int randomEvent;
	int xClick=-99;
	int yClick=-99;

	public GamePanel() {
		
		for(int i=1; i<=30; i++) {
			cityDeck.add(new EventCard("City", i));
			if(i<10)
				roadDeck.add(new EventCard("Road", i));
		}
		
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
						randomEvent = r.nextInt(cityDeck.size())+1;
						state=GameState.CITY_EVENT;
					}
		}else if(state==GameState.CITY_EVENT) {
			g.drawString("City Event "+cityDeck.get(randomEvent).getID(), setting.getGraphicsX(),  setting.getGraphicsYTop());
			g.drawString("1: "+cityDeck.get(randomEvent).getOptionA(), setting.getGraphicsX(), setting.getGraphicsYTop()+50);
			g.drawString("2: "+cityDeck.get(randomEvent).getOptionB(), setting.getGraphicsX(), setting.getGraphicsYTop()+75);
			
			if(cityDeck.get(randomEvent).getChoice()!=0) {
				g.drawString(cityDeck.get(randomEvent).getResults(), setting.getGraphicsX(), setting.getGraphicsYTop()+150);
				g.drawString("Press space to continue", 10, setting.getHeight()-100);
			}
			
			if(key!=null) {
				if(key.getKeyCode()==KeyEvent.VK_1 && cityDeck.get(randomEvent).getChoice()==0) {
					cityDeck.get(randomEvent).setChoice(1);
				}else if(key.getKeyCode()==KeyEvent.VK_2 && cityDeck.get(randomEvent).getChoice()==0) {
					cityDeck.get(randomEvent).setChoice(1);
				}
				
				if(key.getKeyCode()==KeyEvent.VK_SPACE && cityDeck.get(randomEvent).getChoice()!=0) {
					CityEventCardUtilities.resolveCityEvent(cityDeck.get(randomEvent), gloomhaven, party, cityDeck, roadDeck);
					if(CityEventCardLoader.destroyCard(cityDeck.get(randomEvent).getID(), cityDeck.get(randomEvent).getChoice()))
						cityDeck.remove(randomEvent);
					randomEvent = r.nextInt(roadDeck.size())+1;
					state=GameState.ROAD_EVENT;
				}
			}
		}else if(state==GameState.ROAD_EVENT) {
			g.drawString("Road Event "+roadDeck.get(randomEvent).getID(), setting.getGraphicsX(),  setting.getGraphicsYTop());
			//Insert Road Event Stuff here
			
			g.drawString("1: "+roadDeck.get(randomEvent).getOptionA(), setting.getGraphicsX(), setting.getGraphicsYTop()+50);
			g.drawString("2: "+roadDeck.get(randomEvent).getOptionB(), setting.getGraphicsX(), setting.getGraphicsYTop()+75);
			
			if(roadDeck.get(randomEvent).getChoice()!=0) {
				g.drawString(roadDeck.get(randomEvent).getResults(), setting.getGraphicsX(), setting.getGraphicsYTop()+150);
				g.drawString("Press space to continue", 10, setting.getHeight()-100);
			}
			
			if(key!=null) {
				if(key.getKeyCode()==KeyEvent.VK_1 && roadDeck.get(randomEvent).getChoice()==0) {
					roadDeck.get(randomEvent).setChoice(1);
				}else if(key.getKeyCode()==KeyEvent.VK_2 && roadDeck.get(randomEvent).getChoice()==0) {
					roadDeck.get(randomEvent).setChoice(1);
				}
				
				if(key.getKeyCode()==KeyEvent.VK_SPACE && roadDeck.get(randomEvent).getChoice()!=0) {
					RoadEventCardUtilities.resolveCityEvent(roadDeck.get(randomEvent), gloomhaven, party);
					if(CityEventCardLoader.destroyCard(roadDeck.get(randomEvent).getID(), roadDeck.get(randomEvent).getChoice()))
						roadDeck.remove(randomEvent);
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
