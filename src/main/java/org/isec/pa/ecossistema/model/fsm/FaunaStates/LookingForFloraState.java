package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;
import org.isec.pa.ecossistema.utils.Area;

public class LookingForFloraState extends FaunaStateAdapter {

        public LookingForFloraState(FaunaContext context, Fauna fauna) {
            super(context, fauna);
        }

        @Override
        public FaunaState getCurrentState() {
            return FaunaState.LOOKING_FOR_FLORA;
        }


        @Override
        public void evolve() {
            if (!fauna.checkIfAlive()) changeState(FaunaState.DEAD);

            fauna.checkIfCanReproduce();

            if (fauna.checkIfOnFlora()) {
                changeState(FaunaState.EATING);
            } else {
                Area target = fauna.checkForAdjacentFlora();
                if (target != null) {
                    fauna.moveTo(target);
                } else {
                    fauna.setDirection(null);
                    fauna.move();
                }
            }
        }
}
