package simulator.factory;

import com.badlogic.gdx.math.Vector2;
import simulator.component.IComponent;
import simulator.container.IContainer;
import simulator.truck.ITruck;

import java.util.List;

public interface IFactory extends IComponent {
    ITruck getReadyTruck();

    Vector2 getPosition();

    List<IContainer> getProductsToShip();
}
