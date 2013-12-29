package fr.upem.spacekaira.shape;

import java.awt.*;

/**
 * Holds information about a color and its opacity
 */
public class Brush {
    private final Color color;
    private final boolean isOpaque;
    /* Brush that says that a fixture should die in the next step */
    public static final Brush DESTROY_BRUSH = new Brush(Color.PINK, false);

    Brush(Color color, boolean isOpaque) {
        this.color = color;
        this.isOpaque = isOpaque;
    }

    public Color getColor() { return color; }

    public boolean isOpaque() {
        return isOpaque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brush brush = (Brush) o;

        if (isOpaque != brush.isOpaque) return false;
        if (color != null ? !color.equals(brush.color) : brush.color != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Brush.hashCode(color,isOpaque);
    }

    public static int hashCode(Color color,boolean isOpaque) {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (isOpaque ? 1 : 0);
        return result;
    }
}
