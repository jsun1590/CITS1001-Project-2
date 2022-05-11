
/**
 * A class to maintain update the current state of the board.
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 * 
 * Student Names and Numbers: Kim Thanh Huynh 23168144 / Jack Sun 23466264
 */

import java.util.ArrayList;

public class Board {

    Piece[][] board; // A 2D array of pieces, representing the state of a board.

    /**
     * Constructor for the Board class
     * Initializes the board with 12 rows and columns
     * and sets all locations to VACANT
     */
    public Board() {
        // TODO 11
        this.board = new Piece[12][12];

        for (int y = 0; y < 12; y++) {
            for (int x = 0; x < 12; x++) {
                this.board[y][x] = Piece.VACANT;
            }
        }
    }

    /**
     * Constructor for the Board class
     * Initializes the board with the given number of rows and columns
     * and sets all locations to defaultValue
     */
    public Board(int rows, int cols, Piece defaultValue) {
        // TODO 12
        this.board = new Piece[cols][rows];

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                this.board[y][x] = defaultValue;
            }
        }
    }

    /**
     * Returns the size of the board as an integer (i.e. 12x12 board should return
     * 12)
     * hint: You can assume the grid is square (i.e. rows == cols)
     * 
     * @return the size of the board
     */
    public int getBoardSize() {
        // TODO 13
        return this.board.length;
    }

    /**
     * Returns true if x and y are coordinates in the board, false otherwise
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if x and y are coordinates in the board, false otherwise
     */
    public boolean isPiece(int x, int y) {
        // TODO 14
        if (0 <= x && x < this.getBoardSize() &&
                0 <= y && y < this.getBoardSize()) {
            return true;
        }

        return false;
    }

    /**
     * Returns the value of the piece at the given coordinates
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the value of the piece at the given coordinates
     * @throws IllegalArgumentException if x and y are not valid coordinates
     */
    public Piece getPiece(int x, int y) {
        // TODO 15
        if (this.isPiece(x, y)) {
            return this.board[y][x];
        }

        throw new IllegalArgumentException("Coordinates are not valid.");
    }

    /**
     * Check if a given piece is part of a lost item
     * Throw an exeption if the piece is not in the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if a given piece is part of a lost item, false otherwise
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public boolean isLostItem(int x, int y) {
        // TODO 16
        if (this.getPiece(x, y) == Piece.LOSTITEM) {
            return true;
        }
        return false;
    }

    /**
     * Sets the given piece to be part of a lost item (i.e. an item is hidden at
     * this location)
     * Throw an exception if the piece is not on the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public void setLostItem(int x, int y) {
        // TODO 17
        if (this.isPiece(x, y)) {
            this.board[y][x] = Piece.LOSTITEM;
        }
    }

    /**
     * Check if a given piece is vacant
     * Throw an exeption if the piece is not in the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if a given piece is vacant, false otherwise
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public boolean isVacant(int x, int y) {
        // TODO 18
        if (this.getPiece(x, y) == Piece.VACANT) {
            return true;
        }
        return false;
    }

    /**
     * Sets the given piece to be vacant (i.e. no item is on this location)
     * Throw an exception if the piece is not on the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public void setVacant(int x, int y) {
        // TODO 19
        if (this.isPiece(x, y)) {
            this.board[y][x] = Piece.VACANT;
        }
    }

    /**
     * Check if a given piece is part of an item that has been found by the player
     * Throw an exeption if the piece is not in the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if a given piece is part of a found item, false otherwise
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public boolean isFoundItem(int x, int y) {
        // TODO 20
        if (this.getPiece(x, y) == Piece.FOUNDITEM) {
            return true;
        }
        return false;
    }

    /**
     * Sets the given piece to be part of a found item (i.e. an item is hidden at
     * this location and this piece has been found by the player)
     * Throw an exception if the piece is not on the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public void setFoundItem(int x, int y) {
        // TODO 21
        if (this.isPiece(x, y)) {
            this.board[y][x] = Piece.FOUNDITEM;
        }
    }

    /**
     * Check if a given piece has been searched by the player (but does not contain
     * any part of a hidden item)
     * Throw an exeption if the piece is not in the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if a given piece is part of a found item, false otherwise
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public boolean isSearched(int x, int y) {
        // TODO 22
        if (this.getPiece(x, y) == Piece.SEARCHED) {
            return true;
        }
        return false;
    }

    /**
     * Sets the given piece to "searched" (i.e. no item has been hidden at this
     * location and the player has searched this space)
     * Throw an exception if the piece is not on the board
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException if the piece is not in the board
     */
    public void setSearched(int x, int y) {
        // TODO 23
        if (this.isPiece(x, y)) {
            this.board[y][x] = Piece.SEARCHED;
        }
    }

    /**
     * Sets the given piece to a new state, after it has been searched according to
     * the following rules:
     * 1) If the space is currently VACANT, set it to SEARCHED
     * 2) If the space is currently LOST, set it to FOUND
     * 3) Return true if the search was successful (or unnecessary), false otherwide
     * 
     * @param x
     * @param y
     * @return true if the search was successful (or unnecessary), false otherwise
     */
    public boolean searchSpace(int x, int y) {
        // TODO 24
        System.out.println(this.getPiece(x, y));
        if (this.getPiece(x, y) == Piece.VACANT) {
            this.setSearched(x, y);
            return true;
        } else if (this.getPiece(x, y) == Piece.LOSTITEM) {
            this.setFoundItem(x, y);
        }

        return false;
    }

    /**
     * Find the closest lost item piece to the piece that has just been touched (at
     * [touchedX, touchedY]).
     * Return the "number of spaces away" that the closest lost item is.
     * An item is considered "the closest" if the "number of spaces away in the x
     * direction"
     * plus the "number of spaces away in the y direction" is minimal.
     * 
     * @param touchedX the x coordinate of the touched point
     * @param touchedY the y coordinate of the touched point
     * @return an array of size 2 containing the number of spaces away
     *         in the X direction and the number of spaces away in the Y direction
     *         where the array is in the form [x, y]
     */
    public int[] getClosestItem(int touchedX, int touchedY) {
        // TODO 25
        int closestDist = Integer.MAX_VALUE;
        int[] closestLost = new int[2];
        for (int y = 0; y < this.getBoardSize(); y++) {
            for (int x = 0; x < this.getBoardSize(); x++) {
                int distX = Math.abs(touchedX - x);
                int distY = Math.abs(touchedY - y);
                if (this.isLostItem(x, y) && (distX + distY) < closestDist) {
                    closestLost[0] = distX;
                    closestLost[1] = distY;
                    closestDist = distX + distY;
                }
            }
        }
        return closestLost;
    }

    /**
     * Checks if a whole item has been found on the board
     * An item is considered "found" if there are a cluster of spaces on the board
     * that
     * have a value of FOUNDITEM, and that cluster is in the shape of the item.
     * 
     * @param item the item to check.
     * @return true if the item has been found, false otherwise.
     */
    public boolean checkForFoundItem(Item item) {
        // TODO 26;
        int shapeSideLen = item.getShape().length;
        for (int y = 0; y <= this.getBoardSize() - shapeSideLen; y++) {
            for (int x = 0; x <= this.getBoardSize() - shapeSideLen; x++) {
                int correct = 0;
                for (int j = y; j < shapeSideLen; j++) {
                    for (int i = x; i < shapeSideLen; i++) {
                        if (this.board[j][i] == Piece.FOUNDITEM && item.getShape()[j][i] == 1 ||
                                this.board[j][i] != Piece.FOUNDITEM && item.getShape()[j][i] == 0) {
                            correct += 1;
                        }
                    }
                }
                if (correct == shapeSideLen * shapeSideLen) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Goes through a list of items and checks which of the items have been found.
     * (hint: use a helper method that you have already written).
     * 
     * @return an array list of items that have been found.
     */
    public ArrayList<Item> getFoundItemsList(Item[] items) {
        // TODO 27
        ArrayList<Item> foundItems = new ArrayList<Item>();
        for (Item item : items) {
            if (this.checkForFoundItem(item)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

}
