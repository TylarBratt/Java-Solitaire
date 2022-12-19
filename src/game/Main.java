package game;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import javax.swing.SwingConstants;

public class Main extends JFrame implements KeyListener {
	
	/**
	 * Images were collected from https://pixabay.com/photos/playing-card-back-template-568201/ and https://pixabay.com/vectors/card-deck-deck-cards-playing-cards-161536/ as these images are free for commercial use.
	 */
	private static final long serialVersionUID = 1L;
	public static int easyHard = 0;
	static protected Start st;
	static protected Background bg = null;
	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 600;

	protected CardMoveListener mainCardMoveListener;
	
	/*Easy Mode Variables */
	static protected int bestEasyNormalizedTime = 100000;
	static protected String easyPlayerTime = "";
	protected BestTimePanel easyBestTimePanel;
	protected HighScorePanel easyHighScorePanel;
	protected static ScorePanel easyScorePanel;
	static protected boolean easyPlayerWon = false;
	static protected int easyScore;
	static protected int easyHighScore;
	private static UndoButton undoButton;

	/*Hard Mode Variables */
	static protected int bestHardNormalizedTime = 100000;
	static protected String hardPlayerTime = "";
	protected BestTimePanel hardBestTimePanel;
	protected HighScorePanel hardHighScorePanel;
	static protected boolean hardPlayerWon = false;
	static protected int hardScore;
	static protected int hardHighScore;
	protected static ScorePanel hardScorePanel;
	
	/* Vegas Mode Variables */
	static protected int bestVegasNormalizedTime = 100000;
	static protected String vegasPlayerTime = "";
	static protected int vegasScore;
	static protected int vegasHighScore;
	protected static ScorePanel vegasScorePanel;
	protected static HighScorePanel vegasHighScorePanel;
	protected BestTimePanel vegasBestTimePanel;
	static protected boolean vegasPlayerWon = false;

	protected static int tpShift = 100;
	public static Point TABLEAU_POSITION = new Point(20, 150);
	public static int TABLEAU_OFFSET = 100;
	
	public static Card mementoCard;
	public static Tableau mementoTableau;
	public static Foundation mementoFoundation;
	public static TalonPile mementoTalon;

	public static ArrayList<Background> mementoBackgroundArray;
	public static ArrayList<Integer> mementoScoreArray;

	public Main() {
		//This is the main constructor for the game. It gets called immediately on start up,
		// it sets the default close operation to actually close the JFrame window. 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//This section calls the constructor for Background which calls paintComponent and sets a Green background the size of 750*600
		st = new Start();
		st.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		add(st);
		pack();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public static void main(String[] args) {
		//our actual main class that gets run on startup, currently just sets the Main window to visible.
		//new Main().setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main().setVisible(true);
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char a = e.getKeyChar();
		char start = 'e';
		char hard = 'r';
		char j = 'j';
		char vegas = 't';
		
		
		/*Easy Mode*/
		
		if(a == start) {
			//determine if you are coming from start screen or end of a game
			if (   easyPlayerWon == false  ) {
				//clear undo arrays
				mementoBackgroundArray = new ArrayList<Background>();
				mementoScoreArray = new ArrayList<Integer>();
				easyScore = 0;
				easyHighScore = 0;
				easyHard = 0;
				if ( bg != null) {
					remove(bg);
				}
				if ( st != null) {
					remove(st);
				}
				bg = new Background();
				initializePiles(bg);
				
				//add initial state to mementoBackgroundArray
				Background initialBackground = new Background(bg);
				addToBackgroundArray(initialBackground);
				
				//add initial score to mementoBackgroundArray
				Integer initialScore = new Integer(easyScore);
				addToScoreArray(initialScore);
				
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				undoButton = new UndoButton("Undo last move", 300, 500, 125, 50);
				easyScorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + easyScore + "</div></html>", 0, 550, 125, 50);
				easyScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				bg.add(easyScorePanel);
				undoButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Thread() {
							public void run(){
								int mementoBackgroundArraySize = mementoBackgroundArray.size();
								if ( mementoBackgroundArraySize < 2 ) {
									System.out.println("No moves to be made");
								}
								else {
									remove(bg);
									int index = mementoBackgroundArraySize - 2;
									
									//clone item out of ArrayList
									bg = new Background(mementoBackgroundArray.get(index));
									
									//go through score array and decrease all scores by 2 to reflect cost of Undo action
									for ( int i = 0; i < mementoBackgroundArraySize-1; i++ ) {
										Integer currentScore = mementoScoreArray.get(i);
										int newScore = Integer.valueOf(currentScore) - 2;
										Integer newScoreInteger = new Integer(newScore);
										mementoScoreArray.set(i, newScoreInteger);
										System.out.println("decreased score: " + newScoreInteger);
									}
									
									easyScore = new Integer(mementoScoreArray.get(index));
									if ( mementoBackgroundArraySize > 1 ) {
										mementoBackgroundArray.remove(mementoBackgroundArraySize-1);
										mementoScoreArray.remove(mementoBackgroundArraySize-1);
									}
									bg.add(bg.getTpPile());
									bg.add(bg.getStockPile());

									for (int i = 0; i<bg.getTableauArray().length; i++) {
										bg.add(bg.getTableauArray()[i]);
									}

									for (int i = 0; i<bg.getFoundationArray().length; i++) {
										bg.add(bg.getFoundationArray()[i]);
									}

									easyScorePanel.setScoreText(easyScore);

									bg.add(undoButton);
									bg.add(easyScorePanel);
									bg.add(bg.gameTimer);
									bg.addMouseListener(game);
									bg.addMouseMotionListener(game);

									add(bg);

									bg.revalidate();
									bg.repaint();
								}
							}
				
						}.start();
					}
				});
			
