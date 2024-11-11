package game.utils;

/**
 * A Timer class that tracks countdown time in milliseconds, providing methods to start the timer,
 * retrieve the time left, format it as a string, and decrease the countdown time.
 */
public final class Timer {
    private long startTime; // Start time of the countdown
    private long countdownTime; // Total countdown time in milliseconds

    /**
     * Constructs a Timer with a specified countdown duration in seconds.
     *
     * @param seconds the countdown duration in seconds
     */
    public Timer(double seconds) {
        this.countdownTime = (long)Math.floor(seconds * 1000);
        this.start();
    }

    /**
     * Starts or restarts the timer by setting the start time to the current system time.
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Retrieves the time left in the countdown in milliseconds.
     *
     * @return the remaining countdown time in milliseconds, with a minimum of 0
     */
    public long getTimeLeft() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long timeLeft = countdownTime - elapsedTime;
        return Math.max(timeLeft, 0);
    }

    /**
     * Retrieves the time left in the countdown in seconds.
     *
     * @return a formatted int of the remaining time in seconds
     */
    public int getSecondsLeft() {
        long seconds = getTimeLeft() / 1000;
        return (int)seconds;
    }

    /**
     * Checks if the countdown has finished.
     *
     * @return true if the countdown is complete (time left is 0), false otherwise
     */
    public boolean isTimeUp() {
        return getTimeLeft() <= 0;
    }

    /**
     * Decreases the countdown time by a specified number of seconds.
     *
     * @param seconds the number of seconds to decrease from the countdown
     */
    public void decreaseTime(int seconds) {
        this.countdownTime = Math.max(countdownTime - seconds * 1000, 0);
    }
}
