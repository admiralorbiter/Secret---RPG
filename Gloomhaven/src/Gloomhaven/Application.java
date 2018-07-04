package Gloomhaven;

import javax.swing.JFrame;

public class Application extends JFrame {

	GamePanel game;
	
	static int numberOfPlayers=1;
	
	public Application() {
		setSize(1400, 800);
		setTitle("Game");
		game = new GamePanel(numberOfPlayers);
		add(game);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		repaint();
		setLocationRelativeTo(null);
	}
	
	
	public static void main(String[] args) {
		Application app = new Application();
		app.setVisible(true);
	}

}
