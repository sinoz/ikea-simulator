package simulator.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.container.ProductBox;
import simulator.container.ProductContainer;
import simulator.fsm.IAction;
import simulator.fsm.IStateMachine;
import simulator.truck.ITruck;
import simulator.truck.VolvoTruck;

import java.util.ArrayList;
import java.util.List;

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

    // TODO
  }

  private final class AddProductContainer implements IAction {
    @Override
    public void run() {
      float x = readyTruck.getPosition().x - 300;
      float y = readyTruck.getPosition().y + 30;

      readyTruck.addContainer(new ProductContainer(100, new Vector2(x, y), assets));
    }
  }

  private final class AddReadyTruck implements IAction {
    @Override
    public void run() {
      spawnReadyTruck();
    }
  }

  private final class AddProductBox implements IAction {
    private final IkeaFactory ikea;

    AddProductBox(IkeaFactory ikea) {
      this.ikea = ikea;
    }

    ProductBox createProductBox(Vector2 position) {
      return new ProductBox(100, position, assets);
    }

    @Override
    public void run() {
      float ikeaX = ikea.getPosition().x;
      float ikeaY = ikea.getPosition().y;

      float boxX = ikeaX + 20F;

      // the C# version does mineY + (40 - 30 * size) because monogame's coordinate system
      // presumably starts at the top left corner whilst libgdx draws textures starting at the bottom left
      float boxY = ikeaY + (40 + 40 * ikea.getProductsToShip().size());

      Vector2 position = new Vector2(boxX, boxY);

      ikea.getProductsToShip().add(createProductBox(position));
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
