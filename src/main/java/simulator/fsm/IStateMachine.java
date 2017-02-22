package simulator.fsm;

import simulator.component.Updateable;

public interface IStateMachine extends Updateable {

    boolean isBusy();

    void reset();
}
