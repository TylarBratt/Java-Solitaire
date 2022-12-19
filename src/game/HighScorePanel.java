package game;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class HighScorePanel extends JLabel{
	
	private static final long serialVersionUID = 1L;
	
	public HighScorePanel(String text, int position_x, int position_y, int width, int height) {
		super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
		setBackground(Color.pink);
        setOpaque(true);
		System.out.println("Added high score panel");
	}

    public void setScoreText(int score) {
        setText("<html><div style='text-align: center;'>High Score: " + score + "</div></html>");
    }
}
