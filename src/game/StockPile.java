package game;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

public class StockPile extends Stack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public StockPile(int position_x, int position_y) {
		super(position_x, position_y);
		super.setSize(84, 112);
	}

	
	
	public void paintComponent(Graphics a) {
		super.paintComponent(a);
		Graphics2D graphic = (Graphics2D) a;
		graphic.setStroke(new BasicStroke(6));
		graphic.setColor(Color.gray);
		graphic.drawRect(0, 0, this.getWidth(), this.getHeight());
	}
}
