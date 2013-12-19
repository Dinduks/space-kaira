package fr.upem.spacekaira.shape;

import org.jbox2d.dynamics.Body;

@FunctionalInterface
public interface Collidable {
    boolean isCollide(AbstractShape as);
}
