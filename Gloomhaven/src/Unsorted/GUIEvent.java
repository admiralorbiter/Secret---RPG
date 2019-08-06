package Unsorted;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import Gloomhaven.EventCards.EventCard;
import Gloomhaven.EventCards.EventCard.Choice;
import Gloomhaven.EventHandler.State;

public final class GUIEvent {
	
	public static void drawEventHeader(Graphics g, String type, EventCard eventCard, State state) {
		g.setFont(FontSettings.bigText);
		g.drawString(type+" "+eventCard.getID()+"         "+state, GUISettings.gLeft,  GUISettings.gTop);
		
		g.drawString(eventCard.getText(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText);
		
		if(type.equals("City"))
			g.drawImage(new ImageIcon("src/Gloomhaven/img/GloomhavenCity1.png").getImage(), GUISettings.gMidX, GUISettings.gYQ1, GUISettings.eventImageW, GUISettings.eventImageH, null);
	
		if(type.equals("Road"))
			g.drawImage(new ImageIcon("src/Gloomhaven/img/GloomhavenRoad.png").getImage(), GUISettings.gMidX, GUISettings.gYQ1, GUISettings.eventImageW, GUISettings.eventImageH, null);
	}
	
	public static void drawSelection(Graphics g, EventCard eventCard) {
		g.setFont(FontSettings.bigText);
		
		g.drawString("1: "+eventCard.getOptionA(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*2);
		g.drawString("2: "+eventCard.getOptionB(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*3);
		
		if(eventCard.getChoice()!=Choice.NONE) {
			g.drawString(eventCard.getResults(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*4);
			g.drawString("Press space to continue", GUISettings.gLeft, GUISettings.gBottom);
		}
	}
	
	public static void drawThreshold(Graphics g, EventCard eventCard) {
		g.setFont(FontSettings.bigText);
		g.drawString("You must collective pay: "+eventCard.getThresholdAmount(), GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*2);
		g.drawString("Press y to take on "+eventCard.getThresholdAmount()+"   n to refuse to pay.", GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText*3);
	}
}
