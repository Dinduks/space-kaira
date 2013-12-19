package fr.upem.spacekaira.shape;

import java.awt.*;

/**
 * Drawable interface used by all figure an the screen
 */
@FunctionalInterface
public interface Drawable {
    public void draw(Graphics2D graphics,Draw d);
}
