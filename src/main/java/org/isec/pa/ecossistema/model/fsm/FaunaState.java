package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.FaunaData;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.*;

public enum FaunaState {

    INITIAL_STATE, EATING, REPRODUCING, DEAD;

    public IFaunaState getInstance(FaunaContext context, FaunaData data) {
        return switch (this) {
            case INITIAL_STATE -> new InitialState(context, data);
            case EATING -> new EatingState(context, data);
            case REPRODUCING -> new ReproducingState(context, data);
            case DEAD -> new DeadState(context, data);
        };
    }
}
