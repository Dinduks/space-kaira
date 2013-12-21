package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import org.jbox2d.dynamics.World;
import sun.net.www.content.text.plain;

public class FactoryPool {

    private BulletFactory bulletFactory;
    private EnemyFactory enemyFactory;
    private PlanetFactory planetFactory;
    private ShipFactory shipFactory;
    private BrushFactory brushFactory;

    public FactoryPool(World world) {
        this.brushFactory = new BrushFactory();
        this.bulletFactory = new BulletFactory(world);
        this.enemyFactory = new EnemyFactory(world,brushFactory);
        this.planetFactory = new PlanetFactory(world,brushFactory);
        this.shipFactory = new ShipFactory(world,brushFactory);
    }

    public BulletFactory getBulletFactory() {
        return bulletFactory;
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
