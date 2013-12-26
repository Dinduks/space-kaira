package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.Ship;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.List;

public class ShipFactory {
    private World world;
    private BrushFactory brushFactory;

    public ShipFactory(World world, BrushFactory brushFactory) {
        this.world = world;
        this.brushFactory = brushFactory;
    }

    // TODO: Do I really need to pass enemies here?!
    public Ship createShip(List<Enemy> enemies, boolean hardcore) {
        return new Ship(world, enemies,
                brushFactory.createBrush(Color.BLUE,true),
                brushFactory.createBrush(Color.GREEN,true));
    }
}
