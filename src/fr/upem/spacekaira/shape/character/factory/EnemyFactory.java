package fr.upem.spacekaira.shape.character.factory;

import fr.upem.spacekaira.shape.BrushFactory;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.IntergalacticCruiser;
import fr.upem.spacekaira.shape.character.Squadron;
import fr.upem.spacekaira.shape.character.TIE;
import org.jbox2d.dynamics.World;

import java.awt.*;

public class EnemyFactory {
    private World world;
    private BrushFactory bF;

    public EnemyFactory(World world,BrushFactory brushFactory) {
        this.world = world;
        this.bF = brushFactory;
    }

    public Enemy createEnemy(float x, float y) {
        //TODO random with all enemy
        //return new TIE(world,x,y,bF.createBrush(Color.BLUE,false),bF.createBrush(Color.RED,true));
        //return new IntergalacticCruiser(world,x,y,bF.createBrush(Color.BLUE,false),bF.createBrush(Color.RED,true));
        return new Squadron(world,x,y,bF.createBrush(Color.BLUE,false),bF.createBrush(Color.RED,true));
    }
}
