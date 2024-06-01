package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

import java.io.Serial;
import java.io.Serializable;

public class EatingState extends FaunaStateAdapter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public EatingState(FaunaContext context, Fauna fauna) {
        super(context, fauna);
    }

    // Default constructor needed for deserialization
    public EatingState() {
        super(null, null);
    }

    @Override
    public IFaunaState getCurrentState() {
        return FaunaState.EATING.getInstance(context, fauna);
    }

    @Override
    public void evolve() {
        if (!fauna.checkIfAlive()) return;
        if (!fauna.checkIfOnFlora()) {
            changeState(FaunaState.LOOKING_FOR_FLORA);
            return;
        }
        if (fauna.checkIfOnFlora() && fauna.getForca() < 100) {
            fauna.eat();
        } else {
            changeState(FaunaState.LOOKING_FOR_FLORA);
        }
    }
}
