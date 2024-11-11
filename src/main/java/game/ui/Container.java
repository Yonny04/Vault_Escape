package game.ui;

import game.object.Rect;
import game.object.Vector;
import game.panel.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a container UI element that holds and arranges labels in the game.
 * It supports different alignments and margins for positioning the labels.
 */
public class Container extends Rect {
    GamePanel gp;
    Font font;

    /**
     * Enum to define the possible alignments for labels: LEFT, TOP, BOTTOM.
     */
    enum Alignment {LEFT, TOP, BOTTOM}
    
    Alignment horizontalAlignment = Alignment.LEFT;
    Alignment verticalAlignment = Alignment.BOTTOM;

    int leftMargin = 48, rightMargin = 48, topMargin = 48, bottomMargin = 48;
    int separation = 48;

    List<Label> labels = new ArrayList<>();

    /**
     * Constructs a Container with the specified game panel.
     *
     * @param gp The game panel associated with this container.
     */
    public Container(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Draws the labels within the container at their respective positions based on alignment.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        Vector v = new Vector();
        startVector(v);

        if (verticalAlignment == Alignment.TOP) {
            for (Label label : labels) {
                label.draw(g2, v);
                v.y += separation;
            }
        } else if (verticalAlignment == Alignment.BOTTOM) {
            for (int i = labels.size() - 1; i >= 0; i--) {
                Label label = labels.get(i);
                v.y -= separation;
                label.draw(g2, v);
            }
        }
    }

    /**
     * Sets the font for the labels in this container.
     *
     * @param font The font to set for the labels.
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Adds a label to the container and sets its font.
     *
     * @param label The label to add to the container.
     */
    public void addLabel(Label label) {
        label.index = labels.size();
        labels.add(label);
        label.setFont(font);
    }

    /**
     * Retrieves the label at the specified index.
     *
     * @param index The index of the label to retrieve.
     * @return The label at the specified index.
     */
    public Label getLabel(int index) {
        return labels.get(index);
    }

    /**
     * Initializes the starting position vector based on the container's alignment.
     *
     * @param v The vector to initialize with the starting position.
     */
    private void startVector(Vector v) {
        switch (horizontalAlignment) {
            case LEFT:
                v.x = leftMargin;
                break;
            default:
                break;
        }
        switch (verticalAlignment) {
            case TOP:
                v.y = topMargin;
                break;
            case BOTTOM:
                v.y = gp.SCREEN_SIZE.y - bottomMargin;
            default:
                break;
        }
    }
}
