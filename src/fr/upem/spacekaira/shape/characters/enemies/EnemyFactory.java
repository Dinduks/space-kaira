package fr.upem.spacekaira.shape.characters.enemies;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.brush.BrushFactory;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.Objects;

public class EnemyFactory {
    private World world;

    public EnemyFactory(World world) {
        this.world = world;
    }

    public Enemy create(float x, float y, EnemyType type) {
        Objects.requireNonNull(type);
        Brush enemyColor = BrushFactory.getRandomBrush(false);
        Brush bulletColor = BrushFactory.get(Color.RED);

        switch (type) {
            case TIE: return new TIE(world,x,y,enemyColor,bulletColor);
            case SQUADRON: return new Squadron(world,x,y,enemyColor,bulletColor);
            case CRUISER: return new IntergalacticCruiser(world,x,y,enemyColor,bulletColor);
            case BRUTE: return new Brute(world,x,y,enemyColor,bulletColor);
            case ROTATINGTRIANGLE: return new RotatingTriangle(world,x,y,enemyColor,bulletColor);
        }
        assert false;
        return null;
    }
}
