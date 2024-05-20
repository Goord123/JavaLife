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
    public FaunaState getCurrentState() {
        return FaunaState.EATING;
    }

    @Override
    public void eat() {
        if (fauna.checkIfOnFlora())
            fauna.eat();
        else
            changeState(FaunaState.LOOKING_FOR_FLORA);
    }
}
