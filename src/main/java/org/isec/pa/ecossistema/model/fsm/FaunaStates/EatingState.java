package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

public class EatingState extends FaunaStateAdapter {

    public EatingState(FaunaContext context, Fauna data) {
        super(context, data);
    }

    @Override
    public FaunaState getState() {
        return FaunaState.EATING;
    }

}
