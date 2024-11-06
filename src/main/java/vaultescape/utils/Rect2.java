package vaultescape.utils;

/**
 * The {@code Rect2} class represents a rectangle, extending the {@code Vector2} class.
 * It includes properties for width and height, and provides methods to get these properties
 * as well as to check if another rectangle is touching the current rectangle.
 * Inspired by Godot Game Engine's Rect2 class.
 */
public class Rect2 extends Vector2 {
    public int w, h;

    public Rect2() {
        super();
        setDimension(TILE_SIZE);
    }
    /**
     * Constructs a Rect2 object with specified x and y coordinates.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Rect2(int x, int y) {
        super(x, y);
        setDimension(TILE_SIZE);
    }

    /**
     * Constructs a Rect2 object with specified coordinates from a Vector2 object.
     * 
     * @param vector the Vector2 object containing the x and y coordinates
     */
    public Rect2(Vector2 vector) {
        super(vector);
        setDimension(TILE_SIZE);
    }

    /**
     * Constructs a new {@code Rect2} with the specified coordinates and dimensions.
     *
     * @param x the x coordinate of the rectangle
     * @param y the y coordinate of the rectangle
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     */
    public Rect2(int x, int y, int w, int h) {
        super(x, y);
        setDimension(w,h);
    }

    /**
     * Constructs a Rect2 object with specified coordinates and dimensions.
     * 
     * @param xy the vector containing the x and y coordinates
     * @param wh the vector containing the width (w) and height (h)
     */
    public Rect2(Vector2 xy, Vector2 wh) {
        super(xy);
        setDimension(wh);
    }

    public Rect2 getRect() {return this;}
    
    public void setRect(Rect2 rect) {
        setPosition(rect.getPosition());
        setDimension(rect.getDimension());
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
    public void setDimension(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Sets the dimensions of an object using a Vector.
     * 
     * @param vector the Vector containing the width and height to be set
     */
    public void setDimension(Vector2 vector) {
        setDimension(vector.x,vector.y);
    }

    /**
     * Gets the dimensions of the object as a Vector2 object.
     * 
     * @return a new Vector2 object containing the width and height (w, h)
     */
    public Vector2 getDimension() {
        return new Vector2(w, h);
    }

    /**
     * Checks if the current rectangle is touching another rectangle.
     *
     * @param rect the 2 rectangle to check against
     * @return {@code true} if the rectangles are touching, otherwise {@code false}
     */
    public boolean isTouching(Rect2 rect) {
        return (x < (rect.x + rect.w) &&
                (x + w) > rect.x &&
                y < (rect.y + rect.h) &&
                (y + h) > rect.y);
    }

}

