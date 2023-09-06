package cs3500.freecell.model.hw02;

import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw04.AbstractFreecellModel;

/**
 * Represents simple model of the Freecell game.
 */
public class SimpleFreecellModel extends AbstractFreecellModel {

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    Card lastCard = getLastCardFromSourcePile(source, pileNumber, cardIndex);
    if (putCardToDestinationPile(lastCard, destination, destPileNumber)) {
      removeLastCardFromPile(source, pileNumber);
    }
    else {
      throw new IllegalArgumentException("Move is not possible");
    }
  }

  /**
   * Gets the last card from the given pile of the given pile type.
   *
   * @param pileType  determines from which pile type you want to get the last card from
   * @param pileIndex determines from which pile of a certain pile type
   *                  you want to get the last card from.
   * @param cardIndex determines the index of the last (top most) in that particular pile
   *                  of that particular type.
   *
   * @return the last card (top most) Card from the given pile
   *
   * @throws IllegalArgumentException when the source pile is invalid or null
   *                                  or the given card index is not the last one in that
   *                                  particular pile of that particular type.
   *
   *
   */
  protected Card getLastCardFromSourcePile(PileType pileType, int pileIndex, int cardIndex)
      throws IllegalArgumentException {
    if (pileType == PileType.CASCADE) {
      if (getNumCardsInCascadePile(pileIndex) - 1 == cardIndex) {
        return getCascadeCardAt(pileIndex, cardIndex);
      }
      else {
        throw new IllegalArgumentException("Card isn't the last one in pile.");
      }
    }
    else if (pileType == PileType.OPEN) {
      if (getNumCardsInOpenPile(pileIndex) - 1 == cardIndex) {
        return getOpenCardAt(pileIndex);
      }
      else {
        throw new IllegalArgumentException("Card isn't the last one in pile.");
      }
    }
    else {
      throw new IllegalArgumentException("Source pile is illegal or null");
    }
  }

  /**
   * Removes the last (top most) card from a given pile type at the given pile index.
   *
   * @param pileType  determines from which pile type you want to remove the last card from
   * @param pileIndex determines from which pile of a certain pile type you want to remove
   *                  the last card from.
   * @throws IllegalArgumentException when the pile type provided is not cascade not open
   *                                  or the given pile index contains no cards
   */
  protected void removeLastCardFromPile(PileType pileType, int pileIndex)
      throws IllegalArgumentException {
    if (pileType == PileType.CASCADE) {
      if (getNumCardsInCascadePile(pileIndex) > 0) {
        cascadePiles.get(pileIndex).remove(getNumCardsInCascadePile(pileIndex) - 1);
      }
      else {
        throw new IllegalArgumentException("Can't remove card from empty pile.");
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
   * @param card      represents the card to be put on a provided destination pile
   * @param pileType  determines to which pile type you want to put the given card
   * @param pileIndex determines to which pile of a certain pile type
   *                  you want to put the given card to.
   *
   * @return true is it is possible to put a given card to a given destination
   *
   * @throws IllegalArgumentException when the pile type is set to null
   *                                  or the move chosen is not valid according to the
   *                                  freecell rules
   */
  protected boolean putCardToDestinationPile(Card card, PileType pileType, int pileIndex)
      throws IllegalArgumentException {
    if (pileType == PileType.CASCADE) {
      int numberOfCards = getNumCardsInCascadePile(pileIndex);
      if (numberOfCards > 0) {
        Card lastCard =  getCascadeCardAt(pileIndex, numberOfCards - 1);
        if (card.getValue().getPosition() == lastCard.getValue().getPosition() - 1
            && card.getSuit().getSuitColor() != lastCard.getSuit().getSuitColor()) {
          cascadePiles.get(pileIndex).add(card);
          return true;
        }
        else {
          throw new IllegalArgumentException("Move is not valid.");
        }
      }
      else {
        cascadePiles.get(pileIndex).add(card);
      }
    }
    else if (pileType == PileType.FOUNDATION) {
      int numberOfCards = getNumCardsInFoundationPile(pileIndex);
      if (numberOfCards > 0) {
        Card lastCard =  getFoundationCardAt(pileIndex, numberOfCards - 1);
        if (card.getValue().getPosition() == lastCard.getValue().getPosition() + 1
            && card.getSuit() == lastCard.getSuit()) {
          foundationPiles.get(pileIndex).add(card);
          return true;
        }
        else {
          throw new IllegalArgumentException("Move is not valid.");
        }
      }
      else {
        if (card.getValue() == CardValue.ACE) {
          foundationPiles.get(pileIndex).add(card);
          return true;
        }
        else {
          throw new IllegalArgumentException("Move is not valid.");
        }
      }
    }
    else if (pileType == PileType.OPEN) {
      int numberOfCards = getNumCardsInOpenPile(pileIndex);
      if (numberOfCards == 0) {
        openPiles.set(pileIndex, card);
        return true;
      }
      else {
        throw new IllegalArgumentException("Move is not valid.");
      }
    }
    else {
      throw new IllegalArgumentException("Destination is set to null");
    }
    return false;
  }
}

