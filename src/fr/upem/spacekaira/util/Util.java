package fr.upem.spacekaira.util;

public class Util {
    public static String makeTimeCounter(long startTime) {
        long currentTime = (long) Math.floor(System.currentTimeMillis() / 1000);
        startTime = (long) Math.floor(startTime / 1000);

        int counterTime = (int) (currentTime - startTime);
        int minutes = (counterTime / 60);
        int seconds = counterTime % 60;

        return String.format("%d:%02d", minutes, seconds);
    }
}
