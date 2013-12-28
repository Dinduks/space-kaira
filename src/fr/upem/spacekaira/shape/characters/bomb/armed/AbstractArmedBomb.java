package fr.upem.spacekaira.shape.characters.bomb.armed;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.DynamicContact;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.bomb.BombType;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.List;

abstract public class AbstractArmedBomb
        extends AbstractShape implements DynamicContact {
    protected FixtureDef armedBombFixtureDef = new FixtureDef();
    protected Brush brushAfterExploding;
    final private Vec2 position;
    private BombType bombType;
    protected CircleShape circleShape = new CircleShape();
    private final long dropTime = System.currentTimeMillis();
    protected Fixture fixture;
    protected float radius = 0.5f;

    /**
     * Build A new armed bomb
     *
     * @param world The current world
     * @param position The position of the armed bomb
     * @param initialBrush
     * @param brushAfterExploding
     */
    public AbstractArmedBomb(World world, Vec2 position, Brush initialBrush,
                     Brush brushAfterExploding, BombType bombType) {
        this.position = position;
        this.bombType = bombType;
        this.brushAfterExploding = brushAfterExploding;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        armedBombFixtureDef.shape = circleShape;
        armedBombFixtureDef.density = 1.0f;
        armedBombFixtureDef.userData = initialBrush;
        armedBombFixtureDef.filter.categoryBits = FixtureType.ARMED_BOMB;
        armedBombFixtureDef.filter.maskBits = 0;

        fixture = body.createFixture(armedBombFixtureDef);
        body.setUserData(this);
    }

    /**
     * @return true if done exploding, false otherwise
     * @param enemies
     */
    abstract public boolean explode(List<Enemy> enemies);

    @Override
    public void computeTimeStepData() {
    }

    @Override
    public boolean isDead() {
        return body.getFixtureList().getUserData() == Brush.DESTROY_BRUSH;
    }

    public long getDropTime() {
        return dropTime;
    }

    protected float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
