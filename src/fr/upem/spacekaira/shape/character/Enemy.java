package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Viewport;
import fr.upem.spacekaira.shape.DynamicContact;
import org.jbox2d.dynamics.Fixture;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This abstract class represent a base to construct a Enemy
 */
public abstract class Enemy extends AbstractShape implements Shooter, DynamicContact {
    protected final Brush enemyColor;
    protected final Brush bulletColor;
    protected List<Bullet> bullets;

    public Enemy(Brush color, Brush bulletColor) {
        this.bullets = new LinkedList<>();
        this.enemyColor = color;
        this.bulletColor = bulletColor;
    }

    @Override
    public void draw(Graphics2D graphics, Viewport viewport) {
        super.draw(graphics, viewport);
        bullets.forEach(b->b.draw(graphics, viewport));
    }

    @Override
    public void checkForBulletOutScreen(Viewport viewport) {
        Bullet.checkForBulletsOutScreen(viewport,bullets);
    }

    @Override
    public void computeTimeStepData() {
        for (Fixture fix = body.getFixtureList(); fix != null; fix = fix.getNext()) {
            if (fix.getUserData() == Brush.DESTROY_BRUSH) {
                body.setUserData(Brush.DESTROY_BRUSH);
            }
        }

        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.isDead()) {
                b.destroy();
                it.remove();
            }
        }
    }
}
