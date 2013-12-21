package fr.upem.spacekaira.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the configuration of the game
 */
@XmlRootElement
public class Configuration {
    private int gameDuration;

    public Configuration() {
    }

    @XmlElement
    private void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getGameDuration() {
        return gameDuration;
    }
}
