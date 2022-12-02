package game;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements KeyListener {
	
	/**
	 * Images were collected from https://pixabay.com/photos/playing-card-back-template-568201/ and https://pixabay.com/vectors/card-deck-deck-cards-playing-cards-161536/ as these images are free for commercial use.
	 */
	private static final long serialVersionUID = 1L;
	public static int easyHard = 0;
	static protected Start st;
	static protected Background bg = null;
	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 600;
	static protected boolean playerWon = false;
	static protected int bestNormalizedTime = 100000;
	static protected String playerTime = "";
	protected CardMoveListener mainCardMoveListener;
	protected BestTimePanel bestTimePanel;
	public Main() {
		//This is the main constructor for the game. It gets called immediately on start up,
		// it sets the default close operation to actually close the JFrame window. 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//This section calls the constructor for Background which calls paintComponent and sets a Green background the size of 750*600
		st = new Start();
		st.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		add(st);
		pack();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public static void main(String[] args) {
		//our actual main class that gets run on startup, currently just sets the Main window to visible.
		new Main().setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char a = e.getKeyChar();
		char start = 'e';
		char hard = 'r';
		char j = 'j';
		
		
		//System.out.println("The Key Pressed was: " + a);
		if(a == start) {
			//determine if you are coming from start screen or end of a game
			if (  playerWon == false  ) {
				easyHard = 0;
				
				if ( st != null ) {
					remove(st);
				}
				
				if ( bg != null ) {
					remove(bg);
				}
				bg = new Background();
				remove(Main.st);
				add(bg);
				revalidate();
			}
			else {
				easyHard = 0;
				remove(bg);
				bg = new Background();
				remove(Main.st);
				add(bg);
				bestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + playerTime +"</center></html>", 0, 550 , 125, 50, 0);
				bg.add(bestTimePanel);
				revalidate();
			}
		} else if (a == j) {
			//sc = new Score();
		} else if (a == hard) {
			if (  bg == null || playerWon == false ) {
				easyHard = 1;
				remove(st);
				bg = new Background();
				remove(Main.st);
				add(bg);
				revalidate();
			}
			else {
				easyHard = 1;
				remove(bg);
				bg = new Background();
				remove(Main.st);
				add(bg);
				bestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + playerTime +"</center></html>", 0, 550 , 125, 50, 0);
				bg.add(bestTimePanel);
				revalidate();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}	
	}
	public static int getBestNormalizedTime() {
		return bestNormalizedTime;
	}

	public static void setPlayerWon(boolean newPlayerWon) {
		playerWon = newPlayerWon;
	}

	public static void setPlayerTime(String newPlayerTime) {
		playerTime = newPlayerTime;
	}

}
