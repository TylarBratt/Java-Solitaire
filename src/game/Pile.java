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
}
