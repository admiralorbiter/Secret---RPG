package Gloomhaven;
import java.awt.Graphics;
import java.util.List;

import Gloomhaven.Event.State;
import Gloomhaven.EventCards.EventCard;

public final class GUIEvent {
	
	public static void drawEventHeader(Graphics g, String type, List<EventCard> deck, int eventIndex, State state) {
		g.setFont(FontSettings.heading);
		g.drawString(type+" "+deck.get(eventIndex).getID()+"         "+state, GUISettings.gLeft,  GUISettings.gTop);
	}
	
	public static void drawSelection(Graphics g, List<EventCard> deck, int eventIndex) {
		g.setFont(FontSettings.bigText);
		
		g.drawString("1: "+deck.get(eventIndex).getOptionA(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText);
		g.drawString("2: "+deck.get(eventIndex).getOptionB(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*2);
		
		if(deck.get(eventIndex).getChoice()!=0) {
			g.drawString(deck.get(eventIndex).getResults(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*3);
			g.drawString("Press space to continue", GUISettings.gLeft, GUISettings.gBottom);
		}
	}
	
	public static void drawThreshold(Graphics g, List<EventCard> deck, int eventIndex) {
		g.setFont(FontSettings.bigText);
		g.drawString("You must collective pay: "+deck.get(eventIndex).getThresholdAmount(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText);
		g.drawString("Press y to take on "+deck.get(eventIndex).getThresholdAmount()+"   n to refuse to pay.", GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*2);
	}
}
