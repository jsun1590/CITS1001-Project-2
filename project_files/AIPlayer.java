
/**
 * Implments an AI player to place pieces randomly on the board at the start of every game
 * 
 * @author Ms Caitlin Woods
 * @version April 2022
 * 
 * Student Names and Numbers: { ENTER YOUR STUDENT NAME AND NUMBER HERE }
 */

import java.util.*;
import java.awt.Color;

public class AIPlayer {

    public Item[] itemBank; // an array representing the items that the AIPlayer has to choose from when placing items on the board.
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
                new Item("walking stick", new int[][] { { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } }),
                new Item("book", new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 1, 1, 0 } }),
        };
    }
    
    /**
     * Returns a UNIQUE numberOfItems from the itemBank array. For example, if
     * numberOfItems is 2, then two items should be true.
     * These items should be selected at random and should be unique (no items can
     * be the same).
     * @param numberOfItems the number of items to be selected
     * @return an array of items (randomly selected from the itemBank and unique)
     */
    public Item[] selectItems(int numberOfItems) {
        // TODO 28
        return null;
    }

    /**
     * Randomly select a location for the item to be placed. No validation
     * needs to be performed on the selected location at this point but the location should be
     * within the size of the board.
     * 
     * @return an int[] representing a location where (loc[0] = x location and
     *         loc[1] = y location)
     */
    public int[] selectLocation() {
        // TODO 29
        return null;
    }

    /**
     * Randomly select an item orientation value in degrees (i.e. 0, 90, 180 or 270). Return an
     * integer representing an item's orientation.
     * @return an integer (either 0, 90, 180 or 270).
     */
    public int selectOrientation() {
        // TODO 30
        return 0;
    }

    /**
     * Test if a chosen location (int[]) and orientation (int) is valid for a given item (i.e. the item
     * fits at that location on the board)
     * 
     * @param location: the location to be tested (hint: location is represented in the same way 
     * as in the selectLocation method)
     * (hint: you have to rotate the array according to "orr" before testing the location)
     * @param item:  the item to find a place for.
     * @return true if the item fits at the location, false otherwise
     */
    public Boolean tryItemLocation(Item item, int[] location, int orr) {
        // TODO 31
        return null;
    }

    /**
     * Place an item at the location (represented by an int[]) on the board.
     * (hint: the first element of the location array is the x coordinate, and the second element is the y coodinate).
     * The item is placed by setting each piece where the item's value is 1 to a LOSTITEM state.
     * (e.g. for a phone where the shape is [[1, 0], [1, 0]], the output shoud be [[LOSTITEM, VACANT][LOSTITEM, VACAMNT]]
     * @param item:    the item to be placed
     * @param location: the location on the board where first piece of the item 
     * (i.e. the 0,0 coordinate of the item's shape) should be placed.
     */
    public void setLostPieces(Item item, int[] location) {
        // TODO 32
    }

    /**
     * Begin a game of 'Find My Things'. There are some steps to do here. They are:
     * 1) Select a numberOfItems from the itemBank
     * 2) Select a starting location for each item
     * 3) Keep trying to select a valid location for your items until you find a
     * valid space.
     * 4) Set the location of each of the selected items (hint: use a method from the Item class)
     * 5) Set the lost pieces on the board for each of the selected items
     * (hint: you have already written helper methods to do most of these things)
     * @return an array of selectedItems
     */
    public Item[] startGame() {
        // TODO 33
        return null;
    }

}