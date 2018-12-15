package Gloomhaven;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class CardImages {
	public static ImageIcon getMindThiefCard(int index) {

		ImageIcon img = null;
		if(index==1)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT1.png");
		else if(index==2)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT2.png");
		else if(index==3)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT3.png");
		else if(index==4)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT4.png");
		else if(index==5)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT5.png");
		else if(index==6)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT6.png");
		else if(index==7)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT7.png");
		else if(index==8)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT8.png");
		else if(index==9)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT9.png");
		else if(index==10)
			img = new ImageIcon("src/Gloomhaven/Pictures/MindThief/MT10.png");
	
		return img;
	}
	
	public static ImageIcon getSpellweaverCard(int index) {

		ImageIcon img = null;
		if(index==1)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW1.png");
		else if(index==2)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW2.png");
		else if(index==3)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW3.png");
		else if(index==4)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW4.png");
		else if(index==5)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW5.png");
		else if(index==6)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW6.png");
		else if(index==7)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW7.png");
		else if(index==8)
			img = new ImageIcon("src/Gloomhaven/Pictures/Spellweaver/SW8.png");
		
		return img;
	}
	
	public static ImageIcon getBruteCard(int index) {

		ImageIcon img = null;
		if(index==1)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR1.png");
		else if(index==2)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR2.png");
		else if(index==3)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR3.png");
		else if(index==4)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR4.png");
		else if(index==5)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR5.png");
		else if(index==6)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR6.png");
		else if(index==7)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR7.png");
		else if(index==8)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR8.png");
		else if(index==9)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR9.png");
		else if(index==10)
			img = new ImageIcon("src/Gloomhaven/Pictures/Brute/BR10.png");
		
		return img;
	}
	
	public static ImageIcon getScoundrelCard(int index) {

		ImageIcon img = null;
		if(index==1)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC1.png");
		else if(index==2)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC2.png");
		else if(index==3)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC3.png");
		else if(index==4)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC4.png");
		else if(index==5)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC5.png");
		else if(index==6)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC6.png");
		else if(index==7)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC7.png");
		else if(index==8)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC8.png");
		else if(index==9)
			img = new ImageIcon("src/Gloomhaven/Pictures/Scoundrel/SC9.png");
		
		return img;
	}
	
	public static ImageIcon getCragheartCard(int index) {

		ImageIcon img = null;
		if(index==1)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH1.png");
		else if(index==2)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH2.png");
		else if(index==3)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH3.png");
		else if(index==4)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH4.png");
		else if(index==5)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH5.png");
		else if(index==6)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH6.png");
		else if(index==7)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH7.png");
		else if(index==8)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH8.png");
		else if(index==9)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH9.png");
		else if(index==10)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH10.png");
		else if(index==11)
			img = new ImageIcon("src/Gloomhaven/Pictures/Cragheart/CH11.png");
		
		return img;
	}
	
	public static ImageIcon getTinkererCard(int index) {

		ImageIcon img = null;
		if(index==1)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI1.png");
		else if(index==2)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI2.png");
		else if(index==3)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI3.png");
		else if(index==4)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI4.png");
		else if(index==5)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI5.png");
		else if(index==6)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI6.png");
		else if(index==7)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI7.png");
		else if(index==8)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI8.png");
		else if(index==9)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI9.png");
		else if(index==10)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI10.png");
		else if(index==11)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI11.png");
		else if(index==12)
			img = new ImageIcon("src/Gloomhaven/Pictures/Tinkerer/TI12.png");
	
		return img;
	}
}
