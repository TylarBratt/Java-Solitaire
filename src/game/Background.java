package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;

import javax.swing.*;  

public class Background extends JLayeredPane{

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
	private static WinPanel wp;
	private static Foundation[] foundation;
	private static Tableau[] tableau;
	public CardMoveListener game;
	public static boolean playerWon = false;

	public Background() {
		super.setLayout(null);
		initializePiles();
		initializeGameTimer();
		initializeWinPanel();
	}

	private void initializeGameTimer() {
		gameTimer = new GameTimer("Time challenge", 625, 550, 125, 50, 0);
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
	
	public static WinPanel getWinPanel() {
		return wp;
	}

	public void initializeWinPanel() {
		wp = new WinPanel("You won!", 275, 200, 200, 100, 0);
		//add(wp, new Integer(-1) );
		moveToBack(wp);
		wp.setVisible(false);
		System.out.println("Added win panel");
	}

	public void showWinPanel() {
		wp = new WinPanel("You won!", 275, 200, 200, 100, 0);
		//add(wp, new Integer(-1) );
		add(wp);
		wp.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.cyan);
		a.fillRect(0,0,this.getWidth(), this.getHeight());
	}
}
