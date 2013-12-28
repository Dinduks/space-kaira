package fr.upem.spacekaira.shape.characters.bomb.nonarmed;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static fr.upem.spacekaira.shape.characters.bomb.BombType.NORMAL_BOMB;

/**
 * Represents a bomb
 */
public class NormalBomb extends AbstractBomb {
    /**
     * Builds a new bomb
     *
     * @param world    The current world
     * @param position The position of the bomb
     * @param color
     */
    public NormalBomb(World world, Vec2 position, Brush color) {
        super(world, position, color, NORMAL_BOMB);
    }
}