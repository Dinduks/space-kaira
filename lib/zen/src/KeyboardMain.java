import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;

public class KeyboardMain {
  public static void main(String[] args) {
    int WIDTH = 800;
    int HEIGHT = 600;

    Application.run("Keyboard", WIDTH, HEIGHT, context -> {
      Random random = new Random(0);
      Font font = new Font("arial", Font.BOLD, 30);
      for(;;) {
        KeyboardEvent event = context.waitKeyboard();
        if (event == null) {
          return;
        }
        context.render(graphics -> {
          float x = random.nextInt(WIDTH);
          float y = random.nextInt(HEIGHT);

          Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
          graphics.setPaint(color);
          graphics.setFont(font);
          graphics.drawString(event.toString(), x, y);
        });
      }
    });
  }
}
