package fr.upem.spacekaira.config;

import fr.upem.spacekaira.shape.characters.enemies.EnemyWave;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration of the game
 */
@XmlRootElement
public final class Configuration {
    @XmlElement private int screenWidth;
    @XmlElement private int screenHeight;
    @XmlElement private int gameDuration;
    @XmlElement private int planetsDensity;
    @XmlElement private int bombsFrequency;
    @XmlElement private int megaBombsRate;
    @XmlElement private int bulletsFrequency;
    @XmlElement private float shipSpeed;
    @XmlElementWrapper(name = "enemyWaves")
    @XmlElement(name = "enemyWave")
    private List<EnemyWave> enemyWaves;
    private boolean hardcore;

    private Configuration() {
        enemyWaves = new ArrayList<>();
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public int getPlanetsDensity() {
        return planetsDensity;
    }

    public int getBombsFrequency() {
        return bombsFrequency;
    }

    public int getMegaBombsRate() {
        return megaBombsRate;
    }

    public List<EnemyWave> getEnemyWaves() {
        return enemyWaves;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public int getBulletsFrequency() {
        return bulletsFrequency;
    }

    public float getShipSpeed() {
        return shipSpeed;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
