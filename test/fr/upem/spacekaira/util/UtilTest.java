package fr.upem.spacekaira.util;

import static junit.framework.Assert.*;

import fr.upem.spacekaira.shape.Draw;
import org.junit.Test;

public class UtilTest {
    @Test
    public void testMakeTimeCounter() {
        Draw draw = new Draw(0, 0);
        long startTime;

        startTime = System.currentTimeMillis();
        assertEquals("0:00", Util.makeTimeCounter(startTime));

        startTime = System.currentTimeMillis() - 1 * 1000;
        assertEquals("0:01", Util.makeTimeCounter(startTime));

        startTime = System.currentTimeMillis() - 11 * 1000;
        assertEquals("0:11", Util.makeTimeCounter(startTime));

        startTime = System.currentTimeMillis() - 60 * 1000;
        assertEquals("1:00", Util.makeTimeCounter(startTime));

        startTime = System.currentTimeMillis() - 90 * 1000;
        assertEquals("1:30", Util.makeTimeCounter(startTime));
    }
}
