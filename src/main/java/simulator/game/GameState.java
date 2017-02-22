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
    List<ITruck> trucks;
    IFactory factory1;
    IFactory factory2;
    List<IStateMachine> processes;

    Texture background;

    public GameState(AssetManager assets) {
        this.background = assets.get("resources/background.png");

        // truck, mine, orebox, orecontainer
        // truck, ikea, productbox, productcontainer
        factory1 = new MiningFactory(new Vector2(100, 70), assets);//, new Dimension2D(150, 230/2), new Vector2(100,0), new Vector2(5,0), mine, oreContainer, mineCart, volvo);
        factory2 = new IkeaFactory(new Vector2(600, 340), assets);//, new Dimension2D(150, 175/2), new Vector2(-100,0), new Vector2(-4,0), ikea, productContainer, productBox, volvo);

        trucks = new ArrayList<>();

        this.processes = new ArrayList<>();
        this.processes.add(new Repeat(new Call(new AddTruckFromFactory(factory1, trucks))));
        this.processes.add(new Repeat(new Call(new AddTruckFromFactory(factory2, trucks))));
    }

    class AddTruckFromFactory implements IAction {
        IFactory factory;
        List<ITruck> trucks;

        public AddTruckFromFactory(IFactory factory, List<ITruck> trucks) {
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