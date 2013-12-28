package fr.upem.spacekaira.shape.characters.factory.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.characters.bomb.armed.ArmedMegaBomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class ArmedMegaBombFactory {
    public static ArmedMegaBomb create(World world, Vec2 position) {
        Brush initialBrush = BrushFactory.get(Color.RED);
        Brush brushAfterExploding = BrushFactory.get(Color.RED);

        return new ArmedMegaBomb(world, position, initialBrush,
                brushAfterExploding);
    }
}
