import fr.umlv.zen3.Application;
import fr.upem.spacekaira.config.Configuration;
import fr.upem.spacekaira.config.ConfigurationBuilder;
import fr.upem.spacekaira.config.ConfigurationParsingException;
import fr.upem.spacekaira.game.Game;

import java.io.File;

public class Main {
    public static void main(String[] args)
            throws ConfigurationParsingException {
        final int WIDTH = 800;
        final int HEIGHT = 600;

        /*File configurationFile = new File(args[0]);
        Configuration configuration =
                ConfigurationBuilder.buildFrom(configurationFile);*/

        Application.run("Space Kaïra" , WIDTH, HEIGHT, context -> {
            Game.run(HEIGHT, WIDTH, context);
        });
    }

}
