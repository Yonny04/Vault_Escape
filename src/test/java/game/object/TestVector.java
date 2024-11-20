package game.object;

import org.junit.jupiter.api.*;

import static org.junit.Assert.*;

public class TestVector {
    private Vector vector1;
    private Vector vector2;
    private Vector vector3;
    private Vector vector4;

    @BeforeEach
    public void setUp() {
        vector1 = new Vector(3, 4);
        vector2 = new Vector(3);
        vector3 = new Vector();
        vector4 = new Vector(vector2);
    }

    @Test
    public void testSetPositionWithCoordinates() {
        vector1.setPosition(5, 6);
        assertEquals(5, vector1.getX());
        assertEquals(6, vector1.getY());
    }

    @Test
    public void testSetPositionWithVector() {
        vector1.setPosition(vector3);
        assertEquals(0, vector1.getX());
        assertEquals(0, vector1.getY());
    }

    @Test
    public void testGetPosition() {
        Vector position = vector1.getPosition();
        assertEquals(3, position.getX());
        assertEquals(4, position.getY());
    }

    @Test
    public void testEquals() {
        assertTrue(vector1.equals(vector2));
        assertFalse(vector1.equals(vector3));
    }

    @Test
    public void testAdd() {
        Vector sum = vector1.add(vector2);
        assertEquals(6, sum.getX());
        assertEquals(7, sum.getY());
    }

    @Test
    public void testSubtract() {
        Vector difference = vector1.subtract(vector4);
        assertEquals(0, difference.getX());
        assertEquals(1, difference.getY());
    }

    @Test
    public void testMultiply() {
        Vector product = vector1.multiply(vector2);
        assertEquals(9, product.getX());
        assertEquals(12, product.getY());
    }

    @Test
    public void testScaleDouble() {
        Vector scaled = vector1.scale(1.3);
        assertEquals(4, scaled.getX());
        assertEquals(5, scaled.getY());
    }

    @Test
    public void testIsZero() {
        assertTrue(vector3.isZero());
        assertFalse(vector1.isZero());
    }

    @Test
    public void testToGlobal() {
        Vector global = vector1.toGlobal();
        assertEquals(3*Vector.TILE_SIZE.x, global.getX());
        assertEquals(4*Vector.TILE_SIZE.y, global.getY());
    }

    @Test
    public void testGetUnitString() {
        assertEquals("3, 4", vector1.toGlobal().getUnitString());
    }

    @Test
    public void testGreaterThan() {
        assertFalse(vector3.greaterThan(vector1));
        assertTrue(vector1.greaterThan(vector3));
        
    }

    @Test
    public void testLessThan() {
        assertTrue(vector3.lessThan(vector1));
        assertFalse(vector4.lessThan(vector3));
    }

    @Test
    public void distanceTo() {
        //sqrt((3-0)^2 + (4-0)^2) = 5
        assertEquals(5, vector1.distanceTo(vector3));
    }
}
