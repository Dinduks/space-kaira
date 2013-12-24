package fr.upem.spacekaira.shape.character;


import fr.upem.spacekaira.shape.*;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a bomb
 */
public class Bomb extends AbstractShape implements DynamicContact {
    /**
     * Build A new bomb
     * @param world The current world
     * @param position The position of the bomb
     * @param color
     */
    public Bomb(World world, Vec2 position, Brush color) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        FixtureDef bombFixtureDef = new FixtureDef();
        bombFixtureDef.shape = circleShape;
        bombFixtureDef.density = 1.0f;
        bombFixtureDef.userData = color;
        bombFixtureDef.filter.categoryBits = FixtureType.BOMB;
        bombFixtureDef.filter.maskBits = FixtureType.SHIP;

        body.createFixture(bombFixtureDef);
        body.setUserData(this);
    }

    @Override
    public void computeTimeStepData() {
    }

    @Override
    public boolean isDead() {
        return body.getFixtureList().getUserData() == Brush.DESTROY_BRUSH;
    }
}
