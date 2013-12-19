package fr.upem.spacekaira.shape;

import java.awt.*;

/**
 * Contains data to draw a shape
 */
public class Brush {
    private final Color color;
    private final boolean isOpaque;

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
