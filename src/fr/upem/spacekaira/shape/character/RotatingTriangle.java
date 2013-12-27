package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class RotatingTriangle extends Enemy {

    public RotatingTriangle(World world, float x, float y, Brush color, Brush bulletColor) {
        super(color, bulletColor);

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
        triangleFix.filter.categoryBits = FixtureType.STD_ENEMY;
        triangleFix.filter.maskBits = FixtureType.BULLET
                | FixtureType.SHIP
                | FixtureType.ARMED_BOMB
                | FixtureType.PLANET;

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
        body.setLinearVelocity(speed);
        body.setAngularVelocity(2);
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }


    private void addBulletToShoot(Vec2 position, Vec2 velocity,float angle) {
        bullets.add(Bullet.createEnemyBullet(body.getWorld(),
                body.getWorldPoint(position),
                body.getWorldVector(velocity),
               angle,
                bulletColor));
    }

    private void shoot1() {
        addBulletToShoot(new Vec2(-1.2f,-1.03f),new Vec2(-1,-0.86f),body.getAngle()+2.1f);
    }

    private void shoot2() {
        addBulletToShoot(new Vec2(1.2f, -1.03f),new Vec2(1, -0.86f),body.getAngle()-2.1f);
    }

    private void shoot3() {
        addBulletToShoot(new Vec2(0.0f, 1.03f),new Vec2(0, 0.86f),body.getAngle());
    }

    @Override
    public void shoot(Ship ship) {
        Vec2 canon1 = body.getWorldVector(new Vec2(-1, -0.86f));
        canon1.normalize();
        Vec2 canon2 = body.getWorldVector(new Vec2(1, -0.86f));
        canon2.normalize();
        Vec2 canon3 = body.getWorldVector(new Vec2(0, 0.86f));
        canon3.normalize();

        Vec2 shipRT = ship.getPosition().sub(body.getPosition());
        shipRT.normalize();

        if (canon1.sub(shipRT).length() <= 0.05f) shoot1();
        if (canon2.sub(shipRT).length() <= 0.05f) shoot2();
        if (canon3.sub(shipRT).length() <= 0.05f) shoot3();
    }
}
