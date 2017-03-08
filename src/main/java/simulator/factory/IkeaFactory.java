package simulator.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.container.OreContainer;
import simulator.container.ProductBox;
import simulator.container.ProductContainer;
import simulator.fsm.*;
import simulator.truck.ITruck;
import simulator.truck.VolvoTruck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

public final class IkeaFactory implements IFactory {
  private final List<IStateMachine> processes = new ArrayList<>();
  private final AssetManager assets;
  private final Vector2 position;
  private final Texture texture;

  private final List<IContainer> productsToShip = new ArrayList<>();
  private ITruck readyTruck;

  public IkeaFactory(Vector2 position, AssetManager assets) {
    this.assets = assets;
    this.position = position;
    this.texture = assets.get("resources/ikea.png");

    // fills the truck with shippable products
    processes.add(new Repeat(new Sequence(new Wait(new Predicate<Object>() {
      @Override
      public boolean test(Object o) {
        return readyTruck != null && !productsToShip.isEmpty();
      }
    }), new Call(new FillReadyTruck()))));

    // adds a new truck
    processes.add(new Repeat(new Sequence(new Timer(3F), new Sequence(new Wait(new Predicate<Object>() {
      @Override
      public boolean test(Object o) {
        return readyTruck == null;
      }
    }), new Call(new AddReadyTruck())))));

    // every second we add a new product box
    processes.add(new Repeat(new Sequence(new Timer(1F), new Sequence(new Wait(new Predicate<Object>() {
      @Override
      public boolean test(Object o) {
        return productsToShip.size() != 6;
      }
    }), new Call(new AddProductBox())))));
  }

  private final class FillReadyTruck implements IAction {
    @Override
    public void run() {
      if (readyTruck.getContainer() == null) {
        float x = readyTruck.getPosition().x - 60F;
        float y = readyTruck.getPosition().y + 15F;

        readyTruck.addContainer(new ProductContainer(100, new Vector2(x, y), assets));
      }

      Iterator<IContainer> itr = productsToShip.iterator();
      while (itr.hasNext()) {
        if (readyTruck.getContainer().addContent(itr.next().getCurrentAmount())) {
          if (readyTruck.getContainer().getCurrentAmount() >= readyTruck.getContainer().getMaxCapacity()) {
            readyTruck = null;
            break;
          }

          itr.remove();
        }
      }
    }
  }

  private final class AddReadyTruck implements IAction {
    @Override
    public void run() {
      spawnReadyTruck();
    }
  }

  private final class AddProductBox implements IAction {
    ProductBox createProductBox(Vector2 position) {
      return new ProductBox(25, position, assets);
    }

    @Override
    public void run() {
      float ikeaX = getPosition().x;
      float ikeaY = getPosition().y;

      float boxX = ikeaX + 20F;

      // the C# version does mineY + (40 - 30 * size) because monogame's coordinate system
      // presumably starts at the top left corner whilst libgdx draws textures starting at the bottom left
      float boxY = ikeaY + (40 + 40 * getProductsToShip().size());

      Vector2 position = new Vector2(boxX, boxY);
      getProductsToShip().add(createProductBox(position));
    }
  }

  @Override
  public void update(float deltaTime) {
    for (IStateMachine machine : processes) {
      machine.update(deltaTime);
    }
  }

  private void spawnReadyTruck() {
    readyTruck = new VolvoTruck(new Vector2(460, 105), new Vector2(-4, 0), true, assets);
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(texture, position.x, position.y);

    for (IContainer container : productsToShip) {
      container.draw(batch);
    }
  }

  @Override
  public ITruck getReadyTruck() {
    return readyTruck;
  }

  @Override
  public Vector2 getPosition() {
    return position;
  }

  @Override
  public List<IContainer> getProductsToShip() {
    return productsToShip;
  }
}
