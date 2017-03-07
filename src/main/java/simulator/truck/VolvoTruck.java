package simulator.truck;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.container.OreContainer;

public final class VolvoTruck implements ITruck {
  private final Texture texture;
  private final Vector2 position;
  private final Vector2 velocity;
  private final boolean flipped;

  private IContainer carrying;

  public VolvoTruck(Vector2 position, Vector2 velocity, boolean flipped, AssetManager assets) {
    this.texture = assets.get("resources/volvo.png");
    this.position = position;

    this.velocity = velocity;
    this.flipped = flipped;
  }

  @Override
  public void update(float deltaTime) {
    // TODO
  }

  @Override
  public void draw(SpriteBatch batch) {
    drawTruck(batch);
    drawContainer(batch);
  }

  private void drawTruck(SpriteBatch batch) {
    int width = texture.getWidth() / 4;
    if (flipped) {
      width = -width;
    }

    int height = texture.getHeight() / 4;

    float x = getPosition().x;
    float y = getPosition().y;

    if (flipped) {
      x = getPosition().x + (height / 4);
    }

    batch.draw(texture, x, y, width, height);
  }

  private void drawContainer(SpriteBatch batch) {
    if (carrying != null) {

    }
  }

  @Override
  public void startEngine() {
    // TODO
  }

  @Override
  public void addContainer(IContainer container) {
    this.carrying = container;
  }

  @Override
  public IContainer getContainer() {
    return carrying;
  }

  public Vector2 getPosition() {
    return position;
  }

  @Override
  public Vector2 getVelocity() {
    return velocity;
  }
}
