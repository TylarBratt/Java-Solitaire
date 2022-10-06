package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

import javax.swing.JPanel;

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
	
	private static Foundation[] foundation;
	private static Tableau[] tableau;
	public Background() {
		super.setLayout(null);
		initializePiles();
		
		
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

	
	public static StockPile getStockPile() {
		return sp;
	}
	
	
	
	
	
	
	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.green);
		a.fillRect(0,0,this.getWidth(), this.getHeight());
	}
}
