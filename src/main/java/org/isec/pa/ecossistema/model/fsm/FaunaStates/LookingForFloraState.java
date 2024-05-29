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

        if (fauna.checkIfOnFlora() && fauna.getForca() < 80) {
            changeState(FaunaState.EATING);
        } else {
            // se tem target, move-se para ele
            if (fauna.getTarget() != null)
                fauna.getDirectionOfTarget();
            else {
                // se nao tem target, procura um
                Area targetFlora = fauna.checkForAdjacentFlora();
                if (targetFlora != null && fauna.getForca() < 80) { // se encontrou flora
                    fauna.setTarget(targetFlora);
                    fauna.getDirectionOfTarget();
                } else { // se nao encontrou flora, procura fauna
                    Area targetFauna = fauna.lookForWeakestFauna();
                    if (targetFauna != null) {
                        changeState(FaunaState.LOOKING_FOR_FAUNA);
                    } else {
                        fauna.setDirection(null);
                        fauna.setTarget(null);
                        fauna.getDirectionOfTarget();
                    }
                }
            }
        }
    }

}
