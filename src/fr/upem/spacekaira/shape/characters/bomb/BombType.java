package fr.upem.spacekaira.shape.characters.bomb;

import fr.upem.spacekaira.shape.characters.FixtureType;

public enum BombType {
    NORMAL_BOMB, MEGA_BOMB;

    public static int getFixtureType(BombType bombType) {
        switch (bombType) {
            case NORMAL_BOMB:
                return FixtureType.BOMB;
            case MEGA_BOMB:
                return FixtureType.MBOMB;
            default:
                throw new IllegalArgumentException();
        }
    }
}
