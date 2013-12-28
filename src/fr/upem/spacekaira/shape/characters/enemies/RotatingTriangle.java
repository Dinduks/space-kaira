package fr.upem.spacekaira.shape.characters.enemies;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class RotatingTriangle extends Enemy {

    public RotatingTriangle(World world, float x, float y, Brush brush,
                            Brush bulletColor) {
        super(brush, bulletColor);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef triangleFix = new FixtureDef();
        triangleFix.density = 1.0f;
        triangleFix.userData = enemyColor;
        triangleFix.filter.categoryBits = FixtureType.ENEMY;
        triangleFix.filter.maskBits = FixtureType.BULLET
                | FixtureType.SHIP
                | FixtureType.ARMED_BOMB
                | FixtureType.ARMED_MBOMB
                | FixtureType.PLANET
                | FixtureType.ENEMY;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(new Vec2[]{ new Vec2(-1, -0.86f), new Vec2(1, -0.86f),
                new Vec2(0, 0.86f)},3);

        triangleFix.shape = polygonShape;
        body.createFixture(triangleFix);
    }

    @Override
    public void move(Ship ship) {
        Vec2 speed = computeFollowingSpeed(ship.getPosition(),
                body.getPosition(),
                ship.getLinearVelocity(),
                body.getLinearVelocity(),
                0.3f);
        body.setLinearVelocity(speed.mul(0.97f));
        body.setAngularVelocity(2);
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }

    private void shoot1() {
        addBulletToShootLocal(body.getWorldPoint(new Vec2(-1.2f, -1.03f)),new Vec2(-1, -0.86f), body.getAngle() + 2.1f);
    }

    private void shoot2() {
        addBulletToShootLocal(new Vec2(1.2f, -1.03f), new Vec2(1, -0.86f), body.getAngle() - 2.1f);
    }

    private void shoot3() {
        addBulletToShootLocal(new Vec2(0.0f, 1.03f), new Vec2(0, 0.86f), body.getAngle());
    }

    private long lastShootTime = 0;
    @Override
    public void shoot(Ship ship) {
        Vec2 canon1 = body.getWorldVector(new Vec2(-1, -0.86f){{normalize();}});
        Vec2 canon2 = body.getWorldVector(new Vec2(1, -0.86f){{normalize();}});
        Vec2 canon3 = body.getWorldVector(new Vec2(0, 0.86f){{normalize();}});

        Vec2 shipRT = ship.getPosition().sub(body.getPosition());
        shipRT.normalize();
        if (System.currentTimeMillis() - lastShootTime < 1000) return;
        if (canon1.sub(shipRT).length() <= 1f) shoot1();
        else if (canon2.sub(shipRT).length() <= 1f) shoot2();
        else if (canon3.sub(shipRT).length() <= 1f) shoot3();
        lastShootTime = System.currentTimeMillis();
    }
}
