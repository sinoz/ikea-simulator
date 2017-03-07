package simulator.fsm;

public class Call extends AbstractStateMachine {
    private final IAction action;

    public Call(IAction action) {
        this.action = action;
    }

    @Override
    public void reset() {
        setBusy(true);
    }

    @Override
    public void update(float deltaTime) {
        action.run();
        setBusy(false);
    }
}
