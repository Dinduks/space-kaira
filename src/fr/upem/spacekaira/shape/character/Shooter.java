package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Viewport;

/**
 * This interface represent the ability to a character to shoot, and make some stuff with his bullet
 */
public interface Shooter {
    public void shoot();
    public void checkForBulletOutScreen(Viewport viewport);
}
