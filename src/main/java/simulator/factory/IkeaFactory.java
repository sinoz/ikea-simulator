package simulator.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.truck.ITruck;

import java.util.List;

public final class IkeaFactory implements IFactory {
  private final Vector2 position;
  private final Texture texture;

  public IkeaFactory(Vector2 position, AssetManager assets) {
    this.texture = assets.get("resources/ikea.png");
    this.position = position;
  }

  @Override
  public void update(float deltaTime) {
    // TODO
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(texture, position.x, position.y);
  }

  @Override
  public ITruck getReadyTruck() {
    return null;
  }

  @Override
  public Vector2 getPosition() {
    return null;
  }

  @Override
  public List<IContainer> getProductsToShip() {
    return null;
  }
}
