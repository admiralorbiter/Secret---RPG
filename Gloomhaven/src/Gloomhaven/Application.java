package Gloomhaven;

import javax.swing.JFrame;

public class Application extends JFrame {

	GamePanel game;
	Setting setting = new Setting();
	public Application() {
		setSize(Setting.width, Setting.height);
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
