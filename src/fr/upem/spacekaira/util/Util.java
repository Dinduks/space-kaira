package fr.upem.spacekaira.util;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Util {
    private Util() {}

    /**
     * @param startTime    Start time in milliseconds
     * @param gameDuration Game duration in seconds
     * @return             Time left — Example: for 1m5s, return 1:05
     */
    public static String getTimeLeftAsString(long startTime, int gameDuration) {
        long currentTime = (long) Math.floor(System.currentTimeMillis() / 1000);
        Long startTimeInSecs = (long) Math.floor(startTime / 1000);

        int timeLeft = (int) (gameDuration - (currentTime - startTimeInSecs));
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

    /**
     * Builds a single iterator from the iterators of all passed lists
     * @param lists A list of lists whose iterators will be "merged"
     * @param <E> class of element in the list
     * @return An iterator that allows to iterator over all the lists
     */
     public static <E> Iterator<E> asIterator(List<List<E>> lists) {
         List<Iterator<E>> iterators = new ArrayList<>(lists.size());
         for(List<E> list : lists) {
             if(list != null) iterators.add(list.iterator());
         }

         return new Iterator<E> () {
             private int current=0;
             public boolean hasNext() {
                 while (current < iterators.size() &&
                         !iterators.get(current).hasNext()) {
                     current++;
                 }

                 return current < iterators.size();
             }

             public E next() {
                 while (current < iterators.size() &&
                         !iterators.get(current).hasNext()) {
                     current++;
                 }

                 return iterators.get(current).next();
             }
         };
    }
}
