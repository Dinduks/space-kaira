package fr.upem.spacekaira.basicshapes;

import fr.upem.spacekaira.position.Position;
import org.jbox2d.dynamics.Body;

/**
 * Represents a shape in the game
 * Has a {@code Position} and a JBox2D body
 */
abstract public class BasicShape {
    private Position position;
    private Body physicalBody;

    public Position getPosition() {
        return position;
    }

    protected void setPosition(Position position) {
        this.position = position;
    }
}
