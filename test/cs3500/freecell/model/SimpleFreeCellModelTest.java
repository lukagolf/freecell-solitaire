package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;

/** Tests for {@link SimpleFreecellModel} class methods. */
public class SimpleFreeCellModelTest extends AbstractFreecellModelTest {

  protected void initModel() {
    model = new SimpleFreecellModel();
    model2 = new SimpleFreecellModel();
    model3 = new SimpleFreecellModel();
  }
}
