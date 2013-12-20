package fr.upem.spacekaira.shape;

import java.awt.*;

/**
 * Contains data to draw a shape
 */
public class Brush {
    private final Color color;
    private final boolean isOpaque;
    /*Constant Brush who mark that a fixture should die a next time step*/
    public static final Brush DESTROY_BRUSH;
    static {
        DESTROY_BRUSH = new Brush(Color.BLACK,false);}

    public Brush(Color color, boolean isOpaque) {
        this.color = color;
        this.isOpaque = isOpaque;
    }

    public Color getColor() {
        return color;
    }

    public boolean isOpaque() {
        return isOpaque;
    }
}
