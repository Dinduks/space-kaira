package fr.upem.spacekaira.shape.characters.enemies;


import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Brute extends Enemy {
    public Brute(World world, float x, float y, Brush brush,
                 Brush bulletBrush) {
        super(brush, bulletBrush);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;
        bodyDef.position.set(x,y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fd = new FixtureDef();
        fd.density = 1.0f;
        fd.userData = enemyColor;
        fd.filter.categoryBits = FixtureType.ENEMY;
        fd.filter.maskBits = FixtureType.BULLET |
                FixtureType.SHIP |
                FixtureType.ARMED_BOMB |
                FixtureType.ARMED_MBOMB |
                FixtureType.PLANET;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1);

        fd.shape = circleShape;

        body.createFixture(fd);
    }

    @Override
    public void move(Ship ship) {
        Vec2 f = ship.getPosition().sub(body.getPosition()).mul(100);
        body.setLinearVelocity(f);
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }

    @Override
    public void shoot(Ship ship) {
        //No shoot function for this enemy
    }
}