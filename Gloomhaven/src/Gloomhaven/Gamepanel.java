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

import GUI.GUIGamepanel;
import Gloomhaven.BattleGoals.BattleGoalCard;
import Gloomhaven.BattleGoals.BattleGoalCardUtilities;
import Gloomhaven.BattleGoals.BattleGoalSelection;
import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.EventCard;
import Gloomhaven.Hex.UtilitiesHex;
import Gloomhaven.Scenario.Scenario;
import Unsorted.City;
import Unsorted.Setting;


public class Gamepanel extends JPanel implements KeyListener, MouseListener{
	
	public enum GameState {
	    TITLE_STATE,
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
	private EventHandler event;														//Event Holder
	private Point mouseClick=null;												//Mouse Pixels Holder
	private int partyIndex=0;													//Party index for selection and turns
	private BattleGoalSelection battleGoalSelection=null;						//Holds two battle goals to be selected by player(s)
	
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
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("gloomhavenSave.ser"))){				//Read save game data if it exists
			gloomhaven = (City)in.readObject();
            System.out.println("Read in City and Party Data");
		}catch(IOException ex) 
        { 
            System.out.println("No save file."); 
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		scene= new Scenario(Setting.sceneID, party, gloomhaven, shop);												//Creates the scenario
		
		if(Setting.straightToScenario)																				//Starts game loop
			state=GameState.SCENARIO;
		else
			state=GameState.TITLE_STATE;
	}
	
	/**
	 * Goes through the game loop town->roadevent->scene->town etc... 
	 * @param g Graphics Object
	 */
	private void gameManager(Graphics2D g) {	

		if(state==GameState.TITLE_STATE) {																			//[Title State]
			GUIGamepanel.drawTitle(g);																				//Draw title screen
			if((key!=null) && (key.getKeyCode()==KeyEvent.VK_SPACE))												//If space is pressed
				state=GameState.TOWN;																				//Title State -> Town State
			
		}
		else if(state==GameState.TOWN) {																			//[Town State]
			
			shop.drawAndUpdateShop(g, party.get(partyIndex), mouseClick);											//Draw Shop and Update/Buy Items

			if((key!=null) && partyIndex==(party.size()-1) && (key.getKeyCode()==KeyEvent.VK_SPACE)) {				//If all party shopped and space pressed
				partyIndex=0;																						//Resets Party Index
				event = new EventHandler("City", cityDeck);																//Draws a new city event card							
				state=GameState.CITY_EVENT;																			//Town State -> City Event State																	
			}else if((key!=null) && (key.getKeyCode()==KeyEvent.VK_SPACE)) {
				partyIndex++;																						//Procedes to the next party member
			}
		}
		else if(state==GameState.CITY_EVENT) {																		//[City Event State]
			event.playRound(key, g, party, gloomhaven, shop, roadDeck);												//Resolve City Event Round
			
			if(key!=null && event.isFinished() && key.getKeyCode()==KeyEvent.VK_SPACE) {							//If event is resolved and space pressed
				event = new EventHandler("Road", roadDeck);																//Draw a new Road Event card
				state=GameState.ROAD_EVENT;																			//City Event State -> Road Event State
			}
		}
		else if(state==GameState.ROAD_EVENT) {																		//[Road Event State]
			event.playRound(key, g, party, gloomhaven, shop, cityDeck);												//Resolve Road Event Round
			
			if(key!=null && event.isFinished() && key.getKeyCode()==KeyEvent.VK_SPACE) {							//If event is resolved and space pressed
					partyIndex=0;																					//Party Index Reset
					battleGoalSelection = new BattleGoalSelection(battleGoalDeck);									//Battle Goals Randomly Drawn			
					state=GameState.PICK_BATTLE_GOAL_CARD;															//Road Event State -> Pick Battle Goal Card
			}
		}
		else if(state==GameState.PICK_BATTLE_GOAL_CARD) {															//[Pick Battle Goal Card State]
			
			if(battleGoalSelection.chooseCard(g, key, party.get(partyIndex), battleGoalDeck)) {						//Resolves battle goal selection
				partyIndex++;																						//Goes to the next player
				if(partyIndex==party.size()) {																		//If all the party has selected change state
					partyIndex=0;																					//Reset party index
					state=GameState.SCENARIO_TEXT;																	//Pick Battlegoal State-> Scenario Text state
				}else {																								
					battleGoalSelection = new BattleGoalSelection(battleGoalDeck);									//If players are left, select two more cards
				}
			}
		}
		else if(state==GameState.SCENARIO_TEXT) {																	//[Scenario Text State]
			
			GUIGamepanel.drawScenarioIntro(g);																		//Draw scenario intro
			if(key!=null && key.getKeyCode()==KeyEvent.VK_SPACE)													//If space is pressed go to next state
					state=GameState.SCENARIO;																		//Scenario Text State -> Scenario State
		}
		else if(state==GameState.SCENARIO) {																		//[Scenario State]
			
			if(scene.playRound(key, g, mouseClick))																	//Start Scenario Loop and continue until it ends
				state=GameState.END;																				//Scenario State -> End State
			
		}else if(state==GameState.END) {																			//[End State]
			
			for(int i=0; i<party.size(); i++) {																		//Go through party resolving end of scenario
				BattleGoalCardUtilities.evaluateBattleGoals(party.get(i));											//Evaluates each battle goal
				party.get(i).getStats().endScenario();																//Resolves and resets stats									
			}

			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("gloomhavenSave.ser"))){		//Saves Data
				out.writeObject(gloomhaven);
				System.out.println("City and Party has been saved.");
			}  catch(IOException ex) 
	        { 
	            System.out.println("IOException is caught"); 
	        } 
			
			System.exit(0);																							//[TODO] End of the game, just exit program
		}
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;											//Converts graphics go 2d graphics
		super.paintComponent(g);
		g.setColor(Setting.defaultColor);										//Sets the paint component to the default color	
		gameManager(g);															//Runs the game manager everytime repaint is called					
	}
	
	@Override
	public void keyPressed(KeyEvent e) {										//Key stored when key pressed and repaints graphics
		key=e;
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {										//Key set to null when released and repaints graphics
		key=null;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int xClick=arg0.getX();
		int yClick=arg0.getY();
		mouseClick = new Point(xClick, yClick);
		UtilitiesHex.getOffsetHexFromPixels(new Point(xClick, yClick), scene.getData().getHexLayout());
		repaint();
	}

	/**
	 * Unused mouse and key methods
	 */
	@Override public void mouseReleased(MouseEvent arg0) {}
	@Override public void keyTyped(KeyEvent e) {}
	@Override public void mouseClicked(MouseEvent arg0) {}
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}

}
