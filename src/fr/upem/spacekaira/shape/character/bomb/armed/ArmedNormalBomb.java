package fr.upem.spacekaira.shape.character.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static fr.upem.spacekaira.shape.character.bomb.BombType.MEGA_BOMB;

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
        super(world, position, initialBrush, brushAfterExploding, MEGA_BOMB);
    }
}
