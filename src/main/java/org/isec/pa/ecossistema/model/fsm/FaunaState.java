package org.isec.pa.ecossistema.model.fsm;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.EatingState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.IFaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.LookingForFaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.LookingForFloraState;

public enum FaunaState {

    LOOKING_FOR_FLORA, LOOKING_FOR_FAUNA, EATING;

    public IFaunaState getInstance(FaunaContext context, Fauna data) {
        return switch (this) {
            case LOOKING_FOR_FLORA -> new LookingForFloraState(context, data);
            case LOOKING_FOR_FAUNA -> new LookingForFaunaState(context, data);
            case EATING -> new EatingState(context, data);
        };
    }

}
