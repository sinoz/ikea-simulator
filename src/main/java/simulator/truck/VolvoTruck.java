package simulator.truck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.container.IContainer;
import simulator.factory.IkeaFactory;
import simulator.fsm.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public final class VolvoTruck implements ITruck {
  private final List<IStateMachine> processes = new ArrayList<>();
  private final Texture texture;
  private final AssetManager assets;

  private final Vector2 position;
  private final Vector2 velocity;
  private final boolean flipped;

  private IContainer carrying;

  public VolvoTruck(Vector2 position, Vector2 velocity, boolean flipped, AssetManager assets) {
    this.assets = assets;
    this.texture = assets.get("resources/volvo.png");
    this.position = position;

    this.velocity = velocity;
    this.flipped = flipped;

    processes.add(new Repeat(new Sequence(new Wait(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return carrying != null && carrying.getCurrentAmount() >= carrying.getMaxCapacity();
      }
    }), new Call(new IAction() {
      @Override
      public void run() {
        float toMove = (velocity.x * Gdx.graphics.getDeltaTime());

        position.set(position.x + toMove, position.y);
        carrying.getPosition().set(position.x - (flipped ? 60F : 0), position.y + 15F);
      }
    }))));
  }

  @Override
  public void update(float deltaTime) {
    for (IStateMachine machine : processes) {
      machine.update(deltaTime);
    }
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
      carrying.draw(batch);
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
