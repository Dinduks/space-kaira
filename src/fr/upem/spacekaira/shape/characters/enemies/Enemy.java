package fr.upem.spacekaira.shape.characters.enemies;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.shape.ShapeWithDynamicContact;
import fr.upem.spacekaira.game.Viewport;
import fr.upem.spacekaira.shape.characters.Bullet;
import fr.upem.spacekaira.shape.characters.Ship;
import fr.upem.spacekaira.shape.characters.Shooter;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This abstract class represent a base to construct a Enemy
 */
public abstract class Enemy extends ShapeWithDynamicContact
        implements Shooter {
    protected final Brush enemyColor;
    protected final Brush bulletColor;
    protected List<Bullet> bullets;

    public Enemy(Brush brush, Brush bulletColor) {
        this.bullets = new LinkedList<>();
        this.enemyColor = brush;
        this.bulletColor = bulletColor;
    }

    @Override
    public void draw(Graphics2D graphics, Viewport viewport) {
        super.draw(graphics, viewport);
        bullets.forEach(b -> b.draw(graphics, viewport));
    }

    @Override
    public void checkForBulletOutScreen(Viewport viewport) {
        Bullet.checkForBulletsOutScreen(viewport, bullets);
    }

    @Override
    public void computeTimeStepData() {
        for (Fixture fix = getBody().getFixtureList(); fix != null; fix = fix.getNext()) {
            if (fix.getUserData() == Brush.DESTROY_BRUSH) {
                getBody().setUserData(Brush.DESTROY_BRUSH);
            }
        }

        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.isDead()) {
                b.destroy();
                it.remove();
            }
        }
    }

    protected void addBulletToShootLocal(Vec2 position, Vec2 velocity, float angle) {
        addBulletToShootWorld(getBody().getWorldPoint(position),
                getBody().getWorldVector(velocity),
                angle);
    }

    protected void addBulletToShootWorld(Vec2 position, Vec2 velocity, float angle) {
        bullets.add(Bullet.createEnemyBullet(getBody().getWorld(),
            position,
            velocity,
            angle,
            bulletColor)
        );
    }

    /**
     * Compute enemy movement
     * @param ship the player ship
     */
    abstract public void move(Ship ship);

    /**
     * This method computes the speed of B when you want that B rotateAroundDot
     * around A
     *
     * @param A a dot A
     * @param B a dot B
     * @param limit max(distance(A,B))
     * @param angleByCall Angle to give at B at each call (in radian)
     * @return the new Speed of B (~0.017f)
     */
    protected Vec2 computeRotationSpeed(Vec2 A, Vec2 B, float limit,
                                        float angleByCall) {
        Vec2 BA = A.sub(B);
        float length = BA.length();

        if (Math.abs(length - limit) > 1) {
            BA.normalize();
            if (length > limit) {
                BA = BA.mul(length - 0.5f);
            } else {
                BA = BA.mul(length + 0.1f);
            }
        }

        Rot rot = new Rot(angleByCall);
        Vec2 tmp = new Vec2();
        Rot.mulTrans(rot, BA, tmp);

        return A.sub(B).sub(tmp);
    }

    /**
     * This method compute the speed of B, when you want to make B follow A
     *
     * You should do B.getPosition().set(B.getPosition().add(new_Speed_of_B));
     * or a stuff like that
     *
     * @param A     Dot to follow
     * @param B     Dot who follows
     * @param Va    Speed of dot A
     * @param Vb    Speed of dot B
     * @param alpha coefficient that represents the distance between A and B
     * @return the new Speed of B
     */
    protected Vec2 computeFollowingSpeed(Vec2 A, Vec2 B, Vec2 Va, Vec2 Vb,
                                         float alpha) {
        Vec2 BA = A.sub(B);
        if (BA.length() == 0) return Vb;
        return Vb.mul(1 - alpha).add(
                BA.mul(Math.abs(Va.length()) / Math.abs(BA.length()) * alpha));
    }

    @Override
    public void checkForDeadBullet() {
        Bullet.checkForDeadBullet(bullets);
    }

    public void moveToward(Vec2 position) {
        Vec2 f = position;
        Vec2 p = getBody().getWorldCenter();
        getBody().applyForce(f, p);
    }

    @Override
    public boolean isDead() {
        return getBody().getUserData() == Brush.DESTROY_BRUSH;
    }
}
