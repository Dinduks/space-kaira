package fr.upem.spacekaira.shape.characters.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.*;
import java.util.stream.Collectors;

import static fr.upem.spacekaira.shape.characters.bomb.BombType.MEGA_BOMB;

/**
 * Represents a mega bomb that has been dropped
 * The bomb disappears 10 seconds after it has been thrown
 */
public class ArmedMegaBomb extends AbstractArmedBomb {
    /**
     * Build A new armed bomb
     *
     * @param world               The current world
     * @param position            The position of the armed bomb
     * @param initialBrush
     * @param brushAfterExploding
     */
    public ArmedMegaBomb(World world, Vec2 position, Brush initialBrush,
                         Brush brushAfterExploding) {
        super(world, position, initialBrush, brushAfterExploding, MEGA_BOMB);
    }

    private List<Enemy> enemiesToDestroy = new ArrayList<>();
    @Override
    public boolean explode(List<Enemy> enemies) {
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
                    enemies.remove(enemy);
                    continue;
                }

                Vec2 dir = body.getWorldCenter().sub(enemy.getPosition());
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
     * @param enemies
     * @return
     */
    public List<Enemy> getCloseEnemies(List<Enemy> enemies) {
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
     * @param position1
     * @param position2
     * @return
     */
    private boolean roundedPosEqual(Vec2 position1, Vec2 position2) {
        return (Math.ceil(position1.x) == Math.ceil(position2.x) &&
                (Math.ceil(position1.y) == Math.ceil(position2.y)));
    }
}
