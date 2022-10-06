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
	
	public Card(int value, Suit suit) {
		this.value = value;
		switch(suit) {
		case Clubs:
			this.suit = "c";
			break;
		case Diamonds:
			this.suit = "d";
			break;
		case Spades:
			this.suit = "s";
			break;
		case Hearts:
			this.suit = "h";
			break;
		}
		face = false;
	}
	
	public boolean isFace() {
		return face;
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
}