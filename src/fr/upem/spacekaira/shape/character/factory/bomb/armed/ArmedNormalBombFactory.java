package fr.upem.spacekaira.shape.character.factory.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.bomb.armed.ArmedNormalBomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

// TODO: Rename to ArmedNormal…
public class ArmedNormalBombFactory {
    public static ArmedNormalBomb create(World world, Vec2 position) {
        BrushFactory brushFactory = new BrushFactory();
        Brush initialBrush = brushFactory.createBrush(Color.RED, true);
        Brush brushAfterExploding = brushFactory.createBrush(Color.RED, false);

        return new ArmedNormalBomb(world, position, initialBrush,
                brushAfterExploding);
    }
}
