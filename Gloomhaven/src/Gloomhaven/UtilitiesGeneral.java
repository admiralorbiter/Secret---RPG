package Gloomhaven;

import java.awt.event.KeyEvent;

public final class UtilitiesGeneral {
	//Parses key event into either a character or a number to be used
	public static char parseKeyCharacter(KeyEvent key) {
		char k=Character.MIN_VALUE;
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ 														// handle null pointers for getKeyChar
			   
			}
		return k;
	}
	
	public static int parseKeyNum(KeyEvent key) {
		char k=Character.MIN_VALUE;
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ 														// handle null pointers for getKeyChar
			   
			}
		int num = -1;
		try{
			num=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ 														// handle if it isn't a number character
			}   
		return num;
	}
	
	
}
