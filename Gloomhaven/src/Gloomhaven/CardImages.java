package Gloomhaven;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class CardImages {
	public static BufferedImage getMindThiefCard(int index) {

		BufferedImage img = null;
		try {
			if(index==1)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT1.png"));
			else if(index==2)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT2.png"));
			else if(index==3)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT3.png"));
			else if(index==4)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT4.png"));
			else if(index==5)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT5.png"));
			else if(index==6)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT6.png"));
			else if(index==7)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT7.png"));
			else if(index==8)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT8.png"));
			else if(index==9)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT9.png"));
			else if(index==10)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/MindThief/MT10.png"));
		}catch(IOException e) {}
		
		return img;
	}
	
	public static BufferedImage getSpellweaverCard(int index) {

		BufferedImage img = null;
		try {
			if(index==1)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW1.png"));
			else if(index==2)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW2.png"));
			else if(index==3)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW3.png"));
			else if(index==4)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW4.png"));
			else if(index==5)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW5.png"));
			else if(index==6)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW6.png"));
			else if(index==7)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW7.png"));
			else if(index==8)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Spellweaver/SW8.png"));
		}catch(IOException e) {}
		
		return img;
	}
	
	public static BufferedImage getBruteCard(int index) {

		BufferedImage img = null;
		try {
			if(index==1)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR1.png"));
			else if(index==2)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR2.png"));
			else if(index==3)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR3.png"));
			else if(index==4)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR4.png"));
			else if(index==5)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR5.png"));
			else if(index==6)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR6.png"));
			else if(index==7)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR7.png"));
			else if(index==8)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR8.png"));
			else if(index==9)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR9.png"));
			else if(index==10)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Brute/BR10.png"));
		}catch(IOException e) {}
		
		return img;
	}
	
	public static BufferedImage getScoundrelCard(int index) {

		BufferedImage img = null;
		try {
			if(index==1)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC1.png"));
			else if(index==2)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC2.png"));
			else if(index==3)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC3.png"));
			else if(index==4)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC4.png"));
			else if(index==5)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC5.png"));
			else if(index==6)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC6.png"));
			else if(index==7)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC7.png"));
			else if(index==8)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC8.png"));
			else if(index==9)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Scoundrel/SC9.png"));
		}catch(IOException e) {}
		
		return img;
	}
	
	public static BufferedImage getCragheartCard(int index) {

		BufferedImage img = null;
		try {
			if(index==1)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH1.png"));
			else if(index==2)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH2.png"));
			else if(index==3)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH3.png"));
			else if(index==4)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH4.png"));
			else if(index==5)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH5.png"));
			else if(index==6)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH6.png"));
			else if(index==7)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH7.png"));
			else if(index==8)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH8.png"));
			else if(index==9)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH9.png"));
			else if(index==10)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH10.png"));
			else if(index==11)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Cragheart/CH11.png"));
		}catch(IOException e) {}
		
		return img;
	}
	
	public static BufferedImage getTinkererCard(int index) {

		BufferedImage img = null;
		try {
			if(index==1)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI1.png"));
			else if(index==2)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI2.png"));
			else if(index==3)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI3.png"));
			else if(index==4)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI4.png"));
			else if(index==5)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI5.png"));
			else if(index==6)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI6.png"));
			else if(index==7)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI7.png"));
			else if(index==8)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI8.png"));
			else if(index==9)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI9.png"));
			else if(index==10)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI10.png"));
			else if(index==11)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI11.png"));
			else if(index==12)
				img = ImageIO.read(new File("src/Gloomhaven/Pictures/Tinkerer/TI12.png"));
		}catch(IOException e) {}
		
		return img;
	}
}
