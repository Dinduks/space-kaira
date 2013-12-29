package fr.upem.spacekaira.shape;

import java.awt.*;

/**
 * Drawable interface used by all figure an the screen
 */
@FunctionalInterface
public interface Drawable {
    void draw(Graphics2D graphics, Viewport viewport);
}