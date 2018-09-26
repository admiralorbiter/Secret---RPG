package Gloomhaven.TempStorage;

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
	int id;
	
	public Enemy(String name, int id) {
		this.id=id;
		this.name=name;
		position = new Point(0, 0);
		sprite=new Sprite("src/Gloomhaven/Sprites/Enemy.png");
	}
	
	public boolean isElite() {
		return elite;
	}
	
	public void makeElite() {
		elite=true;
	}
	
	public void setXY(Point position) {
		this.position=position;
	}

	public Image getImage(){return sprite.getImage();}
	public Point getPosition() {return position;}
	public double getX() {return position.getX();}
	public double getY() {return position.getY();}
	public String getName() {return name;}
	
}
