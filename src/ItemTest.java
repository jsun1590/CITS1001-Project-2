

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;


/**
 * The test class ItemTest.
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 */
public class ItemTest
{
    Item phone;
    Item keys;
    Item shoe;
    Item walkingStick;
    Item book;

    public ItemTest()
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
        phone = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 } });
        keys = new Item("keys", new int[][] { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } });
        shoe = new Item("shoe", new int[][] { { 1, 0, 0 }, { 1, 0, 0 }, { 1, 1, 0 } });
        walkingStick = new Item("walking stick", new int[][] { { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } });
        book = new Item("book", new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 1, 1, 0 } });
    }
    
    @Test
    public void testGetType() {
        assertEquals(phone.getType(), "phone");
        assertEquals(keys.getType(), "keys");
    }
       
    @Test
    public void testGetShape() {
        assertTrue(Arrays.deepEquals(phone.getShape(), new int[][] {  { 1, 0 }, { 1, 0 } } ));
        assertTrue(Arrays.deepEquals(keys.getShape(), new int[][] {{ 0, 1, 0 }, { 1, 1, 1 }, { 0, 0, 0 } }));
    }
    
    @Test
    public void testSetShape() {
        Item newPhone = new Item("phone", new int[][] {  { 1, 0 }, { 1, 0 } });
        newPhone.setShape(new int[][] { { 0, 1, 0 }, { 1, 1, 1 },  { 0, 1, 0 } });
        assertTrue(Arrays.deepEquals(newPhone.getShape(), new int[][] { { 0, 1, 0 }, { 1, 1, 1 }, { 0, 1, 0 } }));
    }
    
    @Test
    public void testGetIsFound() {
        Item newPhone = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }  });
        assertEquals(newPhone.getIsFound(), false);
        newPhone.setFound();
        assertEquals(newPhone.getIsFound(), true);
    }
    
    @Test
    public void testSetFound() {
        Item newPhone = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }  });
        newPhone.setFound();
        assertEquals(newPhone.getIsFound(), true);
        newPhone.setFound();
        assertEquals(newPhone.getIsFound(), true); // remains found, doesn't toggle.
    }
    
    @Test
    public void testGetLocationX() {
        assertEquals(phone.getLocationX(), -1);
    }
    
    @Test
    public void testGetLocationY() {
        assertEquals(phone.getLocationY(), -1);
    }
    
    @Test
    public void testSetLocation() {
        Item newPhone = new Item("phone", new int[][] { { 1, 0 }, { 1, 0 }  });
        newPhone.setLocation(10, 10);
        assertEquals(newPhone.getLocationX(), 10);
        assertEquals(newPhone.getLocationY(), 10);
    }
    
    @Test
    public void testRotate90Degrees() {
        Item newPhone = new Item("phone", new int[][] {{ 1, 0 }, { 1, 0 }  });
        newPhone.rotate90Degrees();
        assertTrue(Arrays.deepEquals(newPhone.getShape(), new int[][] {{ 1, 1 }, { 0, 0 }  }));
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
