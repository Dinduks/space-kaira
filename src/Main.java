import fr.upem.spacekaira.game.Game;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.Planet;
import fr.upem.spacekaira.shape.character.Ship;
import fr.upem.spacekaira.time.Synchronizer;
import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.xml.transform.sax.SAXSource;

public class    Main {
    public static void main(String[] args) {
        final int WIDTH = 800;
        final int HEIGHT = 600;

        Application.run("Master Pilot" , WIDTH, HEIGHT, context -> {
            Game.run(HEIGHT, WIDTH, context);
        });
    }

}
