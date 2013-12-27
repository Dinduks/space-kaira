package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Planet;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class PlanetFactory {
    private World world;
    private BrushFactory bF;

    public PlanetFactory(World world,BrushFactory brushFactory) {
        this.world = world;
        this.bF = brushFactory;
    }

    public Planet createPlanet(float x, float y) {
        return new Planet(world,x,y,4,bF.createBrush(Color.BLUE,true));
    }
}
