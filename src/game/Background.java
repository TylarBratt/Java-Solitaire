package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;

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
	public StockPile sp;
	public TalonPile tp;
	public GameTimer gameTimer;
	public Foundation[] foundationArray;
	public Tableau[] tableauArray;
	public CardMoveListener game;
	public boolean playerWon = false;
	

	public Background() {
		super.setLayout(null);
		//initializePiles();
		initializeGameTimer();
	}

	public Background(Background other) {
		super.setLayout(null);
		
		StockPile clonedSP = new StockPile(other.getStockPile());
		this.setStockPile(clonedSP);
		
		TalonPile clonedTP = new TalonPile(other.getTpPile());
		this.setTalonPile(clonedTP);

		this.foundationArray = new  Foundation[4];
		for (int i = 0; i<other.getFoundationArray().length; i++) {
			Foundation currentFoundation = new Foundation(other.getFoundationArray()[i]);
			// System.out.println(currentFoundation);
			this.foundationArray[i] = currentFoundation;
		}
		
		this.tableauArray = new Tableau[7];
		for (int i = 0; i<other.getTableauArray().length; i++) {
			Tableau currentTableau = new Tableau(other.getTableauArray()[i]);
			this.tableauArray[i] = currentTableau;
		}
		this.game = other.game;
		this.playerWon = other.playerWon;
		this.gameTimer = other.gameTimer;
	}

	private void initializeGameTimer() {
		gameTimer = new GameTimer("Time challenge", 625, 550, 125, 50, 0);
		add(gameTimer);
	}
		
	public Foundation[] getFoundationArray() {
		return this.foundationArray;
	}

	public Tableau[] getTableauArray() {
		return this.tableauArray;
	}


	public TalonPile getTpPile() {
		return this.tp;
	}

	public void setTalonPile(TalonPile talonpile) {
		this.tp = talonpile;
	}
	
	

	public StockPile getStockPile() {
		return this.sp;
	}

	public void setStockPile(StockPile stockpile) {
		this.sp = stockpile;
	}
	
	public GameTimer getGameTimer() {
		return this.gameTimer;
	}


	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.cyan);
		a.fillRect(0,0,this.getWidth(), this.getHeight());
	}
}
