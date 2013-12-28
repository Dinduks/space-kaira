package fr.upem.spacekaira.shape.characters;

import fr.upem.spacekaira.shape.Viewport;

/**
 * This interface represent the ability to a character to shoot, and make some stuff with his bullet
 */
public interface ShooterEnemy {
    /*
        This method tell to a shooter that he can shoot, the AI decide
     */
    public void shoot(Ship ship);

    /*
        This method tell to a shooter that he should remove his bullet outOfScreen
     */
    public void checkForBulletOutScreen(Viewport viewport);

    /*
         This method tell to a shooter that he should remove his dead bullet outOfScreen
     */
    public void checkForDeadBullet();
}