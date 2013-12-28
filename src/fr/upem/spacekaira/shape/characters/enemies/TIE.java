package fr.upem.spacekaira.shape.characters.enemies;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.characters.Bullet;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * The space ship Enemy
 */
public class TIE extends Enemy {

    public TIE(World world, float x, float y, Brush color, Brush bulletColor) {
        super(color, bulletColor);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef edgeFix = new FixtureDef();
        edgeFix.density = 1.0f;
        edgeFix.userData = enemyColor;
        edgeFix.filter.categoryBits = FixtureType.STD_ENEMY;
        edgeFix.filter.maskBits = FixtureType.BULLET
                | FixtureType.SHIP
                | FixtureType.ARMED_BOMB
                | FixtureType.PLANET;

        //left-side-down
       {
            PolygonShape polygonShape = new PolygonShape();
           Vec2[] vertices = { new Vec2(-3.5f, 2), new Vec2(-3.5f, 2.1f),
                   new Vec2(-1.5f, 4.1f), new Vec2(-1.5f, 4) };
           polygonShape.set(vertices, 4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }

        //left-side-up
       {
            PolygonShape polygonShape = new PolygonShape();
           Vec2[] vertices = { new Vec2(-3.5f, 2), new Vec2(-3.5f, 2.1f),
                   new Vec2(-1.5f, 0.1f), new Vec2(-1.5f, 0) };
           polygonShape.set(vertices, 4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }

        //right-side-down
       {
           PolygonShape polygonShape = new PolygonShape();
           Vec2[] vertices = { new Vec2(3.5f, 2), new Vec2(3.5f, 2.1f),
                   new Vec2(1.5f, 4.1f), new Vec2(1.5f, 4) };
           polygonShape.set(vertices, 4);
           edgeFix.shape = polygonShape;
           body.createFixture(edgeFix);
        }

        //right-side-up
        {
            PolygonShape polygonShape = new PolygonShape();
            Vec2[] vertices = { new Vec2(3.5f, 2), new Vec2(3.5f, 2.1f),
                    new Vec2(1.5f, 0.1f), new Vec2(1.5f, 0) };
            polygonShape.set(vertices, 4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }


        //middle
        {
            PolygonShape polygonShape = new PolygonShape();
            Vec2[] vertices = { new Vec2(-1.5f, 2), new Vec2(-1.5f, 2.1f),
                    new Vec2(1.5f, 2.1f), new Vec2(1.5f, 2) };
            polygonShape.set(vertices, 4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }


        //inner polygon
        {
            PolygonShape polygonShape = new PolygonShape();
            Vec2[] vertices = { new Vec2(-3.5f, 2), new Vec2(-2, 2.75f),
                    new Vec2(2, 2.75f), new Vec2(3.5f, 2),
                    new Vec2(2, 1.25f), new Vec2(-2, 1.25f) };
            polygonShape.set(vertices, 6);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }
    }

    @Override
    public void move(Ship ship) {
        Vec2 speed = computeRotationSpeed(ship.getPosition(),
                body.getPosition(), 10, 0.017f);
        speed.normalize();
        body.setLinearVelocity(speed.mul(ship.getLinearVelocity().length()*0.9f));
        body.setAngularVelocity(2);
    }

    private void shootLeft() {
        addBulletToShoot(new Vec2(-4.5f, 2),new Vec2(-1, 0), body.getAngle() + 1.57f);
    }

    private void shootRight() {
        addBulletToShoot(new Vec2(4.5f, 2),new Vec2(1, 0),body.getAngle() - 1.57f);
    }

    /* This function shoot if the ship is more or less in the shoot-windows */
    @Override
    public void shoot(Ship ship) {
        Vec2 rightCanon = body.getWorldVector(new Vec2(1, 0));
        Vec2 leftCanon = body.getWorldVector(new Vec2(-1, 0));
        Vec2 shipTie = ship.getPosition().sub(body.getPosition());
        shipTie.normalize();

        if( leftCanon.sub(shipTie).length() <= 0.05f ) shootLeft();
        else if( rightCanon.sub(shipTie).length() <= 0.05f ) shootRight();
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }
}
