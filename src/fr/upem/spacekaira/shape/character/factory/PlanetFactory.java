package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Planet;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class PlanetFactory {
    private World world;

    public PlanetFactory(World world) {
        this.world = world;
    }

    public Planet createPlanet(float x, float y) {
        return new Planet(world, x, y, 4, BrushFactory.get(Color.BLUE));
    }
}
