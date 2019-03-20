package Gloomhaven;

import javax.swing.JFrame;

@SuppressWarnings("ucd")
public class Application extends JFrame {

	GamePanel game;

	public Application() {
		setSize(GUISettings.width, GUISettings.height);
		setTitle(Setting.title);
		game = new GamePanel();
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
