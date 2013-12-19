package fr.upem.spacekaira.shape;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import java.awt.*;

/**
 * Represent a base to construct a figure an contains drawing method
 */
public abstract class AbstractShape implements Drawable {
    protected Body body;

    @Override
    public void draw(Graphics2D graphics,Draw d) {
        Fixture list = body.m_fixtureList;

        while (list != null) {
            switch (list.getType()){
                case CIRCLE: d.drawCircle(list, graphics);
                    break;
                case EDGE: d.drawEdge(list, graphics);
                    break;
                case POLYGON: d.drawPolygon(list, graphics);
                    break;
                case CHAIN: assert false; //no chain in this project
                    break;
            }
            list = list.getNext();
        }
    }

    public boolean equalsBody(Body other) {
        return other == body;
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }
}
