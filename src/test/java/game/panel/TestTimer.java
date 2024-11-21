package game.panel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import game.utils.Timer;

import static org.junit.Assert.*;


public class TestTimer {

    Timer timer = new Timer(10);

    @BeforeEach
    public void setUpTimer(){
        timer.start();
    }

    @Test
    public void TestInitialTime(){
        assertTrue(Math.abs(10000 - timer.getTimeLeft()) <= 2);

    }

    @Test
    public void TestDecreaseTime(){
        timer.decreaseTime(5);
        assertTrue(Math.abs(5000 - timer.getTimeLeft()) <= 2);

    }

    @Test 
    public void TestTimeSeconds(){
        timer.decreaseTime(2);
        assertTrue(Math.abs(8000 - timer.getTimeLeft()) <= 3);

    }

    @Test
    public void TestTimeIsUP(){
        timer.decreaseTime(10);
        assertEquals(true, timer.isTimeUp());
    }

    @Test
    public void TestNewTimer(){
        timer.setCountdownTime(9);
        assertTrue(Math.abs(9000 - timer.getTimeLeft()) <= 3);
    }
}
