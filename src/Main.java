import fr.umlv.zen.Application;
import fr.upem.spacekaira.screen.MainScreen;

public class Main {
    final static int WIDTH = 800;
    final static int HEIGHT = 600;

    public static void main(String[] args) {
        Application.run("Space Kaira", WIDTH, HEIGHT, (context) -> {
            (new MainScreen(context, WIDTH, HEIGHT)).start();
        });
    }
}
