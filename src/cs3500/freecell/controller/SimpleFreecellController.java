package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.card.Card;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents a simple controller for the game of freecell.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  private final FreecellModel<Card> model;
  private final Scanner scan;
  private final FreecellView view;

  /**
   * Constructs a {@code SimpleFreecellController} object.
   *
   * @param model  the model of the Freecell game
   * @param rd     the readable component
   * @param ap     the appendable component
   * @throws IllegalArgumentException if any of the @params are set to null
   */
  public SimpleFreecellController(FreecellModel<Card> model,
      Readable rd, Appendable ap) throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Argument(s) null.");
    }
    this.model = model;
    this.scan = new Scanner(rd);
    this.view = new FreecellTextView(model, ap);
  }

  /**
   * Gets the pile type based on the character given.
   * If the given character is:
   * -'C' then the method returns CASCADE pile.
   * -'F' then the method returns FOUNDATION pile.
   * -'O' then the method returns OPEN pile.
   * -otherwise returns null
   *
   * @param ch the provided character
   *
   * @return pile type
   */
  private PileType getPileType(char ch) {
    switch (ch) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        return  null;
    }
  }

  /**
   * Gets the pile number based on the string and pile type given.
   *
   * @param str      number of the pile in the string form
   * @param pileType one of the pile types of the freecell game
   *
   * @return pile number of the pile type given or -1 if the pile type is invalid
   */
  private int getPileNumber(String str, PileType pileType) {
    if (str.length() == 0) {
      return -1;
    }
    try {
      int n = Integer.parseInt(str);
      switch (pileType) {
        case CASCADE:
          if (n <= model.getNumCascadePiles() && n > 0) {
            return n - 1;
          }
          else {
            return -1;
          }
        case FOUNDATION:
          if (n <= 4 && n > 0) {
            return n - 1;
          }
          else {
            return -1;
          }
        case OPEN:
          if (n <= model.getNumOpenPiles() && n > 0) {
            return n - 1;
          }
          else {
            return -1;
          }
        default:
          return -1;
      }
    }
    catch (NumberFormatException e) {
      return -1;
    }
  }

  /**
   * Gets the card number based on the string given.
   *
   * @param str number of the card in the string form
   *
   * @return card number or -1 if the card number is invalid
   */
  private int getCardNumber(String str) {
    try {
      int n = Integer.parseInt(str);
      if (n <= 0) {
        return -1;
      }
      return n - 1;
    }
    catch (NumberFormatException e) {
      return  -1;
    }
  }

  /**
   * Determines that the game is over if it returns false.
   *
   * @return false if game is over
   */
  private boolean makeMove() throws IOException {
    while (true) {
      PileType srcPileType = null;
      PileType dstPileType = null;
      int srcPileNumber = -1;
      int dstPileNumber = -1;
      int cardIndex = -1;
      while (srcPileNumber == -1) {
        if (!scan.hasNext()) {
          view.renderMessage("Waiting for source pile type and pile number");
        }
        String first = scan.next();
        if (first.equalsIgnoreCase("q")) {
          return false;
        }
        srcPileType = getPileType(first.charAt(0));
        if (srcPileType == null) {
          view.renderMessage("Enter source pile type and pile number again.\n");
          continue;
        }
        srcPileNumber = getPileNumber(first.substring(1), srcPileType);
        if (srcPileNumber == -1) {
          view.renderMessage("Enter source pile type and pile number again.\n");
        }
      }

      while (cardIndex == -1) {
        if (!scan.hasNext()) {
          view.renderMessage("Waiting for card index");
        }
        String second = scan.next();
        if (second.equalsIgnoreCase("q")) {
          return false;
        }
        cardIndex = getCardNumber(second);
        if (cardIndex == -1) {
          view.renderMessage("Invalid card index. Enter card index again.\n");
        }
      }

      while (dstPileNumber == -1) {
        if (!scan.hasNext()) {
          view.renderMessage("Waiting for destination pile type and pile number");
        }
        String third = scan.next();
        if (third.equalsIgnoreCase("q")) {
          return false;
        }
        dstPileType = getPileType(third.charAt(0));
        if (srcPileType == null) {
          view.renderMessage("Enter destination pile type and pile number again.\n");
          continue;
        }
        dstPileNumber = getPileNumber(third.substring(1), dstPileType);
        if (dstPileNumber == -1) {
          view.renderMessage("Enter destination pile type and pile number again.\n");
        }
      }

      try {
        model.move(srcPileType, srcPileNumber, cardIndex, dstPileType, dstPileNumber);
        return true;
      } catch (IllegalArgumentException e) {
        view.renderMessage("Invalid move. Try again. " + e.getMessage() + "\n");
      }
    }
  }

  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    try {
      if (model == null || deck == null) {
        throw new IllegalArgumentException("Model or deck is null");
      }
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (IllegalArgumentException e) {
        view.renderMessage("Could not start game.");
        return;
      }

      while (!model.isGameOver()) {
        view.renderBoard();
        view.renderMessage("\n");
        if (!makeMove()) {
          view.renderMessage("Game quit prematurely.");
          return;
        }
      }
      view.renderBoard();
      view.renderMessage("\nGame over.");
    }
    catch (IOException e) {
      throw new IllegalStateException("Problem with output");
    }
    catch (NoSuchElementException e) {
      throw new IllegalStateException("No input");
    }
  }
}
