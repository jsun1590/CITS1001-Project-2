
/**
 * Represents a "Thing" that will be hidden on the board by the AIPlayer.
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 * 
 * Student Names and Numbers: Kim Thanh Huynh 23168144 / Jack Sun 23466264
 */

import java.awt.Color;
import java.util.Arrays;

public class Item {

    private String type; // a string representing the type of the object, i.e. "phone" or "book".
    private int[][] shape; // a 2D array representing the shape of the object. hint: you can assume that
                           // this is a square array where 1 represents an item piece and
                           // 0 represent an empty space.
    private Integer locationX; // the x location of of the shape (i.e. shape[0][0] is at locationX).
    private Integer locationY; // the x location of of the shape (i.e. shape[0][0] is at locationY).
    private boolean isFound; // a boolean representing whether or not a Item has been found by the player.

    /**
     * Constructor for the Item class
     * Initializes the instance varables.
     * hint: initialise locationX and locationY to -1
     */
    public Item(String type, int[][] shape) {
        // TODO 1
        this.type = type;
        this.shape = shape;
        locationX = -1;
        locationY = -1;
        isFound = false;
    }

    /**
     * Returns the type of the item
     * 
     * @return the type of the item
     */
    public String getType() {
        // TODO 2
        return type;
    }

    /**
     * Returns the shape of the item
     * 
     * @return the shape of the item
     */
    public int[][] getShape() {
        // TODO 3
        return shape;
    }

    /**
     * Returns a copy of the shape
     * with the boundary 0s trimmed out
     * E.g.
     * {{1, 0, 0},
     * {0, 1, 0},
     * {0, 0, 0}}
     * would be trimmed to
     * {{1, 0},
     * {0, 1}}
     * 
     * @return the trimmed shape
     */
    public int[][] getTrimmedShape() {
        int xMin = getShape().length;
        int yMin = getShape().length;
        int xMax = -1;
        int yMax = -1;

        // Goes through each position in the item
        // and set the respective min and max bounds
        // for row and column full of 0s
        for (int y = 0; y < getShape().length; y++)
            for (int x = 0; x < getShape().length; x++)
                if (getShape()[y][x] != 0) {
                    if (xMin > x) {
                        xMin = x;
                    }
                    if (xMax < x) {
                        xMax = x;
                    }
                    if (yMin > y) {
                        yMin = y;
                    }
                    if (yMax < y) {
                        yMax = y;
                    }
                }

        // Copy over the shape into a new 2d array with
        // the previously defined bounds
        int[][] trimmedShape = new int[yMax - yMin + 1][];
        for (int y = yMin, i = 0; y <= yMax; y++, i++) {
            trimmedShape[i] = Arrays.copyOfRange(
                    getShape()[y], xMin, xMax + 1);
        }
        return trimmedShape;
    }

    /**
     * Sets the shape of an iem to a new shape
     * 
     * @return the shape
     */
    public void setShape(int[][] newShape) {
        // TODO 4
        shape = newShape;
    }

    /**
     * Sets the state of the item to "found"
     */
    public void setFound() {
        // TODO 5
        isFound = true;
    }

    /**
     * Returns whether or not the item has been found by the player
     * 
     * @return true if the item has been found, false otherwise
     */
    public boolean getIsFound() {
        // TODO 6
        return isFound;
    }

    /**
     * Returns the x location of the item
     * 
     * @return the x location of the item
     */
    public int getLocationX() {
        // TODO 7
        return locationX;
    }

    /**
     * Returns the y location of the item
     * 
     * @return the y location of the item
     */
    public int getLocationY() {
        // TODO 8
        return locationY;
    }

    /**
     * Sets the x and y locations for the item.
     * 
     * @param xloc the x location
     * @param yloc the y location
     */
    public void setLocation(int xloc, int yloc) {
        // TODO 9
        locationX = xloc;
        locationY = yloc;
    }

    /**
     * Rotates the item (i.e. its shape) 90 degrees clockwise and sets the shape of
     * the item as the
     * new rotated array.
     */
    public void rotate90Degrees() {
        // TODO 10
        int sideLen = shape.length;
        int[][] rotatedShape = new int[sideLen][sideLen];
        for (int y = 0; y < sideLen; y++) {
            for (int x = 0; x < sideLen; x++) {
                rotatedShape[x][y] = shape[y][x];
            }
        }
        shape = rotatedShape;
    }
}
