package fr.upem.spacekaira.shape;

import fr.umlv.zen3.ApplicationContext;

import java.awt.*;

public class DrawHelpers {
    public static void drawGameOver(ApplicationContext context) {
        context.render((graphics) -> {
            Font font;
            graphics.setPaint(new Color(255, 0, 0));

            font = new Font("arial", Font.BOLD, 60);
            graphics.setFont(font);
            graphics.drawString("GAME OVER", 200, 200);

            font = new Font("arial", Font.BOLD, 20);
            graphics.setFont(font);
            graphics.drawString("Press Q to quit.", 200, 250);
        });
    }
}
