package fr.upem.spacekaira.shape.character.factory.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.bomb.armed.ArmedMegaBomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class ArmedMegaBombFactory {
    public static ArmedMegaBomb create(World world, Vec2 position) {
        BrushFactory brushFactory = new BrushFactory();
        Brush initialBrush = brushFactory.createBrush(Color.RED, true);
        Brush brushAfterExploding = brushFactory.createBrush(Color.RED, false);

        return new ArmedMegaBomb(world, position, initialBrush,
                brushAfterExploding);
    }
}
