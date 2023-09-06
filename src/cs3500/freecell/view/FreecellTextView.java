package cs3500.freecell.view;

import cs3500.freecell.model.card.CardSuit;
import cs3500.freecell.model.FreecellModelState;
import java.io.IOException;

/**
 * Represents the view of the Freecell game.
 */
public class FreecellTextView implements FreecellView {

  private final Appendable appendable;
  private final FreecellModelState<?> model;

  /**
   * Constructs a {@code FreecellTextView} object.
   *
   * @param model represents a freecell model of any type
   */
  public FreecellTextView(FreecellModelState<?> model) {
    this.model = model;
    this.appendable = null;
  }

  public FreecellTextView(FreecellModelState<?> model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public String toString() {
    if (model.getNumCascadePiles() < 0) {
      return "";
    }
    String res = "";
    for ( int i = 0; i < CardSuit.values().length; i++) {
      res += "F" + (i + 1) + ":";
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
        res += " " + model.getFoundationCardAt(i, j).toString();
        if (j < model.getNumCardsInFoundationPile(i) - 1) {
          res += ",";
        }
      }
      res += "\n";
    }
    for ( int i = 0; i < model.getNumOpenPiles(); i++) {
      res += "O" + (i + 1) + ":";
      if ( model.getNumCardsInOpenPile(i) == 1) {
        res += " " + model.getOpenCardAt(i).toString();
      }
      res += "\n";
    }
    for ( int i = 0; i < model.getNumCascadePiles(); i++) {
      res += "C" + (i + 1) + ":";
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {
        res += " " + model.getCascadeCardAt(i, j).toString();
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          res += ",";
        }
      }
      if (i < model.getNumCascadePiles() - 1) {
        res += "\n";
      }
    }
    return res;
  }

  @Override
  public void renderBoard() throws IOException {
    renderMessage(toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (appendable == null) {
      System.out.println(message);
      return;
    }
    appendable.append(message);
  }
}
