package game;

import javax.swing.JPanel;

public class Stack extends JPanel{

	/**
	 *this class is used as an abstract class for anything that is built to the screen. 
	 *It defines a method that places the object on the window.
	 */
	private static final long serialVersionUID = 1L;
	protected int position_x, position_y;
	
	
	public Stack(int position_x, int position_y) {
		super.setLocation(position_x, position_y);
	}
}
