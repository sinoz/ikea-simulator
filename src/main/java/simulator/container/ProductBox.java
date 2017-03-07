package simulator.container;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public final class ProductBox implements IContainer {
  private final Texture texture;
  private final int capacity;

  private int currentAmount;
  private Vector2 position;

  public ProductBox(int capacity, Vector2 position, AssetManager assets) {
    this.texture = assets.get("resources/product_box.png");
    this.capacity = capacity;
    this.position = position;
  }

  @Override
  public void draw(SpriteBatch batch) {
    batch.draw(texture, position.x, position.y, texture.getWidth() / 4, texture.getHeight() / 4);
  }

  @Override
  public int getCurrentAmount() {
    return currentAmount;
  }

  @Override
  public void setCurrentAmount(int currentAmount) {
    this.currentAmount = currentAmount;
  }

  @Override
  public int getMaxCapacity() {
    return capacity;
  }

  @Override
  public boolean addContent(int amount) {
    if ((currentAmount + amount) > capacity) {
      return false;
    }

    currentAmount += amount;
    return true;
  }

  @Override
  public Vector2 getPosition() {
    return position;
  }

  @Override
  public void setPosition(Vector2 position) {
    this.position = position;
  }
}
