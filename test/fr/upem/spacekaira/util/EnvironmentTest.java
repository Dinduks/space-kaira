package fr.upem.spacekaira.util;

import static junit.framework.Assert.*;

import fr.upem.spacekaira.game.Environment;
import org.junit.Test;

public class EnvironmentTest {
    @Test
    public void testGetTimeLeftAsString() {
        long startTime;
        int gameDuration;

        startTime = System.currentTimeMillis();
        gameDuration = 0;
        assertEquals("0:00", Environment.getTimeLeftAsString(startTime, gameDuration));

        gameDuration = 12;
        startTime = System.currentTimeMillis() - 5 * 1000;
        assertEquals("0:07", Environment.getTimeLeftAsString(startTime, gameDuration));

        gameDuration = 60;
        startTime = System.currentTimeMillis() - 60 * 1000;
        assertEquals("0:00", Environment.getTimeLeftAsString(startTime, gameDuration));

        gameDuration = 60;
        startTime = System.currentTimeMillis() - 59 * 1000;
        assertEquals("0:01", Environment.getTimeLeftAsString(startTime, gameDuration));

        gameDuration = 120;
        startTime = System.currentTimeMillis() - 60 * 1000;
        assertEquals("1:00", Environment.getTimeLeftAsString(startTime, gameDuration));

        gameDuration = 120;
        startTime = System.currentTimeMillis() - 30 * 1000;
        assertEquals("1:30", Environment.getTimeLeftAsString(startTime, gameDuration));
    }

    @Test
    public void testGetTimeLeftAsLong() {
        long startTime;
        int gameDuration;

        startTime = System.currentTimeMillis();
        gameDuration = 0;
        assertEquals(0, Environment.getTimeLeftAsLong(startTime, gameDuration));

        gameDuration = 12;
        startTime = System.currentTimeMillis() - 5 * 1000;
        assertEquals(7, Environment.getTimeLeftAsLong(startTime, gameDuration));

        gameDuration = 60;
        startTime = System.currentTimeMillis() - 60 * 1000;
        assertEquals(0, Environment.getTimeLeftAsLong(startTime, gameDuration));

        gameDuration = 60;
        startTime = System.currentTimeMillis() - 59 * 1000;
        assertEquals(1, Environment.getTimeLeftAsLong(startTime, gameDuration));

        gameDuration = 120;
        startTime = System.currentTimeMillis() - 60 * 1000;
        assertEquals(60, Environment.getTimeLeftAsLong(startTime, gameDuration));

        gameDuration = 120;
        startTime = System.currentTimeMillis() - 30 * 1000;
        assertEquals(90, Environment.getTimeLeftAsLong(startTime, gameDuration));
    }
}
