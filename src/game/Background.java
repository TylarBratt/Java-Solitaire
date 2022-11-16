package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;

public class Background extends JPanel{

	/**
	 * This class draws a green background on the window. 
	 * The color can be changed but for the purposes of Solitaire, I thought green was appropriate.
	 */
	private static final long serialVersionUID = 1L;
	protected static int tpShift = 100;
	
	public static Point TABLEAU_POSITION = new Point(20, 150);
	public static int TABLEAU_OFFSET = 100;
	private static StockPile sp;
	private static TalonPile tp;
	private static GameTimer gameTimer;

	private static Foundation[] foundation;
	private static Tableau[] tableau;
	public Background() {
		super.setLayout(null);
		initializePiles();
		initializeGameTimer();
		
		CardMoveListener game = new CardMoveListener();
		addMouseListener(game);
		addMouseMotionListener(game);
		checkWinState(getFoundation());

	}

	private void initializeGameTimer() {
		gameTimer = new GameTimer("Time challenge", 625, 550, 125, 50, 0);
		// JButton jbtn = new JButton("Starting Timer...");
		// jbtn.setPreferredSize(new Dimension(125, 50));
		// jbtn.setBounds(625, 550, 125, 50);
		// add(jbtn);
		// System.out.println("added jbutton");
		add(gameTimer);
	}

	private void initializePiles() {
		sp = new StockPile(650, 15);
		add(sp);
		tp = new TalonPile(650 - tpShift, 15);
		add(tp);
		foundation = new Foundation[4];
		for(int i = 0; i < foundation.length; i++) {
			foundation[i] = new Foundation(20 + tpShift * i, 20, i + 1);
			add(foundation[i]);
		}
		tableau = new Tableau[7];
		for(int k = 1; k <= tableau.length; k++) {
			tableau[k - 1] = new Tableau(TABLEAU_POSITION.x + TABLEAU_OFFSET * (k - 1), TABLEAU_POSITION.y, k + 1);
			add(tableau[k - 1]);
		}
	}
		
	public static Foundation[] getFoundation() {
		return foundation;
	}

	public static TalonPile getTpPile() {
		return tp;
	}
	

	public static StockPile getStockPile() {
		return sp;
	}
	
	public static boolean checkWinState(Foundation[] foundations) {
		int completeFoundations = 0;
		for ( int i=0; i<4; i++ ) {
			Foundation currentFoundation = foundations[i];
			int foundationSize = currentFoundation.cards.size();
			if ( foundationSize == 13 ) {
				completeFoundations++;
			}
			System.out.println(currentFoundation.cards.size());
		}
		if ( completeFoundations==4 ) {
			System.out.println("You won!");
			return true;
		}
		else {
			System.out.println("You haven't won yet");
			return true;
		} 
	}
	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.cyan);
		a.fillRect(0,0,this.getWidth(), this.getHeight());
	}
}
