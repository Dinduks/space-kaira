package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.game.Viewport;

/**
 * This interface represents the ability of an enemy character to shoot
 */
public interface Shooter {
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
