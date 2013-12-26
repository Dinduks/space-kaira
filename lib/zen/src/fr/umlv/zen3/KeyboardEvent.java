package fr.umlv.zen3;

import java.util.Set;

import fr.umlv.zen3.KeyboardKey.Modifier;

/**
 * A keyboard key and its set of modifiers.
 */
public class KeyboardEvent {
  private final KeyboardKey key;
  private final int modifiers;
  private final boolean released;
  private /*lazy*/ Set<Modifier> modifierSet;

  KeyboardEvent(KeyboardKey key, int modifiers, boolean released) {
    this.key = key;
    this.modifiers = modifiers;
    this.released = released;
  }
  
  /** Returns the keyboard key.
   * @return the keyboard key or {@link KeyboardKey#UNDEFINED} if the key is not recognized.
   */
  public KeyboardKey getKey() {
    return key;
  }
  
  /** Returns true if the keyboard key is released.
   * @return true is the keybord key is released.
   */
  public boolean isReleased() {
    return released;
  }
  
  /** A set of the modifier keys that can contain
   *  {@link KeyboardKey.Modifier#META}, {@link KeyboardKey.Modifier#CTRL}, {@link KeyboardKey.Modifier#ALT},
   *  {@link KeyboardKey.Modifier#SHIFT} and/or {@link KeyboardKey.Modifier#ALT_GR}. 
   * @return a set of modifier keys or an empty set if no modifier keys are used.
   */
  public Set<Modifier> getModifiers() {
    if (modifierSet != null)
      return modifierSet;
    return modifierSet = Modifier.modifierSet(modifiers);
  }
  
  @Override
  public String toString() {
    return getModifiers() + " " + getKey() + ' ' + isReleased();
  }
}
