package fr.upem.spacekaira.shape.characters.bomb.armed;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.brush.BrushFactory;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.shape.characters.FixtureType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.List;

/**
 * Represents a bomb that has been dropped
 */
public class ArmedNormalBomb extends AbstractArmedBomb {
    /**
     * Build A new armed bomb
     *
     * @param world               The current world
     * @param position            The position of the armed bomb
     * @param initialBrush        Its brush when its dropped
     */
    public ArmedNormalBomb(World world, Vec2 position, Brush initialBrush) {
        super(world, position, initialBrush);
    }

    /**
     * Creates a ready-to-use armed bomb
     *
     * @param world    The current world
     * @param position The position where should the bomb be dropped
     * @return         The created bomb
     */
    public static ArmedNormalBomb create(World world, Vec2 position) {
        Brush initialBrush = BrushFactory.get(Color.DARK_GRAY);

        return new ArmedNormalBomb(world, position, initialBrush);
    }

    @Override
    public boolean detonate(List<Enemy> enemies) {
        if (getRadius() >= 10.0f) return false;

        setRadius(getRadius() + 0.5f);
        getCircleShape().setRadius(getRadius());
        getArmedBombFixtureDef().shape = getCircleShape();
        getArmedBombFixtureDef().userData = BrushFactory.get(Color.RED, false);
        getArmedBombFixtureDef().filter.maskBits = FixtureType.ENEMY;
        getBody().destroyFixture(getFixture());
        getBody().createFixture(getArmedBombFixtureDef());

        return true;
    }
}
