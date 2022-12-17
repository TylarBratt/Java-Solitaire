package game;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	
	static protected boolean playerWon = false;
	static protected int bestNormalizedTime = 100000;
	static protected String playerTime = "";
	static protected int score;
	static protected int highScore;

	protected CardMoveListener mainCardMoveListener;
	protected BestTimePanel bestTimePanel;
	protected static ScorePanel scorePanel;
	private static UndoButton undoButton;

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
		
		
		//System.out.println("The Key Pressed was: " + a);
		if(a == start) {
			//determine if you are coming from start screen or end of a game
			if (   playerWon == false  ) {
				//clear undo arrays
				mementoBackgroundArray = new ArrayList<Background>();
				mementoScoreArray = new ArrayList<Integer>();
				score = 0;
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
				Integer initialScore = new Integer(score);
				addToScoreArray(initialScore);
				
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				undoButton = new UndoButton("Undo last move", 300, 500, 125, 50);
				scorePanel = new ScorePanel("<html><div style='text-align: center;'>Score: " + score + "</div></html>", 0, 550, 125, 50);
				scorePanel.setHorizontalAlignment(SwingConstants.CENTER);
				bg.add(scorePanel);
				undoButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Thread() {
							public void run(){
								System.out.println("undo button pressed");

								int mementoBackgroundArraySize = mementoBackgroundArray.size();
								System.out.println("memento array size: " + mementoBackgroundArraySize);
								System.out.println("score array size: " + mementoBackgroundArraySize);
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
									
									score = new Integer(mementoScoreArray.get(index));
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

									scorePanel.setScoreText(score);

									bg.add(undoButton);
									bg.add(scorePanel);
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

									if ( mementoBackgroundArraySize > 1 ) {
										mementoBackgroundArray.remove(mementoBackgroundArraySize-1);
									}
									bg.add(bg.getTpPile());
									bg.add(bg.getStockPile());

									for (int i = 0; i<bg.getTableauArray().length; i++) {
										bg.add(bg.getTableauArray()[i]);
									}

									for (int i = 0; i<bg.getFoundationArray().length; i++) {
										bg.add(bg.getFoundationArray()[i]);
									}

									bg.add(undoButton);
									bg.add(bg.gameTimer);
									bg.addMouseListener(game);
									bg.addMouseMotionListener(game);

									bg.add(bestTimePanel);
									
									add(bg);

									bg.revalidate();
									bg.repaint();
								}
							}
				
						}.start();
					}
				});
			
				bestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + playerTime +"</center></html>", 0, 550 , 125, 50, 0);
				bg.add(bestTimePanel);
				bg.add(undoButton);
				add(bg);
				validate();
			}
		} else if (a == j) {
			//sc = new Score();
		} else if (a == hard) {
			if (  bg == null || playerWon == false ) {
				easyHard = 1;
				remove(st);
				bg = new Background();
				initializePiles(bg);
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				remove(Main.st);
				add(bg);
				
				revalidate();
			}
			else {
				easyHard = 1;
				remove(bg);
				bg = new Background();
				initializePiles(bg);
				CardMoveListener game = new CardMoveListener();
				bg.addMouseListener(game);
				bg.addMouseMotionListener(game);
				
				remove(Main.st);
				add(bg);
				bestTimePanel = new BestTimePanel("<html><center>Best Time<br />" + playerTime +"</center></html>", 0, 550 , 125, 50, 0);
				bg.add(bestTimePanel);
				revalidate();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}	
	}
	public static int getBestNormalizedTime() {
		return bestNormalizedTime;
	}

	public static void setPlayerWon(boolean newPlayerWon) {
		playerWon = newPlayerWon;
	}

	public static void setPlayerTime(String newPlayerTime) {
		playerTime = newPlayerTime;
	}

	private void initializePiles(Background bg) {
		StockPile sp = new StockPile(650, 15);
		bg.add(sp);
		bg.sp = sp;
		TalonPile tp = new TalonPile(650 - tpShift, 15);
		bg.add(tp);
		bg.tp = tp;
		Foundation[] foundation = new Foundation[4];
		for(int i = 0; i < foundation.length; i++) {
			foundation[i] = new Foundation(20 + tpShift * i, 20, i + 1);
			bg.add(foundation[i]);
		}
		bg.foundationArray = foundation;
		Tableau[] tableauArray = new Tableau[7];
		for(int k = 1; k <= tableauArray.length; k++) {
			tableauArray[k - 1] = new Tableau(TABLEAU_POSITION.x + TABLEAU_OFFSET * (k - 1), TABLEAU_POSITION.y, k + 1, bg);
			bg.add(tableauArray[k - 1]);
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

	public static void increaseScore(int increase) {
		score += increase;
	}

	public static void decreaseScore(int decrease) {
		score -= decrease;
	}

	public static ScorePanel getScorePanel() {
		return scorePanel;
	}

	public static void setHighScore(int newHighScore) {
		highScore = newHighScore;
	}

	public static int getHighScore() {
		return highScore;
	}
}

