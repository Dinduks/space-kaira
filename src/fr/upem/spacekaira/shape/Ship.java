package fr.upem.spacekaira.shape;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;

public class Ship extends AbstractShape {
    private boolean shield;
    private final Fixture shieldFix;

    public Ship(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;

        body = world.createBody(bodyDef);

        //Ship
        FixtureDef ship = null;
        {
            PolygonShape polygonShape = new PolygonShape();
            Vec2[] tab = {new Vec2(0,0),new Vec2(-1,-3),new Vec2(0,-3),new Vec2(1,-3)};
            polygonShape.set(tab,4);

            ship = new FixtureDef();
            ship.shape = polygonShape;
            ship.density = 1.0f;
            ship.userData = new Brush(true, Color.BLUE,true);
        }

        //Shield
        FixtureDef shield = null;
        {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(2);
            circleShape.m_p.set(0,-1.7f);

            shield = new FixtureDef();
            shield.shape = circleShape;
            shield.density = 0;
            shield.userData = null; //Use with caution
        }

        body.createFixture(ship);
        shieldFix = body.createFixture(shield);

        body.setUserData(this);
    }

    public void up() {
        Vec2 f = body.getWorldVector(new Vec2(0.0f, 60.0f));
        Vec2 p = body.getWorldPoint(body.getLocalCenter().add(new Vec2(0.0f, 4.0f)));
        body.applyForce(f, p);
    }

    public void left() {
        body.applyTorque(-10.0f);
    }

    public void right() {
        body.applyTorque(10.0f);
    }

    public Vec2 getPosition() {
        return body.getWorldCenter();
    }

    public void shield() {
        shield = (shield)?false:true;
    }

    @Override
    public void draw(Graphics2D graphics, Draw d) {
        shieldFix.setUserData((shield) ? new Brush(true, Color.BLUE, false) : null);
        super.draw(graphics, d);
    }
}
