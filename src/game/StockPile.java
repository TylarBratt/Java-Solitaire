package game;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.Collections;
import java.awt.Graphics;
import java.awt.Color;

public class StockPile extends Pile{

	/**
	 * This defines the color and size of the StockPile
	 */
	private static final long serialVersionUID = 1L;



	public StockPile(int position_x, int position_y) {
		super(position_x, position_y);
		super.setSize(84, 112);
		for(Suit suit: Suit.values()) {
			for(int i = 1; i <= 13; i++) {
				Card card = new Card(i, suit);
				push(card);
				System.out.println("Pushed into deck " + card);
			}
		}
		//enable shuffle in production mode
		Collections.shuffle(cards);
	}

	public StockPile(StockPile other) {
		super(other);
		super.setSize(84, 112);
		super.setPositionX(other.getPositionX());
		super.setPositionY(other.getPositionY());
	}

	public void takeTalon(TalonPile talonPile) {
		if (Main.easyHard == 0) {
		int talonPileSize = talonPile.cards.size();
		for ( int i = 0; i < talonPileSize; i++ ) {
			Card card = talonPile.pop();
			this.cards.addElement(card);
		}
	}}
	public void takeTalon(ExtraTalonPile talonPile) {
		if (Main.easyHard == 2) {
		int talonPileSize = talonPile.cards.size();
		for ( int i = 0; i <= talonPileSize; i++ ) {
			Card card = talonPile.pop();
			this.cards.addElement(card);
		}
	}}
	
	public void paintComponent(Graphics a) {
		super.paintComponent(a);
		Graphics2D graphic = (Graphics2D) a;
		graphic.setStroke(new BasicStroke(6));
		graphic.setColor(Color.gray);
		graphic.drawRect(0, 0, this.getWidth(), this.getHeight());
		
		if(!noCard()) {
		graphic.drawImage(Card.getBack(), 0, 0, 84, this.getHeight(), this);
		}
	}
}
