package Gloomhaven;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import Gloomhaven.Characters.Player;
import Gloomhaven.EventCards.CityEventCardLoader;
import Gloomhaven.EventCards.EventCard;
import Gloomhaven.EventCards.RoadEventCardUtilities;

public class Event {
	
	public enum State{
		SELECTION,
		THRESHOLD,
		PAY_COLLECTIVE_GOLD;
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
		eventIndex = r.nextInt(deck.size())+1;
		state=State.SELECTION;
	}
	
	public void playRound(KeyEvent key, Graphics g , List<Player> party , City gloomhaven) {
		k=UtilitiesGeneral.parseKeyCharacter(key);
		num=UtilitiesGeneral.parseKeyNum(key);
		
		g.drawString(type+" "+deck.get(eventIndex).getID(), setting.getGraphicsX(),  setting.getGraphicsYTop());
		g.drawString("1: "+deck.get(eventIndex).getOptionA(), setting.getGraphicsX(), setting.getGraphicsYTop()+50);
		g.drawString("2: "+deck.get(eventIndex).getOptionB(), setting.getGraphicsX(), setting.getGraphicsYTop()+75);
		
		if(deck.get(eventIndex).getChoice()!=0) {
			g.drawString(deck.get(eventIndex).getResults(), setting.getGraphicsX(), setting.getGraphicsYTop()+150);
			g.drawString("Press space to continue", 10, setting.getHeight()-100);
		}
		if(key!=null) {
			if(key.getKeyCode()==KeyEvent.VK_1 && deck.get(eventIndex).getChoice()==0) {
				deck.get(eventIndex).setChoice(1);
			}else if(key.getKeyCode()==KeyEvent.VK_2 && deck.get(eventIndex).getChoice()==0) {
				deck.get(eventIndex).setChoice(1);
			}
			
			if(key.getKeyCode()==KeyEvent.VK_SPACE && deck.get(eventIndex).getChoice()!=0) {
				
				if(deck.get(eventIndex).hasThreshold())
					state=State.THRESHOLD;
				else
					finishRound(party, gloomhaven);
			}
		}
	}
	
	public void finishRound(List<Player> party, City gloomhaven) {
		finished=true;
		RoadEventCardUtilities.resolveCityEvent(deck.get(eventIndex), gloomhaven, party);
		if(CityEventCardLoader.destroyCard(deck.get(eventIndex).getID(), deck.get(eventIndex).getChoice()))
			deck.remove(eventIndex);
	}
	
	public int getEventIndex() {return eventIndex;}
	public boolean isFinished() {return finished;}
}
