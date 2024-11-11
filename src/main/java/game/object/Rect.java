package game.object;

/**
 * The {@code Rect} class represents a rectangle, extending the {@code Vector} class.
 * It includes properties for width and height, and provides methods to get these properties
 * as well as to check if another rectangle is touching the current rectangle.
 * Inspired by Godot Game Engine's Rect2 class.
 */
public class Rect extends Vector {
    public int w, h;

    public Rect() {
        super();
        setSize(TILE_SIZE);
    }
    /**
     * Constructs a Rect object with specified x and y coordinates.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Rect(int x, int y) {
        super(x, y);
        setSize(TILE_SIZE);
    }

    /**
     * Constructs a Rect object with specified coordinates from a Vector object.
     * 
     * @param vector the Vector object containing the x and y coordinates
     */
    public Rect(Vector vector) {
        super(vector);
        setSize(TILE_SIZE);
    }

    /**
     * Constructs a new {@code Rect} with the specified coordinates and dimensions.
     *
     * @param x the x coordinate of the rectangle
     * @param y the y coordinate of the rectangle
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     */
    public Rect(int x, int y, int w, int h) {
        super(x, y);
        setSize(w,h);
    }

    /**
     * Constructs a Rect object with specified coordinates and dimensions.
     * 
     * @param xy the vector containing the x and y coordinates
     * @param wh the vector containing the width (w) and height (h)
     */
    public Rect(Vector xy, Vector wh) {
        super(xy);
        setSize(wh);
    }

    public Rect getRect() {return this;}
    
    public void setRect(Rect rect) {
        setPosition(rect.getPosition());
        setSize(rect.getSize());
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public int getWidth() {return w;}

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public int getHeight() {return h;}

    /**
     * Sets the dimensions of an object.
     * 
     * @param w the width to be set
     * @param h the height to be set
     */
    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Sets the dimensions of an object using a Vector.
     * 
     * @param vector the Vector containing the width and height to be set
     */
    public void setSize(Vector vector) {
        setSize(vector.x,vector.y);
    }

    /**
     * Gets the dimensions of the object as a Vector object.
     * 
     * @return a new Vector object containing the width and height (w, h)
     */
    public Vector getSize() {
        return new Vector(w, h);
    }

    /**
     * Checks if the current rectangle is touching another rectangle.
     *
     * @param rect the Rect object to check against
     * @return {@code true} if the rectangles are touching, otherwise {@code false}
     */
    public boolean isTouching(Rect rect) {
        return (x < (rect.x + rect.w) &&
                (x + w) > rect.x &&
                y < (rect.y + rect.h) &&
                (y + h) > rect.y);
    }

}

