package Gloomhaven;

import java.awt.Graphics;
import java.util.List;

import Gloomhaven.Characters.Player;

public final class GUIScenario {
	
	public static void EntityTable(Graphics g, List<Player> party) {
		g.setColor(Setting.defaultColor);
		g.drawRect(Setting.graphicsXRight, Setting.graphicsYMid, 200, 300);
		g.drawString("Entity Table", Setting.graphicsXRight+10, Setting.graphicsYMid+Setting.rowSpacing);
		
		for(int i=0; i<party.size(); i++)
			g.drawString(party.get(i).getName(), Setting.graphicsXRight+10, Setting.graphicsYMid+Setting.rowSpacing*2);
	}
}
