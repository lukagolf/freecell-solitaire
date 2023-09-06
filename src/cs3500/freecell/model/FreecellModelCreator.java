package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.FreecellMultiMoveModel;

/**
 * Represents a factory class for the game of freecell.
 */
public class FreecellModelCreator {

  /**
   * Reperesents the types of models freecell game has.
   */
  public static enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Creates either Singlemove or Multimove model based on the game type provided.
   *
   * @param type represents the type of the game we want to create (singlemove/multimove)
   * @return a freecell model
   */
  public static FreecellModel create(GameType type) {
    if (type.equals(GameType.SINGLEMOVE)) {
      return new SimpleFreecellModel();
    }
    else {
      return new FreecellMultiMoveModel();
    }
    //throw new IllegalArgumentException("Invalid game type.");
  }
}
