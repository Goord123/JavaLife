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
    public IFaunaState getCurrentState() {
        return FaunaState.LOOKING_FOR_FLORA.getInstance(context, fauna);
    }


    @Override
    public void evolve() {
        if (!fauna.checkIfAlive()) return;

        fauna.checkIfCanReproduce();

        if (fauna.checkIfOnFlora()) {
            changeState(FaunaState.EATING);
            context.changeState(FaunaState.EATING.getInstance(context, fauna));
        } else {
            // se tem target, move-se para ele
            if (fauna.getTarget() != null)
                fauna.getDirectionOfTarget();
            else {
                // se nao tem target, procura um
                Area target = fauna.checkForAdjacentFlora();
                if (target != null) { // se encontrou flora
                    fauna.setTarget(target);
                    fauna.getDirectionOfTarget();
                } else { // se nao encontrou flora, procura fauna
                    changeState(FaunaState.LOOKING_FOR_FAUNA);
                    context.changeState(FaunaState.LOOKING_FOR_FAUNA.getInstance(context, fauna));
                    Area area = fauna.lookForWeakestFauna();
                    if (area != null) {
                        fauna.setTarget(area);
                        fauna.getDirectionOfTarget();
                    }
                    else {
                        fauna.setDirection(null);
                        fauna.setTarget(null);
                        fauna.getDirectionOfTarget();
                    }
                }
            }
        }
    }

}
