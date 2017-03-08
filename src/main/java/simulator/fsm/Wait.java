package simulator.fsm;

import java.util.function.Predicate;

public class Wait extends AbstractStateMachine {
    Predicate<Object> condition;

    public Wait(Predicate<Object> condition) {
        this.condition = condition;
    }

    public void reset() {
        setBusy(true);
    }

    public void update(float deltaTime) {
        if (condition.test(null)){
            setBusy(false);
        }
    }
}

