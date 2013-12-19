import fr.umlv.zen3.Application;
import fr.upem.spacekaira.game.Game;

public class    Main {
    public static void main(String[] args) {
        final int WIDTH = 800;
        final int HEIGHT = 600;

        Application.run("Master Pilot" , WIDTH, HEIGHT, context -> {
            Game.run(HEIGHT, WIDTH, context);
        });
    }

}
