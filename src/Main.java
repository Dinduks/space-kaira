import fr.umlv.zen.Application;
import fr.upem.spacekaira.MainScreen;

import java.awt.*;

public class Main {
    final static int WIDTH = 800;
    final static int HEIGHT = 600;

    public static void main(String[] args) {
        Application.run("Space Kaira", WIDTH, HEIGHT, (context) -> {
            context.render((graphics) -> graphics.setBackground(Color.black));
            (new MainScreen(context)).start();
        });
    }
}
