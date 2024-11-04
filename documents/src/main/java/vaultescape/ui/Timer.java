package vaultescape.ui;

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
    public Timer(long seconds) {
        this.countdownTime = seconds * 1000;
        start();
    }

    /**
     * Starts or restarts the timer by setting the start time to the current system time.
     */
    public void start() {
        startTime = System.currentTimeMillis();
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
     * Retrieves the formatted time left in the countdown as a string in seconds.
     *
     * @return a formatted string of the remaining time in seconds
     */
    public String getFormattedTimeLeft() {
        long seconds = getTimeLeft() / 1000;
        return String.format(seconds + " s");
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
        countdownTime = Math.max(countdownTime - seconds * 1000, 0);
    }
}
