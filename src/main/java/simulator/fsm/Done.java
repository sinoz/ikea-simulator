package simulator.fsm;

public class Done extends AbstractStateMachine {
    String message;

    public Done(String message) {
        this.message = message;
    }

    public void reset() {
        setBusy(true);
    }

    public void update(float deltaTime) {
        setBusy(false);
    }


}
