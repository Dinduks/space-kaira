package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.Bomb;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class BombFactory {
    public static Bomb create(World world, Vec2 position, Brush color) {
        return new Bomb(world, position, color);
    }
}
