package game;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class ScorePanel extends JLabel{
	
	private static final long serialVersionUID = 1L;
	
	public ScorePanel(String text, int position_x, int position_y, int width, int height) {
		super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
		setBackground(Color.pink);
        setOpaque(true);
	}

    public void setScoreText(int score) {
        setText("<html><div style='text-align: center;'>Score: " + score + "</div></html>");
    }
}
