package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.characters.Planet;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class PlanetFactory {
    private World world;

    public PlanetFactory(World world) {
        this.world = world;
    }

    public Planet create(float x, float y) {
        return new Planet(world, x, y, 4, BrushFactory.get(Color.BLUE));
    }
}
