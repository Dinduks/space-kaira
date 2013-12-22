package fr.upem.spacekaira.util;

public class Util {
    public static String getTimeLeftAsString(long startTime, long gameDuration) {
        long currentTime = (long) Math.floor(System.currentTimeMillis() / 1000);
        startTime = (long) Math.floor(startTime / 1000);

        int timeLeft = (int) (gameDuration - (currentTime - startTime));
        int minutes = (timeLeft / 60);
        int seconds = timeLeft % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

    public static long getTimeLeftAsLong(long startTime, int gameDuration) {
        return (gameDuration * 1000 - System.currentTimeMillis() + startTime) / 1000;
    }

    public static boolean anyTimeLeft(long startTime, int gameDuration) {
        return System.currentTimeMillis() <= startTime + gameDuration * 1000;
    }
}
