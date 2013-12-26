package fr.upem.spacekaira.shape.character.bomb.armed;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.FixtureType;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static fr.upem.spacekaira.shape.character.bomb.BombType.MEGA_BOMB;

/**
 * Represents a bomb that has been dropped
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

    @Override
    public boolean explode(List<Enemy> enemies) {
        List<Enemy> closeEnemies = getCloseEnemies(enemies);
        closeEnemies.forEach(enemy -> {
            enemy.moveToward(getPosition());
        });

//        circleShape.setRadius((radius = radius + 0.5f));
//        armedBombFixtureDef.shape = circleShape;
//        armedBombFixtureDef.userData = brushAfterExploding;
//        armedBombFixtureDef.filter.maskBits = FixtureType.STD_ENEMY;
//        body.destroyFixture(fixture);
//        body.createFixture(armedBombFixtureDef);

        return true;
    }

    /**
     * Takes a collection of {@code Enemy} and returns a view containing the
     * enemies 15f away of the bomb
     *
     * @param enemies
     * @return
     */
    public List<Enemy> getCloseEnemies(List<Enemy> enemies) {
        return Collections.unmodifiableList(enemies)
                .stream()
                .filter(e ->
                        Util.distanceBetweenVectors(
                                e.getPosition(), getPosition()) >= 15
                )
                .collect(Collectors.toList());
    }
}
