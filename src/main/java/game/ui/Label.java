package game.ui;

import game.object.*;
import game.utils.ColorPalette;

import java.awt.*;

/**
 * Represents a label in the game UI, which can display text with 
 * various properties such as color, visibility, and shadow.
 */
public class Label extends Rect {
    String text = "";
    Color color = ColorPalette.WHITE;
    boolean visible = true;
    boolean hasShadow = false;
    boolean displayInt = false;
    Font font;
    public int index = 0;

    /**
     * Constructs a Label with the specified color and shadow option.
     *
     * @param color The color of the label text.
     * @param shadow Whether the label should have a shadow.
     */
    public Label(Color color, boolean shadow) {
        setColor(color);
        if (shadow) enableShadow();
    }

    /**
     * Draws the label on the screen with the given Graphics2D context.
     *
     * @param g2 The Graphics2D object used to render the label.
     * @param screen The screen position to draw the label.
     */
    public void draw(Graphics2D g2, Vector screen) {
        if (isVisible()) {
            if (font != null) g2.setFont(font);
            setPosition(screen);
            // FontMetrics metrics = g2.getFontMetrics(font);
            // this.w = metrics.stringWidth(text);
            // this.h = metrics.getHeight();
            if (hasShadow) {
                g2.setColor(ColorPalette.SHADOW);
                g2.drawString(text, screen.x, screen.y + 4);
            }
            g2.setColor(color);
            g2.drawString(text, screen.x, screen.y);
        }
    }

    /**
     * Sets the color of the label text.
     *
     * @param color The color to set for the label text.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the text of the label.
     *
     * @param text The text to set for the label.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the font for the label text.
     *
     * @param font The font to set for the label.
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Enables the shadow effect for the label.
     */
    public void enableShadow() {
        this.hasShadow = true;
    }

    /**
     * Shows the label by setting its visibility to true.
     */
    public void show() {
        visible = true;
    }

    /**
     * Hides the label by setting its visibility to false.
     */
    public void hide() {
        visible = false;
    }

    /**
     * Checks if the label is visible.
     *
     * @return true if the label is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }
}
