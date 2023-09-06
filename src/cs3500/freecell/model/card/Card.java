package cs3500.freecell.model.card;

import java.util.Objects;

/**
 * Represents a card of a regular 52 card deck.
 */
public class Card {

  private final CardSuit suit;
  private final CardValue value;

  /**
   * Constructs a {@code Card} object.
   *
   * @param suit  the suit of the card
   * @param value the value of the card
   * @throws IllegalArgumentException if any of the @params are set to null
   */
  public Card(CardSuit suit, CardValue value) throws IllegalArgumentException {
    if (suit == null) {
      throw new IllegalArgumentException("Suit not initialized");
    }
    if (value == null) {
      throw new IllegalArgumentException("Value not initialized");
    }
    this.suit = suit;
    this.value = value;
  }

  /**
   * Gets the suit of this card.
   *
   * @return CardSuit
   */
  public CardSuit getSuit() {
    return suit;
  }

  /**
   * Gets the value of this card.
   * @return CardValue
   */
  public CardValue getValue() {
    return value;
  }

  /**
   * Creates a string based on the provided suit and value of the card in the form for example "A♥"
   * if ace was provided for a value and heart was provided for a suit.
   *
   * @return this card in format value suit
   */
  public String toString() {
    String card = value.getValueLabel() + suit.getSuitLabel();
    return card;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return suit == card.suit && value == card.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suit, value);
  }
}
