package fr.upem.spacekaira.shape.characters.enemies;

import fr.upem.spacekaira.game.Viewport;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Vec2;

import java.util.*;

/**
 * This class creates all the enemies during the game, generating enemy waves
 */
public class EnemyWavesGenerator {

    /* Queue to store enemy waves (number of enemy by wave) */
    private Queue<EnemyWave> wavesQueue;
    /*  List that stores the current enemies */
    private List<Enemy> currentWave;
    private EnemyFactory enemyFactory;
    /* Represent the Max between height and width */
    private int maxLength;
    private Viewport viewport;
    private Ship ship;

    /**
     *  Main constructor to instantiate an EnemyWavesGenerator
     * @param enemyFactory an instance of the class EnemyFactory
     * @param viewport an instance of the ViewPort class
     * @param ship the player ship
     * @param enemyWaveList A list who represent all wave of enemy during the game
     * @param enemies
     */
    public EnemyWavesGenerator(EnemyFactory enemyFactory, Viewport viewport,
                               Ship ship, List<EnemyWave> enemyWaveList,
                               List<Enemy> enemies) {
        Objects.requireNonNull(enemyFactory);
        Objects.requireNonNull(viewport);
        Objects.requireNonNull(ship);
        this.wavesQueue = new ArrayDeque<>(enemyWaveList);
        this.currentWave = enemies;
        this.enemyFactory = enemyFactory;
        this.viewport = viewport;
        this.maxLength = Math.max(viewport.getScreenHeight(),viewport.getScreenWidth());
        this.ship = ship;
    }

    /**
     * Get enemy who are on the map
     * @return a list who contains the current enemy
     */
    public List<Enemy> getEnemies() {
        if (wavesQueue.size() != 0 && currentWave.size() == 0) {
            generateNextWave();
        }
        return currentWave;
    }

    /**
     * This method should call when all enemy of the current wave are dead
     * @warring this method feed the current enemy list {@see currentWave}
     */
    private void generateNextWave() {
        EnemyWave enemyWave = wavesQueue.poll();
        if (enemyWave == null || currentWave.size() != 0) return;
        float height = maxLength/viewport.getCameraScale();
        Vec2 start = new Vec2(0,height).add(ship.getPosition());
        Vec2 rotate = new Vec2();
        Rot rot = new Rot(6.28f/enemyWave.getNumberOfEnemies()); /* 2.PI / number */

        for (EnemyType enemyType : enemyWave) {
            Rot.mulTrans(rot, start, rotate);
            Enemy enemy = enemyFactory.create(rotate.x, rotate.y, enemyType);
            currentWave.add(enemy);
            start = new Vec2(rotate);
        }
    }

    /**
     * Tell to the user that there is no more enemy to generate
     * @return true is there is no more enemy, false otherwise
     */
    public boolean noMoreEnemies() {
        return wavesQueue.size() == 0 && currentWave.size() == 0;
    }

    public int wavesLeft() {
        return wavesQueue.size();
    }

    public int enemiesLeftInCurrentWave() {
        return currentWave.size();
    }
}
