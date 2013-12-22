package fr.upem.spacekaira.game;

import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;
import fr.upem.spacekaira.map.Map;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.Ship;
import fr.upem.spacekaira.shape.character.collision.MpContactListener;
import fr.upem.spacekaira.time.Synchronizer;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * This class draw and compute the game via the run method
 */
public class Game {
    // TODO: Remove this
    private Game () {
        throw null; // no instance
    }

    public static void run(final int height,
                           final int width,
                           ApplicationContext context,
                           int gameDuration) {
        final float REFRESH_TIME = 1/60f;
        final int CAMERA_SCALE = 10;

        //Jbox2d World creation
        World world = new World(new Vec2(0, 0));
        world.setContactListener(new MpContactListener());
        //world.setContactFilter(new ContactFilter());

        //Init Draw class
        Draw draw = new Draw(width, height);
        draw.setCamera(0, 0, CAMERA_SCALE);

        //Init Map
        Map map = new Map(world, draw, height, width);
        map.initMap();

        Synchronizer syn = new Synchronizer((long)(REFRESH_TIME * 1000));

        long startTime = System.currentTimeMillis();

        while (Util.anyTimeLeft(startTime, gameDuration)) {
            syn.start();
            checkKeyboardAction(context, map.getShip());
            world.step(REFRESH_TIME, 6, 8);
            map.computeDataGame();
            map.draw(context, draw, startTime, gameDuration);
            draw.setCenter(map.getShip().getPosition());
            syn.waitToSynchronize();
        }

        draw.drawGameOver(context);
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

    private static void checkKeyboardAction(ApplicationContext context,Ship s) {
        for (KeyboardEvent ke; (ke = context.pollKeyboard()) != null; ) {
            switch (ke.getKey()) {
                case UP: s.up();
                    break;
                case LEFT: s.left();
                    break;
                case RIGHT: s.right();
                    break;
                case SPACE: s.shoot();
                    break;
                case B: s.bomb();
                    break;
                case S: s.shield();
                    break;
                default :
                    break;
            }
        }
    }
}
