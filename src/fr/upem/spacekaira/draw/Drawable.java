package fr.upem.spacekaira.draw;

import fr.upem.spacekaira.game.Viewport;

import java.awt.*;

/**
 * Drawable interface used by all figure an the screen
 */
@FunctionalInterface
public interface Drawable {
    void draw(Graphics2D graphics, Viewport viewport);
}