package fr.upem.spacekaira.shape.characters.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.shape.characters.FixtureType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.List;

import static fr.upem.spacekaira.shape.characters.bomb.BombType.NORMAL_BOMB;

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
     * @param brushAfterExploding Its brush when it starts exploding
     */
    public ArmedNormalBomb(World world, Vec2 position, Brush initialBrush,
                           Brush brushAfterExploding) {
        super(world, position, initialBrush, brushAfterExploding, NORMAL_BOMB);
    }

    /**
     * Creates a ready-to-use armed bomb
     *
     * @param world    The current world
     * @param position The position where should the bomb be dropped
     * @return         The created bomb
     */
    public static ArmedNormalBomb create(World world, Vec2 position) {
        Brush initialBrush = BrushFactory.get(Color.RED);
        Brush brushAfterExploding = BrushFactory.get(Color.RED, false);

        return new ArmedNormalBomb(world, position, initialBrush,
                brushAfterExploding);
    }

    @Override
    public boolean explode(List<Enemy> enemies) {
        if (radius >= 10.0f) return false;
        circleShape.setRadius((radius = radius + 0.5f));
        armedBombFixtureDef.shape = circleShape;
        armedBombFixtureDef.userData = brushAfterExploding;
        armedBombFixtureDef.filter.maskBits = FixtureType.ENEMY;
        body.destroyFixture(fixture);
        body.createFixture(armedBombFixtureDef);

        return true;
    }
}
