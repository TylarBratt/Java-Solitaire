package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.*;

public class WinPanel extends JButton{

	/**
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public static String startImage = "Start";
	public static Image image;
	static protected Background bg;
	

	public WinPanel(String text, int position_x, int position_y, int width, int height, int tm) {
		super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
		setBackground(Color.pink);
		System.out.println("added Win Button");
	}



	public static Image getWinPanel() {
		ImageIcon icon = new ImageIcon(Card.class.getResource(Card.directory + "/" + startImage + ".jpg"));
		Image image = icon.getImage();
		return image;
	}
	
	
	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		a.setColor(Color.cyan);
		//a.fillRect(0,0,this.getWidth(), this.getHeight());
		a.drawImage(image, 30, 25, this);
	}
    
	@Override
    public boolean isOptimizedDrawingEnabled()
    {
        return false;
    }

}