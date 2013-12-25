package fr.upem.spacekaira.shape;

/*
    This interface should be implemented by all figure who should contact with other
 */
public interface DynamicContact {
    /*
        Compute pre-solve collisions
     */
    public void computeTimeStepData();

    /*
        return true if the character should be destroy
     */
    public boolean isDead();
}
