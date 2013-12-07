package fr.upem.spacekaira.shape;

import fr.umlv.zen3.ApplicationContext;

import java.awt.*;

@FunctionalInterface
public interface Drawable {
    public void draw(Graphics2D graphics,Draw d);
}
