package fr.upem.spacekaira.shape.characters.bomb.armed;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.shape.ShapeWithDynamicContact;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.List;

public abstract class AbstractArmedBomb extends ShapeWithDynamicContact {
    private FixtureDef armedBombFixtureDef;
    private Brush brushAfterExploding;
    private CircleShape circleShape = new CircleShape();
    private final long dropTime = System.currentTimeMillis();
    private Fixture fixture;
    private float radius = 0.5f;

    /**
     * Build A new armed bomb
     *
     * @param world The current world
     * @param position The position of the armed bomb
     * @param initialBrush
     */
    public AbstractArmedBomb(World world, Vec2 position, Brush initialBrush) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        setBody(world.createBody(bodyDef));

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);

        armedBombFixtureDef = new FixtureDef();
        armedBombFixtureDef.shape = circleShape;
        armedBombFixtureDef.density = 1.0f;
        armedBombFixtureDef.userData = initialBrush;
        armedBombFixtureDef.filter.categoryBits = FixtureType.ARMED_BOMB;
        armedBombFixtureDef.filter.maskBits = 0;

        fixture = getBody().createFixture(armedBombFixtureDef);
        getBody().setUserData(this);
    }

    /**
     * @return true if done exploding, false otherwise
     * @param enemies
     */
    public abstract boolean explode(List<Enemy> enemies);

    @Override
    public void computeTimeStepData() {
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

    public FixtureDef getArmedBombFixtureDef() {
        return armedBombFixtureDef;
    }

    public Brush getBrushAfterExploding() {
        return brushAfterExploding;
    }

    public CircleShape getCircleShape() {
        return circleShape;
    }

    public Fixture getFixture() {
        return fixture;
    }
}
