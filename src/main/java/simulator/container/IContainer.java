package simulator.container;

import com.badlogic.gdx.math.Vector2;
import simulator.component.Drawable;

public interface IContainer extends Drawable {
    int getCurrentAmount();

    void setCurrentAmount(int currentAmount);

    int getMaxCapacity();

    boolean addContent(int amount);

    Vector2 getPosition();

    void setPosition(Vector2 position);
}

