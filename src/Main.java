import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.Planet;
import fr.upem.spacekaira.shape.Ship;
import fr.upem.spacekaira.time.Synchronizer;
import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Main {
    public static void main(String[] args) {
        int WIDTH = 800;
        int HEIGHT = 600;

        Application.run("Colors", WIDTH, HEIGHT, context -> {
            World world = new World(new Vec2(0, 0));

            Ship s = new Ship(world);

            Planet planet1 = new Planet(world, 2, 2);
            Planet planet2 = new Planet(world, 10, 30);
            Planet planet3 = new Planet(world, 10, 100);

            Draw dr = new Draw(WIDTH, HEIGHT);
            dr.setCamera(0, 0, 10);

            Synchronizer syn = new Synchronizer(16);

            for (;;) {
                syn.start();

                KeyboardEvent ke = context.pollKeyboard();

                if (ke != null) {
                    switch (ke.getKey()) {
                        case UP: s.up();
                            break;
                        case LEFT: s.left();
                            break;
                        case RIGHT: s.right();
                            break;
                        case SPACE :
                            break;
                        case S: s.shield();
                            break;
                        default :
                            break;
                    }
                }

                world.step(1f/60, 6, 8);

                context.render(graphics -> {
                    graphics.clearRect(0, 0, WIDTH, HEIGHT);
                    s.draw(graphics, dr);
                    planet1.draw(graphics, dr);
                    planet2.draw(graphics, dr);
                    planet3.draw(graphics, dr);

                });

                dr.setCenter(s.getPosition());

                syn.waitToSynchronize();
            }
        });
    }

}
