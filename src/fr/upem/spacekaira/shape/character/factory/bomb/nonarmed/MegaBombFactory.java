package fr.upem.spacekaira.shape.character.factory.bomb.nonarmed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.bomb.nonarmed.MegaBomb;
import java.awt.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class MegaBombFactory {
    public static MegaBomb create(World world, Vec2 position) {
        Color color = Color.YELLOW;
        Brush brush = (new BrushFactory()).createBrush(color, true);
        return new MegaBomb(world, position, brush);
    }
}
