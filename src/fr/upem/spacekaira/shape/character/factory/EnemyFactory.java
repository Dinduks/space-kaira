package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.IntergalacticCruiser;
import fr.upem.spacekaira.shape.character.Squadron;
import fr.upem.spacekaira.shape.character.TIE;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class EnemyFactory {
    private World world;
    private BrushFactory bF;
    private Random rand;
    public enum EnemyType {TIE, SQUADRON, CRUISER; }


    public EnemyFactory(World world,BrushFactory brushFactory) {
        this.world = world;
        this.bF = brushFactory;
        this.rand = new Random();
    }

    public Enemy createEnemy(float x, float y) {
       return createEnemy(x,y,EnemyType.values()[rand.nextInt(EnemyType.values().length)]);
    }

    public Enemy createEnemy(float x, float y, EnemyType type) {
        Objects.requireNonNull(type);
        Brush enemyColor = bF.getRandBrush(false);
        Brush bulletColor = bF.createBrush(Color.RED,true);

        switch (type) {
            case TIE: return new TIE(world,x,y,enemyColor,bulletColor);
            case SQUADRON: return new Squadron(world,x,y,enemyColor,bulletColor);
            case CRUISER: return new IntergalacticCruiser(world,x,y,enemyColor,bulletColor);
        }
        assert false;
        return null;
    }
}
