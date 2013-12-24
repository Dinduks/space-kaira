package fr.upem.spacekaira.shape.character;


import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.DynamicContact;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Represents a bomb that has been dropped
 */
public class ArmedBomb extends AbstractShape implements DynamicContact {
    /**
     * Build A new armed bomb
     * @param world The current world
     * @param position The position of the armed bomb
     * @param color
     */
    public ArmedBomb(World world, Vec2 position, Brush color) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);

        FixtureDef armedBombFixtureDef = new FixtureDef();
        armedBombFixtureDef.shape = circleShape;
        armedBombFixtureDef.density = 1.0f;
        armedBombFixtureDef.userData = color;
        armedBombFixtureDef.filter.categoryBits = FixtureType.ARMED_BOMB;
        armedBombFixtureDef.filter.maskBits = 0;

        body.createFixture(armedBombFixtureDef);
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
