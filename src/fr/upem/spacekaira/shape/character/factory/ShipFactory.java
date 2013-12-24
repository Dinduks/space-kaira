package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Ship;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class ShipFactory {
    private World world;
    private BrushFactory brushFactory;

    public ShipFactory(World world, BrushFactory brushFactory) {
        this.world = world;
        this.brushFactory = brushFactory;
    }

    public Ship createShip(boolean hardcore) {
        return new Ship(world,
                brushFactory.createBrush(Color.BLUE,true),
                brushFactory.createBrush(Color.GREEN,true));
    }
}
