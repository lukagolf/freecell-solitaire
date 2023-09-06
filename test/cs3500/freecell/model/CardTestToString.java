package cs3500.freecell.model;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuit;
import cs3500.freecell.model.card.CardValue;
import org.junit.Test;

/** Tests for the toString method of {@link Card}.
 */

public class CardTestToString {

  @Test
  public void testAceHeart() {
    assertEquals("A♥",
        new Card(CardSuit.HEARTS, CardValue.ACE).toString());
  }

  @Test
  public void testKingSpade() {
    assertEquals("K♠",
        new Card(CardSuit.SPADES, CardValue.KING).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSuit() throws Exception {
    assertEquals("K♠",
        new Card(null, CardValue.KING).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullValue() throws Exception {
    assertEquals("K♠",
        new Card(CardSuit.SPADES, null).toString());
  }
}
