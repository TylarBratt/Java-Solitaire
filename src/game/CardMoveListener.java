package game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import java.util.Stack;

public class CardMoveListener extends MouseInputAdapter {

	
	private StockPile sp = null;
	private TalonPile tp = null;
	private WinPanel wp = null;
	private Card card = null;
	private Tableau tableaucard = null;
	private Foundation foundationPile = null;
	public boolean listenerPlayerWon = false; 
	private GameTimer gameTimer;

	@Override
	public void mousePressed(MouseEvent e) {
		
		Background bg = Main.getMainBG();
		ScorePanel scorePanel = Main.getScorePanel();
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
					if( tableaucard.moveTo(foundation, card) ) {
						Main.increaseScore(10);
						scorePanel.setScoreText(Main.score);	
						tableaucard = null;
						break;
					}	
				}
			}
		}
		
		else if(pressed instanceof StockPile) {
			if (Main.easyHard != 2) {
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
			}else if(Main.easyHard == 2){
				TalonPile tp = bg.getTpPile();
				ExtraTalonPile etp = bg.getEtpPile();
				sp = bg.getStockPile();
//first thing we do is initialize the piles and move the card from the talon to the extra talon.
				if (!tp.noCard() ) {
					etp.push(tp.pop());
					e.getComponent().repaint();
				}
//this algorithm didnt work it has been rethought. It needs to first check if there is an available card in the stock pile then it needs to move the talon pile card to the extra talon pile. Then it needs to loop 3 times to grab 3 cards from the stockpile but check every single time. if it does not it should break the loop and then move the top card from the extra pile into the talon pile. I frankly think that after every card move e. should be repainted.
//we actually need two loops nested. One will do the first move and if it can't then it will reshuffle the extra pile. If it can do the first move itll move then move onto the second. and attempt to check and do the second and third move. If not possible it should break the whole if statement and end with sending the top card of the extra pile to the talon pile.
					if(!sp.noCard()) {
					etp.push(sp.pop());
						outer:for (int i = 0; i < 2; i++) {
									if(!sp.noCard()) {
										etp.push(sp.pop());
										}else {
										break outer;
										}
									}
						tp.push(etp.pop());
						}else {
						ExtraTalonPile etp2 = bg.getEtpPile();
						System.out.println("Stock pile is empty");
						sp.takeTalon(etp2);
						}
					e.getComponent().repaint();		
			}
			
			
		}
			
			
		else if(pressed instanceof TalonPile) {
			
			tableaucard = null;
			tp = bg.getTpPile();
			card = tp.topCard();

			if(card != null) {
				for(Foundation foundation : bg.getFoundationArray()) {
					if ( foundation.moveWaste(tp, card) == true ) {
						//increase score by 10 points to reflect movement to foundation
						Main.increaseScore(10);
						scorePanel.setScoreText(Main.score);				
					}
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
		ScorePanel scorePanel = Main.getScorePanel();
		if(card != null) {
			
			Component release = e.getComponent().getComponentAt(e.getPoint());
			
			if(release instanceof Tableau) {
				System.out.println("Move Card to tableau!");
				if(tp != null) {
					System.out.println("From talon pile");
					Tableau tableauCard = (Tableau) release;
					if(!tp.noCard()) {
						if(Main.easyHard == 2) {
							ExtraTalonPile etp = bg.getEtpPile();
								tableauCard.moveWaste(tp, etp, card);
						Main.increaseScore(5);
						scorePanel.setScoreText(Main.score);	
						}else {
					
						}
						
						
						
							
					}
					tp.repaint();
					
				}
				else if(tableaucard != null) {
					Tableau src = tableaucard;
					Tableau dest = (Tableau) release;
					
					src.moveTo(dest, card);
					System.out.println("dest: " + dest);
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
		
		if ( Main.easyHard == 0 ) {
			Main.addToBackgroundArray(backgroundMemento);
		}
		
		Integer scoreMemento = new Integer(Main.score);
		Main.addToScoreArray(scoreMemento);
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
				//bestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + playerTime +"</center></html>", 0, 515 , 125, 50, 0);
				Main.bestNormalizedTime = gameTimer.getNormalizedTime();
				Main.setPlayerWon(true);
				Main.setPlayerTime(playerTime);
				//pressed.getParent().add(bestTimePanel);
			}

			
			if ( Main.getScore() > Main.getHighScore()) {
				int highScore = Integer.valueOf(Main.getScore());
				Main.setHighScore(highScore);
				System.out.println("highScore: " + highScore);
			}
			
			return true;	
		}
		else {
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
