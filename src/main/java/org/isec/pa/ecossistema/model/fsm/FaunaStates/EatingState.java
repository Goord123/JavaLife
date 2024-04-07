package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.FaunaData;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

public class EatingState extends FaunaStateAdapter {

    public EatingState(FaunaContext context, FaunaData data) {
        super(context, data);
    }

    @Override
    public FaunaState getState() {
        return FaunaState.EATING;
    }

}
