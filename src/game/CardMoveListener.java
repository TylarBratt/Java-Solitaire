package game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import java.util.Stack;
import java.util.ArrayList;

public class CardMoveListener extends MouseInputAdapter {

	
	private StockPile sp = null;
	private TalonPile tp = null;
	private WinPanel wp = null;
	private BestTimePanel bestTimePanel = null; 
	private Card card = null;
	private Tableau tableaucard = null;
	private Foundation foundationPile = null;
	public boolean listenerPlayerWon = false; 
	private GameTimer gameTimer;

	@Override
	public void mousePressed(MouseEvent e) {
		
		Background bg = Main.getMainBG();
		Background backgroundMemento = new Background(bg);
		
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
			if ( tableaucard.cards.toArray().length > 0 ) {
				card = tableaucard.getTableauCardClick(e.getY() - 150);
				for(Foundation foundation : bg.getFoundationArray()) {
					if(tableaucard.moveTo(foundation, card) && card==tableaucard.topCard()) {
						tableaucard = null;
						break;
					}	
				}
			}
		}
		
		else if(pressed instanceof StockPile) {
			sp = bg.getStockPile();
			tableaucard = null;
			if(!sp.noCard()) {
				TalonPile tp = bg.getTpPile();
				tp.push(sp.pop());
				bg.setTalonPile(tp);
				tp.topCard().showFace();
			}
			else {
				TalonPile tp = bg.getTpPile();
				System.out.println("Stock pile is empty");
				sp.takeTalon(tp);
			}
		}
		
		else if(pressed instanceof TalonPile) {
			
			tableaucard = null;
			tp = bg.getTpPile();
			card = tp.topCard();
			if(card != null) {
				for(Foundation foundation : bg.getFoundationArray()) {
					foundation.moveWaste(tp, card);					
				}
			}
		}
		
		e.getComponent().repaint();
		checkWinState(bg.getFoundationArray(), pressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Background bg = Main.getMainBG();
		Background backgroundMemento = new Background(bg);

		if(card != null) {
			
			Component release = e.getComponent().getComponentAt(e.getPoint());
			
			if(release instanceof Tableau) {
				System.out.println("Move Card to tableau!");
				if(tp != null) {
					Tableau tableauCard = (Tableau) release;
					if(!tp.noCard()) {
						boolean canMove = tableauCard.moveWaste(tp, card);
						// if ( canMove == true ) {
						// 	System.out.println("tableauCard.moveWaste: " + canMove);
						// 	Main.addToBackgroundArray(backgroundMemento);
						// }
					}
					tp.repaint();
					
				}
				else if(tableaucard != null) {
					Tableau src = tableaucard;
					Tableau dest = (Tableau) release;
					
					boolean canMove = src.moveTo(dest, card);
					// if ( canMove == true ) {
					// 	System.out.println("tableauCard.moveTo: " + canMove);
					// 	Main.addToBackgroundArray(backgroundMemento);
					// }
					src.moveTo(dest, card);
					src.repaint();
				}
				else if(foundationPile != null) {	
					Foundation src = foundationPile;
					Tableau dest = (Tableau)release;
					// Main.addToBackgroundArray(backgroundMemento);
					src.moveTo(dest, card);
					src.repaint();
					dest.repaint();
				}
			}
			// bg = Main.getMainBG();
			
			// Background bg = Main.getMainBG();
			// Background backgroundMemento = new Background(bg);
			
		}
		if ( Main.easyHard == 0 ) {
			Main.addToBackgroundArray(backgroundMemento);
		}
		e.getComponent().repaint();
		card = null;
		foundationPile = null;
		tableaucard = null;
		tp = null;
	}

	public boolean checkWinState(Foundation[] foundations, Component pressed) {
		Background bg = Main.getMainBG();
		int completeFoundations = 0;
		for ( int i=0; i<4; i++ ) {
			Foundation currentFoundation = foundations[i];
			int foundationSize = currentFoundation.cards.size();
			if ( foundationSize == 13 ) {
				completeFoundations++;
			}
		}
		if ( completeFoundations==4 ) {
			System.out.println("You won!");
			gameTimer = bg.getGameTimer();
			gameTimer.stopTimer();
			String playerTime = gameTimer.getCurrentTime();
			wp = new WinPanel("<html><center>You won!<br />Your time: " + playerTime + "<br /><br />Press 'e' to start a new game.</center></html>", 250, 300, 250, 125, 0);
			pressed.getParent().add(wp);
			if ( Main.getBestNormalizedTime() > gameTimer.getNormalizedTime() ) {
				bestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + playerTime +"</center></html>", 0, 550 , 125, 50, 0);
				Main.bestNormalizedTime = gameTimer.getNormalizedTime();
				Main.setPlayerWon(true);
				Main.setPlayerTime(playerTime);
				pressed.getParent().add(bestTimePanel);
			}
			return true;	
		}
		else {
			System.out.println("You haven't won yet");
			return false;
		} 
	}
	
	public Stack<Card> getFoundationPile(){
		return foundationPile.getCards();
	}

	
	public void setFoundationPile(Foundation foundation){
		this.foundationPile = foundation;
	}

	public Foundation getFoundation(){
		return foundationPile;
	}

}
