package game.ui;

import game.object.Rect;
import game.object.Vector;
import game.utils.ResourceLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a container UI element that holds and arranges labels in the game.
 * It supports different alignments and margins for positioning the labels.
 */
public class Container extends Rect {
    Font font;
    int fontSize;

    /**
     * Enum to define the possible alignments for labels: LEFT, TOP, BOTTOM.
     */
    public enum Alignment {LEFT, CENTER, TOP, BOTTOM}
    
    Alignment horizontalAlignment = Alignment.LEFT;
    Alignment verticalAlignment = Alignment.BOTTOM;

    public int leftMargin = 48, rightMargin = 48, topMargin = -32, bottomMargin = 48;
    public int separation = 48;
    boolean isPanel = false; // Background panel for container

    List<Label> labels = new ArrayList<>();

    /**
     * Constructs a Container.
     */
    public Container() {}

    /**
     * Draws the labels within the container at their respective positions based on alignment.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        if (isPanel) {
            g2.setColor(new Color(0,0,0,123));
            g2.fillRect(x,y,w,h);
        }
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
        } else if (verticalAlignment == Alignment.CENTER) {
            int totalHeight = separation * labels.size();
            v.y -= totalHeight / 2;
            for (Label label : labels) {
                if (horizontalAlignment == Alignment.CENTER) {
                    v.x = 1280/2 - label.getWidth()/2;
                }
                label.draw(g2, v);
                v.y += separation;
            }
        }
    }

    /**
     * Sets the font for the labels in this container.
     *
     * @param size The font size to set for the labels.
     */
    public void setFont(int size) {
        this.fontSize = size;
        this.font = ResourceLoader.loadFont(size);
    }

    public void setSeparation(int separation) {this.separation = separation;}

    /**
     * Adds a label to the container and sets its font.
     *
     * @param label The label to add to the container.
     */
    public void addLabel(Label label) {
        label.index = labels.size();
        labels.add(label);
        label.setFont(fontSize);
    }

    public void addPanel(Rect panel) {
        isPanel = true;
        setRect(panel);
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
            case CENTER:
                v.x = 1280 / 2;
                break;
            default:
                break;
        }
        switch (verticalAlignment) {
            case TOP:
                v.y = topMargin;
                break;
            case CENTER:
                v.y = 768 / 2 + topMargin - bottomMargin;
                break;
            case BOTTOM:
                v.y = 768 - bottomMargin;
            default:
                break;
        }
    }

    /**
     * Sets the alignment for the container.
     * @param hAlignment The horizontal alignment to set.
     * @param vAlignment The vertical alignment to set.
     */
    public void setAlignment(Alignment hAlignment, Alignment vAlignment) {
        this.horizontalAlignment = hAlignment;
        this.verticalAlignment = vAlignment;
    }
}
