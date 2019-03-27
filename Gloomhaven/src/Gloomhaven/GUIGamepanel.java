package Gloomhaven;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public final class GUIGamepanel {
	
	public static void drawTitle(Graphics g) {
		g.setFont(FontSettings.heading);
		g.drawImage(new ImageIcon("src/Gloomhaven/img/GloomhavenTitleScreen.png").getImage(), 0, 0, GUISettings.width, GUISettings.height, null);
		g.drawString("Press space to continue", GUISettings.gLeft, GUISettings.gBottom);
	}
	
	public static void drawTown(Graphics g) {
		g.setFont(FontSettings.heading);
		g.drawString("Town", GUISettings.gLeft, GUISettings.leadingBody);
		g.drawString("Press space to continue", GUISettings.gLeft, GUISettings.gBottom);
	}
	
	public static void drawScenarioIntro(Graphics g) {
		g.setFont(FontSettings.heading);
		g.drawString("Scenario Intro Text", GUISettings.gLeft, GUISettings.gTop);
		g.drawString("Press space to continue", GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText);
		
	}
}
