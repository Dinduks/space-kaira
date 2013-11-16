package fr.upem.spacekaira.draw;

import java.awt.*;

/**
 * A set of helpers for drawing stuff
 */
public class DrawingHelpers {
    public static void drawStringWithColor(String string, int x, int y,
                                           Graphics2D graphics, Color color) {
        Color initialColor = (Color) graphics.getPaint();
        graphics.setPaint(color);
        graphics.drawString(string, x, y);
        graphics.setPaint(initialColor);
    }
}
