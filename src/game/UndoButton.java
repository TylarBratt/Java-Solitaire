package game;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class UndoButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	public UndoButton(String text, int position_x, int position_y, int width, int height) {
		super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
		setBackground(Color.green);
		System.out.println("added undo Button");
	}

}