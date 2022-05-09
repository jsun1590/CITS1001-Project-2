

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * The test class BoardTest
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 */

 public class BoardTest
{
    Board bigBoard;
    Board smallBoard;
    
    /**
     * Default constructor for test class BoardTest
     */
    public BoardTest()
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
        bigBoard = new Board();
        smallBoard = new Board(6, 6, Piece.VACANT);
    }
    
    @Test
    public void testGetBoardSize() {
        assertEquals(bigBoard.getBoardSize(), 12);
        assertEquals(smallBoard.getBoardSize(), 6);
    }
    
    @Test
    public void testIsPiece() {
        assertTrue(bigBoard.isPiece(11, 11));
        assertFalse(bigBoard.isPiece(15, 15));
        assertFalse(smallBoard.isPiece(11, 11));
        assertTrue(smallBoard.isPiece(0,0));
        assertFalse(smallBoard.isPiece(-1,-1));
    }
    
    @Test
    public void testGetPiece() {
        assertEquals(bigBoard.getPiece(1, 1).name(), Piece.VACANT.name());
    }
        
    @Test
    public void testIsLostItem() {
        Board b = new Board(1, 1, Piece.LOSTITEM);
        assertTrue(b.isLostItem(0,0));
    }
    
    @Test
    public void testSetLostItem() {
        Board b = new Board(1, 1, Piece.VACANT);
        b.setLostItem(0,0);
        assertEquals(b.getPiece(0, 0).name(), Piece.LOSTITEM.name());
    }
    
    @Test
    public void testIsVacant() {
        Board b = new Board(1, 1, Piece.VACANT);
        assertTrue(b.isVacant(0,0));
    }
    
    @Test
    public void testSetVacant() {
        Board b = new Board(1, 1, Piece.LOSTITEM);
        b.setVacant(0,0);
        assertEquals(b.getPiece(0, 0).name(), Piece.VACANT.name());
    }
    
    @Test
    public void testIsFoundItem() {
        Board b = new Board(1, 1, Piece.FOUNDITEM);
        assertTrue(b.isFoundItem(0,0));
    }
    
    @Test
    public void testSetFoundItem() {
        Board b = new Board(1, 1, Piece.VACANT);
        b.setFoundItem(0,0);
        assertEquals(b.getPiece(0, 0).name(), Piece.FOUNDITEM.name());
    }
    
    @Test
    public void testIsSearched() {
        Board b = new Board(1, 1, Piece.SEARCHED);
        assertTrue(b.isSearched(0,0));
    }
    
    @Test
    public void testSetSearched() {
        Board b = new Board(1, 1, Piece.VACANT);
        b.setSearched(0,0);
        assertEquals(b.getPiece(0, 0).name(), Piece.SEARCHED.name());
    }
    
    @Test
    public void testSearchSpace() {
        Board b = new Board(2, 2, Piece.VACANT);
        b.setLostItem(0,0);
        b.setFoundItem(0,1);
        b.setSearched(1,0);
        
        b.searchSpace(0,0);
        b.searchSpace(0,1);
        b.searchSpace(1,0);
        b.searchSpace(1,1);
        
        assertTrue(b.isFoundItem(0,0));
        assertTrue(b.isFoundItem(0,1));
        assertTrue(b.isSearched(1,0));
        assertTrue(b.isSearched(1,1));
    }
    
    @Test
    public void testClosestItem() {
        Board bd = new Board(3, 3, Piece.VACANT);
        bd.setLostItem(0,1);
        bd.setLostItem(2,2);
        int[] closest = bd.getClosestItem(1, 0);
        
        assertEquals(closest[0], 1);
        assertEquals(closest[1], 1);
    }
    
    @Test 
    public void testCheckForFoundItem() {
        Item i = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 } });
        i.setLocation(0,0);
        Board bd = new Board(2, 2, Piece.VACANT);
        bd.setFoundItem(0, 0);
        bd.setFoundItem(0, 1);
        assertTrue(bd.checkForFoundItem(i));
        bd.setVacant(0, 0);
        assertFalse(bd.checkForFoundItem(i));
    }
    
    @Test
    public void testCheckWhichItemsFound() {
        Item i = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 } });
        i.setLocation(0,0);
        Item[] itemList = new Item[1];
        itemList[0] = i;
        Board bd = new Board(2, 2, Piece.VACANT);
        bd.setFoundItem(0, 0);
        bd.setFoundItem(0, 1);
        ArrayList<Item> answer = new ArrayList<Item>();
        answer.add(i);
        assertTrue(bd.getFoundItemsList(itemList).equals(answer));
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
