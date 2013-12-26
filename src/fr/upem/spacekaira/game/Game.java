package fr.upem.spacekaira.game;

import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;
import fr.upem.spacekaira.Main;
import fr.upem.spacekaira.config.Configuration;
import fr.upem.spacekaira.map.Map;
import fr.upem.spacekaira.shape.DrawHelpers;
import fr.upem.spacekaira.shape.Viewport;
import fr.upem.spacekaira.shape.character.Ship;
import fr.upem.spacekaira.shape.character.collision.MpContactListener;
import fr.upem.spacekaira.time.Synchronizer;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.HashSet;

/**
 * This class draw and compute the game via the run method
 */
public class Game {
    private final int width;

    private final int height;
    private final Configuration config;

    private HashSet<KeyboardKey> keys = new HashSet<>();
    private long lastTimeWasShieldToggled = 0;

    public Game(int width, int height, Configuration config) {
        this.width = width;
        this.height = height;
        this.config = config;
    }

    public void run(ApplicationContext context) {
        final float REFRESH_TIME = 1 / 60f;
        final int CAMERA_SCALE = 10;

        //Jbox2d World creation
        World world = new World(new Vec2(0, 0));
        world.setContactListener(new MpContactListener());

        //Init Viewport class
        Viewport viewport = new Viewport(width, height);
        viewport.setCamera(0, 0, CAMERA_SCALE);

        //Init Map
        Map map = new Map(world, viewport, height, width, config);
        map.initMap();

        Synchronizer syn = new Synchronizer((long)(REFRESH_TIME * 1000));

        long startTime = System.currentTimeMillis();

        while (Util.anyTimeLeft(startTime, config.getGameDuration())) {
            syn.start();
            handleKeyboardEvents(context, map.getShip());
            world.step(REFRESH_TIME, 6, 8);
            map.computeDataGame();
            map.draw(context, viewport, startTime, config.getGameDuration());
            viewport.setCenter(map.getShip().getPosition());
            syn.waitToSynchronize();
        }

        DrawHelpers.drawGameOver(context);
        waitForPlayerDecision(context);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Used in the GAME OVER screen, waits for the user to press the Q key to
     * quit, or the R key to restart
     * @param context
     */
    private void waitForPlayerDecision(ApplicationContext context) {
        while (true) {
            KeyboardEvent event = context.pollKeyboard();
            if (event == null) continue;
            if (event.getKey() == KeyboardKey.Q) {
                System.exit(0);
            } else if (event.getKey() == KeyboardKey.R) {
                Game game = new Game(width, height, config);
                Main.startTheGame(game);
            }
        }
    }

    /**
     * Stores pressed keys in the {@code keys} {@link java.util.HashSet}, and
     * removes the released ones from the same collection.
     * In the end, handles the key presses.
     * @param context
     * @param ship
     */
    private void handleKeyboardEvents(ApplicationContext context, Ship ship) {
        KeyboardEvent keyboardEvent;

        keyboardEvent = context.pollKeys();
        if (keyboardEvent != null) {
            if (keyboardEvent.isReleased()) {
                keys.remove(keyboardEvent.getKey());
            } else {
                keys.add(keyboardEvent.getKey());
            }
        }

        keys.forEach(key -> handleKeyPress(key, ship));
    }

    private void handleKeyPress(KeyboardKey key, Ship ship) {
        switch (key) {
            case UP:
                ship.up();
                break;
            case LEFT:
                ship.left();
                break;
            case RIGHT:
                ship.right();
                break;
            case SPACE:
                ship.shoot();
                break;
            case B:
                ship.dropBomb();
                break;
            case S:
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTimeWasShieldToggled >= 300) {
                    ship.toggleShield();
                    lastTimeWasShieldToggled = currentTime;
                }
                break;
            default:
                break;
        }
    }
}
