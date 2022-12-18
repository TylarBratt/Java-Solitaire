package game;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;

public class Pile extends JLayeredPane{

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
		this.setPositionX(position_x);
		this.setPositionY(position_y);
	}

	//cloning constructor for undo
	public Pile(Pile other) {
		
		super.setLocation(other.getPositionX(), other.getPositionY());
		this.setPositionX(other.getPositionX());
		this.setPositionY(other.getPositionY());
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

	public Card secondCard()
	{
		if( this.cards.size() > 1 ) {
			return this.cards.get(1);	
		}
		
		return null;
	}	
	
	public Card pop() {
		try {
			return cards.pop();
		} catch (EmptyStackException e) {
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

	public int getPositionX(){
		return this.position_x;
	}

	public int getPositionY(){
		return this.position_y;
	}

	public void setPositionX(int position_x){
		this.position_x = position_x;
	}

	public void setPositionY(int position_y){
		this.position_y = position_y;
	}
}
