package fr.upem.spacekaira.shape.character;


import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Draw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;

/**
 * Represent a bullet who are a little segment
 */
public class Bullet extends AbstractShape {
    /**
     * Build a new bullet with a fixed velocity
     * @param world the current world
     * @param position start position of the bullet
     * @param velocity velocity vector (will be normalize then mul by a cnst)
     * @param angle the angle a the bullet
     */
    public Bullet(World world,Vec2 position,Vec2 velocity,float angle,Brush color) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.bullet = true;

        body = world.createBody(bodyDef);
        velocity.normalize();
        velocity = velocity.mul(60);
        body.setLinearVelocity(velocity);
        body.setTransform(position,angle);

        PolygonShape polygonShape = new PolygonShape();
        Vec2[] tab = {new Vec2(0,0),new Vec2(0.1f,0),new Vec2(0.1f,1),new Vec2(0,1)};
        polygonShape.set(tab,4);

        FixtureDef bullet = new FixtureDef();
        bullet.shape = polygonShape;
        bullet.density = 1.0f;
        bullet.userData = color;

        body.createFixture(bullet);
        body.setUserData(this);
    }

    boolean isInScreen(Draw d) {
        return  d.isInScreen(body.getPosition());
    }
}
