

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * The test class AIPlayerTest
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 */
public class AIPlayerTest
{  
    AIPlayer ai;
    Item[] itemBank = new Item[] {
                new Item("phone", new int[][] { { 1, 0 }, { 1, 0 } }),
                new Item("keys", new int[][] { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }),
                new Item("shoe", new int[][] { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 } }),
                new Item("walking stick", new int[][] { { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } }),
                new Item("book", new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 1, 1, 0 } }),
    };
    
    public AIPlayerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        ai = new AIPlayer(new Board(), 3);
    }
    
    @Test
    public void testSelectItems() {
        Item[] selected = ai.selectItems(3);
        assertEquals(selected.length, 3, "Length of returned array is incorrect");
        boolean foundPhone = false;
        boolean foundBook = false;
        
        // Randomness test
        for (int i = 0; i<1000000; i++) {
            selected = ai.selectItems(1);
            assertEquals(selected.length, 1, "Length of returned array is incorrect");
            if (selected[0].getType().equals("phone")) {
                foundPhone = true;
            }
            else if (selected[0].getType().equals("book")) {
                foundBook = true;
            }
        }
        assertTrue(foundPhone && foundBook);
    }
    
    @Test
    public void testSelectLocation() {
        int[] location;
        boolean found0x = false;
        boolean found0y = false;
        boolean found11x = false;
        boolean found11y = false;
        for (int i = 0; i<1000000; i++) {
            location = ai.selectLocation();
            if (location[0] == 1) {
                found0x = true;
            }
            else if (location[0] == 11) {
                found11x = true;
            }
            if (location[1] == 1) {
                found0y = true;
            }
            else if (location[1] == 11) {
                found11y = true;
            }
        }
        assertTrue(found0x && found0y && found11x && found11y);
    }
    
    @Test
    public void testSelectOrientation() {
        int selection;
        boolean found0 = false;
        boolean found270 = false;
        for (int i = 0; i<1000000; i++) {
            selection = ai.selectOrientation();
            if (selection == 0) {
                found0 = true;
            } 
            if (selection == 270) {
                found270 = true;
            }
        }
        assertTrue(found0 && found270);
    }
    
    @Test
    public void testTryItemLocation() {
        // setup board
        Board b = new Board(2, 2, Piece.VACANT);
        Item i = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }} );
        AIPlayer a = new AIPlayer(b, 3);
        boolean valid = a.tryItemLocation(i, new int[] {0, 0}, 90);
        
        // test orientation
        assertTrue(Arrays.deepEquals(i.getShape(), new int[][] {{ 1, 1 }, { 0, 0 }  }), "Method not rotating piece");
        
        // test valid if vacant
        assertTrue(valid, "Method not returning true when pieces vacant");
        
        // test invalid if lost item in the way
        b = new Board(2, 2, Piece.LOSTITEM);
        i = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }} );
        a = new AIPlayer(b, 3);
        valid = a.tryItemLocation(i, new int[] {0, 0}, 90);
        assertFalse(valid, "Method not returning false when lost pieces in the way");
        
        // test invalid if out of range lost item in the way
        b = new Board(1, 1, Piece.VACANT);
        i = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }} );
        a = new AIPlayer(b, 3);
        valid = a.tryItemLocation(i, new int[] {0, 0}, 90);
        assertFalse(valid, "Method not returning false when location out of range");
    }
    
    @Test
    public void testLostPieces() {
        Board b = new Board(2, 2, Piece.VACANT);
        Item i = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }} );
        AIPlayer a = new AIPlayer(b, 3);
        a.setLostPieces(i, new int[] {0, 0});
        
        assertTrue(b.isLostItem(0, 0));
        assertTrue(b.isLostItem(0, 1));
        assertFalse(b.isLostItem(1, 0));
        assertFalse(b.isLostItem(1, 1));
    }
    
    
    @Test 
     public void testStartGame() {
        Board b = new Board();
        AIPlayer a = new AIPlayer(b, 2);
        Item[] selected = a.startGame();
        
        // should return two items
        assertEquals(2, selected.length, "Length of returned array is incorrect");
        // both items selected should be unique
        assertFalse(Arrays.deepEquals(selected[0].getShape(), selected[1].getShape()));
        // number of lost pieces on board should be equal to the number of pieces in shapes
        
        int selectedItemsPieceCount = 0;
        for (Item item: selected) {
            int[][] itemShape = item.getShape();
            for (int i = 0; i < itemShape.length; i++) {
                for (int j = 0; j < itemShape[i].length; j++) {
                    if (itemShape[i][j] == 1) {
                        selectedItemsPieceCount++;
                    }
                }
            }
        }
        
        int lostItemsOnBoardCount = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (b.isLostItem(i, j)) {
                    lostItemsOnBoardCount++;
                }
            }
        }
        assertEquals(selectedItemsPieceCount, lostItemsOnBoardCount, "Test number of items placed on board");
    }
        
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
