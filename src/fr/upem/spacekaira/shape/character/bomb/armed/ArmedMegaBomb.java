package fr.upem.spacekaira.shape.character.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static fr.upem.spacekaira.shape.character.bomb.BombType.MEGA_BOMB;

/**
 * Represents a bomb that has been dropped
 */
public class ArmedMegaBomb extends AbstractArmedBomb {
    /**
     * Build A new armed bomb
     *
     * @param world               The current world
     * @param position            The position of the armed bomb
     * @param initialBrush
     * @param brushAfterExploding
     */
    public ArmedMegaBomb(World world, Vec2 position, Brush initialBrush,
                         Brush brushAfterExploding) {
        super(world, position, initialBrush, brushAfterExploding, MEGA_BOMB);
    }

    @Override
    public boolean explode() {
//        if (radius >= 5.0f) return false;
//        circleShape.setRadius((radius = radius + 0.5f));
//        armedBombFixtureDef.shape = circleShape;
//        armedBombFixtureDef.userData = brushAfterExploding;
//        armedBombFixtureDef.filter.maskBits = FixtureType.STD_ENEMY;
//        body.destroyFixture(fixture);
//        body.createFixture(armedBombFixtureDef);
//
        return true;
    }
}
