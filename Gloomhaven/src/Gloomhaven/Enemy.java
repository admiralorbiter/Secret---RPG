package Gloomhaven;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {
	
	String name;
	Sprite sprite;
	boolean elite=false;
	Point position;
	
	public Enemy(String name) {
		this.name=name;
		position = new Point(0, 0);
		sprite=new Sprite("src/Gloomhaven/Sprites/Enemy.png");
	}
	
	public void makeElite() {
		elite=true;
	}
	
	public void setXY(Point position) {
		this.position=position;
	}
	
	public Image getImage(){return sprite.getImage();}
	
	public double getX() {return position.getX();}
	public double getY() {return position.getY();}
	public String getName() {return name;}
	
}
