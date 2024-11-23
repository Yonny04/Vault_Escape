package game.ui;

import org.junit.jupiter.api.*;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLabel {

    Label label;

    @BeforeEach
    public void setUp() {
        label = new Label(new Color(0,0,0),true);
    }

    @Test
    public void testLabelShow() {
        label.show();
        assertTrue(label.isVisible());
    }

    @Test
    public void testLabelHide() {
        label.hide();
        assertTrue(!label.isVisible());
    }

    @Test
    public void testLabelIsVisible() {
        assertTrue(label.visible == label.isVisible());
    }
}
