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
	
	static protected Start st;
	static protected Background bg = null;
	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 600;
	static protected boolean playerWon = false;
	protected CardMoveListener mainCardMoveListener;
	
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
		char j = 'j';
		
		
		//System.out.println("The Key Pressed was: " + a);
		if(a == start) {
			if (  bg == null ) {
				remove(st);
				
				bg = new Background();
				remove(Main.st);
				add(bg);
				revalidate();

				mainCardMoveListener = new CardMoveListener();
				bg.addMouseListener(mainCardMoveListener);
				bg.addMouseMotionListener(mainCardMoveListener);
			}
			else {
				remove(bg);
				bg = new Background();
				remove(Main.st);
				add(bg);
				revalidate();

				mainCardMoveListener = new CardMoveListener();
				bg.addMouseListener(mainCardMoveListener);
				bg.addMouseMotionListener(mainCardMoveListener);
			}
		} else if (a == j) {
			//sc = new Score();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}	
	}
}
