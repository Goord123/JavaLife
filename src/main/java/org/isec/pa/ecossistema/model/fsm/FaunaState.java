package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.*;

public enum FaunaState {

    LOOKING_FOR_FLORA, LOOKING_FOR_FAUNA, EATING, REPRODUCING, DEAD;

    public IFaunaState getInstance(FaunaContext context, Fauna data) {
        return switch (this) {
            case LOOKING_FOR_FLORA -> new LookingForFloraState(context, data);
            case LOOKING_FOR_FAUNA -> new LookingForFaunaState(context, data);
            case EATING -> new EatingState(context, data);
            case REPRODUCING -> new ReproducingState(context, data);
            case DEAD -> new DeadState(context, data);
        };
    }
}
