package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Deque;

import game.Card;
import game.Tableau;

import java.awt.Color;
//import java.awt.GradientPaint;

public class Tableau extends Pile implements Cloneable{

	/**
	 * This file displays the tableau field. the lines comentented out are a color gradient, it look cool but is not important.
	 */
	private static final long serialVersionUID = 1L;

	public Tableau(int position_x, int position_y, int size, Background bg) {
		super(position_x, position_y);
		super.setSize(84, 600); //previously 600
		super.setOpaque(false);
		for(int i = 0; i < size; i++) {
			push(bg.getStockPile().pop());
		}
		
		if(size > 0) {
			topCard().showFace();
		}
	}

	//clone method
	public Tableau(Tableau other) {
		super(other);
		// this.position_x = other.position_x;
		// this.position_y = other.position_y;
		super.setSize(84, 600);
		super.setOpaque(false);
		super.setPositionX(other.getPositionX());
		super.setPositionY(other.getPositionY());
		int size = other.cards.size();
		if(size > 0) {
			topCard().showFace();
		}
	}
	
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);	
		Graphics2D graphic = (Graphics2D) a;
		graphic.setColor(Color.GRAY);
		graphic.drawLine(0, 0, this.getWidth(), 0);
		graphic.drawLine(0, 0, 0, 112);
		graphic.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, 112);
		
		int cardYpos = 0;
		if(this.noCard()) {
			
		}else {
			for(Card c : this.cards) {
				if(c.isFace()) {
					graphic.drawImage(c.getImageCard(), 0, cardYpos, 84, 112, this) ;
					cardYpos += 20;
				}else {
					graphic.drawImage(Card.getBack(), 0, cardYpos, 84, 112, this);
					cardYpos += 20;
			}}
		}
	}

	public boolean moveWaste(TalonPile tp, Card card) {
		
		if(this.accepts(card)) {
			this.push(tp.pop());
			return true;
		}
		return false;
	}

	public boolean accepts(Card card) {
		if(!this.noCard()) {
			boolean result = this.topCard().getValue() == card.getValue() + 1 &&
			!this.topCard().getColor().equals(card.getColor());
			return result;
		}
		return card.getValue() == 13;
	}

	public Card getTableauCardClick(int i) {
		
		int index = i/20;
		
		if(index < this.cards.toArray().length) {
			
			Card returncard = (Card) cards.toArray()[index];
			if(returncard.isFace()) {
				return returncard;
			}
			
		}
		
		return (Card) cards.toArray()[cards.toArray().length - 1];
	}
	
	public boolean moveTo(Foundation dest, Card card) {
		
		if(dest.accepts(card)) {
			//check to make sure card clicked is available to move to foundation
			if ( card == this.topCard()) {
				dest.push(this.pop());
			}
			if(!this.noCard()) {
				this.topCard().showFace();
			}
			
			return true;
		}
		return false;
	}

	
	
	public boolean moveTo(Tableau destination, Card card) {
		if(!this.noCard()) {
			this.topCard().showFace();
		}
		if (!this.noCard() || card.getValue() == 13) {
			if (destination.accepts(card)) {
                Deque<Card> toBeMovedCards = new ArrayDeque<>();
                    while(!this.noCard()) {
                	    Card tmp = this.pop();
                	    toBeMovedCards.push(tmp);
                	    if(tmp.equals(card)) {
                			break;
                	    }
                    }
                    while(!toBeMovedCards.isEmpty()) {
                	    destination.push(toBeMovedCards.pop());
                    }
					return true;
			    }
		    }
		return false;
     }
	 
	 public Object clone() throws CloneNotSupportedException
	 {
		 return super.clone();
	 }
}
