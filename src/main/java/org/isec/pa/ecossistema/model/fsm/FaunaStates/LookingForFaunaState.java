package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;
import org.isec.pa.ecossistema.utils.Area;

public class LookingForFaunaState extends FaunaStateAdapter {

        public LookingForFaunaState(FaunaContext context, Fauna fauna) {
            super(context, fauna);
        }

        @Override
        public IFaunaState getCurrentState() {
            return FaunaState.LOOKING_FOR_FAUNA.getInstance(context, fauna);
        }

        @Override
        public void evolve() {
            if (!fauna.checkIfAlive()) return;
            fauna.checkIfCanReproduce();

            Area target = fauna.checkForAdjacentFlora();
            if (target != null) {
                this.fauna.setTarget(target);
                fauna.getDirectionOfTarget();
                changeState(FaunaState.LOOKING_FOR_FLORA);
                context.changeState(FaunaState.LOOKING_FOR_FLORA.getInstance(context, fauna));
            } else {
                fauna.hunt();
            }
        }
}
