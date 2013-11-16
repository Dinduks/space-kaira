package fr.upem.spacekaira.basicshapes;

import fr.upem.spacekaira.position.Position;

/**
 * Represents a circle that has a radius
 */
public class Circle extends BasicShape {
    private int radius;
    private Position position;

    public Circle(Position position, int radius) {
        setPosition(position);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
