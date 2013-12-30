package fr.upem.spacekaira;

import fr.umlv.zen3.Application;
import fr.upem.spacekaira.config.Configuration;
import fr.upem.spacekaira.config.ConfigurationLoader;
import fr.upem.spacekaira.config.ConfigurationParsingException;
import fr.upem.spacekaira.game.Game;

import java.io.File;

public class Main {
    public static void main(String[] args)
            throws ConfigurationParsingException {
        if (args.length == 0) {
            System.err.println("Please specify a level file.");
            return;
        }

        File configurationFile = new File(args[0]);
        Configuration config = ConfigurationLoader.loadFrom(configurationFile);

        if (args.length > 1 && args[1].equals("hardcore")) {
            config.setHardcore(true);
        }

        Game game = new Game(config);
        startTheGame(game);
    }

    public static void startTheGame(Game game) {
        Application.run("Space Kaïra", game.getWidth(), game.getHeight(),
        game::run);
    }
}
