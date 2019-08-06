package GUI;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import Unsorted.FontSettings;
import Unsorted.GUISettings;

/**
 * Utility Class for GUI tied to the Gamepanel
 * @author admir
 * [TODO] - Move to a different utility class
 */
public final class GUIGamepanel {
	
	private static String spaceContinue="Press space to continue";
	
	private GUIGamepanel() {}
	
	public static void drawTitle(Graphics g) {
		g.setFont(FontSettings.bigText);
		g.drawImage(new ImageIcon("src/Gloomhaven/img/GloomhavenTitleScreen.png").getImage(), 0, 0, GUISettings.width, GUISettings.height, null);
		g.drawString(spaceContinue, GUISettings.gLeft, GUISettings.gBottom);
	}

	public static void drawScenarioIntro(Graphics g) {
		g.setFont(FontSettings.bigText);
		g.drawString("Scenario Intro Text", GUISettings.gLeft, GUISettings.gTop);
		g.drawString(spaceContinue, GUISettings.gLeft, GUISettings.gTop+GUISettings.leadingBigText);
		
	}
}
