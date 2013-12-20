package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.DynamicContact;
import org.jbox2d.dynamics.Fixture;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This abstract class represent a base to construct a Enemy
 */
public abstract class Enemy extends AbstractShape implements Shooter, DynamicContact {
    static {BULLET_COLOR = new Brush(Color.RED,true);}

    protected static final Brush BULLET_COLOR;
    protected List<Bullet> bullets;

    public Enemy() {
        this.bullets = new LinkedList<Bullet>();
    }

    @Override
    public void draw(Graphics2D graphics, Draw d) {
        super.draw(graphics, d);
        bullets.forEach(b->b.draw(graphics,d));
    }

    @Override
    public void checkForBulletOutScreen(Draw d) {
        Bullet.checkForBulletsOutScreen(d,bullets);
    }

    @Override
    public void computeTimeStepData() {
        for(Fixture fix = body.getFixtureList();fix != null;fix = fix.getNext()) {
            if(fix.getUserData() == Brush.DESTROY_BRUSH) {
                body.setUserData(Brush.DESTROY_BRUSH);
            }
        }

        Iterator<Bullet> it = bullets.iterator();
        while(it.hasNext()) {
            Bullet b = it.next();
            if(b.isDie()) {
                b.destroy();
                it.remove();
            }
        }
    }
}
