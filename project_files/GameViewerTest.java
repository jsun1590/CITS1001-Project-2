

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * The test class GameViewerTest.
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 */
public class GameViewerTest
{
    GameViewer gv;
    /**
     * Default constructor for test class GameViewerTest
     */
    public GameViewerTest()
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
        gv = new GameViewer();
    }
    
    @Test
    public void testNearestPiece() {
        int[] test1 = new int[2];
        int[] test1val = gv.getNearestPiece(0,0);
        assertEquals(test1[0], test1val[0]);
        assertEquals(test1[1], test1val[1]);
        
        int[] test2 = new int[2];
        test2[1] = 7;
        int[] test2val = gv.getNearestPiece(0,300);
        assertEquals(test2[0], test2val[0]);
        assertEquals(test2[1], test2val[1]);
    }
    
    @Test
    public void testGetTurnsRemaining() {
        assertEquals(gv.getTurnsRemaining(), 20);
    }
    
    @Test 
    public void testReduceTurns() {
        gv.reduceTurns(false);
        assertEquals(gv.getTurnsRemaining(), 19);
        gv.reduceTurns(true);
        assertEquals(gv.getTurnsRemaining(), 19);
    }
    
    @Test
    public void testTakeTurn() {
        // game viewer with zero turns
        GameViewer gv = new GameViewer(40, 0, 0);
        GameViewer gv2 = new GameViewer(40, 2, 0);
        GameViewer gv3 = new GameViewer(40, 2, 0);
        
        // test case where no turns remaining
        assertEquals(gv.takeTurn(0, 0), 0, "case where no turns remaining");
        
        // test case where one turn remains and items have not been found
        assertEquals(gv2.takeTurn(0,0), 1, "case where turns remain and item not found");
        
        // test case where items are found already
        Board bd = new Board(12, 12, Piece.FOUNDITEM);
        gv3.setBoardStateTestingOnly(bd);
        assertEquals(gv3.takeTurn(0,0), 2, "case where item has already been found at x,y");
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
