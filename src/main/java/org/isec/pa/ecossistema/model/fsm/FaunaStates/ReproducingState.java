package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

public class ReproducingState extends FaunaStateAdapter {

        public ReproducingState(FaunaContext context, Fauna data) {
            super(context, data);
        }

        @Override
        public FaunaState getState() {
            return FaunaState.REPRODUCING;
        }
}
