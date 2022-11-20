package game;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class CardMoveListener extends MouseInputAdapter {

	
	private StockPile sp = Background.getStockPile();
	private TalonPile tp = null;
	private WinPanel wp = null;
	private Card card = null;
	private Tableau tableaucard = null;
	private Foundation foundationPile = null;
	public boolean listenerPlayerWon = false; 

	@Override
	public void mousePressed(MouseEvent e) {
		
		Component pressed = e.getComponent().getComponentAt(e.getPoint());

		if(pressed instanceof Foundation) {
			
			foundationPile = (Foundation) pressed;
			tableaucard = null;
			tp = null;
			card = foundationPile.topCard();
					
		}
		
		else if(pressed instanceof Tableau) {
			tableaucard = (Tableau) pressed;
			tp = null;
			card = tableaucard.getTableauCardClick(e.getY() - 150);
			for(Foundation foundation : Background.getFoundation()) {
				if(tableaucard.moveTo(foundation, card)) {
					tableaucard = null;
					break;
					
				}
			}
		}
		
		else if(pressed instanceof StockPile) {
			
			tableaucard = null;
			if(!sp.noCard()) {
				TalonPile tp = Background.getTpPile();
				tp.push(sp.pop());
				tp.topCard().showFace();
			}
			else {
				TalonPile tp = Background.getTpPile();
				System.out.println("Stock pile is empty");
				sp.takeTalon(tp);
			}
		}
		
		else if(pressed instanceof TalonPile) {
			
			tableaucard = null;
			wp = Background.getWinPanel();
			tp = Background.getTpPile();
			card = tp.topCard();
			if(card != null) {
				for(Foundation foundation : Background.getFoundation()) {
					foundation.moveWaste(tp, card);					
				}
			}
		}
		
		
		e.getComponent().repaint();

		checkWinState(Background.getFoundation(), pressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(card != null) {
			
			Component release = e.getComponent().getComponentAt(e.getPoint());
			
			if(release instanceof Tableau) {
				System.out.println("Move Card to tableau!");
				if(tp != null) {
					Tableau tableauCard = (Tableau) release;
					if(!tp.noCard()) {
						tableauCard.moveWaste(tp, card);
					}
					tp.repaint();
				}
				else if(tableaucard != null) {
					Tableau src = tableaucard;
					Tableau dest = (Tableau) release;
					src.moveTo(dest, card);
					src.repaint();
				}
				else if(foundationPile != null) {	
					Foundation src = foundationPile;
					Tableau dest = (Tableau)release;
					src.moveTo(dest, card);
					src.repaint();
					dest.repaint();
				}
			}
		}
		
		
		e.getComponent().repaint();
		card = null;
		foundationPile = null;
		tableaucard = null;
		tp = null;
	}

	public boolean checkWinState(Foundation[] foundations, Component pressed) {
		int completeFoundations = 0;
		for ( int i=0; i<4; i++ ) {
			Foundation currentFoundation = foundations[i];
			int foundationSize = currentFoundation.cards.size();
			if ( foundationSize == 13 ) {
				completeFoundations++;
			}
			System.out.println(currentFoundation.cards.size());
		}
		if ( completeFoundations==4 ) {
			System.out.println("You won!");
			wp = new WinPanel("You won!", 275, 300, 200, 100, 0);
			pressed.getParent().add(wp);
			return true;	
		}
		else {
			System.out.println("You haven't won yet");
			return false;
		} 
	}

}
