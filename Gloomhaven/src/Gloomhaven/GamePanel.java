package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.*;


public class GamePanel extends JPanel implements KeyListener {

	//Player players[];
	//int numberOfPlayers;
	
	Scenario scene;
	
	public GamePanel(int numberOfPlayers) {
		addKeyListener(this);
		setBackground(new Color(64, 64, 64));
		setDoubleBuffered(true);
		setFocusable(true);
		initGame();
		repaint();
	}
	
	void initGame() {
		int sceneID=1;
		int playerCount=1;
		scene = new Scenario(sceneID, playerCount);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.magenta);
		
		List<Player> players=scene.getPlayers();
		List<Enemy> enemies=scene.getEnemies();
		
		for(int i=0; i<players.size(); i++)
			g.drawImage(players.get(i).getImage(), ((int)players.get(i).getX())*50, ((int)players.get(i).getY())*50, null);
		
		//System.out.println(((int)players.get(0).getX())*50+", "+((int)players.get(0).getY())*50);
		
		for(int i=0; i<enemies.size(); i++) {
			g.drawImage(enemies.get(i).getImage(), ((int)enemies.get(i).getX())*50, ((int)enemies.get(i).getY())*50, null);
			//System.out.println(enemies.get(i).getName()+": "+((int)enemies.get(i).getX())*50+", "+((int)enemies.get(i).getY())*50);
		}
		//System.out.println(enemies.size());
		
		//g.drawImage(players[0].getImage(), 50, 50, null);
	}
	

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
}
