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
import Gloomhaven.EventCards.RoadEventCardLoader;
import Gloomhaven.EventCards.RoadEventCardUtilities;

public class Event implements Serializable{
	
	public enum State{
		SELECTION,
		THRESHOLD,
		FINISHED, 
		DONE;
	}
	
	private State state;
	private char k;					//character from keyboard
	private String type;
	private List<EventCard> deck;
	private Random r = new Random();
	private int eventIndex;
	private boolean finished;
	
	public Event(String type, List<EventCard> deck) {
		this.type=type;
		this.deck=deck;
		finished=false;
		eventIndex = r.nextInt(deck.size()-1);
		state=State.SELECTION;
	}
	
	public void playRound(KeyEvent key, Graphics g , List<Player> party , City gloomhaven, Shop shop, List<EventCard> secondDeck) {
		k=UtilitiesGeneral.parseKeyCharacter(key);

		if(deck.get(eventIndex)!=null)
			GUIEvent.drawEventHeader(g, type, deck, eventIndex, state);
		if(state==State.SELECTION) {
			
			GUIEvent.drawSelection(g, deck, eventIndex);
			
			if(key!=null) {
				if(key.getKeyCode()==KeyEvent.VK_1 && deck.get(eventIndex).getChoice()==0) {
					deck.get(eventIndex).setChoice(1);
					deck.get(eventIndex).getResults();
					
					if(type.equals("City"))
						CityEventCardLoader.thresholdForResults(deck.get(eventIndex), gloomhaven);
					else
						RoadEventCardLoader.thresholdForResults(deck.get(eventIndex), gloomhaven);
					
					
				}else if(key.getKeyCode()==KeyEvent.VK_2 && deck.get(eventIndex).getChoice()==0) {
					deck.get(eventIndex).setChoice(2);
					deck.get(eventIndex).getResults();
					
					if(type.equals("City"))
						CityEventCardLoader.thresholdForResults(deck.get(eventIndex), gloomhaven);
					else
						RoadEventCardLoader.thresholdForResults(deck.get(eventIndex), gloomhaven);

				}
				
				if(key.getKeyCode()==KeyEvent.VK_SPACE && deck.get(eventIndex).getChoice()!=0) {
					if(deck.get(eventIndex).hasThreshold())
						state=State.THRESHOLD;
					else
						state=State.FINISHED;
				}
			}
		}
		else if(state==State.THRESHOLD) {

			if(deck.get(eventIndex).getThresholdType().equals("PayCollectiveGold")) {
				GUIEvent.drawThreshold(g, deck, eventIndex);
				//Temp
				//TODO: Need to have it so it can be split between players.
				if(key!=null) {
					
					if(k=='y' && party.get(0).getCharacterData().getGold()>=deck.get(eventIndex).getThresholdAmount()) {
						party.get(0).getCharacterData().changeGold(-1*deck.get(eventIndex).getThresholdAmount());
						state=State.FINISHED;
					}else if(k=='n' || party.get(0).getCharacterData().getGold()<deck.get(eventIndex).getThresholdAmount()) {
						state=State.FINISHED;
					}
				}
			
			}else if(deck.get(eventIndex).getThresholdType().equals("Class")) {
				List<String> classes = CityEventCardLoader.thresholdClassList(deck.get(eventIndex));
				
				for(int i=0; i<party.size(); i++) {
					for(int j=0; j<classes.size(); j++) {
						if(party.get(i).getClass().equals(classes.get(j)))
							deck.get(eventIndex).setThresholdMet(true);
							System.out.println("Event.java -playRound Loc 102: Successfully met class criteria");
					}
				}
				
				state=State.FINISHED;
			}
		}
		else if(state==State.FINISHED) {
			finishRound(party, gloomhaven, shop, secondDeck);
		}

	}
	
	public void finishRound(List<Player> party, City gloomhaven, Shop shop, List<EventCard> secondDeck) {
		finished=true;
		
		if(type.equals("City")) {
			CityEventCardUtilities.resolveCityEvent(deck.get(eventIndex), gloomhaven, party, deck, secondDeck, shop);
			
			if(CityEventCardLoader.destroyCard(deck.get(eventIndex).getID(), deck.get(eventIndex).getChoice()))
				deck.remove(eventIndex);
		}
		else {
			RoadEventCardUtilities.resolveRoadEvent(deck.get(eventIndex), gloomhaven, party, secondDeck);
			
			if(RoadEventCardLoader.destroyCard(deck.get(eventIndex).getID(), deck.get(eventIndex).getChoice()))
				deck.remove(eventIndex);
		}
		
	
		eventIndex=0;
		state=State.DONE;
	}
	
	public int getEventIndex() {return eventIndex;}
	public boolean isFinished() {return finished;}

}
