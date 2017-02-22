package simulator.fsm;

public class Repeat extends AbstractStateMachine {
    IStateMachine action;

    public Repeat(IStateMachine action) {
        this.action = action;
    }

    @Override
    public boolean isBusy() {
        return true;
    }

    public void reset() {
        action.reset();
    }

    public void update(float deltaTime) {
        if (! action.isBusy()){
            reset();
        }
        action.update(deltaTime);
    }
}
