package Utility;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Unsorted.FontSettings;

public final class StringUtilities {
	private StringUtilities() {}
	
	public static List<String> lineBreakBasedOnCharacter(Graphics g, int pixelWidth, String string){
		List<String> listOfStrings = new ArrayList<>();
		int width = g.getFontMetrics().stringWidth("D");
		width = pixelWidth/width;
		while(string.length()>width+1){
			int index = width;
			for(int i=width; i>=0; i--) {
				if(string.charAt(i)==' ') {
					index=i;
					break;
				}
			}
			
			listOfStrings.add(string.substring(0, index));
			string=string.substring(index+1);
		}
		
		listOfStrings.add(string);

		return listOfStrings	;
	}
}
