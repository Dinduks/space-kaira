package fr.upem.spacekaira.shape;

import fr.upem.spacekaira.draw.Drawable;
import fr.upem.spacekaira.game.Viewport;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import java.awt.*;

/**
 * Represents a base to construct shapes and contains drawing methods
 */
public abstract class AbstractShape implements Drawable {
    private Body body;

    @Override
    public void draw(Graphics2D graphics, Viewport viewport) {
        Fixture list = body.m_fixtureList;

        while (list != null) {
            switch (list.getType()){
                case CIRCLE: viewport.drawCircle(list, graphics);
                    break;
                case EDGE: viewport.drawEdge(list, graphics);
                    break;
                case POLYGON: viewport.drawPolygon(list, graphics);
                    break;
                case CHAIN: throw new UnsupportedOperationException();
            }
            list = list.getNext();
        }
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    public void destroy() {
        body.getWorld().destroyBody(body);
    }

    /**
     * Return the WorldShip position
     * @return a Vec2 who represent the position
     */
    public Vec2 getPosition() {
        return body.getWorldCenter();
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}

