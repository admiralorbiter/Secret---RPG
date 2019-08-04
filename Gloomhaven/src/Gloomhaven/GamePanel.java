package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
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
import Gloomhaven.Hex.FractionalHex;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.HexLayout;
import Gloomhaven.Hex.UtilitiesHex;
import Gloomhaven.Scenario.Scenario;


public class GamePanel extends JPanel implements KeyListener, MouseListener{
	
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
	
	GameState state=GameState.TESTING_SETUP;						//State of the Game
	List<Player> party = new ArrayList<Player>();					//Party
	Scenario scene;													//Current Scenario
	KeyEvent key;													//Current Key Event
	City gloomhaven = new City();
	List<EventCard> cityDeck = new ArrayList<EventCard>();
	List<EventCard> roadDeck = new ArrayList<EventCard>();
	List<BattleGoalCard> battleGoalDeck = new ArrayList<BattleGoalCard>();
	
	Shop shop = new Shop(gloomhaven.getProspLevel());
	Event event;
	//int xClick=-99;
	//int yClick=-99;
	Point mouseClick=null;
	int partyIndex=0;
	
	BattleGoalSelection battleGoalSelection=null;
	
	public GamePanel() {
		
		for(int i=1; i<=30; i++) {
			cityDeck.add(new EventCard("City", i));
			roadDeck.add(new EventCard("Road", i));
		}
		
		battleGoalDeck = BattleGoalCardUtilities.loadFullDeck();
		
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
		for(int id=0; id<Setting.numberOfPlayers; id++)
			party.add(new Player(id, Setting.playerClass));				//Adds the players to the party
	
		
		//Read save game data
		try {
			FileInputStream cityIn = new FileInputStream("gloomhavenSave.ser"); 
            ObjectInputStream in = new ObjectInputStream(cityIn); 

			gloomhaven = (City)in.readObject();
            in.close();
            cityIn.close();
            
            /*FileInputStream partyIn = new FileInputStream("partySave.ser"); 
            in = new ObjectInputStream(partyIn); 
            
            party = (List<Player>)in.readObject();
            in.close();
            partyIn.close();*/
            
            System.out.println("Read in City and Party Data");
		}catch(IOException ex) 
        { 
            System.out.println("No save file."); 
        }catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scene= new Scenario(Setting.sceneID, party, gloomhaven, shop);			//Creates the scenario
		shop.setMaxPlayers(party.size());
		//state=GameState.TOWN;										//Init Phase -> Town Phase
		if(Setting.straightToScenario)
			state=GameState.SCENARIO;
		else
			state=GameState.TITLE_STATE;
	}
	
	public void gameManager(Graphics2D g) {	
		//Goes through the game loop town->roadevent->scene->town etc...
		if(state==GameState.TITLE_STATE) {
			
			GUIGamepanel.drawTitle(g);
			
			if(key!=null)
				if(key.getKeyCode()==KeyEvent.VK_SPACE)
					if(shop.atLastPartyMember()) {
						event = new Event("City", cityDeck);
						state=GameState.TOWN;
					}
		}
		else if(state==GameState.TOWN) {
			
			shop.drawShop(g, party, mouseClick);
			GUIGamepanel.drawTown(g);
			
			//Insert Town State Stuff Here
			if(key!=null)
				if(key.getKeyCode()==KeyEvent.VK_SPACE)
					if(shop.atLastPartyMember()) {
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
