package simulator.fsm;

public class Print extends AbstractStateMachine {
    String message;

    public Print(String message) {
        this.message = message;
    }

    public void reset() {
        setBusy(true);
    }

    public void update(float deltaTime) {
        System.out.println(message);
        setBusy(false);
    }
}

