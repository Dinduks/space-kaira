package fr.upem.spacekaira.shape.characters.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.List;

public class ShipFactory {
    private World world;

    public ShipFactory(World world) {
        this.world = world;
    }

    public Ship createShip(List<Enemy> enemies, boolean hardcore) {
        return new Ship(world, enemies, BrushFactory.get(Color.BLUE),
                BrushFactory.get(Color.GREEN), !hardcore);
    }
}
