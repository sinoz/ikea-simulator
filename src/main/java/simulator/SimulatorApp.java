package simulator;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SimulatorApp {
  public static void main(String[] args) {
    new LwjglApplication(new SimulatorListener(), new SimulatorConfig());
  }

  private static class SimulatorConfig extends LwjglApplicationConfiguration {
    public SimulatorConfig() {
      configure();
    }

    private void configure() {
      title = "INFDEV02-3 - Assignment 1";
      width = 800;
      height = 600;
      resizable = false;
      vSyncEnabled = false;
    }
  }
}
