package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Start extends JPanel {

	/**
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public static String startImage = "Start";
	public static Image image;
	static protected Background bg;
	

	public Start() {
		image = getStart();
		super.setLayout(null);
	}



	public static Image getStart() {
		ImageIcon icon = new ImageIcon(Card.class.getResource(Card.directory + "/" + startImage + ".jpg"));
		Image image = icon.getImage();
		return image;
	}
	
	
	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.cyan);
		a.fillRect(0,0,this.getWidth(), this.getHeight());
		a.drawImage(image, 30, 25, this);
	}


}


