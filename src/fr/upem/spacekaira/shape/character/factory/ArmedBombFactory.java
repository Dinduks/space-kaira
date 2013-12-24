package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.ArmedBomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ArmedBombFactory {
    public static ArmedBomb create(World world, Vec2 position, Brush initialBrush,
                                   Brush brushAfterExploding) {
        return new ArmedBomb(world, position, initialBrush, brushAfterExploding);
    }
}
