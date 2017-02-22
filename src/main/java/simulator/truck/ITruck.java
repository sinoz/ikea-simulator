package simulator.truck;

import com.badlogic.gdx.math.Vector2;
import simulator.component.IComponent;
import simulator.container.IContainer;

public interface ITruck extends IComponent {
    void startEngine();

    void addContainer(IContainer container);

    IContainer getContainer();

    Vector2 getPosition();

    Vector2 getVelocity();
}
