package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.collision.shapes.EdgeShape;
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
        edgeFix.filter.maskBits = FixtureType.BULLET |
                FixtureType.SHIP |
                FixtureType.STD_ENEMY |
                FixtureType.ARMED_BOMB;

        //left-side-down
       {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vec2[]{new Vec2(0,2), new Vec2(0,2.1f), new Vec2(2,4.1f),new Vec2(2,4)},4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }

        //left-side-up
       {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vec2[]{new Vec2(0,2), new Vec2(0,2.1f), new Vec2(2,0.1f), new Vec2(2,0)},4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }

        //right-side-down
       {
           PolygonShape polygonShape = new PolygonShape();
           polygonShape.set(new Vec2[]{new Vec2(7, 2), new Vec2(7, 2.1f), new Vec2(5, 4.1f), new Vec2(5, 4)}, 4);
           edgeFix.shape = polygonShape;
           body.createFixture(edgeFix);
        }

        //right-side-up
        {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vec2[]{new Vec2(7, 2), new Vec2(7, 2.1f), new Vec2(5, 0.1f), new Vec2(5, 0)}, 4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }


        //middle
        {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vec2[]{new Vec2(2, 2), new Vec2(2, 2.1f), new Vec2(5, 2.1f), new Vec2(5, 2)}, 4);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }


        //inner polygon
        {
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vec2[]{new Vec2(0, 2), new Vec2(1.5f, 2.75f), new Vec2(5.5f, 2.75f), new Vec2(7, 2), new Vec2(5.5f, 1.25f), new Vec2(1.5f, 1.25f)}, 6);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }
    }

    @Override
    public void move(Ship ship) {
        Vec2 speed = rotateAlgo(ship.getPosition(),body.getPosition(),10,0.017f);
        body.setLinearVelocity(speed.mul(100));
    }

    private void shootLeft() {
        bullets.add(new Bullet(
                body.getWorld(),
                body.getWorldPoint(new Vec2(-1, 2)),
                body.getWorldVector(new Vec2(-1, 0)),
                body.getAngle() + 1.57f,
                bulletColor));
    }

    private void shootRight() {
        bullets.add(new Bullet(
                body.getWorld(),
                body.getWorldPoint(new Vec2(8, 2)),
                body.getWorldVector(new Vec2(1, 0)),
                body.getAngle() - 1.57f,
                bulletColor));
    }

    @Override
    public void shoot() {
        //TODO feed the body
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }
}
