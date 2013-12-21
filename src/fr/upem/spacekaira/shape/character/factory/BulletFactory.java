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

    public Bullet createBullet(Vec2 position,Vec2 velocity,float angle,Brush color) {
        return new Bullet(world,position,velocity,angle,color);
    }
}
