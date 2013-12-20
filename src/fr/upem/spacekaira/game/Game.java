package fr.upem.spacekaira.game;

import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.upem.spacekaira.map.Map;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.Ship;
import fr.upem.spacekaira.shape.character.collision.MpContactListener;
import fr.upem.spacekaira.time.Synchronizer;
import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * This class draw and compute the game via the run method
 */
public class Game {
    private Game () {
        throw null; // no instance
    }

    public static void run(final int HEIGHT,final int WIDTH, ApplicationContext context) {

        final float REFRESH_TIME = 1/60f;
        final int CAMERA_SCALE = 10;

        //Jbox2d World creation
        World world = new World(new Vec2(0, 0));
        world.setContactListener(new MpContactListener());
        //world.setContactFilter(new ContactFilter());

        //Init Draw class
        Draw draw = new Draw(WIDTH, HEIGHT);
        draw.setCamera(0, 0, CAMERA_SCALE);

        //Init Map
        Map map = new Map(world,draw,HEIGHT,WIDTH);
        map.initMap();

        //Create Synchronizer
        Synchronizer syn = new Synchronizer((long)(REFRESH_TIME * 1000));

        for (;;) {
            syn.start();
            checkKeyboardAction(context, map.getShip());
            world.step(REFRESH_TIME, 6, 8);
            map.computeDataGame();
            map.draw(context,draw);
            draw.setCenter(map.getShip().getPosition());
            syn.waitToSynchronize();
        }
    }

    private static void checkKeyboardAction(ApplicationContext context,Ship s) {
        for (KeyboardEvent ke; (ke =context.pollKeyboard()) != null;) {
            switch (ke.getKey()) {
                case UP: s.up();
                    break;
                case LEFT: s.left();
                    break;
                case RIGHT: s.right();
                    break;
                case SPACE : s.shoot();
                    break;
                case B : s.bomb();
                    break;
                case S: s.shield();
                    break;
                default :
                    break;
            }
        }
    }
}
