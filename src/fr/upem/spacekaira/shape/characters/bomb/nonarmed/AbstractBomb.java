package fr.upem.spacekaira.shape.characters.bomb.nonarmed;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.DynamicContact;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.bomb.BombType;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Represents a bomb
 */
public abstract class AbstractBomb extends AbstractShape implements DynamicContact {
    /**
     * Builds a new non-armed bomb
     *
     * @param world    The current world
     * @param position The position of the bomb
     * @param brush    The brush of the bomb
     * @param bombType The type of the bomb
     */
    public AbstractBomb(World world, Vec2 position, Brush brush,
                        BombType bombType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        setBody(world.createBody(bodyDef));

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        FixtureDef bombFixtureDef = new FixtureDef();
        bombFixtureDef.shape = circleShape;
        bombFixtureDef.density = 0.0f;
        bombFixtureDef.userData = brush;
        bombFixtureDef.filter.categoryBits = BombType.getFixtureType(bombType);

        bombFixtureDef.filter.maskBits = FixtureType.SHIP;

        getBody().createFixture(bombFixtureDef);
        getBody().setUserData(this);
    }

    @Override
    public void computeTimeStepData() {
    }

    @Override
    public boolean isDead() {
        return getBody().getFixtureList().getUserData() == Brush.DESTROY_BRUSH;
    }
}
