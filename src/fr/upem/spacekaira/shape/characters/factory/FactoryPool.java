package fr.upem.spacekaira.shape.characters.factory;

import org.jbox2d.dynamics.World;

public class FactoryPool {
    private EnemyFactory enemyFactory;
    private PlanetFactory planetFactory;
    private ShipFactory shipFactory;

    public FactoryPool(World world) {
        this.enemyFactory = new EnemyFactory(world);
        this.planetFactory = new PlanetFactory(world);
        this.shipFactory = new ShipFactory(world);
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
