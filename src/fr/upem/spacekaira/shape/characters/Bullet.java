package fr.upem.spacekaira.shape.characters;


import fr.upem.spacekaira.shape.*;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Iterator;
import java.util.List;

/**
 * Represent a bullet which is a little segment
 */
public class Bullet extends ShapeWithDynamicContact {
    /**
     * Builds a new bullet with a fixed velocity
     *
     * @param world        The current world
     * @param position     Position of the bullet
     * @param velocity     Velocity vector (will be normalized then mul by a const)
     * @param angle        The angle a the bullet
     * @param brush        The brush of the bullet
     * @param categoryBits The collision category bits
     */
    private Bullet(World world, Vec2 position, Vec2 velocity, float angle,
                   Brush brush, int categoryBits) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.bullet = true;

        setBody(world.createBody(bodyDef));
        Vec2 newVelocity = velocity.clone();
        newVelocity.normalize();
        getBody().setLinearVelocity(velocity.mul(1000));
        getBody().setTransform(position, angle);

        PolygonShape polygonShape = new PolygonShape();
        Vec2[] tab = {new Vec2(0,-0.5f),new Vec2(0.1f,-0.5f),new Vec2(0.1f,0.5f),new Vec2(0,0.5f)};
        polygonShape.set(tab,4);

        FixtureDef bullet = new FixtureDef();
        bullet.shape = polygonShape;
        bullet.density = 0.0000000000000000000000001f; // 0 doesn't "work"
        bullet.userData = brush;
        bullet.filter.categoryBits = categoryBits;
        bullet.filter.maskBits = FixtureType.PLANET |
                FixtureType.ENEMY |
                FixtureType.SHIP |
                FixtureType.SHIELD;

        getBody().createFixture(bullet);
        getBody().setUserData(this);
    }

    public static Bullet createShipBullet(World world, Vec2 position,
                                          Vec2 velocity, float angle,
                                          Brush brush) {
        return new Bullet(world, position, velocity, angle, brush,
                FixtureType.BULLET);
    }

    public static Bullet createEnemyBullet(World world, Vec2 position,
                                           Vec2 velocity, float angle,
                                           Brush brush) {
        return new Bullet(world, position, velocity, angle, brush,
                FixtureType.BULLET_ENEMY);
    }

    boolean isInScreen(Viewport viewport) {
        return  viewport.isInScreen(getBody().getPosition());
    }

    public static void checkForBulletsOutScreen(Viewport viewport,
                                                List<Bullet> bullets) {
        Iterator<Bullet> it = bullets.iterator();

        while(it.hasNext()) {
            Bullet b = it.next();
            if(!b.isInScreen(viewport)) {
                b.destroy();
                it.remove();
            }
        }
    }

    public static void checkForDeadBullet(List<Bullet> bullets) {
        Iterator<Bullet> it = bullets.iterator();

        while(it.hasNext()) {
            Bullet b = it.next();
            if(b.isDead()) {
                b.destroy();
                it.remove();
            }
        }
    }

    @Override
    public void computeTimeStepData() {
    }
}
