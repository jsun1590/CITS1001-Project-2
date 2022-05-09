
/**
 * This is a stripped-down version of the Canvas class from the 
 * BlueJ team, retaining only the most fundamental features.
 * 
 * @author BlueJ team with modifications by Gordon Royle and Lyndon While
 * @version April 2019
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCanvas {

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Image canvasImage;
    private boolean autoRepaint;

    /**
     * Creates and displays a SimpleCanvas of the specified size and background
     */
    public SimpleCanvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        frame.pack();
        Dimension size = canvas.getSize();
        canvasImage = canvas.createImage(size.width, size.height);
        graphic = (Graphics2D) canvasImage.getGraphics();
        graphic.setColor(bgColour);
        graphic.fillRect(0, 0, size.width, size.height);
        graphic.setColor(Color.black);
        frame.setVisible(true);
        this.autoRepaint = true;
    }

    /**
     * Creates and displays a SimpleCanvas of size 400x400 with the
     * default title "SimpleCanvas" and with white background.
     */
    public SimpleCanvas() {
        this("SimpleCanvas", 400, 400, Color.white);
    }

    /**
     * Draws a line on this SimpleCanvas from x1,y1 to x2,y2 with colour c.
     */
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        setForegroundColour(c);
        graphic.drawLine(x1, y1, x2, y2);
        if (autoRepaint)
            canvas.repaint();
    }

    /**
     * Draws the boad lines and the pieces as per their locations.
     * Drawing of lines is provided, students to implement drawing
     * of pieces and number of goats.
     */
    public void drawBoard(int brdSize, int bkSize, int scoreboardSize, int turnsRemaining, int[] closestLostItem,
            int numberOfFoundItems, Item[] selectedItems, Board bd) {
        drawRectangle(0, 0, brdSize, brdSize + scoreboardSize, Color.WHITE); // wipe the scoreboard
        // sc.drawRectangle(0, 0, brdSize, brdSize, Color.WHITE); // wipe the canvas

        // draw pieces
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                if (bd.isVacant(x, y) || bd.isLostItem(x, y)) {
                    drawRectangle(x * bkSize, y * bkSize, (x * bkSize) + bkSize, (y * bkSize) + bkSize, Color.BLUE);
                } else if (bd.isSearched(x, y)) {
                    drawRectangle(x * bkSize, y * bkSize, (x * bkSize) + bkSize, (y * bkSize) + bkSize, Color.RED);
                } else {
                    drawRectangle(x * bkSize, y * bkSize, (x * bkSize) + bkSize, (y * bkSize) + bkSize, Color.GREEN);
                }
            }
        }

        // Draw the lines
        for (int i = 1; i < 12; i++) {
            // draw vertical lines
            drawLine(i * bkSize, 0, i * bkSize, brdSize, Color.WHITE);
            // draw horizontal lines
            drawLine(0, i * bkSize, brdSize, i * bkSize, Color.WHITE);
        }

        // Draw Scoreboard
        drawString("Turns Remaining", bkSize, brdSize + bkSize, Color.BLACK);
        drawString(turnsRemaining, bkSize, brdSize + (bkSize) + 20, Color.GRAY);

        drawString("Closest Lost Item", bkSize, brdSize + 2 * bkSize, Color.BLACK);
        String closestString = "x:" + closestLostItem[0] + ", y:" + closestLostItem[1];
        drawString(closestString, bkSize, brdSize + 2 * bkSize + 20, Color.GRAY);

        drawString("Things To Find", 8 * bkSize, brdSize + bkSize, Color.BLACK);
        for (int i = 0; i < selectedItems.length; i++) {
            Item item = selectedItems[i];
            int pixelSize = 3;
            drawString(item.getType(), (8 * bkSize) + 5 * pixelSize, brdSize + (bkSize) + ((i + 1) * 20),
                    Color.GRAY);
            Color itemColor = item.getIsFound() ? Color.GREEN : Color.RED;
            drawItem(item.getType(), pixelSize, 8 * bkSize, brdSize + (bkSize) + ((i + 1) * 20) - (2 * pixelSize),
                    itemColor);
        }
        drawRestartButton(0, brdSize + scoreboardSize - 50);

    }

    /**
     * Draws a point on this SimpleCanvas at x,y with colour c.
     */
    public void drawPoint(int x, int y, Color c) {
        drawLine(x, y, x, y, c);
    }

    /**
     * Draws a rectangle on this SimpleCanvas with sides parallel to the axes
     * between x1,y1 and x2,y2 with colour c.
     */
    public void drawRectangle(int x1, int y1, int x2, int y2, Color c) {
        setForegroundColour(c);
        graphic.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        if (autoRepaint)
            canvas.repaint();
    }

    public void drawRestartButton(int x1, int y1) {
        drawRectangle(x1, y1, x1 + 150, y1 + 50, Color.gray);
        drawString("Restart Game", x1 + 20, y1 + 30, Color.black);
        if (autoRepaint)
            canvas.repaint();
    }

    public void drawGameWon(int x1, int y1) {
        drawRectangle(x1, y1, x1 + 150, y1 + 50, Color.black);
        drawString("You Won!", x1 + 20, y1 + 30, Color.white);
        if (autoRepaint)
            canvas.repaint();
    }

    public void drawGameLost(int x1, int y1) {
        drawRectangle(x1, y1, x1 + 150, y1 + 50, Color.black);
        drawString("You Lost!", x1 + 20, y1 + 30, Color.white);
        if (autoRepaint)
            canvas.repaint();
    }

    public void drawItem(String itemName, int pixelSize, int x1, int y1, Color c) {
        int[][] shape;
        switch (itemName) {            
            case "keys":
                shape = new int[][] { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };
                break;
            case "shoe":
                shape = new int[][] { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 } };
                break;
            case "walking stick":
                shape = new int[][] { { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } };
                break;
            case "book":
                shape = new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 1, 1, 0 } };
                break;
            case "phone":
                shape = new int[][]  { { 1, 0 }, { 1, 0 } };
                break;
            default:
                shape = new int[][] {};
                break;
        }
        // draw a rectangle with the shape of the item
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    drawRectangle(x1 + j * pixelSize, y1 + i * pixelSize, x1 + (j + 1) * pixelSize,
                            y1 + (i + 1) * pixelSize, c);
                }
            }
        }
    }

    /**
     * Draws a disc on this SimpleCanvas centred at x,y with radius r with colour c.
     */
    public void drawDisc(int x, int y, int r, Color c) {
        for (int i = x - r; i <= x + r; i++)
            for (int j = y - r; j <= y + r; j++)
                if (Math.pow(i - x, 2) + Math.pow(j - y, 2) <= Math.pow(r, 2))
                    drawPoint(i, j, c);
        if (autoRepaint)
            canvas.repaint();
    }

    /**
     * Draws a circle on this SimpleCanvas centred at x,y with radius r with colour
     * c.
     */
    public void drawCircle(int x, int y, int r, Color c) {
        for (int i = x - r; i <= x + r; i++)
            for (int j = y - r; j <= y + r; j++)
                if (Math.pow(i - x, 2) + Math.pow(j - y, 2) <= Math.pow(r, 2) &&
                        Math.pow(i - x, 2) + Math.pow(j - y, 2) >= Math.pow(r - 5, 2))
                    drawPoint(i, j, c);
        if (autoRepaint)
            canvas.repaint();
    }

    /**
     * Writes the String text on this SimpleCanvas at x,y with colour c.
     */
    public void drawString(String text, int x, int y, Color c) {
        setForegroundColour(c);
        graphic.drawString(text, x, y);
        if (autoRepaint)
            canvas.repaint();
    }

    /**
     * Writes the number n on this SimpleCanvas at x,y with colour c.
     */
    public void drawString(int n, int x, int y, Color c) {
        drawString(n + "", x, y, c);
    }

    /**
     * Changes the colour for subsequent drawing on this SimpleCanvas.
     */
    public void setForegroundColour(Color newColour) {
        graphic.setColor(newColour);
    }

    /**
     * Gets the colour currently used for drawing on this SimpleCanvas.
     */
    public Color getForegroundColour() {
        return graphic.getColor();
    }

    /**
     * Changes the font for subsequent Strings on this SimpleCanvas.
     */

    public void setFont(Font newFont) {
        graphic.setFont(newFont);
    }

    /**
     * Gets the font currently used for Strings on this SimpleCanvas.
     */
    public Font getFont() {
        return graphic.getFont();
    }

    /**
     * Sets the repaint mode to either manual or automatic.
     */
    public void setAutoRepaint(boolean autoRepaint) {
        this.autoRepaint = autoRepaint;
    }

    /**
     * If this SimpleCanvas does not automatically repaint after each drawing
     * command,
     * this method can be used to cause a manual repaint.
     */
    public void repaint() {
        canvas.repaint();
    }

    /**
     * Causes execution to pause for the specified amount of time.
     * This is usually used to produce animations in an easy manner,
     * by repeatedly drawing, pausing, and then redrawing an object.
     */
    public void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            System.out.println("Interruption in SimpleCanvas: " + ie);
        }
    }

    /**
     * Sets up this SimpleCanvas to respond to mouse input.
     */
    public void addMouseListener(MouseListener ml) {
        canvas.addMouseListener(ml);
    }

    /**
     * Sets up this SimpleCanvas to respond to mouse motion input.
     */
    public void addMouseMotionListener(MouseMotionListener mml) {
        canvas.addMouseMotionListener(mml);
    }

    class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
}
