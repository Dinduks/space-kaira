package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.brush.BrushFactory;
import fr.upem.spacekaira.shape.ShapeWithDynamicContact;
import fr.upem.spacekaira.game.Viewport;
import fr.upem.spacekaira.shape.characters.bomb.armed.AbstractArmedBomb;
import fr.upem.spacekaira.shape.characters.bomb.armed.ArmedMegaBomb;
import fr.upem.spacekaira.shape.characters.bomb.armed.ArmedNormalBomb;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
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
public class Ship extends ShapeWithDynamicContact {
    private boolean shield;
    private final Fixture shieldFix;
    private final Fixture shipFix;
    private List<Bullet> bullets;
    private final Brush bulletColor;
    private boolean hasBomb;
    private boolean megaBomb;
    private AbstractArmedBomb armedBomb;
    private List<Enemy> enemies;
    private boolean autoShield;

    public Ship(World world, List<Enemy> enemies, Brush shipColor,
                Brush bulletColor, boolean autoShield) {
        this.enemies = enemies;
        this.bulletColor = bulletColor;
        this.autoShield = autoShield;
        this.shield = true;

        //Bullet list
        bullets = new LinkedList<>();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 0.8f;

        setBody(world.createBody(bodyDef));

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
                    FixtureType.ENEMY |
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
            Filter shieldFilter = (autoShield)?shieldFilterOn:shieldFilterOff;
            shield.filter.categoryBits = shieldFilter.categoryBits;
            shield.filter.maskBits = shieldFilter.maskBits;
        }
        shipFix = getBody().createFixture(ship);
        shieldFix = getBody().createFixture(shield);

        getBody().setUserData(this);
    }

    public void up() {
        Vec2 f = getBody().getWorldVector(new Vec2(0.0f, 500.0f));
        Vec2 p = getBody().getPosition();
        getBody().applyForce(f, p);
    }

    public void left() {
        getBody().applyTorque(-30.0f);
    }

    public void right() {
        getBody().applyTorque(30.0f);
    }

    private Filter shieldFilterOff = new Filter() {{
        categoryBits = 0;
        maskBits = 0;
    }};

    private Filter shieldFilterOn = new Filter() {{
        categoryBits = FixtureType.SHIELD;
        maskBits = FixtureType.PLANET |
                FixtureType.ENEMY |
                FixtureType.BULLET_ENEMY | FixtureType.PLANET;
    }};

    public void toggleShield() {
        setShield(!shield);
    }

    public boolean shield() {
        return shield;
    }

    public void dropBomb() {
        if (!hasBomb) return;

        hasBomb = false;
        if (!megaBomb) {
            armedBomb = ArmedNormalBomb.create(getBody().getWorld(),
                    getPosition());
        } else {
            armedBomb = ArmedMegaBomb.create(getBody().getWorld(),
                    getPosition());
        }
    }

    public Vec2 getLinearVelocity() {
        return getBody().getLinearVelocity();
    }

    private static long lastShootTime = 0;

    public void shoot() {
        if (shield) return;
        if (System.currentTimeMillis() - lastShootTime < 100) return;

        lastShootTime = System.currentTimeMillis();
        bullets.add(Bullet.createShipBullet(
                getBody().getWorld(),
                getBody().getPosition(),
                getBody().getWorldVector(new Vec2(0, 3)),
                getBody().getAngle(),
                bulletColor));
    }


    public void checkForBulletOutScreen(Viewport viewport) {
        Bullet.checkForBulletsOutScreen(viewport, bullets);
    }

    @Override
    public void draw(Graphics2D graphics, Viewport viewport) {
        if (armedBomb != null) handleTheArmedBomb(graphics, viewport);
        shieldFix.setUserData((shield) ? BrushFactory.get(Color.BLUE, false) : null);
        super.draw(graphics, viewport);
        bullets.forEach(b -> b.draw(graphics, viewport));
        if (Viewport.isZero(getBody().getLinearVelocity().x) ||
                Viewport.isZero(getBody().getLinearVelocity().y) ||
                Viewport.isZero(getBody().getAngularVelocity())) {
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
       setShield(true);
    }

    private void setShield(boolean shield) {
        if (!autoShield) {
            if (shield)
                shieldFix.setFilterData(shieldFilterOn);
            else
                shieldFix.setFilterData(shieldFilterOff);
        }
        this.shield = shield;
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
        Vec2 leftMotor = new Vec2(-0.5f, -3.3f);
        Vec2 rightMotor = new Vec2(0.5f, -3.3f);

        leftMotor = viewport.getWorldVectorToScreen(
                getBody().getWorldPoint(leftMotor));
        rightMotor = viewport.getWorldVectorToScreen(
                getBody().getWorldPoint(rightMotor));

        graphics.setPaint(Color.YELLOW);
        graphics.fillOval(Math.round(leftMotor.x - (int) viewport.getCameraScale()/2),
                Math.round(leftMotor.y - (int) viewport.getCameraScale()/2),
                (int) viewport.getCameraScale(),
                (int) viewport.getCameraScale());
        graphics.fillOval(Math.round(rightMotor.x - (int) viewport.getCameraScale() / 2),
                Math.round(rightMotor.y - (int) viewport.getCameraScale() / 2),
                (int) viewport.getCameraScale(),
                (int) viewport.getCameraScale());
    }

    @Override
    public void computeTimeStepData() {
        Bullet.checkForDeadBullet(bullets);
    }

    @Override
    public boolean isDead() {
        return shipFix.getUserData() == Brush.DESTROY_BRUSH;
    }
}
