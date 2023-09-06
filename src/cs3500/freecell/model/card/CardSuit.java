package cs3500.freecell.model.card;

/**
 * There are four suits in the game of Freecell.
 * -clubs (♣)
 * -diamonds (♦)
 * -hearts (♥)
 * -spades (♠)
 */
public enum CardSuit {
  CLUBS("♣", "black"), SPADES("♠", "black"),
  DIAMONDS("♦", "red"), HEARTS("♥", "red");

  private final String suitLabel;
  private final String suitColor;

  /**
   * Constructs a {@code CardSuit} object.
   *
   * @param s the suit label
   * @param c the color of the card
   * @throws  IllegalArgumentException if any of the @params are set to null,
   *          if suit label is invalid, if card color is invalid
   */
  private CardSuit(String s, String c) throws IllegalArgumentException {
    if (s == null || c == null) {
      throw new IllegalArgumentException("Suit label or card color are null.");
    }
    if (!s.equals("♣")  && !s.equals("♠")  && !s.equals("♦") && !s.equals("♥")) {
      throw new IllegalArgumentException("Suit label is invalid.");
    }
    if (!c.equals("black") && !c.equals("red")) {
      throw new IllegalArgumentException("Card color is invalid.");
    }
    this.suitLabel = s;
    this.suitColor = c;
  }

  public String getSuitLabel() {
    return suitLabel;
  }

  public String getSuitColor() {
    return suitColor;
  }
}
