package vaultescape.ui;

public class Timer {
    private long startTime;
    private long countdownTime;
    public Timer(long seconds){
        this.countdownTime = seconds * 1000;
        start();
    }
    public void start(){
        startTime = System.currentTimeMillis();
    }
    public long getTimeLeft() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long timeLeft = countdownTime - elapsedTime;
        return Math.max(timeLeft, 0);  // Ensure time left doesn't go below 0
    }

    public String getFormattedTimeLeft() {
        long seconds = getTimeLeft() / 1000;
        return String.format(seconds + " s");  // Format as MM:SS
    }

    public boolean isTimeUp() {
        return getTimeLeft() <= 0;  
    }
}
