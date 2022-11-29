package game;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JPanel;

public class Pile extends JPanel{

	/**
	 *this class is used as an abstract class for anything that is built to the screen. 
	 *It defines a method that places the object on the window.
	 */
	private static final long serialVersionUID = 1L;
	protected int position_x, position_y;
	protected Stack<Card> cards;
	
	
	public Pile(int position_x, int position_y) {
		super.setLocation(position_x, position_y);
		cards = new Stack<>();
	}

	//cloning constructor for undo
	public Pile(Pile other) {
		this.position_x = other.position_x;
		this.position_y = other.position_y;
		this.cards = new Stack<Card>();
		for ( Card card : other.cards) {
			Card cloned_card = (Card) card.clone();
			this.cards.push(cloned_card);
		}
	}
	
	public Card topCard()
	{
		if(!this.cards.isEmpty()) {
			return this.cards.peek();	
		}
		
		return null;
	}	
	
	public Card pop() {
		try {
	
		return cards.pop();
	}catch(EmptyStackException e) {
		return null;
	}
		}
	
	public void push(Card someCard) {
		this.cards.push(someCard);
	}
	public boolean noCard() {
		return this.cards.isEmpty();
	}

	public Stack<Card> getCards() {
		return this.cards;
	}

	public void setCards(Stack<Card> cards) {
		this.cards = cards;
	}
}
