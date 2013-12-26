package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.*;
import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Viewport;
import fr.upem.spacekaira.shape.character.bomb.armed.AbstractArmedBomb;
import fr.upem.spacekaira.shape.character.factory.bomb.armed.ArmedMegaBombFactory;
import fr.upem.spacekaira.shape.character.factory.bomb.armed.ArmedNormalBombFactory;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The player ship
 */
public class Ship extends AbstractShape implements DynamicContact{
    private boolean shield;
    private final Fixture shieldFix;
    private List<Bullet> bullets;
    private final Brush bulletColor;
    private boolean hasBomb;
    private boolean megaBomb;
    private AbstractArmedBomb armedBomb;
    private List<Enemy> enemies;
    public static final float speed = 12;

    public Ship(World world, List<Enemy> enemies, Brush shipColor,
                Brush bulletColor) {
        this.enemies = enemies;
        this.bulletColor = bulletColor;

        //Bullet list
        bullets = new LinkedList<>();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 0.8f;

        body = world.createBody(bodyDef);

        //Ship
        FixtureDef ship = null;
        {
            PolygonShape polygonShape = new PolygonShape();
            Vec2[] tab = {new Vec2(0, 0),
                    new Vec2(-1, -3),
                    new Vec2(0, -3),
                    new Vec2(1, -3)};
            polygonShape.set(tab, 4);

            ship = new FixtureDef();
            ship.shape = polygonShape;
            ship.density = 2.0f;
            ship.userData = shipColor;
            ship.filter.categoryBits = FixtureType.SHIP;
            ship.filter.maskBits = FixtureType.PLANET |
                    FixtureType.STD_ENEMY |
                    FixtureType.BULLET_ENEMY |
                    FixtureType.BOMB |
                    FixtureType.MBOMB;
        }

        //Shield
        FixtureDef shield = null;
        {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(2);
            circleShape.m_p.set(0, -1.7f);

            shield = new FixtureDef();
            shield.shape = circleShape;
            shield.density = 0;
            shield.userData = null; //Use with caution
            shield.filter.categoryBits = FixtureType.SHIP;
            shield.filter.maskBits = FixtureType.PLANET |
                    FixtureType.STD_ENEMY |
                    FixtureType.BULLET_ENEMY;
        }

        body.createFixture(ship);
        shieldFix = body.createFixture(shield);

        body.setUserData(this);
    }

    public void up() {
        Vec2 f = body.getWorldVector(new Vec2(0.0f, 120.0f));
        Vec2 p = body.getWorldPoint(body.getLocalCenter().add(new Vec2(0.0f, 4.0f)));
        body.applyForce(f, p);
    }

    public void left() {
        body.applyTorque(-10.0f);
    }

    public void right() {
        body.applyTorque(10.0f);
    }

    public void toggleShield() {
        shield = !shield;
    }

    public boolean shield() {
        return shield;
    }

    public void dropBomb() {
        if (!hasBomb) return;

        hasBomb = false;
        if (!megaBomb) {
            armedBomb = ArmedNormalBombFactory.create(body.getWorld(),
                    getPosition());
        } else {
            armedBomb = ArmedMegaBombFactory.create(body.getWorld(),
                    getPosition());
        }
    }

    public Vec2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    private static long lastShootTime = 0;

    public void shoot() {
        if (shield) return;
        if (System.currentTimeMillis() - lastShootTime < 100) return;

        lastShootTime = System.currentTimeMillis();
        bullets.add(Bullet.createShipBullet(
                body.getWorld(),
                body.getPosition(),
                body.getWorldVector(new Vec2(0, 3)),
                body.getAngle(),
                bulletColor));
    }


    public void checkForBulletOutScreen(Viewport viewport) {
        Bullet.checkForBulletsOutScreen(viewport, bullets);
    }

    @Override
    public void draw(Graphics2D graphics, Viewport viewport) {
        if (armedBomb != null) handleTheArmedBomb(graphics, viewport);
        shieldFix.setUserData((shield) ? new Brush(Color.BLUE, false) : null);
        super.draw(graphics, viewport);
        bullets.forEach(b -> b.draw(graphics, viewport));
        if (Viewport.isZero(body.getLinearVelocity().x) ||
                Viewport.isZero(body.getLinearVelocity().y) ||
                Viewport.isZero(body.getAngularVelocity())) {
            drawMotors(graphics, viewport);
        }
    }

    private void handleTheArmedBomb(Graphics2D graphics, Viewport viewport) {
        if (System.currentTimeMillis() - armedBomb.getDropTime() >= 1000) {
            if (armedBomb.explode(enemies)) {
                armedBomb.draw(graphics, viewport);
            } else {
                armedBomb = null;
            }
        } else {
            armedBomb.draw(graphics, viewport);
        }
    }

    public void enableShield() {
        shield = true;
    }

    // TODO: Refactor this shit
    public void addMegaBomb() {
        hasBomb = true;
        megaBomb = true;
    }

    public void addBomb() {
        hasBomb = true;
        megaBomb = false;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public boolean hasNormalBomb() {
        return hasBomb && !megaBomb;
    }

    public boolean hasMegaBomb() {
        return hasBomb && megaBomb;
    }

    private void drawMotors(Graphics2D graphics, Viewport viewport) {
        //TODO a faire en dynamique avec une chaine de petites boules et suivant la vitesse
        Vec2 leftMotor = new Vec2(-0.5f, -3.3f);
        Vec2 rightMotor = new Vec2(0.5f, -3.3f);

        leftMotor = viewport.getWorldVectorToScreen(body.getWorldPoint(leftMotor));
        rightMotor = viewport.getWorldVectorToScreen(body.getWorldPoint(rightMotor));

        graphics.setPaint(Color.YELLOW);
        graphics.fillOval(Math.round(leftMotor.x - (int) viewport.getCameraScale()/2),
                Math.round(leftMotor.y - (int) viewport.getCameraScale()/2),
                (int) viewport.getCameraScale(),
                (int) viewport.getCameraScale());
        graphics.fillOval(Math.round(rightMotor.x - (int) viewport.getCameraScale()/2),
                Math.round(rightMotor.y - (int) viewport.getCameraScale()/2),
                (int) viewport.getCameraScale(),
                (int) viewport.getCameraScale());
    }

    @Override
    public void computeTimeStepData() {
        Bullet.checkForDeadBullet(bullets);
    }

    @Override
    public boolean isDead() {
        return false;
    }
}
