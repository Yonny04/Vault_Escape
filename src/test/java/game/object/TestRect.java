package game.object;

import org.junit.*;

import static org.junit.Assert.*;

public class TestRect {
    private Rect rect1;
    private Rect rect2;
    private Rect rect3;
    private Vector vector;

    @Before
    public void setUp() {
        vector = new Vector(3, 4);
        rect1 = new Rect(5, 6);
        rect2 = new Rect(vector);
        rect3 = new Rect(2, 3, 4, 5);
        Rect rect4 = new Rect(rect1);
        rect4 = new Rect(vector,vector);
        rect4 = new Rect();
    }

    @Test
    public void testConstructorWithCoordinates() {
        assertEquals(5, rect1.getX());
        assertEquals(6, rect1.getY());
    }

    @Test
    public void testConstructorWithVector() {
        assertEquals(3, rect2.getX());
        assertEquals(4, rect2.getY());
    }

    @Test
    public void testSetSize() {
        rect1.setSize(10,10);
        assertEquals(10, rect1.getWidth());
        assertEquals(10, rect1.getHeight());
    }

    @Test 
    public void testGetSizeDefault() {
        Vector size = rect1.getSize();
        assertEquals(Vector.TILE_SIZE.x, size.getX());
        assertEquals(Vector.TILE_SIZE.y, size.getY());
    }

    @Test
    public void testGetSizeCustom() {
        Vector size = rect3.getSize();
        assertEquals(4, size.getX());
        assertEquals(5, size.getY());
    }

    @Test
    public void testSetRect() {
        rect1.setRect(rect3);
        assertEquals(2, rect1.getX());
        assertEquals(3, rect1.getY());
        assertEquals(4, rect1.getWidth());
        assertEquals(5, rect1.getHeight());
    }
    @Test
    public void testGetRect() {
        Rect rect = rect3.getRect();
        assertEquals(2, rect.getX());
        assertEquals(3, rect.getY());
        assertEquals(4, rect.getWidth());
        assertEquals(5, rect.getHeight());
    }
    @Test
    public void testGetPosition() {
        Vector position = rect1.getPosition();
        assertEquals(5, position.getX());
        assertEquals(6, position.getY());
    }

    @Test
    public void testEquals() {
        Rect rect3 = new Rect(5, 6);
        assertTrue(rect1.equals(rect3));
        assertFalse(rect1.equals(rect2));
    }

    @Test
    public void testIsTouching() {
        Rect rect4 = new Rect(7, 8, 1, 1);
        assertTrue(rect1.isTouching(rect4));
        assertFalse(rect4.isTouching(rect3));
    }

}