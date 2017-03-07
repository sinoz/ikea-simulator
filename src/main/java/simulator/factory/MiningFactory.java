package simulator.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.truck.ITruck;
import simulator.truck.VolvoTruck;

import java.util.ArrayList;
import java.util.List;

public final class MiningFactory implements IFactory {
  private final AssetManager assets;
  private final Texture texture;
  private final Vector2 position;

  private final List<IContainer> products = new ArrayList<>();
  private ITruck readyTruck;

  public MiningFactory(Vector2 position, AssetManager assets) {
    this.position = position;
    this.assets = assets;
    this.texture = assets.get("resources/mine.png");
  }

  @Override
  public void update(float deltaTime) {
    // TODO
  }

  private void spawnReadyTruck() {
    readyTruck = new VolvoTruck(new Vector2(360, 380), new Vector2(5, 0), false, assets);
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(texture, position.x, position.y);

    for (IContainer container : products) {
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
    return products;
  }
}
