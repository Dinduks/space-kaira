package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.shape.Viewport;

/**
 * This interface represent the ability to a character to shoot, and make some stuff with his bullet
 */
public interface ShooterEnemy {
    /**
     * Orders a shooter to shoot
     *
     * @param ship The player's ship
     */
    void shoot(Ship ship);

    /**
     * Orders a shooter to remove his bullets out of the screen
     * @param viewport The game's viewport
     */
    void checkForBulletOutScreen(Viewport viewport);

    /**
     * Orders a shooter to destroy his dead bullets
     */
    void checkForDeadBullet();
}
