package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.brush.Brush;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Represents a planet on the screen
 */
public class Planet extends AbstractShape {
    public Planet(World world, float x, float y, float radius, Brush brush) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);

        setBody(world.createBody(bodyDef));

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        FixtureDef planet = new FixtureDef();
        planet.shape = circleShape;
        planet.userData = brush;
        planet.filter.categoryBits = FixtureType.PLANET;
        planet.filter.maskBits = FixtureType.BULLET
                                | FixtureType.SHIP
                                | FixtureType.ENEMY
                                | FixtureType.SHIELD
                                | FixtureType.BULLET_ENEMY;

        getBody().createFixture(planet);
        getBody().setUserData(this);
    }
}
