package fr.upem.spacekaira.shape.characters.bomb.nonarmed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

import static fr.upem.spacekaira.shape.characters.bomb.BombType.NORMAL_BOMB;

/**
 * Represents a bomb
 */
public class NormalBomb extends AbstractBomb {
    /**
     * Builds a new non-armed bomb
     *
     * @param world    The current world
     * @param position The position of the bomb
     * @param brush    The brush of the bomb
     */
    public NormalBomb(World world, Vec2 position, Brush brush) {
        super(world, position, brush, NORMAL_BOMB);
    }

    /**
     * Creates a ready-to-use non-armed bomb
     *
     * @param world    The current world
     * @param position The position of the bomb
     * @return         The created  non-armed bomb
     */
    public static NormalBomb create(World world, Vec2 position) {
        Color color = Color.LIGHT_GRAY;
        Brush brush = BrushFactory.get(color);
        return new NormalBomb(world, position, brush);
    }
}
