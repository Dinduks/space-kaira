package fr.upem.spacekaira.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the configuration of the game
 */
@XmlRootElement
public class Configuration {
    private int gameDuration;
    private int planetsDensity;

    public Configuration() {
    }

    public int getGameDuration() {
        return gameDuration;
    }

    @XmlElement
    private void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getPlanetsDensity() {
        return planetsDensity;
    }

    @XmlElement
    public void setPlanetsDensity(int planetsDensity) {
        this.planetsDensity = planetsDensity;
    }
}
