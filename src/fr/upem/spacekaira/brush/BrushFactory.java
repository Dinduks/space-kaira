package fr.upem.spacekaira.brush;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This factory allows easily creating brushes
 *
 * It caches the brushes in order to create them only once
 */
public final class BrushFactory {
    private static Map<Integer, Brush> brushMap = new HashMap<>();
    private static Random random = new Random();
    private static Color[] colors = new Color[] {
            Color.BLUE, Color.CYAN, Color.YELLOW, Color.RED, Color.WHITE,
            Color.ORANGE, Color.MAGENTA, Color.GREEN, Color.PINK, Color.RED };

    private BrushFactory() {};

    /**
     * Returns a brush of the specified color
     * @param color color of the brush
     * @param isOpaque true to make opaque your draw
     * @return a instance of {@link Brush}
     */
    public static Brush get(Color color, boolean isOpaque) {
        Brush res;
        int hashCode = Brush.hashCode(color, isOpaque);
        res = brushMap.get(hashCode);
        if(res == null) {
            res = new Brush(color, isOpaque);
            brushMap.put(hashCode, res);
        }

        return res;
    }

    public static Brush get(Color color) {
        return get(color, true);
    }

    public static Brush getRandomBrush(boolean isOpaque) {
        return get(getRandomColor(), isOpaque);
    }

    private static Color getRandomColor() {
        return colors[random.nextInt(colors.length - 1)];
    }
}
