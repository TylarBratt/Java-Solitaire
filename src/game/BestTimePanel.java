package game;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;

public class BestTimePanel extends JButton{
	private int minutes;
	private int hours;
	private int seconds;

	private static final long serialVersionUID = 1L;
	
	public BestTimePanel(String text, int position_x, int position_y, int width, int height, int tm) {
		super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
		setBackground(Color.pink);
		System.out.println("added BestTime Button");
	}

	public void setTime(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public int getNormalizedTime() {
		return this.hours*3600 + this.minutes*60 + this.seconds;
	}

	public void setTimeText(String playerTime){
		setText("<html><center>Best Time<br />" + playerTime +"</center></html>");
	}

}