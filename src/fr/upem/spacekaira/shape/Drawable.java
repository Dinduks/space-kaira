package fr.upem.spacekaira.shape;

import java.awt.*;

@FunctionalInterface
public interface Drawable {
    public void draw(Graphics2D graphics,Draw d);
}
