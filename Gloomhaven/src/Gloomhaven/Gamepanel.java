package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Gloomhaven.BattleGoals.BattleGoalCard;
import Gloomhaven.BattleGoals.BattleGoalCardUtilities;
import Gloomhaven.BattleGoals.BattleGoalSelection;
import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.EventCard;
import Gloomhaven.Hex.UtilitiesHex;
import Gloomhaven.Scenario.Scenario;


public class Gamepanel extends JPanel implements KeyListener, MouseListener{
	
	public enum GameState {
	    TITLE_STATE,
	    TESTING_SETUP,
	    TOWN,
	    CITY_EVENT,
	    ROAD_EVENT,
	    PICK_BATTLE_GOAL_CARD,
	    SCENARIO_TEXT,
	    SCENARIO,
	    END;
	}
	
	private GameState state=GameState.TITLE_STATE;								//State of the Game
	private List<Player> party = new ArrayList<>();								//Party
	private Scenario scene;														//Current Scenario
	private KeyEvent key;														//Current KeyEvent
	private City gloomhaven = new City();										//Data Object with City Information
	private List<EventCard> cityDeck = new ArrayList<>();						//City Deck for the game
	private List<EventCard> roadDeck = new ArrayList<>();						//Road Deck for the game
	private List<BattleGoalCard> battleGoalDeck = new ArrayList<>();			//Battle Goal Deck
	private Shop shop = new Shop(gloomhaven.getProspLevel());					//Shop with items to buy
	private Event event;														//Event Holder
	private Point mouseClick=null;												//Mouse Pixels Holder
	private int partyIndex=0;													//Party index for selection and turns
	//[TODO] - Refactor so each player can choose a battle goal, not just player 1
	private BattleGoalSelection battleGoalSelection=null;						//Holds battle goal selected
	
	public Gamepanel() {
		
		for(int i=1; i<=30; i++) {												//Creates a new city and road deck
			cityDeck.add(new EventCard("City", i));
			roadDeck.add(new EventCard("Road", i));
		}

		battleGoalDeck = BattleGoalCardUtilities.loadFullDeck();				//Loads the full battle goal deck
		
		addKeyListener(this);
		addMouseListener(this);
		setBackground(new Color(64, 64, 64));
		setDoubleBuffered(true);
		setFocusable(true);
		initGame();																//Initializes events and scenario
		repaint();	
	}
	
	/**
	 * Initials Scenario, Town Event, Road Event
	 */
	private void initGame() {

		for(int id=0; id<Setting.numberOfPlayers; id++)							//[TODO] Need unique IDs and class 
			party.add(new Player(id, Setting.playerClass));						//Adds the players to the party
	
		//Reads in saved city data if it exists
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gloomhavenSave.ser"))){																	//Read save game data if it exists
			gloomhaven = (City)in.readObject();
            System.out.println("Read in City and Party Data");
		}catch(IOException ex) 
        { 
            System.out.println("No save file."); 
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		scene= new Scenario(Setting.sceneID, party, gloomhaven, shop);			//Creates the scenario
		shop.setMaxPlayers(party.size());										//Sets player size for shop
		
		if(Setting.straightToScenario)											//Starts game loop
			state=GameState.SCENARIO;
		else
			state=GameState.TITLE_STATE;
	}
	
	/**
	 * Goes through the game loop town->roadevent->scene->town etc... 
	 * @param g Graphics Object
	 */
	private void gameManager(Graphics2D g) {	

		if(state==GameState.TITLE_STATE) {
			
			GUIGamepanel.drawTitle(g);
			if((key!=null) && (key.getKeyCode()==KeyEvent.VK_SPACE))
				state=GameState.TOWN;
			
		}
		else if(state==GameState.TOWN) {
			
			shop.drawShop(g, party, mouseClick);
			GUIGamepanel.drawTown(g);
			
			//Insert Town State Stuff Here
			if((key!=null) && shop.atLastPartyMember() && (key.getKeyCode()==KeyEvent.VK_SPACE)) {
				event = new Event("City", cityDeck);
				state=GameState.CITY_EVENT;
			}
		}
		else if(state==GameState.CITY_EVENT) {
			event.playRound(key, g, party, gloomhaven, shop, roadDeck);
			
			if(key!=null) {
				if(event.isFinished()) {
					event = new Event("Road", roadDeck);
					state=GameState.ROAD_EVENT;
				}
			}
		}
		else if(state==GameState.ROAD_EVENT) {
			event.playRound(key, g, party, gloomhaven, shop, cityDeck);
			
			if(key!=null) {
				if(event.isFinished()) {
					//event = null;
					partyIndex=0;
					battleGoalSelection = new BattleGoalSelection(battleGoalDeck);
					state=GameState.PICK_BATTLE_GOAL_CARD;
				}
			}
		}
		else if(state==GameState.PICK_BATTLE_GOAL_CARD) {

			boolean finished=battleGoalSelection.chooseCard(g, key, party.get(partyIndex), battleGoalDeck);
			
			if(finished==true) {
				partyIndex++;
				if(partyIndex==party.size()) {
					partyIndex=0;
					state=GameState.SCENARIO_TEXT;
				}else {
					battleGoalSelection = new BattleGoalSelection(battleGoalDeck);
				}
			}
		}
		else if(state==GameState.SCENARIO_TEXT) {
			GUIGamepanel.drawScenarioIntro(g);
			
			if(key!=null) {
				if(key.getKeyCode()==KeyEvent.VK_SPACE)
					state=GameState.SCENARIO;
			}
		}
		else if(state==GameState.SCENARIO) {
			if(scene.playRound(key, g, mouseClick))								//Play Round
				state=GameState.END;
			//if(scene.finished())									//If scenario is off, end state of game
				//state=GameState.END;
		}else if(state==GameState.END) {
			for(int i=0; i<party.size(); i++) {
				BattleGoalCardUtilities.evaluateBattleGoals(party.get(i));
				party.get(i).getStats().endScenario();
			}
			//Save Data
			try {
				FileOutputStream cityOut = new FileOutputStream("gloomhavenSave.ser");
				ObjectOutputStream out = new ObjectOutputStream(cityOut);
				
				out.writeObject(gloomhaven);
				out.close();
				cityOut.close();
				
				/*FileOutputStream partyOut = new FileOutputStream("partySave.ser");
				out = new ObjectOutputStream(partyOut);
				
				out.writeObject(party);
				out.close();
				partyOut.close();*/
				
				System.out.println("City and Party has been saved.");
			}  catch(IOException ex) 
	        { 
	            System.out.println("IOException is caught"); 
	        } 
			System.exit(0);											//[Temp] End of the game, just exit program
		}
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		super.paintComponent(g);
		g.setColor(Setting.defaultColor);						//Sets the paint component to the default color	
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
		int xClick=arg0.getX();
		int yClick=arg0.getY();
		mouseClick = new Point(xClick, yClick);
		UtilitiesHex.getOffsetHexFromPixels(new Point(xClick, yClick), scene.data.getHexLayout());
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
