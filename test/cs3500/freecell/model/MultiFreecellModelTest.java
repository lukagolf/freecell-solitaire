package cs3500.freecell.model;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuit;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.hw04.FreecellMultiMoveModel;
import org.junit.Test;

/** Tests for {@link FreecellMultiMoveModel} class methods. */
public class MultiFreecellModelTest extends AbstractFreecellModelTest {

  /**
   * Initializes the models needed for testing.
   */
  protected void initModel() {
    model = new FreecellMultiMoveModel();
    model2 = new FreecellMultiMoveModel();
    model3 = new FreecellMultiMoveModel();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveRedCardOnRedCard() throws Exception {
    model.startGame(deck, 13, 10, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.CASCADE,0, 2, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 1, PileType.CASCADE,1);
    model.move(PileType.CASCADE,2, 3, PileType.OPEN,2);
    model.move(PileType.CASCADE,1, 3, PileType.CASCADE,2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePickingInvalidCards() throws Exception {
    model.startGame(deck, 13, 10, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.CASCADE,0, 2, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 1, PileType.CASCADE,1);
    model.move(PileType.CASCADE,2, 3, PileType.OPEN,2);
    model.move(PileType.CASCADE,2, 2, PileType.OPEN,3);
    model.move(PileType.CASCADE,1, 2, PileType.CASCADE,2);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNoSpace() throws Exception {
    model.startGame(deck, 13, 5, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.CASCADE,0, 2, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 1, PileType.CASCADE,1);
    model.move(PileType.CASCADE,2, 3, PileType.OPEN,2);
    model.move(PileType.CASCADE,2, 2, PileType.OPEN,3);
    model.move(PileType.CASCADE,1, 3, PileType.CASCADE,2);
    model.move(PileType.CASCADE,2, 1, PileType.CASCADE,3);
  }

  @Test
  public void testMoveWithoutEmptyCascadePilesAndTwoOpenPiles() {
    model.startGame(deck, 13, 6, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.CASCADE,0, 2, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 1, PileType.CASCADE,1);
    model.move(PileType.CASCADE,2, 3, PileType.OPEN,2);
    model.move(PileType.CASCADE,2, 2, PileType.OPEN,3);
    model.move(PileType.CASCADE,1, 3, PileType.CASCADE,2);
    model.move(PileType.CASCADE,2, 1, PileType.CASCADE,3);

    assertEquals(new Card(CardSuit.SPADES, CardValue.ACE), model.getCascadeCardAt(3, 6));
    assertEquals(new Card(CardSuit.HEARTS, CardValue.TWO), model.getCascadeCardAt(3, 5));
    assertEquals(new Card(CardSuit.SPADES, CardValue.THREE), model.getCascadeCardAt(3,4));
  }

  @Test
  public void testMoveWithOneOpenPileAndOneCascadePile() {
    model.startGame(deck, 13, 6, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.CASCADE,0, 2, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 1, PileType.CASCADE,1);
    model.move(PileType.CASCADE,0, 0, PileType.OPEN,2);
    model.move(PileType.CASCADE,2, 3, PileType.OPEN,3);
    model.move(PileType.CASCADE,2, 2, PileType.OPEN,4);
    model.move(PileType.CASCADE,1, 3, PileType.CASCADE,2);
    model.move(PileType.CASCADE,2, 1, PileType.CASCADE,3);

    assertEquals(new Card(CardSuit.SPADES, CardValue.ACE), model.getCascadeCardAt(3, 6));
    assertEquals(new Card(CardSuit.HEARTS, CardValue.TWO), model.getCascadeCardAt(3, 5));
    assertEquals(new Card(CardSuit.SPADES, CardValue.THREE), model.getCascadeCardAt(3,4));
  }

  @Test
  public void testMoveToEmptyCascadePile() {
    model.startGame(deck, 13, 10, false);
    model.move(PileType.CASCADE,0, 3, PileType.OPEN,0);
    model.move(PileType.CASCADE,0, 2, PileType.OPEN,1);
    model.move(PileType.CASCADE,0, 1, PileType.CASCADE,1);
    model.move(PileType.CASCADE,0, 0, PileType.OPEN,2);
    model.move(PileType.CASCADE,1, 3, PileType.CASCADE,0);

    assertEquals(new Card(CardSuit.SPADES, CardValue.ACE), model.getCascadeCardAt(0, 1));
    assertEquals(new Card(CardSuit.HEARTS, CardValue.TWO), model.getCascadeCardAt(0, 0));
  }
}
