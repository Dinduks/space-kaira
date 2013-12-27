package fr.upem.spacekaira;

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

        if (args.length < 1) {
            System.err.println("Please specify a level file.");
            return;
        }

        File configurationFile = new File(args[0]);
        Configuration configuration =
                ConfigurationBuilder.buildFrom(configurationFile);

        Game game = new Game(WIDTH, HEIGHT, configuration);
        startTheGame(game);
    }

    public static void startTheGame(Game game) {
        Application.run("Space Kaïra", game.getWidth(), game.getHeight(),
        game::run);
    }
}
