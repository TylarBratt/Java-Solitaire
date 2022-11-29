package game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import java.util.Stack;
import java.util.ArrayList;

public class CardMoveListener extends MouseInputAdapter {

	
	private StockPile sp = Main.bg.getStockPile();
	private TalonPile tp = null;
	private WinPanel wp = null;
	private BestTimePanel bestTimePanel = null; 
	private Card card = null;
	private Tableau tableaucard = null;
	private Foundation foundationPile = null;
	public boolean listenerPlayerWon = false; 
	private GameTimer gameTimer;
	//private Foundation mementoFoundation;
	private Tableau tableauMemento;

	@Override
	public void mousePressed(MouseEvent e) {
		
		Background bg = Main.getMainBG();
		Component pressed = e.getComponent().getComponentAt(e.getPoint());
		System.out.println("pressed: " + pressed);
		if(pressed instanceof Foundation) {
			
			foundationPile = (Foundation) pressed;
			Main.setMementoFoundation(foundationPile);
			tableaucard = null;
			tp = null;
			card = foundationPile.topCard();
			Main.setMementoCard(card);
			
			for (Card card : Main.mementoFoundation.getCards()) {
				System.out.println("memento card: " + card);
			}
			for (Card card : foundationPile.getCards()) {
				System.out.println("foundation card: " + card);
			}
		}
		
		else if(pressed instanceof Tableau) {
			tableaucard = (Tableau) pressed;
			tp = null;
			card = tableaucard.getTableauCardClick(e.getY() - 150);
			Main.setMementoCard(card);
			for(Foundation foundation : bg.getFoundationArray()) {
				if(tableaucard.moveTo(foundation, card) && card==tableaucard.topCard()) {
					tableaucard = null;
					break;
					
				}
			}
		}
		
		else if(pressed instanceof StockPile) {
			
			tableaucard = null;
			if(!sp.noCard()) {
				TalonPile tp = bg.getTpPile();
				tp.push(sp.pop());
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
			Main.setMementoCard(card);
			Main.setMementoTalon(tp);
			
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
		if(card != null) {
			
			Component release = e.getComponent().getComponentAt(e.getPoint());
			
			if(release instanceof Tableau) {
				System.out.println("Move Card to tableau!");
				if(tp != null) {
					Tableau tableauCard = (Tableau) release;
					Main.setMementoTableau(tableauCard);
					System.out.println("in mouseReleased Tableau instance");
					if(!tp.noCard()) {
						tableauCard.moveWaste(tp, card);
					}
					tp.repaint();
				}
				else if(tableaucard != null) {
					Tableau src = tableaucard;
					Tableau dest = (Tableau) release;

					Main.setMementoTableau(dest);
					System.out.println("in mouseReleased Tableau instance");
					src.moveTo(dest, card);
					src.repaint();
				}
				else if(foundationPile != null) {	
					Foundation src = foundationPile;
					Tableau dest = (Tableau)release;

					Main.setMementoTableau(dest);
					System.out.println("in mouseReleased Tableau instance");
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

		for ( Tableau one_tableau : bg.getTableauArray() ) {
			System.out.println("background tableau in cardlistner: " + one_tableau.cards);
		}
		Background backgroundMemento = new Background(bg);
		Main.addToBackgroundArray(backgroundMemento);

		System.out.println("Background array");
		ArrayList<Background> backgroundArray = Main.getBackgroundArray();
		Main.undoTracker += 1;
		for ( Background background : backgroundArray ) {
			// System.out.println("background: " + background);
			// System.out.println("background talon cards: " + background.tp.cards);
			// System.out.println("background talon: " + background.tp);
		}

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
			System.out.println(currentFoundation.cards.size());
		}
		if ( completeFoundations==4 ) {
			System.out.println("You won!");
			gameTimer = bg.getGameTimer();
			gameTimer.stopTimer();
			String playerTime = gameTimer.getCurrentTime();
			wp = new WinPanel("<html><center>You won!<br />Your time: " + playerTime + "<br /><br />Press 'e' to start a new game.</center></html>", 275, 300, 250, 125, 0);
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
