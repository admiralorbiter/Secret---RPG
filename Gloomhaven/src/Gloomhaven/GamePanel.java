package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener{

	public enum GameState {
	    TITLE_STATE,
	    MAINGAME_STATE,
	    SCENE_START,
	    CARD_SELECTION,
	    INITIATIVE,
	    ATTACK,
	    ENEMYATTACK,
	    PLAYERATTACK,
	    PLAYERDEFENSE,
	    ENEMYDEFENSE,
	    END;
	}
	
	GameState state;
	List<Integer> order = new ArrayList<Integer>();
	//Player players[];
	//int numberOfPlayers;
	List<Player> players = new ArrayList<Player>();
	Scenario scene;
	int currentPlayer;
	char storedkey;
	boolean next;
	int playerCount=1;
	int cardLock;
	int turn;
	
	public GamePanel(int numberOfPlayers) {
		addKeyListener(this);
		setBackground(new Color(64, 64, 64));
		setDoubleBuffered(true);
		setFocusable(true);
		initGame();
		repaint();
		/*
		boolean running=true;
		do{
			repaint();
		}
		while(running);
		*/
		
	}
	
	void initGame() {
		int sceneID=1;
		currentPlayer=-1;
		for(int i=0; i<playerCount; i++)
			players.add(new Player("Mindthief"));
		
		scene = new Scenario(sceneID, players);
		next=true;
		state=GameState.SCENE_START;
	}
	
	public void gameManager(Graphics g) {
		if(state==GameState.SCENE_START) {
			state=GameState.CARD_SELECTION;
			currentPlayer=0;
			cardLock=0;
			next=false;
		}
		if(state==GameState.CARD_SELECTION) {
			if(next==false)
				players.get(currentPlayer).pickCards(g);
			
			if(next==true) {
				if((currentPlayer+1)!=playerCount) {
					currentPlayer++;
					next=false;
				}
				else {
					currentPlayer=-1;
					state=GameState.INITIATIVE;
				}
			}
		}
		if(state==GameState.INITIATIVE) {
			scene.pickCard();
			orderPlayers();
			state=GameState.ATTACK;
		}
		if(state==GameState.ATTACK) {
			//deciede if is player attack or enemy attack
			state=GameState.ENEMYATTACK;
			turn=0;
		}
		if(state==GameState.ENEMYATTACK) {
			if(next==true) {
				scene.enemyAttackProcedure();
				next=false;
			}
			if(next==false) {
				if(scene.getTargetedPlayer()==-1)
				{
					System.out.println("No one in range");
					turn++;
					if(turn==1) {
						currentPlayer=0;
						state=GameState.PLAYERATTACK;
						next=false;
					}
					else {
						state=GameState.END;
					}
					
				}
				else {
					state=GameState.PLAYERDEFENSE;
				}
			}
		}
		if(state==GameState.PLAYERDEFENSE) {
			scene.playerDefendProcedure(g);
			cardLock=0;
		}
		if(state==GameState.PLAYERATTACK) {
			
			System.out.println("Health: "+players.get(0).getHealth());
			printPlayerCards(g);

			if(next==false)
				printPlayerCards(g);
			
			if(next==true) {
				repaint();
				if((currentPlayer+1)!=playerCount) {
					currentPlayer++;
					next=false;
				}
				else {
					currentPlayer=-1;
					turn++;
					if(turn==1) {
						state=GameState.ENEMYATTACK;
					}
					else {
						state=GameState.END;
					}
				}
			}
		}
		
		System.out.println("State: "+state);
	}
	
	private void printPlayerCards(Graphics g) {
		String card1 = players.get(currentPlayer).firstCard();
		String card2 = players.get(currentPlayer).secondCard();
		
		g.drawString("Pick top card. Second card will default bottom.", 50, 450);
		g.drawString(1+": "+card1, 50, 500);
		g.drawString(2+": "+card2, 50, 550);
		g.drawString(3+": Attack +2", 500, 500);
		g.drawString(4+": Move +2", 500, 550);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.magenta);
		List<Enemy> enemies=scene.getEnemies();
		
		for(int i=0; i<players.size(); i++)
			g.drawImage(players.get(i).getImage(), ((int)players.get(i).getX())*50, ((int)players.get(i).getY())*50, null);
		
		for(int i=0; i<enemies.size(); i++) {
			int x = ((int)enemies.get(i).getX());
			int y =  ((int)enemies.get(i).getY())*50;
			
			if(x%2!=0) {
				y=y+50;
			}
			
			x=x*100;
			
			g.drawImage(enemies.get(i).getImage(), x, y, null);
		}
		
		gameManager(g);
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		char k = e.getKeyChar();
		int key = -1;
		try{
			key=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ // handle your exception
			   System.out.println("");
			}
		//int key=Integer.parseInt(String.valueOf(k));  
		switch(state) {
			case CARD_SELECTION:
				if((key>=0) && (key<players.get(currentPlayer).getCardCount()))
				{
					cardLock++;
					storedkey=k;
					if(cardLock==1)
						players.get(currentPlayer).lockedCard1=key;
					if((cardLock==2)&&(key!=players.get(currentPlayer).lockedCard1))
					{
						players.get(currentPlayer).lockedCard2=key;
						next=true;
					}
					else if(cardLock==2) {
						cardLock--;
					}
				}
				break;
			
			case PLAYERATTACK:
				int temp1;
				int temp2;
				if((key>=1) && (key<=4))
				{
					players.get(currentPlayer).setTop(key);
					next=true;
					
					/*cardLock++;
					storedkey=k;
					if(cardLock==1)
						temp1=key;
					if((cardLock==2)&&(key!=players.get(currentPlayer).lockedCard1))
					{
						temp2=key;
						next=true;
					}
					else if(cardLock==2) {
						cardLock--;
					}*/
				}
				break;
				
			case PLAYERDEFENSE:
				//System.out.println(turn);
				if(k=='y') {
					storedkey=k;
					next=true;
					turn++;
					
					//discard a card
					scene.discardTargetPlayer();
					
					if(turn==1) {
						currentPlayer=0;
						state=GameState.PLAYERATTACK;
						next=false;
					}
					else {
						state=GameState.END;
					}
				}
				else if(k=='n') {
					storedkey=k;
					next=true;
					turn++;
					
					//decrease health
					scene.decreaseTargetPlayerHealth();
					
					if(turn==1) {
						currentPlayer=0;
						state=GameState.PLAYERATTACK;
						next=false;
					}
					else {
						state=GameState.END;
					}
				}
				break;
		}
		
		repaint();
	}

	public void orderPlayers() {
		List<Integer> temp = new ArrayList<Integer>();
		temp.clear();
		temp.add(scene.getEnemyInitiative());
		for(int i=0; i<(playerCount); i++) {
			temp.add(players.get(i).getInitiative());
		}
		Collections.sort(temp);

		order.clear();
		
		//i need an ordered list of players, not initiative
		
		for(int i=0; i<(playerCount+1); i++) {
			order.add(i, temp.get(i));
			System.out.println(order.get(i));
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
