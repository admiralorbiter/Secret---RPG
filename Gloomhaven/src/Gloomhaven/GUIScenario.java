package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;

public final class GUIScenario {
	
	public static void EntityTable(Graphics g, List<Player> party , List<Enemy> enemies) {
		g.setColor(Setting.defaultColor);
		g.drawRect(Setting.graphicsXRight, Setting.graphicsYMid, 200, 300);
		g.drawString("Entity Table", Setting.graphicsXRight+10, Setting.graphicsYMid+Setting.rowSpacing);
		
		int entityCount=2;
		
		for(int i=0; i<party.size(); i++) {
			g.setColor(Setting.defaultColor);
			g.drawString(party.get(i).getName(), Setting.graphicsXRight+10, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
			
			if(party.get(i).getPositiveConditions()!=null) {
				g.setColor(Color.YELLOW);
				if(party.get(i).getPositiveConditions().isBless())
					g.drawString("B", Setting.graphicsXRight+70, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.gray);
				if(party.get(i).getPositiveConditions().isInvisibility())
					g.drawString("I", Setting.graphicsXRight+80, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.red);
				if(party.get(i).getPositiveConditions().isStrengthen())
					g.drawString("S", Setting.graphicsXRight+90, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
			}
			
			if(party.get(i).getNegativeConditions()!=null) {
				g.setColor(new Color(238,130,238));
				if(party.get(i).getNegativeConditions().isCurse())
					g.drawString("C", Setting.graphicsXRight+100, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.ORANGE);
				if(party.get(i).getNegativeConditions().isDisarm())
					g.drawString("D", Setting.graphicsXRight+110, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.cyan);
				if(party.get(i).getNegativeConditions().isImmobilize())
					g.drawString("I", Setting.graphicsXRight+120, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.red);
				if(party.get(i).getNegativeConditions().isWound())
					g.drawString("W", Setting.graphicsXRight+125, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.LIGHT_GRAY);
				if(party.get(i).getNegativeConditions().isMuddle())
					g.drawString("M", Setting.graphicsXRight+140, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.GREEN);
				if(party.get(i).getNegativeConditions().isPoison())
					g.drawString("P", Setting.graphicsXRight+150, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.yellow);
				if(party.get(i).getNegativeConditions().isStun())
					g.drawString("S", Setting.graphicsXRight+160, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
			}
			
			entityCount++;
		}
		
		for(int i=0; i<enemies.size(); i++) {
			g.setColor(Setting.defaultColor);
			g.drawString(enemies.get(i).getName(), Setting.graphicsXRight+10, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
			
			if(enemies.get(i).getPositiveConditions()!=null) {
				g.setColor(Color.YELLOW);
				if(enemies.get(i).getPositiveConditions().isBless())
					g.drawString("B", Setting.graphicsXRight+70, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.gray);
				if(enemies.get(i).getPositiveConditions().isInvisibility())
					g.drawString("I", Setting.graphicsXRight+80, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.red);
				if(enemies.get(i).getPositiveConditions().isStrengthen())
					g.drawString("S", Setting.graphicsXRight+90, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
			}
			
			if(enemies.get(i).getNegativeConditions()!=null) {
				g.setColor(new Color(238,130,238));
				if(enemies.get(i).getNegativeConditions().isCurse())
					g.drawString("C", Setting.graphicsXRight+100, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.ORANGE);
				if(enemies.get(i).getNegativeConditions().isDisarm())
					g.drawString("D", Setting.graphicsXRight+110, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.cyan);
				if(enemies.get(i).getNegativeConditions().isImmobilize())
					g.drawString("I", Setting.graphicsXRight+120, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.red);
				if(enemies.get(i).getNegativeConditions().isWound())
					g.drawString("W", Setting.graphicsXRight+125, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.LIGHT_GRAY);
				if(enemies.get(i).getNegativeConditions().isMuddle())
					g.drawString("M", Setting.graphicsXRight+140, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.GREEN);
				if(enemies.get(i).getNegativeConditions().isPoison())
					g.drawString("P", Setting.graphicsXRight+150, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
				g.setColor(Color.yellow);
				if(enemies.get(i).getNegativeConditions().isStun())
					g.drawString("S", Setting.graphicsXRight+160, Setting.graphicsYMid+Setting.rowSpacing*entityCount);
			}
			
			entityCount++;
		}
		
		g.setColor(Setting.defaultColor);
	}
}
