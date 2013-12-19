package fr.upem.spacekaira.shape;

import fr.umlv.zen3.ApplicationContext;

import java.awt.*;

/**
 * Drawable interface used by all figure an the screen
 */
@FunctionalInterface
public interface Drawable {
    public void draw(Graphics2D graphics,Draw d);
}
