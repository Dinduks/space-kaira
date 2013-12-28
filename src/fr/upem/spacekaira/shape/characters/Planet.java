package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Represent a simple planet on the screen
 */
public class Planet extends AbstractShape {
    public Planet(World world, float x, float y, float radius, Brush color) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        FixtureDef planet = new FixtureDef();
        planet.shape = circleShape;
        planet.userData = color;
        planet.filter.categoryBits = FixtureType.PLANET;
        planet.filter.maskBits = FixtureType.BULLET
                                | FixtureType.SHIP
                                | FixtureType.ENEMY
                                | FixtureType.SHIELD
                                | FixtureType.BULLET_ENEMY;

        body.createFixture(planet);

        body.setUserData(this);
    }
}
