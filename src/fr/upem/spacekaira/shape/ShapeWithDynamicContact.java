package fr.upem.spacekaira.shape;

public abstract class ShapeWithDynamicContact extends AbstractShape
        implements DynamicContact {
    @Override
    public boolean isDead() {
        return getBody().getFixtureList().getUserData() == Brush.DESTROY_BRUSH;
    }
}
