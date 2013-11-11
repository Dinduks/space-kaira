package fr.upem.spacekaira;

import java.awt.*;

public class DrawingHelpers {
    public static void drawStringWithColor(String string, int x, int y,
                                           Graphics2D graphics, Color color) {
        Color initialColor = (Color) graphics.getPaint();
        graphics.setPaint(color);
        graphics.drawString(string, x, y);
        graphics.setPaint(initialColor);
    }
}
