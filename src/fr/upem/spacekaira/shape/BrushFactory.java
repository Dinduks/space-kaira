package fr.upem.spacekaira.shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class provide a Brush factory to create easily Brush
 */
public class BrushFactory {
    private static Map<Integer,Brush> brushMap;
    static {brushMap = new HashMap<>();}

    /*create the asked Brush if there is not in the Map*/
    public Brush createBrush(Color color, boolean isOpaque) {
        Brush res = null;
        int hashCode = Brush.hashCode(color,isOpaque);
        if((res = brushMap.get(hashCode)) == null) {
            brushMap.put(hashCode,res = new Brush(color,isOpaque));
        }
        return res;
    }

    private static Random rand = new Random();
    private static Color[] colors = new Color[] { Color.BLUE, Color.CYAN,
            Color.YELLOW, Color.RED, Color.WHITE };

    private Color getRandColor() {
        return colors[rand.nextInt(colors.length - 1)];
    }

    public Brush getRandBrush(boolean isOpaque) {
        return createBrush(getRandColor(),isOpaque);
    }
}
