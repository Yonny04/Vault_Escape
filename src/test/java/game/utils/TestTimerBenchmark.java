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
        assertTrue(Math.abs(10 - timer.getSecondsLeft()) == 0);

    }

    @Test
    public void TestDecreaseTime(){
        timer.decreaseTime(5);
        assertTrue(Math.abs(10 - timer.getSecondsLeft()) == 5);
    }

    @Test 
    public void TestTimeSeconds(){
        timer.decreaseTime(2);
        assertTrue(Math.abs(10 - timer.getSecondsLeft()) == 2);

    }

    @Test
    public void TestTimeIsUp(){
        timer.decreaseTime(10);
        assertEquals(true, timer.isTimeUp());
    }

    @Test
    public void TestNewTimer(){
        timer.setCountdownTime(9);
        assertTrue(Math.abs(timer.getSecondsLeft()) == 9);
    }
}
