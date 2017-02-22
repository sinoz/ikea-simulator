package simulator.fsm;

public class Timer extends AbstractStateMachine {
    float initialTime;
    float time;

    public Timer(float time) {
        this.time = time;
        this.initialTime = time;
        this.isBusy = true;
    }

    public void reset() {
        time = initialTime;
        setBusy(true);
    }

    public void update(float deltaTime) {
        time -= deltaTime;
        if(time <= 0){
            setBusy(false);
        }
    }
}

