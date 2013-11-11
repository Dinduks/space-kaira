import fr.umlv.zen.Application;
import fr.umlv.zen.ApplicationContext;
import fr.umlv.zen.KeyboardEvent;
import fr.umlv.zen.KeyboardKey;
import fr.upem.spacekaira.DrawingHelpers;

import java.awt.*;

public class Main {
    final static Font MAIN_SCREEN_FONT = new Font("arial", Font.BOLD, 30);
    final static int WIDTH = 800;
    final static int HEIGHT = 600;
    final static int NORMAL_MODE_POS_Y = 200;
    final static int HARDCORE_MODE_POS_Y = 250;
    final static int BOTH_MODES_POS_X = 200;

    public static void main(String[] args) {
        Application.run("Space Kaira", WIDTH, HEIGHT, (context) -> {
            context.render((graphics) -> graphics.setBackground(Color.black));
            setupModesScreen(context);
        });
    }

    private static void setupModesScreen(ApplicationContext context) {
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
                start(isNormalMode);
            }
        }
    }

    private static void start(boolean isNormalMode) {
        if (isNormalMode) System.out.println("Started in normal mode.");
        else System.out.println("Started in hardcore mode.");
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
