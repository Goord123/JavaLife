package org.isec.pa.ecossistema.model.fsm.FaunaStates;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStateAdapter;

public class EatingState extends FaunaStateAdapter {

    public EatingState(FaunaContext context, Fauna fauna) {
        super(context, fauna);
    }

    @Override
    public IFaunaState getCurrentState() {
        return FaunaState.EATING.getInstance(context, fauna);
    }

    @Override
    public void evolve() {
        if (!fauna.checkIfAlive()) return;
        if (!fauna.checkIfOnFlora()){
            changeState(FaunaState.LOOKING_FOR_FLORA);
            return;
        }
        if (fauna.checkIfOnFlora() && fauna.getForca() < 100) {
            fauna.eat();
        }
        else {
            changeState(FaunaState.LOOKING_FOR_FLORA);
        }
    }
}
