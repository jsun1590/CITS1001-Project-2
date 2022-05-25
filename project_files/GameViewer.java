
/**
 * Implements a view of a game of "find my things" by allowing users to click to take turns 
 * and displaying the output.
 * 
 * @author Ms Caitlin Woods
 * @version April 2022
 * 
 * Student Names and Numbers: Kim Thanh Huynh 23168144 / Jack Sun 23466264
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameViewer implements MouseListener, MouseMotionListener {

    // instance variables
    private int bkSize; // block size (i.e. size of a piece) - all other measurements to be derived from
                        // bkSize
    private int brdSize; // the size of the board
    private int scoreboardSize; // the size of the scoreboard
    private SimpleCanvas sc; // an object of SimpleCanvas to drae on
    private Board bd; // a board that maintains the current state of the game.
    private AIPlayer ai; // an AI player to place items at the start of every game.

    private Item[] selectedItems; // the items that have been selected by the AIPlayer for the current game.
    private int turnsRemaining; // the number of turns that remains for the player in the current game.
    private int[] closestLostItem; // the distace away of the closest lost item (given a click).
    private int numberOfFoundItems; // the number of items that have been found (i.e. completely uncovered) by the
                                    // player.
    private int numberOfHiddenItems; // the number of items that the AI player needs to hide.
    private int numberOfTurnsAtStartOfGame; // the number of turns that they player has when a new game starts.

    // EXTENSIIONS
    private JFrame frame; // the jframe containing the simple canvas
    private int timeLeft; // amount of time remaining in seconds before the player loses the game.
    private boolean popupShown; // check if the get name pop up for the leaderboard has been shown.
    private timerThread timer; // the timer thread.

    /**
     * Constructor for objects of class GameViewer.
     * Initialises the field variables and begins a game of "find my things".
     * THIS HAS BEEN PROVIDED FOR YOU.
     * PLEASE DO NOT MODIFY OR CHANGE ANY CODE IN THIS CONSTRUCTOR.
     */
    public GameViewer(int bkSize, int numberOfTurns, int numberOfItemsToHide) {
        numberOfHiddenItems = numberOfItemsToHide;
        numberOfTurnsAtStartOfGame = numberOfTurns;
        this.bkSize = bkSize;
        scoreboardSize = 4 * bkSize;
        brdSize = bkSize * 12;
        sc = new SimpleCanvas("Find my Things", brdSize,
                brdSize + scoreboardSize, Color.WHITE);
        sc.addMouseListener(this);
        sc.addMouseMotionListener(this);

        bd = new Board();
        ai = new AIPlayer(bd, numberOfHiddenItems);
        turnsRemaining = numberOfTurnsAtStartOfGame;
        selectedItems = ai.startGame();
        closestLostItem = new int[2];
        sc.drawBoard(brdSize, bkSize, scoreboardSize, turnsRemaining,
                closestLostItem, numberOfFoundItems, selectedItems, bd);
        timeLeft = 10;
        popupShown = false;
        frame = (JFrame) JFrame.getFrames()[0];
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupGame();
    }

    /**
     * An default constructor for objects of class GameViewer.
     * Sets to block size (bkSize) as a default of 40.
     * Sets the numberOfTurns to 20.
     * Sets the numberOfItemsToHide to 3.
     */
    public GameViewer() {
        // TODO 34
        this(40, 20, 3);
    }

    public class timerThread extends Thread {
        public void run() {
            boolean running = true;
            while (running) {
                drawTimer();
                drawGameOutcome();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    running = false;
                }
                timeLeft--;
                if (timeLeft < 1) {
                    running = false;
                }
            }
            drawTimer();
            drawGameOutcome();
        }
    };

    public void setupGame() {
        timer = new timerThread();
        timer.start();
    }

    public void drawLeaderboard(Integer currentScore) throws IOException {
        ArrayList<String[]> leaderboard = new ArrayList<String[]>();
        File lbFile = new File("leaderboard.txt");
        if (!lbFile.exists()) {
            lbFile.createNewFile();
        }
        FileReader lbFileReader = new FileReader(lbFile);
        try (BufferedReader lbBufferedReader = new BufferedReader(lbFileReader)) {
            String line;
            while ((line = lbBufferedReader.readLine()) != null) {
                String[] player = line.split("-");
                leaderboard.add(new String[] { player[0], player[1] });
            }
        }

        if (!popupShown && currentScore != 0) {
            popupShown = true;
            if (leaderboard.size() < 5) {
                String currentName = JOptionPane.showInputDialog(
                        frame, "Please enter your name",
                        "Congrats, you've made the leaderboard!",
                        JOptionPane.PLAIN_MESSAGE);

                if (currentName == null) {
                    currentName = "Guest";
                }

                leaderboard.add(new String[] { currentName, currentScore.toString() });
            } else {
                for (int playerIndex = 0; playerIndex < leaderboard.size(); playerIndex++) {
                    if (currentScore >= Integer.parseInt(leaderboard.get(playerIndex)[1])) {

                        String currentName = JOptionPane.showInputDialog(
                                frame, "Please enter your name",
                                "Congrats, you've made the leaderboard!",
                                JOptionPane.PLAIN_MESSAGE);
                        if (currentName == null) {
                            currentName = "Guest";
                        }
                        leaderboard.add(playerIndex, new String[] { currentName, currentScore.toString() });
                        break;
                    }
                }
            }
        }

        if (leaderboard.size() > 5) {
            leaderboard.remove(5);
        }

        FileWriter lbFileWriter = new FileWriter(lbFile);
        BufferedWriter lbBufferedWriter = new BufferedWriter(lbFileWriter);

        for (String[] user : leaderboard) {
            lbBufferedWriter.write(user[0] + "-" + user[1] + "\n");
        }

        lbBufferedWriter.close();

        int center = brdSize / 2;

        sc.drawRectangle(center - 100, center - 100,
                center + 100, center + 100, Color.lightGray);

        sc.drawString("Leaderboard", center - 35, center - 60, Color.black);
        sc.drawString("Rank", center - 80, center - 30, Color.black);
        sc.drawString("Name", center - 20, center - 30, Color.black);
        sc.drawString("Score", center + 40, center - 30, Color.black);
        for (int i = 0; i < 5; i++) {
            if (i + 1 > leaderboard.size()) {
                break;
            }
            sc.drawString(i + 1, center - 73, center + i * 20, Color.black);
            sc.drawString(leaderboard.get(i)[0], center - 20, center + i * 20, Color.black);
            sc.drawString(leaderboard.get(i)[1], center + 45, center + i * 20, Color.black);

        }
    }

    public int calculateScore() {
        return timeLeft + turnsRemaining;
    }

    public void drawTimer() {
        int x1 = brdSize - 100;
        int y1 = brdSize + scoreboardSize - 50;

        sc.drawRectangle(x1, y1, x1 + 100, y1 + 50, Color.gray);
        sc.drawString(String.format("Timer: %d", timeLeft), x1 + 20, y1 + 30, Color.black);
    }

    /**
     * Restarts a game of "Find My Things". There are a few steps to do here. They
     * are:
     * 1) Re-initialise the board field variable
     * 2) Re-initialise the aiPlayer field variable (set the numberOfItems parameter
     * to 3)
     * 3) Re-initialise the turnsRemaining field variable (set the default to 20)
     * 4) Re-nitialise the selectedItems field variable (hint: use ai.startGame)
     * 5) Re-nitialise the closestLostItem field variable to an array of size 2
     * (default values 0)
     * 6) Re-draw the board (use the SimpleCanvas class);
     */
    private void restartGame() {
        // TODO 35
        bd = new Board();
        ai = new AIPlayer(bd, 3);
        turnsRemaining = 20;
        numberOfFoundItems = 0;
        selectedItems = ai.startGame();
        closestLostItem = new int[] { 0, 0 };
        sc.drawBoard(brdSize, bkSize, scoreboardSize, turnsRemaining,
                closestLostItem, numberOfFoundItems, selectedItems, bd);

        timeLeft = 40;
        timer = new timerThread();
        timer.start();

        popupShown = false;
    }

    /**
     * Get the piece to click (number from 0 to size of board) from a mouse event
     * (this is used in the mousePressed method)
     * hint: you will need to know the size of a piece to calculate the coordinates.
     * 
     * @param xClicked the x cvalue of a mouse event.
     * @param yClicked the y value of a mouse event.
     * @return the x and y coordinates of the piece that has been clicked.
     */
    public int[] getNearestPiece(int xClicked, int yClicked) {
        // TODO 36
        int x = xClicked / bkSize;
        int y = yClicked / bkSize;
        return new int[] { x, y };
    }

    /**
     * Returns the number of turns that the player has remaining
     * 
     * @return the number of turns remaining
     */
    public int getTurnsRemaining() {
        // TODO 37
        return turnsRemaining;
    }

    /**
     * If a click is not successful, reduce the number of turns that the player has
     * remaining by 1
     * 
     * @param a boolean representing if the click was successful (i.e. a player
     *          found an item)
     */
    public void reduceTurns(boolean clickSuccessful) {
        // TODO 38
        if (!clickSuccessful) {
            turnsRemaining--;
        }
    }

    /**
     * Draws the game outcome on the canvas (hint: the simple canvas class provides
     * two helper
     * methods to do this)
     * The location of the game outcome text should be set to to [0, 0];
     */
    public void drawGameOutcome() {
        // TODO 39
        if (turnsRemaining == 0 || timeLeft < 1) {
            timer.interrupt();
            sc.drawGameLost(0, 0);
            try {
                drawLeaderboard(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (numberOfFoundItems == numberOfHiddenItems) {
            timer.interrupt();
            sc.drawGameWon(0, 0);
            try {
                drawLeaderboard(calculateScore());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ;
    }

    /**
     * Refreshes the values shown on the board and scoreboard. There are some steps
     * to do here.
     * Actions are as follows:
     * 1) Update state of selected items if they have been found.
     * 2) Update the closest lost item values.
     * 3) Redraw the board.
     * 4) If there are no turns remaining, draw the game outcome.
     * 
     * @param xLastClicked
     * @param yLastClicked
     */
    public void refreshBoard(int xLastClicked, int yLastClicked) {
        // TODO 40
        numberOfFoundItems = 0;
        ArrayList<Item> foundItems = bd.getFoundItemsList(selectedItems);
        for (Item item : foundItems) {
            item.setFound();
            numberOfFoundItems++;
        }

        closestLostItem = bd.getClosestItem(xLastClicked, yLastClicked);
        sc.drawBoard(brdSize, bkSize, scoreboardSize, turnsRemaining,
                closestLostItem, numberOfFoundItems, selectedItems, bd);

        drawGameOutcome();
    }

    /**
     * Called when the player takes a turn (happens within the mousePressed method).
     * The rules are as follows
     * 1) If the player has no turns remaining, do nothing.
     * 2) If the space that is clicked is already found or searched, do nothing.
     * 3) If the space that is clicked is vacant, search it.
     * 4) If the search was unsuccessful (i.e. the player found nothing), reduce the
     * number of turns remaining by 1.
     * 5) refresh the board (to re-display the board);
     * 
     * @return the number of turns remaining.
     */
    public int takeTurn(int x, int y) {
        // TODO 41
        if (turnsRemaining != 0 && (bd.getPiece(x, y) == Piece.VACANT ||
                bd.getPiece(x, y) == Piece.LOSTITEM)) {
            reduceTurns(!bd.searchSpace(x, y));
        }
        refreshBoard(x, y);
        return turnsRemaining;

    }

    /**
     * Processes a mouse click event. The following actions should be taken.
     * 1) If the "restart game" button has been clicked, restart the game.
     * 2) Otherwise, take a turn.
     * (hint: you must figure out the position (in pixels) of the "restart game"
     * button to do this).
     */
    public void mousePressed(MouseEvent e) {
        // TODO 42
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getX() < 150 &&
                e.getY() > brdSize + scoreboardSize - 50 &&
                e.getY() < brdSize + scoreboardSize) {
            restartGame();
        } else if (numberOfFoundItems == numberOfHiddenItems || timeLeft < 1) {
            return;
        } else if (e.getX() < brdSize &&
                e.getY() < brdSize) {
            int[] nearestPiece = getNearestPiece(e.getX(), e.getY());
            takeTurn(nearestPiece[0], nearestPiece[1]);
        }
        drawTimer();
        // drawGameOutcome();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        sc.drawBoard(brdSize, bkSize, scoreboardSize, turnsRemaining,
                closestLostItem, numberOfFoundItems, selectedItems, bd);
        drawTimer();
        drawGameOutcome();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if (e.getX() < brdSize &&
                e.getY() < brdSize) {
            int[] nearestPiece = getNearestPiece(e.getX(), e.getY());
            int x1 = nearestPiece[0];
            int y1 = nearestPiece[1];
            sc.drawBoard(brdSize, bkSize, scoreboardSize, turnsRemaining,
                    closestLostItem, numberOfFoundItems, selectedItems, bd);
            drawTimer();
            drawGameOutcome();
            if (bd.getPiece(x1, y1) != Piece.VACANT &&
                    bd.getPiece(x1, y1) != Piece.LOSTITEM ||
                    numberOfFoundItems == numberOfHiddenItems ||
                    turnsRemaining == 0 || timeLeft < 1) {
                return;
            }
            sc.drawRectangle(x1 * bkSize, y1 * bkSize,
                    x1 * bkSize + bkSize, y1 * bkSize + bkSize, Color.BLACK);
        } else {
            sc.drawBoard(brdSize, bkSize, scoreboardSize, turnsRemaining,
                    closestLostItem, numberOfFoundItems, selectedItems, bd);
            drawTimer();
            drawGameOutcome();
        }
    }

    /**
     * This is a helper method for testing purposes only.
     * DO NOT USE THIS METHOD IN YOUR CODE
     * DO NOT MODIFY THIS METHOD.
     */
    public void setBoardStateTestingOnly(Board board) {
        bd = board;
    }

    /**
     * This is a helper method for testing purposes only.
     * DO NOT USE THIS METHOD IN YOUR CODE
     * DO NOT MODIFY THIS METHOD.
     */
    public Item[] getSelectedItems() {
        return selectedItems;
    }
}
