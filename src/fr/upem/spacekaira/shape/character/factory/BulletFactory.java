package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.Bullet;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class BulletFactory {
    private World world;

    public BulletFactory(World world) {
        this.world = world;
    }

    public Bullet createBulletShip(Vec2 position,Vec2 velocity,float angle,Brush color) {
        return Bullet.createShipBullet(world,position,velocity,angle,color);
    }

    public Bullet createBulletEnemy(Vec2 position,Vec2 velocity,float angle,Brush color) {
        return Bullet.createEnemyBullet(world,position,velocity,angle,color);
    }
}
