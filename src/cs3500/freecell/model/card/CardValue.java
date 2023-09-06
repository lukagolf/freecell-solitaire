package cs3500.freecell.model.card;

/**
 * There are thirteen card values in the game of Freecell.
 * -ace (labelled A)
 * -two to ten (labeled 2 to 10)
 * -Jack (labelled J)
 * -Queen (labelled Q)
 * -King (labelled K)
 */
public enum CardValue {
  ACE("A", 1), TWO("2", 2), THREE("3", 3), FOUR("4", 4), FIVE("5", 5),
  SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9), TEN("10", 10),
  JACK("J",11), QUEEN("Q", 12), KING("K", 13);

  private final String valueLabel;
  private final int position;

  /**
   * Constructs a {@code CardValue} object.
   *
   * @param v the value label
   * @param n the numeric value of the card
   * @throws  IllegalArgumentException if v is set to null,
   *          if value label is invalid, if numeric value of the card is invalid
   */
  CardValue(String v, int n) throws IllegalArgumentException {
    if (v == null) {
      throw new IllegalArgumentException("Value label is null.");
    }
    if (!v.equals("A") && !v.equals("2") && !v.equals("3") && !v.equals("4") && !v.equals("5")
        && !v.equals("6") && !v.equals("7") && !v.equals("8") && !v.equals("9") && !v.equals("10")
        && !v.equals("J") && !v.equals("Q") && !v.equals("K")) {
      throw new IllegalArgumentException("Value label is invalid.");
    }
    if (n != 1 && n != 2 && n != 3 && n != 4 && n != 5
        && n != 6 && n != 7 && n != 8 && n != 9 && n != 10
        && n != 11 && n != 12 && n != 13) {
      throw new IllegalArgumentException("The numeric value of the card is invalid.");
    }
    this.valueLabel = v;
    this.position = n;
  }

  public String getValueLabel() {
    return valueLabel;
  }

  public int getPosition() {
    return position;
  }
}
