package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameTimer extends JButton implements ActionListener {
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    Timer swingtimer = new Timer(1000,this);
    String currentTime;
    
    public GameTimer(String text, int position_x, int position_y, int width, int height, int tm) {
        super.setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBounds(position_x, position_y, width, height);
        setText(text);
		System.out.println("added jbutton");
        setTimer();
	}
    
    private void setTimer() {
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
        currentTime = pad(hours, 2) + ":" + pad(minutes, 2) + ":" + pad(seconds, 2);
        setText(currentTime); //changing the label of button as the timer decreases
    }

    public void stopTimer() {
        swingtimer.stop();
    }

    public String getCurrentTime() {
        System.out.print("GameTimer current time: " + currentTime);
        return currentTime;
    }

    private String pad(int num, int size) {
        String numStr = Integer.toString(num);
        while (numStr.length() < size) {
            numStr = "0" + numStr;
        }
        return numStr;
    }

    public int getNormalizedTime(){
        return this.hours*3600 + this.minutes*60 + this.seconds;
    }
}

