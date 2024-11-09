package vaultescape.utils;

/**
 * The {@code Vector2} class represents a 2-dimensional vector with integer coordinates.
 * Inspired by Godot Game Engine's Vector2
 */
public class Vector {
    public int x,y;

    // Base Vector Sizing
    public static final Vector TILE_DIM = new Vector(16,16); // Tile Size (base)
    public static final int SCALE = 4; // Tile Scale (to pixels)
    public static final Vector TILE_SIZE = TILE_DIM.scale(SCALE); // Tile Size (pixels)

    /**
     * Constructs a new Vector2 object with coordinates (0, 0).
     */
    public Vector() {this.x = this.y = 0;}

    /**
     * Constructs a new Vector2 object as a copy of an existing vector.
     * 
     * @param vector the Vector2 object to copy
     */
    public Vector(Vector vector) {
        setPosition(vector);
    }

    /**
     * Constructs a new {@code Vector2} with the specified coordinates.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     */
    public Vector(int x, int y) {
        setPosition(x,y);
    }

    /**
     * Constructs a new Vector2 object with both x and y coordinates set to the same value.
     * 
     * @param xy the value to set for both x and y coordinates
     */
    public Vector(int xy) {
        setPosition(xy, xy);
    }

    /**
     * Retrieves the x-coordinate of the sprite.
     *
     * @return the x-coordinate of the sprite
     */
    public int getX() {return x;}

    /**
     * Retrieves the y-coordinate of the sprite.
     *
     * @return the y-coordinate of the sprite
     */
    public int getY() {return y;}

    /**
     * Sets the position of the current object to the specified coordinates.
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the position of the current object based on another vector.
     *
     * @param vector the vector2 whose coordinates will be used to set the position
     */
    public void setPosition(Vector vector) {
        setPosition(vector.x,vector.y);
    }

    /**
     * Gets the position as a Vector2 object.
     * 
     * @return a new Vector2 object containing the x and y coordinates
     */
    public Vector getPosition() {
        return new Vector(x, y);
    }
    
    /**
     * Checks if the current vector is approximately equal to another vector.
     * This method uses a small tolerance value to account for minor differences.
     * 
     * @param vector the vector to compare with
     * @return true if the vectors are approximately equal, false otherwise
     */
    public boolean equals(Vector vector) {
        // NOTE: EQUALS APPROX, TOLERANCE = 1
        return Math.abs(this.x - vector.x) <= 1 && Math.abs(this.y - vector.y) <= 1;
    }

    public boolean isZero() {
        return equals(new Vector(0,0));
    }
    /**
     * Multiplies the current vector with another vector.
     * 
     * @param vector the vector to multiply with
     * @return a new Vector2 object that is the result of the multiplication
     */
    public Vector multiply(Vector vector) {
        return new Vector(x * vector.x, y * vector.y);
    }

    /**
     * Adds another vector to the current vector.
     * 
     * @param vector the vector to add
     * @return a new Vector2 object that is the result of the addition
     */
    public Vector add(Vector vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    /**
     * Subtracts another vector from the current vector.
     * 
     * @param vector the vector to subtract
     * @return a new Vector2 object that is the result of the subtraction
     */
    public Vector subtract(Vector vector) {
        return new Vector(x - vector.x, y - vector.y);
    }

    /**
     * Scales the current vector by a scalar value.
     * 
     * @param scalar the value to scale the vector by
     * @return a new Vector2 object that is the result of the scaling
     */
    public Vector scale(int scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public Vector scale(double scalar) {
        return new Vector((int)Math.round(x * scalar), 
            (int)Math.round(y * scalar));
    }

    public boolean greaterThan(Vector vector) {
        return (x > vector.x && y > vector.y);
    }

    public boolean lessThan(Vector vector) {
        return (x < vector.x && y < vector.y);
    }

    /**
     * Converts the current vector to global coordinates.
     * 
     * @return a new Vector2 object that is the global representation of the vector
     */
    public Vector toGlobal() {
        return this.multiply(TILE_SIZE);
    }

    /**
     * Converts the current vector to tile coordinates.
     * 
     * @return a new Vector2 object that is the tile representation of the vector
     */
    public Vector toTile() {
        return new Vector(x / TILE_SIZE.x, y / TILE_SIZE.y);
    }

}

