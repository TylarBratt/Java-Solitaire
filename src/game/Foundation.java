package game;

import java.awt.Graphics;
public class Foundation extends Pile implements Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int suit;
	
	public Foundation(int position_x, int position_y, int i) {
		super(position_x, position_y);
		super.setSize(84, 112);
		this.suit = i;
	}

	public Foundation(Foundation other){
		super(other);
		this.position_x = other.position_x;
		this.position_y = other.position_y;
		this.suit = other.suit;
	}
	
	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		if(this.noCard()) {
			a.drawImage(Card.getFoundation(suit), 0, 0, this.getWidth(), this.getHeight(), this);
		}
		else {
			a.drawImage(this.topCard().getImageCard(), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	public void moveWaste(TalonPile tp, Card card) {
	
		if(accepts(card)) {
			this.push(tp.pop());
			tp = null;
		}
	}
	
	public void moveTo(Tableau dest, Card card) {
		if(dest.accepts(card)) {
			dest.push(this.pop());
		}
	}

	public boolean accepts(Card card) {

		if(!this.noCard()) {
			boolean result = this.topCard().getValue() == card.getValue() - 1 && this.topCard().getSuit().equals(card.getSuit());
			return result;
		}
		return card.getValue() == 1  && intToSuit(card.getSuit());
	}

	private boolean intToSuit(String suit) {
	
		
		if(suit.equals("c")) {
			return this.suit == 3;
		}
		else if(suit.equals("s")) {
			return this.suit == 1;
		}
		else if(suit.equals("h")) {
			return this.suit == 2;
		}
		else if(suit.equals("d")) {
			return this.suit == 4;
		}
		
		return false;
	}

	public int getSuit() {
		return suit;
	}
}
