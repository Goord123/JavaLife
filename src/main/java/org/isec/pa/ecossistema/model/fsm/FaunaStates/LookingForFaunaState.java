package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

public class LookingForFaunaState extends FaunaStateAdapter {

        public LookingForFaunaState(FaunaContext context, Fauna fauna) {
            super(context, fauna);
        }

        @Override
        public FaunaState getCurrentState() {
            return FaunaState.LOOKING_FOR_FAUNA;
        }

        @Override
        public void evolve() {
            if (!fauna.checkIfAlive()) changeState(FaunaState.DEAD);
            fauna.hunt();
        }
}
