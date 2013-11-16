package fr.upem.spacekaira.basicshapes;

import fr.upem.spacekaira.position.Position;

/**
 * Represents a rectangle which has a height and a width
 */
public class Rectangle extends BasicShape {
    private int width;
    private int height;

    public Rectangle(Position position , int width, int height) {
        setPosition(position);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
