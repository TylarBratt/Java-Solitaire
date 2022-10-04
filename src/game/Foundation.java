package game;

import java.awt.Graphics;
public class Foundation extends Pile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int suit;
	
	public Foundation(int position_x, int position_y, int i) {
		super(position_x, position_y);
		super.setSize(84, 112);
		this.suit = i;
		
	}
	
	@Override
	protected void paintComponent(Graphics a) {
		super.paintComponent(a);
		if(this.noCard()) {
			a.drawImage(Card.getFoundation(suit), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	

}
