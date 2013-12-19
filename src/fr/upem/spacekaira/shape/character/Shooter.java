package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Draw;

import java.util.List;

/**
 * This interface represent the ability to a character to shoot, and make some stuff with his bullet
 */
public interface Shooter {

    public void shoot();
    public void checkForBulletOutScreen(Draw d);
    default public void checkForBulletInPlanets(List<Planet> planets){}
}
