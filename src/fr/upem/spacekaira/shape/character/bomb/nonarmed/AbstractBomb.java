package fr.upem.spacekaira.shape.character.bomb.nonarmed;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.DynamicContact;
import fr.upem.spacekaira.shape.character.FixtureType;
import fr.upem.spacekaira.shape.character.bomb.BombType;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.HashMap;

/**
 * Represents a bomb
 */
public abstract class AbstractBomb extends AbstractShape implements DynamicContact {
    private BombType bombType;

    /**
     * Builds a new bomb
     * @param world The current world
     * @param position The position of the bomb
     * @param color
     * @param bombType
     */
    public AbstractBomb(World world, Vec2 position, Brush color,
                        BombType bombType) {
        this.bombType = bombType;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        FixtureDef bombFixtureDef = new FixtureDef();
        bombFixtureDef.shape = circleShape;
        bombFixtureDef.density = 1.0f;
        bombFixtureDef.userData = color;
        bombFixtureDef.filter.categoryBits = BombType.getFixtureType(bombType);

        bombFixtureDef.filter.maskBits = FixtureType.SHIP;

        body.createFixture(bombFixtureDef);
        body.setUserData(this);
    }

    @Override
    public void computeTimeStepData() {
    }

    @Override
    public boolean isDead() {
        return body.getFixtureList().getUserData() == Brush.DESTROY_BRUSH;
    }
}
