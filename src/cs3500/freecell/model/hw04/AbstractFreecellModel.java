package cs3500.freecell.model.hw04;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuit;
import cs3500.freecell.model.card.CardValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Acts as an abstract class for the multimove and simple freecell models.
 */
public abstract class AbstractFreecellModel implements FreecellModel<Card> {

  protected boolean started;
  protected List<Card> openPiles;
  protected List<List<Card>> cascadePiles;
  protected List<List<Card>> foundationPiles;

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    for (int i = 0; i < CardSuit.values().length; i++) {
      for (int j = 0; j < CardValue.values().length; j++) {
        Card card = new Card(CardSuit.values()[i], CardValue.values()[j]);
        deck.add(card);
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("The deck is empty.");
    }
    if (deck.size() != CardSuit.values().length * CardValue.values().length) {
      throw new IllegalArgumentException("The deck does not have 52 cards.");
    }

    for (int i = 0; i < deck.size(); i++) {
      if (deck.get(i).getSuit() == null) {
        throw new IllegalArgumentException("The deck has illegal suit(s).");
      }
    }
    for (int i = 0; i < deck.size(); i++) {
      if (deck.get(i).getValue() == null) {
        throw new IllegalArgumentException("The deck has illegal value(s).");
      }
    }

    for (int i = 0; i < deck.size(); i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        if (deck.get(i).equals(deck.get(j))) {
          throw new IllegalArgumentException("The deck has two same cards.");
        }
      }
    }

    if (numCascadePiles <= 3) {
      throw new IllegalArgumentException("Invalid number of cascade piles.");
    }
    if (numOpenPiles <= 0) {
      throw new IllegalArgumentException("Invalid number of open piles.");
    }
    if (shuffle) {
      deck = new ArrayList<Card>(deck);
      Collections.shuffle(deck);
    }

    openPiles = new ArrayList<Card>(numOpenPiles);
    for ( int i = 0; i < numOpenPiles; i++) {
      openPiles.add(null);
    }

    foundationPiles = new ArrayList<List<Card>>();
    for ( int i = 0; i < CardSuit.values().length; i++) {
      foundationPiles.add(new ArrayList<Card>());
    }

    cascadePiles = new ArrayList<List<Card>>();
    for ( int i = 0; i < numCascadePiles; i++) {
      cascadePiles.add(i, new ArrayList<Card>());
    }

    for (int i = 0; i < deck.size(); i++) {
      cascadePiles.get(i % numCascadePiles).add(deck.get(i));
    }
    started = true;
  }

  @Override
  public boolean isGameOver() {
    if (!started) {
      return false;
    }
    for (int i = 0; i < foundationPiles.size(); i++) {
      if (getNumCardsInFoundationPile(i) < CardValue.values().length) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (index < 0 || index >= CardSuit.values().length) {
      throw new IllegalArgumentException("Index is out of range.");
    }
    return foundationPiles.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (!started) {
      return -1;
    }
    return cascadePiles.size();
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (index < 0 || index >= getNumCascadePiles()) {
      throw new IllegalArgumentException("Index is out of range.");
    }
    return cascadePiles.get(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Open pile is not initialized");
    }
    if (index < 0 || index >= getNumOpenPiles()) {
      throw new IllegalArgumentException("Index is out of range.");
    }

    if (openPiles.get(index) != null) {
      return 1;
    }
    return 0;
  }

  @Override
  public int getNumOpenPiles() {
    if (!started) {
      return -1;
    }
    return openPiles.size();
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    int numOfCardInFoundationPileAtPileIndex = getNumCardsInFoundationPile(pileIndex);
    if (cardIndex < 0 || cardIndex >= numOfCardInFoundationPileAtPileIndex) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    return foundationPiles.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    int numOfCardInCascadePileAtPileIndex = getNumCardsInCascadePile(pileIndex);
    if (cardIndex < 0 || cardIndex >= numOfCardInCascadePileAtPileIndex) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    return cascadePiles.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    getNumCardsInOpenPile(pileIndex);

    return openPiles.get(pileIndex);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractFreecellModel that = (AbstractFreecellModel) o;
    if (started != that.started
        || openPiles.size() != that.openPiles.size()
        || cascadePiles.size() != that.cascadePiles.size()
        || foundationPiles.size() != that.foundationPiles.size()) {
      return false;
    }
    for (int i = 0; i < openPiles.size(); i++) {
      if ((openPiles.get(i) != null && that.openPiles.get(i) == null)
          || (openPiles.get(i) == null && that.openPiles.get(i) != null)) {
        return false;
      }
      if (openPiles.get(i) != null && that.openPiles.get(i) != null
          && !openPiles.get(i).equals(that.openPiles.get(i))) {
        return false;
      }
    }
    for (int i = 0; i < cascadePiles.size(); i++) {
      if (cascadePiles.get(i).size() != that.cascadePiles.get(i).size()) {
        return false;
      }
      for (int j = 0; j < cascadePiles.get(i).size(); j++) {
        if (!cascadePiles.get(i).get(j).equals(that.cascadePiles.get(i).get(j))) {
          return false;
        }
      }
    }
    for (int i = 0; i < foundationPiles.size(); i++) {
      if (foundationPiles.get(i).size() != that.foundationPiles.get(i).size()) {
        return false;
      }
      for (int j = 0; j < foundationPiles.get(i).size(); j++) {
        if (!foundationPiles.get(i).get(j).equals(that.foundationPiles.get(i).get(j))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(started, openPiles, cascadePiles, foundationPiles);
  }
}
