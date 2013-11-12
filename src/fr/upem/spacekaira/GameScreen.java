package fr.upem.spacekaira;

public class GameScreen implements Screen {
    private final boolean isNormalMode;

    public GameScreen(boolean isNormalMode) {
        this.isNormalMode = isNormalMode;
    }

    @Override
    public void start() {
        if (isNormalMode) System.out.println("Started in normal mode.");
        else System.out.println("Started in hardcore mode.");
    }
}
