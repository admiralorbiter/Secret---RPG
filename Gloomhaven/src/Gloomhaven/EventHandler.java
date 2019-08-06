package Gloomhaven;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.CityEventCardLoader;
import Gloomhaven.EventCards.CityEventCardUtilities;
import Gloomhaven.EventCards.EventCard;
import Gloomhaven.EventCards.EventCard.Choice;
import Unsorted.City;
import Unsorted.GUIEvent;
import Unsorted.UtilitiesGeneral;
import Gloomhaven.EventCards.RoadEventCardLoader;
import Gloomhaven.EventCards.RoadEventCardUtilities;
/**
 * Resolves City and Road Event Cards
 * @author admir
 *
 */
public class EventHandler implements Serializable{
	
	public enum State{
		SELECTION,
		THRESHOLD,
		FINISHED, 
		DONE;
	}
	
	private State state;														//State of the Event
	private String type;														//Type of the Event (City or Road)
	private List<EventCard> deck;												//Event Deck
	private EventCard eventCard=null;											//Random Card Picked
	private boolean finished;	
	private Random r = new Random();
	
	/**
	 * @param type		Type of event (city or road)
	 * @param deck		Event Deck
	 */
	public EventHandler(String type, List<EventCard> deck) {
		this.type=type;
		this.deck=deck;
		finished=false;
		eventCard = deck.get(r.nextInt(deck.size()-1));
		state=State.SELECTION;
	}
	
	/**
	 * Goes through the states of the event card and resolves the card
	 * @param key				KeyEvent object
	 * @param g					Graphics object
	 * @param party				Player list
	 * @param gloomhaven		City object
	 * @param shop				Shop object
	 * @param secondDeck		The other event deck ie( Main is city, second would be road)	
	 */
	public void playRound(KeyEvent key, Graphics g , List<Player> party , City gloomhaven, Shop shop, List<EventCard> secondDeck) {
		char k=UtilitiesGeneral.parseKeyCharacter(key);

		if(eventCard!=null)
			GUIEvent.drawEventHeader(g, type, eventCard, state);													//Draws the event header
		else
			return;
		
		if(state==State.SELECTION) {
			selectionRound(g, key, k, gloomhaven);																	//Resolves selection state for the card choices
		}
		else if(state==State.THRESHOLD) {		
			threholdRound(g, key, k, party, eventCard);																//If card has extra outcomes, resolves those choices
		}
		else if(state==State.FINISHED) {
			finishRound(party, gloomhaven, shop, secondDeck);														//Resolves the outcome of the card and choices
		}

	}
	
	private void selectionRound(Graphics g, KeyEvent key, char k, City gloomhaven) {
		GUIEvent.drawSelection(g, eventCard);																		//Draws the event card choices
		
		if(key!=null) {
			if(key.getKeyCode()==KeyEvent.VK_1 && eventCard.getChoice()==Choice.NONE) {								//If a card hasn't been picked and card picked
				eventCard.setChoice(Choice.TOP);																	//Sets the choice based on key press
				thresholdBasedOnEventType(type, gloomhaven);														//Determines if the card has a threshold based on id and type
				
			}else if(key.getKeyCode()==KeyEvent.VK_2  && eventCard.getChoice()==Choice.NONE) {
				eventCard.setChoice(Choice.BOTTOM);	
				thresholdBasedOnEventType(type, gloomhaven);
			}
			
			if(key.getKeyCode()==KeyEvent.VK_SPACE && eventCard.getChoice()!=Choice.NONE) {
				if(eventCard.hasThreshold())
					state=State.THRESHOLD;
				else
					state=State.FINISHED;
			}
		}
	}
	
	private void thresholdBasedOnEventType(String type, City gloomhaven) {
		if(type.equals("City"))
			CityEventCardLoader.determineThresholdForResults(eventCard, gloomhaven);
		else
			RoadEventCardLoader.determineThresholdForResults(eventCard, gloomhaven);

	}
	
	private void threholdRound(Graphics g, KeyEvent key, char k, List<Player> party, EventCard eventCard) {
		if(eventCard.getThresholdType().equals("PayCollectiveGold")) {												//[PayCollectiveGold]
			GUIEvent.drawThreshold(g, eventCard);																	//Draw choices and event	
			resolvePayCollectiveGold(eventCard, party, key, k);														//Updates the player choices and resolves card
		
		}else if(eventCard.getThresholdType().equals("Class")) {													//[Class]
			List<String> classes = CityEventCardLoader.thresholdClassList(eventCard);								//Create list of classes that trigger event
			
			for(int i=0; i<party.size(); i++) {																		//Goes through the party and checks with each class
				for(int j=0; j<classes.size(); j++) {
					if(party.get(i).getClass().equals(classes.get(j))) {
						setAltChoice();
						eventCard.setThresholdMet(true);	
						System.out.println("Event.java -playRound Loc 102: Successfully met class criteria");
					}
				}
			}
			state=State.FINISHED;
		}
	}
	
	private void resolvePayCollectiveGold(EventCard eventCard, List<Player> party, KeyEvent key, char k) {
		if(key!=null) {	
			if(k=='y' && party.get(0).getCharacterData().getGold()>=eventCard.getThresholdAmount()) {				//If player 1 has enough gold and chooses to spend it, trigger card
				party.get(0).getCharacterData().changeGold(-1*eventCard.getThresholdAmount());						//TODO: Need to have it so it can be split between players.
				eventCard.setThresholdMet(true);
				state=State.FINISHED;
			}else if(k=='n' || party.get(0).getCharacterData().getGold()<eventCard.getThresholdAmount()) {			//If player 1 doesn't have enough gold or chooses no, finish card
				setAltChoice();
				state=State.FINISHED;
			}
		}
	}
	
	private void setAltChoice() {
		if(eventCard.getChoice()==Choice.TOP)
			eventCard.setChoice(Choice.ALTTOP);
		else
			eventCard.setChoice(Choice.ALTBOTTOM);
	}
	
	private void finishRound(List<Player> party, City gloomhaven, Shop shop, List<EventCard> secondDeck) {
		finished=true;
		
		if(type.equals("City")) {
			CityEventCardUtilities.resolveCityEvent(eventCard, gloomhaven, party, deck, secondDeck, shop);			//Resolves outcome of city event
			
			if(CityEventCardLoader.destroyCard(eventCard.getID(), eventCard.getChoice()))							//Destroy city card
				deck.remove(eventCard);
		}
		else {
			RoadEventCardUtilities.resolveRoadEvent(eventCard, gloomhaven, party, secondDeck);						//Resolves road event
			
			if(RoadEventCardLoader.destroyCard(eventCard.getID(), eventCard.getChoice()))							//Destroy road card
				deck.remove(eventCard);
		}
		
		eventCard=null;
		state=State.DONE;
	}
	
	public boolean isFinished() {return finished;}

}
