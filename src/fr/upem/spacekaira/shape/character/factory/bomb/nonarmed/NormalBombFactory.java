package fr.upem.spacekaira.shape.character.factory.bomb.nonarmed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.bomb.nonarmed.NormalBomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class NormalBombFactory {
    public static NormalBomb create(World world, Vec2 position) {
        Color color = Color.LIGHT_GRAY;
        Brush brush = BrushFactory.get(color);
        return new NormalBomb(world, position, brush);
    }
}
