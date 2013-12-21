package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Ship;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class ShipFactory {
    private World world;
    private BrushFactory bF;

    public ShipFactory(World world,BrushFactory brushFactory) {
        this.world = world;
        this.bF = brushFactory;
    }

    public Ship createShip(boolean hardcore) {
        return new Ship(world,bF.createBrush(Color.BLUE,true), bF.createBrush(Color.GREEN,true));
    }
}
