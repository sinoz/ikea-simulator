package simulator.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.container.MineCart;
import simulator.container.OreContainer;
import simulator.fsm.*;
import simulator.truck.ITruck;
import simulator.truck.VolvoTruck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public final class MiningFactory implements IFactory {
  private final List<IStateMachine> processes = new ArrayList<>();
  private final AssetManager assets;
  private final Texture texture;
  private final Vector2 position;

  private final List<IContainer> productsToShip = new ArrayList<>();
  private ITruck readyTruck;

  public MiningFactory(Vector2 position, AssetManager assets) {
    this.position = position;
    this.assets = assets;
    this.texture = assets.get("resources/mine.png");

    // fills the truck with shippable products
    processes.add(new Repeat(new Sequence(new Wait(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return readyTruck != null && !productsToShip.isEmpty();
      }
    }), new Call(new FillReadyTruck()))));

    // adds a new truck
    processes.add(new Repeat(new Sequence(new Timer(3F), new Sequence(new Wait(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return readyTruck == null;
      }
    }), new Call(new AddReadyTruck())))));

    // every second we add a new mine cart
    processes.add(new Repeat(new Sequence(new Timer(1F), new Sequence(new Wait(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return productsToShip.size() != 6;
      }
    }), new Call(new AddMineCart())))));
  }

  private final class FillReadyTruck implements IAction {
    @Override
    public void run() {
      if (readyTruck.getContainer() == null) {
        float x = readyTruck.getPosition().x;
        float y = readyTruck.getPosition().y + 15F;

        readyTruck.addContainer(new OreContainer(100, new Vector2(x, y), assets));
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

  private final class AddMineCart implements IAction {
    MineCart createMineCart(Vector2 position) {
      return new MineCart(25, position, assets);
    }

    @Override
    public void run() {
      float mineX = getPosition().x;
      float mineY = getPosition().y;

      float cartX = mineX - 20F;

      // the C# version does mineY + (40 - 30 * size) because monogame's coordinate system
      // presumably starts at the top left corner whilst libgdx draws textures starting at the bottom left
      float cartY = mineY + (40 + 40 * getProductsToShip().size());

      Vector2 position = new Vector2(cartX, cartY);

      getProductsToShip().add(createMineCart(position));
    }
  }

  @Override
  public void update(float deltaTime) {
    for (IStateMachine machine : processes) {
      machine.update(deltaTime);
    }
  }

  private void spawnReadyTruck() {
    readyTruck = new VolvoTruck(new Vector2(360, 380), new Vector2(3, 0), false, assets);
  }

  @Override
  public void draw(SpriteBatch batch) {
    for (IContainer product : productsToShip) {
      product.draw(batch);
    }

    batch.draw(texture, position.x, position.y);
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
