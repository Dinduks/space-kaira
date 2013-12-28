package fr.upem.spacekaira.config;

import fr.upem.spacekaira.shape.characters.enemies.EnemyWavesGenerator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration of the game
 */
@XmlRootElement
public class Configuration {
    @XmlElement private int gameDuration;
    @XmlElement private int planetsDensity;
    @XmlElement private int bombsFrequency;
    @XmlElement private int megaBombsPerCent;
    @XmlElementWrapper(name = "enemyWaves")
    @XmlElement(name = "enemyWave")
    private List<EnemyWavesGenerator.EnemyWave> enemyWaves;

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

    public int getMegaBombsPerCent() {
        return megaBombsPerCent;
    }

    public List<EnemyWavesGenerator.EnemyWave> getEnemyWaves() {
        return enemyWaves;
    }
}
