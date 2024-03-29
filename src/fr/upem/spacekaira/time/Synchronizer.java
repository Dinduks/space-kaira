package fr.upem.spacekaira.time;

import org.jbox2d.common.Timer;

/**
 * This classe provides methods to execute a loop with a fixed frequency
 */
public class Synchronizer {
    private Timer timer;
    private long millsTimeToWait;

    public Synchronizer(long timeToWait) {
        timer = new Timer();
        this.millsTimeToWait = timeToWait;
    }

    // Should be called at the start of the loop
    public void start() {
        timer.reset();
    }

    // Should e called at the end to wait the delta time
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
