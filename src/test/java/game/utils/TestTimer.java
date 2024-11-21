package game.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTimer {

    @Test
    void testTimerStartsAtZero() {
        Timer zero = new Timer(0);
        assertEquals(0, zero.getTimeLeft());
    }

    @Test
    void testTimerGetTimeLeft() {
        Timer timer = new Timer(2.0);
        assertEquals(2000, (int)timer.getTimeLeft());
    }

    @Test
    void testTimerGetSecondsLeft() {
        Timer timer = new Timer(2.0);
        assertEquals(2, timer.getSecondsLeft());
    }

    @Test
    void testTimerDecreaseTime() {
        Timer timer = new Timer(2.0);
        timer.decreaseTime(1);
        assertEquals(1000, (int)timer.getTimeLeft());
    }

    @Test
    void testTimerSetCountdownTime() {
        Timer timer = new Timer(2.0);
        timer.setCountdownTime(5.0);
        assertEquals(5000, (int)timer.getTimeLeft());
    }

    @Test
    void testTimerIsFinished() {
        Timer timer = new Timer(0);
        assertTrue(timer.isTimeUp());
    }

    @Test
    void testTimerIsNotFinished() {
        Timer timer = new Timer(2.0);
        assertFalse(timer.isTimeUp());
    }
}
