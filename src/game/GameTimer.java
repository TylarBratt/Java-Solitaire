package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameTimer extends JButton implements ActionListener {
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    //private JButton jbtn; //creation of button inside the JFrame window
    
    public GameTimer(String text, int position_x, int position_y, int width, int height, int tm) {
        super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
        // JButton jbtn = new JButton("Starting Timer...");
		// jbtn.setPreferredSize(new Dimension(width, height));
		// jbtn.setBounds(position_x, position_y, width, height);
		// this.add(jbtn);
		System.out.println("added jbutton");
        setTimer();
	}
    
    private void setTimer() {
        Timer swingtimer = new Timer(1000,this);
        swingtimer.start();
    }

    public void actionPerformed(ActionEvent evnt) {
        seconds++;
        if ( seconds == 60 ) {
            seconds = 0;
            minutes++;
            if ( minutes == 60 ) {
                minutes = 0;
                hours++;
            }
        }
        setText(pad(hours, 2) + ":" + pad(minutes, 2) + ":" + pad(seconds, 2)); //changing the label of button as the timer decreases
    }

    private String pad(int num, int size) {
        String numStr = Integer.toString(num);
        while (numStr.length() < size) {
            numStr = "0" + numStr;
        }
        return numStr;
    }
}

