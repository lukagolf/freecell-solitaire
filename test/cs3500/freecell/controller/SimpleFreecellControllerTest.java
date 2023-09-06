package cs3500.freecell.controller;

import static org.junit.Assert.assertTrue;
import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuit;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents a testing class for SimpleFreecellController methods.
 */
public class SimpleFreecellControllerTest {

  SimpleFreecellController controller;
  SimpleFreecellModel model;
  Readable rd;
  Appendable ap = new StringWriter();
  List<Card> deck;

  /**
   * Initializes the objects of this class.
   */
  @Before
  public void initialize() {
    model = new SimpleFreecellModel();

    deck = new ArrayList<Card>();

    deck.add(new Card(CardSuit.CLUBS, CardValue.ACE));
    deck.add(new Card(CardSuit.CLUBS, CardValue.TWO));
    deck.add(new Card(CardSuit.CLUBS, CardValue.THREE));
    deck.add(new Card(CardSuit.CLUBS, CardValue.FOUR));
    deck.add(new Card(CardSuit.CLUBS, CardValue.FIVE));
    deck.add(new Card(CardSuit.CLUBS, CardValue.SIX));
    deck.add(new Card(CardSuit.CLUBS, CardValue.SEVEN));
    deck.add(new Card(CardSuit.CLUBS, CardValue.EIGHT));
    deck.add(new Card(CardSuit.CLUBS, CardValue.NINE));
    deck.add(new Card(CardSuit.CLUBS, CardValue.TEN));
    deck.add(new Card(CardSuit.CLUBS, CardValue.JACK));
    deck.add(new Card(CardSuit.CLUBS, CardValue.QUEEN));
    deck.add(new Card(CardSuit.CLUBS, CardValue.KING));

    deck.add(new Card(CardSuit.SPADES, CardValue.ACE));
    deck.add(new Card(CardSuit.SPADES, CardValue.TWO));
    deck.add(new Card(CardSuit.SPADES, CardValue.THREE));
    deck.add(new Card(CardSuit.SPADES, CardValue.FOUR));
    deck.add(new Card(CardSuit.SPADES, CardValue.FIVE));
    deck.add(new Card(CardSuit.SPADES, CardValue.SIX));
    deck.add(new Card(CardSuit.SPADES, CardValue.SEVEN));
    deck.add(new Card(CardSuit.SPADES, CardValue.EIGHT));
    deck.add(new Card(CardSuit.SPADES, CardValue.NINE));
    deck.add(new Card(CardSuit.SPADES, CardValue.TEN));
    deck.add(new Card(CardSuit.SPADES, CardValue.JACK));
    deck.add(new Card(CardSuit.SPADES, CardValue.QUEEN));
    deck.add(new Card(CardSuit.SPADES, CardValue.KING));

    deck.add(new Card(CardSuit.DIAMONDS, CardValue.ACE));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.TWO));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.THREE));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.FOUR));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.FIVE));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.SIX));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.SEVEN));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.EIGHT));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.NINE));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.TEN));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.JACK));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.QUEEN));
    deck.add(new Card(CardSuit.DIAMONDS, CardValue.KING));

    deck.add(new Card(CardSuit.HEARTS, CardValue.ACE));
    deck.add(new Card(CardSuit.HEARTS, CardValue.TWO));
    deck.add(new Card(CardSuit.HEARTS, CardValue.THREE));
    deck.add(new Card(CardSuit.HEARTS, CardValue.FOUR));
    deck.add(new Card(CardSuit.HEARTS, CardValue.FIVE));
    deck.add(new Card(CardSuit.HEARTS, CardValue.SIX));
    deck.add(new Card(CardSuit.HEARTS, CardValue.SEVEN));
    deck.add(new Card(CardSuit.HEARTS, CardValue.EIGHT));
    deck.add(new Card(CardSuit.HEARTS, CardValue.NINE));
    deck.add(new Card(CardSuit.HEARTS, CardValue.TEN));
    deck.add(new Card(CardSuit.HEARTS, CardValue.JACK));
    deck.add(new Card(CardSuit.HEARTS, CardValue.QUEEN));
    deck.add(new Card(CardSuit.HEARTS, CardValue.KING));
  }

  // tests for playGame method

  @Test(expected = IllegalArgumentException.class)
  public void testDeckNull() throws Exception {
    String s = "C2 3 F1";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(null, 3, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelNull() throws Exception {
    String s = "C2 3 F1";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(null, rd, ap);
    controller.playGame(deck, 13,2,false);
  }

  @Test
  public void testGameOver() {
    String s = "";
    for (int i = 4; i > 0; i--) {
      for (int j = 1; j <= 13; j++) {
        s += "C" + j + " " + i + " F" + i + " ";
      }
    }
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
    assertTrue(ap.toString().endsWith("\nGame over."));
  }

  @Test(expected = IllegalStateException.class)
  public void testIncompleteMove() throws Exception {
    String s = "C2";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyReadable() throws Exception {
    String s = "";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
  }

  @Test
  public void testInvalidSourcePile() {
    String s = "C20 C1 4 F2 Q";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
    assertTrue(ap.toString().contains("Enter source pile type and pile number again."));
  }

  @Test
  public void testInvalidCardIndex() {
    String s = "C1 x 4 F2 Q";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
    assertTrue(ap.toString().contains("Invalid card index. Enter card index again."));
  }

  @Test
  public void testInvalidDestinationPile() {
    String s = "C1 4 F-2 F2 Q";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
    assertTrue(ap.toString().contains("Enter destination pile type and pile number again."));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() throws Exception {
    String s = "Q";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, null);
    controller.playGame(deck, 13,2,false);
  }

  @Test
  public void testQuit() {
    String s = "Q";
    rd = new StringReader(s);
    controller = new SimpleFreecellController(model, rd, ap);
    controller.playGame(deck, 13,2,false);
    assertTrue(ap.toString().contains("Game quit prematurely."));
  }
}



