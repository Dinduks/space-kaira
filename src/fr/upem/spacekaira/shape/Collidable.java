package fr.upem.spacekaira.shape;

@FunctionalInterface
public interface Collidable {
    boolean isCollide(AbstractShape abstractShape);
}
