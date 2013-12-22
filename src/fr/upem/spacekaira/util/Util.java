package fr.upem.spacekaira.util;

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
}
