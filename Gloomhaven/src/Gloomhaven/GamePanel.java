package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
	    PLAYERCHOICE,
	    PLAYERDEFENSE,
	    ENEMYDEFENSE,
	    PLAYERCARD,
	    PLAYERMOVE,
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
	int cardUsed;
	Point select = new Point();
	int move;
	Point dim = new Point();
	
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
			dim = scene.getDim();
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
						state=GameState.PLAYERCHOICE;
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
		if(state==GameState.PLAYERCHOICE) {
			/*
			 * At the moment, the first card picked is the top card.
			 */
			System.out.println("Health: "+players.get(0).getHealth());
			printPlayerCards(g);

			if(next==false)
				printPlayerCards(g);
			
			if(next==true) {
				repaint();
				
				state=GameState.PLAYERCARD;
				next=false;
				cardUsed=0;
				/*if((currentPlayer+1)!=playerCount) {
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
				}*/
				
			}
		}
		
		if(state==GameState.PLAYERMOVE) {
			playerMove(g);
		}
		if(state==GameState.PLAYERCARD) {

			if(next==false) {
				//do attack then change next to true
				playerCard(g, cardUsed);
				
			}
			
			if(next==true) {
				if(cardUsed==0) {
					cardUsed++;
				}
				else {
					cardUsed=0;
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
		}
		
		System.out.println("State: "+state);
	}
	
	private void playerCard(Graphics g, int cardsUsed) {
		//do what player card one does, either attack or defense
		//if attack	
		//display enemies in attack radius
		//use key presses to pick enemies
		//no enemies no attack
		//if move
		//move, one at a time
		g.drawString("Card "+(cardUsed+1), 50, 600);
		repaint();
		AbilityCards card;
		card=players.get(currentPlayer).getCard(cardsUsed);
		int attack;
		List<Enemy> enemies=scene.getEnemies();
		List<Enemy> targets = new ArrayList();

		if(cardsUsed==0) {
			move=card.getTop().move;
			attack=card.getTop().attack;
		}
		else {
			move=card.getBottom().move;
			attack=card.getBottom().attack;
		}
		//System.out.println("GP: 230 - "+move);
		if(move!=0) {
			//do the move stuff first
			state=GameState.PLAYERMOVE;
			select = new Point((int)players.get(currentPlayer).getX(), (int)players.get(currentPlayer).getY());
			dim = scene.getDim();
		}
		if(attack!=0){
			//make a target list
			for(int i=0; i<enemies.size(); i++) {
				
				if(scene.inRange(players.get(currentPlayer).getPosition(), enemies.get(i).getPosition(), move)) {
					if(scene.checkLOS(players.get(currentPlayer).getPosition(), enemies.get(i).getPosition())) {
						//add to enemies list
						targets.add(enemies.get(i));
					}
				}
			}

			if(targets.size()!=0) {
				//attack
			}
			else {
				next=true;
			}
			
		}
		
		next=true;
	}
	
	private void playerMove(Graphics g) {
		g.setColor(Color.MAGENTA);
		//System.out.println((int)players.get(currentPlayer).getX()+" - "+(int)players.get(currentPlayer).getY());
		int x = (int) select.getX();
		int y = (int) (select.getY()*100);
		
		if(x%2!=0) {
			y=y+50;
		}
		
		x=x*150+x*-50;

		
		g.setColor(Color.BLUE);
		g.drawRect(x+50, y+25, 50, 50);
		//drawHex(g, x, y);
		repaint();
	}
	
	private void printPlayerCards(Graphics g) {
		String card1 = players.get(currentPlayer).firstCard();
		String card2 = players.get(currentPlayer).secondCard();
		
		g.drawString("Pick top card. Second card will default bottom.", 50, 600);
		g.drawString(1+": "+card1, 50, 650);
		g.drawString(2+": "+card2, 50, 700);
		g.drawString(3+": Attack +2", 500, 650);
		g.drawString(4+": Move +2", 500, 700);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.magenta);
		List<Enemy> enemies=scene.getEnemies();
	
		for(int h=0; h<=dim.y; h++) {
			for(int w=0; w<dim.x; w++) {
				drawHex(g, w, h);
			}
		}
		g.setColor(Color.MAGENTA);
		
		for(int i=0; i<players.size(); i++)
			drawSprite(g, players.get(i).getImage(), ((int)players.get(i).getX()), ((int)players.get(i).getY()));
		for(int i=0; i<enemies.size(); i++) {
			
			drawSprite(g, enemies.get(i).getImage(), (int)enemies.get(i).getX(), (int)enemies.get(i).getY());
		}
		
		gameManager(g);
		
	}
	
	public void drawHex(Graphics g, int x, int y) {
		int size=100;
		int nPoints=7;
		int offsetY=0;
		int offsetX=0;
		//offsetX=-25*x;
		if(x%2!=0) {
			offsetY=50;
		}
		
		int[] tX = {0+x*size+offsetX, 50+x*size+offsetX, 100+x*size+offsetX, 150+x*size+offsetX, 100+x*size+offsetX, 50+x*size+offsetX, 0+x*size+offsetX};
		int[] tY = {50+y*size+offsetY, 0+y*size+offsetY,  0+y*size+offsetY, 50+y*size+offsetY, 100+y*size+offsetY, 100+y*size+offsetY, 50+y*size+offsetY};
		g.drawPolygon(tX, tY, nPoints);
	}
	
	public void drawSprite(Graphics g, Image image, int x, int y) {
		x = x;
		y =  y*100+25;
		
		if(x%2!=0) {
			y=y+50;
		}
		
		x=50+x*100;
		
		g.drawImage(image, x, y, null);
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
			
			case PLAYERCHOICE:
				int temp1;
				int temp2;
				System.out.println("Stupid test");
				if((key>=1) && (key<=4))
				{
					players.get(currentPlayer).setTop(key);
					next=true;
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
						state=GameState.PLAYERCHOICE;
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
						state=GameState.PLAYERCHOICE;
						next=false;
					}
					else {
						state=GameState.END;
					}
				}
				break;
				
			case PLAYERMOVE:
				System.out.println("Player moves");
				if(k=='w') {
					if(select.y-1>=0) {
						select.y=select.y-1;
					}
				}
				if(k=='a') {
					if(select.x-1>=0) {
						select.x=select.x-1;
					}
				}
				if(k=='s') {
					if(select.y+1<=dim.y) {
						select.y=select.y+1;
					}
				}
				if(k=='d') {
					if(select.x+1<=dim.x) {
						select.x=select.x+1;
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
