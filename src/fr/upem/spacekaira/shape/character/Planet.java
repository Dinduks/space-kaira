package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * Represent a simple planet on the screen
 */
public class Planet extends AbstractShape {
    private static final float RADIUS = 2;

    public Planet(World world,float x, float y) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(RADIUS);

        FixtureDef fd = new FixtureDef();
        fd.shape = circleShape;
        fd.userData = new Brush(Color.RED,true);

        body.createFixture(fd);

        body.setUserData(this);
    }
}
