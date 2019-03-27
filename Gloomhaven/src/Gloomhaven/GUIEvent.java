package Gloomhaven;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import Gloomhaven.Event.State;
import Gloomhaven.EventCards.EventCard;

public final class GUIEvent {
	
	public static void drawEventHeader(Graphics g, String type, List<EventCard> deck, int eventIndex, State state) {
		g.setFont(FontSettings.bigText);
		g.drawString(type+" "+deck.get(eventIndex).getID()+"         "+state, GUISettings.gLeft,  GUISettings.gTop);
		
		g.drawString(deck.get(eventIndex).getText(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText);
		
		if(type.equals("City"))
			g.drawImage(new ImageIcon("src/Gloomhaven/img/GloomhavenCity1.png").getImage(), GUISettings.gMidX, GUISettings.gYQ1, GUISettings.eventImageW, GUISettings.eventImageH, null);
	
		if(type.equals("Road"))
			g.drawImage(new ImageIcon("src/Gloomhaven/img/GloomhavenRoad.png").getImage(), GUISettings.gMidX, GUISettings.gYQ1, GUISettings.eventImageW, GUISettings.eventImageH, null);
	}
	
	public static void drawSelection(Graphics g, List<EventCard> deck, int eventIndex) {
		g.setFont(FontSettings.bigText);
		
		g.drawString("1: "+deck.get(eventIndex).getOptionA(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*2);
		g.drawString("2: "+deck.get(eventIndex).getOptionB(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*3);
		
		if(deck.get(eventIndex).getChoice()!=0) {
			g.drawString(deck.get(eventIndex).getResults(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*4);
			g.drawString("Press space to continue", GUISettings.gLeft, GUISettings.gBottom);
		}
	}
	
	public static void drawThreshold(Graphics g, List<EventCard> deck, int eventIndex) {
		g.setFont(FontSettings.bigText);
		g.drawString("You must collective pay: "+deck.get(eventIndex).getThresholdAmount(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*2);
		g.drawString("Press y to take on "+deck.get(eventIndex).getThresholdAmount()+"   n to refuse to pay.", GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*3);
	}
}
