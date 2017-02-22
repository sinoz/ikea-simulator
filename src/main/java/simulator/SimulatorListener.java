package simulator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import simulator.game.GameState;

public final class SimulatorListener implements ApplicationListener {
  private static final String[] RESOURCES = new String[] {
      "background", "mine_cart", "product_box", "volvo", "mine", "ikea", "ore_container", "product_container"
  };

  private GameState gameState;
  private SpriteBatch batch;
  private AssetManager assets;

  @Override
  public void create() {
    assets = new AssetManager();

    for (int i = 0; i < RESOURCES.length; i++) {
      assets.load("resources/" + RESOURCES[i] + ".png", Texture.class);
    }

    assets.finishLoading();

    gameState = new GameState(assets);
    batch = new SpriteBatch();
  }

  @Override
  public void resize(int width, int height) {
    // TODO
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 255F, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    gameState.update(Gdx.graphics.getDeltaTime());
    gameState.draw(batch);
  }

  @Override
  public void pause() {
    // TODO
  }

  @Override
  public void resume() {
    // TODO
  }

  @Override
  public void dispose() {
    batch.dispose();
  }
}
