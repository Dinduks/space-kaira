package fr.upem.spacekaira.shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
}
