package fr.upem.spacekaira.shape.characters.factory;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.characters.enemies.*;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class EnemyFactory {
    private World world;
    private Random rand;
    public enum EnemyType {TIE, SQUADRON, CRUISER, BRUTE, ROTTRIANGLE }

    public EnemyFactory(World world) {
        this.world = world;
        this.rand = new Random();
    }

    public Enemy createEnemy(float x, float y) {
       return createEnemy(x,y,EnemyType.values()[rand.nextInt(EnemyType.values().length)]);
    }

    public Enemy createEnemy(float x, float y, EnemyType type) {
        Objects.requireNonNull(type);
        Brush enemyColor = BrushFactory.getRandomBrush(false);
        Brush bulletColor = BrushFactory.get(Color.RED);

        switch (type) {
            case TIE: return new TIE(world,x,y,enemyColor,bulletColor);
            case SQUADRON: return new Squadron(world,x,y,enemyColor,bulletColor);
            case CRUISER: return new IntergalacticCruiser(world,x,y,enemyColor,bulletColor);
            case BRUTE: return new Brute(world,x,y,enemyColor,bulletColor);
            case ROTTRIANGLE: return new RotatingTriangle(world,x,y,enemyColor,bulletColor);
        }
        assert false;
        return null;
    }
}
