package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Draw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The player ship
 */
public class Ship extends AbstractShape implements Shooter {
    static {BULLET_COLOR = new Brush(Color.GREEN,true);}
    private final static Brush BULLET_COLOR;

    private boolean shield;
    private final Fixture shieldFix;
    private List<Bullet> bullets;

    public Ship(World world) {

        //Bullet list
        bullets = new LinkedList<Bullet>();

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
            ship.userData = new Brush(Color.BLUE,true);
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

    public void bomb() {
        //TODO the code to compute an explosion
    }

    @Override
    public void shoot() {
        if(!shield)
            bullets.add(new Bullet(body.getWorld(),body.getPosition(),body.getWorldVector(new Vec2(0,3)),body.getAngle(),BULLET_COLOR));
    }

    @Override
    public void checkForBulletOutScreen(Draw d) {
        Bullet.checkForBulletsOutScreen(d,bullets);
    }

    @Override
    public void draw(Graphics2D graphics, Draw d) {
        shieldFix.setUserData((shield) ? new Brush(Color.BLUE, false) : null);
        super.draw(graphics, d);
        bullets.forEach(b->b.draw(graphics,d));
        if(Draw.isZero(body.getLinearVelocity().x) || Draw.isZero(body.getLinearVelocity().y) || Draw.isZero(body.getAngularVelocity())) {
            drawMotors(graphics, d);
        }
    }

    private void drawMotors(Graphics2D graphics, Draw d) {
        //TODO a faire en dynamique avec une chaine de petites boules et suivant la vitesse
        Vec2 leftMotor = new Vec2(-0.5f,-3.3f);
        Vec2 rightMotor = new Vec2(0.5f,-3.3f);

        leftMotor = d.getWorldVectorToScreen(body.getWorldPoint(leftMotor));
        rightMotor = d.getWorldVectorToScreen(body.getWorldPoint(rightMotor));

        graphics.setPaint(Color.YELLOW);
        graphics.fillOval(Math.round(leftMotor.x - (int)d.getCameraScale()/2),Math.round(leftMotor.y - (int)d.getCameraScale()/2),(int)d.getCameraScale(),(int)d.getCameraScale());
        graphics.fillOval(Math.round(rightMotor.x - (int)d.getCameraScale()/2),Math.round(rightMotor.y - (int)d.getCameraScale()/2),(int)d.getCameraScale(),(int)d.getCameraScale());
    }

    @Override
    public void checkForBulletInPlanets(List<Planet> planets) {
        Iterator<Bullet> it = bullets.iterator();
        while(it.hasNext()) {
            Bullet b = it.next();
            for(Planet p : planets) {
                if(b.isCollide(p)) {
                    it.remove();
                }
            }
        }
    }
}