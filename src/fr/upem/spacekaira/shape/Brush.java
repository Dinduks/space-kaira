package fr.upem.spacekaira.shape;

import java.awt.*;

public class Brush {
    private final boolean draw;
    private final Color color;
    private final boolean isOpaque;

    public Brush(boolean draw, Color color, boolean isOpaque) {
        this.draw = draw;
        this.color = color;
        this.isOpaque = isOpaque;
    }

    public boolean isDraw() {
        return draw;
    }

    public Color getColor() {
        return color;
    }

    public boolean isOpaque() {
        return isOpaque;
    }
}
