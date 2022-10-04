package game;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main extends JFrame{
	
	/**
	 * Images were collected from https://pixabay.com/photos/playing-card-back-template-568201/ and https://pixabay.com/vectors/card-deck-deck-cards-playing-cards-161536/ as these images are free for commercial use.
	 */
	private static final long serialVersionUID = 1L;
	
	static protected Background bg;
	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 600;

	public Main() {


		//This is the main constructor for the game. It gets called immediately on start up,
		// it sets the default close operation to actually close the JFrame window. 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//This section calls the constructor for Background which calls paintComponent and sets a Green background the size of 750*600
		bg = new Background();
		bg.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		add(bg);
		pack();
	}
	
	public static void main(String[] args) {
		//our actual main class that gets run on startup, currently just sets the Main window to visible.
		new Main().setVisible(true);
	}

}
