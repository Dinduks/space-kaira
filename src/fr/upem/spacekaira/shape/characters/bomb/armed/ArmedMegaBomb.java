package fr.upem.spacekaira.shape.characters.bomb.armed;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.brush.BrushFactory;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a mega bomb that has been dropped
 * The bomb disappears 10 seconds after it has been thrown
 */
public class ArmedMegaBomb extends AbstractArmedBomb {
    private List<Enemy> enemiesToDestroy = new ArrayList<>();

    /**
     * Builds A new armed mega bomb
     *
     * @param world               The current world
     * @param position            The position of the armed bomb
     * @param initialBrush        Its brush when its dropped
     */
    public ArmedMegaBomb(World world, Vec2 position, Brush initialBrush) {
        super(world, position, initialBrush);
    }

    /**
     * Creates a ready-to-use armed mega bomb
     *
     * @param world    The current world
     * @param position The position where should the bomb be dropped
     * @return         The created mega bomb
     */
    public static ArmedMegaBomb create(World world, Vec2 position) {
        Color darkGoldColor = new Color(210, 105, 30);
        Brush initialBrush = BrushFactory.get(darkGoldColor);

        return new ArmedMegaBomb(world, position, initialBrush);
    }

    @Override
    public boolean detonate(List<Enemy> enemies) {
        if (enemiesToDestroy.isEmpty()) {
            enemiesToDestroy = getCloseEnemies(enemies);
            return true;
        } else {
            if (System.currentTimeMillis() - getDropTime() >= 10000) {
                return false;
            }

            Iterator<Enemy> iterator = enemiesToDestroy.iterator();
            while (iterator.hasNext()) {
                Enemy enemy = iterator.next();
                if (roundedPosEqual(getPosition(), enemy.getPosition())) {
                    iterator.remove();
                    enemy.destroy();
                    enemies.remove(enemy);
                    continue;
                }

                Vec2 dir = getBody().getWorldCenter().sub(enemy.getPosition());
                dir.normalize();
                dir.mulLocal(10000);
                enemy.moveToward(dir);
            }

            return !enemiesToDestroy.isEmpty();
        }
    }

    /**
     * Takes a collection of {@code Enemy} and returns a view containing the
     * enemies 15f away of the bomb
     *
     * @param enemies a list of {@link Enemy}
     * @return a list of close {@link Enemy}
     */
    private List<Enemy> getCloseEnemies(List<Enemy> enemies) {
        return new LinkedList<>(enemies.stream()
                .filter(e -> {
                    float d = Util.distanceBetweenVectors(e.getPosition(),
                            getPosition());
                    return d <= 20;
                })
                .collect(Collectors.toList()));
    }

    /**
     * Takes two vectors, round their positions and check if they're equal
     *
     * @param position1 The first position
     * @param position2 The second position
     * @return          true if the rounded positions are equal, false otherwise
     */
    private boolean roundedPosEqual(Vec2 position1, Vec2 position2) {
        return (Math.ceil(position1.x) == Math.ceil(position2.x) &&
                (Math.ceil(position1.y) == Math.ceil(position2.y)));
    }
}
