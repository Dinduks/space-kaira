package fr.upem.spacekaira.time;

import org.jbox2d.common.Timer;

public class Synchronizer {
    private Timer timer;
    private long millsTimeToWait;

    public Synchronizer(long timeToWait) {
        timer = new Timer();
        this.millsTimeToWait = timeToWait;
    }

    public void start() {
        timer.reset();
    }

    public void waitToSynchronize() {
        long millis = (long)timer.getMilliseconds();

        if (millis < millsTimeToWait) {
            try {
                Thread.sleep(millsTimeToWait - millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
