package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Viewport;
import fr.upem.spacekaira.shape.character.factory.EnemyFactory;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.*;

/**
 * This class create all enemy during the game, generating enemy wave
 */
public class EnemyWavesGenerator {
    /**
     * Queue to store enemy waves (number of enemy by wave)
     */
    private Queue<Integer> wavesQueue;
    /**
     *  List to store current enemy
     */
    private List<Enemy> currentWave;
    /**
     * EnemyFactory to create rand enemy
     */
    private EnemyFactory enemyFactory;
    /**
     * Represent the Max between height and width
     */
    private int maxLength;
    /**
        ViewPort
     */
    private Viewport viewport;

    /**
     *  Main constructor to instantiate an EnemyWavesGenerator
     * @param enemyFactory an instance of the class EnemyFactory
     * @param viewport an instance of the ViewPort class
     * @param waves Table who contains number of enemy by wave wave[0] == number of enemy in the first wave, etc
     * @param size number of wave
     */
    public EnemyWavesGenerator(EnemyFactory enemyFactory,Viewport viewport, int[] waves, int size) {
        if(size < waves.length)
            throw new IllegalArgumentException();
        Objects.requireNonNull(enemyFactory);
        Objects.requireNonNull(viewport);
        this.wavesQueue = new ArrayDeque<>();
        for (int i=0;i<size;i++) {
            this.wavesQueue.add(waves[i]);
        }
        this.currentWave = new ArrayList<>();
        this.enemyFactory = enemyFactory;
        this.viewport = viewport;
        this.maxLength = Math.max(viewport.getScreenHeight(),viewport.getScreenWidth());
    }

    /**
     * Get enemy who are on the map
     * @return an unmodifiable list of enemy
     */
    public List<Enemy> getEnemy() {
        if (currentWave.size() == 0)
            generateNextWave();
        return Collections.unmodifiableList(currentWave);
    }

    /**
     * This method should call when all enemy of the current wave are dead
     * @warring this method feed the current enemy list {@see currentWave}
     */
    private void generateNextWave() {
        Integer numberOfEnemy;
        if ((numberOfEnemy = wavesQueue.poll()) == null || currentWave.size() != 0) return;
        float height = maxLength/viewport.getCameraScale();
        Vec2 start = new Vec2(0,height+(height/8));
        Vec2 rotate = new Vec2();
        Rot rot = new Rot(2.28f/numberOfEnemy); /* 2.PI / number */

        for(int i=0;i<numberOfEnemy;i++) {
            Rot.mulTrans(rot,start,rotate);
            currentWave.add(enemyFactory.createEnemy(rotate.x,rotate.y));
        }
    }
}
