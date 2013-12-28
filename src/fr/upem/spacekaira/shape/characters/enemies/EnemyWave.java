package fr.upem.spacekaira.shape.characters.enemies;

import javax.xml.bind.annotation.XmlElement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a wave of enemies
 */
public class EnemyWave implements Iterable<EnemyType> {
    @XmlElement(name="enemy")
    private List<EnemyType> enemies = new LinkedList<>();

    // For JAXB
    public EnemyWave() {}

    public EnemyWave(List<EnemyType> enemies) {
        this.enemies.addAll(enemies);
    }

    public int getNumberOfEnemies(){
        return enemies.size();
    }

    public List<EnemyType> getEnemies() {
        return enemies;
    }

    @Override
    public Iterator<EnemyType> iterator() {
        return enemies.iterator();
    }
}
