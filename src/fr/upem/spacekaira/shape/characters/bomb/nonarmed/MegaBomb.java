package fr.upem.spacekaira.shape.characters.bomb.nonarmed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

import static fr.upem.spacekaira.shape.characters.bomb.BombType.MEGA_BOMB;

/**
 * Represents a bomb
 */
public class MegaBomb extends AbstractBomb {
    /**
     * Builds a new bomb
     *
     * @param world    The current world
     * @param position The position of the bomb
     * @param brush    The bomb's brush
     */
    public MegaBomb(World world, Vec2 position, Brush brush) {
        super(world, position, brush, MEGA_BOMB);
    }

    /**
     * Creates a ready-to-use non-armed mega bomb
     *
     * @param world    The current world
     * @param position The position of the bomb
     * @return         The created non-armed mega bomb
     */
    public static MegaBomb create(World world, Vec2 position) {
        Color color = Color.YELLOW;
        Brush brush = BrushFactory.get(color);
        return new MegaBomb(world, position, brush);
    }
}
