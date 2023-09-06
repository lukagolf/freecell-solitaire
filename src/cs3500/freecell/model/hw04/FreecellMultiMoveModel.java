package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardValue;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class for the multimove freecell model.
 */
public class FreecellMultiMoveModel extends AbstractFreecellModel {

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    List<Card> cards = getCardsFromSourcePile(source, pileNumber, cardIndex);
    if (putCardsToDestinationPile(cards, destination, destPileNumber)) {
      removeCardsFromPile(source, pileNumber, cardIndex);
    }
    else {
      throw new IllegalArgumentException("Move is not possible");
    }
  }

  /**
   * Gets the last card from the given pile of the given pile type.
   *
   * @param pileType  determines from which pile type you want to get the card(s)
   * @param pileIndex determines from which pile of a certain pile type
   *                  you want to get card(s) from.
   * @param cardIndex determines the index of the card in that particular pile
   *                  of that particular type.
   *
   * @return the card(s) from the given pile
   *
   * @throws IllegalArgumentException when the card index exceeds number of cards in the pile
   *                                  when the card index is negative
   *                                  when the cards selected are not possible to move by the rules
   *                                  of the freecell game
   *                                  when the number of cards that we want to move from the cascade
   *                                  pile exceed the maximum defined by the game
   *                                  when source pile is invalid or null
   */
  private List<Card> getCardsFromSourcePile(PileType pileType, int pileIndex, int cardIndex)
      throws IllegalArgumentException {
    List<Card> listOfCards = new ArrayList<Card>();
    if (pileType == PileType.CASCADE) {
      if (cardIndex >= getNumCardsInCascadePile(pileIndex) || cardIndex < 0) {
        throw new IllegalArgumentException("Card isn't in the pile.");
      }
      for (int i = cardIndex + 1; i < getNumCardsInCascadePile(pileIndex); i++) {
        if (getCascadeCardAt(pileIndex, i).getSuit().getSuitColor()
            .equals(getCascadeCardAt(pileIndex, i - 1).getSuit().getSuitColor())
            || getCascadeCardAt(pileIndex, i).getValue().getPosition() + 1
            != getCascadeCardAt(pileIndex, i - 1).getValue().getPosition()) {
          throw new IllegalArgumentException("Cards selected are not valid");
        }
      }
      if (getNumCardsInCascadePile(pileIndex) - cardIndex
          > (numberOfEmptyOpenPiles() + 1) * Math.pow(2, numberOfEmptyCascadePiles())) {
        throw new IllegalArgumentException("Trying to move too many cards from cascade pile");
      }

      for (int i = cardIndex; i < getNumCardsInCascadePile(pileIndex); i++) {
        listOfCards.add(getCascadeCardAt(pileIndex, i));
      }
    }
    else if (pileType == PileType.OPEN) {
      if (getNumCardsInOpenPile(pileIndex) - 1 == cardIndex) {
        listOfCards.add(getOpenCardAt(pileIndex));
      }
      else {
        throw new IllegalArgumentException("Card isn't the last one in pile.");
      }
    }
    else {
      throw new IllegalArgumentException("Source pile is illegal or null");
    }
    return listOfCards;
  }

  /**
   * Removes the last (top most) card from a given pile type at the given pile index.
   *
   * @param pileType  determines from which pile type you want to remove the card(s) from
   * @param pileIndex determines from which pile of a certain pile type you want to remove
   *                  the card(s) from
   * @param cardIndex determines the index of the card in that particular pile
   *                  of that particular type.
   * @throws IllegalArgumentException when the pile type provided is not cascade not open
   *                                  or the given pile index contains no cards
   */
  private void removeCardsFromPile(PileType pileType, int pileIndex, int cardIndex)
      throws IllegalArgumentException {
    if (pileType == PileType.CASCADE) {
      for (int i = getNumCardsInCascadePile(pileIndex) - 1; i >= cardIndex; i--) {
        cascadePiles.get(pileIndex).remove(i);
      }
    }
    else if (pileType == PileType.OPEN) {
      if (getNumCardsInOpenPile(pileIndex) > 0) {
        openPiles.set(pileIndex, null);
      }
      else {
        throw new IllegalArgumentException("Can't remove card from empty pile.");
      }
    }
    else {
      throw new IllegalArgumentException("Illegal pile type.");
    }
  }

  /**
   * Puts a given card to a given pile of a given type if that move is possible according to
   * the rules of the Freecell game.
   *
   * @param cards     represents the list of card(s) to be put on a provided destination pile
   * @param pileType  determines to which pile type you want to put the given card(s)
   * @param pileIndex determines to which pile index of a certain pile type
   *                  you want to put the given card(s) to.
   *
   * @return true if it is possible to put a given card(s) to a given destination
   *
   * @throws IllegalArgumentException when the pile type is set to null
   *                                  when the move chosen is not valid according to the
   *                                  freecell rules
   *                                  when the user tries to add more than one card at the time to
   *                                  the foundation pile
   */
  private boolean putCardsToDestinationPile(List<Card> cards, PileType pileType, int pileIndex)
      throws IllegalArgumentException {
    Card firstCard = cards.get(0);
    if (pileType == PileType.CASCADE) {
      int numberOfCards = getNumCardsInCascadePile(pileIndex);
      if (numberOfCards > 0) {
        Card lastCard =  getCascadeCardAt(pileIndex, numberOfCards - 1);
        if (firstCard.getValue().getPosition() != lastCard.getValue().getPosition() - 1
            || firstCard.getSuit().getSuitColor() == lastCard.getSuit().getSuitColor()) {
          throw new IllegalArgumentException("Move is not valid.");
        }
      }
      cascadePiles.get(pileIndex).addAll(cards);
      return true;
    }
    else if (pileType == PileType.FOUNDATION) {
      if (cards.size() != 1) {
        throw new IllegalArgumentException("Cannot add more than one card at the time");
      }
      int numberOfCards = getNumCardsInFoundationPile(pileIndex);
      if (numberOfCards > 0) {
        Card lastCard =  getFoundationCardAt(pileIndex, numberOfCards - 1);
        if (firstCard.getValue().getPosition() == lastCard.getValue().getPosition() + 1
            && firstCard.getSuit() == lastCard.getSuit()) {
          foundationPiles.get(pileIndex).add(firstCard);
          return true;
        }
        else {
          throw new IllegalArgumentException("Move is not valid.");
        }
      }
      else {
        if (firstCard.getValue() == CardValue.ACE) {
          foundationPiles.get(pileIndex).add(firstCard);
          return true;
        }
        else {
          throw new IllegalArgumentException("Move is not valid.");
        }
      }
    }
    else if (pileType == PileType.OPEN) {
      int numberOfCards = getNumCardsInOpenPile(pileIndex);
      if (numberOfCards == 0 && cards.size() == 1) {
        openPiles.set(pileIndex, firstCard);
        return true;
      }
      else {
        throw new IllegalArgumentException("Move is not valid.");
      }
    }
    else {
      throw new IllegalArgumentException("Destination is set to null");
    }
  }

  /**
   * Gets the number of empty Cascade piles.
   * @return the number of empty Open piles
   */
  private int numberOfEmptyOpenPiles() {
    int numberEmpty = 0;
    for (int i = 0; i < getNumOpenPiles(); i++) {
      if (getNumCardsInOpenPile(i) == 0) {
        numberEmpty++;
      }
    }
    return numberEmpty;
  }

  /**
   * Gets the number of empty Cascade piles.
   * @return the number of empty Cascade piles
   */
  private int numberOfEmptyCascadePiles() {
    int numberEmpty = 0;
    for (int i = 0; i < getNumCascadePiles(); i++) {
      if (getNumCardsInCascadePile(i) == 0) {
        numberEmpty++;
      }
    }
    return numberEmpty;
  }
}

