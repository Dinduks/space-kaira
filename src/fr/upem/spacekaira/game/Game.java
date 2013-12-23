package fr.upem.spacekaira.game;

import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;
import fr.upem.spacekaira.map.Map;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.DrawHelpers;
import fr.upem.spacekaira.shape.Viewport;
import fr.upem.spacekaira.shape.character.Bomb;
import fr.upem.spacekaira.shape.character.Bullet;
import fr.upem.spacekaira.shape.character.Ship;
import fr.upem.spacekaira.shape.character.collision.MpContactListener;
import fr.upem.spacekaira.time.Synchronizer;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class draw and compute the game via the run method
 */
public class Game {
    public static void run(final int height,
                           final int width,
                           ApplicationContext context,
                           int gameDuration,
                           int planetsDensity,
                           int bombsFrequency) {
        final float REFRESH_TIME = 1 / 60f;
        final int CAMERA_SCALE = 10;

        //Jbox2d World creation
        World world = new World(new Vec2(0, 0));
        world.setContactListener(new MpContactListener());

        //Init Viewport class
        Viewport viewport = new Viewport(width, height);
        viewport.setCamera(0, 0, CAMERA_SCALE);

        //Init Map
        Map map = new Map(world, viewport, height,
                          width, planetsDensity, bombsFrequency);
        map.initMap();

        Synchronizer syn = new Synchronizer((long)(REFRESH_TIME * 1000));

        long startTime = System.currentTimeMillis();

        while (Util.anyTimeLeft(startTime, gameDuration)) {
            syn.start();
            handleKeyboardEvents(context, map.getShip());
            world.step(REFRESH_TIME, 6, 8);
            map.computeDataGame();
            map.draw(context, viewport, startTime, gameDuration);
            viewport.setCenter(map.getShip().getPosition());
            syn.waitToSynchronize();
        }

        DrawHelpers.drawGameOver(context);
        waitForExitRequest(context);
    }

    /**
     * Used in the GAME OVER screen, waits for the user to press the Q key
     * @param context
     */
    private static void waitForExitRequest(ApplicationContext context) {
        while (true) {
            KeyboardEvent event = context.pollKeyboard();
            if (event != null && event.getKey() == KeyboardKey.Q) {
                System.exit(0);
            }
        }
    }

    private static HashSet<KeyboardKey> keys = new HashSet<>();

    /**
     * Stores pressed keys in the {@code keys} {@link java.util.HashSet}, and
     * removes the released ones from the same collection.
     * In the end, handles the key presses.
     * @param context
     * @param ship
     */
    private static void handleKeyboardEvents(ApplicationContext context,
                                             Ship ship) {
        KeyboardEvent keyboardEvent;

        keyboardEvent = context.pollKeyboard();
        if (keyboardEvent != null) keys.add(keyboardEvent.getKey());
        keyboardEvent = context.pollReleasedKeys();
        if (keyboardEvent != null) keys.remove(keyboardEvent.getKey());

        keys.forEach(key -> handleKeyPress(key, ship));
    }

    private static long lastTimeWasShieldToggled = 0;
    private static void handleKeyPress(KeyboardKey key, Ship ship) {
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
                ship.bomb();
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
