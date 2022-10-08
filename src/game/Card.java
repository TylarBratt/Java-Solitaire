package game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Card {
	//this defines a class for cards with a method to draw both the front and back
	//these variables are file names to locate the images.
	public static String cardBackName = "back";
	public static String cardOutlineName = "bottom";
	public static String foundationName = "fpbase0";
	
	public static String directory = "cards";
	public static String extension = ".gif";
	private Image image;
	private int value;
	public String suit;
	private boolean face;
	
	private Colour color;
	
	public Card(int value, Suit suit) {
		this.value = value;
		switch(suit) {
		case Clubs:
			this.suit = "c";
			color = Colour.Black;
			break;
		case Diamonds:
			this.suit = "d";
			color = Colour.Red;
			break;
		case Spades:
			this.suit = "s";
			color = Colour.Black;
			break;
		case Hearts:
			this.suit = "h";
			color = Colour.Red;
			break;
		}
		face = false;
		
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource(directory + fileCards(suit, value)));
			image = icon.getImage();
		}
		catch(Exception e)	{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private String fileCards(Suit suit, int value) {
		
		char ch;
		
		if(value < 1 || value > 13) {
			 throw new IllegalArgumentException("Bad Card Number");
		}
		
		if(suit == Suit.Clubs) {
			ch = 'c';
		}
		else if(suit == Suit.Spades){
			ch = 's';
		}
		else if(suit == Suit.Diamonds){
			ch = 'd';
		}
		else if(suit == Suit.Hearts){
			ch = 'h';
		}
		else throw new IllegalArgumentException("Bad Card Suit");
		
		if(value < 10)
			return "/" + value + ch + extension;
		else 
			return "/" + value + ch + extension;

	}
	
	public Image getImageCard() {
		return image;
	}

	public boolean isFace() {
		return face;
	}
	
	
	
	
	public Colour getColor() {
		return color;
	}

	public String toString() {
		return value + " of " + suit;
	}
	public static Image getFoundation(int suit) {
		ImageIcon icon = new ImageIcon(Card.class.getResource(directory + "/" + foundationName + suit + extension));
		Image image = icon.getImage();
		return image;
	}
	public static Image getCardOutline() {
		ImageIcon icon = new ImageIcon(Card.class.getResource(directory + "/" + cardOutlineName + extension));
		Image image = icon.getImage();
		return image;
	}
	
	
	public static Image getBack() {
	ImageIcon icon = new ImageIcon(Card.class.getResource(directory + "/" + cardBackName + extension));
	Image image = icon.getImage();
	return image;
	}
	
	
	
	public int getValue() {
		return value;
	}

	public String getSuit() {
		return suit;
	}

	public void showFace() {
		face = true;
	}
}