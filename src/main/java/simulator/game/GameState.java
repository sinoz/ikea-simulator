package simulator.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import simulator.component.IComponent;
import simulator.factory.IFactory;
import simulator.factory.IkeaFactory;
import simulator.factory.MiningFactory;
import simulator.fsm.Call;
import simulator.fsm.IAction;
import simulator.fsm.IStateMachine;
import simulator.fsm.Repeat;
import simulator.truck.ITruck;

import java.util.ArrayList;
import java.util.List;

public final class GameState implements IComponent {
    private final List<ITruck> trucks;

    private final IFactory factory1, factory2;

    private final List<IStateMachine> processes;

    private final Texture background;

    public GameState(AssetManager assets) {
        background = assets.get("resources/background.png");

        factory1 = new MiningFactory(new Vector2(50, 310), assets);
        factory2 = new IkeaFactory(new Vector2(500, 70), assets);

        trucks = new ArrayList<>();
        processes = new ArrayList<>();

        processes.add(new Repeat(new Call(new AddTruckFromFactory(factory1, trucks))));
        processes.add(new Repeat(new Call(new AddTruckFromFactory(factory2, trucks))));
    }

    private final class AddTruckFromFactory implements IAction {
        IFactory factory;
        List<ITruck> trucks;

        AddTruckFromFactory(IFactory factory, List<ITruck> trucks) {
            this.factory = factory;
            this.trucks = trucks;
        }

        public void run(){
            ITruck truck = factory.getReadyTruck();
            if (truck != null){
                truck.startEngine();
                trucks.add(truck);
            }
        }
    }
    
    public void update(float deltaTime) {
        trucks.removeIf(truck -> truck.getPosition().x < -50 || truck.getPosition().x > 1000);

        for (IStateMachine process : processes) {
            process.update(deltaTime);
        }

        for (ITruck truck : trucks) {
            truck.update(deltaTime);
        }

        factory1.update(deltaTime);
        factory2.update(deltaTime);
    }

    public void draw(SpriteBatch batch) {
        batch.begin();

        {
            batch.draw(background, 0, 0);

            for (ITruck truck : trucks) {
                truck.draw(batch);
            }

            factory1.draw(batch);
            factory2.draw(batch);
        }

        batch.end();
    }
}