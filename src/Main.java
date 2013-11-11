import fr.umlv.zen.Application;

public class Main {
    public static void main(String[] args) {
        final int WIDTH = 800;
        final int HEIGHT = 600;

        Application.run("Space Kaira", WIDTH, HEIGHT, (context) -> context.render((graphics) -> {
        }));
    }
}
