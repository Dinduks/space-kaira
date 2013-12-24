package fr.upem.spacekaira.shape.character.factory.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.bomb.armed.ArmedNormalBomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class NormalArmedBombFactory {
    public static ArmedNormalBomb create(World world, Vec2 position,
                                         Brush initialBrush,
                                         Brush brushAfterExploding) {
        return new ArmedNormalBomb(world, position, initialBrush,
                brushAfterExploding);
    }
}
