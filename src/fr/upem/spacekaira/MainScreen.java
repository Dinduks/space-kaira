package fr.upem.spacekaira;

import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.KeyboardEvent;
import fr.umlv.zen.KeyboardKey;

import java.awt.*;

public class MainScreen implements Screen {
    private final ApplicationContext context;

    final static Font MAIN_SCREEN_FONT = new Font("arial", Font.BOLD, 30);
    final static int NORMAL_MODE_POS_Y = 200;
    final static int HARDCORE_MODE_POS_Y = 250;
    final static int BOTH_MODES_POS_X = 200;

    public MainScreen(ApplicationContext context, int width, int height) {
        this.context = context;

        context.render((graphics) -> {
            graphics.setBackground(Color.black);
            graphics.clearRect(0, 0, width, height);
        });
    }

    @Override
    public void start() {
        KeyboardEvent event;
        boolean isNormalMode = true;

        selectNormalMode(context);

        while (true) {
            event = context.waitKeyboard();

            if (event.getKey() == KeyboardKey.DOWN) {
                if (!isNormalMode) continue;
                isNormalMode = false;
                selectHardcoreMode(context);
            }

            if (event.getKey() == KeyboardKey.UP) {
                if (isNormalMode) continue;
                isNormalMode = true;
                selectNormalMode(context);
            }

            if (event.getKey() == KeyboardKey.SPACE) {
                (new GameScreen(isNormalMode)).start();
            }
        }
    }

    private static void selectNormalMode(ApplicationContext context) {
        context.render((graphics) -> {
            graphics.setFont(MAIN_SCREEN_FONT);
            DrawingHelpers.drawStringWithColor("Normal mode", BOTH_MODES_POS_X,
                    NORMAL_MODE_POS_Y, graphics, Color.red);
            DrawingHelpers.drawStringWithColor("Hardcore mode", BOTH_MODES_POS_X,
                    HARDCORE_MODE_POS_Y, graphics, Color.white);
        });
    }

    private static void selectHardcoreMode(ApplicationContext context) {
        context.render((graphics) -> {
            graphics.setFont(MAIN_SCREEN_FONT);
            DrawingHelpers.drawStringWithColor("Normal mode", BOTH_MODES_POS_X,
                    NORMAL_MODE_POS_Y, graphics, Color.white);
            DrawingHelpers.drawStringWithColor("Hardcore mode", BOTH_MODES_POS_X,
                    HARDCORE_MODE_POS_Y, graphics, Color.red);
        });
    }
}
