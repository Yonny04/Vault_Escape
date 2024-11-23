package game.ui;

import game.object.*;
import game.ui.Container.Alignment;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestContainer {

    Container container;

    @BeforeEach
    public void setUp() {
        container = new Container();
    }

    @Test
    public void testContainerStartVectorLeftAlign() {
        Vector v = new Vector();
        container.startVector(v);
        assertTrue(v.x == container.leftMargin);
    }

    @Test
    public void testContainerStartVectorBottomAlign() {
        Vector v = new Vector();
        container.startVector(v);
        assertTrue(v.y == 768-container.bottomMargin);
    }

    @Test
    public void testContainerSeparation() {
        container.setSeparation( 48);
        assertTrue(container.separation == 48);
    }

    @Test
    public void testContainerFont() {
        container.setFont(48);
        assertTrue(container.fontSize == 48);
    }

    @Test
    public void testContainerAddPanel() {
        Rect panel = new Rect();
        container.addPanel(panel);
        assertTrue(container.isPanel);
    }

    @Test
    public void testContainerGetLabel() {
        Label label = new Label(null, false);
        container.addLabel(label);
        assertTrue(container.getLabel(0) == label);
    }

    @Test
    public void testContainerSetAlignment() {
        container.setAlignment(Alignment.CENTER, Alignment.CENTER);
        assertTrue(container.horizontalAlignment == Alignment.CENTER);
        assertTrue(container.verticalAlignment == Alignment.CENTER);
    }
}
