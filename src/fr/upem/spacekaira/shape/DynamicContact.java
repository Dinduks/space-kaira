package fr.upem.spacekaira.shape;

/*
    This interface should be implemented by all figure who should contact with other
 */
public interface DynamicContact {
    /**
     * Computes pre-solve collisions
     */
    void computeTimeStepData();

    /**
     * Returns true if the character should be destroyed
     *
     * @return true if the character should be destroyed, false otherwise
     */
    boolean isDead();
}
