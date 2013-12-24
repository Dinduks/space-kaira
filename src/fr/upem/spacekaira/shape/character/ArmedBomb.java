package fr.upem.spacekaira.shape.character;


import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.DynamicContact;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 * Represents a bomb that has been dropped
 */
public class ArmedBomb extends AbstractShape implements DynamicContact {
    private FixtureDef armedBombFixtureDef = new FixtureDef();
    private Brush brushAfterExploding;
    private CircleShape circleShape = new CircleShape();
    private final long dropTime = System.currentTimeMillis();
    private Fixture fixture;
    private float radius = 0.5f;

    /**
     * Build A new armed bomb
     * @param world The current world
     * @param position The position of the armed bomb
     * @param initialBrush
     * @param brushAfterExploding
     */
    public ArmedBomb(World world, Vec2 position, Brush initialBrush,
                     Brush brushAfterExploding) {
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
     * @return true if exploded, false if still exploding
     */
    public boolean explode() {
        if (radius >= 5.0f) return false;
        circleShape.setRadius((radius = radius + 0.5f));
        armedBombFixtureDef.shape = circleShape;
        armedBombFixtureDef.userData = brushAfterExploding;
        body.destroyFixture(fixture);
        body.createFixture(armedBombFixtureDef);

        return true;
    }

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
}
