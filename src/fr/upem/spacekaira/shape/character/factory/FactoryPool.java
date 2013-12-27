package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import org.jbox2d.dynamics.World;

public class FactoryPool {
    private EnemyFactory enemyFactory;
    private PlanetFactory planetFactory;
    private ShipFactory shipFactory;
    private BrushFactory brushFactory;

    public FactoryPool(World world) {
        this.brushFactory = new BrushFactory();
        this.enemyFactory = new EnemyFactory(world, brushFactory);
        this.planetFactory = new PlanetFactory(world, brushFactory);
        this.shipFactory = new ShipFactory(world, brushFactory);
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    public PlanetFactory getPlanetFactory() {
        return planetFactory;
    }

    public ShipFactory getShipFactory() {
        return shipFactory;
    }
}
