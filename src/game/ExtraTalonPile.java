package game;

import java.awt.Graphics;

public class ExtraTalonPile extends Pile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExtraTalonPile(int position_x, int position_y, int px, int py) {
		super(position_x, position_y);
		super.setSize(px, py);
	}
	public ExtraTalonPile(ExtraTalonPile other) {
		super(other);
		super.setSize(84, 112);
		super.setPositionX(other.getPositionX());
		super.setPositionY(other.getPositionY());
	}
	
	public void receiveCard(Card stockCard) {
		this.cards.add(stockCard);
	}


	protected void paintComponent(Graphics a) {
		super.paintComponent(a);

		if ( this.noCard() ) {
			a.drawImage(Card.getCardOutline(),0,0,84, this.getHeight(), this);
		} else if (!this.noCard() && this.topCard() != null) {
			a.drawImage(this.topCard().getImageCard(), 0, 0, 84, this.getHeight(), this);
		} else {
			System.out.println("Error: In ExtraTalonPile PaintComponent.");
		}	
	}
}
