package fr.upem.spacekaira.util;

import org.jbox2d.common.Vec2;

public class Util {
    /**
     * @param startTime Start time in milliseconds
     * @param gameDuration Game duration in seconds
     * @return Example: for 1m5s, return 1:05
     */
    public static String getTimeLeftAsString(long startTime, int gameDuration) {
        long currentTime = (long) Math.floor(System.currentTimeMillis() / 1000);
        startTime = (long) Math.floor(startTime / 1000);

        int timeLeft = (int) (gameDuration - (currentTime - startTime));
        int minutes = (timeLeft / 60);
        int seconds = timeLeft % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

    /**
     * @param startTime Start time in milliseconds
     * @param gameDuration Game duration in seconds
     * @return Example: for 1m10s, returns 70
     */
    public static long getTimeLeftAsLong(long startTime, int gameDuration) {
        return (gameDuration * 1000 - System.currentTimeMillis() + startTime) / 1000;
    }

    public static boolean anyTimeLeft(long startTime, int gameDuration) {
        return System.currentTimeMillis() <= startTime + gameDuration * 1000;
    }

    /**
     * Computes the distance between two vectors
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The distance between the specified vectors
     */
    public static float distanceBetweenVectors(Vec2 v1, Vec2 v2) {
        float x = v1.x - v2.x;
        float y = v1.y - v2.y;
        return (float) Math.sqrt(x * x + y * y);
    }
}
