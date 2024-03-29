package fr.umlv.zen3;

import java.awt.Graphics2D;
import java.util.function.Consumer;

/**
 * Provided by the application framework, this object allows
 * to render a graphic code into the drawing area of the application
 * and to wait/ask for keyboard events.
 */
public interface ApplicationContext {
  /** Returns the first keyboard pressed event since or the application was
   * started or {@link #pollKeyboard()}, {@link #waitKeyboard()},
   * {@link #pollKeys()} or {@link #waitKeys()} was called or null
   * if the user didn't press a key.
   * 
   *  
   * @return a keyboard pressed event or null otherwise.
   */
  public KeyboardEvent pollKeyboard();
  
  /** Returns the first keyboard pressed/released event since or the application
   * was started or {@link #pollKeyboard()}, {@link #waitKeyboard()},
   * {@link #pollKeys()} or {@link #waitKeys()} was called or if the user
   * didn't press a key, this call wait until a key is pressed/released
   * or the application thread is interrupted.
   *  
   * @return a keyboard pressed event or null if the application thread
   * is interrupted.
   */
  public KeyboardEvent waitKeyboard();
  
  /** Returns the first keyboard pressed/released event since or the application
   * was started or {@link #pollKeyboard()}, {@link #waitKeyboard()},
   * {@link #pollKeys()} or {@link #waitKeys()} was called
   * or null if the user didn't press or release a key.
   *  
   * @return a keyboard event or null otherwise.
   */
  public KeyboardEvent pollKeys();
  
  /** Returns the first keyboard event since or the application was started or
   * {@link #pollKeyboard()} or {@link #waitKeyboard()} was called
   * or if the user didn't press a key, this call wait until a key is pressed
   * or the application thread is interrupted.
   *  
   * @return a keyboard event or null if the application thread is interrupted.
   */
  public KeyboardEvent waitKeys();
  
  
  /** Used to ask the application to render a specific graphic code into the drawing area.
   *  In fact, the drawing code doesn't draw directly into the drawing area
   *  but draw into a back buffer that will be drawn to the screen.
   *  
   * @param rendererCode code that should be executed to drawn
   *                     to the drawing area.
   */
  public void render(Consumer<Graphics2D> rendererCode);
}