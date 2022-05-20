
/**
 * Implments an AI player to place pieces randomly on the board at the start of every game
 * 
 * @author Ms Caitlin Woods
 * @version April 2022
 * 
 * Student Names and Numbers: Kim Thanh Huynh 23168144 / Jack Sun 23466264
 */

import java.util.*;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.NumericConversion;

import java.awt.Color;

public class AIPlayer {

    public Item[] itemBank; // an array representing the items that the AIPlayer has to choose from when
                            // placing items on the board.
    public int numberOfItems; // the number of items that the AIPlayer should select to hide on the board
    public Board board; // a board for the AIPlayer to hide items on.

    /**
     * Constructor for objects of class AIplayer.
     * Initialises the itemBank array with five default items that can be placed on
     * THIS HAS BEEN PROVIDED FOR YOU.
     * PLEASE DO NOT MODIFY OR CHANGE ANY CODE IN THIS CONSTRUCTOR.
     */
    public AIPlayer(Board board, int numberOfItems) {
        this.board = board;
        this.numberOfItems = numberOfItems;
        itemBank = new Item[] {
                new Item("phone", new int[][] { { 1, 0 }, { 1, 0 } }),
                new Item("keys", new int[][] { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }),
                new Item("shoe", new int[][] { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 } }),
                new Item("walking stick",
                        new int[][] { { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } }),
                new Item("book", new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 1, 1, 0 } }),
        };
    }

    /**
     * Returns a UNIQUE numberOfItems from the itemBank array. For example, if
     * numberOfItems is 2, then two items should be true.
     * These items should be selected at random and should be unique (no items can
     * be the same).
     * 
     * @param numberOfItems the number of items to be selected
     * @return an array of items (randomly selected from the itemBank and unique)
     */
    public Item[] selectItems(int numberOfItems) {
        // TODO 28
        Item[] selectedItems = new Item[numberOfItems];
        Random random = new Random();

        for (int i = 0; i < numberOfItems; i++) {
            boolean uniqueRandomItem = false;
            while (!uniqueRandomItem) {
                int randomIndex = random.nextInt(itemBank.length);
                Item randomItem = itemBank[randomIndex];
                if (!Arrays.stream(selectedItems).anyMatch(item -> item == randomItem)) {
                    selectedItems[i] = randomItem;
                    uniqueRandomItem = true;
                }
            }
        }
        return selectedItems;
    }

    /**
     * Randomly select a location for the item to be placed. No validation
     * needs to be performed on the selected location at this point but the location
     * should be
     * within the size of the board.
     * 
     * @return an int[] representing a location where (loc[0] = x location and
     *         loc[1] = y location)
     */
    public int[] selectLocation() {
        // TODO 29
        Random random = new Random();
        int[] loc = new int[] { random.nextInt(board.getBoardSize()),
                random.nextInt(board.getBoardSize()) };
        return loc;
    }

    /**
     * Randomly select an item orientation value in degrees (i.e. 0, 90, 180 or
     * 270). Return an
     * integer representing an item's orientation.
     * 
     * @return an integer (either 0, 90, 180 or 270).
     */
    public int selectOrientation() {
        // TODO 30
        Random random = new Random();
        int orientation = random.nextInt(4) * 90;
        return orientation;
    }

    /**
     * Test if a chosen location (int[]) and orientation (int) is valid for a given
     * item (i.e. the item
     * fits at that location on the board)
     * 
     * @param location: the location to be tested (hint: location is represented in
     *                  the same way
     *                  as in the selectLocation method)
     *                  (hint: you have to rotate the array according to "orr"
     *                  before testing the location)
     * @param item:     the item to find a place for.
     * @return true if the item fits at the location, false otherwise
     */
    public Boolean tryItemLocation(Item item, int[] location, int orr) {
        // TODO 31
        for (int i = 0; i < orr / 90; i++) {
            item.rotate90Degrees();
        }

        int[][] trimmedShape = trim(item.getShape());

        if (location[1] + trimmedShape.length > board.getBoardSize() ||
                location[0] + trimmedShape[0].length > board.getBoardSize()) {
            return false;
        }
        for (int y = location[1]; y < location[1] + trimmedShape.length; y++) {
            for (int x = location[0]; x < location[0] + trimmedShape[0].length; x++) {
                if (trimmedShape[y - location[1]][x - location[0]] == 1 &&
                        board.getPiece(x, y) != Piece.VACANT) {
                    return false;
                }
            }
        }
        return true;
        // return location[0] + item.getShape().length <= board.getBoardSize() &&
        // location[1] + item.getShape().length <= board.getBoardSize() &&
        // board.board[location[1]][location[0]] == Piece.VACANT;
    }

    public static int[][] trim(int[][] arr) {
        int cmin = arr[0].length;
        int rmin = arr.length;
        int cmax = -1;
        int rmax = -1;

        for (int r = 0; r < arr.length; r++)
            for (int c = 0; c < arr[0].length; c++)
                if (arr[r][c] != 0) {
                    if (cmin > c)
                        cmin = c;
                    if (cmax < c)
                        cmax = c;
                    if (rmin > r)
                        rmin = r;
                    if (rmax < r)
                        rmax = r;
                }

        int[][] result = new int[rmax - rmin + 1][];
        for (int r = rmin, i = 0; r <= rmax; r++, i++) {
            result[i] = Arrays.copyOfRange(arr[r], cmin, cmax + 1);
        }
        return result;
    }

    /**
     * Place an item at the location (represented by an int[]) on the board.
     * (hint: the first element of the location array is the x coordinate, and the
     * second element is the y coodinate).
     * The item is placed by setting each piece where the item's value is 1 to a
     * LOSTITEM state.
     * (e.g. for a phone where the shape is [[1, 0], [1, 0]], the output shoud be
     * [[LOSTITEM, VACANT][LOSTITEM, VACAMNT]]
     * 
     * @param item:     the item to be placed
     * @param location: the location on the board where first piece of the item
     *                  (i.e. the 0,0 coordinate of the item's shape) should be
     *                  placed.
     */
    public void setLostPieces(Item item, int[] location) {
        // TODO 32
        int[][] trimmedShape = trim(item.getShape());

        for (int y = 0; y < trimmedShape.length; y++) {
            for (int x = 0; x < trimmedShape[0].length; x++) {
                if (item.getShape()[y][x] == 1) {
                    board.board[y + location[1]][x + location[0]] = Piece.LOSTITEM;
                }
            }
        }
    }

    /**
     * Begin a game of 'Find My Things'. There are some steps to do here. They are:
     * 1) Select a numberOfItems from the itemBank
     * 2) Select a starting location for each item
     * 3) Keep trying to select a valid location for your items until you find a
     * valid space.
     * 4) Set the location of each of the selected items (hint: use a method from
     * the Item class)
     * 5) Set the lost pieces on the board for each of the selected items
     * (hint: you have already written helper methods to do most of these things)
     * 
     * @return an array of selectedItems
     */
    public Item[] startGame() {
        // TODO 33

        Item[] items = selectItems(numberOfItems);
        for (Item item : items) {
            boolean isSet = false;
            while (!isSet) {
                int[] loc = selectLocation();
                if (tryItemLocation(item, loc, selectOrientation())) {
                    item.setLocation(loc[0], loc[1]);
                    setLostPieces(item, loc);
                    isSet = true;
                }
            }
        }
        return items;
    }
}