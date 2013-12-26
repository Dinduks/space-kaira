package fr.upem.spacekaira.shape.character.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.FixtureType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.List;

import static fr.upem.spacekaira.shape.character.bomb.BombType.NORMAL_BOMB;

/**
 * Represents a bomb that has been dropped
 */
public class ArmedNormalBomb extends AbstractArmedBomb {
    /**
     * Build A new armed bomb
     *
     * @param world               The current world
     * @param position            The position of the armed bomb
     * @param initialBrush
     * @param brushAfterExploding
     */
    public ArmedNormalBomb(World world, Vec2 position, Brush initialBrush,
                           Brush brushAfterExploding) {
        super(world, position, initialBrush, brushAfterExploding, NORMAL_BOMB);
    }

    @Override
    public boolean explode(List<Enemy> enemies) {
        if (radius >= 5.0f) return false;
        circleShape.setRadius((radius = radius + 0.5f));
        armedBombFixtureDef.shape = circleShape;
        armedBombFixtureDef.userData = brushAfterExploding;
        armedBombFixtureDef.filter.maskBits = FixtureType.STD_ENEMY;
        body.destroyFixture(fixture);
        body.createFixture(armedBombFixtureDef);

        return true;
    }
}
