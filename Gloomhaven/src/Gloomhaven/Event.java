package Gloomhaven;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.CityEventCardLoader;
import Gloomhaven.EventCards.CityEventCardUtilities;
import Gloomhaven.EventCards.EventCard;
import Gloomhaven.EventCards.RoadEventCardLoader;
import Gloomhaven.EventCards.RoadEventCardUtilities;

public class Event {
	
	public enum State{
		SELECTION,
		THRESHOLD,
		PAY_COLLECTIVE_GOLD,
		FINISHED, 
		DONE;
	}
	Setting setting = new Setting();
	private State state;
	private char k;					//character from keyboard
	private int num;				//number from keyboard
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
	
	public void playRound(KeyEvent key, Graphics g , List<Player> party , City gloomhaven, Shop shop) {
		k=UtilitiesGeneral.parseKeyCharacter(key);
		num=UtilitiesGeneral.parseKeyNum(key);
		if(deck.get(eventIndex)!=null)
			g.drawString(type+" "+deck.get(eventIndex).getID()+"         "+state, setting.getGraphicsX(),  setting.getGraphicsYTop());
		if(state==State.SELECTION) {
			g.drawString("1: "+deck.get(eventIndex).getOptionA(), setting.getGraphicsX(), setting.getGraphicsYTop()+50);
			g.drawString("2: "+deck.get(eventIndex).getOptionB(), setting.getGraphicsX(), setting.getGraphicsYTop()+75);
			
			if(deck.get(eventIndex).getChoice()!=0) {
				g.drawString(deck.get(eventIndex).getResults(), setting.getGraphicsX(), setting.getGraphicsYTop()+150);
				g.drawString("Press space to continue", 10, setting.getHeight()-100);
			}
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
				
				if(key!=null) {
					if(key.getKeyCode()==KeyEvent.VK_SPACE && deck.get(eventIndex).getChoice()!=0) {
						if(deck.get(eventIndex).hasThreshold())
							state=State.THRESHOLD;
						else
							state=State.FINISHED;
					}
				}
			}
		}
		else if(state==State.THRESHOLD) {

			if(deck.get(eventIndex).getThresholdType().equals("PayCollectiveGold")) {
				g.drawString("You must collective pay: "+deck.get(eventIndex).getThresholdAmount(), setting.getGraphicsX(), setting.getGraphicsYTop()+50);
				g.drawString("Press y to take on "+deck.get(eventIndex).getThresholdAmount()+"   n to refuse to pay.", setting.getGraphicsX(), setting.getGraphicsYTop()+75);
		
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
			finishRound(party, gloomhaven, shop);
		}

	}
	
	public void finishRound(List<Player> party, City gloomhaven, Shop shop) {
		finished=true;
		
		if(type.equals("City")) {
			CityEventCardUtilities.resolveCityEvent(deck.get(eventIndex), gloomhaven, party, deck, shop);
			
			if(CityEventCardLoader.destroyCard(deck.get(eventIndex).getID(), deck.get(eventIndex).getChoice()))
				deck.remove(eventIndex);
		}
		else {
			RoadEventCardUtilities.resolveRoadEvent(deck.get(eventIndex), gloomhaven, party);
			
			if(RoadEventCardLoader.destroyCard(deck.get(eventIndex).getID(), deck.get(eventIndex).getChoice()))
				deck.remove(eventIndex);
		}
		
	
		eventIndex=0;
		state=State.DONE;
	}
	
	public int getEventIndex() {return eventIndex;}
	public boolean isFinished() {return finished;}

}
