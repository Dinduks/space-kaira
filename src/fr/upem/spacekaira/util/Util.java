package fr.upem.spacekaira.util;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Util {
    private Util() {}

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
