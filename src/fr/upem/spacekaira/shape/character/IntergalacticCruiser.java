package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class IntergalacticCruiser extends Enemy {

    public IntergalacticCruiser(World world, float x, float y, Brush color,
                                Brush bulletColor) {
        super(color, bulletColor);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef cruiserFix = new FixtureDef();
        cruiserFix.density = 1.0f;
        cruiserFix.userData = enemyColor;
        cruiserFix.filter.categoryBits = FixtureType.STD_ENEMY;
        cruiserFix.filter.maskBits = FixtureType.BULLET
                | FixtureType.SHIP
                | FixtureType.PLANET;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(new Vec2[]{ new Vec2(0, 0), new Vec2(0, 2),
                new Vec2(7, 2), new Vec2(7, 0) }, 4);

        cruiserFix.shape = polygonShape;
        body.createFixture(cruiserFix);
    }

    private void shootUp() {
        bullets.add(Bullet.createEnemyBullet(
                body.getWorld(),
                body.getWorldPoint(new Vec2(3.5f, 4)),
                body.getWorldVector(new Vec2(0, 1)),
                body.getAngle(),
                bulletColor));
    }

    private void shootDown() {
        bullets.add(Bullet.createEnemyBullet(body.getWorld(),
                body.getWorldPoint(new Vec2(3.5f, -2)),
                body.getWorldVector(new Vec2(0, -1)),
                body.getAngle(),
                bulletColor));
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }

    @Override
    public void shoot(Ship ship) {
        Vec2 upCanon = body.getWorldVector(new Vec2(0, 1));
        Vec2 downCanon = body.getWorldVector(new Vec2(0, -1));

        Vec2 shipIc = ship.getPosition().sub(body.getPosition());
        shipIc.normalize();

        if (upCanon.sub(shipIc).length() <= 0.05f) shootUp();
        else if (downCanon.sub(shipIc).length() <= 0.05f) shootDown();
    }

    @Override
    public void move(Ship ship) {
        Vec2 speed = computeFollowingSpeed(ship.getPosition(),
                body.getPosition(),
                ship.getLinearVelocity(),
                body.getLinearVelocity(),
                0.5f);
        body.setLinearVelocity(speed);
        body.setAngularVelocity(2);
    }
}