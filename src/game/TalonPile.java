package game;

import java.awt.Graphics;

public class TalonPile extends Pile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TalonPile(int position_x, int position_y) {
		super(position_x, position_y);
		super.setSize(84, 112);
	}
	
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		
		if(this.noCard()) {
			a.drawImage(Card.getCardOutline(),0,0,84, this.getHeight(), this);
		}
		else {
			a.drawImage(this.topCard().getImageCard(), 0, 0, 84, this.getHeight(), this);
		}
		
	}
	

}
