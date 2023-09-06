package cs3500.freecell.view;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import org.junit.Before;
import org.junit.Test;

/** Tests for {@link FreecellTextView} class to String method. */

public class FreeCellTextViewTest {
  SimpleFreecellModel model;
  FreecellTextView view;

  @Before
  public void initialize() {
    model = new SimpleFreecellModel();
    view = new FreecellTextView(model);
  }

  @Test
  public void testMove() {
    model.startGame(model.getDeck(), 13, 4, false);
    model.move(PileType.CASCADE,0, 3, PileType.FOUNDATION,0);
    model.move(PileType.CASCADE,1, 3, PileType.OPEN,0);
    assertEquals("F1: A♥\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1: 2♥\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, A♠, A♦\n"
            + "C2: 2♣, 2♠, 2♦\n"
            + "C3: 3♣, 3♠, 3♦, 3♥\n"
            + "C4: 4♣, 4♠, 4♦, 4♥\n"
            + "C5: 5♣, 5♠, 5♦, 5♥\n"
            + "C6: 6♣, 6♠, 6♦, 6♥\n"
            + "C7: 7♣, 7♠, 7♦, 7♥\n"
            + "C8: 8♣, 8♠, 8♦, 8♥\n"
            + "C9: 9♣, 9♠, 9♦, 9♥\n"
            + "C10: 10♣, 10♠, 10♦, 10♥\n"
            + "C11: J♣, J♠, J♦, J♥\n"
            + "C12: Q♣, Q♠, Q♦, Q♥\n"
            + "C13: K♣, K♠, K♦, K♥", view.toString());
  }

  @Test
  public void testInvalidStartGame() {
    try {
      model.startGame(model.getDeck(), -13, 4, false);
    }
    catch (Exception e) {
      assertEquals("", view.toString());
    }
  }
}
