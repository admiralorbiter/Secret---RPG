package Gloomhaven;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

public final class GUIInfusion {
	public static void drawConsumeAny(Graphics g, List<String> strong, List<String> wanning) {
		g.setFont(FontSettings.body);
		
		int index=0;
		if(strong.contains("Fire") || wanning.contains("Fire")){
			g.drawString("1 Fire", GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*index);
			index++;
		}
		else if(strong.contains("Ice") || wanning.contains("Ice")){
			g.drawString("2 Ice",  GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*index);
			index++;
		}
		else if(strong.contains("Air") || wanning.contains("Air")){
			g.drawString("3 Air",  GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*index);
			index++;
		}
		else if(strong.contains("Earth") || wanning.contains("Earth")){
			g.drawString("4 Earth",  GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*index);
			index++;
		}
		else if(strong.contains("Light") || wanning.contains("Light")){
			g.drawString("5 Light",  GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*index);
			index++;
		}
		else if(strong.contains("Dark") || wanning.contains("Dark")){
			g.drawString("6 Dark",  GUISettings.gLeft, GUISettings.gBottom+GUISettings.leadingBody*index);
			index++;
		}
	}
	
	public static void drawInfusionTable(Graphics g, List<String> strong, List<String> wanning) {
		
		g.drawImage(new ImageIcon("src/Gloomhaven/img/InfusionTable.png").getImage(), GUISettings.infusionTableX-GUISettings.padding, GUISettings.infusionTableY-33, GUISettings.infusionTableW, GUISettings.infusionTableH, null);
		
		
		//g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ3, 200, 33);
		g.drawString("Earth:", GUISettings.infusionTableX+10, GUISettings.infusionTableY+GUISettings.leadingBody);
		if(strong.contains("Earth"))
			g.drawString("Strong", GUISettings.infusionTableX+135, GUISettings.infusionTableY+GUISettings.leadingBody);
		else if(wanning.contains("Earth"))
			g.drawString("Wanning", GUISettings.infusionTableX+65, GUISettings.infusionTableY+GUISettings.leadingBody);
		
		//Air
		//g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ3, 200, 33*2);
		g.drawString("Air:", GUISettings.infusionTableX+10, GUISettings.infusionTableY+GUISettings.leadingBody+33);
		if(strong.contains("Air"))
			g.drawString("Strong", GUISettings.infusionTableX+135, GUISettings.infusionTableY+GUISettings.leadingBody+33);
		else if(wanning.contains("Air"))
			g.drawString("Wanning", GUISettings.infusionTableX+65, GUISettings.infusionTableY+GUISettings.leadingBody+33);
		
		//Fire
		//g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ3, 200, 33*3);
		g.drawString("Fire:", GUISettings.infusionTableX+10, GUISettings.infusionTableY+GUISettings.leadingBody+33*2);
		if(strong.contains("Fire"))
			g.drawString("Strong", GUISettings.infusionTableX+135, GUISettings.infusionTableY+GUISettings.leadingBody+33*2);
		else if(wanning.contains("Fire"))
			g.drawString("Wanning", GUISettings.infusionTableX+65, GUISettings.infusionTableY+GUISettings.leadingBody+33*2);
		
		//Water
		//g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ3, 200, 33*4);
		g.drawString("Water:", GUISettings.infusionTableX+10, GUISettings.infusionTableY+GUISettings.leadingBody+33*3);
		if(strong.contains("Water"))
			g.drawString("Strong", GUISettings.infusionTableX+135, GUISettings.infusionTableY+GUISettings.leadingBody+33*3);
		else if(wanning.contains("Water"))
			g.drawString("Wanning", GUISettings.infusionTableX+65, GUISettings.infusionTableY+GUISettings.leadingBody+33*3);
		
		//Dark
		//g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ3, 200, 33*5);
		g.drawString("Dark:", GUISettings.infusionTableX+10, GUISettings.infusionTableY+GUISettings.leadingBody+33*4);
		if(strong.contains("Dark"))
			g.drawString("Strong", GUISettings.infusionTableX+135, GUISettings.infusionTableY+GUISettings.leadingBody+33*4);
		else if(wanning.contains("Dark"))
			g.drawString("Wanning", GUISettings.infusionTableX+65, GUISettings.infusionTableY+GUISettings.leadingBody+33*4);
		
		//Light
		//g.drawRect(GUISettings.gRight-GUISettings.padding, GUISettings.gYQ3, 200, 33*6);
		g.drawString("Light:", GUISettings.infusionTableX+10, GUISettings.infusionTableY+GUISettings.leadingBody+33*5);
		if(strong.contains("Light"))
			g.drawString("Strong", GUISettings.infusionTableX+135, GUISettings.infusionTableY+GUISettings.leadingBody+33*5);
		else if(wanning.contains("Light"))
			g.drawString("Wanning", GUISettings.infusionTableX+65, GUISettings.infusionTableY+GUISettings.leadingBody+33*5);
	}
}
