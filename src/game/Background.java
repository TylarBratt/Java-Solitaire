package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Background extends JPanel{

	/**
	 * This class draws a green background on the window. 
	 * The color can be changed but for the purposes of Solitaire, I thought green was appropriate.
	 */
	private static final long serialVersionUID = 1L;
	private static StockPile stockpile;
	
	public Background() {
		super.setLayout(null);
		stockpile = new StockPile(650, 15);
		add(stockpile);
		
	}

	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.green);
		a.fillRect(0,0,this.getWidth(), this.getHeight());
	}
}