				bg.add(undoButton);
				add(bg);
				validate();
			}
			else {
				//clear undo array
				mementoBackgroundArray = new ArrayList<Background>();
				easyHard = 0;
				easyScore = 0;
				//remove components
				bg.removeAll();

				//remove background
				remove(bg);
				
				bg = new Background();
				initializePiles(bg);
				
				//add initial state to mementoBackgroundArray
				Background initialBackground = new Background(bg);
				addToBackgroundArray(initialBackground);

				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				easyScorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + easyScore + "</div></html>", 0, 550, 125, 50);
				easyScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				easyBestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + easyPlayerTime +"</center></html>", 0, 500 , 125, 50, 0);
				easyHighScorePanel = new HighScorePanel("<html><div style='text-align: center;'>High Score: " + easyHighScore + "</div></html>", 125, 550, 125, 50);
				easyHighScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				
				bg.add(easyBestTimePanel);
				bg.add(easyHighScorePanel);
				bg.add(easyScorePanel);
				undoButton = new UndoButton("Undo last move", 300, 500, 125, 50);
				undoButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Thread() {
							public void run(){
								System.out.println("undo button pressed");

								int mementoBackgroundArraySize = mementoBackgroundArray.size();
								System.out.println("memento array size: " + mementoBackgroundArraySize);
								if ( mementoBackgroundArraySize < 2 ) {
									System.out.println("No moves to be made");
								}
								else {
									remove(bg);

									int index = mementoBackgroundArraySize - 2;
									
									//clone item out of ArrayList
									bg = new Background(mementoBackgroundArray.get(index));
									
									//go through score array and decrease all scores by 2 to reflect cost of Undo action
									for ( int i = 0; i < mementoBackgroundArraySize-1; i++ ) {
										Integer currentScore = mementoScoreArray.get(i);
										int newScore = Integer.valueOf(currentScore) - 2;
										Integer newScoreInteger = new Integer(newScore);
										mementoScoreArray.set(i, newScoreInteger);
										System.out.println("decreased score: " + newScoreInteger);
									}
									
									easyScore = new Integer(mementoScoreArray.get(index));
									if ( mementoBackgroundArraySize > 1 ) {
										mementoBackgroundArray.remove(mementoBackgroundArraySize-1);
										mementoScoreArray.remove(mementoBackgroundArraySize-1);
									}
									bg.add(bg.getTpPile());
									bg.add(bg.getStockPile());

									for (int i = 0; i<bg.getTableauArray().length; i++) {
										bg.add(bg.getTableauArray()[i]);
									}

									for (int i = 0; i<bg.getFoundationArray().length; i++) {
										bg.add(bg.getFoundationArray()[i]);
									}

									easyScorePanel.setScoreText(easyScore);
									easyHighScorePanel.setScoreText(easyHighScore);
									easyBestTimePanel.setTimeText(easyPlayerTime);
									bg.add(undoButton);
									bg.add(easyScorePanel);
									bg.add(easyHighScorePanel);
									bg.add(easyBestTimePanel);
									bg.add(bg.gameTimer);
									bg.addMouseListener(game);
									bg.addMouseMotionListener(game);

									add(bg);

									bg.revalidate();
									bg.repaint();
								}
							}
				
						}.start();
					}
				});
			
				easyBestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + easyPlayerTime +"</center></html>", 0, 500 , 125, 50, 0);
				easyHighScorePanel = new HighScorePanel("<html><div style='text-align: center;'>High Score: " + easyHighScore + "</div></html>", 125, 550, 125, 50);
				easyHighScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				bg.add(easyBestTimePanel);
				bg.add(easyHighScorePanel);
				bg.add(undoButton);
				add(bg);
				validate();
			}

		/*Hard Mode */

		} else if (a == hard) {
			if ( hardPlayerWon == false ) {
				if ( bg != null ) {
					remove(bg);
				}
				hardScore = 0;
				easyHard = 1;
				remove(st);
				bg = new Background();
				
				hardScorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + hardScore + "</div></html>", 0, 550, 125, 50);
				hardScorePanel.setHorizontalAlignment(SwingConstants.CENTER);

				bg.add(hardScorePanel);
				
				initializePiles(bg);
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				add(bg);
				revalidate();
			}
			
			else {
				easyHard = 1;
				hardScore = 0;
				remove(bg);
				bg = new Background();
				initializePiles(bg);
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				hardScorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + hardScore + "</div></html>", 0, 550, 125, 50);
				hardScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				hardBestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + hardPlayerTime +"</center></html>", 0, 500 , 125, 50, 0);
				hardHighScorePanel = new HighScorePanel("<html><div style='text-align: center;'>High Score: " + hardHighScore + "</div></html>", 125, 550, 125, 50);
				hardHighScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				
				bg.add(hardScorePanel);
				bg.add(hardBestTimePanel);
				bg.add(hardHighScorePanel);
		
				add(bg);
				revalidate();
			}

		/*Vegas Mode */

		} else if ( a == vegas ) {
			if (  vegasPlayerWon == false ) {
				if ( bg != null ) {
					remove(bg);
				}
				easyHard = 2;
				remove(st);
				vegasScore = -52;
				bg = new Background();
				initializePiles(bg);
	
				vegasScorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + vegasScore + "</div></html>", 0, 550, 125, 50);
				vegasScorePanel.setHorizontalAlignment(SwingConstants.CENTER);

				bg.add(vegasScorePanel);
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				remove(Main.st);
				add(bg);
				
				revalidate();
			}
			else {
				easyHard = 2;
				remove(bg);
				bg = new Background();
				initializePiles(bg);
				CardMoveListener game = new CardMoveListener();
				vegasScore = -52;
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				vegasScorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + vegasScore + "</div></html>", 0, 550, 125, 50);
				vegasScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				vegasBestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + vegasPlayerTime +"</center></html>", 0, 500 , 125, 50, 0);
				vegasHighScorePanel = new HighScorePanel("<html><div style='text-align: center;'>High Score: " + vegasHighScore + "</div></html>", 125, 550, 125, 50);
				vegasHighScorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				
				bg.add(vegasScorePanel);
				bg.add(vegasBestTimePanel);
				bg.add(vegasHighScorePanel);
				add(bg);
				revalidate();
			}
		
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}	
	}

	private void initializePiles(Background bg) {
		StockPile sp = new StockPile(650, 15);
		bg.add(sp);
		bg.sp = sp;
		TalonPile tp = new TalonPile(650 - tpShift, 15);
		bg.add(tp);
		bg.tp = tp;
		if(Main.easyHard == 2) {
			ExtraTalonPile etp = new ExtraTalonPile(625 - tpShift, 15, 80, 112);
			ExtraTalonPile dummyEtp =  new ExtraTalonPile(600 - tpShift, 15, 80, 112);
			bg.add(etp);
			bg.etp = etp;
			bg.add(dummyEtp);
			bg.dummyEtp = dummyEtp;
		}
		Foundation[] foundation = new Foundation[4];
		for(int i = 0; i < foundation.length; i++) {
			foundation[i] = new Foundation(20 + tpShift * i, 20, i + 1);
			bg.add(foundation[i]);
		}
		bg.foundationArray = foundation;
		Tableau[] tableauArray = new Tableau[7];
		for(int k = 0; k <= tableauArray.length-1; k++) {
			tableauArray[k] = new Tableau(TABLEAU_POSITION.x + TABLEAU_OFFSET * (k), TABLEAU_POSITION.y, k + 1, bg);
			bg.add(tableauArray[k]);
		}
		bg.tableauArray = tableauArray;
	}

	public static void addToBackgroundArray(Background background) {
		mementoBackgroundArray.add(background);
	}

	public static ArrayList<Background> getBackgroundArray() {
		return mementoBackgroundArray;
	}

	public static void addToScoreArray(Integer score) {
		mementoScoreArray.add(score);
	}

	public static ArrayList<Integer> getScoreArray() {
		return mementoScoreArray;
	}

	public static Background getMainBG() {
		return bg;
	}

	/* Easy mode functions */

	public static int getBestEasyNormalizedTime() {
		return bestEasyNormalizedTime;
	}
	
	public static void setEasyPlayerWon(boolean newPlayerWon) {
		easyPlayerWon = newPlayerWon;
	}

	public static void setEasyPlayerTime(String newPlayerTime) {
		easyPlayerTime = newPlayerTime;
	}
	
	public static void increaseEasyScore(int increase) {
		easyScore += increase;
	}

	public static void decreaseEasyScore(int decrease) {
		easyScore -= decrease;
	}

	public static ScorePanel getEasyScorePanel() {
		return easyScorePanel;
	}

	public static void setEasyHighScore(int newHighScore) {
		easyHighScore = newHighScore;
	}

	public static int getEasyHighScore() {
		return easyHighScore;
	}

	public static int getEasyScore() {
		return easyScore;
	}

	public static void setEasyScore(int resetScore) {
		easyScore = resetScore;
	}

	/* Hard Mode Functions */

	public static int getBestHardNormalizedTime() {
		return bestHardNormalizedTime;
	}
	
	
	public static void setHardPlayerWon(boolean newPlayerWon) {
		hardPlayerWon = newPlayerWon;
	}

	public static void setHardPlayerTime(String newPlayerTime) {
		hardPlayerTime = newPlayerTime;
	}
	
	public static ScorePanel getHardScorePanel() {
		return hardScorePanel;
	}

	public static void setHardHighScore(int newHighScore) {
		hardHighScore = newHighScore;
	}

	public static int getHardHighScore() {
		return hardHighScore;
	}

	public static int getHardScore() {
		return hardScore;
	}

	public static void setHardScore(int resetScore) {
		hardScore = resetScore;
	}

	public static void increaseHardScore(int increase) {
		hardScore += increase;
	}

	/* Vegas Mode Functions */
	
	public static int getBestVegasNormalizedTime() {
		return bestVegasNormalizedTime;
	}

	public static void setVegasPlayerWon(boolean newPlayerWon) {
		vegasPlayerWon = newPlayerWon;
	}

	public static void setVegasPlayerTime(String newPlayerTime) {
		vegasPlayerTime = newPlayerTime;
	}
	
	public static ScorePanel getVegasScorePanel() {
		return vegasScorePanel;
	}
	
	public static void increaseVegasScore(int increase) {
		vegasScore += increase;
	}

	public static void setVegasHighScore(int newHighScore) {
		vegasHighScore = newHighScore;
	}

	public static int getVegasHighScore() {
		return vegasHighScore;
	}

	public static int getVegasScore() {
		return vegasScore;
	}

	public static void setVegasScore(int resetScore) {
		vegasScore = resetScore;
	}

}

