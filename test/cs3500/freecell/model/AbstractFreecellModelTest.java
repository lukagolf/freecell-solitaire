package cs3500.freecell.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuit;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.hw04.AbstractFreecellModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** Tests for {@link AbstractFreecellModel} class methods. */
public abstract class AbstractFreecellModelTest {

  protected AbstractFreecellModel model;
  protected AbstractFreecellModel model2;
  protected AbstractFreecellModel model3;
  List<Card> deck;

  protected abstract void initModel();

  @Before
  public void initialize() {
    initModel();
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

  // below is the test for getDeck

  @Test
  public void testGetDeck() {
    assertEquals(deck, model.getDeck());
  }

  // below are the tests for startGame method

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckNull() throws Exception {
    model.startGame(null, 5, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckLongerAndTwoSameCards() throws Exception {
    deck.add(new Card(CardSuit.HEARTS, CardValue.KING));
    model.startGame(deck, 5, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckShorter() throws Exception {
    Card card = deck.get(3);
    deck.remove(card);
    model.startGame(deck, 5, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckInvalidNumberOfCascadePiles() throws Exception {
    model.startGame(deck, 2, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckInvalidNumberOfOpenPiles() throws Exception {
    model.startGame(deck, 5, 0, false);
  }

  @Test
  public void testStartGameShuffle() {
    model.startGame(deck, 5, 2, false);
    model2.startGame(deck, 5, 2, true);
    model3.startGame(deck, 5, 2, false);
    assertEquals(model, model3);
    assertNotEquals(model, model2);
  }

  @Test
  public void testStartGameRestart() {
    model.startGame(deck, 5, 2, false);
    model.move(PileType.CASCADE,0, 10, PileType.OPEN,0);
    model.startGame(deck, 5, 2, false);
    model3.startGame(deck, 5, 2, false);
    assertEquals(model, model3);
  }

  // below are the tests for getNumCardsInFoundationPile

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexInFoundationPile() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getNumCardsInFoundationPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexInFoundationPile() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getNumCardsInFoundationPile(CardSuit.values().length);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedForFoundationPile() throws Exception {
    model.getNumCardsInFoundationPile(2);
  }

  @Test
  public void testNumberOfCardsInFoundationPile() {
    model.startGame(deck, 13, 2, false);
    assertEquals(0, model.getNumCardsInFoundationPile(0));
    model.move(PileType.CASCADE,0, 3, PileType.FOUNDATION,0);
    assertEquals(1, model.getNumCardsInFoundationPile(0));
  }

  // below is the test for getNumCascadePiles

  @Test
  public void testNumCascadePiles() {
    assertEquals(-1,model.getNumCascadePiles());
    model.startGame(deck, 13, 2, false);
    assertEquals(13, model.getNumCascadePiles());
  }

  // below are the tests for getNumCardsInCascadePile

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexInCascadePile() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexInCascadePile() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getNumCardsInCascadePile(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedForCascadePile() throws Exception {
    model.getNumCardsInCascadePile(2);
  }

  @Test
  public void testNumberOfCardsInCascadePile() {
    model.startGame(deck, 13, 2, false);
    assertEquals(4, model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE,0, 3, PileType.FOUNDATION,0);
    assertEquals(3, model.getNumCardsInCascadePile(0));
  }

  // below is the test for getNumOpenPiles

  @Test
  public void testNumOpenPiles() {
    assertEquals(-1,model.getNumOpenPiles());
    model.startGame(deck, 13, 2, false);
    assertEquals(2, model.getNumOpenPiles());
  }

  // below are the tests for getNumCardsInOpenPile

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexInOpenPile() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getNumCardsInOpenPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexInOpenPile() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getNumCardsInOpenPile(2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedForOpenPile() throws Exception {
    model.getNumCardsInOpenPile(2);
  }

  @Test
  public void testNumberOfCardsInOpenPile() {
    model.startGame(deck, 13, 2, false);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    assertEquals(1, model.getNumCardsInOpenPile(0));
  }

  // below are the tests for getFoundationCardAt

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexInFoundationPileCard() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getFoundationCardAt(0,-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexInFoundationPileCard() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getFoundationCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedForFoundationPileCard() throws Exception {
    model.getFoundationCardAt(0,0);
  }

  @Test
  public void testNumberOfCardsInFoundationPileCard() {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 3, PileType.FOUNDATION,0);
    assertEquals(new Card(CardSuit.HEARTS, CardValue.ACE), model.getFoundationCardAt(0,0));
  }

  // below are the tests for getCascadeCardAt

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexInCascadePileCard() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getCascadeCardAt(0,-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexInCascadePileCard() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.getCascadeCardAt(0, 4);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedForCascadePileCard() throws Exception {
    model.getCascadeCardAt(0,0);
  }

  @Test
  public void testNumberOfCardsInCascadePileCard() {
    model.startGame(deck, 13, 2, false);
    assertEquals(new Card(CardSuit.HEARTS, CardValue.ACE), model.getCascadeCardAt(0,3));
  }

  // below are the tests for getOpenCardAt

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexInOpenPileCard() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexInOpenPileCard() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.getOpenCardAt(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedForOpenPileCard() throws Exception {
    model.getOpenCardAt(0);
  }

  @Test
  public void testNumberOfCardsInOpenPileCard() {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    assertEquals(new Card(CardSuit.HEARTS, CardValue.ACE), model.getOpenCardAt(0));
  }

  // below are the tests for move

  @Test
  public void testMove() {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.OPEN,0, 0, PileType.FOUNDATION,0);
    model.move(PileType.CASCADE,0, 2, PileType.FOUNDATION,1);
    assertEquals(new Card(CardSuit.HEARTS, CardValue.ACE), model.getFoundationCardAt(0,0));
    assertEquals(new Card(CardSuit.DIAMONDS, CardValue.ACE), model.getFoundationCardAt(1,0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSourceIndexMove() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.move(PileType.CASCADE,-1, 3, PileType.OPEN,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeDestIndexMove() throws Exception {
    model.startGame(deck, 5, 2, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexSourceMove() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 13, PileType.OPEN,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooBigIndexDescMove() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedMove() throws Exception {
    model.move(PileType.CASCADE,0, 0, PileType.OPEN,2);
    model.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWrongTypeMove() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 13, PileType.FOUNDATION,0);
    model.move(PileType.FOUNDATION,0, 0, PileType.OPEN,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToCascadePile() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 13, PileType.CASCADE,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToFullOpenPile() throws Exception {
    model.startGame(deck, 13, 2, false);
    model.move(PileType.CASCADE,0, 13, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 12, PileType.OPEN,1);
  }

  // below are the tests for isGameOver

  @Test(expected = IllegalArgumentException.class)
  public void testGameOver() throws Exception {
    assertEquals(false, model.isGameOver());
    model.startGame(deck, 13, 2, false);
    assertEquals(false, model.isGameOver());
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 13; j++) {
        model.move(PileType.CASCADE,j, i, PileType.FOUNDATION,i);
      }
    }
    assertEquals(true, model.isGameOver());
  }

}
