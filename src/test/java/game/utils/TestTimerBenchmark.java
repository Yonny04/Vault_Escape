package game.utils;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestTimerBenchmark {

    Timer timer;

    @BeforeEach
    public void setUpTimer(){
        timer = new Timer(10);
    }

    @Test
    public void TestInitialTime(){
        timer.start();
        assertTrue(Math.abs(10 - timer.getSecondsLeft()) == 0);

    }

    @Test
    public void TestDecreaseTime(){
        timer.start();
        timer.decreaseTime(5);
        assertTrue(Math.abs(10 - timer.getSecondsLeft()) == 5);
    }

    @Test 
    public void TestTimeSeconds(){
        timer.start();
        timer.decreaseTime(2);
        assertTrue(Math.abs(10 - timer.getSecondsLeft()) == 2);

    }

    @Test
    public void TestTimeIsUp(){
        timer.start();
        timer.decreaseTime(10);
        assertEquals(true, timer.isTimeUp());
    }

    @Test
    public void TestNewTimer(){
        timer.start();
        timer.setCountdownTime(9);
        assertTrue(Math.abs(timer.getSecondsLeft()) == 9);
    }
}
