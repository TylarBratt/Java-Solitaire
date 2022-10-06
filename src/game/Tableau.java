package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
//import java.awt.GradientPaint;

public class Tableau extends Pile{

	/**
	 * This file displays the tableau field. the lines comentented out are a color gradient, it look cool but is not important.
	 */
	private static final long serialVersionUID = 1L;

	public Tableau(int position_x, int position_y, int size) {
		super(position_x, position_y);
		super.setSize(84, 600);
		super.setOpaque(false);
		for(int i = 0; i < size; i++) {
			push(Background.getStockPile().pop());
		}
	}
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);	
		Graphics2D graphic = (Graphics2D) a;
		graphic.setColor(Color.GRAY);
		graphic.drawLine(0, 0, this.getWidth(), 0);
		graphic.drawLine(0, 0, 0, 112);
		graphic.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, 112);
		//graphic.setPaint(new GradientPaint(36, 0, new Color(255, 255, 255, 160), 36, 60, new Color(0, 0, 0, 0)));
		//graphic.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		
		int cardYpos = 0;
		if(this.noCard()) {
			
		}else {
			for(Card c : this.cards) {
				if(c.isFace()) {
					
				}else {
				graphic.drawImage(Card.getBack(), 0, cardYpos, 84, 112, this);
				cardYpos += 20;
			}}
		}
	}

	
	
	
	
	
	
	
}
