package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Viewport;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class IntergalacticCruiser extends Enemy {

    public IntergalacticCruiser(World world, float x, float y, Brush color, Brush bulletColor) {
        super(world, x, y, color, bulletColor);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef cruiserFix = new FixtureDef();
        cruiserFix.density = 1.0f;
        cruiserFix.userData = ENEMY_COLOR;
        cruiserFix.filter.categoryBits = FixtureType.STD_ENEMY;
        cruiserFix.filter.maskBits = FixtureType.BULLET | FixtureType.SHIP | FixtureType.PLANET | FixtureType.STD_ENEMY;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(new Vec2[]{new Vec2(0, 0), new Vec2(0, 2), new Vec2(7, 2), new Vec2(7, 0)},4);

        cruiserFix.shape = polygonShape;
        body.createFixture(cruiserFix);
    }

    private void shootUp() {
        bullets.add(new Bullet(body.getWorld(),body.getWorldPoint(new Vec2(3.5f,4)),body.getWorldVector(new Vec2(0,1)),body.getAngle(),BULLET_COLOR));
    }

    private void shootDown() {
        bullets.add(new Bullet(body.getWorld(),body.getWorldPoint(new Vec2(3.5f,-2)),body.getWorldVector(new Vec2(0,-1)),body.getAngle(),BULLET_COLOR));
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }

    @Override
    public void shoot() {
        shootUp(); shootDown();
    }

    @Override
    public void move(Ship ship) {
        Vec2 speed = followAlgo(ship.getPosition(),body.getPosition(),ship.getLinearVelocity(),body.getLinearVelocity(),0.1f);
        body.setLinearVelocity(speed);
    }
}